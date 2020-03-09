#!/usr/bin/env python3
# ----------------------------------------------------------------------------
# Copyright (c) 2018 FIRST. All Rights Reserved.
# Open Source Software - may be modified and shared by FRC teams. The code
# must be accompanied by the FIRST BSD license file in the root directory of
# the project.

# My 2019 license: use it as much as you want. Crediting is recommended because it lets me know that I am being useful.
# Credit to Screaming Chickens 3997

# This is meant to be used in conjuction with WPILib Raspberry Pi image: https://github.com/wpilibsuite/FRCVision-pi-gen
# ----------------------------------------------------------------------------

# import the necessary packages
import datetime
import json
import math
import sys
from threading import Thread

import cv2
import numpy as np
from cscore import CameraServer
from networktables import NetworkTables
from networktables import NetworkTablesInstance

BLACK = (0, 0, 0)
RED = (0, 0, 255)
GREEN = (0, 255, 0)
BLUE = (255, 0, 0)
TURQ = (208, 224, 64)
ORANGE = (0, 127, 255)
YELLOW = (0, 255, 255)
PURPLE = (128, 0, 128)
WHITE = (255, 255, 255)


########### SET RESOLUTION TO 256x144 !!!! ############

# Class to examine Frames per second of camera stream. Currently not used.
class FPS:
  def __init__(self):
    # store the start time, end time, and total number of frames
    # that were examined between the start and end intervals
    self._start = None
    self._end = None
    self._numFrames = 0

  def start(self):
    # start the timer
    self._start = datetime.datetime.now()
    return self

  def stop(self):
    # stop the timer
    self._end = datetime.datetime.now()

  def update(self):
    # increment the total number of frames examined during the
    # start and end intervals
    self._numFrames += 1

  def elapsed(self):
    # return the total number of seconds between the start and
    # end interval
    return (self._end - self._start).total_seconds()

  def fps(self):
    # compute the (approximate) frames per second
    return self._numFrames / self.elapsed()


# class that runs separate thread for showing video,
class VideoShow:
  """
  Class that continuously shows a frame using a dedicated thread.
  """

  def __init__(self, imgWidth, imgHeight, cameraServer, frame=None):
    self.outputStream = cameraServer.putVideo("stream", imgWidth, imgHeight)
    self.frame = frame
    self.stopped = False

  def start(self):
    Thread(target=self.show, args=()).start()
    return self

  def show(self):
    while not self.stopped:
      self.outputStream.putFrame(self.frame)

  def stop(self):
    self.stopped = True

  def notifyError(self, error):
    self.outputStream.notifyError(error)


# Class that runs a separate thread for reading  camera server also controlling exposure.
class WebcamVideoStream:
  def __init__(self, camera, cameraServer, frameWidth, frameHeight, name="WebcamVideoStream"):
    # initialize the video camera stream and read the first frame
    # from the stream

    # Automatically sets exposure to 0 to track tape
    self.webcam = camera
    self.webcam.setExposureManual(0)
    # Some booleans so that we don't keep setting exposure over and over to the same value
    self.autoExpose = False
    self.prevValue = self.autoExpose
    # Make a blank image to write on
    self.img = np.zeros(shape=(frameWidth, frameHeight, 3), dtype=np.uint8)
    # Gets the video
    self.stream = cameraServer.getVideo()
    (self.timestamp, self.img) = self.stream.grabFrame(self.img)

    # initialize the thread name
    self.name = name

    # initialize the variable used to indicate if the thread should
    # be stopped
    self.stopped = False

  def start(self):
    # start the thread to read frames from the video stream
    t = Thread(target=self.update, name=self.name, args=())
    t.daemon = True
    t.start()
    return self

  def update(self):
    # keep looping infinitely until the thread is stopped
    while True:
      # if the thread indicator variable is set, stop the thread
      if self.stopped:
        return
      # Boolean logic we don't keep setting exposure over and over to the same value
      if self.autoExpose:
        if (self.autoExpose != self.prevValue):
          self.prevValue = self.autoExpose
          self.webcam.setExposureAuto()
      else:
        if (self.autoExpose != self.prevValue):
          self.prevValue = self.autoExpose
          self.webcam.setExposureManual(0)
      # gets the image and timestamp from cameraserver
      (self.timestamp, self.img) = self.stream.grabFrame(self.img)

  def read(self):
    # return the frame most recently read
    return self.timestamp, self.img

  def stop(self):
    # indicate that the thread should be stopped
    self.stopped = True

  def getError(self):
    return self.stream.getError()


