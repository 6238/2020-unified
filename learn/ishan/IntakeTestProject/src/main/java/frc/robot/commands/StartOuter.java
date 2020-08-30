package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.IntakeSubsystem;

public class StartOuter extends InstantCommand {
    public StartOuter(IntakeSubsystem subsystem) {
        super(subsystem::startOuter, subsystem);
    }
}