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
            this.driveTrain.setMaxSpeed(this.maxSpeedSlider.getDouble());
        }

        if (controller != null) {
            driveTrain.drive(-controller.getAxisY(), controller.getTwist());
        } else {
            driveTrain.drive(0, 0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
