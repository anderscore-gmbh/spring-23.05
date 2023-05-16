package tx.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hsqldb.jdbc.JDBCDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Profile("default")
    @Bean
    DataSource embeddedDataSource() {
        EmbeddedDatabase ds = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .build();

        return ds;
    }

    @Profile("basic")
    @Bean
    DataSource basicDataSource(@Value("${jdbc.url}") String jdbcUrl) {
        // tag::dbcp[]
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        ds.setUrl(jdbcUrl);
        ds.setUsername("SA");
        ds.setPassword("");
        ds.setInitialSize(1);
        ds.setMaxTotal(10);
        // end::dbcp[]

        return ds;
    }

    @Profile("hikari")
    @Bean
    DataSource hikariDataSource(@Value("${jdbc.url}") String jdbcUrl) {
        // tag::hikari[]
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername("SA");
        config.setPassword("");
        config.setMaximumPoolSize(10);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);
        // end::hikari[]

        return ds;
    }

    @Profile("single")
    @Bean
    DataSource singleConnectionDataSource(@Value("${jdbc.url}") String jdbcUrl) {
        SingleConnectionDataSource ds = new SingleConnectionDataSource();
        ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        ds.setUrl(jdbcUrl);
        ds.setUsername("SA");
        ds.setPassword("");

        return ds;
    }

    @Profile("native")
    @Bean
    DataSource nativeDataSource(@Value("${jdbc.url}") String jdbcUrl) throws Exception {
        Properties props = new Properties();
        props.put("url", jdbcUrl);
        props.put("username", "SA");
        props.put("password", "");
        DataSource ds = JDBCDataSourceFactory.createDataSource(props);

        return ds;
    }
}