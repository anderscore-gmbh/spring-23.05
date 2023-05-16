package testing.learning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Config2 {

    @Primary
    @Bean(name = "number")
    Integer magicNumber() {
        return 1234;
    }

}
