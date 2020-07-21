#!/usr/bin/env python3

import json
import sys
import time

import numpy

import cv2 as cv
import numpy as np
from networktables import NetworkTablesInstance

import ntcore
from cscore import CameraServer, MjpegServer, UsbCamera, VideoSource

#   JSON format:
#   {
#       "team": <team number>,
#       "ntmode": <"client" or "server", "client" if unspecified>
#       "cameras": [
#           {
#               "name": <camera name>
#               "path": <path, e.g. "/dev/video0">
#               "pixel format": <"MJPEG", "YUYV", etc>   // optional
#               "width": <video mode width>              // optional
#               "height": <video mode height>            // optional
#               "fps": <video mode fps>                  // optional
#               "brightness": <percentage brightness>    // optional
#               "white balance": <"auto", "hold", value> // optional
#               "exposure": <"auto", "hold", value>      // optional
#               "properties": [                          // optional
#                   {
#                       "name": <property name>
#                       "value": <property value>
#                   }
#               ],
#               "stream": {                              // optional
#                   "properties": [
#                       {
#                           "name": <stream property name>
#                           "value": <stream property value>
#                       }
#                   ]
#               }
#           }
#       ]
#       "switched cameras": [
#           {
#               "name": <virtual camera name>
#               "key": <network table key used for selection>
#               // if NT value is a string, it's treated as a name
#               // if NT value is a double, it's treated as an integer index
#           }
#       ]
#   }

configFile = "/boot/frc.json"

class CameraConfig: pass

team = None
server = False
cameraConfigs = []
switchedCameraConfigs = []
cameras = []

cvSink = None
outputStream = None

height = 120
width = 160

img = np.zeros(shape=(height, width, 3), dtype=np.uint8) # first used for bgr, then used for hsv

centerX = int(width/2)
centerY = int(height/2)

targetW = 39.25
targetH = 17.0

def parseError(str):
    """Report parse error."""
    print("config error in '" + configFile + "': " + str, file=sys.stderr)

def readCameraConfig(config):
    """Read single camera configuration."""
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

    # stream properties
    cam.streamConfig = config.get("stream")

    cam.config = config

    cameraConfigs.append(cam)
    return True

def readConfig():
    """Read configuration file."""
    global team
    global server

    # parse file
    try:
        with open(configFile, "rt", encoding="utf-8") as f:
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

def startCamera(config):
    """Start running the camera."""
    global cvSink
    global outputStream

    print("Starting camera '{}' on {}".format(config.name, config.path))
    inst = CameraServer.getInstance()
    camera = UsbCamera(config.name, config.path)
    server = inst.startAutomaticCapture(camera=camera, return_server=True)

    camera.setConfigJson(json.dumps(config.config))
    camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen)

    if config.streamConfig is not None:
        server.setConfigJson(json.dumps(config.streamConfig))

    # Get a CvSink. This will capture images from the camera
    cvSink = inst.getVideo()

    # (optional) Setup a CvSource. This will send images back to the Dashboard
    outputStream = inst.putVideo("image", width, height)
    
    return camera

if __name__ == "__main__":
    if len(sys.argv) >= 2:
        configFile = sys.argv[1]

    # read configuration
    if not readConfig():
        sys.exit(1)

    # start NetworkTables
    ntinst = NetworkTablesInstance.getDefault()
    if server:
        print("Setting up NetworkTables server")
        ntinst.startServer()
    else:
        print("Setting up NetworkTables client for team {}".format(team))
        ntinst.startClientTeam(team)

    table = ntinst.getTable("SmartDashboard")

    # start cameras
    for config in cameraConfigs:
        cameras.append(startCamera(config))
        
    # define range of blue color in HSV
    lowerGreen = np.array([50,100,100])
    upperGreen = np.array([70,255,250])

    # loop forever
    while True:
        # Tell the CvSink to grab a frame from the camera and put it
        # in the source image.  If there is an error notify the output.
        time, img = cvSink.grabFrame(img)
        if time == 0:
            # Send the output the error.
            outputStream.notifyError(cvSink.getError())
            # skip the rest of the current iteration
            continue

        hsv = cv.cvtColor(img, cv.COLOR_BGR2HSV)    # Convert BGR img to HSV format so that you can more easily filter on a color

        # Threshold the HSV image to get only blue colors, based on lower_blue, upper_blue
        mask = cv.inRange(hsv, lowerGreen, upperGreen)
        
        # Bitwise-AND mask and original image and the blue mask to get a final result that "only" has the green colors.
        res = cv.bitwise_and(img, img, mask=mask)

        maskcopy = mask  # make a copy of mask, some documents suggest that the contours function changes the image that is passed.
        image, contours, hierarchy = cv.findContours(maskcopy,cv.RETR_EXTERNAL, cv.CHAIN_APPROX_SIMPLE)     # Find the contours
        # cv.drawContours(res, contours, -1, (255,0,0), 2)
        
        # Draw Cross Hairs At Center of Frame
        res = cv.circle(res, (centerX, centerY), 10, (255,0,0), 1)  #  Draw a circle using center and radius of target
        res = cv.line(res, (centerX-10, centerY),(centerX+10, centerY), (225,0,0), 1)     # Draw a red horizontal line
        res = cv.line(res, (centerX, centerY-10), (centerX, centerY+10), (225,0,0), 1)
        
        if len(contours) > 0:  # Avoid processing null contours
            Obj1 = max(contours, key=cv.contourArea)  # find largest area contour aka green reflective tape
            (x,y,w,h) = cv.boundingRect(Obj1)     # get geometric information

            res = cv.rectangle(res, (x,y), (x+w, y+h), (0, 0, 255), 2)
            
            z = (w**2 + h**2)**0.5

            table.putNumber("x", width/2 - (x + w/2))
            table.putNumber("y", height/2 - (y + h/2))
            table.putNumber("z", z)
            table.putNumber("w", w)
            table.putNumber("h", h)
        

        outputStream.putFrame(res)
