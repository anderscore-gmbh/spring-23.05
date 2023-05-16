package container.fragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * Erfordert VM-Parameter:
 * <pre>
 * --add-exports wator/container.fragments=spring.beans,spring.context
 * --add-opens wator/container.fragments=spring.core
 * </pre> 
 */
@SpringJUnitConfig(CarConfig.class)
public class JavaConfigTest {

    @Autowired
    private Car firstCar;

    @Autowired
    private Car bobbyCar;

    @Autowired
    private Car luxuryCar;

    @Autowired
    private ApplicationContext ctx;

    @Test
    void testContextLoads() {
        assertThat(firstCar).isNotNull();
        assertThat(bobbyCar).isSameAs(ctx.getBean("firstCar2", Car.class))
                .isSameAs(ctx.getBean("car2"));
    }

    @Test
    void testLuxuryCar() {
        Feature aircon = new Feature("air conditioning");
        // tag::luxuryCar[]
        Car car = new Car();
        car.setColor("black");
        car.setEngine(new Engine("gas", 120));
        car.setFeatures(Arrays.asList(aircon, new Feature("radio")));
        // end::luxuryCar[]
        assertThat(luxuryCar).isEqualToComparingFieldByFieldRecursively(car);
    }
    
    @Test
    void testCreateApplicationContext() {
        // tag::createCtx[]
        ApplicationContext ctx = new AnnotationConfigApplicationContext(CarConfig.class);
        Car car = ctx.getBean("firstCar", Car.class);
        // end::createCtx[]
        assertThat(car).isNotNull();
    }

}
