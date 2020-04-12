package frc.robot.subsystems;

public class MotorController {

    int canBusId_;
    double v_;

    public MotorController(int canBusId) {
        canBusId_ = canBusId;
    }

    public void set(double v) {
        v_ = v;
    }


    public int getCanBusId() {
        return canBusId_;
    }

    public double getSet() {
        return v_;
    }

}