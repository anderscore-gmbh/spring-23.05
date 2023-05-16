package testing.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.inject.Named;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class QualifierTest {

    @Configuration
    static class Config {

        @Bean(name="blub")
        @Qualifier("important")
        String name1() {
            return "blub";
        }

        @Bean(name="bla")
        String name2() {
            return "bla";
        }

        @Bean(name="brr")
        @Named("special")
        String name3() {
            return "brr";
        }
    }

    @Autowired
    @Qualifier("important")
    private String name;

    @Autowired
    @Named("special")
    private String specialName;

    @Test
    public void test() {
        assertThat(name).isEqualTo("blub");
        assertThat(specialName).isEqualTo("brr");
    }
}
