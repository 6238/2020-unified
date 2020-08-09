package frc.robot.commands;

import frc.robot.helpers.TestableInstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeOutCommand extends TestableInstantCommand {
	public IntakeOutCommand(IntakeSubsystem intake) {
		super(intake::out, intake);
	}
}