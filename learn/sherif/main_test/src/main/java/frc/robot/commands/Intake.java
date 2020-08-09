package frc.robot.commands;

import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeControl;

import static frc.robot.Constants.*;

/**
 * The command to intake a ball
 * @author sherif
 */
public class Intake extends TestableCommand {
    public static final int THROAT_SPEED = 0;
    public static final int ELEVATOR_SPEED = 1;
    public static final int FEEDER_SPEED = 2;
    public double speeds[] = {1.0, -1.0, 1.0};
    public boolean elevatorLeft = false;
    public boolean elevatorRight = false;
    public boolean throat = false;
    public boolean feeder = false;

    private final IntakeControl intakeControl;
    private final TestableJoystick joystick;
    private Slider sliders[] = {null, null, null};


    /**
     * Creates and starts an intake
     * @param intakeControl the IntakeControl subsystem
     */
    public Intake(IntakeControl intakeControl, TestableJoystick joystick) {
        this.intakeControl = intakeControl;
        this.joystick = joystick;
        addRequirements(intakeControl);
    }

    public Intake(Factory f, IntakeControl intakeControl, TestableJoystick joystick) {
        this(intakeControl, joystick);
        this.sliders[0] = f.getSlider("Throat Speed", 1.0, -1.0, 1.0);
        this.sliders[1] = f.getSlider("Elevator Speed", 1.0, -1.0, 1.0);
        this.sliders[2] = f.getSlider("Elevator Speed", 1.0, -1.0, 1.0);
    }

    private void readSliders() {
        int i = 0;
        for (Slider slider: sliders) {
            if (slider != null) {
                double speed = slider.getDouble(speeds[i]);
                speeds[i] = speed;
            }
            i++;
        }
    }

    @Override
    public void execute() {
        readSliders();
        System.out.println("executed");
        this.elevatorLeft = this.joystick.getRawButton(ELEVATOR_LEFT_BUTTON);
        this.elevatorRight = this.joystick.getRawButton(ELEVATOR_RIGHT_BUTTON);
        this.throat = this.joystick.getRawButton(THROAT_BUTTON);
        this.feeder = this.joystick.getRawButton(FEEDER_BUTTON);

        System.out.println("feeder: " + this.feeder);

        this.intakeControl.setElevatorSpeed(this.elevatorLeft || this.elevatorRight ? this.speeds[ELEVATOR_SPEED] : 0);
        this.intakeControl.setThroatSpeed(this.throat ? this.speeds[THROAT_SPEED] : 0);
        this.intakeControl.setFeederSpeed(this.feeder ? this.speeds[FEEDER_SPEED] : 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
