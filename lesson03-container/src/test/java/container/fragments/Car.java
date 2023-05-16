package container.fragments;

import java.util.List;
import java.util.Properties;

public class Car {
    private Engine engine;
    private String color;
    private List<Feature> features;
    private Properties properties;

    public void startEngine() {
        engine.setStarted(true);
    }

    public void stopEngine() {
        engine.setStarted(false);
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
