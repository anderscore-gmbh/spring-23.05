package wator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import wator.core.FishStrategy;
import wator.simple.SimpleTorus;

@Configuration
@Import(WatorBaseConfig.class)
@ComponentScan(basePackageClasses = FishStrategy.class)
public class WatorSimpleConfig {

    @Bean
    SimpleTorus torus() {
        return new SimpleTorus(60, 10);
    }
}
