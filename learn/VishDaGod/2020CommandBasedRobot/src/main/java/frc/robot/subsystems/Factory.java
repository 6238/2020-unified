package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;

import java.util.*;

//This Class is a Factory in case of mocking
//Not sure if I will do this today or something
public class Factory {

	private ArrayList<WPI_TalonSRX> talons = new ArrayList<WPI_TalonSRX>();
	private ArrayList<CANSparkMax> sparks = new ArrayList<CANSparkMax>();
	// private ArrayList<Integer> canBusIDs = new ArrayList<Integer>();
	private ArrayList<Joystick> joysticks = new ArrayList<Joystick>();
	// private ArrayList<Integer> joystickIDs = new ArrayList<Integer>();

	public final Joystick createJoystick(int joystickID) {
		joysticks.add(new Joystick(joystickID));
		// joystickIDs.add(joystickID);
		return joysticks.get(joysticks.size() - 1);
	}

	public final WPI_TalonSRX createTalon(int canBusID) {
		talons.add(new WPI_TalonSRX(canBusID));
		return talons.get(talons.size() - 1);
	}

	public final CANSparkMax createSpark(int canBusID) {
		sparks.add(new CANSparkMax(canBusID, MotorType.kBrushless));
		return sparks.get(sparks.size() - 1);
	}
}