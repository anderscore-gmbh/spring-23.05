package tx.fragments;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.inject.Inject;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig

@Sql("/init-schema.sql")
public class TxSamples {

    @Configuration
    static class Config {

        @Bean
        DataSource embeddedDataSource() {
            EmbeddedDatabase ds = new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.HSQL)
                    .build();
            return ds;
        }

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
    // tag::txTemplate[]
    void executeTx() {
        Integer result = transactionTemplate.execute(this::withinTx);
        assertThat(result).isEqualTo(2);
    }

    private Integer withinTx(TransactionStatus status) {
        return jdbcTemplate.queryForObject("select 1 + 1 from (values(0))", Integer.class);
    }
    // end::txTemplate[]
}
