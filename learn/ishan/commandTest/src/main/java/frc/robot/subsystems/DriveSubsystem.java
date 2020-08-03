/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Map;

import com.analog.adis16470.frc.ADIS16470_IMU;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;

public class DriveSubsystem extends SubsystemBase {
    private final WPI_TalonSRX m_leftTalon1 = new WPI_TalonSRX(DriveConstants.kLeftTalon1Port);
    private final WPI_TalonSRX m_leftTalon2 = new WPI_TalonSRX(DriveConstants.kLeftTalon2Port);
    private final WPI_TalonSRX m_leftTalon3 = new WPI_TalonSRX(DriveConstants.kLeftTalon3Port);

    private final WPI_TalonSRX m_rightTalon1 = new WPI_TalonSRX(DriveConstants.kRightTalon1Port);
    private final WPI_TalonSRX m_rightTalon2 = new WPI_TalonSRX(DriveConstants.kRightTalon2Port);
    private final WPI_TalonSRX m_rightTalon3 = new WPI_TalonSRX(DriveConstants.kRightTalon3Port);

    private final SpeedControllerGroup m_leftDrive = new SpeedControllerGroup(m_leftTalon1, m_leftTalon2, m_leftTalon3);
    private final SpeedControllerGroup m_rightDrive = new SpeedControllerGroup(m_rightTalon1, m_rightTalon2,
            m_rightTalon3);

    private final DifferentialDrive m_drive = new DifferentialDrive(m_leftDrive, m_rightDrive);

    private final ADIS16470_IMU m_imu = new ADIS16470_IMU();

    private double maxSpeed = 0.5;

    public static boolean arcade;

    private ShuffleboardTab tab = Shuffleboard.getTab("SmartDashboard");
    private NetworkTableEntry insanityFactor = tab.add("insanityFactor", maxSpeed)
            .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();

    private Joystick m_rightJoystick = new Joystick(OIConstants.kRightJoystickPort);

    DifferentialDriveOdometry m_odometry = new DifferentialDriveOdometry(getAngle(),
            new Pose2d(0.0, 0.0, new Rotation2d()));

    public DriveSubsystem() {
        m_leftTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);
        m_rightTalon1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, Constants.kTimeoutMs);

        resetEncoders();
    }

    /**
     * Returns the angle of the robot as a Rotation2d.
     *
     * @return The angle of the robot.
     */
    public Rotation2d getAngle() {
        // Negating the angle because WPILib gyros are CW positive.
        return Rotation2d.fromDegrees(m_imu.getAngle() * (DriveConstants.kGyroReversed ? 1.0 : -1.0));
    }

    @Override
    public void periodic() {
        // Update the odometry in the periodic block
        m_odometry.update(getAngle(), m_leftTalon1.getSelectedSensorPosition(),
                m_rightTalon1.getSelectedSensorPosition());

    }

    /**
     * Returns the currently-estimated pose of the robot.
     *
     * @return The pose.
     */
    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    /**
     * Resets the odometry to the specified pose.
     *
     * @param pose The pose to which to set the odometry.
     */
    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        m_odometry.resetPosition(pose, getAngle());
    }

    /**
     * Drives the robot at given left/right or y/z (rotation) speeds. Speeds range
     * from [-1, 1].
     * 
     * @param aSpeed either speed of left motors or linear speed
     * @param bSpeed either speed of right motors or rotation value
     * @param arcade if false, uses tank drive (l/r); if true, uses arcade drive
     *               (y/z)
     */
    @SuppressWarnings("ParameterName")
    public void drive(double aSpeed, double bSpeed) {
        if (maxSpeed != 0.5 * (1 + m_rightJoystick.getThrottle())) {
            maxSpeed = 0.5 * (1 + m_rightJoystick.getThrottle());
        } else if (maxSpeed != insanityFactor.getDouble(maxSpeed)) {
            maxSpeed = insanityFactor.getDouble(maxSpeed);
        }

        if (!arcade) {
            m_drive.tankDrive(maxSpeed * aSpeed, maxSpeed * bSpeed);
        } else {
            m_drive.arcadeDrive(maxSpeed * aSpeed, maxSpeed * bSpeed);
        }
    }

    /**
     * Resets the drive encoders to currently read a position of 0.
     */
    public void resetEncoders() {
        m_leftTalon1.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMs);
        m_rightTalon1.getSensorCollection().setQuadraturePosition(0, Constants.kTimeoutMs);
    }

    /**
     * Gets the current wheel speeds.
     *
     * @return the current wheel speeds in a MecanumDriveWheelSpeeds object.
     */

    public DifferentialDriveWheelSpeeds getCurrentWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(m_leftTalon1.getSelectedSensorVelocity(),
                m_rightTalon1.getSelectedSensorVelocity());
    }

    /**
     * Sets the max output of the drive. Useful for scaling the drive to drive more
     * slowly.
     *
     * @param maxOutput the maximum output to which the drive will be constrained
     */
    public void setMaxOutput(double maxOutput) {
        m_drive.setMaxOutput(maxOutput);
    }

    /**
     * Switch between tank and arcade driving
     */
    public void toggleMode() {
        arcade = !arcade;
    }

    /**
     * Controls the left and right sides of the drive directly with voltages.
     *
     * @param leftVolts  the commanded left output
     * @param rightVolts the commanded right output
     */
    public void tankDriveVolts(double leftVolts, double rightVolts) {
        m_leftDrive.setVoltage(leftVolts);
        m_rightDrive.setVoltage(-rightVolts);
    }

    /**
     * Gets the average distance of the two encoders.
     *
     * @return the average of the two encoder readings
     */
    public double getAverageEncoderDistance() {
        return (m_leftTalon1.getSelectedSensorPosition() + m_rightTalon1.getSelectedSensorPosition()) / 2.0;
    }

    /**
     * Zeroes the heading of the robot.
     */
    public void zeroHeading() {
        m_imu.reset();
    }

    /**
     * Returns the heading of the robot.
     *
     * @return the robot's heading in degrees, from 180 to 180
     */
    public double getHeading() {
        return Math.IEEEremainder(m_imu.getAngle(), 360) * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
    }

    /**
     * Returns the turn rate of the robot.
     *
     * @return The turn rate of the robot, in degrees per second
     */
    public double getTurnRate() {
        return m_imu.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
    }
}