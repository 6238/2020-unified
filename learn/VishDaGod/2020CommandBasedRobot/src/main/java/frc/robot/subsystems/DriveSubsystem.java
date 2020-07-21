/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.DriveConstants;
import frc.robot.shuffleboard.Dashboard;

//This class is for the West Coast Drive Train based on Arcade Drive
public class DriveSubsystem extends SubsystemBase {
  
  //Speed distortion
  private double insanityFactor;
  private double sensitivityFactor;
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

  //The type of drive that we are using
  private DifferentialDrive robotDrive;


  public DriveSubsystem(Factory talonFactory, Factory factory) { //Should implement joystick factory
    //Keeping it chill
    insanityFactor = DriveConstants.kDefaultValueInsanityFactor;
    sensitivityFactor = DriveConstants.kDefaultValueSensitivityFactor; 

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
  }

  @Override
  public void periodic() {
    insanityFactor = Dashboard.insanityFactorEntry.get();
    sensitivityFactor = Dashboard.sensitivityFactorEntry.get();
  }

  //Choose which drive type to use
  public void drive() {

  }

  //Arcade drive is speed with one joystick, and direction with one joystick
  public void arcadeDrive(double leftJoystickY, double rightJoystickZ) {
    robotDrive.arcadeDrive(leftJoystickY * insanityFactor, rightJoystickZ * sensitivityFactor);
  }

  //Tank Drive is speed with both joysticks
  public void tankDrive(double leftSpeed, double rightSpeed) {
    robotDrive.tankDrive(leftSpeed * insanityFactor, rightSpeed * insanityFactor);
  }

  //Curvature Drive is controlling speed with one and controlling turning speed with another 
  public void curvatureDrive(double ySpeed, double zSpeed, boolean isQuickTurn) {
    robotDrive.curvatureDrive(ySpeed * insanityFactor, zSpeed * insanityFactor, isQuickTurn);
  }

  //When game ends
  public void stop() {
  }
}
