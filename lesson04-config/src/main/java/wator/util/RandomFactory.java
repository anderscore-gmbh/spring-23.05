package wator.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import jakarta.inject.Named;

import org.springframework.beans.factory.FactoryBean;

@Named
public class RandomFactory implements FactoryBean<Random> {

    @Override
    public Random getObject() throws Exception {
        return ThreadLocalRandom.current();
    }

    @Override
    public Class<?> getObjectType() {
        return Random.class;
    }
}
