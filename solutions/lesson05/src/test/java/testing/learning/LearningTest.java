package testing.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class LearningTest {

    @Configuration
    @Import(Config1.class)
    static class Config {

        @Bean
        Integer magicNumber() {
            return 42;
        }
    }

    @Resource
    private Integer number;

    @Inject
    private Integer magicNumber;

    @Autowired
    private Integer someNumber;

    @Autowired
    @Nullable
    private Long someLongValue;

    @Test
    public void test() {
        assertThat(magicNumber).isEqualTo(1234);
        assertThat(number).isEqualTo(1234);
        assertThat(someNumber).isEqualTo(1234);
        assertThat(someLongValue).isNull();
    }
}
