package frc.robot.subsystems;

import java.util.*;

public class MotorControllerFactory {
    public ArrayList<MotorController> motorControllers_ = new ArrayList<MotorController>();

    public final MotorController create(int canBusId) {
        motorControllers_.add(new MotorController(canBusId));
        return  motorControllers_.get(motorControllers_.size()-1);
    }

    public ArrayList<MotorController> getMotorControllers() {
        return motorControllers_;
    }

}