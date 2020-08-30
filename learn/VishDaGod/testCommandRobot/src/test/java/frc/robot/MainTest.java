package frc.robot;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import frc.robot.commands.DriveCommand;

import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableJoystick;

import frc.robot.subsystems.Factory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class MainTest {
    @Mock Factory factory = new Factory();
    @Mock DriveSubsystem m_drive;
    @Mock ElevatorSubsystem m_elevator;
    @Mock IntakeSubsystem m_intake;
    @Mock ShooterSubsystem m_shooter;

    @Mock DriveCommand m_driveCommand;

    @Mock TestableJoystick m_leftJoystick;
    @Mock TestableJoystick m_rightJoystick;

    RobotContainer container;
    Robot robot;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(this.m_leftJoystick.getJoyY()).thenReturn(0.0);
        when(this.m_rightJoystick.getJoyZ()).thenReturn(0.0);

        var injection = new RobotInjection();
        injection.m_drive = m_drive;
        injection.m_elevator = m_elevator;
        injection.m_intake = m_intake;
        injection.m_shooter = m_shooter;
        injection.m_leftJoystick = m_leftJoystick;
        injection.m_rightJoystick = m_rightJoystick;
        injection.m_driveCommand = m_driveCommand;

        this.container = new RobotContainer(injection);
        this.robot = new Robot(container);
    }

}