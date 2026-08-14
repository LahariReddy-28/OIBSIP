package DigitalLibraryManagementSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import DigitalLibraryManagementSystem.repository.UserRepository;

@Controller
public class DashboardController {

    private final UserRepository userRepository;

    public DashboardController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(
            @RequestParam String username,
            Model model) {

        // Check whether user exists
        if (userRepository.findByUsername(username).isEmpty()) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);

        return "dashboard";
    }
}