package frc.robot.interfaces;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANError;
import com.revrobotics.CANPIDController;

import edu.wpi.first.wpilibj.SpeedController;

public interface CANSparkMaxInterface extends SpeedController {
    CANEncoder getEncoder();
	CANPIDController getPIDController();

	CANError follow(CANSparkMaxInterface leader, boolean invert);
}
