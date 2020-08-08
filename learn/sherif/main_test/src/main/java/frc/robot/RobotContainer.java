/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drive;
import frc.robot.commands.Intake;
import frc.robot.helpers.RobotInjection;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeControl;

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
    @Nullable private final DriveTrain m_drivetrain;
    @Nullable private final Joystick m_controller;
    @Nullable private final IntakeControl m_intake;

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        this.m_drivetrain = new DriveTrain(factory);
        this.m_controller = new Joystick(Constants.JOYSTICK_A);
        this.m_intake = new IntakeControl(factory);

        configureButtonBindings();
    }

    // Allows for a (mock) controller to be injected
    public RobotContainer(RobotInjection injection) {
        this.m_controller = injection.joystick;
        this.m_drivetrain = injection.driveTrain;
        this.m_intake = injection.intakeControl;

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
        if (this.m_controller == null) return;

        if (this.m_drivetrain != null) {
            new JoystickButton(m_controller, Joystick.AxisType.kThrottle.value)
                    .or(new JoystickButton(m_controller, Joystick.AxisType.kTwist.value))
                    .whenActive(new Drive(this.m_drivetrain, m_controller))
                    .whenInactive(new Drive(this.m_drivetrain, 0, 0));
        }

        if (this.m_intake != null) {
            new JoystickButton(m_controller, Joystick.ButtonType.kTop.value)
                    .whenPressed(new Intake(this.m_intake, new Timer()));
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
}
