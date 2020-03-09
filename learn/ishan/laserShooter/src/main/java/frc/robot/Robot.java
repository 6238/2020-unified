/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
// import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
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

  private WPI_TalonSRX rightTalon1;

  private PowerDistributionPanel pdp;

  private double leftInsanityFactor = 0.5;
  private double rightInsanityFactor = 0.5;

  private boolean reverseLeft = false;
  private boolean insanityCheck = true;

  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;

  // private Encoder encoder;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    leftTalon1 = new WPI_TalonSRX(4);

    rightTalon1 = new WPI_TalonSRX(3);

    pdp = new PowerDistributionPanel();

    SmartDashboard.putBoolean("reverseLeft", reverseLeft);

    SmartDashboard.putNumber("leftInsanityFactor", leftInsanityFactor);
    SmartDashboard.putNumber("rightInsanityFactor", rightInsanityFactor);

    SmartDashboard.putBoolean("insanityCheck", insanityCheck);

    // PWM port 9
    // Must be a PWM header, not MXP or DIO
    m_led = new AddressableLED(9);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(93);
    m_led.setLength(m_ledBuffer.getLength());

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();
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
    reverseLeft = SmartDashboard.getBoolean("reverseLeft", reverseLeft);
    insanityCheck = SmartDashboard.getBoolean("insanityCheck", insanityCheck);
    if (!insanityCheck) {
      leftInsanityFactor = SmartDashboard.getNumber("leftInsanityFactor", leftInsanityFactor);
      rightInsanityFactor = SmartDashboard.getNumber("rightInsanityFactor", rightInsanityFactor);
    } else {
      if (SmartDashboard.getNumber("leftInsanityFactor", leftInsanityFactor) != leftInsanityFactor) {
        leftInsanityFactor = SmartDashboard.getNumber("leftInsanityFactor", leftInsanityFactor);
        rightInsanityFactor = SmartDashboard.getNumber("leftInsanityFactor", leftInsanityFactor);
      } else if (SmartDashboard.getNumber("rightInsanityFactor", rightInsanityFactor) != rightInsanityFactor) {
        leftInsanityFactor = SmartDashboard.getNumber("rightInsanityFactor", rightInsanityFactor);
        rightInsanityFactor = SmartDashboard.getNumber("rightInsanityFactor", rightInsanityFactor);
      }

      SmartDashboard.putNumber("leftInsanityFactor", leftInsanityFactor);
      SmartDashboard.putNumber("rightInsanityFactor", rightInsanityFactor);
    }


    if (reverseLeft) {
      leftTalon1.setInverted(true);
    } else {
      leftTalon1.setInverted(false);
    }

    leftTalon1.set(-leftInsanityFactor);
    rightTalon1.set(-rightInsanityFactor);

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 0, 255, 0);
   }
   
   m_led.setData(m_ledBuffer);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    pdp.clearStickyFaults();

    leftTalon1.set(0.25);
    rightTalon1.set(0.25);
  }
}
