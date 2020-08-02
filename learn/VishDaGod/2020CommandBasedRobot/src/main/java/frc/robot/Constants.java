/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.Factory;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 * 
 * @author Vishnu Velayuthan
 * @author vishnuvelayuthan@gmail.com
 * @version 1.0
 * @since 1.00
 */
public final class Constants {

	// Factory Object
	private static Factory factory = new Factory();

	/**
	 * These are the Shuffleboard Constants
	 */
	public static final class OIConstants {
		public static final ShuffleboardTab kTab = Shuffleboard.getTab("SmartDashboard");
	}

	/**
	 * These are the Joystick Objects for Driving
	 */
	public static final class JoystickConstants {
		public static final Joystick leftJoystick = factory.createJoystick(0); // Change based on ID needed

        public static final Joystick rightJoystick = factory.createJoystick(1); // Change based on ID needed
        
		public static final int isQuickTurnID = 1; //Change base on ID needed
	}

	// Driving Contstants
	/**
	 * These are the Drive Constants for ID numbers and Default values
	 */
	public static final class DriveConstants {
		public static final double kDefaultValueInsanityFactor = 1;
		public static final double kDefaultValueSensitivityFactor = 1;

		public static final int leftTalon1 = 34; // Change based on ID needed
		public static final int leftTalon2 = 35; // Change based on ID needed
		public static final int leftTalon3 = 36; // Change based on ID needed
		public static final int rightTalon1 = 22; // Change based on ID needed
		public static final int rightTalon2 = 23; // Change based on ID needed
		public static final int rightTalon3 = 24; // Change based on ID needed

		public static final int kDriveModeDefault = 1;

		public static final boolean kDefaultReverse = false;
		public static final boolean kDefaultIsQuickTurn = false;
	}

	/**
	 * These are ID numbers for the Elevator Subsystem
	 */
	public static final class ElevatorConstants {
		public static final int feeder = 6; // Change based on ID Needed
		public static final int backMagazine = 7; // Change based on ID Needed
		public static final int frontMagazine = 8; // Change based on ID Needed
	}
	
	public static final class IntakeConstants {
		
	}
    
	/**
	 * These are numbers for the Shooter Subsystem
	 */
    public static final class ShooterConstants {
        public static final int leftShooter = 9; // Change based on ID Needed
        public static final int rightShooter = 10; // Change based on ID Needed
        public static final int feeder = 11; // Change based on ID Needed
        public static final double kDefaultShooterSpeed = 0;
        public static final double kDefaultFeederSpeed = 0;
        public static final int kDefaultSolenoidForwardChannel = 1;
        public static final int kDefaultSolenoidReverseChannel = 2;
    }
}