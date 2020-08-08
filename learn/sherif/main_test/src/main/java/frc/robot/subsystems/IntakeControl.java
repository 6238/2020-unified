package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeControl extends SubsystemBase {
    private final PWMTalonSRX m_throat_left;
    private final PWMTalonSRX m_throat_right;
    private final PWMTalonSRX m_elevator_left;
    private final PWMTalonSRX m_elevator_right;

    public IntakeControl(Factory f) {
        this.m_throat_left = f.getTalonMotor(Constants.THROAT_LEFT);
        this.m_throat_right = f.getTalonMotor(Constants.THROAT_RIGHT);
        this.m_elevator_left = f.getTalonMotor(Constants.ELEVATOR_LEFT);
        this.m_elevator_right = f.getTalonMotor(Constants.ELEVATOR_RIGHT);
    }

    public void setThroatSpeed(double speed) {
        this.m_throat_left.set(speed);
        this.m_throat_right.set(-speed);
    }

    public void setElevatorSpeed(double speed) {
        this.m_elevator_left.set(speed);
        this.m_elevator_right.set(speed);
    }

    public void stop() {
        this.setThroatSpeed(0.0);
        this.setElevatorSpeed(0.0);
    }
}