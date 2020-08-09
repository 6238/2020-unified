package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;

import frc.robot.helpers.TestableCommand;

import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends TestableCommand {
	private final DriveSubsystem m_drive;

	private final Joystick m_leftJoystick;
	private final Joystick m_rightJoystick;

	public DriveCommand(DriveSubsystem drive, Joystick leftJoystick, Joystick rightJoystick) {
        m_drive = drive;
        
        m_leftJoystick = leftJoystick;
        m_rightJoystick = rightJoystick;

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