package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeInCommand extends InstantCommand {
	public IntakeInCommand(IntakeSubsystem intake) {
		super(intake::in, intake);
	}
}