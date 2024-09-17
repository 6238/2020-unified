package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.helpers.MockableSparkMax;
import frc.robot.interfaces.CANSparkMaxInterface;
import frc.robot.io.Info;
import frc.robot.io.Slider;

public class ShooterSubsystem extends SubsystemBase {
    private final CANSparkMaxInterface leftSide;
    private final CANSparkMaxInterface rightSide;
    private final boolean useFollower;
    private double speed = Constants.INITIAL_SHOOTER;
    private CANEncoder leftEncoder;
    private CANPIDController pidController;

    private Slider sliders[] = {null, null, null, null, null};
    private Info rpmInfo = null;

    private double p;
    private double i;
    private double d;
    private double iRange;
    private double ff;

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    private double target;

    private boolean usePID = false;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }



    public ShooterSubsystem(Factory f) {
        this(f, false);
   }

    public ShooterSubsystem(Factory f, boolean useFollower) {
        this.useFollower = useFollower;
        leftSide = f.getSparkMotor(Constants.SHOOTER_LEFT);
        rightSide = f.getSparkMotor(Constants.SHOOTER_RIGHT);
        if (useFollower) {
            rightSide.follow((MockableSparkMax)leftSide, true);
        }
    }

    public void usePID(Factory f) {
        sliders[0] = f.getSlider("P", 0.0, 0.0, 1.0);
        sliders[1] = f.getSlider("I", 0.0, 0.0, 1.0);
        sliders[2] = f.getSlider("D", 0.0, 0.0, 1.0);
        sliders[3] = f.getSlider("IZone", 0.0, 0.0, 1.0);
        sliders[4] = f.getSlider("FF", 0.0, 0.0, 1.0);
        rpmInfo = f.getInfo("Current RPM", 0.0);
        usePID = true;
        leftEncoder = leftSide.getEncoder();
        pidController = leftSide.getPIDController();
    }

    private void readSliders() {
        if (sliders[0] != null && sliders[0].getDouble() != p) {
            p = sliders[0].getDouble();
            pidController.setP(p);
        }
        if (sliders[1] != null && sliders[1].getDouble() != i) {
            i = sliders[1].getDouble();
            pidController.setI(i);
        }
        if (sliders[2] != null && sliders[2].getDouble() != d) {
            d = sliders[2].getDouble();
            pidController.setD(d);
        }
        if (sliders[3] != null && sliders[3].getDouble() != iRange) {
            iRange = sliders[3].getDouble();
            pidController.setIZone(iRange);
        }
        if (sliders[4] != null && sliders[4].getDouble() != ff) {
            ff = sliders[4].getDouble();
            pidController.setFF(ff);
        }

        if (sliders[4] != null) {
            rpmInfo.setOutput(leftEncoder.getVelocity());
        }
    }

    @Override
    public void periodic() {
        readSliders();

        if (!usePID) {
            leftSide.set(speed);
            if (!useFollower) {
                rightSide.set(-speed);
            }
        } else {
            pidController.setReference(target, ControlType.kVelocity);
        }
    }
}
