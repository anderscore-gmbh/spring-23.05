package wator.core;

/**
 * A shark is a fish that will starve if it does not eat any fish.
 */
public class Shark extends Fish {
    private int starveCount;

    public int getStarveCount() {
        return starveCount;
    }

    public void setStarveCount(int starveCount) {
        this.starveCount = starveCount;
    }
}
