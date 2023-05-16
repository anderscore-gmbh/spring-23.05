package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import wator.core.Fish;
import wator.core.FishStrategy;
import wator.core.Torus;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
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
        verify(mockTorus).setFishAt(10, 20, null);
        verify(mockTorus).setFishAt(11, 20, fish);
    }

    @Test
    void testMoveWithNextNeighbourCellOccupied() {
        // OPTIONAL: Simulieren Sie das Verschieben, wenn die Zelle (11, 20) bereits
        // durch einen anderen Fisch belegt ist.
        when(mockTorus.getFishAt(11, 20)).thenReturn(new Fish());
        fishStrategy.tick(fish);
        assertThat(fish.getBreedCount()).isEqualTo(2);
        verify(mockTorus).setFishAt(10, 20, null);
        verify(mockTorus).setFishAt(9, 20, fish);
    }

    @Test
    void testBreedWithEmptySurrounding() {
        // OPTIONAL: Simulieren Sie die Vermehrung eines Fisches bei leerer Umgebung.
        Iterator<Fish> iter = Arrays.asList(null, new Fish()).iterator();
        when(mockTorus.getFishAt(11, 20)).thenAnswer(inv -> iter.next());
        fish.setBreedCount(5);
        fishStrategy.tick(fish);
        assertThat(fish.getBreedCount()).isEqualTo(0);
        ArgumentCaptor<Fish> childFishCaptor = ArgumentCaptor.forClass(Fish.class);
        verify(mockTorus).setFishAt(eq(11), eq(20), childFishCaptor.capture());
        assertThat(childFishCaptor.getValue()).isNotSameAs(fish);
        assertThat(childFishCaptor.getValue().getBreedCount()).isEqualTo(0);
        verify(mockTorus).setFishAt(10, 20, null);
        verify(mockTorus).setFishAt(9, 20, fish);
    }

}
