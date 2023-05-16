package testing;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import wator.core.FishStrategy;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringUnitTests {

    private FishStrategy fishStrategy = new FishStrategy();

    @Test
    void testOnePlusOne() {
        // TODO: Führen Sie diesen Test aus. Was passiert, wenn sie das erwartete Ergebnis ändern?
        assertThat(1 + 1).as("strange arithmetic").isEqualTo(2);
    }

    @Test
    void testAccessPrivateInstanceVariableUsingReflection() throws NoSuchFieldException, IllegalAccessException {
        // TODO: Reparieren Sie diesen Test. Es sind zwei Änderungen notwendig.
        Field rndField = FishStrategy.class.getDeclaredField("rnd");
        rndField.setAccessible(true);
        rndField.set(fishStrategy, new FixedRandom());
        assertThat(fishStrategy.getRnd().nextInt(42)).isEqualTo(0);
    }

    @Test
    void testAccessPrivateInstanceVariableUsingReflectionTestUtils() {
        // TODO: Verwenden Sie die ReflectionTestUtils, um das rnd-Feld mit FixedRadom vorzubelegen.
        ReflectionTestUtils.setField(fishStrategy, "rnd", new FixedRandom());
        assertThat(fishStrategy.getRnd().nextInt(42)).isEqualTo(0);
    }
}
