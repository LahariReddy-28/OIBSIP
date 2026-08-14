package DigitalLibraryManagementSystem.Controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import DigitalLibraryManagementSystem.model.Book;
import DigitalLibraryManagementSystem.model.Borrow;
import DigitalLibraryManagementSystem.repository.BookRepository;
import DigitalLibraryManagementSystem.repository.BorrowRepository;
import DigitalLibraryManagementSystem.repository.UserRepository;

@Controller
public class AdminController {

    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    // =====================================================
    // LIBRARY SETTINGS
    // =====================================================

    private static final int BORROW_DAYS = 14;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public AdminController(
            BorrowRepository borrowRepository,
            UserRepository userRepository,
            BookRepository bookRepository) {

        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    // =====================================================
    // ADMIN DASHBOARD
    // =====================================================

    @GetMapping("/admin")
    public String adminDashboard(Model model) {

        model.addAttribute(
                "userCount",
                userRepository.count()
        );

        model.addAttribute(
                "borrowCount",
                borrowRepository.countByStatusIgnoreCase("BORROWED")
        );

        return "admin";
    }


    // =====================================================
    // VIEW USERS
    // =====================================================

    @GetMapping("/admin/users")
    public String viewUsers(Model model) {

        model.addAttribute(
                "users",
                userRepository.findAll()
        );

        return "users";
    }


    // =====================================================
    // VIEW ISSUED BOOKS
    // =====================================================

    @GetMapping("/admin/issued-books")
    public String issuedBooks(Model model) {

        List<Borrow> borrows =
                borrowRepository.findByStatusIgnoreCase("BORROWED");

        List<IssuedBookRecord> records =
                new ArrayList<>();

        LocalDate today = LocalDate.now();

        int overdueCount = 0;

        for (Borrow borrow : borrows) {

            if (borrow.getBorrowDate() == null) {
                continue;
            }

            LocalDate dueDate =
                    borrow.getBorrowDate()
                          .plusDays(BORROW_DAYS);

            boolean overdue =
                    today.isAfter(dueDate);

            if (overdue) {
                overdueCount++;
            }

            IssuedBookRecord record =
                    new IssuedBookRecord();

            record.setBorrow(borrow);
            record.setDueDate(dueDate);
            record.setOverdue(overdue);

            records.add(record);
        }


        model.addAttribute(
                "issuedBooks",
                records
        );

        model.addAttribute(
                "issuedCount",
                records.size()
        );

        model.addAttribute(
                "overdueCount",
                overdueCount
        );

        model.addAttribute(
                "borrowDays",
                BORROW_DAYS
        );

        model.addAttribute(
                "today",
                today
        );

        return "issued-books";
    }


    // =====================================================
    // RETURN BOOK
    // =====================================================

    @PostMapping("/admin/issued-books/return/{id}")
    public String returnBook(
            @PathVariable Long id) {

        Borrow borrow =
                borrowRepository
                        .findById(id)
                        .orElse(null);

        if (borrow == null) {
            return "redirect:/admin/issued-books";
        }


        // Already returned
        if ("RETURNED".equalsIgnoreCase(
                borrow.getStatus())) {

            return "redirect:/admin/issued-books";
        }


        Book book = borrow.getBook();


        // =================================================
        // UPDATE BOOK AVAILABILITY
        // =================================================

        if (book != null) {

            int available =
                    book.getAvailableQuantity();

            int total =
                    book.getQuantity();

            if (available < total) {

                book.setAvailableQuantity(
                        available + 1
                );

                bookRepository.save(book);
            }
        }


        // =================================================
        // UPDATE BORROW RECORD
        // =================================================

        borrow.setReturnDate(
                LocalDate.now()
        );

        borrow.setStatus(
                "RETURNED"
        );

        borrowRepository.save(borrow);


        return "redirect:/admin/issued-books";
    }


    // =====================================================
    // INNER CLASS
    // =====================================================

    public static class IssuedBookRecord {

        private Borrow borrow;

        private LocalDate dueDate;

        private boolean overdue;


        // =================================================
        // BORROW
        // =================================================

        public Borrow getBorrow() {
            return borrow;
        }

        public void setBorrow(Borrow borrow) {
            this.borrow = borrow;
        }


        // =================================================
        // DUE DATE
        // =================================================

        public LocalDate getDueDate() {
            return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
        }


        // =================================================
        // OVERDUE
        // =================================================

        public boolean isOverdue() {
            return overdue;
        }

        public void setOverdue(boolean overdue) {
            this.overdue = overdue;
        }
    }
}