package container.fragments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.BeanDefinitionOverrideException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class ConflictsTest {

    @Configuration 
    static class Config1 {
        @Bean
        Car car() {
            Car car = new Car();
            car.setColor("green");
            return car;
        }
    }

    @Configuration 
    static class Config2 {
        @Bean
        Car car() {
            Car car = new Car();
            car.setColor("blue");
            return car;
        }
    }
    
    @Test
    public void testConflict() {
        var ctx = new AnnotationConfigApplicationContext(Config1.class, Config2.class);
        var cars = ctx.getBeansOfType(Car.class);
        cars.forEach((name, car) -> System.out.printf("%s: %s%n", name, car.getColor()));
        assertThat(cars).hasSize(1);
    }

    @Test
    public void testDetection() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.setAllowBeanDefinitionOverriding(false);
        ctx.register(Config1.class, Config2.class);
        BeanDefinitionOverrideException ex = assertThrows(BeanDefinitionOverrideException.class, ctx::refresh);
        System.out.println(ex.getBeanName() + " in " + ex.getResourceDescription() + " caused: " + ex.getMessage());
    }
}
