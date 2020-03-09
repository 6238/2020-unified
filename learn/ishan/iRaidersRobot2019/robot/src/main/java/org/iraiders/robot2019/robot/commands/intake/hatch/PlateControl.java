package org.iraiders.robot2019.robot.commands.intake.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.iraiders.robot2019.robot.Robot;
import org.iraiders.robot2019.robot.subsystems.IntakeSubsystem;

public class PlateControl extends GenericHatch {
  public PlateControl(IntakeSubsystem intakeSubsystem, DoubleSolenoid solenoid) {
    super(intakeSubsystem, solenoid);
  }
  
  @Override
  public boolean isSafe() {
    return Robot.intakeSubsystem.hatchExtendCommand.getPosition() == IntakeSubsystem.HatchPosition.EXTENDED;
  }
}
