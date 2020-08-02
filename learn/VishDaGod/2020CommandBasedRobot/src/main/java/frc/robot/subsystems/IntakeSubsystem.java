/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

/**
 * This is the intake subsystem that is taking in the ball from the field
 * @author Vishnu Velayuthan
 * @author vishnuvelayuthan@gmail.com
 * @version 1.0
 * @since 1.00
 */
public class IntakeSubsystem extends SubsystemBase {
  /**
   * Creates a new IntakeSubsystem.
   */

   private WPI_TalonSRX intakeOuter;
   private WPI_TalonSRX intakeLeft;
   private WPI_TalonSRX intakeRight;

  public IntakeSubsystem(Factory factory) {
    //Outer wheel that takes in the ball from the floor
    intakeOuter = factory.createTalon(IntakeConstants.intakeOuter);

    //Two motors that takes
    intakeLeft = factory.createTalon(IntakeConstants.intakeLeft);
    intakeRight = factory.createTalon(IntakeConstants.intakeRight);

    //Set the intake left as a follower of the right 
    intakeLeft.follow(intakeRight);
    intakeLeft.setInverted(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  public void start() {
    intakeRight.set(IntakeConstants.kIntakeSpeed);
    intakeOuter.set(IntakeConstants.kIntakeSpeed);
  }

  public void stop() {
    intakeRight.set(0);
    intakeOuter.set(0);
  }

}
