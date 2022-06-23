package com.softserve.dimoybiyca.MVCApp2.DAO;

import com.softserve.dimoybiyca.MVCApp2.models.Book;
import com.softserve.dimoybiyca.MVCApp2.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM project1.\"Person\"",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM project1.\"Person\" WHERE id =?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
       jdbcTemplate.update("INSERT INTO project1.\"Person\"(name, birthday) VALUES(?, ?)",
               person.getName(),
               person.getBirthday());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE project1.\"Person\" SET name=?, birthday=? WHERE id=?",
                updatedPerson.getName(),
                updatedPerson.getBirthday(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM project1.\"Person\" WHERE id=?", id);
    }

    public List<Book> takenBooks(int id) {
        return jdbcTemplate.query("SELECT project1.\"Book\".* FROM project1.\"Book\" JOIN project1.\"Person\"\n" +
                "ON project1.\"Book\".\"ownerId\" = project1.\"Person\".\"id\" WHERE project1.\"Book\".\"ownerId\" = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
