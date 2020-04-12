package frc.robot.subsystems;

import java.util.ArrayList;

public class MotorControllerFactory {
    public ArrayList<MotorController> motorControllers_ = new ArrayList<MotorController>();
    public MotorControllerFactory(){};

    public MotorController create(int canBusID) {
        motorControllers_.add(new MotorController(canBusID));
        return motorControllers_.get(motorControllers_.size() - 1);
    }

    public ArrayList<MotorController> getMotorControllers() {
        return motorControllers_;
    }
}