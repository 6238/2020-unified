package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import java.util.*;

public class WPI_TalonFactory {
    private ArrayList<WPI_TalonSRX> talons = new ArrayList<WPI_TalonSRX>();

    public  WPI_TalonSRX create(int canBusID) {
        talons.add(new WPI_TalonSRX(canBusID));
        return talons.get(talons.size() - 1);
    }
}