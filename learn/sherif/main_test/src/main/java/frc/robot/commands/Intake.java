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
    public double[] speeds = {1.0, -1.0, 1.0};
    public boolean elevatorLeft = false;
    public boolean elevatorReverse = false;
    public boolean throat = false;
    public boolean feeder = false;

    private final IntakeControl intakeControl;
    private final TestableJoystick joystick;
    private Slider[] sliders = {null, null, null};
    private boolean feederReverse;


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
        useSlider(f);
    }

    public void useSlider(Factory f) {
        this.sliders[0] = f.getSlider("Throat Speed", 1.0, -1.0, 1.0);
        this.sliders[1] = f.getSlider("Elevator Speed", 1.0, -1.0, 1.0);
        this.sliders[2] = f.getSlider("Feeder Speed", 1.0, -1.0, 1.0);
    }

    private void readSliders() {
        int i = 0;
        for (Slider slider: sliders) {
            if (slider != null) {
                speeds[i] = slider.getDouble();
            }
            i++;
        }
    }

    @Override
    public void execute() {
        readSliders();
        this.elevatorLeft = this.joystick.getRawButton(ELEVATOR_BUTTON);
        this.elevatorReverse = this.joystick.getRawButton(ELEVATOR_REVERSE_BUTTON);
        this.feeder = this.joystick.getRawButton(FEEDER_BUTTON);
        this.feederReverse = this.joystick.getRawButton(FEEDER_REVERSE_BUTTON);

        var elevatorSpeed = this.elevatorReverse ? -this.speeds[ELEVATOR_SPEED] : this.speeds[ELEVATOR_SPEED];
        var feederSpeed = this.feederReverse ? -this.speeds[FEEDER_SPEED] : this.speeds[FEEDER_SPEED];

        this.intakeControl.setElevatorSpeed(this.elevatorLeft || this.elevatorReverse ? elevatorSpeed: 0);
        this.intakeControl.setFeederSpeed(this.feeder || this.feederReverse ? feederSpeed: 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
