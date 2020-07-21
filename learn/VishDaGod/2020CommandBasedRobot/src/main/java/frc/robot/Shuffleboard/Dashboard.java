package frc.robot.shuffleboard;

import frc.robot.Constants.DriveConstants;

public class Dashboard {
	// Drive Subsystem Entries
	public static Slider insanityFactorSlider = new Slider("Insanity Factor",
			DriveConstants.kDefaultValueInsanityFactor);
	public static Slider sensitivityFactorSlider = new Slider("Sensitivty Factor",
			DriveConstants.kDefaultValueSensitivityFactor);
	public static ToggleButton reverseButton = new ToggleButton("Reverse Toggle", DriveConstants.kDefaultReverse);
}