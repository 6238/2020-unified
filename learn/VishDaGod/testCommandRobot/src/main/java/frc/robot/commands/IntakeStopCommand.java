package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeStopCommand extends InstantCommand {
	public IntakeStopCommand(IntakeSubsystem intake) {
		super(intake::stop, intake);
	}
}