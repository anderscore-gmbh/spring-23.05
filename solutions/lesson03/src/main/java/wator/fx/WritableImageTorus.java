package wator.fx;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import wator.core.Fish;
import wator.core.Shark;
import wator.core.Torus;

/**
 * Implements the torus as a {@link WritableImage} suitable to be shown on a JavaFx scene.
 * The pixel color is used to encode the properties of a fish as
 * <ul>
 *     <li>black pixels are empty cells</li>
 *     <li>blue pixels represent fish, the breed count is encoded in the color value</li>
 *     <li>yellow pixels represent sharks, the green part is used to encode the breed count
 *     and the red part encodes the starve count</li>
 *     <li>the opacity is used to encode the epoch a fish is in currently</li>
 * </ul>
 */
public class WritableImageTorus implements Torus {
    private final int width;
    private final int height;

    private final WritableImage image;
    private final PixelReader pixelReader;
    private final PixelWriter pixelWriter;
    private int epoch = 0;

    public WritableImageTorus(int width, int height) {
        this.width = width;
        this.height = height;
            image = new WritableImage(width, height);
            pixelReader = image.getPixelReader();
            pixelWriter = image.getPixelWriter();
    }

    @Override
    public Fish getFishAt(int x, int y) {
        Color color = pixelReader.getColor(normalize(x, width), normalize(y, height));
        Fish fish;
        if (color.getBlue() > 0) {
            fish = new Fish();
            fish.setBreedCount(255 - asRgbInt(color.getBlue()));
        } else if (color.getGreen() > 0) {
            Shark shark = new Shark();
            shark.setBreedCount(255 - asRgbInt(color.getGreen()));
            shark.setStarveCount(255 - asRgbInt(color.getRed()));
            fish = shark;
        } else {
            return null;
        }
        fish.setEpoch(255 - asRgbInt(color.getOpacity()));
        return fish;
    }

    int asRgbInt(double value) {
        return (int) (value * 255);
    }

    @Override
    public void setFishAt(int x, int y, Fish fish) {
        int r = 0;
        int g = 0;
        int b = 0;
        int o = 255;
        if (fish != null) {
            if (fish instanceof Shark) {
                Shark shark = (Shark) fish;
                g = 255 - shark.getBreedCount();
                r = 255 - shark.getStarveCount();
            } else {
                b = 255 - fish.getBreedCount();
            }
            o = 255 - fish.getEpoch();
        }
        Color color = Color.rgb(r, g, b, o / 255.0);
        pixelWriter.setColor(normalize(x, width), normalize(y, height), color);
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
     * @return the image this torus represents to be shown in JavaFx scene-graph.
     */
    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "WritableImageTorus [width=" + width + ", height=" + height + "]";
    }
}
