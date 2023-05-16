package testing;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import wator.core.Torus;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = "classpath:context.xml")
public class WatorApplicationContextTest {

    @Inject
    private Torus torus;

    @Test
    public void contextLoads() {
        assertThat(torus).isNotNull();
    }
}
