package com.softserve.dimoybiyca.MVCApp2.services;

import com.softserve.dimoybiyca.MVCApp2.models.Book;
import com.softserve.dimoybiyca.MVCApp2.models.Person;
import com.softserve.dimoybiyca.MVCApp2.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BookRepository bookRepository;

    @Autowired
    public BooksService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYearOfPublication) {
        if (sortByYearOfPublication) {
            return bookRepository.findAll(Sort.by("yearOfPublication"));
        } else {
            return bookRepository.findAll();
        }
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYearOfPublication) {
        if (sortByYearOfPublication) {
            return bookRepository.findAll(PageRequest
                    .of(page, booksPerPage, Sort.by("yearOfPublication"))).getContent();
        } else {
            return bookRepository.findAll(PageRequest
                    .of(page, booksPerPage)).getContent();
        }
    }

    public Book findOne(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> takenBooks(int ownerId) {
        return bookRepository.findByOwnerId(ownerId);
    }

    public Optional<Person> getBookOwner(int id) {
        return bookRepository.findById(id).map(Book::getOwner);
    }

    @Transactional
    public void release(int id) {
        bookRepository.findById(id).ifPresent(
                value -> {
                    value.setOwner(null);
                    value.setTakenAt(null);
                }
        );
    }

    @Transactional
    public void appoint(int bookId, Person person) {
        bookRepository.findById(bookId).ifPresent(
                book -> {
                    book.setOwner(person);
                    book.setTakenAt(new Date());
                }
        );
    }

    public List<Book> search(String title) {
        return bookRepository.findByTitleStartingWithIgnoreCase(title);
    }

    @Transactional
    public boolean isOverdue(int id) {
        Date current = new Date();
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            Date takenAt = book.get().getTakenAt();
            return takenAt.getTime() - current.getTime() < 864_000_000;
        } else {
            return false;
        }
    }
}
