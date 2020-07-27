package frc.robot;

import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.SpeedController;

public interface CANSparkFunctions extends SpeedController {
    CANEncoder GetEncoder();
}
