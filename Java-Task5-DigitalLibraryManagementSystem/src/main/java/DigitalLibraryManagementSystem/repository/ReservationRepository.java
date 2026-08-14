package DigitalLibraryManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DigitalLibraryManagementSystem.model.Book;
import DigitalLibraryManagementSystem.model.Reservation;
import DigitalLibraryManagementSystem.model.User;

@Repository
public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

    List<Reservation> findByBook(Book book);

    boolean existsByUserAndBookAndStatus(
            User user,
            Book book,
            String status
    );
}