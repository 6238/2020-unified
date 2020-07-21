/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

/**
 * This class is for the Ball Intake Subsystem 
 * @author Vishnu Velayuthan
 * @author vishnuvelayuthan@gmail.com
 * @version 1.0
 * @since 1.00
 */
public class IntakeSubsystem extends SubsystemBase {

	private final WPI_TalonSRX feeder;
	private final WPI_TalonSRX backMagazine;
	private final WPI_TalonSRX frontMagazine;


	/**
	 * This constructor initialises the talons of the subsystem
	 * @param factory The factory that creates the Talon objects
	 */
	public IntakeSubsystem(final Factory factory) {
		// Top feeder wheel
		feeder = factory.createTalon(IntakeConstants.feeder); 

		// Back wheel for ball intake
		backMagazine = factory.createTalon(IntakeConstants.backMagazine); 

		// Front wheel for ball intake
		frontMagazine = factory.createTalon(IntakeConstants.frontMagazine); 

		// Back is a follower of front
		backMagazine.follow(frontMagazine);
		backMagazine.setInverted(true); // Back is inverted of front
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}


	// 
	/**
	 * This function is run when the subsystem starts
	 * Starts all motors
	 */
	public void start() {
		frontMagazine.set(0.5); // Back should move with the front
		feeder.set(0.5);
	}


	/**
	 * This function is run when the subsystem ends
	 * Stops all motors
	 */
	public void stop() {
		frontMagazine.set(0.0); // Back should move with the front
		feeder.set(0.0);
	}
}
