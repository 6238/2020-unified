package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
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
    private Joystick controller;

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
