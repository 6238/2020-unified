package org.iraiders.robot2019.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.iraiders.robot2019.robot.subsystems.IntakeSubsystem;

public class BallIntakeMonitor extends Command {
  private IntakeSubsystem intakeSubsystem;
  private double firstDetectedTime = 0;
  private boolean isIn = false;

  public BallIntakeMonitor(IntakeSubsystem intakeSubsystem){
    this.intakeSubsystem = intakeSubsystem;
  }

  @Override
  protected void execute() {
    if ((!intakeSubsystem.ballIntakeLightSensor.get() && !isIn) || (intakeSubsystem.intakeTalon.getOutputCurrent() >= 100)) {
      if (firstDetectedTime == 0) firstDetectedTime = Timer.getFPGATimestamp();

      if (Timer.getFPGATimestamp() - firstDetectedTime >= .5 && (!isIn || intakeSubsystem.intakeTalon.getOutputCurrent() >= 100)) {
        intakeSubsystem.ballIntakeJointCommand.setIntakeJointPosition(IntakeSubsystem.IntakeJointPosition.UP);
        intakeSubsystem.ballIntakeControlCommand.setBallIntakeState(BallIntakeControlCommand.BallIntakeState.STOPPED);
        isIn = true;
      }
    } else {      firstDetectedTime = 0;
                  isIn = false;
    }
  }

  @Override
  protected boolean isFinished() {
    return false;
  }
}
