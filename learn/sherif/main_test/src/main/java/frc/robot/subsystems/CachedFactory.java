package frc.robot.subsystems;

import java.util.HashMap;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.interfaces.CANSparkMaxInterface;
import frc.robot.helpers.MockableSparkMax;

public class CachedFactory extends Factory {
    private HashMap<Integer, CANSparkMaxInterface> _map = new HashMap<>();

    @Override
    public CANSparkMaxInterface getSparkMotor(int busID) {
        if (!this._map.containsKey(busID)) {
            var controller = new MockableSparkMax(busID, MotorType.kBrushless);
            this._map.put(busID, controller);
        }
        return this._map.get(busID);
    }
}