###################### PROCESSING OPENCV ################################

# Angles in radians

# image size ratioed to 16:9
image_width = 256
image_height = 144

# Lifecam 3000 from datasheet
diagonalView = math.radians(90)

# 16:9 aspect ratio
horizontalAspect = 16
verticalAspect = 9

# Reasons for using diagonal aspect is to calculate horizontal field of view.
diagonalAspect = math.hypot(horizontalAspect, verticalAspect)
# Calculations: http://vrguy.blogspot.com/2013/04/converting-diagonal-field-of-view-and.html
horizontalView = math.atan(math.tan(diagonalView / 2) * (horizontalAspect / diagonalAspect)) * 2
verticalView = math.atan(math.tan(diagonalView / 2) * (verticalAspect / diagonalAspect)) * 2

# Focal Length calculations: https://docs.google.com/presentation/d/1ediRsI-oR3-kwawFJZ34_ZTlQS2SDBLjZasjzZ-eXbQ/pub?start=false&loop=false&slide=id.g12c083cffa_0_165
H_FOCAL_LENGTH = image_width / (2 * math.tan((horizontalView / 2)))
V_FOCAL_LENGTH = image_height / (2 * math.tan((verticalView / 2)))
# blurs have to be odd
green_blur = 1
orange_blur = 27

# define range of green of retroreflective tape in HSV
lower_green = np.array([55, 0, 245])
upper_green = np.array([107, 95, 255])
# define range of orange from cargo ball in HSV
lower_orange = np.array([0, 193, 92])
upper_orange = np.array([23, 255, 255])


# Flip image if camera mounted upside down
def flipImage(frame):
  return cv2.flip(frame, -1)


# Blurs frame
def blurImg(frame, blur_radius):
  img = frame.copy()
  blur = cv2.blur(img, (blur_radius, blur_radius))
  return blur


# Masks the video based on a range of hsv colors
# Takes in a frame, range of color, and a blurred frame, returns a masked frame
def threshold_video(lower_color, upper_color, blur):
  # Convert BGR to HSV
  hsv = cv2.cvtColor(blur, cv2.COLOR_BGR2HSV)

  # hold the HSV image to get only red colors
  mask = cv2.inRange(hsv, lower_color, upper_color)
  rgb = cv2.cvtColor(mask, cv2.COLOR_BAYER_BG2RGB)

  gray = cv2.cvtColor(rgb, cv2.COLOR_BGR2GRAY)
  gray = cv2.GaussianBlur(gray, (5, 5), 3)
  edged = cv2.Canny(gray, 300, 400, L2gradient=True)
  # Returns the masked imageBlurs video to smooth out image

  return edged


# Finds the tape targets from the masked image and displays them on original stream + network tales
def findTargets(frame, mask):
  # Finds contours

  _, contours, _ = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_TC89_KCOS)
  rectborders = [cv2.minAreaRect(c) for c in contours]
  # Take each frame
  # Gets the shape of video
  screenHeight, screenWidth, _ = frame.shape
  # Gets center of height and width
  centerX = (screenWidth / 2) - .5
  centerY = (screenHeight / 2) - .5
  # Copies frame and stores it in image
  image = frame.copy()
  # Processes the contours, takes in (contours, output_image, (centerOfImage)
  if len(rectborders) != 0:

    image = findTarget(rectborders, image)

  # Shows the contours overlayed on the original video
  return image


# Finds the balls from the masked image and displays them on original stream + network tables
def findCargo(frame, mask):
  # Finds contours
  _, contours, _ = cv2.findContours(mask, cv2.RETR_TREE, cv2.CHAIN_APPROX_TC89_KCOS)
  # Take each frame
  # Gets the shape of video
  screenHeight, screenWidth, _ = frame.shape
  # Gets center of height and width
  centerX = (screenWidth / 2) - .5
  centerY = (screenHeight / 2) - .5
  # Copies frame and stores it in image
  image = frame.copy()
  # Processes the contours, takes in (contours, output_image, (centerOfImage)
  if len(contours) != 0:
    image = findBall(contours, image, centerX, centerY)
  # Shows the contours overlayed on the original video
  return image


