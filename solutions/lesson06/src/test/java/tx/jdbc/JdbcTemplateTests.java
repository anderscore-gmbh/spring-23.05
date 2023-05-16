package tx.jdbc;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jakarta.inject.Inject;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@TestPropertySource(properties = { "jdbc.url=jdbc:hsqldb:mem:mymemdb", "jdbc.url2=jdbc:hsqldb:hsql://localhost/xdb" })
@ActiveProfiles("hikari")
public class JdbcTemplateTests {

    @Configuration
    @Import(DataSourceConfig.class)
    static class Config {

        @Bean
        JdbcTemplate jdbcTemplate(DataSource ds) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
            return jdbcTemplate;
        }
    }

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Test
    void testSimpleQuery() {
        Integer result = jdbcTemplate.queryForObject("select 1 + 1 from (values(0))", Integer.class);
        assertThat(result).isEqualTo(2);
    }

    @Test
    @Sql("/init-schema.sql")
    void testCrudPerson() {
        insertPerson(1, "Anna", "Gramm");
        insertPerson(2, "Franz", "Brandwein");
        assertThat(countAllPersons()).isEqualTo(2);

        List<Person> persons = findPersonsByLastName("Gramm");
        assertThat(persons).hasSize(1);
        assertThat(persons.get(0).getFirstName()).isEqualTo("Anna");

        assertThat(updatePersonLastName(1, "Nass"));
        persons = findPersonsByLastName("Nass");
        assertThat(persons).hasSize(1);
        assertThat(persons.get(0).getFirstName()).isEqualTo("Anna");
        assertThat(findPersonsByLastName("Gramm")).isEmpty();

        assertThat(deleteAll()).isEqualTo(2);
        assertThat(countAllPersons()).isEqualTo(0);
    }

    private void insertPerson(int id, String firstName, String lastName) {
        // TODO: Fügen Sie eine Person in die Datenbank ein. Verwenden Sie ein
        // SQL-Statement der Form:
        // insert into person(id, firstName, lastName) values (1, 'Anna', 'Gramm')
        jdbcTemplate.update("insert into person(id, firstName, lastName) values (?, ?, ?)", id, firstName, lastName);
    }

    private int countAllPersons() {
        // TODO: Ermitteln Sie die Anzahl der Personen in der Tabelle
        Integer count = jdbcTemplate.queryForObject("select count(*) from person", Integer.class);
        return count;
    }

    private List<Person> findPersonsByLastName(String lastName) {
        // TODO: Rufen Sie die Liste der Personen mit den Nachname lastName ab.
        // Nutzen Sie mapPersonRow als RowMapper.
        List<Person> persons = jdbcTemplate.query("select * from person where lastName = ?", this::mapPersonRow,
                lastName);
        return persons;
    }

    private Person mapPersonRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("firstName"));
        person.setLastName(rs.getString("lastName"));
        return person;
    }

    private int updatePersonLastName(int id, String newLastName) {
        // TODO: Ersetzen Sie den Nachnamen der Person mit der entsprechenden Id und
        // geben Sie die Anzahl
        // der veränderten Datensätze zurück.
        int updatedCount = jdbcTemplate.update("update person set lastName = ? where id = ?", newLastName, id);
        return updatedCount;
    }

    private int deleteAll() {
        // TODO: Loeschen Sie alle Datensätze in der Tabelle 'person' und geben Sie
        // die Anzahl der geloeschten Datensätze zurück.
        int deletedCount = jdbcTemplate.update("delete from person");
        return deletedCount;
    }

}
