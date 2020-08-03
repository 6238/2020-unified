/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

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

  private Joystick joystick;
  private DigitalInput limit;

  private WPI_TalonSRX frontMagazine;
  private WPI_TalonSRX backMagazine;
  private WPI_TalonSRX feederMotor;

  private SpeedControllerGroup magazine;

  // private CANSparkMax leftShooter;
  // private CANSparkMax rightShooter;

  // private CANEncoder leftEncoder;
  // private CANEncoder rightEncoder;
  private boolean shootBoolean;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    SmartDashboard.putBoolean("Elevator Synched", true);

    SmartDashboard.putNumber("Magazine Elevator", 0);

    SmartDashboard.putNumber("Front Magazine Motor", 0);
    SmartDashboard.putNumber("Back Magazine Motor", 0);

    SmartDashboard.putNumber("Feeder Motor", 0);

    frontMagazine = new WPI_TalonSRX(8);// Arbirtary
    frontMagazine.setInverted(true);

    backMagazine = new WPI_TalonSRX(7); // Arbritary

    feederMotor = new WPI_TalonSRX(9);
    feederMotor.setInverted(true);

    // Speed Controllers
    magazine = new SpeedControllerGroup(frontMagazine, backMagazine);

    limit = new DigitalInput(0);

    leftShooter = new CANSparkMax(0, MotorType.kBrushless);
    rightShooter = new CANSparkMax(1, MotorType.kBrushless);
    leftEncoder = leftShooter.getEncoder();
    rightEncoder = rightShooter.getEncoder();
    
    shootBoolean = false;
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
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


    if (!limit.get() && !shootBoolean) { 
      // if limit switch is engaged, feeder motor stops spinning
      feederMotor.set(0);
    }
    if (joystick.getTrigger()) {
      // set shootBoolean to true if we want to shoot
      shootBoolean = true;
    }
    if (shootBoolean) {
      // if shootBoolean is true, run the feederMotor at 0.5 so we can shoot
      feederMotor.set(0.5); 
    }

    if (SmartDashboard.getBoolean("Elevator Synched", true)){
      magazine.set(SmartDashboard.getNumber("Magazine Elevator", 0));
    }

    else{
      frontMagazine.set(SmartDashboard.getNumber("Front Magazine Motor", 0));
      backMagazine.set(SmartDashboard.getNumber("Back Magazine Motor", 0));
    }
    

    feederMotor.set(SmartDashboard.getNumber("Feeder Motor", 0));

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
