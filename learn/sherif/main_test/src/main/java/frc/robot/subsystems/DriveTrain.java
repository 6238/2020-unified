package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveTrain extends SubsystemBase {
    private final WPI_TalonSRX m_left_a;
    private final WPI_TalonSRX m_left_b;
    private final WPI_TalonSRX m_left_c;

    private final SpeedControllerGroup left;

    private final WPI_TalonSRX m_right_a;
    private final WPI_TalonSRX m_right_b;
    private final WPI_TalonSRX m_right_c;

    private final SpeedControllerGroup right;

    private final DifferentialDrive differentialDrive;

    public DriveTrain(Factory f) {
        this.m_left_a = f.getTalonMotor(DRIVE_LEFT_MOTOR_A);
        this.m_left_b = f.getTalonMotor(DRIVE_LEFT_MOTOR_B);
        this.m_left_c = f.getTalonMotor(DRIVE_LEFT_MOTOR_C);
        this.left = new SpeedControllerGroup(this.m_left_a, this.m_left_b, this.m_left_c);

        this.m_right_a = f.getTalonMotor(DRIVE_RIGHT_MOTOR_A);
        this.m_right_b = f.getTalonMotor(DRIVE_RIGHT_MOTOR_B);
        this.m_right_c = f.getTalonMotor(DRIVE_RIGHT_MOTOR_C);
        this.right = new SpeedControllerGroup(this.m_right_a, this.m_right_b, this.m_right_c);
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
