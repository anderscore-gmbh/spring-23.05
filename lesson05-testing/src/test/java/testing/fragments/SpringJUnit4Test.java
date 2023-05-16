package testing.fragments;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import wator.config.WatorConfig;
import wator.core.Torus;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

// tag::impl[]
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WatorConfig.class)
public class SpringJUnit4Test {

    @Inject
    private Torus torus;

    @Test
    public void testInjectionWorks() {
        assertThat(torus).isNotNull();
    }
}
// end::impl[]