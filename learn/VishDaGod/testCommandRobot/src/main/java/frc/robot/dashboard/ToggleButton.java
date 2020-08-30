/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.dashboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.Constants.OIConstants;

/**
 * Shuffleboard Boolean ToggleButton Trigger
 */
public class ToggleButton extends Trigger {
    private boolean value;
    private String name;
	private NetworkTableEntry entry;

	public ToggleButton(String name, boolean defaultValue) {
        this.value = defaultValue;
        this.name = name;
		entry = OIConstants.SHUFFLEBOARD_TAB.add(name, defaultValue).withWidget(BuiltInWidgets.kToggleButton)
				.getEntry();
	}

	public NetworkTableEntry getEntry() {
		return entry;
	}

	@Override
	public boolean get() {
		value = entry.getBoolean(value);
		return value;
	}

	public void set(boolean input) {
		value = input;
		entry.setBoolean(value);
    }
    
    public String getName() {
        return name;
    }
}