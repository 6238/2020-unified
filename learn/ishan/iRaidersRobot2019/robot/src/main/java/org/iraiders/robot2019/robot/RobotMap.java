package org.iraiders.robot2019.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  // Config
  public static final boolean SEPERATE_TRACKING_OPTIONS = true;
  public static final int MAX_MOTOR_FREE_AMPS = 80;
  public static final int MAX_MOTOR_STALL_AMPS = 40;
  public static final double DEADBAND = 0.04;

  // Motors
  public static final int frontLeftTalonPort = 1;
  public static final int backLeftTalonPort = 2;
  public static final int frontRightTalonPort = 3;
  public static final int backRightTalonPort = 4;
  public static final int liftOneTalonPort = 5;
  public static final int liftTwoTalonPort = 6;
  public static final int liftThreeTalonPort = 7;
  public static final int liftFourTalonPort = 9;
  public static final int ballIntakeMotorPort = 10;
  public static final int leftLArmPort = 11;
  public static final int rightLArmPort = 12;
  public static final int climberPistonMotorPort = 13;

  // Pneumatics
  // PCM1
  public static int ballIntakeUpNodeId = 0;
  public static int ballIntakeDownNodeId = 1;
  public static int hatchInNodeId = 2;
  public static int hatchOutNodeId = 3;
  public static int plateCloseNodeId = 4;
  public static int plateOpenNodeId = 5;
  
  // PCM2
  public static int climbFrontOpenNodeId = 1 + 7; // PCM2, Slot 0
  public static int climbFrontCloseNodeId = 2 + 7;


  // Sensors
 public static int ballIntakeLimitSwitchPort = 0;
  public static int leftLineSensorPort = 1;
  public static int midLineSensorPort = 2;
  public static int rightLineSensorPort = 3;
 // public static int ultraSonicPing = 5;
 // public static int ultraSonicEcho = 6;
  //public static int lineSenseAnalog = 7;
  public static int ultrasonicAnalog = 0;
  public static int ballIntakeLightSensor = 5;
  //public static int liftLimitSwitchDown = ???;
  //public static int liftLimitSwitchUp = 9;

  // Buttons
  private static GenericHID buttonBox = OI.arcadeController;
  private static XboxController xboxController = OI.xBoxController;
  private static Joystick joystick = OI.leftAttack;

  public static JoystickButton climberArmManualDown = new JoystickButton(joystick, 5);
  public static JoystickButton climberArmManualUp = new JoystickButton(joystick, 4);
  public static JoystickButton climberArmButton = new JoystickButton(joystick, 8);
  public static JoystickButton climberLevelButton = new JoystickButton(joystick, 9);
  public static JoystickButton  climbTurtle = new JoystickButton(joystick, 11);
  public static JoystickButton liftPositionControl = new JoystickButton(joystick, 10);

  public static JoystickButton ballIntakeMotorOutButton = new JoystickButton(joystick, 1);
  public static JoystickButton ballIntakeJointToggleButton = new JoystickButton(joystick, 3);

  public static JoystickButton hatchToggleButton = new JoystickButton(joystick, 6);
  public static JoystickButton plateToggleButton = new JoystickButton(joystick, 7);

  public static JoystickButton unifiedTrackingToggle = new JoystickButton(xboxController, 6); // Right Xbox Bumper (5 is left)
  public static JoystickButton lineTrackingToggle = new JoystickButton(xboxController, 4); // Y button
  public static JoystickButton turboSpeed = new JoystickButton(xboxController, 5);
  public static JoystickButton swapDriveMode = new JoystickButton(xboxController, 7);
  public static JoystickButton pidLineToggle = new JoystickButton(xboxController, 3);

}
