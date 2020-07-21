/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.JoystickConstants;
import frc.robot.subsystems.DriveSubsystem;


/**
 * This class is the Driving Command called by the Scheduler
 * @author Vishnu Velayuthan
 * @author vishnuvelayuthan@gmail.com
 * @version 1.0
 * @since 1.00
 */
public class DriveCommand extends CommandBase {


  // Joysticks for driving
  private Joystick leftJoystick = JoystickConstants.leftJoystick;
  private Joystick rightJoystick = JoystickConstants.rightJoystick;

  //Doubles for the driving numbers
  private double tank_leftY;
  private double tank_rightY;
  private double ySpeed;
  private double zSpeed;

  //The subsystem for the file
  private final DriveSubsystem driveSubsystem;

  /**
   * Adds all the Subsystems and requirements to the Command
   * @param driveSubsystem The Drive Subsystem is the subsystem we are using for this command
   */
  public DriveCommand(DriveSubsystem driveSubsystem) {
    this.driveSubsystem = driveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
  }


  @Override
  /**
   * Called every time the scheduler runs while the command is scheduled
   * Runs the drive method from the subsystem
   */
  public void execute() {
    tank_leftY = leftJoystick.getY();
    tank_rightY = rightJoystick.getY();
    ySpeed = leftJoystick.getY();
    zSpeed = rightJoystick.getZ();
    driveSubsystem.drive(tank_leftY, tank_rightY, ySpeed, zSpeed);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}