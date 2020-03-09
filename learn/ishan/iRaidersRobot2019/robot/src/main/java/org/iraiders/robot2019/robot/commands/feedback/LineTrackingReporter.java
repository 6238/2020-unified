package org.iraiders.robot2019.robot.commands.feedback;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.RobotMap;
import org.iraiders.robot2019.robot.UltraSonicSensorAnalog;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;


public class LineTrackingReporter extends Command {
  private DriveSubsystem driveSubsystem;
  private UltraSonicSensorAnalog ultraSonicSensorAnalog;

  public LineTrackingReporter(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    ultraSonicSensorAnalog = new UltraSonicSensorAnalog(RobotMap.ultrasonicAnalog);
    this.setRunWhenDisabled(true);
  }

  @Override
  protected void execute() {
    SmartDashboard.putBoolean("LeftLine", driveSubsystem.leftLine.getValue());
    SmartDashboard.putBoolean("midLine", driveSubsystem.midLine.getValue());
    SmartDashboard.putBoolean("rightLine", driveSubsystem.rightLine.getValue());

    SmartDashboard.putNumber("distance", ultraSonicSensorAnalog.distanceCalc());

  }

  @Override
  protected boolean isFinished() {
     return false;
  }
}
