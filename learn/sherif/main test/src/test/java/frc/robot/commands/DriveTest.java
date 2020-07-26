package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

public class DriveTest {
    @Mock DriveTrain dr;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute() {
        var command = new Drive(dr, 0.5, 0.2);
        command.execute();

        verify(dr).drive(0.5, 0.2);
        assertTrue(command.isFinished());
    }
}