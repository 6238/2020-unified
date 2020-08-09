package frc.robot.helpers;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * A wrapper over CommandBase that allows test mode to be toggled for Unit
 * Testing
 * 
 * @author sherif
 */
public class TestableInstantCommand extends InstantCommand {
    private static boolean m_test_mode = false;

    @Override
    public boolean runsWhenDisabled() {
        return TestableInstantCommand.m_test_mode;
    }

    public static void activateTestMode() {
        TestableInstantCommand.m_test_mode = true;
    }

    public static void deactivateTestMode() {
        TestableInstantCommand.m_test_mode = false;
    }

    /**
     * Creates a new InstantCommand that runs the given Runnable with the given
     * requirements.
     *
     * @param toRun        the Runnable to run
     * @param requirements the subsystems required by this command
     */
    public TestableInstantCommand(Runnable toRun, Subsystem... requirements) {
        super(toRun, requirements);
    }
}
