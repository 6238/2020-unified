package frc.robot.shuffleboard;

import java.util.Map;

import frc.robot.Constants.OIConstants;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;

/**
 * This class is a NetworkTableEntry type that makes a slider
 * This makes the syntax of the Slider creation much shorter and simpler
 * @author Vishnu Velayuthan
 * @author vishnuvelayuthan@gmail.com
 * @version 1.0
 * @since 1.00
 */
public class Slider {

	private NetworkTableEntry slider;
	private double value;

	/**
	 * This constructs the Slider instead of typing that long line of code
	 * @param name The name of the slider
	 * @param defaultValue The default value of the slider
	 */
	public Slider(String name, double defaultValue) {
		slider = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", 0, "max", 1)).getEntry();
		value = defaultValue;
	}


	/**
	 * This constructor takes more inputs for a more customizable slider
	 * @param name The name of the slider
	 * @param defaultValue The default value of the slider
	 * @param min The minimum of the slider
	 * @param max The maximum of the slider
	 */
	public Slider(String name, double defaultValue, double min, double max) {
		slider = OIConstants.kTab.add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
				.withProperties(Map.of("min", min, "max", max)).getEntry();
		value = defaultValue;
	}

	/**
	 * Gets the value of the slider
	 * @return Value of the slider
	 */
	public double get() {
		value = slider.getDouble(value);
		return value;
	}


	/**
	 * Sets the value of the slider to a desired number
	 * @param input The value to set the slider too
	 */
	public void set(double input) {
		value = input;
		slider.setDouble(value);
	}
}