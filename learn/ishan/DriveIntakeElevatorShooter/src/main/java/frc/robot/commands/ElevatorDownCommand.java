package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorDownCommand extends InstantCommand {
	public ElevatorDownCommand(ElevatorSubsystem elevator) {
		super(elevator::down, elevator);
	}
}