package tx.hsql;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hsqldb.jdbc.JDBCDataSourceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class DataSourceTests {
    private static final String SQL = "select 1 + 1 from (values(0))";
    private static final String HSQL_URL = "jdbc:hsqldb:mem:mymemdb";

    private JdbcTemplate jdbcTemplate;

    @Test
    public void testNativeDataSourceWithoutDI() throws Exception {
        DataSource dataSource = createNativeDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        executeQuery();
    }

    private DataSource createNativeDataSource() throws Exception {
        Properties props = new Properties();
        props.put("url", HSQL_URL);
        props.put("username", "SA");
        props.put("password", "");
        DataSource dataSource = JDBCDataSourceFactory.createDataSource(props);
        return dataSource;
    }

    @Test
    public void testBasicDatasourceWithoutDI() throws Exception {
        DataSource dataSource = createBasicDataSource();
        jdbcTemplate = new JdbcTemplate(dataSource);
        executeQuery();
    }

    private DataSource createBasicDataSource() throws Exception {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        ds.setUrl(HSQL_URL);
        ds.setUsername("SA");
        ds.setPassword("");
        ds.setInitialSize(1);
        ds.setMaxTotal(10);
        return ds;
    }

    private void executeQuery() {
        Integer result = jdbcTemplate.queryForObject(SQL, Integer.class);
        assertThat(result).isEqualTo(2);
    }
}
