package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableCommand;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.ShooterSubsystem;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShootCommandTest {
    @Mock ShooterSubsystem shooterSubsystem;
    @Mock Factory f;
    @Mock Slider slider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestableCommand.activateTestMode();
        when(slider.getDouble()).thenReturn(0.8);
        when(f.getSlider("Shooter speed: ", Constants.INITIAL_SHOOTER, 0.0, 1.0)).thenReturn(this.slider);
    }

    @Test
    public void TestShuffleBoard() {
        var command = new ShootCommand(f, shooterSubsystem);

        command.execute();

        verify(slider).getDouble();
        verify(shooterSubsystem).setSpeed(0.8);
    }

    @Test
    public void TestShootCommand() {
        when(slider.getDouble()).thenReturn(0.8);
        var injection = new RobotInjection();
        injection.shooterSubsystem = shooterSubsystem;
        injection.factory = f;
        var container = new RobotContainer(injection);
        var robot = new Robot(container);

        robot.teleopInit();

        robot.robotPeriodic();

        verify(this.shooterSubsystem).setSpeed(0.8);
    }
}