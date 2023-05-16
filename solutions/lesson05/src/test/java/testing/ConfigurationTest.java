package testing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestPropertySource("classpath:test.properties")
@ActiveProfiles("dev")
public class ConfigurationTest {

    @Inject
    private Environment env;

    @Inject
    private Integer magicNumber;

    @Test
    void testProperties() {
        //TODO: Lagern Sie 'value' in eine Properties-Datei aus
        assertThat(env.getProperty("value")).isEqualTo("42");
    }

    @Test
    void testDevProfile() {
        //TODO: Aktivieren Sie das 'dev' Profil. Wie geht das ohne @ActiveProfiles?
        //Antwort: Mit dem VM-Paramter: -Dspring.profiles.active=dev
        assertThat(magicNumber).isEqualTo(421);
    }

    @Configuration
    static class Config {

        @Profile("!dev")
        @Bean
        Integer magicNumber(@Value("${value}") int value) {
            return value;
        }

        @Profile("dev")
        @Bean(name="magicNumber")
        Integer magicNumberDev() {
            return 421;
        }

    }
}
