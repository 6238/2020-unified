package frc.robot;

import org.junit.*;

import frc.robot.subsystems.ExampleSubsystem;

public class MainTest {
    @Test
    public void testFrameworkWorks() {
        Assert.assertEquals(2, 2);
    }

    @Test
    public void exampleCommandStarts() {

        ExampleSubsystem subsystem = new ExampleSubsystem();

        subsystem.periodic();

    }
}