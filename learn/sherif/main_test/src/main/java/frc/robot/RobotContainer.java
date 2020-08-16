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
import frc.robot.commands.Drive;
import frc.robot.commands.Intake;
import frc.robot.commands.Shoot;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableJoystick;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeControl;
import frc.robot.subsystems.ShooterController;

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
    @Nullable private final DriveTrain driveTrain;
    @Nullable private final TestableJoystick joystick;
    @Nullable private final IntakeControl intakeControl;
    @Nullable private final ShooterController shooterController;

    @Nullable
    public Drive getDriveCommand() {
        return driveCommand;
    }

    @Nullable private Drive driveCommand = null;
    @Nullable private Intake intakeCommand = null;
    @Nullable private Shoot shootCommand = null;

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        this.driveTrain = new DriveTrain(factory);
        this.joystick = new TestableJoystick(Constants.JOYSTICK_A);
        this.intakeControl = new IntakeControl(factory);
//        this.shooterController = null;
        this.shooterController = new ShooterController(factory, true);

        configureButtonBindings();
    }

    // Allows for a (mock) controller to be injected
    public RobotContainer(RobotInjection injection) {
        this.joystick = injection.joystick;
        this.driveTrain = injection.driveTrain;
        this.intakeControl = injection.intakeControl;
        this.shooterController = injection.shooterController;

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
        if (this.joystick == null) return;

//        if (this.m_drivetrain != null) {
//            new JoystickButton(m_controller, Joystick.AxisType.kThrottle.value)
//                    .or(new TestableJoystickButton(m_controller, Joystick.AxisType.kTwist.value))
//                    .whenActive(new Drive(this.m_drivetrain, m_controller))
//                    .whenInactive(new Drive(this.m_drivetrain, 0, 0));
//        }

//        if (this.m_intake != null) {
////            new JoystickButton(m_controller, Joystick.ButtonType.kTop.value)
////                    .whenPressed(new Intake(this.m_intake, this.m_controller));
//        }
    }

    public void startDrive() {
        if (this.driveTrain != null) {
            this.driveCommand = new Drive(this.driveTrain, this.joystick);
            this.driveCommand.useShuffleboard(this.factory);
            CommandScheduler.getInstance().schedule(this.driveCommand);
        }

        if (this.intakeControl != null) {
            this.intakeCommand = new Intake(this.factory, this.intakeControl, this.joystick);
            CommandScheduler.getInstance().schedule(this.intakeCommand);
        }

        if (this.shooterController != null) {
            this.shootCommand = new Shoot(this.shooterController);
            this.shootCommand.useSlider(this.factory);
            CommandScheduler.getInstance().schedule(this.shootCommand);
        }
    }

    public void stopDrive() {
        CommandScheduler.getInstance().cancel(this.driveCommand);
        CommandScheduler.getInstance().cancel(this.intakeCommand);
        CommandScheduler.getInstance().cancel(this.shootCommand);
    }

    public void logTestableJoystick() {
        if (this.joystick == null) return;
        System.out.println("GetX: " + this.joystick.getX());
        System.out.println("GetY: " + this.joystick.getY());
        System.out.println("GetZ: " + this.joystick.getZ());
        System.out.println("Throttle: " + this.joystick.getThrottle());
        System.out.println("Radians: " + this.joystick.getTwist());
    }

    public void logIntake() {
        if (this.joystick == null) return;

        System.out.println("Raw button 3: " + this.joystick.getRawButton(3));
        System.out.println("Raw button 4: " + this.joystick.getRawButton(4));
        System.out.println("Raw button 5: " + this.joystick.getRawButton(5));
        System.out.println("Raw button 6: " + this.joystick.getRawButton(6));
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
