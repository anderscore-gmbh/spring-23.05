package container.fragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import container.fragments.components.BigDieselEngine;
import container.fragments.components.CarConstructorWiring;
import container.fragments.components.Umbrella;

public class ComponentScanTest {

    @Configuration
    // @formatter:off
    @ComponentScan(basePackageClasses = BigDieselEngine.class, 
                   excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, 
                                            classes = Umbrella.class))
    // @formatter:on
    static class Config {
    }

    @Test
    void testConstructorWiring() {
        var ctx = new AnnotationConfigApplicationContext(Config.class);
        var car = ctx.getBean(CarConstructorWiring.class);
        System.out.println(car);
        assertThat(car.toString()).contains("diesel (132 kw)").contains("radio").contains("air-conditioning")
                .doesNotContain("umbrella");
    }
}
