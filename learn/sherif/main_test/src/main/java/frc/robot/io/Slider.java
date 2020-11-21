package frc.robot.io;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class Slider {
    private final SimpleWidget value;
    private double lastDouble;

    public static HashMap<String, Slider> cache = new HashMap<>();

    @Nullable
    public static Slider checkCache(String name) {
        if (cache.containsKey(name)) {
            return cache.get(name);
        }
        return null;
    }

    private void saveToCache() {
        Slider.cache.put(value.getTitle(), this);
    }

    public Slider(String name, double defaultValue) {
        this(name, defaultValue, 0, 1.0);
        saveToCache();
    }

    public Slider(String name, double defaultValue, double min, double max) {
        value = BoardManager.getManager().getTab().add(name, defaultValue).withWidget(BuiltInWidgets.kNumberSlider)
                .withProperties(Map.of("min", min, "max", max));
        lastDouble = defaultValue;
        saveToCache();
    }

    public Slider(String name, double defaultValue, int x, int y, int w, int h) {
        this(name, defaultValue, 0.0, 1.0);
        value.withPosition(x, y).withSize(w, h);
        saveToCache();
    }

    public Slider(String name, double defaultValue, double min, double max, int x, int y, int w, int h) {
        this(name, defaultValue, min, max);
        value.withPosition(x, y).withSize(w, h);
        saveToCache();
    }

    public NetworkTableEntry getEntry() {
        return value.getEntry();
    }

    public double getDouble(double def) {
        lastDouble = getEntry().getDouble(def);
        return getEntry().getDouble(def);
    }

    public double getDouble() {
        return getDouble(lastDouble);
    }
}
