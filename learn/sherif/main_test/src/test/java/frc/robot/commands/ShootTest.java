package frc.robot.commands;

import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.ShooterController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShootTest {
    @Mock ShooterController shooterController;
    @Mock Factory f;
    @Mock Slider slider;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(f.getSlider("Shooter speed: ", 1.0, 0.0, 1.0)).thenReturn(this.slider);
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
}