package DigitalLibraryManagementSystem.Controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import DigitalLibraryManagementSystem.model.Borrow;
import DigitalLibraryManagementSystem.repository.BorrowRepository;

@Controller
public class AdminFineController {

    private final BorrowRepository borrowRepository;

    private static final double FINE_PER_DAY = 10.0;

    private static final int BORROW_DAYS = 14;

    public AdminFineController(
            BorrowRepository borrowRepository) {

        this.borrowRepository = borrowRepository;
    }

    // =====================================================
    // ADMIN FINE MANAGEMENT
    // =====================================================

    @GetMapping("/admin/fines")
    public String adminFines(Model model) {

        List<Borrow> borrows =
                borrowRepository.findAll();

        List<FineRecord> fineRecords =
                new ArrayList<>();

        double totalFine = 0.0;

        LocalDate today = LocalDate.now();

        for (Borrow borrow : borrows) {

            if (borrow.getBorrowDate() == null) {
                continue;
            }

            LocalDate dueDate =
                    borrow.getBorrowDate()
                          .plusDays(BORROW_DAYS);

            int lateDays = 0;

            double fine = 0.0;

            // =============================================
            // CURRENTLY BORROWED
            // =============================================

            if ("BORROWED".equalsIgnoreCase(
                    borrow.getStatus())) {

                if (today.isAfter(dueDate)) {

                    lateDays =
                            (int) ChronoUnit.DAYS.between(
                                    dueDate,
                                    today
                            );

                    fine =
                            lateDays * FINE_PER_DAY;
                }
            }

            // =============================================
            // RETURNED
            // =============================================

            else if ("RETURNED".equalsIgnoreCase(
                    borrow.getStatus())) {

                if (borrow.getReturnDate() != null
                        && borrow.getReturnDate()
                                  .isAfter(dueDate)) {

                    lateDays =
                            (int) ChronoUnit.DAYS.between(
                                    dueDate,
                                    borrow.getReturnDate()
                            );

                    fine =
                            lateDays * FINE_PER_DAY;
                }
            }

            // =============================================
            // ADD FINE RECORD
            // =============================================

            if (fine > 0) {

                FineRecord record =
                        new FineRecord();

                record.setBorrow(borrow);

                record.setDueDate(dueDate);

                record.setLateDays(lateDays);

                record.setFine(fine);

                fineRecords.add(record);

                totalFine += fine;
            }
        }

        model.addAttribute(
                "fineRecords",
                fineRecords
        );

        model.addAttribute(
                "totalFine",
                totalFine
        );

        model.addAttribute(
                "finePerDay",
                FINE_PER_DAY
        );

        model.addAttribute(
                "borrowDays",
                BORROW_DAYS
        );

        model.addAttribute(
            "fineCount",
            fineRecords.size()
        );
        model.addAttribute("fineCount", fineRecords.size());

        return "admin-fines";
    }


    // =====================================================
    // FINE RECORD
    // =====================================================

    public static class FineRecord {

        private Borrow borrow;

        private LocalDate dueDate;

        private int lateDays;

        private double fine;


        public Borrow getBorrow() {
            return borrow;
        }

        public void setBorrow(Borrow borrow) {
            this.borrow = borrow;
        }


        public LocalDate getDueDate() {
            return dueDate;
        }

        public void setDueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
        }


        public int getLateDays() {
            return lateDays;
        }

        public void setLateDays(int lateDays) {
            this.lateDays = lateDays;
        }


        public double getFine() {
            return fine;
        }

        public void setFine(double fine) {
            this.fine = fine;
        }
    }
}