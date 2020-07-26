package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class Drive extends CommandBase {
    private final DriveTrain m_driveTrain;
    private boolean m_finished = false;
    private double m_speed;
    private double m_rot;

    public Drive(DriveTrain dr, double speed, double rot) {
        m_driveTrain = dr;

        m_speed = speed;
        m_rot = rot;

        addRequirements(dr);
    }

    @Override
    public void execute() {
        m_driveTrain.drive(m_speed, m_rot);
        m_finished = true;
    }

    @Override
    public boolean isFinished() {
        return m_finished;
    }
}
