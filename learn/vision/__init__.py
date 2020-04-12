'''
Created on Aug 10, 2017

@author: Prat Bruns
'''

################################## REVISIONS BOX ##################################
'''
  | Person       | Date       | Reason                       | Successful?    | Notes   
  | PRAT         | 10/7       | Expand the correct contours  |  YYEEEESSSSS   | Ran into a few issues - Had to factor width into filtering
  |              |            | parameters to sort through   |                | Contour does not have to be entirely in screen for it to 
  |              |            | the top 3 (by area) contours |                | select, but it doesn't have to be the biggest thing in screen
  |              |            | and compare (allows for a    |                |
  |              |            | larger target to be in view  |                |
  |              |            | but not select it)           |                |
  |              |            |                              |                |
  |              |            |                              |                |
  |              |            |                              |                |
  |              |            |                              |                |
  |              |            |                              |                |
  |              |            |                              |                |
'''
###################################################################################
# This example will open a multiple windows and display sequential frames that represent the various manipulations to the image
# it also can identify the blue blocks on a sheet of paper and draw boundaries around them. 

import cv2
import socketserver
import numpy as np
import time
from tracemalloc import Frame

cap = cv2.VideoCapture(0)   #Establishes "cap" as the source
#cap2 = cv2.VideoCapture(3)
    
##################     Get a frame1 from the camera and Collect information on frame1 size and center   #######################
_,frame1 = cap.read()
frameHeight = frame1.shape[0] 
frameWidth = frame1.shape[1]
frameCenter_X = int(frameWidth/2)
frameCenter_Y = int(frameHeight/2)

t0 = time.time()    #define variables 
t1 = time.time()

fpsCount = 1
fpsSum = 0
framesPerSec = str(0)

Pick1 = False
Pick2 = False

'''class MyTCPHandler(socketserver.BaseRequestHandler):
    """
    The request handler class for our server.

    It is instantiated once per connection to the server, and must
    override the handle() method to implement communication to the
    client.
    """

    def handle(self):
        # self.request is the TCP socket connected to the client
        self.data = self.request.recv(1024).strip()
        print ("{} wrote:".format(self.client_address[0]))
        print (self.data)
        # just send back the same data, but upper-cased
        self.request.sendall(self.data.upper())
        
if __name__ == "__main__":
    HOST, PORT = "localhost", 6060

    # Create the server, binding to localhost on port 9999
    server = socketserver.TCPServer((HOST, PORT), MyTCPHandler)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    server.serve_forever()'''
    
