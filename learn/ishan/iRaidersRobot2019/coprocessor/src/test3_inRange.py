import cv2
from coprocessor.src.stream import WebcamVideoStream
import numpy as np
def nothing(x):
    pass
vs = WebcamVideoStream().start()
cv2.namedWindow('image')
h = 78
s = 106
v = 218

hh = 99
sh = 255
vh = 255
cv2.createTrackbar('H','image',0,180,nothing)
cv2.createTrackbar('S','image',0,255,nothing)
cv2.createTrackbar('V','image',0,255,nothing)

cv2.createTrackbar('HH','image',0,180,nothing)
cv2.createTrackbar('SH','image',0,255,nothing)
cv2.createTrackbar('VH','image',0,255,nothing)


while(1):

    # Take each frame
    frame_filtered = vs.readFiltered()
    #frame_filtered= cv2.imread('ballOrange.jpg')
    #frame_filtered = adjust_gamma(frame_filtered, 1.0)
    # Convert BGR to HSV

    hsv = cv2.cvtColor(frame_filtered, cv2.COLOR_BGR2HSV)
    print(hsv)
    h = cv2.getTrackbarPos('H', 'image')
    s = cv2.getTrackbarPos('S', 'image')
    v = cv2.getTrackbarPos('V', 'image')

    hh = cv2.getTrackbarPos('HH', 'image')
    sh = cv2.getTrackbarPos('SH', 'image')
    vh = cv2.getTrackbarPos('VH', 'image')
    """print("h = " + str(h))
    print("s = " + str(s))
    print("v = " + str(v))
    print("")
    print("hh = " + str(hh))
    print("sh = " + str(sh))
    print("vh = " + str(vh))"""
    print("lower_c = np.array([",h,",",s,",",v,"])")
    print("upper_c = np.array([", hh, ",", sh, ",", vh, "])")
    # define range of blue color in HSV
    lower_c = (h, s, v)
    upper_c = (hh, sh, vh)

    # Threshold the HSV image to get only blue colors
    mask = cv2.inRange(hsv, lower_c, upper_c)

    # Bitwise-AND mask and original image
    res = cv2.bitwise_and(frame_filtered, frame_filtered, mask= mask)

    # Convert the img to grayscale
    gray = cv2.cvtColor(res, cv2.COLOR_BGR2GRAY)

    # Apply edge detection method on the image
    edges = cv2.Canny(gray, 50, 150, apertureSize=3)

    cv2.imshow('edges', edges)
    cv2.imshow('image', frame_filtered)
    cv2.imshow('res',res)
    k = cv2.waitKey(1) & 0xFF
    if k == ord('q'):
        break
cv2.destroyAllWindows()