# Draws Contours and finds center and yaw of orange ball
# centerX is center x coordinate of image
# centerY is center y coordinate of image
def findBall(contours, image, centerX, centerY):
  screenHeight, screenWidth, channels = image.shape;
  # Seen vision targets (correct angle, adjacent to each other)
  cargo = []

  if len(contours) > 0:
    # Sort contours by area size (biggest to smallest)
    cntsSorted = sorted(contours, key=lambda x: cv2.contourArea(x), reverse=True)

    biggestCargo = []
    for cnt in cntsSorted:
      x, y, w, h = cv2.boundingRect(cnt)
      aspect_ratio = float(w) / h
      # Get moments of contour; mainly for centroid
      M = cv2.moments(cnt)
      # Get convex hull (bounding polygon on contour)
      hull = cv2.convexHull(cnt)
      # Calculate Contour area
      cntArea = cv2.contourArea(cnt)
      # Filters contours based off of size
      if (checkBall(cntArea, aspect_ratio)):
        ### MOSTLY DRAWING CODE, BUT CALCULATES IMPORTANT INFO ###
        # Gets the centeroids of contour
        if M["m00"] != 0:
          cx = int(M["m10"] / M["m00"])
          cy = int(M["m01"] / M["m00"])
        else:
          cx, cy = 0, 0
        if (len(biggestCargo) < 3):

          ##### DRAWS CONTOUR######
          # Gets rotated bounding rectangle of contour
          rect = cv2.minAreaRect(cnt)
          # Creates box around that rectangle
          box = cv2.boxPoints(rect)
          # Not exactly sure
          box = np.int0(box)
          # Draws rotated rectangle
          cv2.drawContours(image, [box], 0, (23, 184, 80), 3)

          # Draws a vertical white line passing through center of contour
          cv2.line(image, (cx, screenHeight), (cx, 0), (255, 255, 255))
          # Draws a white circle at center of contour
          cv2.circle(image, (cx, cy), 6, (255, 255, 255))

          # Draws the contours
          cv2.drawContours(image, [cnt], 0, (23, 184, 80), 1)

          # Gets the (x, y) and radius of the enclosing circle of contour
          (x, y), radius = cv2.minEnclosingCircle(cnt)
          # Rounds center of enclosing circle
          center = (int(x), int(y))
          # Rounds radius of enclosning circle
          radius = int(radius)
          # Makes bounding rectangle of contour
          rx, ry, rw, rh = cv2.boundingRect(cnt)

          # Draws countour of bounding rectangle and enclosing circle in green
          cv2.rectangle(image, (rx, ry), (rx + rw, ry + rh), (23, 184, 80), 1)

          cv2.circle(image, center, radius, (23, 184, 80), 1)

          # Appends important info to array
          if not biggestCargo:
            biggestCargo.append([cx, cy, cnt])
          elif [cx, cy, cnt] not in biggestCargo:
            biggestCargo.append([cx, cy, cnt])

    # Check if there are cargo seen
    if (len(biggestCargo) > 0):
      # pushes that it sees cargo to network tables
      networkTable.putBoolean("cargoDetected", True)

      # Sorts targets based on x coords to break any angle tie
      biggestCargo.sort(key=lambda x: math.fabs(x[0]))
      closestCargo = min(biggestCargo, key=lambda x: (math.fabs(x[0] - centerX)))
      xCoord = closestCargo[0]
      finalTarget = calculateYaw(xCoord, centerX, H_FOCAL_LENGTH)
      print("Yaw: " + str(finalTarget))
      # Puts the yaw on screen
      # Draws yaw of target + line where center of target is
      cv2.putText(image, "Yaw: " + str(finalTarget), (40, 40), cv2.FONT_HERSHEY_COMPLEX, .6,
                  (255, 255, 255))
      cv2.line(image, (xCoord, screenHeight), (xCoord, 0), (255, 0, 0), 2)

      currentAngleError = finalTarget
      # pushes cargo angle to network tables
      networkTable.putNumber("cargoYaw", currentAngleError)

    else:
      # pushes that it doesn't see cargo to network tables
      networkTable.putBoolean("cargoDetected", False)

    cv2.line(image, (round(centerX), screenHeight), (round(centerX), 0), (255, 255, 255), 2)

    return image


def isCorrectShape(rect):
  if rect[1][0] > 2 and rect[1][1] > 2:
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


def haveSameCoordinates(rect1, rect2):
  if round(rect1[0][0], 0) == round(rect2[0][0], 0) and round(rect1[0][1], 0) == round(rect2[0][1], 0):
    return True
  else:
    return False


