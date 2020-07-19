package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import java.util.*;

//This Class is a Talon Factory in case of mocking
//Not sure if I will do this today or something
public class WPI_TalonFactory {

    private ArrayList<WPI_TalonSRX> talons = new ArrayList<WPI_TalonSRX>();
    private ArrayList<Integer> canBusIDs = new ArrayList<Integer>(); // Will implement later, after talking to Ishan 

    public final WPI_TalonSRX create(int canBusID) {
        talons.add(new WPI_TalonSRX(canBusID));
        return talons.get(talons.size() - 1);
    }

}

