package testing.fragments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnit5Test {

    @Test
    public void testOnePlusOne() {
        assertEquals(2, 1 + 1, "strange arithmetic");
    }
}
