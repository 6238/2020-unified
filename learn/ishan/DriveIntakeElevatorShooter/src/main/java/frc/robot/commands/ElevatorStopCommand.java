package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorStopCommand extends InstantCommand {
	public ElevatorStopCommand(ElevatorSubsystem elevator) {
		super(elevator::stop, elevator);
	}
}