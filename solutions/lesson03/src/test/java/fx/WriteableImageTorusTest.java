package fx;

import wator.core.Fish;
import wator.core.Shark;
import wator.fx.WritableImageTorus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WriteableImageTorusTest {
    private WritableImageTorus torus = new WritableImageTorus(20, 20);

    @Test
    public void test() {
        assertThat(torus.getFishAt(10, 10)).isNull();

        Fish fish = new Fish();
        check(fish);
        fish.setBreedCount(5);
        check(fish);
        fish.setEpoch(3);
        check(fish);

        Shark shark = new Shark();
        check(shark);
        shark.setStarveCount(7);
        check(shark);
        shark.setBreedCount(9);
        check(shark);
        shark.setEpoch(11);
        check(shark);
    }

    private void check(Fish fish) {
        torus.setFishAt(10, 10, fish);
        assertThat(torus.getFishAt(10, 10)).isEqualToComparingFieldByField(fish);
    }
}
