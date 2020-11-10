/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.Intake;
import frc.robot.dashboard.Dashboard;
import frc.robot.dashboard.Slider;

public class IntakeSubsystem extends SubsystemBase {
	WPI_TalonSRX m_left;
	WPI_TalonSRX m_right;
	WPI_TalonSRX m_outer;

	private double innerSpeed = Intake.INTAKE_STARTING_INNER_SPEED;
	private double outerSpeed = Intake.INTAKE_STARTING_OUTER_SPEED;

	private final Slider innerSpeedSlider;
	private final Slider outerSpeedSlider;

	public IntakeSubsystem(Factory f, Dashboard d) {
		m_left = f.createTalon(Intake.INTAKE_LEFT_TALON_ID);
		m_right = f.createTalon(Intake.INTAKE_RIGHT_TALON_ID);
		m_outer = f.createTalon(Intake.INTAKE_OUTER_TALON_ID);

		m_left.setInverted(false);
		
		m_right.follow(m_left);
		m_right.setInverted(true);
		
        m_outer.setInverted(false);
        
        innerSpeedSlider = d.getSlider("innerSpeed", innerSpeed, -1, 1);
        outerSpeedSlider = d.getSlider("outerSpeed", outerSpeed, -1, 1);
	}

	@Override
	public void periodic() {
		innerSpeed = innerSpeedSlider.get();
		outerSpeed = outerSpeedSlider.get();
	}

	public void in() {
		m_left.set(innerSpeed); //TODO: split up inner and outer
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
