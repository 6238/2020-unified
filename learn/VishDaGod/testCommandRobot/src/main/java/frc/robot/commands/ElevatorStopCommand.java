package frc.robot.commands;

import frc.robot.helpers.TestableInstantCommand;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorStopCommand extends TestableInstantCommand {
	public ElevatorStopCommand(ElevatorSubsystem elevator) {
		super(elevator::stop, elevator);
	}
}