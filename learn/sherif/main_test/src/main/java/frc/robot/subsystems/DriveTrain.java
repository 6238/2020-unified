package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

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

    public DriveTrain(Factory f) {
        this.leftA = f.getTalonMotor(LEFT_MOTOR_A);
        this.leftB = f.getTalonMotor(LEFT_MOTOR_B);
        this.leftC = f.getTalonMotor(LEFT_MOTOR_C);
        this.left = new SpeedControllerGroup(this.leftA, this.leftB, this.leftC);

        this.rightA = f.getTalonMotor(RIGHT_MOTOR_A);
        this.rightB = f.getTalonMotor(RIGHT_MOTOR_B);
        this.rightC = f.getTalonMotor(RIGHT_MOTOR_C);
        this.right = new SpeedControllerGroup(this.rightA, this.rightB, this.rightC);
        this.right.setInverted(true);

        this.differentialDrive = new DifferentialDrive(this.left, this.right);
        this.differentialDrive.setDeadband(0.0);
    }

    public void drive(double xSpeed, double rot) {
        this.differentialDrive.arcadeDrive(xSpeed, rot, false);
    }

    public void brake() {
        this.differentialDrive.tankDrive(0, 0, false);
    }
}
