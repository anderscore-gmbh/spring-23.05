package tx.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// TODO: Lassen Sie das DAO von JdbcDaoSupport erben
public class PersonDaoImpl implements PersonDao {

    public void insertPerson(int id, String firstName, String lastName) {
        // TODO: Fügen Sie eine Person in die Datenbank ein. Verwenden Sie ein
        // SQL-Statement der Form:
        // insert into person(id, firstName, lastName) values (1, 'Anna', 'Gramm')
    }

    public int countAllPersons() {
        // TODO: Ermitteln Sie die Anzahl der Personen in der Tabelle
        return -1;
    }

    public List<Person> findPersonsByLastName(String lastName) {
        // TODO: Rufen Sie die Liste der Personen mit den Nachname lastName ab.
        // Nutzen Sie mapPersonRow als RowMapper.
        return null;
    }

    public int updatePersonLastName(int id, String newLastName) {
        // TODO: Ersetzen Sie den Nachnamen der Person mit der entsprechenden Id und
        // geben Sie die Anzahl
        // der veränderten Datensätze zurück.
        return -1;
    }

    public int deleteAll() {
        // TODO: Loeschen Sie alle Datensätze in der Tabelle 'person' und geben Sie
        // die Anzahl der geloeschten Datensätze zurück.
        return -1;
    }

    private Person mapPersonRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("firstName"));
        person.setLastName(rs.getString("lastName"));

        return person;
    }
}