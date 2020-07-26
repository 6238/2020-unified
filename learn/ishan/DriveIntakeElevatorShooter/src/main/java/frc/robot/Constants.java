/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.analog.adis16470.frc.ADIS16470_IMU;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
	public static final class Drive {
		public static final int kLeftTalon1 = 36; //
		public static final int kLeftTalon2 = 35; //
		public static final int kLeftTalon3 = 34; //

		public static final int kRightTalon1 = 24; //
		public static final int kRightTalon2 = 23; //
		public static final int kRightTalon3 = 22; //

		public static final ADIS16470_IMU kIMU = new ADIS16470_IMU();

		public static final int kPIDLoopIdx = 0;
	}

	public static final class Intake {
		public static final int kIntakeOuterTalon = 4; //
		public static final int kIntakeLeftTalon = 5; //
		public static final int kIntakeRightTalon = 6; //

		public static final int[] kIntakeSolenoid = { 0, 1 };

		public static final double kStartingInnerSpeed = 0.5;
		public static final double kStartingOuterSpeed = 0.5;
	}

	public static final class Elevator {
		public static final int kRearElevatorTalon = 11;
		public static final int kFrontElevatorTalon = 12;

		public static final int kFeederTalon = 13;

		public static final double kStartingSpeed = 0.5;
		public static final double kStartingFeederSpeed = 0.5;
	}

	public static final class Shooter {
		public static final int kLeftShooterSpark = 7;
		public static final int kRightShooterSpark = 6;

		public static final double kStartingSpeed = 0.5;
	}

	public static final class OIConstants {
		private static final int kLeftJoystickPort = 0;
		private static final int kRightJoystickPort = 1;

		// The joysticks
		private static final Joystick kLeftJoystick = new Joystick(kLeftJoystickPort);
		private static final Joystick kRightJoystick = new Joystick(kRightJoystickPort);

		public static final Joystick[] joysticks = { kLeftJoystick, kRightJoystick };

		public static final int[] kCurvatureDriveQuickTurnToggle = { 1, 2 };

		public static final int[] kIntakeToggle = { 0, 1, 2 };

		public static final int[] kShooterToggle = { 1, 1 };

		public static final int[] kElevatorToggle = { 0, 5, 3 };

		private static final NetworkTableInstance kInst = NetworkTableInstance.getDefault();
		public static final NetworkTable kTable = kInst.getTable("SmartDashboard");
		public static final ShuffleboardTab kTab = Shuffleboard.getTab("SmartDashboard");
	}

	public static final int kTimeoutMs = 0;
}