def drawBox(frame, rect, color=(255, 0, 0)):
  box = cv2.boxPoints(rect)
  box = np.array(box).reshape((-1, 1, 2)).astype(np.int32)
  cv2.drawContours(frame, [box], -1, color, 1)


def anglesInRange(angle1, angle2):
  angles = (abs(angle1), abs(angle2))
  e = 15
  if 151 + e > (max(angles) + 90 - min(angles)) > 151 - e:
    return True
  else:
    return False


def ratiosInRange(ratio1, ratio2):
  sim_ratio = 3
  if (ratio2 < ratio1 + sim_ratio and ratio2 > ratio1 - sim_ratio):
    return True
  else:
    return False


def findTarget(rectborders, frame=-1):
  for r in rectborders:

    if isCorrectShape(r):

      height1 = max(r[1])
      width1 = min(r[1])
      ratio1 = round(height1 / width1, 2)
      tilt1 = translateRotation(round(r[2], 1), r[1][0], r[1][1])

      cx1 = r[0][0]
      cy1 = r[0][1]

      for r2 in rectborders:
        if r == r2 or haveSameCoordinates(r, r2):
          break
        elif isCorrectShape(r2):
          # print("close")
          width2 = r[1][0]
          height2 = r2[1][1]

          tilt2 = translateRotation(round(r2[2], 1), width2, height2)

          cx2 = r2[0][0]
          cy2 = r2[0][1]

          height2 = max(r2[1])
          width2 = min(r2[1])
          ratio2 = round(height2 / width2, 2)
          avg_width = (width1 + width2) / 2
          avg_height = (height1 + height2) / 2

          offset = (cy2 - cy1) ** 2 + (cx2 - cx1) ** 2

          if offset < (7 * avg_width) ** 2 or offset < (2.3 * avg_height) ** 2:

            if ratiosInRange(ratio1, ratio2):

              if anglesInRange(tilt1, tilt2):
                # distance = distanceToCameraFromHeight(offset)
                tapes = [[cx1, height1], [cx2, height2]]
                tapes.sort(key=lambda x: math.fabs(x[0]))
                d1 = distanceToCameraFromHeight(tapes[0][1])
                d2 = distanceToCameraFromHeight(tapes[1][1])
                # print(d1, d2)
                if (d1 > 4) and (d2 > 4):
                  T = 11.313  # actual target width in inches
                  ac = (d1 ** 2 + d2 ** 2 - T ** 2) / (2 * d1 * d2)
                  if (ac > 1):
                    alpha = 0
                  elif (ac < -1):
                    alpha = math.pi
                  else:
                    alpha = math.acos(ac) / 2
                  rat = 2 * d1 * math.sin(alpha) / T
                  if rat > 1:
                    rat = 1
                  elif rat < -1:
                    rat = -1
                  theta = math.acos(rat)

                  degrees = (theta * 180 / math.pi)
                  if d1 > d2:
                    degrees = 90 - degrees
                  else:
                    degrees = 90 + degrees

                  distance = round((d1 + d2) / 2, 2)
                  centerOfTarget = ((cx1 + cx2) / 2, (cy1 + cy2) / 2)
                  centerX = frame.shape[1] / 2 - 0.5
                  yawToTarget = calculateYaw(centerOfTarget[0], centerX, H_FOCAL_LENGTH)
                  global updater
                  updater.addValues(distance, degrees, yawToTarget)
  updater.update()

  return frame


class NetworkTablesUpdater():
  def __init__(self, networkTable):
    self.reset()
    self.table = networkTable


  def reset(self):
    self.detections = 0
    self.distances = []
    self.correctionAngles = []
    self.yaws = []
    self.iter = 0

  def addValues(self, distance, correctionAngle, yaw):
    self.detections += 1
    self.distances.append(distance)
    self.correctionAngles.append(correctionAngle)
    self.yaws.append(yaw)


  def update(self):  # tries to average last 6 values

    self.table.putNumber("VideoTimestamp", timestamp)
    if self.iter >= 8:
      self.iter = 0
      if self.detections >= 5 and np.std(self.distances) < 3 and np.std(self.yaws) < 3:
        ca = np.average(self.correctionAngles)
        dist = np.average(self.distances)
        yaw = np.average(self.yaws)
        self.table.putNumber("tapeYaw", yaw)
        self.table.putNumber("distance", dist)
        self.table.putNumber("correctionAngle", ca)
        self.table.putBoolean("tapeDetected", True)
        if (ca < 90):
          # Rotate robot counter clockwise
          self.rotation = -1
          self.bankingLeft = True
        elif (ca > 90):
          # Rotate robot clockwise
          self.rotation = 1
          self.bankingLeft = False
        self.table.putBoolean("bankingLeft", self.bankingLeft)

        print("dist:", str(dist), "yaw:", str(yaw), "ca:", str(ca))

      else:
        self.table.putNumber("tapeYaw", 0)
        self.table.putNumber("distance", -1)
        self.table.putNumber("correctionAngle", -1)
        self.table.putBoolean("tapeDetected", False)
        print("not found")
      self.reset()
    self.iter+=1

