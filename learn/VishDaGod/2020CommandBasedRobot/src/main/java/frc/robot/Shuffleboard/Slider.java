package frc.robot.Shuffleboard;

import java.util.Map;

import frc.robot.Constants.OIConstants;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

public class Slider {

	private NetworkTableEntry slider;
	private double value;

	public Slider(String name, double defaultValue) {
		slider = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", 0, "max", 1)).getEntry();
		value = defaultValue;
	}

	public Slider(String name, double defaultValue, double min, double max) {
		slider = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", min, "max", max)).getEntry();
		value = defaultValue;
	}

	public double get() {
		value = slider.getDouble(value);
		return value;
	}

	public void set(double input) {
		value = input;
		slider.setDouble(value);
	}
}