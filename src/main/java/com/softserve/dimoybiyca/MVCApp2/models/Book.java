package com.softserve.dimoybiyca.MVCApp2.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Person owner;

    @NotEmpty
    @Column(name = "title")
    private String title;

    @NotEmpty
    @Column(name = "author")
    private String author;

    @Max(value = 2023, message = "Year of publication should be less than 2023")
    @Column(name = "year_of_publication")
    private int yearOfPublication;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean overdue;

    public Book() {
    }

    public Book(String title, String author, int yearOfPublication, Date takenAt) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.takenAt = takenAt;
    }

    public Book(int id, String title, String author, int yearOfPublication, Date takenAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.takenAt = takenAt;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }
    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Person getOwner() {
        return owner;
    }
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTakenAt() {
        return takenAt;
    }
    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean getOverdue() {
        return overdue;
    }
    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public void update(Book updatedBook) {
        this.setTitle(updatedBook.getTitle());
        this.setAuthor(updatedBook.getAuthor());
        this.setYearOfPublication(updatedBook.getYearOfPublication());
        this.setOwner(updatedBook.getOwner());
        this.setTakenAt(updatedBook.getTakenAt());
    }

    public boolean isOverdue(int id) {
        Date current = new Date();
        Date takenAt = this.getTakenAt();

        return current.getTime() - takenAt.getTime() < 864_000_000;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", owner=" + owner +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }
}
