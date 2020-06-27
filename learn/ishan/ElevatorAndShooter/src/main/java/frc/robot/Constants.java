/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int kRearElevatorTalon = 11;
    public static final int kFrontElevatorTalon = 12;

    public static final int kFeederTalon = 13;

    public static final int kLeftShooterSpark = 7;
    public static final int kRightShooterSpark = 6;

    /**
     * Get the default instance of NetworkTables that was created automatically when
     * your program starts
     */
    private static final NetworkTableInstance kInst = NetworkTableInstance.getDefault();

    /*
     * Get the table within that instance that contains the data. There can be as
     * many tables as you like and exist to make it easier to organize your data. In
     * this case, it's a table called datatable.
     */
    public static final NetworkTable kTable = kInst.getTable("SmartDashboard");

    public static final ShuffleboardTab kTab = Shuffleboard.getTab("SmartDashboard");
}
