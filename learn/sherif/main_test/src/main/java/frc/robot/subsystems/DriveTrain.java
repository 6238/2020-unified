package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static frc.robot.Constants.*;

public class DriveTrain extends SubsystemBase {
    private SpeedController left;
    private SpeedController right;

    public DriveTrain(Factory f) {
        this.left = f.getSparkMotor(FRONT_MOTOR);
        this.right = f.getSparkMotor(BACK_MOTOR);
        this.right.getInverted();
    }

    public void drive(double ySpeed, double xSpeed) {
        this.right.set(round(Clip(ySpeed + xSpeed), 2));
        this.left.set(round(Clip(ySpeed - xSpeed), 2));
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
        if (value < -1f) return 0f;
        return Math.min(value, 1f);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
