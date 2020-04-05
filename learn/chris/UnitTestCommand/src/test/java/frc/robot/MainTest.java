package frc.robot;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import frc.robot.subsystems.ExampleSubsystem;

public class MainTest {
    @Test
    public void testFrameworkWorks() {
        Assert.assertEquals(2, 2);
    }

    @Test
    public void exampleCommandStarts() {

        final ExampleSubsystem subsystem = new ExampleSubsystem();

        assertEquals(subsystem.getPeriodicCount(), 0);
        subsystem.periodic();

        assertEquals(subsystem.getPeriodicCount(), 1);
    }
}