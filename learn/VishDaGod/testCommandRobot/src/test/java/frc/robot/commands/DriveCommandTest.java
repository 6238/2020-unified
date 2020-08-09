package frc.robot.commands;

import org.junit.*;
import org.mockito.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.hal.sim.DriverStationSim;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import com.analog.adis16470.frc.ADIS16470_IMU;

import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableJoystick;
import frc.robot.dashboard.Dashboard;
import frc.robot.dashboard.Slider;

import frc.robot.subsystems.Factory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

import frc.robot.Robot;
import frc.robot.RobotContainer;

public class DriveCommandTest {
    @Mock Factory factory = new Factory();
    @Mock Dashboard dashboard = new Dashboard();
    @Mock DriveSubsystem m_drive;
    @Mock ElevatorSubsystem m_elevator;
    @Mock IntakeSubsystem m_intake;
    @Mock ShooterSubsystem m_shooter;

    @Mock DriveCommand m_driveCommand;

    @Mock TestableJoystick m_leftJoystick;
    @Mock TestableJoystick m_rightJoystick;

    @Mock ADIS16470_IMU kIMU;

    @Mock Slider insanityFactorSlider;
    @Mock Slider sensitivitySlider;

    Robot robot;
    RobotContainer container;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(this.factory.getIMU()).thenReturn(kIMU);
        when(this.dashboard.getSlider("insanityFactor", 0.5)).thenReturn(insanityFactorSlider);
        when(this.dashboard.getSlider("sensitivity", 0.75)).thenReturn(sensitivitySlider);
        when(this.m_leftJoystick.getJoyY()).thenReturn(0.0);
        when(this.m_rightJoystick.getJoyZ()).thenReturn(0.0);
        
        var injection = new RobotInjection();
        injection.drive = m_drive;
        injection.elevator = m_elevator;
        injection.intake = m_intake;
        injection.shooter = m_shooter;
        injection.leftJoystick = m_leftJoystick;
        injection.rightJoystick = m_rightJoystick;
        injection.driveCommand = m_driveCommand;
        
        this.container = new RobotContainer(injection);
        this.robot = new Robot(container);

        HAL.initialize(500, 0);
        DriverStationSim dsSim = new DriverStationSim();
        dsSim.setDsAttached(true);
        dsSim.setAutonomous(false);
        dsSim.setEnabled(true);
    }

    @Test
    public void testDriveCommandCallsMethod() {
        var command = new DriveCommand(m_drive, m_leftJoystick, m_rightJoystick);
        command.execute();

        verify(m_drive).drive(0.0, 0.0);

        when(this.m_leftJoystick.getJoyY()).thenReturn(0.5);
        when(this.m_rightJoystick.getJoyZ()).thenReturn(0.25);
        command.execute();

        verify(m_drive).drive(0.5, 0.25);
        assertFalse(m_driveCommand.isFinished());
    }

    @Test
    public void testDrivingFullRobotCode() {
        this.robot.robotInit();

        verify(m_drive).setDefaultCommand(m_driveCommand);

        when(insanityFactorSlider.get()).thenReturn(0.5);
        when(sensitivitySlider.get()).thenReturn(0.75);

        when(this.m_leftJoystick.getJoyY()).thenReturn(0.5);
        when(this.m_rightJoystick.getJoyZ()).thenReturn(0.5);
        when(this.m_leftJoystick.getRawButton(TestableJoystick.AxisType.kY.value)).thenReturn(true);
        when(this.m_rightJoystick.getRawButton(TestableJoystick.AxisType.kZ.value)).thenReturn(true);

        this.robot.robotPeriodic();

        verify(m_drive, times(1)).drive(0.5, 0.5);

        when(this.m_leftJoystick.getJoyY()).thenReturn(0.7);
        when(this.m_rightJoystick.getJoyZ()).thenReturn(0.2);

        this.robot.robotPeriodic();

        verify(m_drive, times(1)).drive(0.2, 0.7);

        when(this.m_leftJoystick.getJoyY()).thenReturn(0.0);
        when(this.m_rightJoystick.getJoyZ()).thenReturn(0.0);
        when(this.m_leftJoystick.getRawButton(TestableJoystick.AxisType.kY.value)).thenReturn(false);
        when(this.m_rightJoystick.getRawButton(TestableJoystick.AxisType.kZ.value)).thenReturn(false);

        this.robot.robotPeriodic();

        verify(m_drive, times(1)).drive(0.0, 0.0);

        when(this.m_leftJoystick.getJoyY()).thenReturn(0.5);
        when(this.m_rightJoystick.getJoyZ()).thenReturn(0.5);
        when(this.m_leftJoystick.getRawButton(TestableJoystick.AxisType.kTwist.value)).thenReturn(true);
        when(this.m_rightJoystick.getRawButton(TestableJoystick.AxisType.kThrottle.value)).thenReturn(true);

        this.robot.robotPeriodic();

        verify(m_drive, times(2)).drive(0.5, 0.5);
    }
}