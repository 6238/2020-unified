package org.iraiders.robot2019.robot.commands.feedback;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.subsystems.ClimbSubsystem;

public class ClimberEncoderReporter extends Command {
  private ClimbSubsystem climbSubsystem;

  public ClimberEncoderReporter(ClimbSubsystem climbSubsystem){
    this.climbSubsystem = climbSubsystem;
    this.setRunWhenDisabled(true);
  }

  @Override
  protected void execute(){
    SmartDashboard.putNumber("Encoder Value", climbSubsystem.leftLArm.getEncoder().getPosition());
  }


  @Override
  protected boolean isFinished(){
    return false;
  }
}
