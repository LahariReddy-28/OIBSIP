package DigitalLibraryManagementSystem.Controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import DigitalLibraryManagementSystem.model.ContactMessage;
import DigitalLibraryManagementSystem.repository.ContactMessageRepository;

@Controller
public class ContactController {

    private final ContactMessageRepository contactMessageRepository;

    public ContactController(
            ContactMessageRepository contactMessageRepository) {

        this.contactMessageRepository =
                contactMessageRepository;
    }


    // =====================================================
    // USER CONTACT PAGE
    // =====================================================

    @GetMapping("/contact")
    public String contactPage(
            @RequestParam(required = false) String username,
            Model model) {

        model.addAttribute("username", username);

        return "contact";
    }


    // =====================================================
    // SUBMIT QUERY
    // =====================================================

    @PostMapping("/contact/submit")
    public String submitContact(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message,
            @RequestParam(required = false) String username,
            RedirectAttributes redirectAttributes) {


        // Basic validation

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || subject == null || subject.trim().isEmpty()
                || message == null || message.trim().isEmpty()) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Please fill in all fields."
            );

            return "redirect:/contact?username=" +
                    (username == null ? "" : username);
        }


        // Create message

        ContactMessage contactMessage =
                new ContactMessage();

        contactMessage.setName(name.trim());

        contactMessage.setEmail(email.trim());

        contactMessage.setSubject(subject.trim());

        contactMessage.setMessage(message.trim());

        contactMessage.setStatus("NEW");

        contactMessage.setCreatedAt(
                LocalDateTime.now()
        );


        // Save to database

        contactMessageRepository.save(
                contactMessage
        );


        redirectAttributes.addFlashAttribute(
                "success",
                "Your query has been submitted successfully."
        );


        return "redirect:/contact?username=" +
                (username == null ? "" : username);
    }


    // =====================================================
    // ADMIN - VIEW QUERIES
    // =====================================================

    @GetMapping("/admin/queries")
    public String adminQueries(Model model) {

        List<ContactMessage> messages =
                contactMessageRepository
                        .findAllByOrderByCreatedAtDesc();

        model.addAttribute(
                "messages",
                messages
        );

        return "admin-queries";
    }


    // =====================================================
    // ADMIN - MARK AS RESOLVED
    // =====================================================

    @PostMapping("/admin/queries/resolve")
    public String resolveQuery(
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {

        ContactMessage message =
                contactMessageRepository
                        .findById(id)
                        .orElse(null);


        if (message == null) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Query not found."
            );

            return "redirect:/admin/queries";
        }


        message.setStatus("RESOLVED");

        contactMessageRepository.save(message);


        redirectAttributes.addFlashAttribute(
                "success",
                "Query marked as resolved."
        );


        return "redirect:/admin/queries";
    }


    // =====================================================
    // ADMIN - DELETE QUERY
    // =====================================================

    @PostMapping("/admin/queries/delete")
    public String deleteQuery(
            @RequestParam Long id,
            RedirectAttributes redirectAttributes) {

        if (!contactMessageRepository.existsById(id)) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Query not found."
            );

            return "redirect:/admin/queries";
        }


        contactMessageRepository.deleteById(id);


        redirectAttributes.addFlashAttribute(
                "success",
                "Query deleted successfully."
        );


        return "redirect:/admin/queries";
    }
}