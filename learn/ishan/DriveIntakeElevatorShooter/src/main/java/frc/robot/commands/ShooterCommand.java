package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends InstantCommand {
	public ShooterCommand(ShooterSubsystem shooter) {
		super(shooter::run, shooter);
	}
}