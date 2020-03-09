package org.iraiders.robot2019.robot.commands.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.subsystems.IntakeSubsystem;

import static org.iraiders.robot2019.robot.commands.intake.BallIntakeControlCommand.BallIntakeState.IN;
import static org.iraiders.robot2019.robot.commands.intake.BallIntakeControlCommand.BallIntakeState.STOPPED;

public class BallIntakeJointCommand {
  private IntakeSubsystem intakeSubsystem;
  private IntakeSubsystem.IntakeJointPosition position = IntakeSubsystem.IntakeJointPosition.UP;

 public BallIntakeJointCommand(IntakeSubsystem intakeSubsystem) {
   this.intakeSubsystem = intakeSubsystem;
   update();
 }

 private void update() {
   switch(position) {
     default:
     case UP:
       intakeSubsystem.ballIntakeSolenoid.set(DoubleSolenoid.Value.kReverse);
       intakeSubsystem.ballIntakeControlCommand.setBallIntakeState(STOPPED);
       break;

     case DOWN:
       intakeSubsystem.ballIntakeSolenoid.set(DoubleSolenoid.Value.kForward);
       intakeSubsystem.ballIntakeControlCommand.setBallIntakeState(IN);
       break;
   }
   SmartDashboard.putString("IntakeJointState", position.name());
 }

  public IntakeSubsystem.IntakeJointPosition getIntakeJointPosition(){
   return position;
  }

  public boolean setIntakeJointPosition(IntakeSubsystem.IntakeJointPosition ijp){
    if (intakeSubsystem.hatchExtendCommand.getPosition() == IntakeSubsystem.HatchPosition.EXTENDED) {
      DriverStation.reportWarning("BallIntake not changed for safety reasons", false);
      return false;
    }
    position = ijp;
    update();
    return true;
  }
}
