package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.helpers.TestableCommand;
import frc.robot.subsystems.IntakeControl;

/**
 * The command to intake a ball
 * @author sherif
 */
public class Intake extends TestableCommand {
    public static final double THROAT_SPEED = 0.5;
    public static final double ELEVATOR_SPEED = 0.5;

    private final IntakeControl m_intakeControl;
    private final Timer m_timer;

    private int m_state = 0;
    private double m_nextTime = 0;

    /**
     * Creates and starts an intake
     * @param intakeControl the IntakeControl subsystem
     * @param timer a timer to keep track of time
     */
    public Intake(IntakeControl intakeControl, Timer timer) {
        this.m_timer = timer;
        this.m_timer.reset();
        this.m_timer.start();

        this.m_intakeControl = intakeControl;
        addRequirements(intakeControl);
    }

    @Override
    public void execute() {
        switch (this.m_state) {
            // Initial state, activate the throat to intake
            case 0: {
                if (m_timer.get() >= m_nextTime) {
                    m_intakeControl.setThroatSpeed(THROAT_SPEED);
                    m_nextTime = 0.5;
                    m_state++;
                }
                break;
            }
            // Proceed to take the ball up the elevator
            case 1: {
                if (m_timer.get() >= m_nextTime) {
                    m_intakeControl.setElevatorSpeed(ELEVATOR_SPEED);
                    m_nextTime = 1.0;
                    m_state++;
                }
                break;
            }
            // Final state, de-activate and finish the command
            case 2: {
                if (m_timer.get() >= m_nextTime) {
                    m_intakeControl.stop();
                    m_state++;
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return this.m_state == 3;
    }
}
