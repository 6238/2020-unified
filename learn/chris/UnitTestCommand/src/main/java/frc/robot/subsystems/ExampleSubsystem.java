/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {

  int periodicCount_;

  public ExampleSubsystem() {
    periodicCount_ = 0;
  }

  @Override
  public void periodic() {
    periodicCount_++;
  }

  final public int getPeriodicCount() {

    return periodicCount_;

  }

}
