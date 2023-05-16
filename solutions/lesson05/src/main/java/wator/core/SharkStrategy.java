package wator.core;

import jakarta.inject.Named;
import java.util.Optional;

/**
 * This strategy handles the life of a shark. It's <a href="https://en.wikipedia.org/wiki/Wa-Tor">rules</a> are:
 * <ol>
 * <li>At each chronon, a shark moves randomly to an adjacent square occupied by a fish. If there is none, the shark
 * moves to a random adjacent unoccupied square. If there are no free squares, no movement takes place.</li>
 * <li>At each chronon, each shark is deprived of a unit of energy.</li>
 * <li>Upon reaching zero energy, a shark dies.</li>
 * <li>If a shark moves to a square occupied by a fish, it eats the fish and earns a certain amount of energy.</li>
 * <li>Once a shark has survived a certain number of chronons it may reproduce in exactly the same way as the fish.</li>
 * </ol>
 */
@Named
public class SharkStrategy extends FishStrategy {
    private int starveTime;

    public SharkStrategy() {
        super(Shark::new);
    }

    /**
     * Process the live of a shark for one chronon. Does nothing if the fish is not a {@link Shark}.
     *
     * @param fish the fish to process
     */
    @Override
    public void tick(Fish fish) {
        if (fish.getClass() != Shark.class) {
            return;
        }
        Shark shark = (Shark) fish;
        if (shark.getStarveCount() < starveTime) {
            shark.setStarveCount(shark.getStarveCount() + 1);
        } else {
            remove(shark);
            return;
        }
        breed(shark);
        // either the shark eats a fish in a neighbor cell or it moves to an empty neighbor cell
        if (!feed(shark)) {
            move(shark);
        }
    }

    /**
     * Finds an neighbor cell occupied by a fish. If there is one move to that cell, hence replace
     * that fish and reset the starve count. Returns true if fed.
     */
    private boolean feed(Shark shark) {
        Optional<Direction> neighborCell = findNeighborCell(shark, fish -> fish != null && fish.getClass() == Fish.class);
        if (neighborCell.isPresent()) {
            shark.setStarveCount(0);
            move(shark, neighborCell.get());
            return true;
        }
        return false;
    }

    /**
     * @return time in chronos until a shark dies by starvation
     */
    public int getStarveTime() {
        return starveTime;
    }

    public void setStarveTime(int starveTime) {
        this.starveTime = starveTime;
    }
}
