package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorUpCommand extends InstantCommand {
	public ElevatorUpCommand(ElevatorSubsystem elevator) {
		super(elevator::up, elevator);
	}
}