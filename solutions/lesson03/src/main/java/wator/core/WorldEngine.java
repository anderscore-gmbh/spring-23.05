package wator.core;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This service puts everything together and manages the world.
 */
@Component
public class WorldEngine implements InitializingBean {
    private static final Logger log = LogManager.getLogger(WorldEngine.class);
    
    private final Torus torus;
    private final FishStrategy[] strategies;
    private final Random rnd;
    private double fishRate = 0.3;
    private double sharkRate = 0.1;
    
    private int epoch;

    /**
     * Factory method to create a populated world.
     */
    public static WorldEngine createPopulatedWorld(double fishRate, double sharkRate, Random rnd, Torus torus, FishStrategy[] strategies) {
        WorldEngine worldEngine = new WorldEngine(rnd, torus, strategies);
        worldEngine.fishRate = fishRate;
        worldEngine.sharkRate = sharkRate;
        worldEngine.populate();
        return worldEngine;
    }

    /**
     * Constructor used for autowiring for using annotation based configuration.
     */
    @Autowired
    WorldEngine(Random rnd, Torus torus, FishStrategy[] strategies) {
        this.torus = torus;
        this.strategies = strategies;
        this.rnd = rnd;
    }

    /**
     * Initialize after all properties have been set.
     */
    @Override
    public void afterPropertiesSet() {
        populate();
    }

    /**
     * Creates the world's initial population
     */
    private void populate() {
        log.info("populating...");
        for (int y = 0; y < torus.getHeight(); y++) {
            for (int x = 0; x < torus.getWidth(); x++) {
                double r = rnd.nextDouble();
                if (r < sharkRate) {
                    torus.setFishAt(x, y, new Shark());
                } else if (r < sharkRate + fishRate) {
                    torus.setFishAt(x, y, new Fish());
                }
            }
        }
    }

    /**
     * Each call to this method forwards the world by one chronos.
     */
    public void tick() {
        for (int y = 0; y < torus.getHeight(); y++) {
            for (int x = 0; x < torus.getWidth(); x++) {
                Fish fish = torus.getFishAt(x, y);
                if (fish != null) {
                    fish.setX(x);
                    fish.setY(y);
                    fish.setEpoch(epoch);
                    tick(fish);
                }
            }
        }
        epoch = 1 - epoch;
    }

    /**
     * Simulate the life of a fish for one chronos by calling each available strategy
     * for that fish. Each strategy must decide by itself whether it is responsible or not.
     */
    private void tick(Fish fish) {
        for (FishStrategy strategy : strategies) {
            strategy.tick(fish);
        }
    }

    @Override
    public String toString() {
        return "WorldEngine [fishRate=" + fishRate + ", sharkRate=" + sharkRate + "]";
    }
}
