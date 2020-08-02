package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import frc.robot.interfaces.CANSparkMaxInterface;
import frc.robot.helpers.MockableSparkMax;

public class Factory {
    public CANSparkMaxInterface getSparkMotor(int busID) {
        return new MockableSparkMax(busID, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public PWMTalonSRX getTalonMotor(int busID) {
        return new PWMTalonSRX(busID);
    }
}
