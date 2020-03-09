from socketserver import ThreadingMixIn
from threading import Thread
from io import BytesIO
from PIL import Image
import numpy as np
import cv2
import math
from networktables import NetworkTables
from http.server import BaseHTTPRequestHandler, HTTPServer
import time
from stream import WebcamVideoStream
import os

isRunningOnPi = True
try:
  import RPi.GPIO as GPIO
except ImportError:
  print("Not running on coproc...")
  isRunningOnPi = False

BLACK = (0, 0, 0)
RED = (0, 0, 255)
GREEN = (0, 255, 0)
BLUE = (255, 0, 0)
TURQ = (208, 224, 64)
ORANGE = (0, 127, 255)
YELLOW = (0, 255, 255)
PURPLE = (128, 0, 128)
WHITE = (255, 255, 255)


class CamHandler(BaseHTTPRequestHandler):
  def do_GET(self):

    if self.path.endswith('.mjpg'):
      self.send_response(200)
      self.send_header('Content-type', 'multipart/x-mixed-replace; boundary=--jpgboundary')
      self.end_headers()
      while True:
        try:
          global frame_filtered, port
          img = frame_filtered
          imgRGB = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
          jpg = Image.fromarray(imgRGB)
          tmpFile = BytesIO()
          jpg.save(tmpFile, 'JPEG')
          self.wfile.write("--jpgboundary".encode())
          self.send_header('Content-type', 'image/jpeg')
          self.send_header('Content-length', str(tmpFile.getbuffer().nbytes))
          self.end_headers()
          self.wfile.write(tmpFile.getvalue())
          # jpg.save(self.wfile, 'JPEG')
          time.sleep(0.05)
        except KeyboardInterrupt:
          break
      return
    if self.path.endswith('.html'):
      self.send_response(200)
      self.send_header('Content-type', 'text/html')
      self.end_headers()
      self.wfile.write('<html><head></head><body>'.encode())
      self.wfile.write(
        ('<img src="http:// ' + self.client_address[0] + ':' + str(port) + '/cam.mjpg"/>').encode())
      self.wfile.write('</body></html>'.encode())
      return


class ThreadedHTTPServer(ThreadingMixIn, HTTPServer):
  """Handle requests in a separate thread."""


def serve():
  global server
  port = 8087
  server = ThreadedHTTPServer(("", port), CamHandler)
  print("started server on port", port)
  server.serve_forever()


def stop_server():
  global server
  server.server_close()
  server.stopped = True
  server.shutdown()


def resetTable(vt):
  vt.putNumber("angleOfTarget", -1)
  vt.putNumber("distanceToTarget", -1)
  vt.putNumber("targetX", -1)
  vt.putNumber("targetY", -1)

  vt.putNumber("ballX", -1)
  vt.putNumber("ballY", -1)
  vt.putNumber("distanceToBall", -1)

  vt.putNumber("heartbeat", -1)


def haveSameCoordinates(rect1, rect2):
  if round(rect1[0][0], 0) == round(rect2[0][0], 0) and round(rect1[0][1], 0) == round(rect2[0][1], 0):
    return True
  else:
    return False


def isCorrectShape(rect):
  if rect[1][0] > 6 and rect[1][1] > 6:
    correct_ratio = 5.5 / 2.0  # 5.5" by 2" tape strip
    err = 5
    width = rect[1][0]
    height = rect[1][1]
    ratio = round(height / width, 2)
    if ratio < 1:
      ratio = 1 / ratio
    if (correct_ratio - 1) < ratio < (correct_ratio + err):
      return True
  return False


def getRegularRatio(ratio):
  r = ratio
  if r < 1:
    r = 1 / r
  return r


def distance_to_camera(pixHeight):
  KNOWN_DISTANCE = 61
  KNOWN_HEIGHT = 115
  focalHeight = 51.333336
  return (KNOWN_HEIGHT * focalHeight) / pixHeight


def width_to_pixel_width(width):
  return 8 / width


def drawBox(frame, rect, color=RED):
  box = cv2.boxPoints(rect)
  box = np.array(box).reshape((-1, 1, 2)).astype(np.int32)
  cv2.drawContours(frame, [box], -1, color, 1)


def removeRepeatContours(rectborders):
  # ---- FILTER OUT REPEAT CONTOURS ----
  # Is this necessary???
  rounded = []
  for rect in rectborders:
    n = -1
    rnd_rect = ((round(rect[0][0], n), round(rect[0][1], n)), (round(rect[1][0], n), round(rect[1][1], n)),
                round(rect[2], n))
    for r2 in rounded:
      if rnd_rect == r2:
        rectborders.remove(rect)
        rounded.remove(rnd_rect)

    rounded.append(rnd_rect)
  return rectborders


