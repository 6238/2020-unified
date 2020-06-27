/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Map;

import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.networktables.NetworkTableEntry;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.Constants;

/**
 * An example command that uses an example subsystem.
 */
public class RunAllCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ShooterSubsystem m_shooter;
  private final ElevatorSubsystem m_elevator;

  private double feederSpeed = 0.5;
  private double shooterSpeed = 0.5;
  private double elevatorSpeed = 0.5;

  private boolean enable = false;

  private NetworkTableEntry feederSpeedEntry = Constants.kTab.add("feederSpeed", feederSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
  private NetworkTableEntry shooterSpeedEntry = Constants.kTab.add("shooterSpeed", shooterSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
  private NetworkTableEntry elevatorSpeedEntry = Constants.kTab.add("elevatorSpeed", elevatorSpeed).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();

  private ToggleButton enableButton = new ToggleButton("enable", enable);
  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public RunAllCommand(ShooterSubsystem shooter, ElevatorSubsystem elevator) {
    m_shooter = shooter;
    m_elevator = elevator;

    addRequirements(m_shooter, m_elevator);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("started");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    feederSpeed = feederSpeedEntry.getDouble(feederSpeed);
    shooterSpeed = shooterSpeedEntry.getDouble(shooterSpeed);
    elevatorSpeed = elevatorSpeedEntry.getDouble(elevatorSpeed);

    enable = enableButton.get();
    
    if (enable) {
      m_elevator.front(elevatorSpeed);
      m_elevator.back(elevatorSpeed);
      m_elevator.feeder(feederSpeed);
      
      m_shooter.shooter(shooterSpeed);
    } else {
      m_elevator.front(0);
      m_elevator.back(0);
      m_elevator.feeder(0);
  
      m_shooter.shooter(0);  
    }

    System.out.println("running");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_elevator.front(0);
    m_elevator.back(0);
    m_elevator.feeder(0);
  
    m_shooter.shooter(0);
    System.out.println("stopped");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
