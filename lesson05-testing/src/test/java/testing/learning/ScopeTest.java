package testing.learning;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
public class ScopeTest {

    @Configuration
    static class Config {

        @Bean
        @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
        StringBuilder builder() {
            return new StringBuilder();
        }
    }

    @Autowired
    private StringBuilder sb1;

    @Autowired
    private StringBuilder sb2;

    @Test
    public void test() {
        sb1.append("xx");
        sb2.append("yy");
        assertThat(sb1.toString()).isEqualTo("xx");
        assertThat(sb2.toString()).isEqualTo("yy");
    }

}
