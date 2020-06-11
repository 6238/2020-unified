# this is not successful in ishan's Ubuntu 20.0.4 VM (with opencv and cscore installed) - it complains about an error getting frames

# Import the camera server
from cscore import CameraServer

# Import OpenCV and NumPy
import cv2
import numpy as np

def main():
    cs = CameraServer.getInstance()
    print("1")
    cs.enableLogging()
    print("2")

    # Capture from the first USB Camera on the system
    camera = cs.startAutomaticCapture()
    print("3")
    camera.setResolution(320, 240)
    print("4")

    # Get a CvSink. This will capture images from the camera
    cvSink = cs.getVideo()
    cvSink.setVideoMode(cscore.VideoMode.PixelFormat.kYUYV, 320, 240, 30)
    print("5")

    # (optional) Setup a CvSource. This will send images back to the Dashboard
    outputStream = cs.putVideo("Name", 320, 240)
    print("6")

    # Allocating new images is very expensive, always try to preallocate
    img = np.zeros(shape=(240, 320, 3), dtype=np.uint8)
    print("7")

    while True:
        # Tell the CvSink to grab a frame from the camera and put it
        # in the source image.  If there is an error notify the output.
        time, img = cvSink.grabFrame(img)
        print("8")
        if time == 0:
            print("9")
            # Send the output the error.
            outputStream.notifyError(cvSink.getError())
            print("10")
            # skip the rest of the current iteration
            continue

        #
        # Insert your image processing logic here!
        #

        # (optional) send some image back to the dashboard
        print("11")
        outputStream.putFrame(img)
        print("12")
