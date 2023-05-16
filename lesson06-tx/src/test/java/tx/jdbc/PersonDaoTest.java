package tx.jdbc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig
@TestPropertySource(properties = {"jdbc.url=jdbc:hsqldb:mem:mymemdb", "jdbc.url2=jdbc:hsqldb:hsql://localhost/xdb"})
@ActiveProfiles("hikari")
public class PersonDaoTest {

    @Configuration
    @Import(DataSourceConfig.class)
    static class Config {

        @Bean
        public PersonDao personDao(){
            PersonDaoImpl dao = new PersonDaoImpl();
            // TODO: Setzen Sie die DataSource

            return dao;
        }
    }

    @Inject
    private PersonDao dao;

    @Test
    @Sql("/init-schema.sql")
    @Disabled
    void testCrudPerson() {
        dao.insertPerson(1, "Anna", "Gramm");
        dao.insertPerson(2, "Franz", "Brandwein");
        assertThat(dao.countAllPersons()).isEqualTo(2);

        List<Person> persons = dao.findPersonsByLastName("Gramm");
        assertThat(persons).hasSize(1);
        assertThat(persons.get(0).getFirstName()).isEqualTo("Anna");

        assertThat(dao.updatePersonLastName(1, "Nass"));
        persons = (dao.findPersonsByLastName("Nass"));
        assertThat(persons).hasSize(1);
        assertThat(persons.get(0).getFirstName()).isEqualTo("Anna");
        assertThat(dao.findPersonsByLastName("Gramm")).isEmpty();

        assertThat(dao.deleteAll()).isEqualTo(2);
        assertThat(dao.countAllPersons()).isEqualTo(0);
    }
}