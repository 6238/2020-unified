package frc.robot.commands;


import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeControl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class IntakeTest {
    @Mock IntakeControl intakeControl;
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
        injection.intakeControl = this.intakeControl;

        this.container = new RobotContainer(injection);
        this.robot = new Robot(this.container);
    }

    @Test
    public void TestIntake() {
        var intake = new Intake(intakeControl, controller);
        when(controller.getRawButton(Constants.FEEDER_BUTTON)).thenReturn(true);
        intake.execute();
        verify(intakeControl).setFeederSpeed(1.0);
    }

    @Test
    public void TestSliderIntake() {
        when(f.getSlider("Elevator Speed", 1.0, -1.0, 1.0)).thenReturn(throat_slider);
        when(throat_slider.getDouble()).thenReturn(0.3);

        when(controller.getRawButton(Constants.ELEVATOR_REVERSE_BUTTON)).thenReturn(true);
        var intake = new Intake(f, intakeControl, controller);

        intake.execute();

        verify(intakeControl).setElevatorSpeed(-0.3);
    }

    @Test
    public void TestIntakeCommand() {
        robot.robotInit();
        robot.teleopInit();

        when(this.controller.getRawButton(Constants.ELEVATOR_REVERSE_BUTTON)).thenReturn(true);

        robot.robotPeriodic();

        verify(this.intakeControl, times(1)).setElevatorSpeed(-1.0);
    }
}
