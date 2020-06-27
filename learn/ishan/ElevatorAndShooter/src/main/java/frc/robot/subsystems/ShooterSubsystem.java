/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  CANSparkMax m_shooterLeft;
  CANSparkMax m_shooterRight;

  public ShooterSubsystem(Factory f) {
    m_shooterLeft = f.createSpark(Constants.kLeftShooterSpark);
    m_shooterRight = f.createSpark(Constants.kRightShooterSpark);
    m_shooterRight.follow(m_shooterLeft, true);
  }

  public void shooter(double speed) {
    m_shooterLeft.set(speed);
  }
}
