package frc.robot.commands;

import frc.robot.helpers.TestableInstantCommand;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorUpCommand extends TestableInstantCommand {
	public ElevatorUpCommand(ElevatorSubsystem elevator) {
		super(elevator::up, elevator);
	}
}