package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import wator.core.Fish;
import wator.core.FishStrategy;
import wator.core.Torus;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class FishStrategyTest {
    private FishStrategy fishStrategy = new FishStrategy();
    private Random mockRnd = mock(Random.class, withSettings().withoutAnnotations());
    private Torus mockTorus = mock(Torus.class);
    private Fish fish = createSampleFish();

    @BeforeEach
    void initFishStrategy() {
        fishStrategy.setBreedTime(5);
        ReflectionTestUtils.setField(fishStrategy, "rnd", mockRnd);
        ReflectionTestUtils.setField(fishStrategy, "torus", mockTorus);
    }

    @BeforeEach
    void prepareTorus() {
        when(mockTorus.getWidth()).thenReturn(300);
        when(mockTorus.getHeight()).thenReturn(200);
    }

    @BeforeEach
    void prepareRnd() {
        when(mockRnd.nextInt(anyInt())).thenReturn(1);
    }

    private Fish createSampleFish() {
        Fish fish = new Fish();
        fish.setBreedCount(1);
        fish.setX(10);
        fish.setY(20);
        return fish;
    }

    @Test
    void testMoveWithEmptySurrounding() {
        fishStrategy.tick(fish);
        assertThat(fish.getBreedCount()).isEqualTo(2);
        // TODO: Prüfen Sie, dass der Fisch von (10, 20) nach (11, 20) verschoben wurde.
    }

    @Test
    void testMoveWithNextNeighbourCellOccupied() {
        // OPTIONAL: Simulieren Sie das Verschieben, wenn die Zelle (11, 20) bereits
        // durch einen anderen Fisch belegt ist.
    }

    @Test
    void testBreedWithEmptySurrounding() {
        // OPTIONAL: Simulieren Sie die Vermehrung eines Fisches bei leerer Umgebung.
    }

}
