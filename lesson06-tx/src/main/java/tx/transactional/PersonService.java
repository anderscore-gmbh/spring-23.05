package tx.transactional;

import tx.jdbc.Person;

import java.util.List;

public interface PersonService {
    void insert(List<Person> persons);

    void save(Person person);

    void insert(Person person);

    void update(Person person);

    List<Person> findAll();

    Person findById(int id);

    void deleteById(int id);
}
