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

    public ADIS16470_IMU imu = new ADIS16470_IMU();
    public static int instanceCount = 0;
    
    public Factory() {
        if (instanceCount > 0) {
            throw new RuntimeException("Only one Factory is allowed per robot.");
        }
        instanceCount++;
    }

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
        return imu;
    }
}