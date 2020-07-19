package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.Joystick;
import java.util.*;

public class JoystickFactory {
    private ArrayList<Joystick> joysticks = new ArrayList<Joystick>();
    private ArrayList<Integer> joystickIDs = new ArrayList<Integer>(); // Will implement later, after talking to Ishan 

    public final Joystick create(int joystickID) {
        joysticks.add(new Joystick(joystickID));
        return joysticks.get(joysticks.size() - 1);
    }
}