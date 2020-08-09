package frc.robot.helpers;

import edu.wpi.first.wpilibj2.command.CommandBase;


/**
 * A wrapper over CommandBase that allows test mode to be toggled for
 * Unit Testing
 * @author sherif
 */
public class TestableCommand extends CommandBase {
    private static boolean m_test_mode = false;

    @Override
    public boolean runsWhenDisabled() {
        return TestableCommand.m_test_mode;
    }

    public static void activateTestMode() {
        TestableCommand.m_test_mode = true;
    }

    public static void deactivateTestMode() {
        TestableCommand.m_test_mode = false;
    }
}
