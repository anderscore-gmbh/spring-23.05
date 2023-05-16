package container.fragments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Erfordert VM-Parameter:
 * <pre>
 * --add-exports wator/container.fragments=spring.beans
 * --add-opens wator/container.fragments=spring.core
 * </pre> 
 */
@SpringJUnitConfig(locations = "classpath:car-context.xml")
public class XmlConfigTest {

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
        ApplicationContext ctx = new ClassPathXmlApplicationContext("car-context.xml");
        Car car = ctx.getBean("firstCar", Car.class);
        // end::createCtx[]
        assertThat(car).isNotNull();
    }

    private void copyPaste() {
        Car firstCar = new Car();
        Engine engine = new Engine("diesel", 48);
        Car realCar = new Car();
        realCar.setColor("yellow");
        realCar.setEngine(engine);
    }
}
