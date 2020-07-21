/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.Shuffleboard.Dashboard;

//This class is for the West Coast Drive Train based on Arcade Drive
public class DriveSubsystem extends SubsystemBase {
  
  //Speed and turn distortion
  private double insanityFactor;
  private double sensitivityFactor;

  private boolean isReverse;
  //Shuffleboard entry for changing the insanity factor

  //All of the motor controllers for the 6 motors on the Gearbox
  private WPI_TalonSRX leftTalon1;
  private WPI_TalonSRX leftTalon2;
  private WPI_TalonSRX leftTalon3;
  private WPI_TalonSRX rightTalon1;
  private WPI_TalonSRX rightTalon2;
  private WPI_TalonSRX rightTalon3;

  //Representing each side of motors
  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;

  //The robot drive 
  private DifferentialDrive robotDrive;

  //Driver can choose which robot drive to choose
  private final SendableChooser<String> driveModeChooser = new SendableChooser<>();
  private ArrayList<String> driveModeOptions = new ArrayList<String>();
  public int driveMode = DriveConstants.kDriveModeDefault;
  private String driveModeSelected;

  public DriveSubsystem(Factory talonFactory, Factory factory) { //Should implement joystick factory
    //Keeping it chill
    insanityFactor = DriveConstants.kDefaultValueInsanityFactor;
    sensitivityFactor = DriveConstants.kDefaultValueSensitivityFactor; 
    isReverse = DriveConstants.kDefaultReverse;

    leftTalon1 = talonFactory.createTalon(DriveConstants.leftTalon1); 
    leftTalon2 = talonFactory.createTalon(DriveConstants.leftTalon2); 
    leftTalon3 = talonFactory.createTalon(DriveConstants.leftTalon3); 
    rightTalon1 = talonFactory.createTalon(DriveConstants.rightTalon1); 
    rightTalon2 = talonFactory.createTalon(DriveConstants.rightTalon1); 
    rightTalon3 = talonFactory.createTalon(DriveConstants.rightTalon1); //Change based on ID needed

    //Left of the robot
    leftMotors = new SpeedControllerGroup(leftTalon1,leftTalon2, leftTalon3); 
    //Right of the robot
    rightMotors = new SpeedControllerGroup(rightTalon1, rightTalon2, rightTalon3);

    robotDrive = new DifferentialDrive(leftMotors, rightMotors);

    //Add the options for the chooser
    driveModeOptions.add("Tank");
    driveModeOptions.add("Arcade");
    driveModeOptions.add("Curvature");

    driveModeChooser.setDefaultOption(driveModeOptions.get(driveMode), driveModeOptions.get(driveMode));

    //Set the options
    for (int i = 0; i < driveModeOptions.size(); i++) {
      if (i != driveMode) {
        driveModeChooser.addOption(driveModeOptions.get(i), driveModeOptions.get(driveMode));
      }
    }

    //Add the sendable chooser to the dashboard
    OIConstants.kTab.add(driveModeChooser);
  }

  @Override
  public void periodic() {
    //Get the factors
    insanityFactor = Dashboard.insanityFactorEntry.get();
    sensitivityFactor = Dashboard.sensitivityFactorEntry.get();
    isReverse = Dashboard.reverseButton.get();

    //Choose the drive mode
    driveModeSelected = driveModeChooser.getSelected();
    driveMode = driveModeOptions.indexOf(driveModeSelected);
    if (driveMode < 0 || driveMode >= driveModeOptions.size())
      driveMode = DriveConstants.kDriveModeDefault;
  }

  //Choose which drive type to use
  public void drive(double tank_leftY, double tank_rightY, double ySpeed, double zSpeed) {
    //When it is in reverse
    if (isReverse) {
      switch (driveMode){
        case 0:
          this.tankDrive(-tank_leftY, -tank_rightY);
        case 1:
          this.arcadeDrive(-ySpeed, zSpeed);
        case 2:
          this.curvatureDrive(-ySpeed, zSpeed, true);
      }
    }
    //This is the NORMAL one
    else {
      switch (driveMode){
        case 0:
          this.tankDrive(tank_leftY, tank_rightY);
        case 1:
          this.arcadeDrive(ySpeed, zSpeed);
        case 2:
          this.curvatureDrive(ySpeed, zSpeed, true);
      }
    }

    

  }

  //Arcade drive is speed with one joystick, and direction with one joystick
  public void arcadeDrive(double leftJoystickY, double rightJoystickZ) {
    robotDrive.arcadeDrive(leftJoystickY * insanityFactor, rightJoystickZ * sensitivityFactor);
  }

  //Tank Drive is speed with both joysticks
  public void tankDrive(double leftY, double rightY) {
    robotDrive.tankDrive(leftY * insanityFactor, rightY * insanityFactor);
  }

  //Curvature Drive is controlling speed with one and controlling turning speed with another 
  public void curvatureDrive(double ySpeed, double zSpeed, boolean isQuickTurn) {
    robotDrive.curvatureDrive(ySpeed * insanityFactor, zSpeed * insanityFactor, isQuickTurn);
  }

  //When game ends
  public void stop() {
  }
}
