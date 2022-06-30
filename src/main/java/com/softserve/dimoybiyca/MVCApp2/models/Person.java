package com.softserve.dimoybiyca.MVCApp2.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Book> books;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 1900, message = "Year of birth should be greater than 1900")
    @Max(value = 2023, message = "Year of birth should be less than 2023")
    @Column(name = "birthday")
    private int birthday;


    public Person() {

    }

    public Person(String name, int birthday) {
        this.name = name;
        this.birthday = birthday;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void update(Person updatedPerson) {
        this.setName(updatedPerson.getName());
        this.setBirthday(updatedPerson.getBirthday());
        this.setBooks(updatedPerson.getBooks());
    }


    @Override
    public String toString() {
        return "Person{" +
                "  id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}