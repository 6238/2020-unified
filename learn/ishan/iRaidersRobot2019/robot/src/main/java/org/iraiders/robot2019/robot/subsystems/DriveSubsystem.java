package org.iraiders.robot2019.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.iraiders.robot2019.robot.LineSensorAnalog;
import org.iraiders.robot2019.robot.Robot;
import org.iraiders.robot2019.robot.RobotMap;
import org.iraiders.robot2019.robot.commands.drive.*;
import org.iraiders.robot2019.robot.commands.feedback.LineTrackingReporter;

public class DriveSubsystem extends Subsystem {
  public final CANSparkMax frontLeft = new CANSparkMax(RobotMap.frontLeftTalonPort, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANSparkMax backLeft = new CANSparkMax(RobotMap.backLeftTalonPort, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANSparkMax frontRight = new CANSparkMax(RobotMap.frontRightTalonPort, CANSparkMaxLowLevel.MotorType.kBrushless);
  private final CANSparkMax backRight = new CANSparkMax(RobotMap.backRightTalonPort, CANSparkMaxLowLevel.MotorType.kBrushless);

 /* public final DigitalInput leftLine = new DigitalInput(RobotMap.leftLineSensorPort);
  public final DigitalInput midLine = new DigitalInput(RobotMap.midLineSensorPort);
  public final DigitalInput rightLine = new DigitalInput(RobotMap.rightLineSensorPort);*/
 public final LineSensorAnalog leftLine = new LineSensorAnalog(RobotMap.leftLineSensorPort);
  public final LineSensorAnalog midLine = new LineSensorAnalog(RobotMap.midLineSensorPort);
  public final LineSensorAnalog rightLine = new LineSensorAnalog(RobotMap.rightLineSensorPort);

  private OIDrive oiDrive;
  public LineTrackingCommand lineTracking;
  public VisionDrive visionDrive;
  private VisionLineTrack visionLineTrack;
  private LineTrackingReporter lineTrackingReporter;
  public DifferentialDrive roboDrive = new DifferentialDrive(frontLeft, frontRight);

  public DriveSubsystem() {
    Robot.initializeSparkDefaults(frontLeft, frontRight);
    
    //backLeft.set(ControlMode.Follower, RobotMap.frontLeftTalonPort);
    //backRight.set(ControlMode.Follower, RobotMap.frontRightTalonPort);

    oiDrive = new OIDrive(this);
    visionDrive = new VisionDrive(this);
    lineTracking = new LineTrackingCommand(this);
    visionLineTrack = new VisionLineTrack(this);
    lineTrackingReporter = new LineTrackingReporter(this);
  }

  public void initTeleop() {
    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

    roboDrive.setDeadband(RobotMap.DEADBAND);

    oiDrive.start();
    lineTrackingReporter.start();
  }

  public void initControls() {
    if (RobotMap.SEPERATE_TRACKING_OPTIONS) {
      // Start Vision drive, cancel OI and Line Tracking
      RobotMap.unifiedTrackingToggle.cancelWhenPressed(oiDrive);
      RobotMap.unifiedTrackingToggle.cancelWhenPressed(lineTracking);
      RobotMap.unifiedTrackingToggle.whenReleased(oiDrive);
      RobotMap.unifiedTrackingToggle.whileHeld(visionDrive);

      // Start
      RobotMap.lineTrackingToggle.cancelWhenPressed(oiDrive);
      RobotMap.lineTrackingToggle.cancelWhenPressed(visionDrive);
      RobotMap.lineTrackingToggle.whenReleased(oiDrive);
      RobotMap.lineTrackingToggle.whileHeld(lineTracking);
    } else {
      RobotMap.unifiedTrackingToggle.toggleWhenPressed(visionLineTrack);
    }

    RobotMap.turboSpeed.whileHeld(new EngageTurbo(this));
  }

  @Override
  protected void initDefaultCommand() {

  }

  /**
   * Given a target number, current number, and increment, adjust current number by increment until we reach target
   * This is useful particularly in {@link OIDrive} where we need to ramp up to user input to avoid jerkiness
   *
   * @param target The number you eventually want to get to (ie. joystick speed)
   * @param current The current number you are at (so we know what to start at for the increment)
   * @param increment How much to increase current by until current = target
   *
   * @see <a href="https://en.wikipedia.org/wiki/Slew_rate">Wikipedia article on Slew rates</a>
   * @return Adjusted target
   */
  public static double slewLimit(double target, double current, double increment) {
    increment = Math.abs(increment); // Professionally validating user input right here 👌
    double change = target - current;
    if (Math.abs(current) > Math.abs(target)) return target; // Always slow down immediately for safety concerns
    if (change > increment) { change = increment; }
    else if (change < -increment) { change = -increment; }
    return current + change;
  }
}
