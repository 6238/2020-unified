package frc.robot.helpers;

import edu.wpi.first.wpilibj.Joystick;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

import frc.robot.commands.DriveCommand;

public class RobotInjection {
    public DriveSubsystem drive;
    public ElevatorSubsystem elevator;
    public IntakeSubsystem intake;
    public ShooterSubsystem shooter;

    public Joystick leftJoystick;
    public Joystick rightJoystick;

    public DriveCommand driveCommand;
}