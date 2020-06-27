/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class RunAllCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ShooterSubsystem m_shooter;
  private final ElevatorSubsystem m_elevator;

  private double speed = 0.25;

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
    m_elevator.front(speed);
    m_elevator.back(speed);
    m_elevator.feeder(speed);

    m_shooter.shooter(speed);
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
