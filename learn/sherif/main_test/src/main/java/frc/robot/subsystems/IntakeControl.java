package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeControl extends SubsystemBase {
    private final WPI_TalonSRX m_throat_left;
    private final WPI_TalonSRX m_throat_right;
    private final WPI_TalonSRX m_elevator_left;
    private final WPI_TalonSRX m_elevator_right;
    private final Solenoid m_solenoid;

    public IntakeControl(Factory f) {
        this.m_throat_left = f.getTalonMotor(Constants.THROAT_FRONT);
        this.m_throat_right = f.getTalonMotor(Constants.THROAT_BACK);
        this.m_elevator_left = f.getTalonMotor(Constants.ELEVATOR_FRONT);
        this.m_elevator_right = f.getTalonMotor(Constants.ELEVATOR_BACK);
        this.m_solenoid = f.getSolenoid(Constants.INTAKE_SOLENOID);
    }

    public void setThroatSpeed(double speed) {
        this.m_throat_left.set(speed);
        this.m_throat_right.set(-speed);
    }

    public void setElevatorSpeed(double speed) {
        this.m_elevator_left.set(speed);
        this.m_elevator_right.set(speed);
    }

    public void activateSolenoid() {
        this.m_solenoid.set(true);
    }

    public void deactivateSolenoid() {
        this.m_solenoid.set(false);
    }

    public void stop() {
        this.setThroatSpeed(0.0);
        this.setElevatorSpeed(0.0);
    }
}