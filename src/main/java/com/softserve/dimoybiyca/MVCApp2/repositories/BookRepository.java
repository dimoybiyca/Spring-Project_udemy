package com.softserve.dimoybiyca.MVCApp2.repositories;

import com.softserve.dimoybiyca.MVCApp2.models.Book;
import com.softserve.dimoybiyca.MVCApp2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwnerId(int id);

    List<Book> findByTitleStartingWithIgnoreCase(String title);
}
