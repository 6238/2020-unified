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

public class ElevatorSubsystem extends SubsystemBase {

  WPI_TalonSRX m_front;
  WPI_TalonSRX m_back;
  WPI_TalonSRX m_feeder;

  /**
   * Creates a new ExampleSubsystem.
   */
  public ElevatorSubsystem(Factory f) {
    m_front = f.createTalon(Constants.kFrontElevatorTalon);
    m_back = f.createTalon(Constants.kRearElevatorTalon);
    m_feeder = f.createTalon(Constants.kFeederTalon);

    m_front.setInverted(true);
    m_feeder.setInverted(true);
  }

  public void front(double speed) {
    m_front.set(speed);
  }
  
  public void back(double speed) {
    m_back.set(speed);
  }
  
  public void feeder(double speed) {
    m_feeder.set(speed);
  }
}
