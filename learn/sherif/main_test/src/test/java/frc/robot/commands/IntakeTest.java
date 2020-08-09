package frc.robot.commands;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableCommand;
import frc.robot.subsystems.IntakeControl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class IntakeTest {
    @Mock IntakeControl intakeControl;
    @Mock Timer timer;
    @Mock Joystick controller;
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
        var intake = new Intake(intakeControl, null);

        when(timer.get()).thenReturn(0.0);
        intake.execute();
        verify(intakeControl, times(1)).setThroatSpeed(Intake.THROAT_SPEED);

        when(timer.get()).thenReturn(0.4);
        intake.execute();
        verify(intakeControl, times(1)).setThroatSpeed(Intake.THROAT_SPEED);

        when(timer.get()).thenReturn(0.8);
        intake.execute();
        verify(intakeControl, times(1)).setThroatSpeed(Intake.THROAT_SPEED);
        verify(intakeControl, times(1)).setElevatorSpeed(Intake.ELEVATOR_SPEED);

        when(timer.get()).thenReturn(1.2);
        intake.execute();
        verify(intakeControl, times(1)).setThroatSpeed(Intake.THROAT_SPEED);
        verify(intakeControl, times(1)).setElevatorSpeed(Intake.ELEVATOR_SPEED);
        verify(intakeControl, times(1)).stop();

        when(timer.get()).thenReturn(2.1);
        intake.execute();
        Assert.assertTrue(intake.isFinished());
    }

    @Test
    public void TestIntakeCommand() {
        robot.robotInit();

        when(this.controller.getRawButton(Joystick.ButtonType.kTop.value)).thenReturn(true);
        when(this.controller.getTopPressed()).thenReturn(true);

        robot.robotPeriodic();

        verify(this.intakeControl, times(1)).setThroatSpeed(Intake.THROAT_SPEED);
    }
}
