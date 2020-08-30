package frc.robot.commands;

import frc.robot.helpers.TestableInstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeInCommand extends TestableInstantCommand {
	public IntakeInCommand(IntakeSubsystem intake) {
		super(intake::in, intake);
	}
}