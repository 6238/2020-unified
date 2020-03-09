/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
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

    private final WPI_TalonSRX m_leftTalon1 = new WPI_TalonSRX(36);
    private final WPI_TalonSRX m_leftTalon2 = new WPI_TalonSRX(35);
    private final WPI_TalonSRX m_leftTalon3 = new WPI_TalonSRX(34);

    private final WPI_TalonSRX m_rightTalon1 = new WPI_TalonSRX(36);
    private final WPI_TalonSRX m_rightTalon2 = new WPI_TalonSRX(35);
    private final WPI_TalonSRX m_rightTalon3 = new WPI_TalonSRX(34);

    private final Joystick joystick = new Joystick(0);
    
    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
        m_chooser.addOption("My Auto", kCustomAuto);
        SmartDashboard.putData("Auto choices", m_chooser);

        m_leftTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
        m_rightTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

        m_leftTalon1.getSensorCollection().setQuadraturePosition(0, 10);
        m_rightTalon1.getSensorCollection().setQuadraturePosition(0, 10);

        /**
         * Phase sensor accordingly. Positive Sensor Reading should match Green
         * (blinking) Leds on Talon
         */
        m_rightTalon1.setSensorPhase(false);

        /* Config the peak and nominal outputs */
        m_rightTalon1.configNominalOutputForward(0, 10);
        m_rightTalon1.configNominalOutputReverse(0, 10);
        m_rightTalon1.configPeakOutputForward(1, 10);
        m_rightTalon1.configPeakOutputReverse(0, 10);

        SmartDashboard.putNumber("P", 0);
        SmartDashboard.putNumber("I", 0);
        SmartDashboard.putNumber("D", 0);
        SmartDashboard.putNumber("F", .3009);
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
        SmartDashboard.putNumber("leftEncoder", m_leftTalon1.getSelectedSensorVelocity());
        SmartDashboard.putNumber("rightEncoder", m_rightTalon1.getSelectedSensorVelocity());

        m_rightTalon1.config_kF(0, SmartDashboard.getNumber("F", 0), 10);
        m_rightTalon1.config_kP(0, SmartDashboard.getNumber("P", 0), 10);
        m_rightTalon1.config_kI(0, SmartDashboard.getNumber("I", 0), 10);
        m_rightTalon1.config_kD(0, SmartDashboard.getNumber("D", 0), 10);

        // m_rightTalon1.set(TalonSRXControlMode.Velocity, 1000);
        m_rightTalon1.set(TalonSRXControlMode.PercentOutput, joystick.getY());
        m_rightTalon2.set(TalonSRXControlMode.Follower, 36);
        m_rightTalon3.set(TalonSRXControlMode.Follower, 36);
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
