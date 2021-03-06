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
import frc.robot.Constants.Elevator;

public class ElevatorSubsystem extends SubsystemBase {
	WPI_TalonSRX m_front;
	WPI_TalonSRX m_back;
	WPI_TalonSRX m_feeder;

	private double speed = Elevator.kStartingSpeed;
	private double feederSpeed = Elevator.kStartingFeederSpeed;

	private final Slider kSpeedSlider = new Slider("elevatorSpeed", speed, -1, 1);
	private final Slider kFeederSpeedSlider = new Slider("feederSpeed", speed, -1, 1);

	public ElevatorSubsystem(Factory f) {
		m_front = f.createTalon(Elevator.kFrontElevatorTalon);
		m_back = f.createTalon(Elevator.kRearElevatorTalon);
		m_feeder = f.createTalon(Elevator.kFeederTalon);

		m_front.setInverted(false);

		m_back.follow(m_front);
		m_back.setInverted(true);

		m_feeder.setInverted(false);
	}

	@Override
	public void periodic() {
		speed = kSpeedSlider.get();
		feederSpeed = kFeederSpeedSlider.get();
	}

	public void up() {
		m_front.set(speed);
		m_feeder.set(feederSpeed);
	}

	public void down() {
		m_front.set(speed * -1);
		m_feeder.set(feederSpeed * -1);
	}

	public void stop() {
		m_front.set(0);
		m_feeder.set(0);
	}
}
