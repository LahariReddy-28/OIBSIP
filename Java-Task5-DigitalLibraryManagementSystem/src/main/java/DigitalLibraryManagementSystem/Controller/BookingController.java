package DigitalLibraryManagementSystem.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DigitalLibraryManagementSystem.model.Borrow;
import DigitalLibraryManagementSystem.model.User;
import DigitalLibraryManagementSystem.repository.BorrowRepository;
import DigitalLibraryManagementSystem.repository.UserRepository;

@Controller
public class BookingController {

    private final BorrowRepository borrowRepository;
    private final UserRepository userRepository;

    public BookingController(
            BorrowRepository borrowRepository,
            UserRepository userRepository) {

        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
    }

    // =====================================================
    // USER BOOKINGS
    // =====================================================

    @GetMapping("/bookings")
    public String viewBookings(
            @RequestParam String username,
            Model model) {

        Optional<User> userOptional =
                userRepository.findByUsername(username);

        // If user doesn't exist, go back to login
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }

        User user = userOptional.get();

        // Get all borrow/booking records of this user
        List<Borrow> bookings =
                borrowRepository.findByUser(user);

        model.addAttribute(
                "bookings",
                bookings
        );

        model.addAttribute(
                "username",
                username
        );

        return "bookings";
    }
}