package testing.fragments;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertJTest {

    @Test
    public void testAdding() {
        assertThat(1 + 1).as("strange arithmetic").isEqualTo(2);
    }
}
