package frc.robot.helpers;

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
}
