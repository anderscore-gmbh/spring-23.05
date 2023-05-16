package wator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import wator.big.Big;
import wator.core.FishStrategy;
import wator.core.Timer;
import wator.fx.WritableImageTorus;

@Configuration
@Import(WatorBaseConfig.class)
@ComponentScan(basePackageClasses = FishStrategy.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = Timer.class))
public class WatorFxConfig {

    // @Profile("default")
    @Big(false)
    @Bean
    WritableImageTorus torus() {
        return new WritableImageTorus(800, 600);
    }

    // @Profile("big")
    @Big
    @Bean
    WritableImageTorus bigTorus() {
        return new WritableImageTorus(1024, 786);
    }

}