while(1):       # This is a simple continuous loop that recursively grabs frames from the system default camera.
    #########################################################
    t0 = time.time()    # Get starting time of loop
    #########################################################
    
    
    _, frame1 = cap.read()   # Read a BGR frame1 from the camera and store in "frame1"
    #_, frame2 = cap2.read()
    hsv = cv2.cvtColor(frame1, cv2.COLOR_BGR2HSV)    # Convert BGR frame1 to HSV format so that you can more easily filter on a color

    # define range of blue color in HSV
    lowerGreen = np.array([50,100,100])       #lower_blue = np.array([110,50,50])      experiment with different values
    upperGreen = np.array([70,255,250])    #upper_blue = np.array([130,255,255])    experiment with different values
    
    # Threshold the HSV image to get only blue colors, based on lower_blue, upper_blue
    mask = cv2.inRange(hsv, lowerGreen, upperGreen)
    
    # Bitwise-AND mask and original image and the blue mask to get a final result that "only" has the blue colors.
    res = cv2.bitwise_and(frame1,frame1, mask= mask)
    
    
    maskcopy = mask  #make a copy of mask, some documents suggest that the contours function changes the image that is passed.
    image, contours, hierarchy = cv2.findContours(maskcopy,cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)     # Find the contours
    cv2.drawContours(frame1, contours, -1, (255,0,0), 2)
    #cv2.drawContours(frame2, contours, -1, (255,0,0), 2)
    
    #Draw Cross Hairs At Center of Frame
    frame1 = cv2.circle(frame1, (frameCenter_X, frameCenter_Y), 10, (255,0,0), 1)  #  Draw a circle using center and radius of target
    frame1 = cv2.line(frame1, (frameCenter_X-10, frameCenter_Y),(frameCenter_X+10, frameCenter_Y), (225,0,0), 1)     # Draw a red horizontal line
    frame1 = cv2.line(frame1, (frameCenter_X, frameCenter_Y-10), (frameCenter_X, frameCenter_Y+10), (225,0,0), 1) 
    
    if len(contours) > 1:  # Avoid processing null contours 
        Obj1 = max(contours, key=cv2.contourArea)  #find largest area contour aka green reflective tape
        (xf,yf,wf,hf) = cv2.boundingRect(Obj1)     # get geometric information
       
        #######################################################Find The Second Largest Contour
        goal_ycrcb_mint = np.array([0, 90, 100],np.uint8)
        goal_ycrcb_maxt = np.array([25, 255, 255],np.uint8)
        goal_ycrcb = cv2.inRange(frame1, goal_ycrcb_mint, goal_ycrcb_maxt)
        areaArray = []
        count = 1

        for i, c in enumerate(contours):
            area = cv2.contourArea(c)
            areaArray.append(area)

        #first sort the array by area
        sorteddata = sorted(zip(areaArray, contours), key=lambda x: x[0], reverse=True)

        #find the nth largest contour [n-1][1], in this case 2
        secondlargestcontour = sorteddata[1][1]

        #draw it
        xs, ys, ws, hs = cv2.boundingRect(secondlargestcontour)
        
        #####################################################################END Find Second Largest Contour
        
        goal_HeightRatio1to2 = float(hf)/hs
        goal_WidthRatio1to2 = float(wf)/ws
        
        #set min and max ratios for height and width
        ratioMaxH = 2.00
        ratioMinH = 1.55
        ratioAvgH = 1.775
        ratioMaxW = 2.00
        ratioMinW = 0.95
        ratioavgW = 1.475
        if len(contours) > 2:
        
            ##################################################################### Find Third Largest Contour 
            thirdlargestcontour = sorteddata[2][1]  #Find the Third Largest
        
            xt, yt, wt, ht = cv2.boundingRect(thirdlargestcontour) #Draw the Bounding Rectangle
            ##################################################################### END Find Third Largest Contour
            
            #######calculate  aspect ratio of contour
            #aspect_ratio1 = float(wg)/hg
            goal_HeightRatio1to3 = float(hf)/ht
            goal_HeightRatio2to3 = float(hs)/ht
            goal_WidthRatio1to3 = float(wf)/wt
            goal_WidthRatio2to3 = float(ws)/wt
            
            if (ratioMinH <= goal_HeightRatio1to3 <= ratioMaxH and ratioMinW <= goal_WidthRatio1to3 <= ratioMaxW):
                #################  Calculate center of contour and draw cross hairs on center of target
                centerOfTarget_X = int(xf+wf/2)
                centerOfTarget_Y = int(yf+hf/2)
                frame1 = cv2.circle(frame1,(centerOfTarget_X,centerOfTarget_Y),10, (0,0,255), 1)  #  Draw a circle using center and radius of target
                frame1 = cv2.line(frame1,(centerOfTarget_X-10,centerOfTarget_Y),(centerOfTarget_X+10,centerOfTarget_Y),(0,0,255),1)     # Draw a red horizontal line
                frame1 = cv2.line(frame1,(centerOfTarget_X,centerOfTarget_Y-10),(centerOfTarget_X,centerOfTarget_Y+10),(0,0,255),1)     # Draw a red horizontal line
    
                #################  Calculate offset from center of frame1 to center of target  ###################
                targetOffset_X = centerOfTarget_X - frameCenter_X
                targetOffset_Y = frameCenter_Y - centerOfTarget_Y
    
                #Draw Line of Where the Robot must move
                frame1 = cv2.line(frame1,(centerOfTarget_X,centerOfTarget_Y),(frameCenter_X,frameCenter_Y),(225,225,225),2)
            
                ##################################    Write Distance to Target Point Coordinates to the Screen
                centerPoint = "(" + str(targetOffset_X) + "," + str(targetOffset_Y) + ")"
                cv2.putText(frame1, centerPoint, (475,400), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2, cv2.LINE_AA)
                ################################## End Write Distance to Target Point Coordinate
                
                #Print Aspect Ratio On Screen
                goal_RatioPrint = str(round(goal_HeightRatio1to3,2))
                cv2.putText(frame1, goal_RatioPrint, (475,350), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2, cv2.LINE_AA)
                #End Print Aspect Ratio On Screen
                
                Pick1 = True
                        
            if (ratioMinH <= goal_HeightRatio2to3 <= ratioMaxH and ratioMinW <= goal_WidthRatio2to3 <= ratioMaxW):
                #################  Calculate center of contour and draw cross hairs on center of target
                centerOfTarget_X = int(xs+ws/2)
                centerOfTarget_Y = int(ys+hs/2)
                frame1 = cv2.circle(frame1,(centerOfTarget_X,centerOfTarget_Y),10, (0,0,255), 1)  #  Draw a circle using center and radius of target
                frame1 = cv2.line(frame1,(centerOfTarget_X-10,centerOfTarget_Y),(centerOfTarget_X+10,centerOfTarget_Y),(0,0,255),1)     # Draw a red horizontal line
                frame1 = cv2.line(frame1,(centerOfTarget_X,centerOfTarget_Y-10),(centerOfTarget_X,centerOfTarget_Y+10),(0,0,255),1)     # Draw a red horizontal line
    
                #################  Calculate offset from center of frame1 to center of target  ###################
                targetOffset_X = centerOfTarget_X - frameCenter_X
                targetOffset_Y = frameCenter_Y - centerOfTarget_Y
    
                #Draw Line of Where the Robot must move
                frame1 = cv2.line(frame1,(centerOfTarget_X,centerOfTarget_Y),(frameCenter_X,frameCenter_Y),(225,225,225),2)
            
                ##################################    Write Distance to Target Point Coordinates to the Screen
                centerPoint = "(" + str(targetOffset_X) + "," + str(targetOffset_Y) + ")"
                cv2.putText(frame1, centerPoint, (475,400), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2, cv2.LINE_AA)
                ################################## End Write Distance to Target Point Coordinate
                
                #Print Aspect Ratio On Screen
                goal_RatioPrint = str(round(goal_HeightRatio2to3,2))
                cv2.putText(frame1, goal_RatioPrint, (475,350), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2, cv2.LINE_AA)
                #End Print Aspect Ratio On Screen
                     
                Pick2 = True
                     
        #only run if contour is within ratioValues 
        if (ratioMinH <= goal_HeightRatio1to2 <= ratioMaxH and ratioMinW <= goal_WidthRatio1to2 <= ratioMaxW):
                     
            #################  Calculate center of contour and draw cross hairs on center of target
            centerOfTarget_X = int(xf+wf/2)
            centerOfTarget_Y = int(yf+hf/2)
            frame1 = cv2.circle(frame1,(centerOfTarget_X,centerOfTarget_Y),10, (0,0,255), 1)  #  Draw a circle using center and radius of target
            frame1 = cv2.line(frame1,(centerOfTarget_X-10,centerOfTarget_Y),(centerOfTarget_X+10,centerOfTarget_Y),(0,0,255),1)     # Draw a red horizontal line
            frame1 = cv2.line(frame1,(centerOfTarget_X,centerOfTarget_Y-10),(centerOfTarget_X,centerOfTarget_Y+10),(0,0,255),1)     # Draw a red horizontal line
    
            #################  Calculate offset from center of frame1 to center of target  ###################
            targetOffset_X = centerOfTarget_X - frameCenter_X
            targetOffset_Y = frameCenter_Y - centerOfTarget_Y
    
            #Draw Line of Where the Robot must move
            frame1 = cv2.line(frame1,(centerOfTarget_X,centerOfTarget_Y),(frameCenter_X,frameCenter_Y),(225,225,225),2)
            
            ##################################    Write Distance to Target Point Coordinates to the Screen
            centerPoint = "(" + str(targetOffset_X) + "," + str(targetOffset_Y) + ")"
            cv2.putText(frame1, centerPoint, (475,400), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2, cv2.LINE_AA)
            ################################## End Write Distance to Target Point Coordinate
            
            #Print Aspect Ratio On Screen
            goal_RatioPrint = str(round(goal_HeightRatio1to2,2))
            cv2.putText(frame1, goal_RatioPrint, (475,350), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2, cv2.LINE_AA)
            #End Print Aspect Ratio On Screen
            
            Pick1 = True
            
    ###########################################
    t1 = time.time()    # Get ending time for FPS  loop 
    ###########################################
    
    ##################################     manage FPS averaging 
    fpsSum = fpsSum + (t1-t0)
    if (fpsCount == 10 ):
        framesPerSec = "FPS:"+str(int(1/(fpsSum/10)))   #Calculate frames per sec., and convert to a string
        fpsCount = 0 # reset counter
        fpsSum = 0  #reset sum
    cv2.putText(frame1,framesPerSec,(500,450), cv2.FONT_HERSHEY_SIMPLEX, 1, (0,255,0), 2, cv2.LINE_AA)  # write FPS to the processed frame1
    fpsCount = fpsCount+1 #increment  counter
    ##################################      end manage FPS averaging 
        
    ################################## Calculate ThetaX and ThetaY
    #ThetaX = 
    ################################## End Calculate ThetaX and ThetaY

    cv2.imshow('Camera-frame1 with contour',frame1)
    #cv2.imshow('Camera Raw', frame2)
         
    # exit while loop using escape key
    k = cv2.waitKey(1) & 0xFF
    if k == 27:
        break

cv2.destroyAllWindows()     # Best practice is to clean up all windows before exiting.

cv2.imshow('Final Frame with contours',frame1)
#cv2.imshow('Camera Raw', frame2)
#cv2.imshow('resize of Frame with contours',res)
k = cv2.waitKey(0) & 0xFF
if k == 27:
    cv2.destroyAllWindows()     # clean up after ESC key. Best practice is to clean up all windows before exiting.
    



        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        