package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeControl extends SubsystemBase {
    public final WPI_TalonSRX m_throat_left;
    public final WPI_TalonSRX m_throat_right;
    public final WPI_TalonSRX m_elevator_left;
    public final WPI_TalonSRX m_elevator_right;
    public final WPI_TalonSRX m_feeder;
    private double m_throat_speed = 0.0;
    private double m_elevator_speed = 0.0;
    private double m_feeder_speed = 0.0;
    private final Solenoid m_solenoid = null;

    public IntakeControl(Factory f) {
        this.m_throat_left = f.getTalonMotor(Constants.THROAT_FRONT);
        this.m_throat_right = f.getTalonMotor(Constants.THROAT_BACK);
        this.m_elevator_left = f.getTalonMotor(Constants.ELEVATOR_FRONT);
        this.m_elevator_right = f.getTalonMotor(Constants.ELEVATOR_BACK);
        this.m_feeder = f.getTalonMotor(Constants.FEEDER);
//        this.m_solenoid = f.getSolenoid(Constants.INTAKE_SOLENOID);
    }

    public void setThroatSpeed(double speed) {
        this.m_throat_speed = speed;
    }

    public void setElevatorSpeed(double speed) {
        this.m_elevator_speed = speed;
    }

    public void setFeederSpeed(double speed) {
        this.m_feeder_speed = speed;
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

    @Override
    public void periodic() {
//        System.out.println("Throat speed: " + this.m_throat_speed);
//        System.out.println("Elevator speed: " + this.m_elevator_speed);
//        System.out.println("Feeder speed: " + this.m_feeder_speed);
        this.m_throat_left.set(this.m_throat_speed);
        this.m_throat_right.set(-this.m_throat_speed);

        this.m_elevator_left.set(this.m_elevator_speed);
        this.m_elevator_right.set(-this.m_elevator_speed);

        this.m_feeder.set(this.m_feeder_speed);
    }
}