# Checks if tape contours are worthy based off of contour area and (not currently) hull area
def checkContours(cntSize, hullSize):
  return cntSize > (image_width / 6)


# Checks if ball contours are worthy based off of contour area and (not currently) hull area
def checkBall(cntSize, cntAspectRatio):
  return (cntSize > (image_width / 2)) and (round(cntAspectRatio) == 1)


# Forgot how exactly it works, but it works!
def translateRotation(rotation, width, height):
  """
  if (width < height):
    pass
    #rotation = 90 - rotation
  """
  if (rotation > 90):
    rotation = 180 - rotation
  rotation *= -1
  return round(rotation)


def distanceToCameraFromHeight(pixHeight):
  return (5.33 * H_FOCAL_LENGTH) / pixHeight


# Uses trig and focal length of camera to find yaw.
# Link to further explanation: https://docs.google.com/presentation/d/1ediRsI-oR3-kwawFJZ34_ZTlQS2SDBLjZasjzZ-eXbQ/pub?start=false&loop=false&slide=id.g12c083cffa_0_298
def calculateYaw(pixelX, centerX, hFocalLength):
  yaw = math.degrees(math.atan((pixelX - centerX) / hFocalLength))
  return round(yaw)


# Link to further explanation: https://docs.google.com/presentation/d/1ediRsI-oR3-kwawFJZ34_ZTlQS2SDBLjZasjzZ-eXbQ/pub?start=false&loop=false&slide=id.g12c083cffa_0_298
def calculatePitch(pixelY, centerY, vFocalLength):
  pitch = math.degrees(math.atan((pixelY - centerY) / vFocalLength))
  # Just stopped working have to do this:
  pitch *= -1
  return round(pitch)


def getEllipseRotation(image, cnt):
  try:
    # Gets rotated bounding ellipse of contour
    ellipse = cv2.fitEllipse(cnt)
    centerE = ellipse[0]
    # Gets rotation of ellipse; same as rotation of contour
    rotation = ellipse[2]
    # Gets width and height of rotated ellipse
    widthE = ellipse[1][0]
    heightE = ellipse[1][1]
    # Maps rotation to (-90 to 90). Makes it easier to tell direction of slant
    rotation = translateRotation(rotation, widthE, heightE)

    cv2.ellipse(image, ellipse, (23, 184, 80), 3)
    return rotation
  except:
    # Gets rotated bounding rectangle of contour
    rect = cv2.minAreaRect(cnt)
    # Creates box around that rectangle
    box = cv2.boxPoints(rect)
    # Not exactly sure
    box = np.int0(box)
    # Gets center of rotated rectangle
    center = rect[0]
    # Gets rotation of rectangle; same as rotation of contour
    rotation = rect[2]
    # Gets width and height of rotated rectangle
    width = rect[1][0]
    height = rect[1][1]
    # Maps rotation to (-90 to 90). Makes it easier to tell direction of slant
    rotation = translateRotation(rotation, width, height)
    return rotation


#################### FRC VISION PI Image Specific #############
configFile = "/boot/frc.json"


class CameraConfig: pass


team = None
server = False
cameraConfigs = []

"""Report parse error."""


def parseError(str):
  print("config error in '" + configFile + "': " + str, file=sys.stderr)


"""Read single camera configuration."""


def readCameraConfig(config):
  cam = CameraConfig()

  # name
  try:
    cam.name = config["name"]
  except KeyError:
    parseError("could not read camera name")
    return False

  # path
  try:
    cam.path = config["path"]
  except KeyError:
    parseError("camera '{}': could not read path".format(cam.name))
    return False

  cam.config = config

  cameraConfigs.append(cam)
  return True


"""Read configuration file."""


