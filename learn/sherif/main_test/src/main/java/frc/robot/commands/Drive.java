package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.helpers.TestableCommand;
import frc.robot.subsystems.DriveTrain;

/**
 * Drive command for a two motor system
 * @author sherif
 */
public class Drive extends TestableCommand {
    private final DriveTrain m_driveTrain;
    private boolean m_finished = false;
    private double m_speed;
    private double m_rot;
    private Joystick m_controller;

    /**
     * Takes in a speed and a rotation for a one time command
     * @param dr The robot's drivetrain
     * @param speed The speed to move at
     * @param rot The rotation to use
     */
    public Drive(DriveTrain dr, double speed, double rot) {

        m_driveTrain = dr;

        m_speed = speed;
        m_rot = rot;

        addRequirements(dr);
    }

    /**
     * Takes in a speed
     * @param dr The robot's drivetrain
     * @param controller The joystick controller to use
     */
    public Drive(DriveTrain dr, Joystick controller) {

        m_driveTrain = dr;
        this.m_controller = controller;


        addRequirements(dr);
    }

    @Override
    public void execute() {
        if (m_controller != null) {
            m_speed = m_controller.getThrottle();
            m_rot = m_controller.getDirectionRadians();
        }
        m_driveTrain.drive(m_speed, m_rot);
        m_finished = true;
    }

    @Override
    public boolean isFinished() {
        return m_finished && (this.m_controller == null);
    }
}
