package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.mockito.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import frc.robot.subsystems.*;

public class MainTest {

    TalonFactory f;
    
    WPI_TalonSRX front;
    WPI_TalonSRX back;

    @Before
    public void setup() {
        f = mock(TalonFactory.class);
        front = mock(WPI_TalonSRX.class);
        back = mock(WPI_TalonSRX.class);
        
        when(f.create(0)).thenReturn(front);
        when(f.create(1)).thenReturn(back);
    }

    @Test
    public void testElevatorCreatesMotorControllers() {
        ElevatorSubsystem e = new ElevatorSubsystem(f);
        
        verify(f).create(0);
        verify(f).create(1);
    }
    
    @Test
    public void testElevatorStopCausesMotorsToStop() {
        ElevatorSubsystem e = new ElevatorSubsystem(f);
        
        e.stop();
        e.periodic();

        verify(front).set(ControlMode.PercentOutput, 0.0);
        verify(back).set(ControlMode.PercentOutput, -0.0);
    }
    
    @Test
    public void testElevatorStartCausesMotorsToStart() {
        ElevatorSubsystem e = new ElevatorSubsystem(f);
        
        e.start();
        e.periodic();

        verify(front).set(ControlMode.PercentOutput, 0.5);
        verify(back).set(ControlMode.PercentOutput, -0.5);
    }
}