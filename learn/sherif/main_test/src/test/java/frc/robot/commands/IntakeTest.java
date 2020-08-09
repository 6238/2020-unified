package frc.robot.commands;


import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.subsystems.IntakeControl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class IntakeTest {
    @Mock IntakeControl intakeControl;
    @Mock Timer timer;
    @Mock TestableJoystick m_controller;
    RobotContainer container;
    Robot robot;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestableCommand.activateTestMode();
        var injection = new RobotInjection();
        injection.joystick = this.m_controller;
        injection.intakeControl = this.intakeControl;

        this.container = new RobotContainer(injection);
        this.robot = new Robot(this.container);
    }

    @Test
    public void TestIntake() {
        var intake = new Intake(intakeControl, m_controller);
        when(m_controller.getRawButton(Constants.FEEDER_BUTTON)).thenReturn(true);
        intake.execute();
        verify(intakeControl).setFeederSpeed(Intake.FEEDER_SPEED);
    }

    @Test
    public void TestIntakeCommand() {
        robot.robotInit();
        robot.teleopInit();

        when(this.m_controller.getRawButton(Constants.THROAT_BUTTON)).thenReturn(true);

        robot.robotPeriodic();

        verify(this.intakeControl, times(1)).setThroatSpeed(Intake.THROAT_SPEED);
    }
}
