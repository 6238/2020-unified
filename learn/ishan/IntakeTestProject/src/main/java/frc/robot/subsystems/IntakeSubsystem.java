/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  WPI_TalonSRX outer;

  WPI_TalonSRX throatLeft;
  WPI_TalonSRX throatRight;

  WPI_TalonSRX elevatorBack;
  WPI_TalonSRX elevatorFront;

  double outerSpeed;
  double throatSpeed;
  double elevatorSpeed;

  /**
   * Creates a new ExampleSubsystem.
   */
  public IntakeSubsystem(TalonFactory f) {
    outer = f.create(Constants.kOuterPort);

    throatLeft = f.create(Constants.kThroatLeftPort);
    throatRight = f.create(Constants.kThroatRightPort);

    elevatorBack = f.create(Constants.kElevatorBackPort);
    elevatorFront = f.create(Constants.kElevatorFrontPort);

    throatRight.follow(throatLeft);
    throatRight.setInverted(true);

    elevatorBack.follow(elevatorFront);
    elevatorBack.setInverted(true);
  }

  public void startOuter() {
    outerSpeed = 0.5;
  }

  public void startThroat() {
    throatSpeed = 0.5;
  }

  public void startElevator() {
    elevatorSpeed = 0.5;
  }
  public void stopOuter() {
    outerSpeed = 0;
  }

  public void stopThroat() {
    throatSpeed = 0;
  }

  public void stopElevator() {
    elevatorSpeed = 0;
  }

  @Override
  public void periodic() {
    outer.set(outerSpeed);

    throatLeft.set(throatSpeed);

    elevatorFront.set(elevatorSpeed);
  }
}
