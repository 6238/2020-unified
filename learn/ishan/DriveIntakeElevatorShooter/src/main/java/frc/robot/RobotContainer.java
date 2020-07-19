/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Constants.OIConstants;

import frc.robot.commands.DriveCommand;
import frc.robot.commands.ElevatorDownCommand;
import frc.robot.commands.ElevatorStopCommand;
import frc.robot.commands.ElevatorUpCommand;
import frc.robot.commands.IntakeInCommand;
import frc.robot.commands.IntakeOutCommand;
import frc.robot.commands.IntakeStopCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.ShooterStopCommand;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

import frc.robot.subsystems.Factory;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
	private final Factory factory = new Factory();
	private final DriveSubsystem m_drive = new DriveSubsystem(factory);
	private final ElevatorSubsystem m_elevator = new ElevatorSubsystem(factory);
	private final IntakeSubsystem m_intake = new IntakeSubsystem(factory);
	private final ShooterSubsystem m_shooter = new ShooterSubsystem(factory);

	private final DriveCommand m_driveCommand = new DriveCommand(m_drive);

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		// Configure the button bindings
		configureButtonBindings();
		m_drive.setDefaultCommand(m_driveCommand);
	}

	/**
	 * Use this method to define your button->command mappings. Buttons can be
	 * created by instantiating a {@link GenericHID} or one of its subclasses
	 * ({@link Joystick} or {@link XboxController}), and then passing it to a
	 * {@link JoystickButton}.
	 */
	private void configureButtonBindings() {
		new JoystickButton(OIConstants.joysticks[OIConstants.kCurvatureDriveQuickTurnToggle[0]],
				OIConstants.kCurvatureDriveQuickTurnToggle[1])
						.whenPressed(() -> m_drive.setCurvatureDriveQuickTurn(true))
						.whenReleased(() -> m_drive.setCurvatureDriveQuickTurn(false));

		new JoystickButton(OIConstants.joysticks[OIConstants.kElevatorToggle[0]], OIConstants.kElevatorToggle[1])
				.whenPressed(new ElevatorUpCommand(m_elevator)).whenReleased(new ElevatorStopCommand(m_elevator));

		new JoystickButton(OIConstants.joysticks[OIConstants.kElevatorToggle[0]], OIConstants.kElevatorToggle[2])
				.whenPressed(new ElevatorDownCommand(m_elevator)).whenReleased(new ElevatorStopCommand(m_elevator));
		
		new JoystickButton(OIConstants.joysticks[OIConstants.kIntakeToggle[0]], OIConstants.kIntakeToggle[1])
				.whenPressed(new IntakeInCommand(m_intake)).whenReleased(new IntakeStopCommand(m_intake));

		new JoystickButton(OIConstants.joysticks[OIConstants.kIntakeToggle[0]], OIConstants.kIntakeToggle[2])
				.whenPressed(new IntakeOutCommand(m_intake)).whenReleased(new IntakeStopCommand(m_intake));

		new JoystickButton(OIConstants.joysticks[OIConstants.kShooterToggle[0]], OIConstants.kShooterToggle[1])
				.whenPressed(new ShooterCommand(m_shooter)).whenReleased(new ShooterStopCommand(m_shooter));
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
}
