package wator.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Random;

/**
 * This service puts everything together and manages the world.
 */
@Named
public class WorldEngine {
    private static final Logger log = LogManager.getLogger(WorldEngine.class);

    private final Torus torus;
    private final FishStrategy[] strategies;

    private int epoch;

    /**
     * Factory method to create a populated world.
     */
    public static WorldEngine createPopulatedWorld(double fishRate, double sharkRate, Random rnd, Torus torus, FishStrategy[] strategies) {
        WorldEngine worldEngine = new WorldEngine(torus, strategies);
        worldEngine.populate(rnd, fishRate, sharkRate);
        return worldEngine;
    }

    private WorldEngine(Torus torus, FishStrategy[] strategies) {
        this.torus = torus;
        this.strategies = strategies;
    }

    /**
     * Constuctor used for autowiring
     */
    @Inject
    WorldEngine(Random rnd, Torus torus, FishStrategy[] strategies) {
        this(torus, strategies);
        populate(rnd, 0.3, 0.1);
    }

    /**
     * Creates the world's initial population
     */
    private void populate(Random rnd, double fishRate, double sharkRate) {
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
}
