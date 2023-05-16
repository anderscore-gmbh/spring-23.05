package wator.settings;


import jakarta.inject.Inject;
import org.springframework.core.env.Environment;

/**
 * Musterlösung zu Aufgabe 4
 */
public class EnvWatorSettings implements WatorSettings {

    @Inject
    private Environment env;

    @Override
    public int getFishBreedTime() {
        return env.getProperty("fish.breedTime", Integer.class, 4);
    }

    @Override
    public int getSharkBreedTime() {
        return env.getProperty("shark.breedTime", Integer.class, 6);
    }

    @Override
    public int getSharkStarveTime() {
        return env.getProperty("shark.starveTime", Integer.class, 5);
    }
}
