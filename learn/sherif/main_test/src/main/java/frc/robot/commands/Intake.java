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
    public double m_speeds[] = {1.0, -1.0, 1.0};
    public boolean elevatorLeft = false;
    public boolean elevatorRight = false;
    public boolean throat = false;
    public boolean feeder = false;

    private final IntakeControl m_intakeControl;
    private final TestableJoystick m_joystick;
    private Slider m_sliders[] = {null, null, null};


    /**
     * Creates and starts an intake
     * @param intakeControl the IntakeControl subsystem
     */
    public Intake(IntakeControl intakeControl, TestableJoystick joystick) {
        this.m_intakeControl = intakeControl;
        this.m_joystick = joystick;
        addRequirements(intakeControl);
    }

    public Intake(Factory f, IntakeControl intakeControl, TestableJoystick joystick) {
        this(intakeControl, joystick);
        this.m_sliders[0] = f.getSlider("Throat Speed", 1.0, -1.0, 1.0);
        this.m_sliders[1] = f.getSlider("Elevator Speed", 1.0, -1.0, 1.0);
        this.m_sliders[2] = f.getSlider("Elevator Speed", 1.0, -1.0, 1.0);
    }

    private void readSliders() {
        int i = 0;
        for (Slider slider: m_sliders) {
            if (slider != null) {
                double speed = slider.getDouble(m_speeds[i]);
                m_speeds[i] = speed;
            }
            i++;
        }
    }

    @Override
    public void execute() {
        readSliders();
        System.out.println("executed");
        this.elevatorLeft = this.m_joystick.getRawButton(ELEVATOR_LEFT_BUTTON);
        this.elevatorRight = this.m_joystick.getRawButton(ELEVATOR_RIGHT_BUTTON);
        this.throat = this.m_joystick.getRawButton(THROAT_BUTTON);
        this.feeder = this.m_joystick.getRawButton(FEEDER_BUTTON);

        System.out.println("feeder: " + this.feeder);

        this.m_intakeControl.setElevatorSpeed(this.elevatorLeft || this.elevatorRight ? this.m_speeds[ELEVATOR_SPEED] : 0);
        this.m_intakeControl.setThroatSpeed(this.throat ? this.m_speeds[THROAT_SPEED] : 0);
        this.m_intakeControl.setFeederSpeed(this.feeder ? this.m_speeds[FEEDER_SPEED] : 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
