package testing.learning;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import(Config2.class)
public class Config1 {

    @Bean
    Integer magicNumber() {
        return 4321;
    }
}