# sim_* resembles range of difference between rectangles that is deemed "acceptable" for them to be a pair
sim_ratio = 3
sim_angle = 10


def translateRotation(rotation, width, height):
  if (width < height):
    rotation = -1 * (rotation - 90)
  if (rotation > 90):
    rotation = -1 * (rotation - 180)
  rotation *= -1
  return round(rotation)

def findTarget(rectborders, frame=-1):
  pairs = []
  for r in rectborders:

    if isCorrectShape(r):

      width_r = r[1][0]
      height_r = r[1][1]
      ratio_r = round(height_r / width_r, 2)
      angle_r = translateRotation(round(r[2], 1), width_r, height_r)

      x_r = r[0][0]
      y_r = r[0][1]

      if ratio_r < 1:
        ratio_r = 1 / ratio_r
        width_r = r[1][1]
        height_r = r[1][0]
        cv2.putText(frame, "switched", (int(x_r), int(y_r + 10)),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.4, RED, 1)

      for r2 in rectborders:
        if r == r2 or haveSameCoordinates(r, r2):
          break
        elif isCorrectShape(r2):
          drawBox(frame, r, WHITE)
          drawBox(frame, r2, WHITE)

          width_r2 = r[1][0]
          height_r2 = r2[1][1]
          ratio_r2 = round(height_r2 / width_r2, 2)
          angle_r2 = translateRotation(round(r2[2], 1), width_r2, height_r2)
          x_r2 = r2[0][0]
          y_r2 = r2[0][1]

          if ratio_r2 < 1:
            ratio_r2 = 1 / ratio_r2
            width_r2 = r2[1][1]
            height_r2 = r2[1][0]
            cv2.putText(frame, "switched", (int(x_r2), int(y_r2 + 10)),
                        cv2.FONT_HERSHEY_SIMPLEX, 0.4, RED, 1)

          avg_width = (width_r + width_r2) / 2
          avg_height = (height_r + height_r2) / 2

          offset = math.sqrt((y_r2 - y_r) ** 2 + (x_r2 - x_r) ** 2)

          cv2.putText(frame, "R1", (int(x_r2), int(y_r2)),
                      cv2.FONT_HERSHEY_SIMPLEX, 0.4, RED, 1)
          cv2.putText(frame, "R2", (int(x_r), int(y_r)),
                      cv2.FONT_HERSHEY_SIMPLEX, 0.4, RED, 1)

          if offset < 7 * avg_width or offset < 2.3 * avg_height:
            drawBox(frame, r, YELLOW)
            drawBox(frame, r2, YELLOW)
            if (ratio_r2 < ratio_r + sim_ratio and ratio_r2 > ratio_r - sim_ratio):
              drawBox(frame, r, BLUE)
              drawBox(frame, r2, BLUE)
              cv2.putText(frame, "angle: " + str(round(angle_r2, 0)) + "deg", (int(x_r2), int(y_r2 + 60)),
                          cv2.FONT_HERSHEY_SIMPLEX, 0.4, BLUE, 1)
              cv2.putText(frame, "angle: " + str(round(angle_r, 0)) + "deg", (int(x_r), int(y_r + 60)),
                          cv2.FONT_HERSHEY_SIMPLEX, 0.4, BLUE, 1)
              if (29 + sim_angle) > abs(angle_r2 - angle_r) > (29 - sim_angle):
                distance = (distance_to_camera(offset) / 12)  # in feet
                if type(frame) != int:
                  cv2.putText(frame, "angle: " + str(round(angle_r2, 0)) + "deg", (int(x_r2), int(y_r2 + 60)),
                              cv2.FONT_HERSHEY_SIMPLEX, 0.4, WHITE, 1)
                  cv2.putText(frame, "angle: " + str(round(angle_r, 0)) + "deg", (int(x_r), int(y_r + 60)),
                              cv2.FONT_HERSHEY_SIMPLEX, 0.4, WHITE, 1)
                  cv2.line(frame, (int(x_r2), int(y_r2)), (int(x_r), int(y_r)), YELLOW, 1)
                  # Display Distance
                  cv2.putText(frame, "%.2fft" % distance, (frame.shape[1] - 200, frame.shape[0] - 100),
                              cv2.FONT_HERSHEY_SIMPLEX, 2.0, YELLOW, 3)
                  cv2.putText(frame, "angle: " + str(round(angle_r, 0)) + "deg", (int(x_r), int(y_r + 60)),
                              cv2.FONT_HERSHEY_SIMPLEX, 0.4, WHITE, 1)
                  drawBox(frame, r, PURPLE)
                  drawBox(frame, r2, PURPLE)
                pairs.append([r, r2])
                center = ((x_r + x_r2) / 2, (y_r + y_r2) / 2)
                if enableNetworkTables:
                  # vt.putNumber("angle", perspective_angle)
                  vt.putNumber("distanceToBall", distance)
                  vt.putNumber("ballX", center[0])
                  vt.putNumber("ballY", center[1])
  return pairs


