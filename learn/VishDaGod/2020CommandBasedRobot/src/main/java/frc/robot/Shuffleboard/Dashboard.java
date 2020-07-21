package frc.robot.Shuffleboard;

import frc.robot.Constants.DriveConstants;

public class Dashboard {
    //Drive Subsystem Entries
    public static Slider insanityFactorEntry = new Slider("Insanity Factor", DriveConstants.kDefaultValueInsanityFactor);
    public static Slider sensitivityFactorEntry = new Slider("Sensitivty Factor", DriveConstants.kDefaultValueSensitivityFactor);
}