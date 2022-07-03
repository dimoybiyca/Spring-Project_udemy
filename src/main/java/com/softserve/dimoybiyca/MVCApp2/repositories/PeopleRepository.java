package com.softserve.dimoybiyca.MVCApp2.repositories;

import com.softserve.dimoybiyca.MVCApp2.models.Book;
import com.softserve.dimoybiyca.MVCApp2.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
