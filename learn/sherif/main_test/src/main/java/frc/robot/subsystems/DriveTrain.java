package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveTrain extends SubsystemBase {
    private final SpeedController left;
    private final SpeedController right;

    public DriveTrain(Factory f) {
        this.left = f.getSparkMotor(FRONT_MOTOR);
        this.right = f.getSparkMotor(BACK_MOTOR);
        this.right.getInverted();
    }

    public void drive(double ySpeed, double xSpeed) {
        this.right.set((Clip(ySpeed + xSpeed)));
        this.left.set((Clip(ySpeed - xSpeed)));
    }

    public void brake() {
        this.right.set(0.0);
        this.left.set(0.0);
    }

    public static double Clip(double value, double min, double max) {
        if (value < min) return min;
        return Math.min(value, max);
    }

    public static double Clip(double value) {
        if (value < -1.0) return -1.0;
        return Math.min(value, 1.0);
    }
}
