package tx.transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import tx.jdbc.Person;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Transactional
public class PersonServiceImpl implements PersonService {

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public void insert(List<Person> persons) {
        persons.forEach(this::insert);
    }

    @Override
    public void save(Person person) {
        if (exists(person.getId())) {
            update(person);
        } else {
            insert(person);
        }
    }

    private boolean exists(int personId) {
        String sql = "select count(*) from person where id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, personId);
        return count != 0;
    }

    @Override
    public void insert(Person person) {
        String sql = "insert into person(id, firstName, lastName) values (?, ?, ?)";
        jdbcTemplate.update(sql, person.getId(), person.getFirstName(), person.getLastName());
    }

    @Override
    public void update(Person person) {
        String sql = "update person set firstName = ?, lastName = ? where id = ?";
        jdbcTemplate.update(sql, person.getFirstName(), person.getLastName(), person.getId());
    }

    @Override
    public List<Person> findAll() {
        String sql = "select id, firstName, lastName from person";
        List<Person> persons = jdbcTemplate.query(sql, this::mapPersonRow);
        return persons;
    }

    @Override
    public Person findById(int id) {
        String sql = "select id, firstName, lastName from person where id = ?";
        Person  person = jdbcTemplate.queryForObject(sql, this::mapPersonRow, id);
        return person;
    }

    private Person mapPersonRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(rs.getInt("id"));
        person.setFirstName(rs.getString("firstName"));
        person.setLastName(rs.getString("lastName"));

        return person;
    }

    @Override
    public void deleteById(int id) {
        String sql = "delete from person where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
