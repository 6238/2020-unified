package frc.robot.subsystems;

public class MotorController {
    private int canBusID_;
    private double v_;
    
    public MotorController(int canBusID) {
        canBusID_ = canBusID;
    }

    public void set(double v) {
        v_ = v;
    }

    public int getCanBusID() {
        return canBusID_;
    }

    public double getSpeed() {
        return v_;
    }
}