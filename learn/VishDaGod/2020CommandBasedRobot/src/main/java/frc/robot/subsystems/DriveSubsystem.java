/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//This class is for the West Coast Drive Train based on Arcade Drive
public class DriveSubsystem extends SubsystemBase {

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

  private SpeedControllerGroup leftMotor;
  private SpeedControllerGroup rightMotor;

  private DifferentialDrive robotDrive;

  private double insanityFactor;

  public DriveSubsystem(WPI_TalonFactory talonFactory, JoystickFactory joystickFactory) { //Should implement joystick factory
    //Joysticks for controlling driving
    leftJoystick = joystickFactory.create(0); //Change based on ID needed
    rightJoystick = joystickFactory.create(1); //Change based on ID needed

    leftTalon1 = talonFactory.create(5); //Change based on ID needed
    leftTalon2 = talonFactory.create(6); //Change based on ID needed
    leftTalon3 = talonFactory.create(7); //Change based on ID needed
    rightTalon1 = talonFactory.create(8); //Change based on ID needed
    rightTalon2 = talonFactory.create(9); //Change based on ID needed
    rightTalon3 = talonFactory.create(69); //Change based on ID needed hehehheheeheh

    //Left of the robot
    leftMotors = new SpeedControllerGroup(leftTalon1,leftTalon2, leftTalon3); 
    //Right of the robot
    rightMotors = new SpeedControllerGroup(rightTalon1, rightTalon2, rightTalon3);

    robotDrive = new DifferentialDrive(leftMotors, rightMotors);
  }

  @Override
  public void periodic() {
    
  }

  public void drive() {
    robotDrive.arcadeDrive(leftJoystick.getY(), rightJoystick.getzRotation());
  }

  //When game ends
  public void stop() {
    robotDrive.arcadeDrive(0,0);
  }
}
