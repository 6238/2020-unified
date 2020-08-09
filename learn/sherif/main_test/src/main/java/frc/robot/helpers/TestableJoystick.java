package frc.robot.helpers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

public class TestableJoystick {
    public void setXChannel(int channel) {
        joystick.setXChannel(channel);
    }

    public void setYChannel(int channel) {
        joystick.setYChannel(channel);
    }

    public void setZChannel(int channel) {
        joystick.setZChannel(channel);
    }

    public void setThrottleChannel(int channel) {
        joystick.setThrottleChannel(channel);
    }

    public void setTwistChannel(int channel) {
        joystick.setTwistChannel(channel);
    }

    public int getXChannel() {
        return joystick.getXChannel();
    }

    public int getYChannel() {
        return joystick.getYChannel();
    }

    public int getZChannel() {
        return joystick.getZChannel();
    }

    public int getTwistChannel() {
        return joystick.getTwistChannel();
    }

    public int getThrottleChannel() {
        return joystick.getThrottleChannel();
    }

    public double getX(GenericHID.Hand hand) {
        return joystick.getX(hand);
    }

    public double getY(GenericHID.Hand hand) {
        return joystick.getY(hand);
    }

    public double getZ() {
        return joystick.getZ();
    }

    public double getTwist() {
        return joystick.getTwist();
    }

    public double getThrottle() {
        return joystick.getThrottle();
    }

    public boolean getTrigger() {
        return joystick.getTrigger();
    }

    public boolean getTriggerPressed() {
        return joystick.getTriggerPressed();
    }

    public boolean getTriggerReleased() {
        return joystick.getTriggerReleased();
    }

    public boolean getTop() {
        return joystick.getTop();
    }

    public boolean getTopPressed() {
        return joystick.getTopPressed();
    }

    public boolean getTopReleased() {
        return joystick.getTopReleased();
    }

    public double getMagnitude() {
        return joystick.getMagnitude();
    }

    public double getDirectionRadians() {
        return joystick.getDirectionRadians();
    }

    public double getDirectionDegrees() {
        return joystick.getDirectionDegrees();
    }

    public double getX() {
        return joystick.getX();
    }

    public double getY() {
        return joystick.getY();
    }

    public boolean getRawButton(int button) {
        return joystick.getRawButton(button);
    }

    public boolean getRawButtonPressed(int button) {
        return joystick.getRawButtonPressed(button);
    }

    public boolean getRawButtonReleased(int button) {
        return joystick.getRawButtonReleased(button);
    }

    public double getRawAxis(int axis) {
        return joystick.getRawAxis(axis);
    }

    public int getPOV(int pov) {
        return joystick.getPOV(pov);
    }

    public int getPOV() {
        return joystick.getPOV();
    }

    public int getAxisCount() {
        return joystick.getAxisCount();
    }

    public int getPOVCount() {
        return joystick.getPOVCount();
    }

    public int getButtonCount() {
        return joystick.getButtonCount();
    }

    public GenericHID.HIDType getType() {
        return joystick.getType();
    }

    public String getName() {
        return joystick.getName();
    }

    public int getAxisType(int axis) {
        return joystick.getAxisType(axis);
    }

    public int getPort() {
        return joystick.getPort();
    }

    public void setOutput(int outputNumber, boolean value) {
        joystick.setOutput(outputNumber, value);
    }

    public void setOutputs(int value) {
        joystick.setOutputs(value);
    }

    public void setRumble(GenericHID.RumbleType type, double value) {
        joystick.setRumble(type, value);
    }

    /**
     * Construct an instance of a joystick. The joystick index is the USB port on the drivers
     * station.
     *
     * @param port The port on the Driver Station that the joystick is plugged into.
     */
    private Joystick joystick;
    public TestableJoystick(int port) {
        this.joystick = new Joystick(port);
    }
}
