package frc.robot.helpers;

import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * A wrapper over CommandBase that allows test mode to be toggled for
 * Unit Testing
 * @author sherif
 */
public class TestableCommand extends CommandBase {
    private static boolean testMode = false;

    @Override
    public boolean runsWhenDisabled() {
        return TestableCommand.testMode;
    }

    public static void activateTestMode() {
        TestableCommand.testMode = true;
    }

    public static void deactivateTestMode() {
        TestableCommand.testMode = false;
    }
}
