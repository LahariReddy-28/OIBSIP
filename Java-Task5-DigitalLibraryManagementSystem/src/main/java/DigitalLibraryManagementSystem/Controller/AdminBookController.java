package DigitalLibraryManagementSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DigitalLibraryManagementSystem.model.Book;
import DigitalLibraryManagementSystem.repository.BookRepository;

@Controller
public class AdminBookController {

    private final BookRepository bookRepository;

    public AdminBookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // =========================
    // VIEW ALL BOOKS
    // =========================
    @GetMapping("/admin/books")
    public String viewBooks(Model model) {

        model.addAttribute("books", bookRepository.findAll());

        return "admin-books";
    }

    // =========================
    // ADD BOOK PAGE
    // =========================
    @GetMapping("/admin/books/add")
    public String addBookPage(Model model) {

        model.addAttribute("book", new Book());

        return "add-book";
    }

    // =========================
    // ADD BOOK
    // =========================
    @PostMapping("/admin/books/add")
    public String addBook(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam String category,
            @RequestParam int quantity,
            Model model) {

        // Basic validation
        if (title == null || title.trim().isEmpty()) {
            model.addAttribute("error", "Book title is required!");
            return "add-book";
        }

        if (author == null || author.trim().isEmpty()) {
            model.addAttribute("error", "Author is required!");
            return "add-book";
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            model.addAttribute("error", "ISBN is required!");
            return "add-book";
        }

        if (category == null || category.trim().isEmpty()) {
            model.addAttribute("error", "Category is required!");
            return "add-book";
        }

        if (quantity <= 0) {
            model.addAttribute("error", "Quantity must be greater than 0!");
            return "add-book";
        }

        // Create book
        Book book = new Book();

        book.setTitle(title.trim());
        book.setAuthor(author.trim());
        book.setIsbn(isbn.trim());
        book.setCategory(category.trim());
        book.setQuantity(quantity);
        book.setAvailableQuantity(quantity);

        bookRepository.save(book);

        return "redirect:/admin/books";
    }

    // =========================
    // EDIT BOOK PAGE
    // =========================
    @GetMapping("/admin/books/edit/{id}")
    public String editBookPage(
            @PathVariable Long id,
            Model model) {

        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return "redirect:/admin/books";
        }

        model.addAttribute("book", book);

        return "edit-book";
    }

    // =========================
    // UPDATE BOOK
    // =========================
    @PostMapping("/admin/books/update")
    public String updateBook(
            @RequestParam Long id,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String isbn,
            @RequestParam String category,
            @RequestParam int quantity,
            Model model) {

        Book book = bookRepository.findById(id).orElse(null);

        if (book == null) {
            return "redirect:/admin/books";
        }

        if (quantity <= 0) {
            model.addAttribute("error", "Quantity must be greater than 0!");
            model.addAttribute("book", book);
            return "edit-book";
        }

        // Calculate how many books are currently borrowed
        int borrowedQuantity =
                book.getQuantity() - book.getAvailableQuantity();

        // Quantity cannot be less than currently borrowed books
        if (quantity < borrowedQuantity) {
            model.addAttribute(
                    "error",
                    "Quantity cannot be less than currently borrowed books!"
            );

            model.addAttribute("book", book);

            return "edit-book";
        }

        book.setTitle(title.trim());
        book.setAuthor(author.trim());
        book.setIsbn(isbn.trim());
        book.setCategory(category.trim());
        book.setQuantity(quantity);

        // Preserve currently borrowed quantity
        book.setAvailableQuantity(quantity - borrowedQuantity);

        bookRepository.save(book);

        return "redirect:/admin/books";
    }

    // =========================
    // DELETE BOOK
    // =========================
    @GetMapping("/admin/books/delete/{id}")
    public String deleteBook(
            @PathVariable Long id) {

        if (bookRepository.existsById(id)) {

            bookRepository.deleteById(id);
        }

        return "redirect:/admin/books";
    }
}