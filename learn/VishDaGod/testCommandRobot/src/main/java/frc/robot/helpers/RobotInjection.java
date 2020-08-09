package frc.robot.helpers;

import frc.robot.commands.DriveCommand;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RobotInjection {
    public DriveSubsystem m_drive;
    public ElevatorSubsystem m_elevator;
    public IntakeSubsystem m_intake;
    public ShooterSubsystem m_shooter;

    public TestableJoystick m_leftJoystick;
    public TestableJoystick m_rightJoystick;

    public DriveCommand m_driveCommand;
}