package org.iraiders.robot2019.robot.commands;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class SimpleMotorCommand extends Command {
  private SpeedController motor;
  private double speed;
  
  public SimpleMotorCommand(SpeedController motor, double speed) {
    this(null, motor, speed);
  }
  
  public SimpleMotorCommand(Subsystem subsystem, SpeedController motor, double speed) {
    if (subsystem != null) this.requires(subsystem);
    this.motor = motor;
    this.speed = speed;
  }
  
  @Override
  protected void execute() {
    motor.set(speed);
  }
  
  protected void end() {
    motor.set(0);
  }
  
  @Override
  protected boolean isFinished() {
    return false;
  }
}
