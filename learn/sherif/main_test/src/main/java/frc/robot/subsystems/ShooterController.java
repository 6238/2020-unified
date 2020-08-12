package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.MockableSparkMax;
import frc.robot.interfaces.CANSparkMaxInterface;

public class ShooterController extends SubsystemBase {
    private final CANSparkMaxInterface leftSide;
    private final CANSparkMaxInterface rightSide;
    private final boolean useFollower;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double speed = 1.0;

    public ShooterController(Factory f) {
        this(f, false);
   }

    public ShooterController(Factory f, boolean useFollower) {
        this.useFollower = useFollower;
        this.leftSide = f.getSparkMotor(Constants.SHOOTER_LEFT);
        this.rightSide = f.getSparkMotor(Constants.SHOOTER_RIGHT);
        if (useFollower) {
            this.rightSide.follow((MockableSparkMax)leftSide, true);
        }
    }


    @Override
    public void periodic() {
        this.leftSide.set(speed);
        if (!useFollower) {
            this.rightSide.set(-speed);
        }
    }
}
