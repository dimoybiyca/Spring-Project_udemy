package com.softserve.dimoybiyca.MVCApp2.DAO;

import com.softserve.dimoybiyca.MVCApp2.models.Book;
import com.softserve.dimoybiyca.MVCApp2.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM project1.\"Book\"",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM project1.\"Book\" WHERE id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO project1.\"Book\"(title, author, \"yearOfPublication\") VALUES(?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYearOfPublication());
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE project1.\"Book\" SET title=?, author=?, \"yearOfPublication\" = ? WHERE id=?",
                updateBook.getTitle(),
                updateBook.getAuthor(),
                updateBook.getYearOfPublication(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM project1.\"Book\" WHERE id=?", id);
    }

    public Optional<Person> owner(int id) {
        return jdbcTemplate.query("SELECT project1.\"Person\".* FROM project1.\"Book\" JOIN project1.\"Person\"\n" +
                "ON project1.\"Book\".\"ownerId\" = project1.\"Person\".\"id\" WHERE project1.\"Book\".id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void release(int id) {
        jdbcTemplate.update("UPDATE project1.\"Book\" SET \"ownerId\"=NULL WHERE id=?", id);
    }

    public void appoint(int bookId, int personId) {
        jdbcTemplate.update("UPDATE project1.\"Book\" SET \"ownerId\"=? WHERE id=?", personId, bookId);
    }
}
