package tx.jdbc;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.inject.Inject;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
@TestPropertySource(properties = {"jdbc.url=jdbc:hsqldb:mem:mymemdb",
        "jdbc.url2=jdbc:hsqldb:hsql://localhost/xdb"})
@Sql("/init-schema.sql")
public class TransactionTemplateTests {
    private static final Logger log = LoggerFactory.getLogger(TransactionTemplateTests.class);

    @Configuration
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
        TransactionTemplate transactionTemplate(PlatformTransactionManager txManager) {
            TransactionTemplate txTemplate = new TransactionTemplate(txManager);
            return txTemplate;
        }
    }

    @Inject
    private JdbcTemplate jdbcTemplate;
    @Inject
    private TransactionTemplate transactionTemplate;

    @Test
    void testInsertWithinTransaction() {
        executeWithinTx(this::insertSamplePersons);
        executeWithinTx(() -> deleteAll(3));
    }

    @Test
    void testInsertWithConflict() {
        DuplicateKeyException ex = assertThrows(DuplicateKeyException.class, () -> executeWithinTx(this::insertWithConflict));
        log.info("Got expected exception:" , ex);
        assertThat(ex.getMessage()).contains("Izmir");
        executeWithinTx(() -> deleteAll(0));
    }

    private void executeWithinTx(Runnable action) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                action.run();
            }
        });
    }

    private void insertSamplePersons() {
        jdbcTemplate.execute("insert into person(id, firstName, lastName) values (1, 'Anna', 'Gramm')");
        jdbcTemplate.execute("insert into person(id, firstName, lastName) values (2, 'Franz', 'Brandwein')");
        jdbcTemplate.execute("insert into person(id, firstName, lastName) values (3, 'Izmir', 'Egal')");
    }

    private void insertWithConflict() {
        jdbcTemplate.execute("insert into person(id, firstName, lastName) values (1, 'Anna', 'Gramm')");
        jdbcTemplate.execute("insert into person(id, firstName, lastName) values (2, 'Franz', 'Brandwein')");
        jdbcTemplate.execute("insert into person(id, firstName, lastName) values (2, 'Izmir', 'Egal')");
    }

    private void deleteAll(int deletedCountExpected) {
        int deletedCount = jdbcTemplate.update("delete from person");
        assertThat(deletedCount).isEqualTo(deletedCountExpected);
    }
}
