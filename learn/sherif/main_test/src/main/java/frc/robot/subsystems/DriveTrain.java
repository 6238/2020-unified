package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class DriveTrain extends SubsystemBase {
    private final SpeedController leftFront;
    private final SpeedController leftMid;
    private final SpeedController leftEnd;

    private final SpeedControllerGroup left;

    private final SpeedController rightFront;
    private final SpeedController rightMid;
    private final SpeedController rightEnd;

    private final SpeedControllerGroup right;

    private final DifferentialDrive differentialDrive;

    public DriveTrain(Factory f) {
        this.leftFront = f.getTalonMotor(LEFT_FRONT);
        this.leftMid = f.getTalonMotor(LEFT_MID);
        this.leftEnd = f.getTalonMotor(LEFT_END);
        this.left = new SpeedControllerGroup(this.leftFront, this.leftMid, this.leftEnd);

        this.rightFront = f.getTalonMotor(RIGHT_FRONT);
        this.rightMid = f.getTalonMotor(RIGHT_MID);
        this.rightEnd = f.getTalonMotor(RIGHT_END);
        this.right = new SpeedControllerGroup(this.rightFront, this.rightMid, this.rightEnd);
        this.right.setInverted(true);

        this.differentialDrive = new DifferentialDrive(this.left, this.right);
        this.differentialDrive.setDeadband(0.0);
    }

    public void drive(double xSpeed, double rot) {
        this.differentialDrive.tankDrive(xSpeed-rot, xSpeed+rot, false);
    }

    public void brake() {
        this.differentialDrive.tankDrive(0, 0, false);
    }
}
