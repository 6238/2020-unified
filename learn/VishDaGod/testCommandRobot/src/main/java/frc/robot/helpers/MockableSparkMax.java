package frc.robot.helpers;

import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import frc.robot.interfaces.CANSparkMaxInterface;

public class MockableSparkMax extends CANSparkMax implements CANSparkMaxInterface {
    /**
     * Create a new SPARK MAX Controller
     *
     * @param deviceID The device ID.
     * @param type     The motor type connected to the controller. Brushless motors
     *                 must be connected to their matching color and the hall sensor
     *                 plugged in. Brushed motors must be connected to the Red and
     */
    public MockableSparkMax(int deviceID, MotorType type) {
        super(deviceID, type);
    }

    /**
     * Causes this controller's output to mirror the provided leader.
     *
     * Only voltage output is mirrored. Settings changed on the leader do not affect
     * the follower.
     * 
     * Following anything other than a CAN SPARK MAX is not officially supported.
     *
     * @param leader The motor controller to follow.
     * @param invert Set the follower to output opposite of the leader
     *
     * @return CANError Set to CANError.kOK if successful
     */
    public CANError follow(final CANSparkMaxInterface leader, boolean invert) {
        return super.follow((CANSparkMax) leader, invert);
    }
}
