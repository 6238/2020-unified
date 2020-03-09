package org.iraiders.robot2019.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.iraiders.robot2019.robot.Robot;
import org.iraiders.robot2019.robot.RobotMap;
import org.iraiders.robot2019.robot.commands.lift.LiftControlCommand;
import org.iraiders.robot2019.robot.commands.lift.LiftEncoderMonitor;
import org.iraiders.robot2019.robot.commands.lift.LiftJoystickControl;

import static org.iraiders.robot2019.robot.RobotMap.liftPositionControl;

//Subsystem for elevator arm
public class LiftSubsystem extends Subsystem {
  //establishes motors (upper and lower)
  public final WPI_TalonSRX liftTalon = new WPI_TalonSRX(RobotMap.liftOneTalonPort);
  private final WPI_TalonSRX liftTalonTwo = new WPI_TalonSRX(RobotMap.liftTwoTalonPort);
  private final WPI_TalonSRX liftTalonThree = new WPI_TalonSRX(RobotMap.liftThreeTalonPort);
  private final WPI_TalonSRX liftTalonFour = new WPI_TalonSRX(RobotMap.liftFourTalonPort);
  //private final DigitalInput liftLimitSwitchDown = new DigitalInput(RobotMap.liftLimitSwitchDown);
  //private final DigitalInput liftLimitSwitchUp = new DigitalInput(RobotMap.liftLimitSwitchUp);
  
  private final LiftJoystickControl liftJoystickControl = new LiftJoystickControl(this);
  private final LiftEncoderMonitor liftEncoderMonitor = new LiftEncoderMonitor(this);

  public LiftSubsystem() {
    this.setName("LiftSubsystem");
    liftTalon.setName(this.getName(), "LiftMaster");
    liftTalonTwo.setName(this.getName(), "LiftTwo");
    liftTalonThree.setName(this.getName(), "LiftThree");
    liftTalonFour.setName(this.getName(), "LiftFour");
    
    Robot.initializeTalonDefaults(liftTalon, liftTalonTwo, liftTalonThree, liftTalonFour);
    SmartDashboard.putNumber("Lift Master Volts", liftTalon.getMotorOutputVoltage());
    //SmartDashboard.putBoolean("Down", liftLimitSwitchDown.get());
    //SmartDashboard.putBoolean("Up Encoder Value", liftLimitSwitchUp.get());
    //System.out.println("sfgserth");
    liftTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    liftTalonTwo.set(ControlMode.Follower, RobotMap.liftOneTalonPort);
    liftTalonThree.set(ControlMode.Follower, RobotMap.liftOneTalonPort);
    liftTalonFour.set(ControlMode.Follower, RobotMap.liftOneTalonPort);
  }

  public void initControls(){
    //liftUpButton.whileHeld(new SimpleMotorCommand(liftTalon,-.25));
    //liftDownButton.whileHeld(new SimpleMotorCommand(liftTalon,.25));
    liftPositionControl.whileHeld(new LiftControlCommand(this, LiftPosition.HATCH));
  }

  public void initTeleop() {
    liftEncoderMonitor.start();
    liftJoystickControl.start();
    //new EncoderReporter(FeedbackDevice.CTRE_MagEncoder_Relative, liftTalon).start();
    //new LaserArduinoInterface().start();
  }


  @Override
  protected void initDefaultCommand() {
  }


  public enum LiftPosition {
    STARTING, ROCKET_LOW, ROCKET_MID, ROCKET_HIGH, HATCH
  }
}
