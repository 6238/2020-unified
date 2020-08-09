/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.OIConstants;
import frc.robot.Constants.Shooter;
import frc.robot.dashboard.Dashboard;
import frc.robot.dashboard.Slider;
import frc.robot.dashboard.ToggleButton;
import frc.robot.interfaces.CANSparkMaxInterface;

public class ShooterSubsystem extends SubsystemBase {
	CANSparkMaxInterface m_shooterLeft;
	CANSparkMaxInterface m_shooterRight;
	CANPIDController m_pidController;
	CANEncoder m_encoder;
	double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput, targetRPM, rpm;

	NetworkTableEntry kPEntry, kIEntry, kDEntry, kIzEntry, kFFEntry, rpmEntry;
	Slider kMaxOutputEntry, kMinOutputEntry, targetRPMEntry;

	private double speed = Shooter.SHOOTER_STARTING_SPEED;
	private final Slider kSpeedEntry;

	boolean pidToggle = false;
	ToggleButton pidToggleButton = new ToggleButton("pidToggle", pidToggle);

	public ShooterSubsystem(Factory f, Dashboard d) {
		m_shooterLeft = f.createSpark(Shooter.SHOOTER_LEFT_SPARK_ID);
		m_shooterRight = f.createSpark(Shooter.SHOOTER_RIGHT_SPARK_ID);
		m_shooterLeft.setInverted(true);
		m_shooterRight.follow(m_shooterLeft, true);

		m_pidController = m_shooterLeft.getPIDController();
		m_encoder = m_shooterLeft.getEncoder();

		kMaxOutput = 1;
		kMinOutput = -1;
		targetRPM = 5700;

		kPEntry = OIConstants.SHUFFLEBOARD_TAB.add("kP", kP).getEntry();
		kIEntry = OIConstants.SHUFFLEBOARD_TAB.add("kI", kI).getEntry();
		kDEntry = OIConstants.SHUFFLEBOARD_TAB.add("kD", kD).getEntry();
		kIzEntry = OIConstants.SHUFFLEBOARD_TAB.add("kIz", kIz).getEntry();
		kFFEntry = OIConstants.SHUFFLEBOARD_TAB.add("kFF", kFF).getEntry();
		kMaxOutputEntry = d.getSlider("kMaxOutput", kMaxOutput, -1, 1);
		kMinOutputEntry = d.getSlider("kMinOutput", kMinOutput, -1, 1);
		targetRPMEntry = d.getSlider("targetRPM", targetRPM, 0, 5700);
        rpmEntry = OIConstants.SHUFFLEBOARD_TAB.add("rpm", rpm).withWidget(BuiltInWidgets.kGraph).getEntry();
        
        kSpeedEntry = d.getSlider("shooterSpeed", speed, -1, 1);

		periodic();
	}

	@Override
	public void periodic() {
		speed = kSpeedEntry.get();

		pidToggle = pidToggleButton.get();

		// read PID coefficients from Shuffleboard
		double p = kPEntry.getDouble(kP);
		double i = kIEntry.getDouble(kI);
		double d = kDEntry.getDouble(kD);
		double iz = kIzEntry.getDouble(kIz);
		double ff = kFFEntry.getDouble(kFF);
		double max = kMaxOutputEntry.get();
		double min = kMinOutputEntry.get();

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

		targetRPM = targetRPMEntry.get();

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
