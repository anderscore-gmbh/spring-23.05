package tx.transactional;

import tx.jdbc.Person;

import java.util.Arrays;
import java.util.List;

public final class DummyPersonFactory {
    private DummyPersonFactory() {
    }

    public static List<Person> createPersons() {
        return Arrays.asList(
                new Person(1, "Anna", "Gramm"),
                new Person(2, "Franz", "Brandwein"),
                new Person(3, "Izmir", "Egal")
        );
    }

    public static List<Person> createPersonsWithConflict() {
        List<Person> persons = createPersons();
        persons.get(2).setId(2);
        return persons;
    }
}
