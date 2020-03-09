package org.iraiders.robot2019.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.iraiders.robot2019.robot.OI;
import org.iraiders.robot2019.robot.RobotMap;
import org.iraiders.robot2019.robot.commands.intake.BallIntakeControlCommand;
import org.iraiders.robot2019.robot.commands.intake.BallIntakeJointCommand;
import org.iraiders.robot2019.robot.commands.intake.BallIntakeMonitor;
import org.iraiders.robot2019.robot.commands.intake.hatch.GenericHatch;
import org.iraiders.robot2019.robot.commands.intake.hatch.HatchControl;
import org.iraiders.robot2019.robot.commands.intake.hatch.PlateControl;

import static org.iraiders.robot2019.robot.RobotMap.*;
import static org.iraiders.robot2019.robot.commands.intake.BallIntakeControlCommand.BallIntakeState.OUT;
import static org.iraiders.robot2019.robot.commands.intake.BallIntakeControlCommand.BallIntakeState.STOPPED;
import static org.iraiders.robot2019.robot.subsystems.IntakeSubsystem.HatchPosition.EXTENDED;
import static org.iraiders.robot2019.robot.subsystems.IntakeSubsystem.HatchPosition.RETRACTED;
import static org.iraiders.robot2019.robot.subsystems.IntakeSubsystem.IntakeJointPosition.DOWN;
import static org.iraiders.robot2019.robot.subsystems.IntakeSubsystem.IntakeJointPosition.UP;

public class IntakeSubsystem extends Subsystem {
  public final WPI_TalonSRX intakeTalon = new WPI_TalonSRX(RobotMap.ballIntakeMotorPort);
  public final DoubleSolenoid ballIntakeSolenoid = OI.getDoubleSolenoid(RobotMap.ballIntakeUpNodeId, RobotMap.ballIntakeDownNodeId);
  public final DoubleSolenoid hatchSolenoid = OI.getDoubleSolenoid(RobotMap.hatchInNodeId, RobotMap.hatchOutNodeId);
  public final DoubleSolenoid plateSolenoid = OI.getDoubleSolenoid(RobotMap.plateOpenNodeId, RobotMap.plateCloseNodeId);
  public final DigitalInput ballIntakeLimitSwitch = new DigitalInput(RobotMap.ballIntakeLimitSwitchPort);
  public DigitalInput ballIntakeLightSensor = new DigitalInput(RobotMap.ballIntakeLightSensor);

  private final BallIntakeMonitor ballIntakeMonitor = new BallIntakeMonitor(this);
  public BallIntakeControlCommand ballIntakeControlCommand = new BallIntakeControlCommand(this);
  public final BallIntakeJointCommand ballIntakeJointCommand = new BallIntakeJointCommand(this);
  public final GenericHatch plateExtendCommand = new PlateControl(this, plateSolenoid);
  public final GenericHatch hatchExtendCommand = new HatchControl(this, hatchSolenoid);


  public IntakeSubsystem() {
    ballIntakeSolenoid.setName("BallIntakeSolenoid");
    hatchSolenoid.setName("HatchExtendSolenoid");
    plateSolenoid.setName("PlateExtendSolenoid");
    intakeTalon.setName("BallIntakeTalon");
    intakeTalon.setInverted(true);
  }
  
  public void initTeleop() {
    ballIntakeControlCommand.start();
    ballIntakeMonitor.start();
    //new EncoderReporter(intakeTalon).start();

    //Robot.initializeTalonDefaults(intakeTalon);
    intakeTalon.configPeakCurrentLimit(40);
    intakeTalon.configContinuousCurrentLimit(20);
    intakeTalon.enableCurrentLimit(true);
  }

  public void initControls() {
    ballIntakeJointToggleButton.whenPressed(new InstantCommand(() -> this.ballIntakeJointCommand.setIntakeJointPosition(this.ballIntakeJointCommand.getIntakeJointPosition() == UP ? DOWN : UP)));
    plateToggleButton.whenPressed(new InstantCommand(() -> this.plateExtendCommand.setPosition(this.plateExtendCommand.getPosition() == RETRACTED ? EXTENDED : RETRACTED)));
    hatchToggleButton.whenPressed(new InstantCommand(() -> this.hatchExtendCommand.setPosition(this.hatchExtendCommand.getPosition() == RETRACTED ? EXTENDED : RETRACTED)));
    ballIntakeMotorOutButton.whenPressed(new InstantCommand(() -> this.ballIntakeControlCommand.setBallIntakeState(OUT)));
    ballIntakeMotorOutButton.whenReleased(new InstantCommand(() -> this.ballIntakeControlCommand.setBallIntakeState(STOPPED)));
  }

  @Override
  protected void initDefaultCommand() {
  
  }

  public enum IntakeJointPosition {
    UP, DOWN
  }

  public enum HatchPosition {
    EXTENDED, RETRACTED
  }
}
