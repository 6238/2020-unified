package frc.robot.commands;


import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeSubsystem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class IntakeCommandTest {
    @Mock
    IntakeSubsystem intakeSubsystem;
    @Mock TestableJoystick controller;
    @Mock Factory f;
    @Mock Slider throat_slider;
    RobotContainer container;
    Robot robot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestableCommand.activateTestMode();
        var injection = new RobotInjection();
        injection.joystick = this.controller;
        injection.intakeSubsystem = this.intakeSubsystem;

        this.container = new RobotContainer(injection);
        this.robot = new Robot(this.container);
    }

    @Test
    public void TestIntake() {
        var intake = new IntakeCommand(f, intakeSubsystem, controller);
        when(controller.getRawButton(Constants.FEEDER_BUTTON)).thenReturn(true);
        intake.execute();
        verify(intakeSubsystem).setFeederSpeed(1.0);
    }

    @Test
    public void TestSliderIntake() {
        when(f.getSlider("Elevator Speed", 1.0, -1.0, 1.0)).thenReturn(throat_slider);
        when(throat_slider.getDouble()).thenReturn(0.3);

        when(controller.getRawButton(Constants.ELEVATOR_REVERSE_BUTTON)).thenReturn(true);
        var intake = new IntakeCommand(f, intakeSubsystem, controller);

        intake.execute();

        verify(intakeSubsystem).setElevatorSpeed(-0.3);
    }

    @Test
    public void TestIntakeCommand() {
        robot.robotInit();
        robot.teleopInit();

        when(this.controller.getRawButton(Constants.ELEVATOR_REVERSE_BUTTON)).thenReturn(true);

        robot.robotPeriodic();

        verify(this.intakeSubsystem, times(1)).setElevatorSpeed(-1.0);
    }
}
