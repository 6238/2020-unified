/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableJoystick;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

import javax.annotation.Nullable;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    public Factory factory = new Factory();
    @Nullable private final DriveSubsystem driveSubsystem;
    @Nullable private final TestableJoystick joystick;
    @Nullable private final IntakeSubsystem intakeSubsystem;
    @Nullable private final ShooterSubsystem shooterSubsystem;

    @Nullable
    public DriveCommand getDriveCommand() {
        return driveCommand;
    }

    @Nullable private DriveCommand driveCommand = null;
    @Nullable private IntakeCommand intakeCommand = null;
    @Nullable private ShootCommand shootCommand = null;

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        driveSubsystem = new DriveSubsystem(factory);
        joystick = new TestableJoystick(Constants.JOYSTICK_A);
        intakeSubsystem = new IntakeSubsystem(factory);
//        shooterController = null;
        shooterSubsystem = new ShooterSubsystem(factory, true);

        configureButtonBindings();
    }

    // Allows for a (mock) controller to be injected
    public RobotContainer(RobotInjection injection) {
        joystick = injection.joystick;
        driveSubsystem = injection.driveSubsystem;
        intakeSubsystem = injection.intakeSubsystem;
        shooterSubsystem = injection.shooterSubsystem;
        factory = injection.factory != null ? injection.factory : new Factory();

        configureButtonBindings();
    }


    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * verify(f.getMotor(0)).set(0.2);
     * verify(f.getMotor(1)).set(0.8); * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        if (joystick == null) return;

//        if (m_drivetrain != null) {
//            new JoystickButton(m_controller, Joystick.AxisType.kThrottle.value)
//                    .or(new TestableJoystickButton(m_controller, Joystick.AxisType.kTwist.value))
//                    .whenActive(new Drive(m_drivetrain, m_controller))
//                    .whenInactive(new Drive(m_drivetrain, 0, 0));
//        }

//        if (m_intake != null) {
////            new JoystickButton(m_controller, Joystick.ButtonType.kTop.value)
////                    .whenPressed(new Intake(m_intake, m_controller));
//        }
    }

    public void startDrive() {
        if (driveSubsystem != null) {
            driveCommand = new DriveCommand(factory, driveSubsystem, joystick);
            CommandScheduler.getInstance().schedule(driveCommand);
        }

        if (intakeSubsystem != null) {
            intakeCommand = new IntakeCommand(factory, intakeSubsystem, joystick);
            CommandScheduler.getInstance().schedule(intakeCommand);
        }

        if (shooterSubsystem != null) {
            shootCommand = new ShootCommand(factory, shooterSubsystem);
            CommandScheduler.getInstance().schedule(shootCommand);
        }
    }

    public void stopDrive() {
        CommandScheduler.getInstance().cancel(driveCommand);
        CommandScheduler.getInstance().cancel(intakeCommand);
        CommandScheduler.getInstance().cancel(shootCommand);
    }

    public void logTestableJoystick() {
        if (joystick == null) return;
        System.out.println("GetX: " + joystick.getX());
        System.out.println("GetY: " + joystick.getY());
        System.out.println("GetZ: " + joystick.getZ());
        System.out.println("Throttle: " + joystick.getThrottle());
        System.out.println("Radians: " + joystick.getTwist());
    }

    public void logIntake() {
        if (joystick == null) return;

        System.out.println("Raw button 3: " + joystick.getRawButton(3));
        System.out.println("Raw button 4: " + joystick.getRawButton(4));
        System.out.println("Raw button 5: " + joystick.getRawButton(5));
        System.out.println("Raw button 6: " + joystick.getRawButton(6));
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
