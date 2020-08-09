package frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
import frc.robot.interfaces.CANSparkMaxInterface;
import frc.robot.helpers.MockableSparkMax;

public class Factory {
    public CANSparkMaxInterface getSparkMotor(int busID) {
        return new MockableSparkMax(busID, CANSparkMaxLowLevel.MotorType.kBrushless);
    }
}
