package frc.robot.subsystems;

import java.util.HashMap;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedController;
import frc.robot.CANSparkFunctions;
import frc.robot.mockablespark;

public class Factory {
    private HashMap<Integer, CANSparkFunctions> _map = new HashMap<>();

    public CANSparkFunctions getMotor(int busID) {
        if (this._map.containsKey(busID)) {
            return this._map.get(busID);
        }
        var controller = new mockablespark(busID, MotorType.kBrushless);
        this._map.put(busID, controller);
        return controller;
    }
}