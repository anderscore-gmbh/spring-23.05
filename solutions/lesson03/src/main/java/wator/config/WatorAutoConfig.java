package wator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wator.core.FishStrategy;
import wator.core.SharkStrategy;
import wator.core.Torus;
import wator.core.WorldEngine;
import wator.simple.SimpleTorus;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class WatorAutoConfig {

    @Bean
    Random rnd() {
        return ThreadLocalRandom.current();
    }

    @Bean
    SimpleTorus torus() {
        return new SimpleTorus(60, 10);
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

    @Bean
    WorldEngine worldEngine(Random rnd, Torus torus, FishStrategy[] strategies) {
        WorldEngine worldEngine = WorldEngine.createPopulatedWorld(
                0.3, 0.1,
                rnd, torus, strategies);
        return worldEngine;
    }
}