def getFPS(frame_counter):
  global start_t
  dt = (time.time() - start_t)
  FPS = int(frame_counter / dt)
  start_t = time.time()
  return FPS


def halt():
  vs.stop()
  cv2.destroyAllWindows()
  if isRunningOnPi:
    GPIO.output(21, GPIO.LOW)
    GPIO.cleanup()
  stop_server()


def nothing(x):
  pass


def targetImagesProcessed(frame):
  pt = time.time()



  kernel = np.ones((6, 6), np.uint8)
  hsv = cv2.cvtColor(frame, cv2.COLOR_BGR2HSV)
  #hsv = cv2.morphologyEx(hsv, cv2.MORPH_OPEN, kernel)
  processTimes[0] += time.time() - pt
  pt = time.time()

  mask = cv2.inRange(hsv, lower_c_hole, upper_c_hole) # color filtering is about 14% processing power (just target)
  rgb = cv2.cvtColor(mask, cv2.COLOR_BAYER_BG2RGB)
  res = cv2.bitwise_and(frame, frame, mask=mask)
  processTimes[1] += time.time() - pt
  pt = time.time()

  gray = cv2.cvtColor(rgb, cv2.COLOR_BGR2GRAY)
  gray = cv2.GaussianBlur(gray, (5, 5), 3)
  edged = cv2.Canny(gray, 300, 400, L2gradient=True)
  processTimes[2] += time.time() - pt
  return res, edged


