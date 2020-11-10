/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.Elevator;
import frc.robot.dashboard.Dashboard;
import frc.robot.dashboard.Slider;

public class ElevatorSubsystem extends SubsystemBase {
	WPI_TalonSRX m_front;
	WPI_TalonSRX m_back;
	WPI_TalonSRX m_feeder;

	private double speed = Elevator.ELEVATOR_STARTING_SPEED;
	private double feederSpeed = Elevator.FEEDER_STARTING_SPEED;

	private final Slider speedSlider;
	private final Slider feederSpeedSlider;

	public ElevatorSubsystem(Factory f, Dashboard d) {
		m_front = f.createTalon(Elevator.ELEVATOR_FRONT_TALON_ID);
		m_back = f.createTalon(Elevator.ELEVATOR_REAR_TALON_ID);
		m_feeder = f.createTalon(Elevator.FEEDER_TALON_ID);

		m_front.setInverted(false);

		m_back.follow(m_front);
		m_back.setInverted(true);

        m_feeder.setInverted(false);
        
        speedSlider = d.getSlider("elevatorSpeed", speed, -1, 1);
        feederSpeedSlider = d.getSlider("feederSpeed", speed, -1, 1);
	}

	@Override
	public void periodic() {
		speed = speedSlider.get();
		feederSpeed = feederSpeedSlider.get();
	}

	public void up() {
		m_front.set(speed); //TODO: split up elevator and feeder
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
