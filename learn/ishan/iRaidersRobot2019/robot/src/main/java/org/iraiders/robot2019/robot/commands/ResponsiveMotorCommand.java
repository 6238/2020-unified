package org.iraiders.robot2019.robot.commands;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class ResponsiveMotorCommand extends Command {
  private WPI_TalonSRX motor;
  private double speed;
  private DigitalInput sensor;

  public ResponsiveMotorCommand(WPI_TalonSRX motor, double speed, DigitalInput sensor) {
    this(null, motor, speed, sensor);
  }

  public ResponsiveMotorCommand(Subsystem subsystem, WPI_TalonSRX motor, double speed, DigitalInput sensor) {
    if (subsystem != null) this.requires(subsystem);
    this.motor = motor;
    this.speed = speed;
    this.sensor = sensor;
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
    return sensor.get();
  }
}
