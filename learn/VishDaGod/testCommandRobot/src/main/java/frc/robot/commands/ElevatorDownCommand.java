package frc.robot.commands;

import frc.robot.helpers.TestableInstantCommand;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorDownCommand extends TestableInstantCommand {
	public ElevatorDownCommand(ElevatorSubsystem elevator) {
		super(elevator::down, elevator);
	}
}