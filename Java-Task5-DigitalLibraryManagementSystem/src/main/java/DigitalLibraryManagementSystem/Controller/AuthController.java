package DigitalLibraryManagementSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DigitalLibraryManagementSystem.model.User;
import DigitalLibraryManagementSystem.repository.UserRepository;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // =========================
    // LOGIN PAGE
    // =========================

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // =========================
    // LOGIN
    // =========================

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            Model model) {

        User user = userRepository
                .findByUsername(username)
                .orElse(null);

        if (user == null) {
            model.addAttribute(
                    "error",
                    "Username not found!"
            );
            return "login";
        }

        if (!user.getPassword().equals(password)) {
            model.addAttribute(
                    "error",
                    "Incorrect password!"
            );
            return "login";
        }

        if (!role.equalsIgnoreCase(user.getRole())) {
            model.addAttribute(
                    "error",
                    "Incorrect role selected!"
            );
            return "login";
        }

        // ADMIN LOGIN
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/admin";
        }

        // USER LOGIN
        return "redirect:/dashboard?username="
                + username;
    }

    // =========================
    // REGISTER PAGE
    // =========================

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // =========================
    // REGISTER
    // =========================

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String email,
            Model model) {

        if (userRepository
                .findByUsername(username)
                .isPresent()) {

            model.addAttribute(
                    "error",
                    "Username already exists!"
            );

            return "register";
        }

        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // New registrations are normal users
        user.setRole("USER");

        userRepository.save(user);

        model.addAttribute(
                "success",
                "Registration successful! Please login."
        );

        return "login";
    }
}