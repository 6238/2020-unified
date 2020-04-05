# Import the camera server
from cscore import CameraServer

# Import OpenCV and NumPy
import cv2 as cv
import numpy as np

def main():
    cs = CameraServer.getInstance()
    cs.enableLogging()

    # Capture from the first USB Camera on the system
    camera = cs.startAutomaticCapture()
    camera.setResolution(320, 240)

    # Get a CvSink. This will capture images from the camera
    cvSink = cs.getVideo()

    # (optional) Setup a CvSource. This will send images back to the Dashboard
    outputStream = cs.putVideo("Name", 320, 240)

    # Allocating new images is very expensive, always try to preallocate
    img = np.zeros(shape=(240, 320, 3), dtype=np.uint8)

    while True:
        # Tell the CvSink to grab a frame from the camera and put it
        # in the source image.  If there is an error notify the output.
        time, img = cvSink.grabFrame(img)
        if time == 0:
            # Send the output the error.
            outputStream.notifyError(cvSink.getError())
            # skip the rest of the current iteration
            continue

        #
        # Insert your image processing logic here!
        #
        
        blur = cv.blur(img, (10,10))
        output = cv.cvtColor(blur, cv.COLOR_BGR2HSV)

        lower_red = np.array([10, 150, 50])
        upper_red = np.array([170, 200, 150])

        mask = cv.inRange(output, lower_red, upper_red)
        res = cv.bitwise_and(output, output, mask=mask)

        # (optional) send some image back to the dashboard
        outputStream.putFrame(res)