/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

import frc.robot.Constants.OIConstants;

/**
 * Shuffleboard Slider
 */
public class Slider {
	private double value;
	private NetworkTableEntry entry;

	public Slider(String name, double defaultValue) {
		value = defaultValue;
		entry = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", 0, "max", 1)).getEntry();
	}

	public Slider(String name, double defaultValue, double min, double max) {
		value = defaultValue;
		entry = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", min, "max", max)).getEntry();
	}

	public Slider(String name, double defaultValue, int x, int y, int w, int h) {
		value = defaultValue;
		entry = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", 0, "max", 1)).withPosition(x, y).withSize(w, h).getEntry();
	}

	public Slider(String name, double defaultValue, double min, double max, int x, int y, int w, int h) {
		value = defaultValue;
		entry = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", min, "max", max)).withPosition(x, y).withSize(w, h).getEntry();
	}

	public NetworkTableEntry getEntry() {
		return entry;
	}

	public double get() {
		value = entry.getDouble(value);
		return value;
	}

	public void set(double input) {
		value = input;
		entry.setDouble(value);
	}
}