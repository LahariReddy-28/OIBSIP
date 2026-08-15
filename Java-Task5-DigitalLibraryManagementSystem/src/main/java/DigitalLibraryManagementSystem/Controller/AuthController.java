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

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            Model model) {

        User user = userRepository
                .findByUsername(username)
                .orElse(null);

        if (user != null &&
                user.getPassword().equals(password)) {

            if (!role.equalsIgnoreCase(user.getRole())) {
                model.addAttribute(
                        "error",
                        "Incorrect role selected for this account!"
                );
                return "login";
            }

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/admin";
            }

            return "redirect:/dashboard?username=" + username;
        }

        model.addAttribute(
                "error",
                "Invalid username or password!"
        );

        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String email,
            Model model) {

        if (userRepository.findByUsername(username).isPresent()) {
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
        user.setRole("USER");

        userRepository.save(user);

        model.addAttribute(
                "success",
                "Registration successful! Please login."
        );

        return "login";
    }
}