package com.softserve.dimoybiyca.MVCApp2.DAO;

import com.softserve.dimoybiyca.MVCApp2.models.Book;
import com.softserve.dimoybiyca.MVCApp2.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();

        List<Book> books = session.createQuery("select b from Book b", Book.class)
                .getResultList();

        System.out.println("Books :" + books.toString());

        return books;
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, id);

        System.out.println("Book :" + book.toString());

        return book;
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();

        session.save(book);
    }

    @Transactional
    public void update(int id, Book updateBook) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, id);
        book.update(updateBook);
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, id);
        session.delete(book);
    }

    @Transactional(readOnly = true)
    public Optional<Person> owner(int id) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, id);

        System.out.println("Owner:" + book.getOwner());

        return Optional.ofNullable(book.getOwner());
    }

    @Transactional
    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, id);
        book.setOwner(null);
    }

    @Transactional
    public void appoint(int bookId, Person person) {
        Session session = sessionFactory.getCurrentSession();

        Book book = session.get(Book.class, bookId);
        book.setOwner(person);
    }
}
