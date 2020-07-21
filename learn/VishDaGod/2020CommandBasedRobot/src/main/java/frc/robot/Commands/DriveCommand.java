/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.JoystickObjects;
import frc.robot.subsystems.DriveSubsystem;

/**
 * Creates a new DriveCommand.
 */
public class DriveCommand extends CommandBase {

	// Joysticks for driving
	private Joystick leftJoystick = JoystickObjects.leftJoystick;
	private Joystick rightJoystick = JoystickObjects.rightJoystick;

	// Doubles for the driving numbers
	private double tank_left;
	private double tank_right;
	private double ySpeed;
	private double zSpeed;

	// The subsystem for the file
	private final DriveSubsystem driveSubsystem;

	public DriveCommand(DriveSubsystem driveSubsystem) {
		this.driveSubsystem = driveSubsystem;
		addRequirements(driveSubsystem);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		tank_left = leftJoystick.getY();
		tank_right = rightJoystick.getY();
		ySpeed = leftJoystick.getY();
		zSpeed = rightJoystick.getZ();

		driveSubsystem.drive(tank_left, tank_right, ySpeed, zSpeed);
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		return false;
	}
}
