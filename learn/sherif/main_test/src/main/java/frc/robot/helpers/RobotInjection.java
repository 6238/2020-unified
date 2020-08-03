package frc.robot.helpers;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IntakeControl;

/**
 * A class holding all subsystems, controllers, etc to be injected into
 * RobotContainer
 * 
 * @author sherif
 */
public class RobotInjection {
    public Joystick joystick;
    public IntakeControl intakeControl;
    public DriveTrain driveTrain;
}
