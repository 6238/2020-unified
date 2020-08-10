package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.interfaces.CANSparkMaxInterface;

public class ShooterController extends SubsystemBase {
    private final CANSparkMaxInterface leftSide;
    private final CANSparkMaxInterface rightSide;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double speed = 1.0;

    public ShooterController(Factory f) {
        this.leftSide = f.getSparkMotor(Constants.SHOOTER_LEFT);
        this.rightSide = f.getSparkMotor(Constants.SHOOTER_RIGHT);
    }


    @Override
    public void periodic() {
        this.leftSide.set(speed);
        this.rightSide.set(-speed);
    }
}
