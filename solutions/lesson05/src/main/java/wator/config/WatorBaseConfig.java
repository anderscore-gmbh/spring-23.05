package wator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wator.core.FishStrategy;
import wator.core.SharkStrategy;

import java.util.Random;

@Configuration
public class WatorBaseConfig {

    @Bean
    Random rnd() {
        return new Random();
    }

    @Bean
    FishStrategy fishStrategy() {
        FishStrategy fishStrategy = new FishStrategy();
        fishStrategy.setBreedTime(3);
        return fishStrategy;
    }

    @Bean
    SharkStrategy sharkStrategy() {
        SharkStrategy sharkStrategy = new SharkStrategy();
        sharkStrategy.setBreedTime(5);
        sharkStrategy.setStarveTime(4);
        return sharkStrategy;
    }
}

