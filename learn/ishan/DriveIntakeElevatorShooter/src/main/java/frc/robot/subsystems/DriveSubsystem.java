package frc.robot.subsystems;

import java.util.Map;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.analog.adis16470.frc.ADIS16470_IMU.IMUAxis;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.ToggleButton;
import frc.robot.Constants.Drive;
import frc.robot.Constants.OIConstants;

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

	private final ADIS16470_IMU kIMU = Drive.kIMU;

	private double insanityFactor = 0.5;
	private double sensitivity = 0.75;
	
	private boolean curvatureDrive = false;
	private boolean curvatureDriveQuickTurn = false;

	private NetworkTableEntry insanityFactorEntry = OIConstants.kTab.add("insanityFactor", insanityFactor)
			.withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
	private NetworkTableEntry sensitivityEntry = OIConstants.kTab.add("sensitivity", sensitivity)
			.withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();

	private NetworkTableEntry leftEncoderEntry = OIConstants.kTab.add("leftEncoder", 0).getEntry();
	private NetworkTableEntry rightEncoderEntry = OIConstants.kTab.add("rightEncoder", 0).getEntry();
	
	private ToggleButton curvatureDriveButton = new ToggleButton("curvatureDrive", curvatureDrive);
	private ToggleButton curvatureDriveQuickTurnButton = new ToggleButton("curvatureDriveQuickTurn", curvatureDriveQuickTurn);

	public DriveSubsystem(Factory f) {
		m_leftTalon1 = f.createTalon(Drive.kLeftTalon1);
		m_leftTalon2 = f.createTalon(Drive.kLeftTalon2);
		m_leftTalon3 = f.createTalon(Drive.kLeftTalon3);

		m_rightTalon1 = f.createTalon(Drive.kRightTalon1);
		m_rightTalon2 = f.createTalon(Drive.kRightTalon2);
		m_rightTalon3 = f.createTalon(Drive.kRightTalon3);

		m_leftDrive = new SpeedControllerGroup(m_leftTalon1, m_leftTalon2, m_leftTalon3);
		m_rightDrive = new SpeedControllerGroup(m_rightTalon1, m_rightTalon2, m_rightTalon3);

		m_drive = new DifferentialDrive(m_leftDrive, m_rightDrive);

		m_leftTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Drive.kPIDLoopIdx,
				Constants.kTimeoutMs);
		m_rightTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Drive.kPIDLoopIdx,
				Constants.kTimeoutMs);

		kIMU.setYawAxis(IMUAxis.kZ);
		OIConstants.kTab.add(kIMU);
	}

	@Override
	public void periodic() {
		insanityFactor = insanityFactorEntry.getDouble(insanityFactor);
		sensitivity = sensitivityEntry.getDouble(sensitivity);

		curvatureDrive = curvatureDriveButton.get();
		curvatureDriveQuickTurnButton.set(curvatureDriveQuickTurn);

		leftEncoderEntry.setNumber(m_leftTalon1.getSelectedSensorVelocity());
		rightEncoderEntry.setNumber(m_rightTalon1.getSelectedSensorVelocity());
	}

	public void drive(double ySpeed, double zSpeed) {
		if (curvatureDrive) {
			m_drive.curvatureDrive(ySpeed * insanityFactor, zSpeed * sensitivity, curvatureDriveQuickTurn);
		} else {
			m_drive.arcadeDrive(ySpeed * insanityFactor, zSpeed * sensitivity, false);
		}
	}

	public void setCurvatureDriveQuickTurn(boolean value) {
		curvatureDriveQuickTurn = value;
	}
}