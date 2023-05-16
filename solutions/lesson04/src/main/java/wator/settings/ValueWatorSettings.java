package wator.settings;

import org.springframework.beans.factory.annotation.Value;

/**
 * Musterlösung zu Aufgabe 2
 */
public class ValueWatorSettings implements WatorSettings {
    @Value("${fish.breedTime:4}")
    private int fishBreedTime = 3;

    @Value("${shark.breedTime:6}")
    private int sharkBreedTime = 5;

    @Value("${shark.starveTime:5}")
    private int sharkStarveTime = 4;

    @Override
    public int getFishBreedTime() {
        return fishBreedTime;
    }

    @Override
    public int getSharkBreedTime() {
        return sharkBreedTime;
    }

    @Override
    public int getSharkStarveTime() {
        return sharkStarveTime;
    }
}
