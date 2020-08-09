package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveTrain extends SubsystemBase {
    private final WPI_TalonSRX leftA;
    private final WPI_TalonSRX leftB;
    private final WPI_TalonSRX leftC;

    private final SpeedControllerGroup left;

    private final WPI_TalonSRX rightA;
    private final WPI_TalonSRX rightB;
    private final WPI_TalonSRX rightC;

    private final SpeedControllerGroup right;

    private final DifferentialDrive differentialDrive;

    private double xSpeed = 0.0;
    private double rot = 0.0;

    public DriveTrain(Factory f) {
        this.leftA = f.getTalonMotor(DRIVE_LEFT_MOTOR_A);
        this.leftB = f.getTalonMotor(DRIVE_LEFT_MOTOR_B);
        this.leftC = f.getTalonMotor(DRIVE_LEFT_MOTOR_C);
        this.left = new SpeedControllerGroup(this.leftA, this.leftB, this.leftC);

        this.rightA = f.getTalonMotor(DRIVE_RIGHT_MOTOR_A);
        this.rightB = f.getTalonMotor(DRIVE_RIGHT_MOTOR_B);
        this.rightC = f.getTalonMotor(DRIVE_RIGHT_MOTOR_C);
        this.right = new SpeedControllerGroup(this.rightA, this.rightB, this.rightC);

        this.differentialDrive = new DifferentialDrive(this.left, this.right);
    }

    public void drive(double xSpeed, double rot) {
        this.xSpeed = xSpeed;
        this.rot = rot;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.differentialDrive.setMaxOutput(maxSpeed);
    }

    @Override
    public void periodic() {
        this.differentialDrive.arcadeDrive(this.xSpeed,this.rot, false);
    }

    public void brake() {
        this.differentialDrive.tankDrive(0.0, 0.0, false);
    }
}
