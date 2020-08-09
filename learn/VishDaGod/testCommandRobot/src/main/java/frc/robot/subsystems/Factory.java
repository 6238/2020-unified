package frc.robot.subsystems;

import java.util.ArrayList;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SpeedController;

import frc.robot.helpers.MockableSparkMax;
import frc.robot.interfaces.CANSparkMaxInterface;

public class Factory {
    public ArrayList<SpeedController> motorControllers = new ArrayList<SpeedController>();

    private ADIS16470_IMU imu;
    
    public Factory() {}

    public WPI_TalonSRX createTalon(int canBusID) {
        motorControllers.add(new WPI_TalonSRX(canBusID));
        return (WPI_TalonSRX) motorControllers.get(motorControllers.size() - 1);
    }
    
    public CANSparkMaxInterface createSpark(int canBusID) {
        motorControllers.add(new MockableSparkMax(canBusID, MotorType.kBrushless));
        return (CANSparkMaxInterface) motorControllers.get(motorControllers.size() - 1);
    }

    public ArrayList<SpeedController> getMotorControllers() {
        return motorControllers;
    }

    public ADIS16470_IMU getIMU() {
        if (imu == null) {
            imu = new ADIS16470_IMU();
        }
        return imu;
    }
}