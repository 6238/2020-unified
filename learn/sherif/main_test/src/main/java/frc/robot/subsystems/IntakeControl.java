package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeControl extends SubsystemBase {
    private final PWMTalonSRX throatLeft;
    private final PWMTalonSRX throatRight;
    private final PWMTalonSRX elevatorLeft;
    private final PWMTalonSRX elevatorRight;

    public IntakeControl(Factory f) {
        this.throatLeft = f.getTalonMotor(Constants.THROAT_LEFT);
        this.throatRight = f.getTalonMotor(Constants.THROAT_RIGHT);
        this.elevatorLeft = f.getTalonMotor(Constants.ELEVATOR_LEFT);
        this.elevatorRight = f.getTalonMotor(Constants.ELEVATOR_RIGHT);
    }

    public void setThroatSpeed(double speed) {
        this.throatLeft.set(speed);
        this.throatRight.set(-speed);
    }

    public void setElevatorSpeed(double speed) {
        this.elevatorLeft.set(speed);
        this.elevatorRight.set(speed);
    }

    public void stop() {
        this.setThroatSpeed(0.0);
        this.setElevatorSpeed(0.0);
    }
}