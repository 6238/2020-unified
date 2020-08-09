package frc.robot.commands;

import frc.robot.helpers.TestableInstantCommand;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterStopCommand extends TestableInstantCommand {
	public ShooterStopCommand(ShooterSubsystem shooter) {
		super(shooter::stop, shooter);
	}
}