package tx.hsql;

import org.hsqldb.jdbc.JDBCDataSourceFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class AccessDbTests {
    private static final String SQL = "select 1 + 1, current_timestamp from (values(0))";


    @Test
    public void testAccessInMemoryDb() throws ClassNotFoundException, SQLException {
        accessDatabase("jdbc:hsqldb:mem:mymemdb");
    }

    @Test
    public void testAccessSandaloneDb() throws ClassNotFoundException, SQLException {
        accessDatabase("jdbc:hsqldb:file:build/standalonedb");
    }

    @Test
    @Disabled("Start HSQLDB server first")
    public void testAccessDbServer() throws ClassNotFoundException, SQLException {
        accessDatabase("jdbc:hsqldb:hsql://localhost/xdb");
    }

    @Test
    @Disabled("Start HSQLDB server first")
    public void testWithDataSource() throws Exception {
        Properties props = new Properties();
        props.put("url", "jdbc:hsqldb:hsql://localhost/xdb");
        props.put("username", "SA");
        props.put("password", "");
        DataSource dataSource = JDBCDataSourceFactory.createDataSource(props);
        try (Connection connection = dataSource.getConnection()) {
            executeSampleStatment(connection);
        }
    }

    private void accessDatabase(String jdbcUrl) throws ClassNotFoundException, SQLException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        try (Connection connection = DriverManager.getConnection(jdbcUrl, "SA", "")) {
            executeSampleStatment(connection);
        }
    }

    private void executeSampleStatment(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL);) {
            try (ResultSet resultSet = statement.executeQuery()) {
                assertThat(resultSet.next()).as("resultSet empty").isTrue();
                assertThat(resultSet.getInt(1)).isEqualTo(2);
            }
        }
    }
}
