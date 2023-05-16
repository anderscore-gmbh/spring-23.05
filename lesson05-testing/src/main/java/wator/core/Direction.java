package wator.core;

/**
 * These are the possible directions a fish or shark can move.
 */
public enum Direction {
    EAST(1, 0),
    NORTH(0, -1),
    WEST(-1, 0),
    SOUTH(0, 1);

    public final int xOffset;
    public final int yOffset;

    private Direction(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
