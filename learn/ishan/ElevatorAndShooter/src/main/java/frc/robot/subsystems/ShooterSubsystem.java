/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ToggleButton;

public class ShooterSubsystem extends SubsystemBase {
	CANSparkMax m_shooterLeft;
	CANSparkMax m_shooterRight;
	CANPIDController m_pidController;
	CANEncoder m_encoder;
	double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, targetRPM, rpm;

	NetworkTableEntry kPEntry, kIEntry, kDEntry, kIzEntry, kFFEntry, kMaxOutputEntry, kMinOutputEntry, targetRPMEntry, rpmEntry;
	
	boolean pidToggle = false;
	ToggleButton pidToggleButton = new ToggleButton("pidToggle", pidToggle);

	public ShooterSubsystem(Factory f) {
		m_shooterLeft = f.createSpark(Constants.kLeftShooterSpark);
		m_shooterRight = f.createSpark(Constants.kRightShooterSpark);
		m_shooterLeft.setInverted(true);
		m_shooterRight.follow(m_shooterLeft, true);

		m_pidController = m_shooterLeft.getPIDController();
		m_encoder = m_shooterLeft.getEncoder();

		kMaxOutput = 1;
		kMinOutput = -1;
		targetRPM = 5700;

		kPEntry = Constants.kTab.add("kP", kP).getEntry();
		kIEntry = Constants.kTab.add("kI", kI).getEntry();
		kDEntry = Constants.kTab.add("kD", kD).getEntry();
		kIzEntry = Constants.kTab.add("kIz", kIz).getEntry();
		kFFEntry = Constants.kTab.add("kFF", kFF).getEntry();
		kMaxOutputEntry = Constants.kTab.add("kMaxOutput", kMaxOutput).withProperties(Map.of("min", -1, "max", 1)).getEntry();
		kMinOutputEntry = Constants.kTab.add("kMinOutput", kMinOutput).withProperties(Map.of("min", -1, "max", 1)).getEntry();
		targetRPMEntry = Constants.kTab.add("targetRPM", targetRPM).withProperties(Map.of("min", 0, "max", 5700)).getEntry();
		rpmEntry = Constants.kTab.add("rpm", rpm).withWidget(BuiltInWidgets.kGraph).getEntry();
		periodic();
	}

	@Override
	public void periodic() {
		pidToggle = pidToggleButton.get();

		// read PID coefficients from Shuffleboard
		double p = kPEntry.getDouble(kP);
		double i = kIEntry.getDouble(kI);
		double d = kDEntry.getDouble(kD);
		double iz = kIzEntry.getDouble(kIz);
		double ff = kFFEntry.getDouble(kFF);
		double max = kMaxOutputEntry.getDouble(kMaxOutput);
		double min = kMinOutputEntry.getDouble(kMinOutput);
	
		// if PID coefficients on Shuffleboard have changed, write new values to controller
		if((p != kP)) { m_pidController.setP(p); kP = p; }
		if((i != kI)) { m_pidController.setI(i); kI = i; }
		if((d != kD)) { m_pidController.setD(d); kD = d; }
		if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
		if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
		if((max != kMaxOutput) || (min != kMinOutput)) { 
		  m_pidController.setOutputRange(min, max); 
		  kMinOutput = min; kMaxOutput = max; 
		}

		targetRPM = targetRPMEntry.getDouble(targetRPM);

		rpm = m_encoder.getVelocity();
		rpmEntry.setNumber(rpm);
	}

	public void shooter(double speed) {
		if (!pidToggle) {
			m_shooterLeft.set(speed);
		} else {
			m_pidController.setReference(targetRPM, ControlType.kVelocity);
		}
	}
}
