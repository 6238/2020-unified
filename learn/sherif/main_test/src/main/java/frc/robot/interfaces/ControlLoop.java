package frc.robot.interfaces;

public interface ControlLoop {
    double getDelta(double current_time, double desired_position, double current_position);
    double getLastTime();
    void setLastTime(double last_time);
    void reset();
}
