package org.iraiders.robot2019.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2019.robot.Robot;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;

public class EngageTurbo extends Command {
  private DriveSubsystem driveSubsystem;
  private final float TURBO_SPEED = 1f;
  public static final float REGULAR_SPEED = .8f;

  public EngageTurbo(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
  }

  @Override
  protected void initialize() {
    driveSubsystem.roboDrive.setMaxOutput(TURBO_SPEED);
  }

  @Override
  protected void end() {
    driveSubsystem.roboDrive.setMaxOutput((Robot.prefs.getFloat("OIMaxSpeed", REGULAR_SPEED)));
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
