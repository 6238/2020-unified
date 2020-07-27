package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.helpers.PIDLoop;
import frc.robot.interfaces.ControlLoop;

public class StabilizedDriveTrain extends DriveTrain {
    private Timer m_timer;
    private Gyro m_gyro;
    private double m_adjust = 0.0;

    public double getAngle() {
        return m_angle;
    }

    public void setAngle(double m_angle) {
        this.m_angle = m_angle;
        this.m_loop.reset();
        this.m_timer.reset();
    }

    private double m_angle = 0.0;
    private double m_last_time = 0.0;
    private double m_last_speed = 0.0;
    private double m_last_rot = 0.0;
    private ControlLoop m_loop;


    public double getAdjust() {
        return m_adjust;
    }

    private double m_tolerance = 0.0;

    public StabilizedDriveTrain(Factory f, Gyro gyro, Timer time, ControlLoop loop) {
        super(f);
        this.m_gyro = gyro;
        this.m_timer = time;
        this.m_last_time = time.get();
        this.m_loop = loop;
    }

    @Override
    public void drive(double ySpeed, double xSpeed) {
        double adjusted = xSpeed+m_adjust;
        this.m_last_speed = ySpeed;
        this.m_last_rot = xSpeed;
        super.drive(ySpeed, adjusted);
    }

    @Override
    public void periodic() {
        this.m_adjust = this.m_loop.getDelta(m_timer.get(), m_angle, m_gyro.getAngle());

        this.drive(m_last_speed, m_last_rot);

        this.m_last_time = m_timer.get();
    }
}
