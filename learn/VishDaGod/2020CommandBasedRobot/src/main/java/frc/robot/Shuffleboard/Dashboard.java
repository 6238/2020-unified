package frc.robot.shuffleboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Constants.ShooterConstants;

/**
 * This class has all the NetworkTableEntries used by the entirety of the Robot
 * Code
 * 
 * @author Vishnu Velayuthan
 * @author vishnuvelayuthan@gmail.com
 * @version 1.0
 * @since 1.00
 */
public class Dashboard {

	/**
	 * Drive Subsystem NetworkEntries
	 */
	public static Slider insanityFactorEntry = new Slider("Insanity Factor",
			DriveConstants.kDefaultValueInsanityFactor);
			
	public static Slider sensitivityFactorEntry = new Slider("Sensitivty Factor",
			DriveConstants.kDefaultValueSensitivityFactor);

	public static ToggleButton reverseButton = new ToggleButton("Reverse Toggle",
			DriveConstants.kDefaultReverse);

	public static NetworkTableEntry isQuickTurnDisplay = OIConstants.kTab
			.add("Curvature Quick Turn", DriveConstants.kDefaultIsQuickTurn).getEntry();

	public static Slider shooterSpeedEntry = new Slider("Shooter Speed", 
			ShooterConstants.kDefaultShooterSpeed);

	public static Slider feederSpeedEntry = new Slider("Feeder Speed",
			ShooterConstants.kDefaultFeederSpeed);
}