package frc.robot.commands;

import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.io.Slider;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Factory;

import javax.annotation.Nullable;

/**
 * Drive command for a two motor system
 * @author sherif
 */
public class Drive extends TestableCommand {
    private final DriveTrain driveTrain;
    private double speed;
    private double rot;
    private double maxSpeed = 1.0;
    @Nullable private Slider maxSpeedSlider = null;
    private TestableJoystick controller;

    /**
     * Takes in a speed and a rotation for a one time command
     * @param dr The robot's drivetrain
     * @param speed The speed to move at
     * @param rot The rotation to use
     */
    public Drive(DriveTrain dr, double speed, double rot) {

        driveTrain = dr;

        this.speed = speed; this.rot = rot;

        addRequirements(dr);
    }

    /**
     * Takes in a speed
     * @param dr The robot's drivetrain
     * @param controller The joystick controller to use
     */
    public Drive(DriveTrain dr, TestableJoystick controller) {

        driveTrain = dr;
        this.controller = controller;

        this.speed = 0.0;
        this.rot = 0.0;

        addRequirements(dr);
    }

    public void useShuffleboard(Factory f) {
        this.maxSpeedSlider = f.getSlider("Max Speed", 1.0, 0.0, 1.0);
    }


    @Override
    public void execute() {
        if (this.maxSpeedSlider != null) {
            this.maxSpeed = this.maxSpeedSlider.getDouble(maxSpeed);
            this.driveTrain.setMaxSpeed(this.maxSpeed);
        }

        if (controller != null) {
            speed = -controller.getAxisY();
            rot = controller.getTwist();
        }
        driveTrain.drive(speed, rot);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
