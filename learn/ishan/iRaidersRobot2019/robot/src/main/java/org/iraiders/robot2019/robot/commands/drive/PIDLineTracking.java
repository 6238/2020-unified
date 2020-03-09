package org.iraiders.robot2019.robot.commands.drive;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;

public class PIDLineTracking extends PIDCommand {
  private XboxController xbox = OI.xBoxController;
  private double lastLeftStickVal = 0;
  private double lastRightStickVal = 0;
  private DriveSubsystem driveSubsystem;

  public double snapScaleValue = 0;
  private double joystickChangeLimit = .09;

  public byte lineSensorByte = 0;

  public PIDLineTracking(DriveSubsystem driveSubsystem) {
    super(.03,0,0);
    this.driveSubsystem = driveSubsystem;
    requires(driveSubsystem);

    this.setSetpoint(0);
    this.getPIDController().setInputRange(-1,1);
    this.getPIDController().setOutputRange(-1,1);

  }

  @Override
  protected void initialize() {
    DriverStation.reportWarning("Started LineTracking", false);

    driveSubsystem.roboDrive.setMaxOutput(.25);

    snapScaleValue = SmartDashboard.getNumber("Snap Scale Value", 0.62);
    SmartDashboard.putNumber("Snap Scale Value", snapScaleValue); // In case it's vanished from the dashboard
  }

  @Override
  protected double returnPIDInput() {
    double measuredLeft;
    double measuredTurn = 0;
    double measuredError = 0;

    lineSensorByte = 0;

    if (driveSubsystem.leftLine.getValue()) {
      lineSensorByte |= 1;
    }

    if (driveSubsystem.midLine.getValue()) {
      lineSensorByte |= 2;
    }

    if (driveSubsystem.rightLine.getValue()) {
      lineSensorByte |= 4;
    }

    if (snapScaleValue == 0) return 0; // Don't move if disabled


    switch (lineSensorByte) {
      // No line sensors are detected
      case 0:
        measuredError = 0;
        break;
      // Just right line sensor detected
      case 4:
        measuredError = 1;
        break;
      // Just middle line sensor detected (error)
      case 2:
        measuredError = 0;
        break;
      // Middle and right line sensor detected
      case 6:
        measuredError = 1 / 2;
        break;
      // left line sensor detected
      case 1:
        measuredError = -1;
        break;
      // Left and right line sensors detected (error)
      case 5:
        measuredError = 0;
        break;
      // Left and middle sensors detected
      case 3:
        measuredError = -1 / 2;
        break;
      // All line sensors detected
      case 7:
        measuredError = 0;
        break;
      default:
        break;
    }

    return measuredError;

  }

  @Override
  protected void usePIDOutput(double output) {
    driveSubsystem.roboDrive.arcadeDrive(OI.xBoxController.getX(GenericHID.Hand.kLeft), output * snapScaleValue, false);

  }


  @Override
  protected void end() {
    driveSubsystem.roboDrive.stopMotor();
    DriverStation.reportWarning("Stopped LineTracking", false);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}

