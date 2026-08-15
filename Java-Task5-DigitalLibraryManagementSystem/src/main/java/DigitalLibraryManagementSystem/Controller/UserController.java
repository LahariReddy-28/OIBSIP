package DigitalLibraryManagementSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    // =========================
    // USER HOME
    // =========================
    @GetMapping("/user")
    public String userHome() {
        return "home";
    }

    // =========================
    // USER BOOKS
    // =========================
    @GetMapping("/user/books")
    public String userBooks() {
        return "books";
    }

}