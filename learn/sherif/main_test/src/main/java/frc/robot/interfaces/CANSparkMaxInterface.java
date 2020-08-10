package frc.robot.interfaces;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SpeedController;

public interface CANSparkMaxInterface extends SpeedController {
    CANEncoder getEncoder();
    CANError follow(CANSparkMax leader, boolean invert);
}
