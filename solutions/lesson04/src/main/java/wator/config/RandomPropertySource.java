package wator.config;

import org.springframework.core.env.PropertySource;

import java.util.Random;

public class RandomPropertySource extends PropertySource<Random> {

    public RandomPropertySource() {
        super("random", new Random());
    }

    @Override
    public String getProperty(String key) {
        switch (key) {
            case "fish.breedTime":
            case "shark.breedTime":
            case "shark.starveTime":
                return Integer.toString(getSource().nextInt(99) + 1);
        }
        return null;
    }
}
