package frc.robot.dashboard;

import java.util.ArrayList;
import java.util.HashMap;

public class Dashboard {
    // private final ArrayList<Slider> sliders = new ArrayList<Slider>();
    private final HashMap<String, Slider> sliders = new HashMap();
    // private final ArrayList<ToggleButton> toggleButtons = new ArrayList<ToggleButton>();
    private final HashMap<String, ToggleButton> toggleButtons = new HashMap();

    public Dashboard() {}

    public Slider getSlider(String name, double defaultValue) {
        if (sliders.containsKey(name)) {
            return sliders.get(name);
        }
        Slider slider = new Slider(name, defaultValue);
        sliders.put(name, slider);
        return slider;
    }

    public Slider getSlider(String name, double defaultValue, double min, double max) {
		if (sliders.containsKey(name)) {
            return sliders.get(name);
        }
        Slider slider = new Slider(name, defaultValue, min, max);
        sliders.put(name, slider);
        return slider;
	}

    public Slider getSlider(String name, double defaultValue, int x, int y, int w, int h) {
        if (sliders.containsKey(name)) {
            return sliders.get(name);
        }
        Slider slider = new Slider(name, defaultValue, x, y, w, h);
        sliders.put(name, slider);
        return slider;
    }

    public Slider getSlider(String name, double defaultValue, double min, double max, int x, int y, int w, int h) {
        if (sliders.containsKey(name)) {
            return sliders.get(name);
        }
        Slider slider = new Slider(name, defaultValue, min, max, x, y, w, h);
        sliders.put(name, slider);
        return slider;
    }

    public ToggleButton getToggleButton(String name, boolean defaultValue) {
        if (toggleButtons.containsKey(name)) {
            return toggleButtons.get(name);
        }
        ToggleButton toggleButton = new ToggleButton(name, defaultValue);
        toggleButtons.put(name, toggleButton);
        return toggleButton;
    }
}