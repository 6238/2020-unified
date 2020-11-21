package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.io.Slider;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeSubsystem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class DriveCommandTest {
    @Mock
    DriveSubsystem dr;
    @Mock TestableJoystick controller;
    @Mock Factory f;
    @Mock Slider slider;
    @Mock
    IntakeSubsystem control;
    RobotContainer container;
    Robot robot;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(this.controller.getAxisY()).thenReturn(0.0);
        when(this.controller.getTwist()).thenReturn(0.0);
        when(this.f.getSlider("Max Speed", 1.0, 0.0, 1.0)).thenReturn(this.slider);
        TestableCommand.activateTestMode();

        var injection = new RobotInjection();
        injection.joystick = this.controller;
        injection.driveSubsystem = this.dr;
        injection.intakeSubsystem = this.control;
        this.container = new RobotContainer(injection);
        this.robot = new Robot(this.container);
    }

    @Test
    public void TestShuffleBoard() {
        when(this.slider.getDouble()).thenReturn(0.8);
        var drive = new DriveCommand(f, dr, controller);

        drive.execute();

        verify(this.dr, times(1)).setMaxSpeed(0.8);
    }

    @Test
    public void TestJoystickControl() {
        this.robot.teleopInit();

        // Test Command runs
        when(controller.getTwist()).thenReturn(0.5);
        when(controller.getAxisY()).thenReturn(0.5);
        when(this.controller.getRawButton(Joystick.AxisType.kTwist.value)).thenReturn(true);
        when(this.controller.getRawButton(Joystick.AxisType.kY.value)).thenReturn(true);

        assert this.container.getDriveCommand() != null;
        this.container.getDriveCommand().execute();

        verify(dr, times(1)).drive(-0.5, 0.5);

        // Test you can change speed/turn
        when(controller.getTwist()).thenReturn(0.7);
        when(controller.getAxisY()).thenReturn(0.2);

        this.container.getDriveCommand().execute();

        verify(dr, times(1)).drive(-0.2, 0.7);

        // Test you can stop the robot
        when(controller.getTwist()).thenReturn(0.0);
        when(controller.getAxisY()).thenReturn(0.0);
        when(this.controller.getRawButton(Joystick.AxisType.kTwist.value)).thenReturn(false);
        when(this.controller.getRawButton(Joystick.AxisType.kY.value)).thenReturn(false);

        this.container.getDriveCommand().execute();

        verify(dr, times(1)).drive(-0.0, 0.0);

        when(controller.getTwist()).thenReturn(0.5);
        when(controller.getAxisY()).thenReturn(0.5);
        when(this.controller.getRawButton(Joystick.AxisType.kTwist.value)).thenReturn(true);
        when(this.controller.getRawButton(Joystick.AxisType.kY.value)).thenReturn(true);

        this.container.getDriveCommand().execute();

        verify(dr, times(2)).drive(-0.5, 0.5);
    }
}