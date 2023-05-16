package tx.fragments;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcSamples {

    @Test
    public void testConnect() throws ClassNotFoundException, SQLException {
        Connection connection = createConnection();
        connection.close();
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        // tag::connect[]
        String jdbcUrl = "jdbc:hsqldb:mem:mymemdb"; // In-Memory-Datenbank mit Name 'mymemdb'
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        Connection connection = DriverManager.getConnection(jdbcUrl, "SA", "");
        // end::connect[]
        return connection;
    }

    @Test
    public void testQuery() throws ClassNotFoundException, SQLException {
        Connection connection = createConnection();
        // tag::query[]
        String sql = "select ? + 1 from (values(0))";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, 3);
            try (ResultSet rs = stmt.executeQuery()) {
                assertThat(rs.next()).isTrue();
                assertThat(rs.getInt(1)).isEqualTo(4);
            }
        }
        // end::query[]
        connection.close();
    }

    @Test
    public void testTx() throws ClassNotFoundException, SQLException {
        Connection connection = createConnection();
        // tag::tx[]
        connection.setAutoCommit(false);
        // do something here ...
        connection.commit();
        // end::tx[]
        connection.close();
    }

    @Test
    public void testTemplate() throws ClassNotFoundException, SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(createDataSource());
        // tag::template[]
        String sql = "select ? + 1 from (values(0))";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, 3);
        assertThat(result).isEqualTo(4);
        // end::template[]
    }

    private DataSource createDataSource() {
        SingleConnectionDataSource ds = new SingleConnectionDataSource();
        ds.setDriverClassName("org.hsqldb.jdbc.JDBCDriver");
        ds.setUrl("jdbc:hsqldb:mem:mymemdb");
        ds.setUsername("SA");
        ds.setPassword("");
        return ds;
    }

}
