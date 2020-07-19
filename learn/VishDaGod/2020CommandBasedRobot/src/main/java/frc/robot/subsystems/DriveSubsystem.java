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
  

  //Joysticks to control robot
  private Joystick leftJoystick;
  private Joystick rightJoystick;

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
    sensitivityFactor = DriveConstants.kDefaultValueInsanityFactor; 

    //Joysticks for controlling driving
    leftJoystick = factory.createJoystick(0); //Change based on ID needed
    rightJoystick = factory.createJoystick(1); //Change based on ID needed

    leftTalon1 = talonFactory.createTalon(5); //Change based on ID needed
    leftTalon2 = talonFactory.createTalon(6); //Change based on ID needed
    leftTalon3 = talonFactory.createTalon(7); //Change based on ID needed
    rightTalon1 = talonFactory.createTalon(8); //Change based on ID needed
    rightTalon2 = talonFactory.createTalon(9); //Change based on ID needed
    rightTalon3 = talonFactory.createTalon(69); //Change based on ID needed hehehheheeheh

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

  public void arcadeDrive() {
    robotDrive.arcadeDrive(leftJoystick.getY() * insanityFactor, rightJoystick.getZ() * sensitivityFactor);
  }

  //When game ends
  public void stop() {
  }
}
