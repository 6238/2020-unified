package frc.robot.commands;

import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.io.Slider;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Factory;

/**
 * Drive command for a two motor system
 * @author sherif
 */
public class DriveCommand extends TestableCommand {
    private final DriveSubsystem driveSubsystem;
    private final Slider maxSpeedSlider;
    private final TestableJoystick controller;

    /**
     * Takes in a speed
     * @param dr The robot's drivetrain
     * @param controller The joystick controller to use
     */
    public DriveCommand(Factory f, DriveSubsystem dr, TestableJoystick controller) {
        driveSubsystem = dr;
        this.controller = controller;

        maxSpeedSlider = f.getSlider("Max Speed", 1.0, 0.0, 1.0);

        addRequirements(dr);
    }


    @Override
    public void execute() {
        if (maxSpeedSlider != null) {
            driveSubsystem.setMaxSpeed(maxSpeedSlider.getDouble());
        }

        if (controller != null) {
            driveSubsystem.drive(-controller.getAxisY(), controller.getTwist());
        } else {
            driveSubsystem.drive(0, 0);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