if __name__ == '__main__':
  start_t = time.time()

  # Tracking Setings
  ballTracking = False
  targetTracking = True

  # FPS settings
  displayFPS = True
  FPS = 0
  frame_counter = 0
  FPS_update_period = 0.2
  FPSColors = [RED, YELLOW, GREEN]

  # Enable or Disable network tables
  enableNetworkTables = True

  # Display settings
  displayWindows = (os.name == 'nt') or ("DISPLAY" in os.environ)
  debugWindows = True
  unfilteredWindow = True

  vs = WebcamVideoStream().start()
  # vt.putNumber("screen_width", 1920)

  # Range of color in hsv (Target)
  lower_c_hole = np.array([86, 0, 176])
  upper_c_hole = np.array([180, 255, 255])
  # lower_c_hole = np.array([83, 0, 191])
  # upper_c_hole = np.array([180, 138, 255])
  # at home
  # lower_c_hole = np.array([28, 22, 121])
  # upper_c_hole = np.array([64, 255, 255])
  # Range of color in hsv (Ball)
  lower_c_ball = np.array([0, 79, 49])
  upper_c_ball = np.array([29, 255, 255])
  # lower_c_ball = np.array([0, 93, 58])
  # upper_c_ball = np.array([13, 255, 255])
  cv2.imshow("image_ball", vs.read())
  cv2.createTrackbar('Param1', 'image_ball', 1, 1000, nothing)
  cv2.createTrackbar('Param2', 'image_ball', 1, 1000, nothing)
  cv2.createTrackbar('DP', 'image_ball', 1, 5, nothing)
  cv2.setTrackbarPos("Param1", 'image_ball', 25)
  cv2.setTrackbarPos("Param2", 'image_ball', 176)
  cv2.setTrackbarPos("DP", 'image_ball', 2)
  server_thread = Thread(target=serve, args=())
  server_thread.start()

  indicatorLED = 21

  if isRunningOnPi:
    GPIO.setwarnings(False)
    GPIO.setmode(GPIO.BCM)
    GPIO.setup(indicatorLED, GPIO.OUT, initial=GPIO.LOW)

  if enableNetworkTables:
    NetworkTables.initialize(server="roboRIO-2713-frc.local")
    vt = NetworkTables.getTable("VisionProcessing")
    resetTable(vt)
    vt.putNumber("heartbeat", 0)
  isRunning = True
  cv2.createTrackbar('var', 'image_ball', 1, 2, nothing)
  try:
    processStart = time.time()
    processTimes = [0,0,0]
    while isRunning:


      if isRunningOnPi:
        if vs.grabbed:
          GPIO.output(indicatorLED, GPIO.HIGH)
        else:
          GPIO.output(indicatorLED, GPIO.LOW)

      frame_filtered = vs.readFiltered()
      frame_unfiltered = vs.read()

      # Display FPS
      frame_counter += 1
      if displayFPS == True:

        if time.time() - start_t >= FPS_update_period:
          FPS = getFPS(frame_counter)
          frame_counter = 0

        c = int(math.floor(FPS / 10))
        if c > 2:
          c = 2
        cv2.putText(frame_filtered, str(FPS) + " FPS", (frame_filtered.shape[1] - 130, 40),
                    cv2.FONT_HERSHEY_SIMPLEX, 1.0, FPSColors[c], 2)

      # Hole Target Processing

      if targetTracking:
        res_target, edged_target = targetImagesProcessed(frame_filtered)


        frame_filtered = frame_filtered.copy()
        _, cnts, _ = cv2.findContours(edged_target, cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
        rectborders = [cv2.minAreaRect(c) for c in cnts]
        rectborders = removeRepeatContours(rectborders)
        pairs = findTarget(rectborders, frame_filtered)

        if displayWindows: # 50% of target processing
          if debugWindows:
            cv2.imshow("res target", cv2.resize(res_target, (350, 300)))
            cv2.imshow("edged target", cv2.resize(edged_target, (350, 300)))
          cv2.imshow("frame target", frame_filtered)


      if ballTracking:

        # Ball processing
        hsv_ball = cv2.cvtColor(frame_unfiltered, cv2.COLOR_BGR2HSV)
        kernel = np.ones((10, 10), np.uint8)
        hsv_ball = cv2.morphologyEx(hsv_ball, cv2.MORPH_OPEN, kernel)
        orangeBallMask = cv2.inRange(hsv_ball, lower_c_ball, upper_c_ball)
        rgb_ball = cv2.cvtColor(orangeBallMask, cv2.COLOR_BAYER_BG2BGR)
        res_ball = cv2.bitwise_and(frame_unfiltered, frame_unfiltered, mask=orangeBallMask)
        gray_ball = cv2.cvtColor(res_ball, cv2.COLOR_BGR2GRAY)

        gray_ball = cv2.GaussianBlur(gray_ball, (5, 5), 3)

        frame_unfiltered = frame_unfiltered.copy()
        p1 = cv2.getTrackbarPos('Param1', 'image_ball')
        p2 = cv2.getTrackbarPos('Param2', 'image_ball')
        dp = cv2.getTrackbarPos('DP', 'image_ball')
        circles = cv2.HoughCircles(gray_ball, cv2.HOUGH_GRADIENT, dp, np.shape(gray_ball)[0] / 8,
                                   param1=p1, param2=p2, minRadius=20, maxRadius=500)

        if circles is not None:
          circles = np.round(circles[0, :]).astype("int")
          for (x, y, r) in circles:
            cv2.circle(frame_unfiltered, (x, y), r, BLUE, 2)
            cv2.rectangle(frame_unfiltered, (x - 5, y - 5), (x + 5, y + 5), BLUE, -1)
        if displayWindows:
          if debugWindows:
            cv2.imshow("res_ball", cv2.resize(res_ball, (350, 300)))
            cv2.imshow("gray_ball", cv2.resize(gray_ball, (350, 300)))
          if unfilteredWindow:
            cv2.imshow("image_ball", frame_unfiltered)

      if enableNetworkTables:
        # If roboRIO sets running to 0, stop running.
        vt.putNumber("heartbeat", vt.getNumber("heartbeat", 0) + 1)
        isRunning = vt.getBoolean("isRunning", True)
        targetTracking = vt.getBoolean("targetTracking", targetTracking)
        ballTracking = vt.getBoolean("ballTracking", ballTracking)

      if (cv2.waitKey(1) & 0xFF) == ord('q'):
        break

      for t in range(len(processTimes)):
        print("[" + str(t) + "]" + str(int(100 * processTimes[t] / (time.time() - processStart))))
    halt()
    print("\nEnding")

  except KeyboardInterrupt:
    halt()
    print("\nKeyboardInterrupt: Exiting Vision.")
