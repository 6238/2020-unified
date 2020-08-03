package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class TalonFactory {
    public ArrayList<WPI_TalonSRX> motorControllers_ = new ArrayList<WPI_TalonSRX>();
    
    public TalonFactory() {};

    public WPI_TalonSRX create(int canBusID) {
        motorControllers_.add(new WPI_TalonSRX(canBusID));
        return motorControllers_.get(motorControllers_.size() - 1);
    }

    public ArrayList<WPI_TalonSRX> getMotorControllers() {
        return motorControllers_;
    }
}