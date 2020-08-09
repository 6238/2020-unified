package frc.robot.io;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import java.util.Map;

public class Slider {
    private final SimpleWidget value;
    public Slider(String name, double defaultValue) {
        this(name, defaultValue, 0, 1.0);
    }

    public Slider(String name, double defaultValue, double min, double max) {
        value = BoardManager.getManager().getTab()
                .add(name, defaultValue)
                .withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", min, "max", max));
    }

    public Slider(String name, double defaultValue, int x, int y, int w, int h) {
        this(name, defaultValue, 0.0, 1.0);
        value.withPosition(x, y)
                .withSize(w, h);
    }

    public Slider(String name, double defaultValue, double min, double max, int x, int y, int w, int h) {
        this(name, defaultValue, min, max);
        value.withPosition(x, y)
                .withSize(w, h);
    }

    public NetworkTableEntry getEntry() {
        return value.getEntry();
    }

    public double getDouble(double def) {
        return this.getEntry()
                .getDouble(def);
    }
}
