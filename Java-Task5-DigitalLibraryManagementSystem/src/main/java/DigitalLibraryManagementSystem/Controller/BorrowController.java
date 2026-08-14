package DigitalLibraryManagementSystem.Controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DigitalLibraryManagementSystem.model.Book;
import DigitalLibraryManagementSystem.model.Borrow;
import DigitalLibraryManagementSystem.model.User;
import DigitalLibraryManagementSystem.repository.BookRepository;
import DigitalLibraryManagementSystem.repository.BorrowRepository;
import DigitalLibraryManagementSystem.repository.UserRepository;

@Controller
public class BorrowController {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BorrowController(
            BorrowRepository borrowRepository,
            BookRepository bookRepository,
            UserRepository userRepository) {

        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }


    // =====================================================
    // BORROW BOOK
    // =====================================================

    @PostMapping("/books/borrow")
    public String borrowBook(
            @RequestParam Long bookId,
            @RequestParam String username) {

        Optional<User> userOptional =
                userRepository.findByUsername(username);

        Optional<Book> bookOptional =
                bookRepository.findById(bookId);

        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }

        if (bookOptional.isEmpty()) {
            return "redirect:/books?username=" + username
                    + "&error=Book+not+found";
        }

        User user = userOptional.get();
        Book book = bookOptional.get();


        // Check availability

        if (book.getAvailableQuantity() <= 0) {

            return "redirect:/books?username="
                    + username
                    + "&error=Book+is+not+available";
        }


        // Check already borrowed

        for (Borrow borrow : borrowRepository.findByUser(user)) {

            if (borrow.getBook() != null
                    && borrow.getBook().getId().equals(book.getId())
                    && "BORROWED".equals(borrow.getStatus())) {

                return "redirect:/books?username="
                        + username
                        + "&error=Already+borrowed";
            }
        }


        // Reduce available quantity

        book.setAvailableQuantity(
                book.getAvailableQuantity() - 1
        );

        bookRepository.save(book);


        // Create borrow record

        Borrow borrow = new Borrow();

        borrow.setUser(user);
        borrow.setBook(book);
        borrow.setBorrowDate(LocalDate.now());
        borrow.setStatus("BORROWED");

        borrowRepository.save(borrow);


        // Go to My Books

        return "redirect:/my-books?username=" + username;
    }


    // =====================================================
    // MY BOOKS
    // =====================================================

    @GetMapping("/my-books")
    public String myBooks(
            @RequestParam String username,
            Model model) {

        Optional<User> userOptional =
                userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOptional.get();

        model.addAttribute(
                "borrows",
                borrowRepository.findByUser(user)
        );

        model.addAttribute(
                "username",
                username
        );

        return "my-books";
    }


    // =====================================================
    // RETURN BOOK
    // =====================================================

    @PostMapping("/books/return")
    public String returnBook(
            @RequestParam Long borrowId,
            @RequestParam String username) {

        Optional<Borrow> borrowOptional =
                borrowRepository.findById(borrowId);

        if (borrowOptional.isPresent()) {

            Borrow borrow = borrowOptional.get();

            if ("BORROWED".equals(borrow.getStatus())) {

                borrow.setStatus("RETURNED");

                borrow.setReturnDate(
                        LocalDate.now()
                );

                Book book = borrow.getBook();

                if (book != null) {

                    book.setAvailableQuantity(
                            book.getAvailableQuantity() + 1
                    );

                    bookRepository.save(book);
                }

                borrowRepository.save(borrow);
            }
        }

        return "redirect:/my-books?username=" + username;
    }
}