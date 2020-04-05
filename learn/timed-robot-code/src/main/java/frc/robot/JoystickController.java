package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickController {

    public Joystick controller;
    public int port;

    public JoystickController(int port) {
        this.port = port;
        this.controller = new Joystick(port);
        // When the Controller is initialized, it will automatically set the controller
        // object and port value
    }

    // Configuration

    double DEAD_ZONE = 0.08;
    // This is a sensitivity buffer for the joysticks. (Recommended 0.05 or higher)
    // Prevents the robot from going nuts at the slightest movement.

    /*
     * INDEX Joystick Axis - Get the X,Y,Z values for the Joystick Numeral 1-12
     * Buttons - Get the Pressed status for the numbered buttons (1-12) Slider - Get
     * the value for the +/- Slider DPad - Get the Pressed status for the DPad
     * Up/UpLeft/UpRight/Left/Right/DownLeft/DownRight/Down inputs on the cap head
     */

    public double correctDeadSpot(double value) {
        if (Math.abs(value) < DEAD_ZONE) {
            return 0;
        }
        return value;
    }

    public boolean getButton(int buttonNumber) {
        return controller.getRawButton(buttonNumber);
    }

    public double getAxis(int axisNumber) {
        return controller.getRawAxis(axisNumber);
    }

    public int getPOV(int povNumber) {
        return controller.getPOV(povNumber);
    }

    public double getThrottle() {
        return controller.getThrottle();
    }

    // Joystick

    public double getX() {
        // return correctDeadSpot(getAxis(0));
        return correctDeadSpot(-controller.getX());
    }

    public double getY() {
        // return correctDeadSpot(getAxis(1));
        return correctDeadSpot(controller.getY());
    }

    public double getZ() {
        // return correctDeadSpot(getAxis(2));
        return correctDeadSpot(controller.getZ());
    }

    // Numeral Buttons

    public boolean getTrigger() {
        return getButton(1);
    }

    public boolean getThumb() {
        return getButton(2);
    }

    public boolean getThree() {
        return getButton(3);
    }

    public boolean getFour() {
        return getButton(4);
    }

    public boolean getFive() {
        return getButton(5);
    }

    public boolean getSix() {
        return getButton(6);
    }

    public boolean getSeven() {
        return getButton(7);
    }

    public boolean getEight() {
        return getButton(8);
    }

    public boolean getNine() {
        return getButton(9);
    }

    public boolean getTen() {
        return getButton(10);
    }

    public boolean getEleven() {
        return getButton(11);
    }

    public boolean getTwelve() {
        return getButton(12);
    }

    // Slider

    public double getSlider() {
        return -1 * getThrottle();
    }

    // DPad
    // The DPad is unique in that it works with a 0-360 degrees POV

    public boolean getDPadUp() {
        int degree = getPOV(0);
        return (degree >= 337 || degree <= 22);
    }

    public boolean getDPadDown() {
        int degree = getPOV(0);
        return (degree <= 202 && degree >= 157);
    }

    public boolean getDPadLeft() {
        int degree = getPOV(0);
        return (degree <= 292 && degree >= 247);
    }

    public boolean getDPadRight() {
        int degree = getPOV(0);
        return (degree <= 112 && degree >= 67);
    }

    public boolean getDPadUpRight() {
        int degree = getPOV(0);
        return (degree <= 67 && degree >= 22);
    }

    public boolean getDPadUpLeft() {
        int degree = getPOV(0);
        return (degree <= 337 && degree >= 292);
    }

    public boolean getDPadDownRight() {
        int degree = getPOV(0);
        return (degree <= 157 && degree >= 112);
    }

    public boolean getDPadDownLeft() {
        int degree = getPOV(0);
        return (degree <= 247 && degree >= 207);
    }
}