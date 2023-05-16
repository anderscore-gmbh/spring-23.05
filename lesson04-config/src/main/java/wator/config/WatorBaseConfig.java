package wator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import wator.settings.FixedWatorSettings;
import wator.settings.WatorSettings;
import wator.util.RandomFactory;

@Configuration
@ComponentScan(basePackageClasses = RandomFactory.class)
public class WatorBaseConfig {

    @Bean
    WatorSettings settings() {
        return new FixedWatorSettings();
    }
}
