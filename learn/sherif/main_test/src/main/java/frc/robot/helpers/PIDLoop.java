package frc.robot.helpers;

import frc.robot.interfaces.ControlLoop;

public class PIDLoop implements ControlLoop {
    public final double P;
    public final double I;
    public final double D;
    private double m_last_time = 0.0;
    private double m_i = 0.0;
    private double m_i_limit = 1.0;
    private double m_previous_error = 0.0;

    public PIDLoop(double p, double i, double d) {
        P = p;
        I = i;
        D = d;
    }

    public PIDLoop(double p, double i, double d, double i_limit) {
        P = p;
        I = i;
        D = d;
        m_i_limit = i_limit;
    }

    public double getLastTime() {
        return m_last_time;
    }

    public void setLastTime(double last_time) {
        m_last_time = last_time;
    }

    @Override
    public void reset() {
        m_i = 0;
        m_previous_error = 0.0;
        m_last_time = 0.0;
    }

    @Override
    public double getDelta(double current_time, double desired_position, double current_position) {
        var delta_time = (current_time - m_last_time);
        var current_error = desired_position - current_position;

        var p = P * current_error;

        this.m_i += this.I * (current_error * delta_time);

        if (this.m_i > m_i_limit) {
            m_i = m_i_limit;
        } else if (this.m_i < -m_i_limit) {
            m_i = -m_i_limit;
        }

        var d = D * (current_error - m_previous_error)/(delta_time);

        m_previous_error = current_error;
        m_last_time = current_time;

        return p + m_i + d;
    }
}
