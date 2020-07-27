package frc.robot;

import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.SpeedController;

public interface CANSparkMaxInterface extends SpeedController {
    CANEncoder getEncoder();
}
