/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.sql.Driver;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.JoystickConstants;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ElevatorCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.ElevatorSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	private Factory factory;

	private DriveSubsystem driveSubsystem;
	private DriveCommand driveCommand; 

	private ElevatorSubsystem intakeSubsystem;
	private ElevatorCommand intakeCommand;
	
	public RobotContainer() {
		factory = new Factory();

		driveSubsystem = new DriveSubsystem(factory);
		driveCommand = new DriveCommand(driveSubsystem); 

		intakeSubsystem = new ElevatorSubsystem(factory);
		intakeCommand = new ElevatorCommand(intakeSubsystem);

		// Configure the button bindings
		configureButtonBindings();
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by instantiating a {@link GenericHID} or one of its subclasses
	 * ({@link Joystick} or {@link XboxController}), and then passing it to a
	 * {@link JoystickButton}.
	 */
	private void configureButtonBindings() {
		new JoystickButton(JoystickConstants.rightJoystick, JoystickConstants.isQuickTurnID)
				.whenPressed(() -> driveSubsystem.changeQuickTurn());

		
		// new JoystickButton(JoystickConstants.leftJoystick, JoystickConstants.DriveCommand).whenPressed(drive)
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		// An ExampleCommand will run in autonomous
		return null;
	}



	public DriveCommand getDriveCommand() {
		return this.driveCommand;
	}


	public ElevatorCommand getIntakeCommand() {
		return this.intakeCommand;
	}
}
