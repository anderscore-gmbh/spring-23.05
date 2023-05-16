package tx.transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.util.AopTestUtils;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tx.jdbc.DataSourceConfig;
import tx.jdbc.Person;

import jakarta.inject.Inject;
import javax.sql.DataSource;
import jakarta.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestPropertySource(properties = {"jdbc.url=jdbc:hsqldb:mem:mymemdb",
        "jdbc.url2=jdbc:hsqldb:hsql://localhost/xdb"})
@Sql("/init-schema2.sql")
@ActiveProfiles("basic")
@Transactional
@Rollback
public class ProxyTxTests {
    private static final Logger log = LoggerFactory.getLogger(ProxyTxTests.class);

    @Configuration
    @EnableTransactionManagement(proxyTargetClass = false)
    @Import(DataSourceConfig.class)
    static class Config {

        @Bean
        JdbcTemplate jdbcTemplate(DataSource ds) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return jdbcTemplate;
        }

        @Bean
        DataSourceTransactionManager transactionManager(DataSource ds) {
            DataSourceTransactionManager txManager = new DataSourceTransactionManager(ds);
            return txManager;
        }

        @Bean
        PersonServiceImpl personService() {
            PersonServiceImpl service = new PersonServiceImpl();
            return service;
        }
    }

    @Inject
    private PersonService personService;

    @BeforeEach
    void logTestMethod(TestInfo testInfo) {
        log.info("Executing {} ", testInfo.getDisplayName());
    }

    @Test
    void testProxy() {
        // TODO: Ändern Sie die Konfiguration derart, dass dieser Test funktioniert.
        assertThat(personService).isNotInstanceOf(PersonServiceImpl.class);
        PersonService impl = AopTestUtils.getTargetObject(personService);
        assertThat(impl).isInstanceOf(PersonServiceImpl.class);
    }

    @Test
    void testInsertPersons() {
        personService.insert(DummyPersonFactory.createPersons());
    }

    @Test
    void testInsertPersons2() {
        personService.insert(DummyPersonFactory.createPersons());
    }

    @Test
    void testUpdate() {
        personService.insert(DummyPersonFactory.createPersons());
        Person person = personService.findById(1);
        person.setLastName("Nass");
        personService.save(person);
    }

    @Test
    void testInsert() {
        personService.insert(DummyPersonFactory.createPersons());
        Person person = new Person(4, "Anna", "Nass");
        personService.save(person);
    }

    @Test
    void testInsertPersonsWithConflict() {
        DuplicateKeyException ex =
                assertThrows(DuplicateKeyException.class,
                        () -> personService.insert(DummyPersonFactory.createPersonsWithConflict()));
        log.info("Caught expected: ", ex);
    }
}
