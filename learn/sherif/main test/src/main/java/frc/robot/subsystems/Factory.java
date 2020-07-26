package frc.robot.subsystems;

import java.util.HashMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;

public class Factory {
    private HashMap<Integer, SpeedController> _map = new HashMap<>();

    public SpeedController getMotor(int busID) {
        if (this._map.containsKey(busID)) {
            return this._map.get(busID);
        }
        var controller = new CANSparkMax(busID, MotorType.kBrushless);
        this._map.put(busID, controller);
        return controller;
    }
}