package frc.robot.commands;

import frc.robot.helpers.TestableInstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeStopCommand extends TestableInstantCommand {
	public IntakeStopCommand(IntakeSubsystem intake) {
		super(intake::stop, intake);
	}
}