def readConfig():
  global team
  global server

  # parse file
  try:
    with open(configFile, "rt") as f:
      j = json.load(f)
  except OSError as err:
    print("could not open '{}': {}".format(configFile, err), file=sys.stderr)
    return False

  # top level must be an object
  if not isinstance(j, dict):
    parseError("must be JSON object")
    return False

  # team number
  try:
    team = j["team"]
  except KeyError:
    parseError("could not read team number")
    return False

  # ntmode (optional)
  if "ntmode" in j:
    str = j["ntmode"]
    if str.lower() == "client":
      server = False
    elif str.lower() == "server":
      server = True
    else:
      parseError("could not understand ntmode value '{}'".format(str))

  # cameras
  try:
    cameras = j["cameras"]
  except KeyError:
    parseError("could not read cameras")
    return False
  for camera in cameras:
    if not readCameraConfig(camera):
      return False

  return True


"""Start running the camera."""


def startCamera(config):
  print("Starting camera '{}' on {}".format(config.name, config.path))
  cs = CameraServer.getInstance()
  camera = cs.startAutomaticCapture(name=config.name, path=config.path)

  camera.setConfigJson(json.dumps(config.config))

  return cs, camera


if __name__ == "__main__":
  if len(sys.argv) >= 2:
    configFile = sys.argv[1]
  # read configuration
  if not readConfig():
    sys.exit(1)

  # start NetworkTables
  ntinst = NetworkTablesInstance.getDefault()
  # Name of network table - this is how it communicates with robot. IMPORTANT
  networkTable = NetworkTables.getTable('ChickenVision')
  updater = NetworkTablesUpdater(networkTable)

  if server:
    print("Setting up NetworkTables server")
    ntinst.startServer()
  else:
    print("Setting up NetworkTables client for team {}".format(team))
    ntinst.startClientTeam(team)

  # start cameras
  cameras = []
  streams = []
  for cameraConfig in cameraConfigs:
    cs, cameraCapture = startCamera(cameraConfig)
    streams.append(cs)
    cameras.append(cameraCapture)
  # Get the first camera

  webcam = cameras[0]
  cameraServer = streams[0]
  # Start thread reading camera
  cap = WebcamVideoStream(webcam, cameraServer, image_width, image_height).start()

  # (optional) Setup a CvSource. This will send images back to the Dashboard
  # Allocating new images is very expensive, always try to preallocate
  img = np.zeros(shape=(image_height, image_width, 3), dtype=np.uint8)
  # Start thread outputing stream
  streamViewer = VideoShow(image_width, image_height, cameraServer, frame=img).start()
  # cap.autoExpose=True;
  tape = False
  fps = FPS().start()
  # TOTAL_FRAMES = 200;
  # loop forever
  while True:
    # Tell the CvSink to grab a frame from the camera and put it
    # in the source image.  If there is an error notify the output.
    timestamp, img = cap.read()
    # Uncomment if camera is mounted upside down
    # frame = flipImage(img)
    # Comment out if camera is mounted upside down
    frame = img
    if timestamp == 0:
      # Send the output the error.
      streamViewer.notifyError(cap.getError())
      # skip the rest of the current iteration
      continue
    # Checks if you just want camera for driver (No processing), False by default
    if (networkTable.getBoolean("Driver", False)):
      cap.autoExpose = True
      processed = frame
    else:
      # Checks if you just want camera for Tape processing , False by default
      if (networkTable.getBoolean("Tape", True)):
        # NOTANYMORE REKT #Lowers exposure to 0
        cap.autoExpose = True
        boxBlur = blurImg(frame, green_blur)
        threshold = threshold_video(lower_green, upper_green, boxBlur)
        processed = findTargets(frame, threshold)
      else:
        # Checks if you just want camera for Cargo processing, by dent of everything else being false, true by default
        cap.autoExpose = True
        boxBlur = blurImg(frame, orange_blur)
        threshold = threshold_video(lower_orange, upper_orange, boxBlur)
        processed = findCargo(frame, threshold)
    # Puts timestamp of camera on netowrk tables

    streamViewer.frame = processed
    # update the FPS counter
    fps.update()
    # Flushes camera values to reduce latency
    ntinst.flush()
  # Doesn't do anything at the moment. You can easily get this working by indenting these three lines
  # and setting while loop to: while fps._numFrames < TOTAL_FRAMES
  fps.stop()
  print("[INFO] elasped time: {:.2f}".format(fps.elapsed()))
  print("[INFO] approx. FPS: {:.2f}".format(fps.fps()))
