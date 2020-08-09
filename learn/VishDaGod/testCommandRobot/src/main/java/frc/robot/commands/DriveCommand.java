package frc.robot.commands;

import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.subsystems.DriveSubsystem;

public class DriveCommand extends TestableCommand {
	private final DriveSubsystem m_drive;

	private final TestableJoystick m_leftJoystick;
	private final TestableJoystick m_rightJoystick;

	public DriveCommand(DriveSubsystem drive, TestableJoystick leftJoystick, TestableJoystick rightJoystick) {
        m_drive = drive;
        
        m_leftJoystick = leftJoystick;
        m_rightJoystick = rightJoystick;

		addRequirements(m_drive);
    }

	@Override
	public void execute() {
		m_drive.drive(m_leftJoystick.getJoyY(), m_rightJoystick.getJoyZ());
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}