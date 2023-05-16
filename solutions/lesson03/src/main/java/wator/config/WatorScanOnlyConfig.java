package wator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.EnableScheduling;
import wator.core.FishStrategy;
import wator.simple.SimpleTorus;
import wator.util.RandomFactory;

@Configuration
@EnableScheduling
@ComponentScan(basePackageClasses = {FishStrategy.class, RandomFactory.class})
public class WatorScanOnlyConfig {
    
    @Bean
    SimpleTorus torus() {
        return new SimpleTorus(60, 10);
    }
}
