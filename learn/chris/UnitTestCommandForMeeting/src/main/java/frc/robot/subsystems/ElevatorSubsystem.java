/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
  WPI_TalonSRX front;
  WPI_TalonSRX back;

  double speed;

  public ElevatorSubsystem(MotorControllerFactory m) {
    front = m.create(13);
    back = m.create(16);
  }

  public void start() {
    speed = 0.5;
  }

  public void stop() {
    speed = 0;
  }

  @Override
  public void periodic() {
    front.set(speed);
    back.set(-speed);
  }
}
