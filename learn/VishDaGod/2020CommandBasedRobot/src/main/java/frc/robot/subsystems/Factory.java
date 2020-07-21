package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import java.util.*;

/**
 * This class creates objects so that we can run it through Mockito 
 * @author Vishnu Velayuthan
 * @author vishnuvelayuthan@gmail.com
 * @version 1.0
 * @since 1.00
 */
public class Factory {

	//Motor Controllers
	private ArrayList<WPI_TalonSRX> talons = new ArrayList<WPI_TalonSRX>();
	private ArrayList<CANSparkMax> sparks = new ArrayList<CANSparkMax>();
	private ArrayList<Integer> canBusIDs = new ArrayList<Integer>();

	//Joysticks
	private ArrayList<Joystick> joysticks = new ArrayList<Joystick>();
	private ArrayList<Integer> joystickIDs = new ArrayList<Integer>();

	private ArrayList<JoystickButton> joystickButtons = new ArrayList<JoystickButton>();

	/**
	 * This function creates a Joystick object 
	 * @param joystickID this is the ID of the joystick 
	 * @throws IllegalArgumentException when matches ID of previously created object
	 * @return Joystick object created
	 */
	public final Joystick createJoystick(int joystickID) {
		if (checkID(joystickID, joystickIDs))
			throw new IllegalArgumentException("Repetition of JoystickID");
		joysticks.add(new Joystick(joystickID));
		joystickIDs.add(joystickID);
		return joysticks.get(joysticks.size() - 1);
	}

	public final JoystickButton createJoystickButton(Joystick joystick, int joystickButtonID) {
		joystickButtons.add(new JoystickButton(joystick, joystickButtonID));
		return joystickButtons.get(joystickButtons.size() - 1);
	}


	/**
	 * This function creates a Talon object. 
	 * @param canBusID This is the ID of the Talon
	 * @throws IllegalArgumentException when matches ID of previously created object
	 * @return Talon object created
	 */
	public final WPI_TalonSRX createTalon(int canBusID) {
		if (checkID(canBusID, canBusIDs))
			throw new IllegalArgumentException("Repetition of CanBusID");
		canBusIDs.add(canBusID);
		talons.add(new WPI_TalonSRX(canBusID));
		return talons.get(talons.size() - 1);
	}


	/**
	 * This function creates a Spark object. 
	 * @param canBusID This is the ID of the Spark
	 * @throws IllegalArgumentException when matches ID of previously created object
	 * @return Spark object created
	 */
	public final CANSparkMax createSpark(int canBusID) {
		if (checkID(canBusID, canBusIDs))
			throw new IllegalArgumentException("Repetition of CanBusID");
		canBusIDs.add(canBusID);
		sparks.add(new CANSparkMax(canBusID, MotorType.kBrushless));
		return sparks.get(sparks.size() - 1);
	}


	/**
	 * This function checks to see if a desired ID has already been used
	 * @param newID ID of the new Object to be created
	 * @param list List of IDs to check for duplicates
	 * @return Boolean value of whether there is a duplicate or not
	 */
	public static boolean checkID(int newID, ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == newID)
				return true;
		}
		return false;
	}
}