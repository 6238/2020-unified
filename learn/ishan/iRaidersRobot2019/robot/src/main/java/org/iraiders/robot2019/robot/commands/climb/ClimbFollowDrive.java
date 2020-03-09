package org.iraiders.robot2019.robot.commands.climb;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2019.robot.subsystems.ClimbSubsystem;
import org.iraiders.robot2019.robot.subsystems.DriveSubsystem;

public class ClimbFollowDrive extends Command {
  private final DriveSubsystem driveSubsystem;
  private final ClimbSubsystem climbSubsystem;

  public ClimbFollowDrive(DriveSubsystem driveSubsystem, ClimbSubsystem climbSubsystem) {
    this.driveSubsystem = driveSubsystem;
    this.climbSubsystem = climbSubsystem;
  }

  @Override
  protected void execute() {
    this.climbSubsystem.climberPistonMotor.set(ControlMode.PercentOutput, driveSubsystem.frontLeft.get());
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
