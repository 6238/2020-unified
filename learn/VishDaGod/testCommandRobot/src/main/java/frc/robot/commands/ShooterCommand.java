package frc.robot.commands;

import frc.robot.helpers.TestableInstantCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends TestableInstantCommand {
	public ShooterCommand(ShooterSubsystem shooter) {
		super(shooter::run, shooter);
	}
}