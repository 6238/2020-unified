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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

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

import frc.robot.dashboard.Dashboard;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableJoystick;

import frc.robot.subsystems.Factory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private final Factory factory = new Factory();
    private final Dashboard dashboard = new Dashboard();
    private final DriveSubsystem drive;
    private final ElevatorSubsystem m_elevator;
    private final IntakeSubsystem m_intake;
    private final ShooterSubsystem m_shooter;

    private DriveCommand m_driveCommand;

    private final TestableJoystick[] joysticks;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        drive = new DriveSubsystem(factory, dashboard);
        m_elevator = new ElevatorSubsystem(factory, dashboard);
        m_intake = new IntakeSubsystem(factory, dashboard);
        m_shooter = new ShooterSubsystem(factory, dashboard);

        joysticks = new TestableJoystick[2];
        joysticks[0] = new TestableJoystick(OIConstants.JOYSTICK_LEFT_PORT);
        joysticks[1] = new TestableJoystick(OIConstants.JOYSTICK_RIGHT_PORT);

        m_driveCommand = new DriveCommand(drive, joysticks[0], joysticks[1]);

        drive.setDefaultCommand(m_driveCommand);

        configureButtonBindings();
    }

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     * Allows for a (mock) controller to be injected.
     */
    public RobotContainer(final RobotInjection injection) {
        drive = injection.m_drive;
        m_elevator = injection.m_elevator;
        m_intake = injection.m_intake;
        m_shooter = injection.m_shooter;

        joysticks = new TestableJoystick[2];
        joysticks[0] = injection.m_leftJoystick;
        joysticks[1] = injection.m_rightJoystick;

        m_driveCommand = injection.m_driveCommand;
        
        drive.setDefaultCommand(m_driveCommand);

        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link Joystick} or {@link XboxController}), and then passing it to a
     * {@link JoystickButton}.
     */
    private void configureButtonBindings() {
        if (joysticks[0] == null || joysticks[1] == null) {
            return;
        }

        if (drive != null) {
            new JoystickButton(joysticks[OIConstants.CURVATURE_DRIVE_QUICK_TURN_TOGGLE[0]],
                    OIConstants.CURVATURE_DRIVE_QUICK_TURN_TOGGLE[1])
                            .whenPressed(() -> drive.setCurvatureDriveQuickTurn(true))
                            .whenReleased(() -> drive.setCurvatureDriveQuickTurn(false));
        }

        if (m_elevator != null) {
            new JoystickButton(joysticks[OIConstants.ELEVATOR_TOGGLE[0]], OIConstants.ELEVATOR_TOGGLE[1])
                    .whenPressed(new ElevatorUpCommand(m_elevator)).whenReleased(new ElevatorStopCommand(m_elevator));

            new JoystickButton(joysticks[OIConstants.ELEVATOR_TOGGLE[0]], OIConstants.ELEVATOR_TOGGLE[2])
                    .whenPressed(new ElevatorDownCommand(m_elevator)).whenReleased(new ElevatorStopCommand(m_elevator));
        }

        if (m_intake != null) {
            new JoystickButton(joysticks[OIConstants.INTAKE_TOGGLE[0]], OIConstants.INTAKE_TOGGLE[1])
                    .whenPressed(new IntakeInCommand(m_intake)).whenReleased(new IntakeStopCommand(m_intake));

            new JoystickButton(joysticks[OIConstants.INTAKE_TOGGLE[0]], OIConstants.INTAKE_TOGGLE[2])
                    .whenPressed(new IntakeOutCommand(m_intake)).whenReleased(new IntakeStopCommand(m_intake));
        }

        if (m_shooter != null) {
            new JoystickButton(joysticks[OIConstants.SHOOTER_TOGGLE[0]], OIConstants.SHOOTER_TOGGLE[1])
                    .whenPressed(new ShooterCommand(m_shooter)).whenReleased(new ShooterStopCommand(m_shooter));
        }
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

    public void scheduleCommands() {
        CommandScheduler.getInstance().schedule(m_driveCommand);
    }

    public void unscheduleCommands() {
        CommandScheduler.getInstance().cancel(m_driveCommand);
    }
}
