package frc.robot.helpers;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

import javax.annotation.Nullable;

/**
 * A class holding all subsystems, controllers, etc to be injected into
 * RobotContainer
 * 
 * @author sherif
 */
public class RobotInjection {
    @Nullable public TestableJoystick joystick;
    @Nullable public IntakeSubsystem intakeSubsystem;
    @Nullable public DriveSubsystem driveSubsystem;
    @Nullable public ShooterSubsystem shooterSubsystem;
    @Nullable public Factory factory;
}
