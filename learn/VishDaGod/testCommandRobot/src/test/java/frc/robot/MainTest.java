package frc.robot;

import org.junit.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import edu.wpi.first.wpilibj.Joystick;

import frc.robot.subsystems.Factory;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

import frc.robot.commands.DriveCommand;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableInstantCommand;

public class MainTest {
    @Mock Factory factory = new Factory();
    @Mock DriveSubsystem m_drive;
    @Mock ElevatorSubsystem m_elevator;
    @Mock IntakeSubsystem m_intake;
    @Mock ShooterSubsystem m_shooter;

    @Mock DriveCommand m_driveCommand;

    @Mock Joystick m_leftJoystick;
    @Mock Joystick m_rightJoystick;

    RobotContainer container;
    Robot robot;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(this.m_leftJoystick.getY()).thenReturn(0.0);
        when(this.m_rightJoystick.getZ()).thenReturn(0.0);
        TestableCommand.activateTestMode();
        TestableInstantCommand.activateTestMode();

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
    }

}