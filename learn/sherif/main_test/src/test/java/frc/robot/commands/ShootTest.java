package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.helpers.RobotInjection;
import frc.robot.helpers.TestableCommand;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.ShooterController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShootTest {
    @Mock ShooterController shooterController;
    @Mock Factory f;
    @Mock Slider slider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestableCommand.activateTestMode();
        when(f.getSlider("Shooter speed: ", Constants.INITIAL_SHOOTER, 0.0, 1.0)).thenReturn(this.slider);
        when(this.slider.getDouble()).thenReturn(0.8);
    }

    @Test
    public void TestShuffleBoard() {
        var command = new Shoot(this.shooterController);
        command.useSlider(this.f);

        command.execute();

        verify(this.slider).getDouble();
        verify(this.shooterController).setSpeed(0.8);
    }

    @Test
    public void TestShootCommand() {
        var injection = new RobotInjection();
        injection.shooterController = this.shooterController;
        var container = new RobotContainer(injection);
        var robot = new Robot(container);

        robot.teleopInit();

        robot.robotPeriodic();

        verify(this.shooterController).setSpeed(0.5);
    }
}