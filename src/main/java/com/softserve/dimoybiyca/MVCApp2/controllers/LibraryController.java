package com.softserve.dimoybiyca.MVCApp2.controllers;

import com.softserve.dimoybiyca.MVCApp2.models.Book;
import com.softserve.dimoybiyca.MVCApp2.models.Person;
import com.softserve.dimoybiyca.MVCApp2.services.BooksService;
import com.softserve.dimoybiyca.MVCApp2.services.PeopleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class LibraryController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    public LibraryController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year_of_publication", required = false) boolean sortByYearOfPublication) {
        if(page == null || booksPerPage == null) {
            model.addAttribute("books", booksService.findAll(sortByYearOfPublication));
        } else {
            model.addAttribute("books",
                    booksService.findWithPagination(page, booksPerPage, sortByYearOfPublication));
        }

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("book",booksService.findOne(id));
        model.addAttribute("isTaken", booksService.getBookOwner(id).isPresent());
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("person", new Person());

        if(booksService.getBookOwner(id).isPresent()){
            model.addAttribute("owner", booksService.getBookOwner(id).get());
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

        booksService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));

        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("id") int id,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "books/edit";
        }

        booksService.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/appoint")
    public String appoint(@PathVariable("id") int id,
                          @ModelAttribute("person") Person person) {
        booksService.appoint(id, peopleService.findOne(person.getId()));

        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam("query") String query) {
        model.addAttribute("books",
                booksService.search(query));
        return "books/search";
    }
}