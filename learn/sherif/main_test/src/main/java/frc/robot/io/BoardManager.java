package frc.robot.io;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants;

public class BoardManager {
    private static BoardManager manager = null;

    private BoardManager() {

    }

    public static BoardManager getManager() {
        if (manager == null) {
            manager = new BoardManager();
        }
        return manager;
    }

    public ShuffleboardTab getTab() {
        return Shuffleboard.getTab(Constants.DEFAULT_TAB);
    }
}
