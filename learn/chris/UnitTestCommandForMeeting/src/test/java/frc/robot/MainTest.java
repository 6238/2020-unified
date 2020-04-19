package frc.robot;

import org.junit.*;

import frc.robot.subsystems.*;


public class MainTest {

    @Test
    public void testElevatorMotorRunsWhenElevatorStarted() {

        MotorControllerFactory f = new MotorControllerFactory();

        Assert.assertEquals(f.getMotorControllers().size(), 0);

        ExampleSubsystem e = new ExampleSubsystem(f);
        Assert.assertEquals(f.getMotorControllers().size(), 2);

        e.start();

        e.periodic();

        Assert.assertEquals(f.getMotorControllers().size(), 2);
        Assert.assertEquals(f.getMotorControllers().get(0).getCanBusId(), 13);
        Assert.assertEquals(f.getMotorControllers().get(1).getCanBusId(), 16);
        Assert.assertEquals(f.getMotorControllers().get(0).getSet(), 0.5, 0.0001);
        Assert.assertEquals(f.getMotorControllers().get(1).getSet(), -0.5, 0.0001);

    }

    @Test
    public void testElevatorMotorStopsWhenSubsystemStopped() {

        MotorControllerFactory f = new MotorControllerFactory();

        ExampleSubsystem e = new ExampleSubsystem(f);

        e.start();
        e.periodic();
        e.stop();
        e.periodic();

        Assert.assertEquals(f.getMotorControllers().size(), 2);
        Assert.assertEquals(f.getMotorControllers().get(0).getSet(), 0, 0.0001);
        Assert.assertEquals(f.getMotorControllers().get(1).getSet(), 0, 0.0001);

    }
}