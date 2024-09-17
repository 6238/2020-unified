package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.helpers.TestableCommand;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends TestableCommand {
    private final ShooterSubsystem shooterSubsystem;
    private Slider speedSlider;

    public ShootCommand(Factory f, ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        speedSlider = f.getSlider("Shooter speed: ", Constants.INITIAL_SHOOTER, 0.0, 1.0);
    }


    @Override
    public void execute() {
        shooterSubsystem.setSpeed(speedSlider.getDouble());
    }
}
