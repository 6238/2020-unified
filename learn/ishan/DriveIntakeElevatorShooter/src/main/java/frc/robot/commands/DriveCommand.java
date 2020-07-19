package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants.OIConstants;

import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends CommandBase {
	private final DriveSubsystem m_drive;

	private final Joystick m_leftJoystick = OIConstants.joysticks[0];
	private final Joystick m_rightJoystick = OIConstants.joysticks[1];

	public DriveCommand(DriveSubsystem drive) {
		m_drive = drive;
		addRequirements(m_drive);
	}

	@Override
	public void execute() {
		m_drive.drive(m_leftJoystick.getY(), m_rightJoystick.getZ());
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}