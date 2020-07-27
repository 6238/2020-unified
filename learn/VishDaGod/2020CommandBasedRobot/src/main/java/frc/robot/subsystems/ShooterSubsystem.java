/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */

  //Left Shooter Motors
  private final CANSparkMax leftShooter;
  private final CANEncoder leftShooterEncoder;

  //Right Shooter Motors
  private final CANSparkMax rightShooter;
  private final CANEncoder rightShooterEncoder;


  private final WPI_TalonSRX feeder;


  
  public ShooterSubsystem(Factory factory) {
    leftShooter = factory.createSpark(ShooterConstants.leftShooter);
    leftShooterEncoder = leftShooter.getEncoder();

    rightShooter = factory.createSpark(ShooterConstants.rightShooter);
    rightShooterEncoder = rightShooter.getEncoder();

    feeder = factory.createTalon(ShooterConstants.feeder);

    //Restore to factory settings
    leftShooter.restoreFactoryDefaults();
    rightShooter.restoreFactoryDefaults();
    feeder.configFactoryDefault();


    //Set left as follower of right
    leftShooter.follow(rightShooter, true);


  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
