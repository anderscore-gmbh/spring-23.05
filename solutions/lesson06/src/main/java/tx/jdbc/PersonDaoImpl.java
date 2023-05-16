package tx.jdbc;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonDaoImpl extends JdbcDaoSupport implements PersonDao {

    public void insertPerson(int id, String firstName, String lastName) {
        // TODO: Fügen Sie eine Person in die Datenbank ein. Verwenden Sie ein
        // SQL-Statement der Form:
        // insert into person(id, firstName, lastName) values (1, 'Anna', 'Gramm')
        getJdbcTemplate().update("insert into person(id, firstName, lastName) values (?, ?, ?)", id, firstName, lastName);
    }

    public int countAllPersons() {
        // TODO: Ermitteln Sie die Anzahl der Personen in der Tabelle
        return getJdbcTemplate().queryForObject("select count(*) from person", Integer.class);
    }

    public List<Person> findPersonsByLastName(String lastName) {
        // TODO: Rufen Sie die Liste der Personen mit den Nachname lastName ab.
        // Nutzen Sie mapPersonRow als RowMapper.
        return getJdbcTemplate().query("select * from person where lastName = ?", this::mapPersonRow,
                lastName);
    }

    public int updatePersonLastName(int id, String newLastName) {
        // TODO: Ersetzen Sie den Nachnamen der Person mit der entsprechenden Id und
        // geben Sie die Anzahl
        // der veränderten Datensätze zurück.
        return getJdbcTemplate().update("update person set lastName = ? where id = ?", newLastName, id);
    }

    public int deleteAll() {
        // TODO: Loeschen Sie alle Datensätze in der Tabelle 'person' und geben Sie
        // die Anzahl der geloeschten Datensätze zurück.
        return getJdbcTemplate().update("delete from person");
    }

    private Person mapPersonRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("firstName"));
        person.setLastName(rs.getString("lastName"));
        return person;
    }
}