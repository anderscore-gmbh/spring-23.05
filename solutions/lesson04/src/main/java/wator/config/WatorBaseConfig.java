package wator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import wator.settings.EnvWatorSettings;
import wator.settings.FixedWatorSettings;
import wator.settings.WatorSettings;
import wator.util.RandomFactory;

@Configuration
@ComponentScan(basePackageClasses = RandomFactory.class)
@PropertySource(value = "wator-${platform:dev}.properties", ignoreResourceNotFound = true)
public class WatorBaseConfig {

    @Bean
    WatorSettings settings() {
        return new EnvWatorSettings();
    }
    
    @Bean
    WatorSettings elSettingsAlternative(@Value("${fish.breedTime}") Integer configuredFishBreedTime) {
    	int fishBreedTime = configuredFishBreedTime != null ? configuredFishBreedTime : (int) (Math.random() * 18) + 2;
    	int sharkBreedTime = fishBreedTime * 2;
    	int sharkStarveTime = sharkBreedTime - 2;
    	
    	FixedWatorSettings settings = new FixedWatorSettings();
    	settings.setFishBreedTime(fishBreedTime);
    	settings.setSharkBreedTime(sharkBreedTime);
    	settings.setSharkStarveTime(sharkStarveTime);
    	
    	return settings;
    }
}