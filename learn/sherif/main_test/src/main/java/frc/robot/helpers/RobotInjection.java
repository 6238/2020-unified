package frc.robot.helpers;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeControl;
import frc.robot.subsystems.ShooterController;

import javax.annotation.Nullable;

/**
 * A class holding all subsystems, controllers, etc to be injected into
 * RobotContainer
 * 
 * @author sherif
 */
public class RobotInjection {
    @Nullable public TestableJoystick joystick;
    @Nullable public IntakeControl intakeControl;
    @Nullable public DriveTrain driveTrain;
    @Nullable public ShooterController shooterController;
}
