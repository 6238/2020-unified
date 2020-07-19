/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

//This class is for the Ball Intake Subsystem
public class IntakeSubsystem extends SubsystemBase {
 
  private WPI_TalonSRX feeder;
  private WPI_TalonSRX backMagazine;
  private WPI_TalonSRX frontMagazine;

  public IntakeSubsystem(WPI_TalontalonFactory talonFactory) {
    //Top feeder wheel
    feeder = talonFactory.create(0); //Change based on ID needed

    //Back wheel for ball intake
    backMagazine = talonFactory.create(2); //Change based on ID needed

    //Front wheel for ball intake
    frontMagazine = talonFactory.create(1); //Change based on ID Needed

    //Back is a follower of front
    backMagazine.follow(frontMagazine);
    backMagazine.setInverted(InvertType.FollowMaster); //Back is inverted of front
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  //Run to start all motors
  public void start(){
    frontMagazine.set(0.5); //Back should move with the front
    feeder.set(0.5);
  }

  //Run to stop all motors
  public void stop(){
    frontMagazine.set(0.0); // Back should move with the front
    feeder.set(0.0);
  }
}
