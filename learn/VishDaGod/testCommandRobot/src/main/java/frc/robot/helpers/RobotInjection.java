package frc.robot.helpers;

import frc.robot.commands.DriveCommand;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotInjection {
    public DriveSubsystem drive;
    public ElevatorSubsystem elevator;
    public IntakeSubsystem intake;
    public ShooterSubsystem shooter;

    public TestableJoystick leftJoystick;
    public TestableJoystick rightJoystick;

    public DriveCommand driveCommand;
}