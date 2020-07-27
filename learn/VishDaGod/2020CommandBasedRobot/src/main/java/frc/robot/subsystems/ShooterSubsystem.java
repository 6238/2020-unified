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

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.shuffleboard.Dashboard;

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

  //Feeder wheel
  private final WPI_TalonSRX feeder;

  //Double Solenoid for Retracting and Extending
  private final DoubleSolenoid shooterSolenoid;

  //Speed of Motors
  private double shooterSpeed;
  private double feederSpeed;
  

  public ShooterSubsystem(Factory factory) {
    leftShooter = factory.createSpark(ShooterConstants.leftShooter);
    leftShooterEncoder = leftShooter.getEncoder();

    rightShooter = factory.createSpark(ShooterConstants.rightShooter);
    rightShooterEncoder = rightShooter.getEncoder();

    feeder = factory.createTalon(ShooterConstants.feeder);

    shooterSolenoid = new DoubleSolenoid(ShooterConstants.kDefaultSolenoidForwardChannel, 
        ShooterConstants.kDefaultSolenoidReverseChannel);
    
    //Restore to factory settings
    leftShooter.restoreFactoryDefaults();
    rightShooter.restoreFactoryDefaults();
    feeder.configFactoryDefault();

    //Set left as follower of right
    leftShooter.follow(rightShooter, true);



  }

  @Override
  public void periodic() {
    //Get the speeds of the shooters
    shooterSpeed = Dashboard.shooterSpeedEntry.get();
    feederSpeed = Dashboard.feederSpeedEntry.get();
  }

  /**
   * Function run whne need to stop the shooter
   */
  public void stop() {
    rightShooter.set(0);
  }

  /**
   * Accesses the shooterSpeed variable of the class and sets shooter to that speed
   */
  public void start() {
    rightShooter.set(shooterSpeed);
  }

  /**
   * Accesses the feederSpeed variable of the class and sets feeder to that speed
   */
  public void runFeeder() {
    feeder.set(feederSpeed);
  }

  /**
   * Stops the feeder wheel
   */
  public void stopFeeder() {
    feeder.set(0);
  }

  /**
   * This extends our shooter using the solenoid
   */
  public void extendShooter() {
    shooterSolenoid.set(Value.kForward);
  }

  /**
   * This retract the shooter using the solenoid
   */
  public void retractShooter() { 
    shooterSolenoid.set(Value.kReverse);
  }

}
