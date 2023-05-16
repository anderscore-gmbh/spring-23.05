package wator.config;

import org.springframework.context.annotation.*;
import wator.core.FishStrategy;
import wator.core.Timer;
import wator.fx.WritableImageTorus;

@Configuration
@Import(WatorBaseConfig.class)
@ComponentScan(basePackageClasses = FishStrategy.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Timer.class))
public class WatorFxConfig {

    @Bean
    WritableImageTorus torus() {
        return new WritableImageTorus(800, 600);
    }
}
