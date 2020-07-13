package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.IntakeSubsystem;

public class StartIntake extends SequentialCommandGroup {
    public StartIntake(IntakeSubsystem subsystem) {
        addRequirements(subsystem);
        addCommands(
            new StartElevator(subsystem), 
            new StartThroat(subsystem), 
            new StartOuter(subsystem));
    }
}