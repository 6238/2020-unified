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
    @Nullable private Slider maxSpeedSlider = null;
    private final TestableJoystick controller;

    /**
     * Takes in a speed
     * @param dr The robot's drivetrain
     * @param controller The joystick controller to use
     */
    public Drive(DriveTrain dr, TestableJoystick controller) {

        driveTrain = dr;
        this.controller = controller;


        addRequirements(dr);
    }

    public void useShuffleboard(Factory f) {
        this.maxSpeedSlider = f.getSlider("Max Speed", 1.0, 0.0, 1.0);
    }


    @Override
    public void execute() {
        if (this.maxSpeedSlider != null) {
            var maxSpeed = this.maxSpeedSlider.getDouble();
            this.driveTrain.setMaxSpeed(maxSpeed);
        }

        double speed = 0.0;
        double rot = 0.0;
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
