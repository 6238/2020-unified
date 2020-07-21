package frc.robot.Shuffleboard;


import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.Constants.OIConstants;


/**
 * This class has all creates a button NetworkEntryTable
 * @author Vishnu Velayuthan
 * @author vishnuvelayuthan@gmail.com
 * @version 1.0
 * @since 1.00
 */
public class ToggleButton {
    
    private NetworkTableEntry button;
	private boolean value;


	/**
	 * This Constructor creates a toggle button instead of having to type that long line
	 * @param name The name of the button
	 * @param defaultValue The boolean default value
	 */
	public ToggleButton(String name, boolean defaultValue) {
		button = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kToggleButton).getEntry();
		value = defaultValue;
	}


	/**
	 * Get the button value 
	 * @return The boolean value
	 */
	public boolean get() {
		value = button.getBoolean(value);
		return value;
	}


	/**
	 * Sets the button to a certain value
	 * @param input Boolean value to set the button to
	 */
	public void set(boolean input) {
		value = input;
		button.setBoolean(value);
	}
}