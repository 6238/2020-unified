/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

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

  private WPI_TalonSRX motor;

  private double insanityFactor = 0.6;
  private double insanityFactor2 = 0.25;

  private PowerDistributionPanel pdp;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch colorMatcher = new ColorMatch();

  /* private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113); */
  
  private final Color kBlueTarget = ColorMatch.makeColor(0.224, 0.476, 0.300);
  private final Color kGreenTarget = ColorMatch.makeColor(0.209, 0.565, 0.226);
  private final Color kRedTarget = ColorMatch.makeColor(0.526, 0.341, 0.129);
  private final Color kYellowTarget = ColorMatch.makeColor(0.337, 0.528, 0.113);

  private Color setColor;

  private int tempCount = 0;
  private int colorCount = 0;

  private int countThreshold = 2;
  private double confidenceThreshold = 0.7;
  private int maxCount = 4;

  private boolean positionControl = false;
  private boolean pcSetup = true;

  // private ArrayList<String> positions;

  private int colorInt;
  private int positionInt;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    motor = new WPI_TalonSRX(4);
    SmartDashboard.putNumber("insanityFactor", insanityFactor);
    SmartDashboard.putNumber("insanityFactor2", insanityFactor2);
    pdp = new PowerDistributionPanel();

    /* positions = new ArrayList<String>();

    positions.add("blue");
    positions.add("green");
    positions.add("red");
    positions.add("yellow"); */

    colorMatcher.addColorMatch(kBlueTarget);
    colorMatcher.addColorMatch(kGreenTarget);
    colorMatcher.addColorMatch(kRedTarget);
    colorMatcher.addColorMatch(kYellowTarget);

    SmartDashboard.putNumber("countThreshold", countThreshold);
    SmartDashboard.putNumber("confidenceThreshold", confidenceThreshold);
    SmartDashboard.putNumber("maxCount", maxCount);
    
    SmartDashboard.putBoolean("positionControl", positionControl);
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
    SmartDashboard.putData(pdp);

    /* Color detectedColor = colorSensor.getColor();
    SmartDashboard.putString("color", "" + detectedColor);

    String colorString;

    ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString); */
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

  @Override
  public void teleopInit() {
    Color detectedColor = colorSensor.getColor();
    SmartDashboard.putString("color", "" + detectedColor);

    ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

    setColor = match.color;

    colorCount = 0;
    tempCount = 0;
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    positionControl = SmartDashboard.getBoolean("positionControl", positionControl);
    countThreshold = (int) SmartDashboard.getNumber("countThreshold", countThreshold);
    confidenceThreshold = SmartDashboard.getNumber("confidenceThreshold", confidenceThreshold);
    maxCount = (int) SmartDashboard.getNumber("maxCount", maxCount);

    String gameData;
    gameData = DriverStation.getInstance().getGameSpecificMessage();

    gameData = "B";

    if (positionControl && gameData.length() > 0) {
      if (pcSetup) {
        Color detectedColor = colorSensor.getColor();
        SmartDashboard.putString("color", "" + detectedColor);

        ColorMatchResult match = colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
          colorInt = 0;
          System.out.println("blue");
        } else if (match.color == kGreenTarget) {
          colorInt = 1;
          System.out.println("green");
        } else if (match.color == kRedTarget) {
          colorInt = 2;
          System.out.println("red");
        } else if (match.color == kYellowTarget) {
          colorInt = 3;
          System.out.println("yellow");
        } else {
          colorInt = -1;
          System.out.println("idk");
        }
        
        switch (gameData.charAt(0))
        {
          case 'B' :
            positionInt = 0;
            //Blue case code
            break;
          case 'G' :
            positionInt = 1;
            //Green case code
            break;
          case 'R' :
            positionInt = 2;
            //Red case code
            break;
          case 'Y' :
            positionInt = 3;
            //Yellow case code
            break;
          default :
            //This is corrupt data
            break;
        }
        
        positionInt += 2;
        if (positionInt > 3) {
          positionInt -= 4;
        }

        pcSetup = false;
      }

      if (colorInt < positionInt) {
        motor.set(-1 * SmartDashboard.getNumber("insanityFactor2", insanityFactor2));
      } else if (colorInt > positionInt) {
        motor.set(SmartDashboard.getNumber("insanityFactor2", insanityFactor2));
      } else {
        motor.set(0);
      }
    } else if (!positionControl) {
      ColorMatchResult colorMatchResult = checkColor();
      boolean colorMatched = colorMatchResult.color == setColor;

      SmartDashboard.putBoolean("color matched", colorMatched);
      SmartDashboard.putNumber("colorCount", colorCount);
      SmartDashboard.putNumber("tempCount", tempCount);

      if (colorCount < maxCount * 2) {
        motor.set(SmartDashboard.getNumber("insanityFactor", insanityFactor));
      } else {
        motor.set(0);
        positionControl = true;
      }

      if (colorMatched && colorMatchResult.confidence > confidenceThreshold) {
        tempCount++;
      } else {
        if (tempCount > countThreshold) {
          colorCount++;
        }
        tempCount = 0;
      }
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    insanityFactor = SmartDashboard.getNumber("insanityFactor", insanityFactor);
    motor.set(insanityFactor);
  }

  public ColorMatchResult checkColor() {
    Color newColor = colorSensor.getColor();

    ColorMatchResult match = colorMatcher.matchClosestColor(newColor);

    return match;
  }
}
