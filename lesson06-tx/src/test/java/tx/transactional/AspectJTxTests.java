package tx.transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tx.jdbc.DataSourceConfig;

import jakarta.inject.Inject;
import javax.sql.DataSource;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Load-time-weaving requires the VM-Parameter
 * -javaagent:<path to org.springframework:spring-instrument:5.1.9.RELEASE.jar>
 * for example:
 * -javaagent:C:\Users\hjhessmann\.m2\repository\org\springframework\spring-instrument\5.1.9.RELEASE\spring-instrument-5.1.9.RELEASE.jar
 */
@SpringJUnitConfig
@TestPropertySource(properties = {"jdbc.url1=jdbc:hsqldb:mem:mymemdb",
        "jdbc.url=jdbc:hsqldb:hsql://localhost/xdb"})
@Sql("/init-schema2.sql")
@ActiveProfiles("default")
@Transactional
@Rollback
@Disabled("Enable load-time weaving first")
public class AspectJTxTests {
    private static final Logger log = LoggerFactory.getLogger(AspectJTxTests.class);

    @Configuration
    @EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
    @EnableLoadTimeWeaving
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
    void testInsertPersons() {
        personService.insert(DummyPersonFactory.createPersons());
    }

    @Test
    void testInsertPersonsWithConflict() {
        DuplicateKeyException ex =
                assertThrows(DuplicateKeyException.class,
                        () -> personService.insert(DummyPersonFactory.createPersonsWithConflict()));
        log.info("Caught expected: ", ex);
    }
}
