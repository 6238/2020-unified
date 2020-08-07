package frc.robot.helpers;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeControl;

import javax.annotation.Nullable;

/**
 * A class holding all subsystems, controllers, etc to be injected into
 * RobotContainer
 * 
 * @author sherif
 */
public class RobotInjection {
    @Nullable public Joystick joystick;
    @Nullable public IntakeControl intakeControl;
    @Nullable public DriveTrain driveTrain;
}
