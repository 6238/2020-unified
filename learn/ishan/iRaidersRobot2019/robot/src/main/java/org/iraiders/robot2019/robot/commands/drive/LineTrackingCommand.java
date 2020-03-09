package org.iraiders.robot2019.robot.commands.drive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;

public class LineTrackingCommand extends Command {
  private XboxController xbox = OI.xBoxController;
  private double lastLeftStickVal = 0;
  private double lastRightStickVal = 0;
  private DriveSubsystem driveSubsystem;

  public double snapScaleValue = 0;
  private double joystickChangeLimit = .09;

  public byte lineSensorByte = 0;

  public LineTrackingCommand(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    requires(driveSubsystem);
  }

  @Override
  protected void initialize() {
    DriverStation.reportWarning("Started LineTracking", false);

    driveSubsystem.roboDrive.setMaxOutput(.25);

    snapScaleValue = SmartDashboard.getNumber("Snap Scale Value", 0.62);
    SmartDashboard.putNumber("Snap Scale Value", snapScaleValue); // In case it's vanished from the dashboard
  }

  @Override
  protected void execute() {
    double measuredLeft;
    double measuredTurn = 0;

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

    if (snapScaleValue == 0) return; // Don't move if disabled


    switch (lineSensorByte) {
      // No line sensors are detected
      case 0:
        measuredTurn = 0;
        break;
      // Just right line sensor detected
      case 4:
        measuredTurn = snapScaleValue;
        break;
      // Just middle line sensor detected (error)
      case 2:
        measuredTurn = 0;
        break;
      // Middle and right line sensor detected
      case 6:
        measuredTurn = snapScaleValue / 2;
        break;
      // left line sensor detected
      case 1:
        measuredTurn = -snapScaleValue;
        break;
      // Left and right line sensors detected (error)
      case 5:
        measuredTurn = 0;
        break;
      // Left and middle sensors detected
      case 3:
        measuredTurn = -snapScaleValue / 2;
        break;

      // All line sensors detected
      case 7:
        measuredTurn = 0;
        break;
      default:
        break;
    }

    measuredLeft = DriveSubsystem.slewLimit(xbox.getY(GenericHID.Hand.kLeft), lastLeftStickVal, joystickChangeLimit);
    measuredTurn = DriveSubsystem.slewLimit(measuredTurn, lastRightStickVal, joystickChangeLimit);
    driveSubsystem.roboDrive.arcadeDrive(measuredLeft, measuredTurn, true);

    lastLeftStickVal = measuredLeft;
    lastRightStickVal = measuredTurn;
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
