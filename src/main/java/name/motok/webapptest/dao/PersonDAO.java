//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package name.motok.webapptest.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import name.motok.webapptest.models.Person;

@Component
public class PersonDAO {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        PersonDAO.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper(Person.class));
    }

    public PersonDAO() {
    }

    public Person show(String name) {
        return (Person)jdbcTemplate.query("SELECT * FROM Person WHERE name = ?", new Object[]{name}, new BeanPropertyRowMapper(Person.class)).stream().findAny().orElse((Object)null);
    }

    public Person show(int id) {
        return (Person)jdbcTemplate.query("SELECT * FROM Person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper(Person.class)).stream().findAny().orElse((Object)null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, age, address) VALUES (?, ?, ?)", new Object[]{person.getName(), person.getAge(), person.getAddress()});
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET name = ?, age = ?, address = ? WHERE id = ?", new Object[]{person.getName(), person.getAge(), person.getAddress(), id});
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", new Object[]{id});
    }

    public void testBatchUpdate() {
        final List<Person> people = this.create1000people();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO Person (name, age) VALUES (?, ?)", new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, ((Person)people.get(i)).getName());
                ps.setInt(2, ((Person)people.get(i)).getAge());
            }

            public int getBatchSize() {
                return people.size();
            }
        });
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    public void testMultiUpdate() {
        List<Person> people = this.create1000people();
        long before = System.currentTimeMillis();

        for(Person person : people) {
            jdbcTemplate.update("INSERT INTO Person (name, age) VALUES (?, ?)", new Object[]{person.getName(), person.getAge()});
        }

        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000people() {
        List<Person> people = new ArrayList();

        for(int i = 0; i < 1000; ++i) {
            people.add(new Person(i, "name" + i, 30));
        }

        return people;
    }
}
