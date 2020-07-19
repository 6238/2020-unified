public class Slider{

    private NetworkTableEntry slider;

    public Slider(String name, double defaultValue){
        slider = OIConstants.kTab.add(name, defaultValue)
            .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
    }

    public Slider(String name, double defaultValue, double min, double max){
        slider = OIConstants.kTab.add(name, defaultValue)
            .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", min, "max", max)).getEntry();
    }

    public double get() {
        return slider.getDouble();
    }

    public void set(double speed){
        
    }


}