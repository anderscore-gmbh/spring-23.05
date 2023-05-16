package wator.settings;

public class FixedWatorSettings implements WatorSettings {

    private int fishBreedTime = 3;

    private int sharkBreedTime = 5;

    private int sharkStarveTime = 4;

    @Override
    public int getFishBreedTime() {
        return fishBreedTime;
    }

    public void setFishBreedTime(int fishBreedTime) {
        this.fishBreedTime = fishBreedTime;
    }

    @Override
    public int getSharkBreedTime() {
        return sharkBreedTime;
    }

    public void setSharkBreedTime(int sharkBreedTime) {
        this.sharkBreedTime = sharkBreedTime;
    }

    @Override
    public int getSharkStarveTime() {
        return sharkStarveTime;
    }

    public void setSharkStarveTime(int sharkStarveTime) {
        this.sharkStarveTime = sharkStarveTime;
    }
}
