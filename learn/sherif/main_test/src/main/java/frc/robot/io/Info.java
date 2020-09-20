package frc.robot.io;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import java.util.Map;

public class Info {
    private final SimpleWidget value;

    public Info(String name, double defaultValue) {
        value = BoardManager.getManager().getTab()
                .add(name, defaultValue)
                .withWidget(BuiltInWidgets.kDial);
    }

    public Info withMinMax(double min, double max) {
        value.withProperties(Map.of(
                "min", min,
                "max", max
        ));
        return this;
    }

    public void setOutput(double out) {
        value.getEntry()
                .setDouble(out);
    }
}
