package DigitalLibraryManagementSystem.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import DigitalLibraryManagementSystem.model.Book;
import DigitalLibraryManagementSystem.model.Reservation;
import DigitalLibraryManagementSystem.model.User;
import DigitalLibraryManagementSystem.repository.BookRepository;
import DigitalLibraryManagementSystem.repository.ReservationRepository;
import DigitalLibraryManagementSystem.repository.UserRepository;

@Controller
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    public ReservationController(
            ReservationRepository reservationRepository,
            UserRepository userRepository,
            BookRepository bookRepository) {

        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }


    // =====================================================
    // RESERVE BOOK
    // =====================================================

    @PostMapping("/books/reserve")
    public String reserveBook(
            @RequestParam Long bookId,
            @RequestParam String username,
            RedirectAttributes redirectAttributes) {

        Optional<User> userOptional =
                userRepository.findByUsername(username);

        Optional<Book> bookOptional =
                bookRepository.findById(bookId);


        if (userOptional.isEmpty()) {

            return "redirect:/login";
        }


        if (bookOptional.isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Book not found."
            );

            return "redirect:/books?username=" + username;
        }


        User user = userOptional.get();

        Book book = bookOptional.get();


        // =================================================
        // CHECK AVAILABILITY
        // =================================================

        if (book.getAvailableQuantity() > 0) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "This book is currently available. You can borrow it."
            );

            return "redirect:/books?username=" + username;
        }


        // =================================================
        // CHECK EXISTING RESERVATION
        // =================================================

        boolean alreadyReserved =
                reservationRepository
                        .existsByUserAndBookAndStatus(
                                user,
                                book,
                                "WAITING"
                        );


        if (alreadyReserved) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "You have already reserved this book."
            );

            return "redirect:/books?username=" + username;
        }


        // =================================================
        // CREATE RESERVATION
        // =================================================

        Reservation reservation =
                new Reservation();

        reservation.setUser(user);

        reservation.setBook(book);

        reservation.setReservationDate(
                LocalDate.now()
        );

        reservation.setStatus("WAITING");


        reservationRepository.save(reservation);


        redirectAttributes.addFlashAttribute(
                "success",
                "Book reserved successfully."
        );


        return "redirect:/books?username=" + username;
    }


    // =====================================================
    // VIEW MY RESERVATIONS
    // =====================================================

    @GetMapping("/reservations")
    public String viewReservations(
            @RequestParam String username,
            Model model) {

        Optional<User> userOptional =
                userRepository.findByUsername(username);


        if (userOptional.isEmpty()) {

            return "redirect:/login";
        }


        User user = userOptional.get();


        List<Reservation> reservations =
                reservationRepository.findByUser(user);


        model.addAttribute(
                "reservations",
                reservations
        );

        model.addAttribute(
                "username",
                username
        );


        return "reservations";
    }


    // =====================================================
    // CANCEL RESERVATION
    // =====================================================

    @PostMapping("/reservations/cancel")
    public String cancelReservation(
            @RequestParam Long reservationId,
            @RequestParam String username,
            RedirectAttributes redirectAttributes) {

        Optional<Reservation> reservationOptional =
                reservationRepository.findById(
                        reservationId
                );


        if (reservationOptional.isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Reservation not found."
            );

            return "redirect:/reservations?username=" + username;
        }


        Reservation reservation =
                reservationOptional.get();


        reservation.setStatus("CANCELLED");

        reservationRepository.save(reservation);


        redirectAttributes.addFlashAttribute(
                "success",
                "Reservation cancelled successfully."
        );


        return "redirect:/reservations?username=" + username;
    }
}