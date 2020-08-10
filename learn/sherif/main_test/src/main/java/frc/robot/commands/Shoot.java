package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.ShooterController;

import javax.annotation.Nullable;

public class Shoot extends CommandBase {
    private final ShooterController shooterController;
    @Nullable private Slider speedSlider = null;

    public Shoot(ShooterController shooterController) {
        this.shooterController = shooterController;
    }

    public void useSlider(Factory f) {
        this.speedSlider = f.getSlider("Shooter speed: ", 1.0, 0.0, 1.0);
    }

    @Override
    public void execute() {
        if (this.speedSlider != null) {
            var newSpeed = this.speedSlider.getDouble();
            this.shooterController.setSpeed(newSpeed);
        }
    }
}
