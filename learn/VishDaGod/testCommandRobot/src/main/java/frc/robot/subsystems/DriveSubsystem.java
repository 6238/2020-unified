package frc.robot.subsystems;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.analog.adis16470.frc.ADIS16470_IMU.IMUAxis;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.Constants.Drive;
import frc.robot.Constants.OIConstants;

import frc.robot.dashboard.Dashboard;
import frc.robot.dashboard.Slider;
import frc.robot.dashboard.ToggleButton;

public class DriveSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_leftTalon1;
    private final WPI_TalonSRX m_leftTalon2;
    private final WPI_TalonSRX m_leftTalon3;

    private final WPI_TalonSRX m_rightTalon1;
    private final WPI_TalonSRX m_rightTalon2;
    private final WPI_TalonSRX m_rightTalon3;

    private final SpeedControllerGroup m_leftDrive;
    private final SpeedControllerGroup m_rightDrive;

    private final DifferentialDrive m_drive;

    private final ADIS16470_IMU m_IMU;

    private final boolean arcadeSquaredInputs = false;

    private double insanityFactor = 0.5;
    private double sensitivity = 0.75;

    private boolean curvatureDrive = false;
    private boolean curvatureDriveQuickTurn = false;

    private Slider insanityFactorSlider;
    private Slider sensitivitySlider;

    private NetworkTableEntry leftEncoderEntry = OIConstants.SHUFFLEBOARD_TAB.add("leftEncoder", 0).getEntry();
    private NetworkTableEntry rightEncoderEntry = OIConstants.SHUFFLEBOARD_TAB.add("rightEncoder", 0).getEntry();

    private ToggleButton curvatureDriveButton;
    private ToggleButton curvatureDriveQuickTurnButton;

    public DriveSubsystem(Factory f, Dashboard d) {
        m_leftTalon1 = f.createTalon(Drive.DRIVE_LEFT_TALON_1_ID);
        m_leftTalon2 = f.createTalon(Drive.DRIVE_LEFT_TALON_2_ID);
        m_leftTalon3 = f.createTalon(Drive.DRIVE_LEFT_TALON_3_ID);

        m_rightTalon1 = f.createTalon(Drive.DRIVE_RIGHT_TALON_1_ID);
        m_rightTalon2 = f.createTalon(Drive.DRIVE_RIGHT_TALON_2_ID);
        m_rightTalon3 = f.createTalon(Drive.DRIVE_RIGHT_TALON_3_ID);

        m_leftDrive = new SpeedControllerGroup(m_leftTalon1, m_leftTalon2, m_leftTalon3);
        m_rightDrive = new SpeedControllerGroup(m_rightTalon1, m_rightTalon2, m_rightTalon3);

        m_drive = new DifferentialDrive(m_leftDrive, m_rightDrive);

        m_leftTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Drive.DRIVE_PID_LOOP_IDX,
                Constants.TIMEOUT_MS);
        m_rightTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Drive.DRIVE_PID_LOOP_IDX,
                Constants.TIMEOUT_MS);

        m_IMU = f.getIMU();
        m_IMU.setYawAxis(IMUAxis.kZ);
        OIConstants.SHUFFLEBOARD_TAB.add("IMU", m_IMU);

        insanityFactorSlider = d.getSlider("insanityFactor", insanityFactor);
        sensitivitySlider = d.getSlider("sensitivity", sensitivity);
        curvatureDriveButton = d.getToggleButton("curvatureDrive", curvatureDrive);
        curvatureDriveQuickTurnButton = d.getToggleButton("curvatureDriveQuickTurn", curvatureDriveQuickTurn);
    }

    @Override
    public void periodic() {
        insanityFactor = insanityFactorSlider.get();
        sensitivity = sensitivitySlider.get();

        curvatureDrive = curvatureDriveButton.get();
        curvatureDriveQuickTurnButton.set(curvatureDriveQuickTurn);

        leftEncoderEntry.setNumber(m_leftTalon1.getSelectedSensorVelocity());
        rightEncoderEntry.setNumber(m_rightTalon1.getSelectedSensorVelocity());
    }

    public void drive(double ySpeed, double zSpeed) {
        if (curvatureDrive) {
            m_drive.curvatureDrive(ySpeed * insanityFactor, zSpeed * sensitivity, curvatureDriveQuickTurn);
        } else {
            m_drive.arcadeDrive(ySpeed * insanityFactor, zSpeed * sensitivity, arcadeSquaredInputs);
        }
    }

    public void setCurvatureDriveQuickTurn(boolean value) {
        curvatureDriveQuickTurn = value;
    }

    public double getInsanityFactor() {
        return insanityFactor;
    }

    public double getSensitivity() {
        return sensitivity;
    }
}