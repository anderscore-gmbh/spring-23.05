package testing;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddingTest {

    @Test
    void testOnePlusOne() {
        assertThat(1 + 1).isEqualTo(2);
    }
}
