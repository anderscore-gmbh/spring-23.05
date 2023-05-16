package tx.jdbc;

import java.util.List;

public interface PersonDao {

    void insertPerson(int id, String firstName, String lastName);

    int countAllPersons();

    List<Person> findPersonsByLastName(String lastName);

    int updatePersonLastName(int id, String newLastName);

    int deleteAll();
}