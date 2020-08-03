package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.helpers.TestableCommand;
import frc.robot.subsystems.DriveTrain;

public class Drive extends TestableCommand {
    private final DriveTrain m_driveTrain;
    private boolean m_finished = false;
    private double m_speed;
    private double m_rot;
    private Joystick controller;

    public Drive(DriveTrain dr, double speed, double rot) {

        m_driveTrain = dr;

        m_speed = speed;
        m_rot = rot;

        addRequirements(dr);
    }

    public Drive(DriveTrain dr, Joystick controller) {

        m_driveTrain = dr;
        this.controller = controller;


        addRequirements(dr);
    }

    @Override
    public void execute() {
        if (controller != null) {
            m_speed = controller.getThrottle();
            m_rot = controller.getDirectionRadians();
        }
        m_driveTrain.drive(m_speed, m_rot);
        m_finished = true;
    }

    @Override
    public boolean isFinished() {
        return m_finished;
    }
}
