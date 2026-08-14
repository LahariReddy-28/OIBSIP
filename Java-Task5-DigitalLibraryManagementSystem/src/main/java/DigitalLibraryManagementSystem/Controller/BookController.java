package DigitalLibraryManagementSystem.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DigitalLibraryManagementSystem.model.Book;
import DigitalLibraryManagementSystem.repository.BookRepository;

@Controller
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    public String viewBooks(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category,
            Model model) {

        List<Book> books = bookRepository.findAll();

        // =========================
        // SEARCH
        // =========================

        if (search != null && !search.trim().isEmpty()) {

            String keyword = search.trim().toLowerCase();

            books = books.stream()
                    .filter(book ->
                            (book.getTitle() != null &&
                             book.getTitle().toLowerCase().contains(keyword))

                            ||

                            (book.getAuthor() != null &&
                             book.getAuthor().toLowerCase().contains(keyword))

                            ||

                            (book.getIsbn() != null &&
                             book.getIsbn().toLowerCase().contains(keyword))

                            ||

                            (book.getCategory() != null &&
                             book.getCategory().toLowerCase().contains(keyword))
                    )
                    .toList();
        }

        // =========================
        // CATEGORY FILTER
        // =========================

        if (category != null &&
                !category.trim().isEmpty() &&
                !category.equalsIgnoreCase("ALL")) {

            String selectedCategory = category.trim().toLowerCase();

            books = books.stream()
                    .filter(book ->
                            book.getCategory() != null &&
                            book.getCategory()
                                .toLowerCase()
                                .equals(selectedCategory))
                    .toList();
        }

        // =========================
        // CATEGORY LIST
        // =========================

        List<String> categories = bookRepository.findAll()
                .stream()
                .map(Book::getCategory)
                .filter(c -> c != null && !c.trim().isEmpty())
                .distinct()
                .sorted()
                .toList();

        model.addAttribute("books", books);
        model.addAttribute("username", username);
        model.addAttribute("search", search);
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);

        return "books";
    }
}