package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.helpers.TestableCommand;
import frc.robot.subsystems.IntakeControl;

import static frc.robot.Constants.*;

/**
 * The command to intake a ball
 * @author sherif
 */
public class Intake extends TestableCommand {
    public static final double THROAT_SPEED = 1.0;
    public static final double ELEVATOR_SPEED = -1.0;
    public static final double FEEDER_SPEED = 1.0;
    public boolean elevatorLeft = false;
    public boolean elevatorRight = false;
    public boolean throat = false;
    public boolean feeder = false;

    private final IntakeControl m_intakeControl;
    private final Joystick m_joystick;

    private int m_state = 0;
    private double m_nextTime = 0;

    /**
     * Creates and starts an intake
     * @param intakeControl the IntakeControl subsystem
     */
    public Intake(IntakeControl intakeControl, Joystick joystick) {

        this.m_intakeControl = intakeControl;
        this.m_joystick = joystick;
        addRequirements(intakeControl);
    }

    @Override
    public void execute() {
        System.out.println("executed");
        this.elevatorLeft = this.m_joystick.getRawButton(JOYSTICK_BUTTON_3);
        this.elevatorRight = this.m_joystick.getRawButton(JOYSTICK_BUTTON_4);
        this.throat = this.m_joystick.getRawButton(JOYSTICK_BUTTON_5);
        this.feeder = this.m_joystick.getRawButton(JOYSTICK_BUTTON_6);

        System.out.println("feeder: " + this.feeder);

        this.m_intakeControl.setElevatorSpeed(this.elevatorLeft || this.elevatorRight ? ELEVATOR_SPEED : 0);
        this.m_intakeControl.setThroatSpeed(this.throat ? THROAT_SPEED : 0);
        this.m_intakeControl.setFeederSpeed(this.feeder ? FEEDER_SPEED : 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
