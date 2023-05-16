package wator.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This strategy handles the life of a fish. It's <a href="https://en.wikipedia.org/wiki/Wa-Tor">rules</a> are:
 * <ol>
 * <li>At each chronon, a fish moves randomly to one of the adjacent unoccupied squares.
 * If there are no free squares, no movement takes place.</li>
 * <li>Once a fish has survived a certain number of chronons it may reproduce. This is done as it moves to
 * a neighbouring square, leaving behind a new fish in its old position.
 * Its reproduction time is also reset to zero.</li>
 * </ol>
 * This class separates the logic from the domain object. Hence it is easy to replace that logic.
 */
@Component
public class FishStrategy {
    private final Supplier<Fish> fishSupplier;
    private int breedTime = 3;
    private Random rnd;
    private Torus torus;

    public FishStrategy() {
        this(Fish::new);
    }

    protected FishStrategy(Supplier<Fish> fishSupplier) {
        this.fishSupplier = fishSupplier;
    }

    /**
     * Simulates the life of a fish for one chronon. It must processes only those fish it is
     * responsible for.
     *
     * @param fish the fish to process
     */
    public void tick(Fish fish) {
        if (fish.getClass() != Fish.class) {
            return;
        }
        breed(fish);
        move(fish);
    }

    /**
     * Either increases the breed count, or if the breed count has reached the breed time, then create a child
     * in an empty neighbor cell, if there is any, and reset the breed count to zero.
     */
    protected void breed(Fish fish) {
        if (fish.getBreedCount() < breedTime) {
            fish.setBreedCount(fish.getBreedCount() + 1);
        } else {
            fish.setBreedCount(0);
            findEmptyNeighborCell(fish).ifPresent(direction -> add(fish, direction, fishSupplier.get()));
        }
    }

    /**
     * Move the fish to an empty neighbor cell.
     */
    protected void move(Fish fish) {
        Optional<Direction> emptyNeighborCell = findEmptyNeighborCell(fish);
        if (emptyNeighborCell.isPresent()) {
            move(fish, emptyNeighborCell.get());
        } else {
            // Write the fish to the torus even if it has not been moved. This is required to update the properties
            // of a fish in the torus. I. e. a graphical torus may encode the properties of a fish in the color of
            // a pixel. Thus if the properties of a fish changed the color if the pixel has to be updated.
            torus.setFishAt(fish.getX(), fish.getY(), fish);
        }
    }

    /**
     * Add a new fish on the torus besides the current fish on the side given by the direction.
     *
     * @param fish the parent
     * @param direction specifies the the side to add the child relative to it's parent
     * @param child the child to add
     */
    protected final void add(Fish fish, Direction direction, Fish child) {
        child.setEpoch(fish.getEpoch());
        torus.setFishAt(fish.getX() + direction.xOffset, fish.getY() + direction.yOffset, child);
    }

    /**
     * Move a fish on the torus to a neighbor cell in the specified direction.
     *
     * @param fish the fish to move
     * @param direction the direction to move the fish to
     */
    protected final void move(Fish fish, Direction direction) {
        torus.setFishAt(fish.getX(), fish.getY(), null);
        torus.setFishAt(fish.getX() + direction.xOffset, fish.getY() + direction.yOffset, fish);
    }

    /**
     * Remove a fish that died.
     *
     * @param fish
     */
    protected final void remove(Fish fish) {
        torus.setFishAt(fish.getX(), fish.getY(), null);
    }

    /**
     * Find a randomly picked empty neighbor cell.
     *
     * @param fish the fish to find an empty neighbor cell for
     * @return an direction if there was an empty cell
     */
    protected final Optional<Direction> findEmptyNeighborCell(Fish fish) {
        return findNeighborCell(fish, f -> f == null);
    }

    /**
     * Find a randomly picked neighbor cell with properties specified by some predicate.
     *
     * @param fish to find an empty neighbor cell for
     * @param predicate a suitable cell must fulfill
     * @return the direction the fish would have to move to that cell
     */
    protected final Optional<Direction> findNeighborCell(Fish fish, Predicate<Fish> predicate) {
        return shuffle().stream().filter(d -> predicate.test(getNeighbor(fish, d))).findAny();
    }

    /**
     * Returns the neighbor of the fish in the corresponding direction or null if there is none.
     */
    private Fish getNeighbor(Fish fish, Direction direction) {
        return torus.getFishAt(fish.getX() + direction.xOffset, fish.getY() + direction.yOffset);
    }

    /**
     * @return randomly sorted list of available directions
     */
    private List<Direction> shuffle() {
        List<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
        Collections.shuffle(directions, rnd);
        return directions;
    }

    /**
     * @return time in chronos this type of fish needs to reproduce itself
     */
    public int getBreedTime() {
        return breedTime;
    }

    public void setBreedTime(int breedTime) {
        this.breedTime = breedTime;
    }

    public Random getRnd() {
        return rnd;
    }

    @Autowired
    public void setRnd(Random rnd) {
        this.rnd = rnd;
    }

    public Torus getTorus() {
        return torus;
    }

    @Autowired
    public void setTorus(Torus torus) {
        this.torus = torus;
    }

    @Override
    public String toString() {
        return "FishStrategy [breedTime=" + breedTime + "]";
    }
}
