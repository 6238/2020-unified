/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private WPI_TalonSRX leftTalon1;
  private WPI_TalonSRX leftTalon2;
  private WPI_TalonSRX leftTalon3;
  private WPI_TalonSRX rightTalon1;
  private WPI_TalonSRX rightTalon2;
  private WPI_TalonSRX rightTalon3;

  private SpeedControllerGroup leftMotors;
  private SpeedControllerGroup rightMotors;

  private DifferentialDrive robotDrive;

  private Joystick leftJoystick;
  private Joystick rightJoystick;
  private double insanityFactor = 0.5;


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftTalon1 = new WPI_TalonSRX(1); //this is assigning the talon 1 to leftTalon1.
    leftTalon2 = new WPI_TalonSRX(2);
    leftTalon3 = new WPI_TalonSRX(3);

    rightTalon1 = new WPI_TalonSRX(4);
    rightTalon2 = new WPI_TalonSRX(5);
    rightTalon3 = new WPI_TalonSRX(6);

    leftMotors = new SpeedControllerGroup(leftTalon1, leftTalon2, leftTalon3); 
    //^this is allowing us to control the left motors all at once rather than having individual lines of code.
    rightMotors = new SpeedControllerGroup(rightTalon1, rightTalon2, rightTalon3);

    robotDrive = new DifferentialDrive(leftMotors, rightMotors);

    leftJoystick = new Joystick(0);
    rightJoystick = new Joystick(1);

    SmartDashboard.putNumber("insanityFactor", insanityFactor);
    //^on the dashboard, it will create  abox of sort with title insanityFactor and display
    //insanityFactor.


  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
        
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    // here's where we write our drive train code.
    // if driver changes insanityFactor, use that. otherwise if programmer changes insanityFactor, use that
    if (insanityFactor != 0.5 * (1 + rightJoystick.getThrottle())) {
      insanityFactor = 0.5 * (1 + rightJoystick.getThrottle());
    } else if (insanityFactor != SmartDashboard.getNumber("insanityFactor", insanityFactor)) {
      insanityFactor = SmartDashboard.getNumber("insanityFactor", insanityFactor);
    }
    
    robotDrive.tankDrive(-1 * insanityFactor * leftJoystick.getY(), -1 * insanityFactor * rightJoystick.getY());
    
    SmartDashboard.putNumber("insanityFactor", insanityFactor);
    SmartDashboard.putNumber("left", leftJoystick.getY());
    SmartDashboard.putNumber("right", rightJoystick.getY());
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {

  }
}
