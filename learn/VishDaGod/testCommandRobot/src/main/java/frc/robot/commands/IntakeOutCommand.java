package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeOutCommand extends InstantCommand {
	public IntakeOutCommand(IntakeSubsystem intake) {
		super(intake::out, intake);
	}
}