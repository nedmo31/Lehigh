# ENGR 10
# Nathan Edmondson and Ethan Lavi
# nje225@lehigh.edu
# esl225@lehigh.edu
# Final Project
# Server.py

# Contributions of team members
"""
Ethan:
Created the improved webpage
Wired the raspberry pi for the distance sensor and the LEDs
Wrote code for features 8, 9, 10, 11 in Server.py

Nathan:
Implemented features 1, 2, 3, 4, 5, 6, 7 in Server.py
Wrote functions for Ethan to use
Helped test and debug the robot
"""

from http.server import BaseHTTPRequestHandler, HTTPServer
import time
import os
from gpiozero import CamJamKitRobot
import time
from gpiozero import DistanceSensor
from gpiozero import LED
import _thread

# Web page variables
hostName = "esl225.local"
serverPort = 8080
encoding = "utf-8"

# Globals
global keepRunning
keepRunning = False

# Distance Sensor variables
pintrigger = 17
pinecho = 18
sensor = DistanceSensor(echo=pinecho, trigger=pintrigger)
robot_going_forward = False

# Create robot object
robot = CamJamKitRobot()

# Control speed
left_motor = .5
right_motor = .5
mforward = (left_motor, right_motor)
mbackward = (-left_motor, -right_motor)
mright = (left_motor, -right_motor)
mleft = (-left_motor, right_motor)

# Speed setting functions
def setSpeed(ns):
    new_speed = round(ns, 1)
    if new_speed <= 1 and new_speed >= 0:
        f = open("speed.txt", "w")
        f.write(str(new_speed))
        f.close()

def getSpeed():
    f = open("speed.txt", "r")
    s = f.read()
    f.close()
    left_motor = float(s)
    right_motor = float(s)
    mforward = (left_motor, right_motor)
    mbackward = (-left_motor, -right_motor)
    mright = (left_motor, -right_motor)
    mleft = (-left_motor, right_motor)
    return float(s)
getSpeed()


# Turn Signal Variables
left_blinker = LED(16)
right_blinker = LED(26)
left_blinker_on = False
right_blinker_on = False

# Movement functions
def forward():
    s = getSpeed()
    mforward = (s, s)
    robot.value = mforward


def backward():
    s = getSpeed()
    mbackward = (-s, -s)
    robot.value = mbackward


def stop():
    robot.stop()


def right():
    s = getSpeed()
    mright = (s, -s)
    right_blinker.on()
    robot.value = mright
    time.sleep(0.5)
    right_blinker.off()
    robot.stop()
    time.sleep(0.5)
    robot.stop()
    right_blinker.on()
    time.sleep(0.5)
    right_blinker.off()



def left():
    s = getSpeed()
    mleft = (-s, s)
    left_blinker.on()
    robot.value = mleft
    time.sleep(0.5)
    left_blinker.off()
    robot.stop()
    time.sleep(0.5)
    left_blinker.on()
    time.sleep(0.5)
    left_blinker.off()


def full_turn():
    s = getSpeed()
    mright = (s, -s)
    right_blinker.on()
    robot.value = mright
    time.sleep(0.5)
    right_blinker.off()
    time.sleep(0.5)
    right_blinker.on()
    robot.stop()
    time.sleep(0.5)
    right_blinker.off()


def left_signal():
    left_blinker_on = True
    for i in range(0, 5):
        left_blinker.on()
        time.sleep(.5)
        left_blinker.off()
        time.sleep(.5)
        left_blinker_on = False


def right_signal():
    right_blinker_on = True
    for i in range(0, 5):
        right_blinker.on()
        time.sleep(.5)
        right_blinker.off()
        time.sleep(.5)
        right_blinker_on = False


# Server stuff
class MyServer(BaseHTTPRequestHandler):
    def send_distance(self):
        global keepRunning
        keepRunning = True
        while keepRunning:
            time.sleep(0.1)
            distance = sensor.distance * 100
            display = "0"*(3 - len(str(round(distance)))) + str(round(distance))
            self.wfile.write(bytes("Distance" + display, encoding))
            if (distance < 20) : # If it is going to collide with something
                stop()

    def do_GET(self):
        if self.path.endswith("forward"):
          print("Go Forward")
          forward()
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("Forward", encoding))
        elif self.path.endswith("backward"):
          print("Go Backward")
          backward()
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("Backward", encoding))
        elif self.path.endswith("stop"):
          print("Stopping")
          stop()
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("Stopping", encoding))
        elif self.path.endswith("left"):
          print("Turning Left")
          left()
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("Left", encoding))
        elif self.path.endswith("right"):
          print("Turning Right")
          right()
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("Right", encoding))
        elif self.path.endswith("rightsignal"):
          print("Right Turn Signal")
          right_signal()
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("RightSignal", encoding))
        elif self.path.endswith("leftsignal"):
          print("Left Turn Signal")
          left_signal()
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("LeftSignal", encoding))
        elif self.path.endswith("up"):
          s = getSpeed()
          setSpeed(s + 0.1)
          s = getSpeed()
          print("Up Speed")
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("Up Speed" + str(round(s, 1)), encoding))
        elif self.path.endswith("down"):
          s = getSpeed()
          setSpeed(s - 0.1)
          s = getSpeed()
          print("Down Speed")
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("Down Speed" + str(round(s, 1)), encoding))
        elif self.path.endswith("U-turn"):
          print("180 degree turn")
          full_turn()
          self.send_response(200)
          self.send_header("Content-type", "text/text")
          self.end_headers()
          self.wfile.write(bytes("Making large turn", encoding))
        elif self.path.endswith("sdistance"):
          _thread.start_new_thread(self.send_distance, ())
        elif self.path.endswith("edistance"):
          keepRunning = False
        elif self.path.endswith("d"):
          distance = sensor.distance * 100
          display = "0"*(3 - len(str(round(distance)))) + str(round(distance))
          self.wfile.write(bytes("Distance" + display, encoding))
        else:
          # Send a static file to the client
          self.send_response(200)
          root = os.path.join(os.path.dirname(os.path.abspath(__file__)))
          if self.path == '/':
            filename = root + '/index.html'
          else:
            filename = root + self.path
          if filename[-4:] == '.css':
            self.send_header('Content-type', 'text/css')
          elif filename[-5:] == '.json':
            self.send_header('Content-type', 'application/javascript')
          elif filename[-3:] == '.js':
            self.send_header('Content-type', 'application/javascript')
          elif filename[-4:] == '.ico':
            self.send_header('Content-type', 'image/x-icon')
          else:
            self.send_header('Content-type', 'text/html')          
          self.end_headers()
          with open(filename, 'rb') as fh:
            html = fh.read()
            self.wfile.write(html)
          
if __name__ == "__main__":        
    webServer = HTTPServer((hostName, serverPort), MyServer)
    print("Server started http://%s:%s" % (hostName, serverPort))

    try:
        webServer.serve_forever()            
    except KeyboardInterrupt:
        pass

    webServer.server_close()
    print("Server stopped.")

