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

import frc.robot.Constants.OIConstants;
import frc.robot.Constants.Shooter;
import frc.robot.ToggleButton;

public class ShooterSubsystem extends SubsystemBase {
	CANSparkMax m_shooterLeft;
	CANSparkMax m_shooterRight;
	CANPIDController m_pidController;
	CANEncoder m_encoder;
	double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, targetRPM, rpm;

	NetworkTableEntry kPEntry, kIEntry, kDEntry, kIzEntry, kFFEntry, kMaxOutputEntry, kMinOutputEntry, targetRPMEntry,
			rpmEntry;

	private double speed = Shooter.kStartingSpeed;
	private final NetworkTableEntry kSpeedEntry = OIConstants.kTab.add("shooterSpeed", speed)
			.withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -1, "max", 1)).getEntry();

	boolean pidToggle = false;
	ToggleButton pidToggleButton = new ToggleButton("pidToggle", pidToggle);

	public ShooterSubsystem(Factory f) {
		m_shooterLeft = f.createSpark(Shooter.kLeftShooterSpark);
		m_shooterRight = f.createSpark(Shooter.kRightShooterSpark);
		m_shooterLeft.setInverted(true);
		m_shooterRight.follow(m_shooterLeft, true);

		m_pidController = m_shooterLeft.getPIDController();
		m_encoder = m_shooterLeft.getEncoder();

		kMaxOutput = 1;
		kMinOutput = -1;
		targetRPM = 5700;

		kPEntry = OIConstants.kTab.add("kP", kP).getEntry();
		kIEntry = OIConstants.kTab.add("kI", kI).getEntry();
		kDEntry = OIConstants.kTab.add("kD", kD).getEntry();
		kIzEntry = OIConstants.kTab.add("kIz", kIz).getEntry();
		kFFEntry = OIConstants.kTab.add("kFF", kFF).getEntry();
		kMaxOutputEntry = OIConstants.kTab.add("kMaxOutput", kMaxOutput).withProperties(Map.of("min", -1, "max", 1))
				.getEntry();
		kMinOutputEntry = OIConstants.kTab.add("kMinOutput", kMinOutput).withProperties(Map.of("min", -1, "max", 1))
				.getEntry();
		targetRPMEntry = OIConstants.kTab.add("targetRPM", targetRPM).withProperties(Map.of("min", 0, "max", 5700))
				.getEntry();
		rpmEntry = OIConstants.kTab.add("rpm", rpm).withWidget(BuiltInWidgets.kGraph).getEntry();
		periodic();
	}

	@Override
	public void periodic() {
		speed = kSpeedEntry.getDouble(speed);

		pidToggle = pidToggleButton.get();

		// read PID coefficients from Shuffleboard
		double p = kPEntry.getDouble(kP);
		double i = kIEntry.getDouble(kI);
		double d = kDEntry.getDouble(kD);
		double iz = kIzEntry.getDouble(kIz);
		double ff = kFFEntry.getDouble(kFF);
		double max = kMaxOutputEntry.getDouble(kMaxOutput);
		double min = kMinOutputEntry.getDouble(kMinOutput);

		// if PID coefficients on Shuffleboard have changed, write new values to
		// controller
		if ((p != kP)) {
			m_pidController.setP(p);
			kP = p;
		}
		if ((i != kI)) {
			m_pidController.setI(i);
			kI = i;
		}
		if ((d != kD)) {
			m_pidController.setD(d);
			kD = d;
		}
		if ((iz != kIz)) {
			m_pidController.setIZone(iz);
			kIz = iz;
		}
		if ((ff != kFF)) {
			m_pidController.setFF(ff);
			kFF = ff;
		}
		if ((max != kMaxOutput) || (min != kMinOutput)) {
			m_pidController.setOutputRange(min, max);
			kMinOutput = min;
			kMaxOutput = max;
		}

		targetRPM = targetRPMEntry.getDouble(targetRPM);

		rpm = m_encoder.getVelocity();
		rpmEntry.setNumber(rpm);
	}

	public void run() {
		if (!pidToggle) {
			m_shooterLeft.set(speed);
		} else {
			m_pidController.setReference(targetRPM, ControlType.kVelocity);
		}
	}
	
	public void stop() {
		if (!pidToggle) {
			m_shooterLeft.set(0);
		} else {
			m_pidController.setReference(0, ControlType.kVelocity);
		}
	}
}
