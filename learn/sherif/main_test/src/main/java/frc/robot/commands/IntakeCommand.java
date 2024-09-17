package frc.robot.commands;

import frc.robot.helpers.TestableCommand;
import frc.robot.helpers.TestableJoystick;
import frc.robot.io.Slider;
import frc.robot.subsystems.Factory;
import frc.robot.subsystems.IntakeSubsystem;

import static frc.robot.Constants.*;

/**
 * The command to intake a ball
 * @author sherif
 */
public class IntakeCommand extends TestableCommand {
    public static final int THROAT_SPEED = 0;
    public static final int ELEVATOR_SPEED = 1;
    public static final int FEEDER_SPEED = 2;
    public double[] speeds = {1.0, -1.0, 1.0};
    public boolean elevatorLeft = false;
    public boolean elevatorReverse = false;
    public boolean throat = false;
    public boolean feeder = false;

    private final IntakeSubsystem intakeSubsystem;
    private final TestableJoystick joystick;
    private Slider[] sliders = new Slider[3];
    private boolean feederReverse;


    /**
     * Creates and starts an intake
     * @param intakeSubsystem the IntakeControl subsystem
     */

    public IntakeCommand(Factory f, IntakeSubsystem intakeSubsystem, TestableJoystick joystick) {
        this.intakeSubsystem = intakeSubsystem;
        this.joystick = joystick;
        sliders[0] = f.getSlider("Throat Speed", 1.0, -1.0, 1.0);
        sliders[1] = f.getSlider("Elevator Speed", 1.0, -1.0, 1.0);
        sliders[2] = f.getSlider("Feeder Speed", 1.0, -1.0, 1.0);
    }


    private void readSliders() {
        int i = 0;
        for (Slider slider : sliders) {
            try {
                speeds[i] = slider.getDouble();
            } catch (NullPointerException ignored) {}
            i++;
        }
    }

    @Override
    public void execute() {
        readSliders();
        elevatorLeft = joystick.getRawButton(ELEVATOR_BUTTON);
        elevatorReverse = joystick.getRawButton(ELEVATOR_REVERSE_BUTTON);
        feeder = joystick.getRawButton(FEEDER_BUTTON);
        feederReverse = joystick.getRawButton(FEEDER_REVERSE_BUTTON);

        var elevatorSpeed = elevatorReverse ? -speeds[ELEVATOR_SPEED] : speeds[ELEVATOR_SPEED];
        var feederSpeed = feederReverse ? -speeds[FEEDER_SPEED] : speeds[FEEDER_SPEED];

        intakeSubsystem.setElevatorSpeed(elevatorLeft || elevatorReverse ? elevatorSpeed: 0);
        intakeSubsystem.setFeederSpeed(feeder || feederReverse ? feederSpeed: 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
