package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import frc.robot.interfaces.CANSparkMaxInterface;
import frc.robot.helpers.MockableSparkMax;

public class Factory {
    /**
     * Creates a MockableSparkMax from a CANSparkMax
     * @param busID the ID of the motor controller
     * @return the MockableSparkMax in interface form
     */
    public CANSparkMaxInterface getSparkMotor(int busID) {
        return new MockableSparkMax(busID, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    /**
     * Creates a Talon Motor
     * @param busID the ID of the motor controller
     * @return the TalonSRX
     */
    public PWMTalonSRX getTalonMotor(int busID) {
        return new PWMTalonSRX(busID);
    }
}
