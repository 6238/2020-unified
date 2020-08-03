/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Slider;
import frc.robot.Constants.Intake;

public class IntakeSubsystem extends SubsystemBase {
	WPI_TalonSRX m_left;
	WPI_TalonSRX m_right;
	WPI_TalonSRX m_outer;

	private double innerSpeed = Intake.kStartingInnerSpeed;
	private double outerSpeed = Intake.kStartingOuterSpeed;

	private final Slider kInnerSpeedSlider = new Slider("innerSpeed", innerSpeed, -1, 1);
	private final Slider kOuterSpeedSlider = new Slider("outerSpeed", outerSpeed, -1, 1);

	public IntakeSubsystem(Factory f) {
		m_left = f.createTalon(Intake.kIntakeLeftTalon);
		m_right = f.createTalon(Intake.kIntakeRightTalon);
		m_outer = f.createTalon(Intake.kIntakeOuterTalon);

		m_left.setInverted(false);
		
		m_right.follow(m_left);
		m_right.setInverted(true);
		
		m_outer.setInverted(false);
	}

	@Override
	public void periodic() {
		innerSpeed = kInnerSpeedSlider.get();
		outerSpeed = kOuterSpeedSlider.get();
	}

	public void in() {
		m_left.set(innerSpeed);
		m_outer.set(outerSpeed);
	}
	
	public void out() {
		m_left.set(innerSpeed * -1);
		m_outer.set(outerSpeed * -1);
	}
	
	public void stop() {
		m_left.set(0);
		m_outer.set(0);
	}
}
