package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.subsystems.ShooterSubsystem;

public class ShooterStopCommand extends InstantCommand {
	public ShooterStopCommand(ShooterSubsystem shooter) {
		super(shooter::stop, shooter);
	}
}