/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

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
		public static final int DRIVE_LEFT_TALON_1_ID = 36; //
		public static final int DRIVE_LEFT_TALON_2_ID = 35; //
		public static final int DRIVE_LEFT_TALON_3_ID = 34; //

		public static final int DRIVE_RIGHT_TALON_1_ID = 24; //
		public static final int DRIVE_RIGHT_TALON_2_ID = 23; //
		public static final int DRIVE_RIGHT_TALON_3_ID = 22; //

		public static final int DRIVE_PID_LOOP_IDX = 0;
	}

	public static final class Intake {
		public static final int INTAKE_OUTER_TALON_ID = 4; //
		public static final int INTAKE_LEFT_TALON_ID = 5; //
		public static final int INTAKE_RIGHT_TALON_ID = 6; //

		public static final int[] INTAKE_SOLENOID_ID = { 0, 1 };

		public static final double INTAKE_STARTING_INNER_SPEED = 0.5;
		public static final double INTAKE_STARTING_OUTER_SPEED = 0.5;
	}

	public static final class Elevator {
		public static final int ELEVATOR_REAR_TALON_ID = 11;
		public static final int ELEVATOR_FRONT_TALON_ID = 12;

		public static final int FEEDER_TALON_ID = 13;

		public static final double ELEVATOR_STARTING_SPEED = 0.5;
		public static final double FEEDER_STARTING_SPEED = 0.5;
	}

	public static final class Shooter {
		public static final int SHOOTER_LEFT_SPARK_ID = 7;
		public static final int SHOOTER_RIGHT_SPARK_ID = 6;

		public static final double SHOOTER_STARTING_SPEED = 0.5;
	}

	public static final class OIConstants {
		public static final int JOYSTICK_LEFT_PORT = 0;
		public static final int JOYSTICK_RIGHT_PORT = 1;

		public static final int[] CURVATURE_DRIVE_QUICK_TURN_TOGGLE = { 1, 2 };

		public static final int[] INTAKE_TOGGLE = { 0, 1, 2 };

		public static final int[] SHOOTER_TOGGLE = { 1, 1 };

		public static final int[] ELEVATOR_TOGGLE = { 0, 5, 3 }; //TODO: turn this into 3 separate variables

		private static final NetworkTableInstance NETWORK_TABLE_INSTANCE = NetworkTableInstance.getDefault();
		public static final NetworkTable NETWORK_TABLE = NETWORK_TABLE_INSTANCE.getTable("SmartDashboard");
		public static final ShuffleboardTab SHUFFLEBOARD_TAB = Shuffleboard.getTab("SmartDashboard");
	}

	public static final int TIMEOUT_MS = 0;
}
