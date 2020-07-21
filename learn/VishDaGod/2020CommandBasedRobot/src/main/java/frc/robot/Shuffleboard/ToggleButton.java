package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.Constants.OIConstants;

/*
This is for a ToggleButton
*/
public class ToggleButton {
    
    private NetworkTableEntry button;
	private boolean value;

	public ToggleButton(String name, boolean defaultValue) {
		button = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kToggleButton).getEntry();
		value = defaultValue;
	}

	public boolean get() {
		value = button.getBoolean(value);
		return value;
	}

	public void set(boolean input) {
		value = input;
		button.setBoolean(value);
	}
}