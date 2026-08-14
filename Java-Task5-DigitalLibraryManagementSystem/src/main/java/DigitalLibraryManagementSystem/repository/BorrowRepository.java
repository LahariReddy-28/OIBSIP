package DigitalLibraryManagementSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import DigitalLibraryManagementSystem.model.Book;
import DigitalLibraryManagementSystem.model.Borrow;
import DigitalLibraryManagementSystem.model.User;

@Repository
public interface BorrowRepository
        extends JpaRepository<Borrow, Long> {


    // =====================================================
    // USER BORROWINGS
    // =====================================================

    List<Borrow> findByUser(User user);


    // =====================================================
    // USER + STATUS
    // =====================================================

    List<Borrow> findByUserAndStatus(
            User user,
            String status
    );


    // =====================================================
    // BOOK BORROWINGS
    // =====================================================

    List<Borrow> findByBook(Book book);


    // =====================================================
    // ADMIN - FILTER BY STATUS
    // =====================================================

    List<Borrow> findByStatusIgnoreCase(
            String status
    );


    // =====================================================
    // ADMIN - COUNT BY STATUS
    // =====================================================

    long countByStatusIgnoreCase(
            String status
    );
}