package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class StartElevator extends InstantCommand {
    public StartElevator(IntakeSubsystem subsystem) {
        super(subsystem::startElevator, subsystem);
    }
}