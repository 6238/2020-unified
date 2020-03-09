package org.iraiders.robot2019.robot.commands.lift;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.RobotMap;
import org.iraiders.robot2019.robot.subsystems.LiftSubsystem;

import static org.iraiders.robot2019.robot.OI.leftAttack;

public class LiftJoystickControl extends Command {
  private final WPI_TalonSRX motor;

  public LiftJoystickControl(LiftSubsystem liftSubsystem) {
    motor = liftSubsystem.liftTalon;
  }

  @Override
  protected void execute(){
    double value;
    value = OI.getDeadband(leftAttack.getY(), .05);
    if (RobotMap.climbTurtle.get()) { value = value/2; }

    if (value != 0) motor.set(Math.copySign(Math.pow(value, 2), value) / 2);
  }


  @Override
  protected boolean isFinished() {
    return false;
  }
}
