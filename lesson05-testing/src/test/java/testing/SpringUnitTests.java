package testing;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import wator.core.FishStrategy;

public class SpringUnitTests {

    private FishStrategy fishStrategy = new FishStrategy();

    @Test
    void testOnePlusOne() {
        // TODO: Führen Sie diesen Test aus. Was passiert, wenn sie das erwartete Ergebnis ändern?
        assertThat(1 + 1).as("strange arithmetic").isEqualTo(2);
    }

    @Test
    @Disabled("TODO: Reparieren Sie diesen Test. Es sind zwei Änderungen notwendig.")
    void testAccessPrivateInstanceVariableUsingReflection() throws NoSuchFieldException, IllegalAccessException {
        Field rndField = FishStrategy.class.getField("rnd");
        rndField.set(fishStrategy, new FixedRandom());
        assertThat(fishStrategy.getRnd().nextInt(42)).isEqualTo(0);
    }

    @Test
    @Disabled("TODO: Verwenden Sie die ReflectionTestUtils, um das rnd-Feld mit FixedRadom vorzubelegen.")
    void testAccessPrivateInstanceVariableUsingReflectionTestUtils() {
        assertThat(fishStrategy.getRnd().nextInt(42)).isEqualTo(0);
    }
}