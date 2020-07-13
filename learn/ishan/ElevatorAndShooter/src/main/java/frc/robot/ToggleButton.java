/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Shuffleboard Boolean ToggleButton Trigger
 */
public class ToggleButton extends Trigger {
    private boolean value;
    private NetworkTableEntry toggleButtonEntry;

    public ToggleButton(String buttonName, boolean defaultValue) {
        value = defaultValue;
        toggleButtonEntry = Constants.kTab.add(buttonName, defaultValue).withWidget(BuiltInWidgets.kToggleButton).getEntry();
    }

    public NetworkTableEntry getEntry() {
        return toggleButtonEntry;
    }

    @Override
    public boolean get() {
        value = toggleButtonEntry.getBoolean(value);
        return value;
    }
}