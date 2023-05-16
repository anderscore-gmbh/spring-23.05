package wator.core;

/**
 * These are the properties of a fish, thus all the information the corresponding strategy
 * needs to update a fish for one step of it's life (tick). The x and y coordinates are
 * given by it's current position on the {@link Torus}. The epoch is required to recognize
 * the fish that have already been processed in one turn. I. e. if the torus is processed
 * line by line an the fish moved south it'd reappear when processing the next row.
 */
public class Fish {
    private int x;
    private int y;
    private int epoch;
    private int breedCount;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public int getBreedCount() {
        return breedCount;
    }

    public void setBreedCount(int breedCount) {
        this.breedCount = breedCount;
    }
}
