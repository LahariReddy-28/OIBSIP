package DigitalLibraryManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DigitalLibraryManagementSystem.model.ContactMessage;

@Repository
public interface ContactMessageRepository
        extends JpaRepository<ContactMessage, Long> {

    List<ContactMessage> findAllByOrderByCreatedAtDesc();
}