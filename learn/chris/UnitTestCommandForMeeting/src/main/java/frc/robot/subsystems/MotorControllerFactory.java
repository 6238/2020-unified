package frc.robot.subsystems;

import java.util.*;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MotorControllerFactory {
    public WPI_TalonSRX create(int canBusId) {
        return new WPI_TalonSRX(canBusId);
    }
}