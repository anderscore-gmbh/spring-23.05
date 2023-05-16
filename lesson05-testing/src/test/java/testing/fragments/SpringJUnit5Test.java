package testing.fragments;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import wator.config.WatorConfig;
import wator.core.Torus;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

// tag::impl[]
@SpringJUnitConfig(WatorConfig.class)
public class SpringJUnit5Test {

    @Inject
    private Torus torus;

    @Test
    void testInjectionWorks() {
        assertThat(torus).isNotNull();
    }
}
// end::impl[]