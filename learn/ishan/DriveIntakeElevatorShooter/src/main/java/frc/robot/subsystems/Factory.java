package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;

public class Factory {
    public ArrayList<SpeedController> motorControllers_ = new ArrayList<SpeedController>();
    
    public Factory() {};

    public WPI_TalonSRX createTalon(int canBusID) {
        motorControllers_.add(new WPI_TalonSRX(canBusID));
        return (WPI_TalonSRX) motorControllers_.get(motorControllers_.size() - 1);
    }
    
    public CANSparkMax createSpark(int canBusID) {
        motorControllers_.add(new CANSparkMax(canBusID, MotorType.kBrushless));
        return (CANSparkMax) motorControllers_.get(motorControllers_.size() - 1);
    }

    public ArrayList<SpeedController> getMotorControllers() {
        return motorControllers_;
    }
}