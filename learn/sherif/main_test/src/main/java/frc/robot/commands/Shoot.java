package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.helpers.TestableCommand;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.ShooterController;

import javax.annotation.Nullable;

public class Shoot extends TestableCommand {
    private final ShooterController shooterController;
    @Nullable private Slider speedSlider = null;

    public Shoot(ShooterController shooterController) {
        this.shooterController = shooterController;
    }

    public void useSlider(Factory f) {
        this.speedSlider = f.getSlider("Shooter speed: ", Constants.INITIAL_SHOOTER, 0.0, 1.0);
    }

    @Override
    public void execute() {
        if (this.speedSlider != null) {
            this.shooterController.setSpeed(this.speedSlider.getDouble());
        } else {
            this.shooterController.setSpeed(Constants.INITIAL_SHOOTER);
        }
    }
}
