package frc.robot.subsystems;

import java.util.HashMap;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.CANSparkFunctions;
import frc.robot.MockableSparkMax;

public class Factory {
    private HashMap<Integer, CANSparkFunctions> _map = new HashMap<>();

    public CANSparkFunctions getMotor(int busID) {
        if (this._map.containsKey(busID)) {
            return this._map.get(busID);
        }
        var controller = new MockableSparkMax(busID, MotorType.kBrushless);
        this._map.put(busID, controller);
        return controller;
    }
}