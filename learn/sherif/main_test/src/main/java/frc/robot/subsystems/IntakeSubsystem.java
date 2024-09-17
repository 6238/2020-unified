package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
    public final WPI_TalonSRX throatLeft;
    public final WPI_TalonSRX throatRight;
    public final WPI_TalonSRX elevatorLeft;
    public final WPI_TalonSRX elevatorRight;
    public final WPI_TalonSRX feeder;
    private double throatSpeed = 0.0;
    private double elevatorSpeed = 0.0;
    private double feederSpeed = 0.0;
    private final Solenoid solenoid = null;

    public IntakeSubsystem(Factory f) {
        throatLeft = f.getTalonMotor(Constants.THROAT_FRONT);
        throatRight = f.getTalonMotor(Constants.THROAT_BACK);
        elevatorLeft = f.getTalonMotor(Constants.ELEVATOR_FRONT);
        elevatorRight = f.getTalonMotor(Constants.ELEVATOR_BACK);
        feeder = f.getTalonMotor(Constants.FEEDER);
//        m_solenoid = f.getSolenoid(Constants.INTAKE_SOLENOID);
    }

    public void setThroatSpeed(double speed) {
        throatSpeed = speed;
    }

    public void setElevatorSpeed(double speed) {
        elevatorSpeed = speed;
    }

    public void setFeederSpeed(double speed) {
        feederSpeed = speed;
    }

//    public void activateSolenoid() {
//        solenoid.set(true);
//    }

//    public void deactivateSolenoid() {
//        solenoid.set(false);
//    }

    public void stop() {
        setThroatSpeed(0.0);
        setElevatorSpeed(0.0);
    }

    @Override
    public void periodic() {
//        System.out.println("Throat speed: " + m_throat_speed);
//        System.out.println("Elevator speed: " + m_elevator_speed);
//        System.out.println("Feeder speed: " + m_feeder_speed);
        throatLeft.set(throatSpeed);
        throatRight.set(-throatSpeed);

        elevatorLeft.set(elevatorSpeed);
        elevatorRight.set(-elevatorSpeed);

        feeder.set(feederSpeed);
    }
}