package tx.jdbc;

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

    @Profile("single")
    @Bean
    SingleConnectionDataSource singleConnectionDataSource(@Value("${jdbc.url}") String jdbcUrl) {
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