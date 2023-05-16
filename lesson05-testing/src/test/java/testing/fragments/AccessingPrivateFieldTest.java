package testing.fragments;

import org.junit.jupiter.api.Test;
import wator.core.FishStrategy;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public class AccessingPrivateFieldTest {
    private FishStrategy fishStrategy = new FishStrategy();

    @Test
    void accessPrivateInstanceVariableTest() throws NoSuchFieldException, IllegalAccessException {
        // tag::accessPrivateField[]
        Field breedTimeField = FishStrategy.class.getDeclaredField("breedTime");
        breedTimeField.setAccessible(true);
        breedTimeField.set(fishStrategy, 99);
        // end::accessPrivateField[]
        assertThat(fishStrategy.getBreedTime()).isEqualTo(99);
    }
}
