package wator.settings;

import org.springframework.beans.factory.annotation.Value;

/**
 * Musterlösung zu Aufgabe 3
 */
public class ElWatorSettings implements WatorSettings {

    // Dazu muss die RandomFactory mit @Named("rnd") annotiert werden
    @Value("#{ ${fish.breedTime:null} ?: rnd.nextInt(18) + 2 }")
    private int fishBreedTime = 3;

    @Value("#{ settings.fishBreedTime * 2 }")
    private int sharkBreedTime = 5;

    @Value("#{ settings.sharkBreedTime - 2 }")
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
