package container.fragments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Engine {
    private static final Logger log = LoggerFactory.getLogger(Engine.class);

    private final String type;
    private final int powerInKW;

    private boolean started;

    public Engine(String type, int powerInKW) {
        this.type = type;
        this.powerInKW = powerInKW;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
        log.info("started: {}", started);
    }
    
    public String toString() {
        return type + " (" + powerInKW + " kw)"; 
    }
}
