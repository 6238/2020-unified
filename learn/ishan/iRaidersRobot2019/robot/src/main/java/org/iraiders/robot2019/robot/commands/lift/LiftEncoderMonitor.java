package org.iraiders.robot2019.robot.commands.lift;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.subsystems.LiftSubsystem;

public class LiftEncoderMonitor extends Command {
  private final LiftSubsystem subsystem;
  
  public LiftEncoderMonitor(LiftSubsystem subsystem) {
    this.subsystem = subsystem;
    this.setRunWhenDisabled(true);
  }
  
  @Override
  protected void execute() {
    if (subsystem.liftTalon.getSensorCollection().isFwdLimitSwitchClosed())
      subsystem.liftTalon.setSelectedSensorPosition(0);
    SmartDashboard.putNumber("LiftPos", subsystem.liftTalon.getSelectedSensorPosition());
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
