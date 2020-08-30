package frc.robot.helpers;

import edu.wpi.first.wpilibj.Joystick;

public class TestableJoystick extends Joystick {

    /**
     * Construct an instance of a joystick. The joystick index is the USB port on the drivers
     * station.
     *
     * @param port The port on the Driver Station that the joystick is plugged into.
     */
    public TestableJoystick(int port) {
        super(port);
    }

    public double getAxisX() {
        return super.getX();
    }

    public double getAxisY() {
        return super.getY();
    }

    public double getAxisZ() {
        return super.getZ();
    }
}
