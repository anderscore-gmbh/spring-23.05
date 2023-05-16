package wator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import wator.util.LogBeansPostProcessor;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class WatorBaseConfig {

    @Bean
    Random rnd() {
        return ThreadLocalRandom.current();
    }
    
    @Bean
    static LogBeansPostProcessor logBeansPostProcessor() {
        return new LogBeansPostProcessor();
    }
}
