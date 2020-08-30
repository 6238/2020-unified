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

public class ShooterController extends SubsystemBase {
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



    public ShooterController(Factory f) {
        this(f, false);
   }

    public ShooterController(Factory f, boolean useFollower) {
        this.useFollower = useFollower;
        this.leftSide = f.getSparkMotor(Constants.SHOOTER_LEFT);
        this.rightSide = f.getSparkMotor(Constants.SHOOTER_RIGHT);
        if (useFollower) {
            this.rightSide.follow((MockableSparkMax)leftSide, true);
        }
    }

    public void usePID(Factory f) {
        this.sliders[0] = f.getSlider("P", 0.0, 0.0, 1.0);
        this.sliders[1] = f.getSlider("I", 0.0, 0.0, 1.0);
        this.sliders[2] = f.getSlider("D", 0.0, 0.0, 1.0);
        this.sliders[3] = f.getSlider("IZone", 0.0, 0.0, 1.0);
        this.sliders[4] = f.getSlider("FF", 0.0, 0.0, 1.0);
        this.rpmInfo = f.getInfo("Current RPM", 0.0);
        this.usePID = true;
        this.leftEncoder = this.leftSide.getEncoder();
        this.pidController = this.leftSide.getPIDController();
    }

    private void readSliders() {
        if (sliders[0] != null && sliders[0].getDouble() != this.p) {
            this.p = sliders[0].getDouble();
            pidController.setP(this.p);
        }
        if (sliders[1] != null && sliders[1].getDouble() != this.i) {
            this.i = sliders[1].getDouble();
            pidController.setI(this.i);
        }
        if (sliders[2] != null && sliders[2].getDouble() != this.d) {
            this.d = sliders[2].getDouble();
            pidController.setD(this.d);
        }
        if (sliders[3] != null && sliders[3].getDouble() != this.iRange) {
            this.iRange = sliders[3].getDouble();
            pidController.setIZone(this.iRange);
        }
        if (sliders[4] != null && sliders[4].getDouble() != this.ff) {
            this.ff = sliders[4].getDouble();
            pidController.setFF(this.ff);
        }

        rpmInfo.setOutput(leftEncoder.getVelocity());
    }

    @Override
    public void periodic() {
        this.readSliders();

        if (!usePID) {
            this.leftSide.set(speed);
            if (!useFollower) {
                this.rightSide.set(-speed);
            }
        } else {
            pidController.setReference(target, ControlType.kVelocity);
        }
    }
}
