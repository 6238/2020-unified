package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeControl extends SubsystemBase {
    public final WPI_TalonSRX throatLeft;
    public final WPI_TalonSRX throatRight;
    public final WPI_TalonSRX elevatorLeft;
    public final WPI_TalonSRX elevatorRight;
    public final WPI_TalonSRX feeder;
    private double throatSpeed = 0.0;
    private double elevatorSpeed = 0.0;
    private double feederSpeed = 0.0;
    private final Solenoid solenoid = null;

    public IntakeControl(Factory f) {
        this.throatLeft = f.getTalonMotor(Constants.THROAT_FRONT);
        this.throatRight = f.getTalonMotor(Constants.THROAT_BACK);
        this.elevatorLeft = f.getTalonMotor(Constants.ELEVATOR_FRONT);
        this.elevatorRight = f.getTalonMotor(Constants.ELEVATOR_BACK);
        this.feeder = f.getTalonMotor(Constants.FEEDER);
//        this.m_solenoid = f.getSolenoid(Constants.INTAKE_SOLENOID);
    }

    public void setThroatSpeed(double speed) {
        this.throatSpeed = speed;
    }

    public void setElevatorSpeed(double speed) {
        this.elevatorSpeed = speed;
    }

    public void setFeederSpeed(double speed) {
        this.feederSpeed = speed;
    }

//    public void activateSolenoid() {
//        this.solenoid.set(true);
//    }

//    public void deactivateSolenoid() {
//        this.solenoid.set(false);
//    }

    public void stop() {
        this.setThroatSpeed(0.0);
        this.setElevatorSpeed(0.0);
    }

    @Override
    public void periodic() {
//        System.out.println("Throat speed: " + this.m_throat_speed);
//        System.out.println("Elevator speed: " + this.m_elevator_speed);
//        System.out.println("Feeder speed: " + this.m_feeder_speed);
        this.throatLeft.set(this.throatSpeed);
        this.throatRight.set(-this.throatSpeed);

        this.elevatorLeft.set(this.elevatorSpeed);
        this.elevatorRight.set(-this.elevatorSpeed);

        this.feeder.set(this.feederSpeed);
    }
}