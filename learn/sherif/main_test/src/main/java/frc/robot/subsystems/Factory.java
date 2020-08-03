package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import frc.robot.interfaces.CANSparkMaxInterface;
import frc.robot.helpers.MockableSparkMax;
import com.ctre.phoenix.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

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
    public WPI_TalonSRX getTalonMotor(int busID) {
        return new WPI_TalonSRX(busID);
    }
}
