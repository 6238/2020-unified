package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.helpers.TestableCommand;
import frc.robot.subsystems.IntakeControl;

public class Intake extends TestableCommand {
    public static final double THROAT_SPEED = 0.5;
    public static final double ELEVATOR_SPEED = 0.5;

    private final IntakeControl intakeControl;
    private final Timer timer;

    private int state = 0;
    private double nextTime = 0;

    public Intake(IntakeControl intakeControl, Timer timer) {
        this.timer = timer;
        this.timer.reset();
        this.timer.start();

        this.intakeControl = intakeControl;
        addRequirements(intakeControl);
    }

    @Override
    public void execute() {
        switch (this.state) {
            case 0: {
                if (timer.get() >= nextTime) {
                    intakeControl.setThroatSpeed(0.5);
                    nextTime = 0.5;
                    state++;
                }
                break;
            }
            case 1: {
                if (timer.get() >= nextTime) {
                    intakeControl.setElevatorSpeed(0.5);
                    nextTime = 1.0;
                    state++;
                }
                break;
            }
            case 2: {
                if (timer.get() >= nextTime) {
                    intakeControl.stop();
                    state++;
                }
                break;
            }
            default:
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return this.state == 3;
    }
}
