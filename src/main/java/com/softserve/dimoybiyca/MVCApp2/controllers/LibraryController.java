package com.softserve.dimoybiyca.MVCApp2.controllers;

import com.softserve.dimoybiyca.MVCApp2.DAO.BookDAO;
import com.softserve.dimoybiyca.MVCApp2.DAO.PersonDAO;
import com.softserve.dimoybiyca.MVCApp2.models.Book;
import com.softserve.dimoybiyca.MVCApp2.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class LibraryController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    @Autowired
    public LibraryController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book",bookDAO.show(id));
        model.addAttribute("isTaken", bookDAO.owner(id).isPresent());
        model.addAttribute("people", personDAO.index());
        model.addAttribute("person", new Person());

        if(bookDAO.owner(id).isPresent()){
            model.addAttribute("owner", bookDAO.owner(id).get());
        }

        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {

        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "books/new";
        }

        bookDAO.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("id") int id,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "books/edit";
        }

        bookDAO.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookDAO.release(id);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/appoint")
    public String appoint(@PathVariable("id") int id,
                          @ModelAttribute("person") Person person) {
        bookDAO.appoint(id, person.getId());

        return "redirect:/books/{id}";
    }
}