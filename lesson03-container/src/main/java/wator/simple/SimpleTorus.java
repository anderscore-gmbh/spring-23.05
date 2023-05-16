package wator.simple;

import wator.core.Fish;
import wator.core.Shark;
import wator.core.Torus;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Represents a {@link Torus} as a 2dimensional array of {@link Fish}.
 */
public class SimpleTorus implements Torus {
    private final int width;
    private final int height;
    private final Fish[][] grid;
    private final PrintWriter pw = new PrintWriter(new OutputStreamWriter(System.out));

    public SimpleTorus(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Fish[height][width];
    }

    @Override
    public Fish getFishAt(int x, int y) {
        return grid[normalize(y, height)][normalize(x, width)];
    }

    @Override
    public void setFishAt(int x, int y, Fish fish) {
        grid[normalize(y, height)][normalize(x, width)] = fish;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /**
     * Dump the content of the torus to a writer. Represents a {@link Fish} as 'f', a {@link Shark} as 'S'
     * and an empty cell as a space.
     */
    public void dump(Writer writer) throws IOException {
        for (int y = 0; y < height; y++) {
            writer.write("   ");
            for (int x = 0; x < width; x++) {
                Fish fish = grid[y][x];
                if (fish == null) {
                    writer.write(" ");
                } else if (fish instanceof Shark) {
                    writer.write("S");
                } else {
                    writer.write("f");
                }
            }
            writer.write("\n");
        }
    }

    public void dump(int round) throws IOException {
        pw.printf("%2d. ------------------------------------------------------------%n", round);
        dump(pw);
        pw.flush();
    }
}
