package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.helpers.TestableCommand;
import frc.robot.subsystems.DriveTrain;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class DriveTest {
    @Mock DriveTrain dr;
    @Mock Joystick controller;
    RobotContainer container;
    Robot robot;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(this.controller.getThrottle()).thenReturn(0.0);
        when(this.controller.getDirectionRadians()).thenReturn(0.0);
        TestableCommand.activateTestMode();

        this.container = new RobotContainer(this.controller, dr);
        this.robot = new Robot(this.container);
    }

    @Test
    public void TestDriveCommand() {
        var command = new Drive(dr, 0.5, 0.2);
        command.execute();

        verify(dr).drive(0.5, 0.2);
        assertTrue(command.isFinished());
    }

@Test
public void TestMockedRobot() {
    this.robot.robotInit();

    when(controller.getDirectionRadians()).thenReturn(0.5);
    when(controller.getThrottle()).thenReturn(0.5);
    when(this.controller.getRawButton(Joystick.AxisType.kTwist.value)).thenReturn(true);
    when(this.controller.getRawButton(Joystick.AxisType.kThrottle.value)).thenReturn(true);

    this.robot.robotPeriodic();

    verify(dr, atLeast(1)).drive(0.5, 0.5);
}
}