package testing;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import wator.config.WatorConfig;
import wator.core.Torus;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(WatorConfig.class)
public class WatorApplicationContextTest {

    @Inject
    private Torus torus;

    @Test
    public void contextLoads() {
        assertThat(torus).isNotNull();
    }
}
