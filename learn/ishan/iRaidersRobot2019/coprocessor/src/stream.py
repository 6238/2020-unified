import cv2
import numpy as np
from threading import Thread


class WebcamVideoStream:

  def __init__(self):
    # initialize the video camera stream and read the first frame from the stream
    self.stream = cv2.VideoCapture(0)  # 0 = built in camera, 1 = extra camera
    (self.grabbed, self.frame) = self.stream.read()
    self.gamma = 0.1
    self.filtered = self.adjust_gamma(self.frame, self.gamma)
    # initialize the variable used to indicate if the thread should be stopped
    self.stopped = False

  def start(self):
    # start the thread to read frames from the video stream
    Thread(target=self.update, args=()).start()
    return self

  def update(self):
    # keep looping infinitely until the thread is stopped
    while True:
      # if the thread indicator variable is set, stop the thread
      if self.stopped:
        return
      # otherwise, read the next frame from the stream
      (self.grabbed, self.frame) = self.stream.read()
      self.filtered = self.adjust_gamma(self.frame, self.gamma)

  def adjust_gamma(self, image, gamma=1.0):
    # build a lookup table mapping the pixel values [0, 255] to
    # their adjusted gamma values
    invGamma = 1.0 / gamma
    table = np.array([((i / 255.0) ** invGamma) * 255
                      for i in np.arange(0, 256)]).astype("uint8")

    # apply gamma correction using the lookup table
    return cv2.LUT(image, table)

  def readFiltered(self):
    # return gamma filtered frame
    return self.filtered

  def read(self):
    # return the frame most recently read
    return self.frame

  def stop(self):
    # indicate that the thread should be stopped
    self.stopped = True
