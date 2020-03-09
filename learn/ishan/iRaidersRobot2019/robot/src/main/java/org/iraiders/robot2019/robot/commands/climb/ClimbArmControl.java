package org.iraiders.robot2019.robot.commands.climb;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.PIDCommand;
import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.RobotMap;
import org.iraiders.robot2019.robot.subsystems.ClimbSubsystem;

public class ClimbArmControl extends PIDCommand {
  private final ClimbSubsystem cs;
  private CANSparkMax armMotor;
  private boolean inverted;

  public ClimbArmControl(ClimbSubsystem climbSubsystem, CANSparkMax armMotor, boolean inverted) {
    super(.05, 0, 0); // TODO Tune this and max & min range
    this.cs = climbSubsystem;
    this.setInputRange(-1, 1);
    this.getPIDController().setPercentTolerance(5);
    this.armMotor = armMotor;
    this.inverted = inverted;
  }

  @Override
  protected double returnPIDInput() {
    this.setSetpoint(OI.arcadeController.getZ());
    return ((cs.leftLArm.getEncoder().getPosition()+5) // add 5 to change encoder min -5 to 0, and max to 95
      /(95.0/2.0) // set max to 2, min is 0
    )-1; // set max to 1, min to -1
  }

  @Override
  protected void usePIDOutput(double output) {
    if (!RobotMap.climberArmButton.get()) return;
    if (inverted) output *= -1;

    armMotor.set(output);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
