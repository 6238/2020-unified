package frc.robot.dashboard;

import java.util.ArrayList;

public class Dashboard {
    private final ArrayList<Slider> sliders = new ArrayList<Slider>();
    private final ArrayList<ToggleButton> toggleButtons = new ArrayList<ToggleButton>();

    public static int instanceCount = 0;

    public Dashboard() {
        if (instanceCount > 0) {
            throw new RuntimeException("Only one Dashboard is allowed per robot.");
        }
        instanceCount++;
    }

    public Slider getSlider(String name, double defaultValue) {
        for (Slider slider : sliders) {
            if (slider.getName().equals(name))
                return slider;
        }
        Slider slider = new Slider(name, defaultValue);
        sliders.add(slider);
        return slider;
    }

    public Slider getSlider(String name, double defaultValue, double min, double max) {
		for (Slider slider : sliders) {
            if (slider.getName().equals(name))
                return slider;
        }
        Slider slider = new Slider(name, defaultValue, min, max);
        sliders.add(slider);
        return slider;
	}

    public Slider getSlider(String name, double defaultValue, int x, int y, int w, int h) {
        for (Slider slider : sliders) {
            if (slider.getName().equals(name))
                return slider;
        }
        Slider slider = new Slider(name, defaultValue, x, y, w, h);
        sliders.add(slider);
        return slider;
    }

    public Slider getSlider(String name, double defaultValue, double min, double max, int x, int y, int w, int h) {
        for (Slider slider : sliders) {
            if (slider.getName().equals(name))
                return slider;
        }
        Slider slider = new Slider(name, defaultValue, min, max, x, y, w, h);
        sliders.add(slider);
        return slider;
    }

    public ToggleButton getToggleButton(String name, boolean defaultValue) {
        for (ToggleButton toggleButton : toggleButtons) {
            if (toggleButton.getName().equals(name))
                return toggleButton;
        }
        ToggleButton toggleButton = new ToggleButton(name, defaultValue);
        toggleButtons.add(toggleButton);
        return toggleButton;
    }
}