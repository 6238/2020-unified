package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

public class MockableSparkMax extends CANSparkMax implements CANSparkFunctions {
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

    @Override
    public CANEncoder GetEncoder() {
        return this.getEncoder();
    }
}
