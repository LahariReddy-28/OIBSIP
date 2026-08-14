package ui;

import dao.ReservationDAO;
import model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReservationFrame extends JFrame implements ActionListener {

    // Labels
    JLabel lblTitle;
    JLabel lblPassengerName;
    JLabel lblTrainNumber;
    JLabel lblTrainName;
    JLabel lblClassType;
    JLabel lblJourneyDate;
    JLabel lblSource;
    JLabel lblDestination;

    // Text fields
    JTextField txtPassengerName;
    JTextField txtJourneyDate;
    JTextField txtSource;
    JTextField txtDestination;

    // Combo boxes
    JComboBox<String> cmbTrainNumber;
    JComboBox<String> cmbTrainName;
    JComboBox<String> cmbClassType;

    // Buttons
    JButton btnBook;
    JButton btnClear;
    JButton btnCancellation;

    // DAO
    ReservationDAO reservationDAO = new ReservationDAO();

    // Constructor
    public ReservationFrame() {

        setTitle("Online Reservation System");
        setSize(550, 550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // --------------------------------
        // TITLE
        // --------------------------------

        lblTitle = new JLabel("TRAIN RESERVATION SYSTEM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBounds(120, 20, 320, 30);
        add(lblTitle);


        // --------------------------------
        // PASSENGER NAME
        // --------------------------------

        lblPassengerName = new JLabel("Passenger Name");
        lblPassengerName.setBounds(50, 80, 120, 25);
        add(lblPassengerName);

        txtPassengerName = new JTextField();
        txtPassengerName.setBounds(190, 80, 230, 25);
        add(txtPassengerName);


        // --------------------------------
        // TRAIN NUMBER
        // --------------------------------

        lblTrainNumber = new JLabel("Train Number");
        lblTrainNumber.setBounds(50, 120, 120, 25);
        add(lblTrainNumber);

        String[] trainNumbers = {
                "12723",
                "12724",
                "12603",
                "12759",
                "17011",
                "12785"
        };

        cmbTrainNumber = new JComboBox<String>(trainNumbers);
        cmbTrainNumber.setBounds(190, 120, 230, 25);
        add(cmbTrainNumber);


        // --------------------------------
        // TRAIN NAME
        // --------------------------------

        lblTrainName = new JLabel("Train Name");
        lblTrainName.setBounds(50, 160, 120, 25);
        add(lblTrainName);

        String[] trainNames = {
                "Telangana Express",
                "Telangana Express",
                "Charminar Express",
                "Charminar Express",
                "Hyderabad Express",
                "Kacheguda Express"
        };

        cmbTrainName = new JComboBox<String>(trainNames);
        cmbTrainName.setBounds(190, 160, 230, 25);
        add(cmbTrainName);


        // --------------------------------
        // TRAIN NUMBER AND TRAIN NAME
        // SYNCHRONIZATION
        // --------------------------------

        cmbTrainNumber.addActionListener(e -> {

            int index = cmbTrainNumber.getSelectedIndex();

            if (index >= 0 && index < cmbTrainName.getItemCount()) {
                cmbTrainName.setSelectedIndex(index);
            }
        });


        // --------------------------------
        // CLASS TYPE
        // --------------------------------

        lblClassType = new JLabel("Class Type");
        lblClassType.setBounds(50, 200, 120, 25);
        add(lblClassType);

        String[] classTypes = {
                "Sleeper",
                "3AC",
                "2AC",
                "1AC"
        };

        cmbClassType = new JComboBox<String>(classTypes);
        cmbClassType.setBounds(190, 200, 230, 25);
        add(cmbClassType);


        // --------------------------------
        // JOURNEY DATE
        // --------------------------------

        lblJourneyDate = new JLabel("Journey Date");
        lblJourneyDate.setBounds(50, 240, 120, 25);
        add(lblJourneyDate);

        txtJourneyDate = new JTextField();
        txtJourneyDate.setBounds(190, 240, 230, 25);
        txtJourneyDate.setToolTipText("Format: yyyy-MM-dd");
        add(txtJourneyDate);


        // --------------------------------
        // SOURCE
        // --------------------------------

        lblSource = new JLabel("Source Station");
        lblSource.setBounds(50, 280, 120, 25);
        add(lblSource);

        txtSource = new JTextField();
        txtSource.setBounds(190, 280, 230, 25);
        add(txtSource);


        // --------------------------------
        // DESTINATION
        // --------------------------------

        lblDestination = new JLabel("Destination");
        lblDestination.setBounds(50, 320, 120, 25);
        add(lblDestination);

        txtDestination = new JTextField();
        txtDestination.setBounds(190, 320, 230, 25);
        add(txtDestination);


        // --------------------------------
        // BOOK BUTTON
        // --------------------------------

        btnBook = new JButton("Book Ticket");
        btnBook.setBounds(40, 390, 140, 40);
        btnBook.addActionListener(this);
        add(btnBook);


        // --------------------------------
        // CLEAR BUTTON
        // --------------------------------

        btnClear = new JButton("Clear");
        btnClear.setBounds(200, 390, 100, 40);
        btnClear.addActionListener(this);
        add(btnClear);


        // --------------------------------
        // CANCELLATION BUTTON
        // --------------------------------

        btnCancellation = new JButton("Cancel Ticket");
        btnCancellation.setBounds(320, 390, 160, 40);
        btnCancellation.addActionListener(this);
        add(btnCancellation);


        // Show window
        setVisible(true);
    }


    // ====================================
    // BOOK TICKET
    // ====================================

    private void bookTicket() {

        // Passenger validation
        String passengerName =
                txtPassengerName.getText().trim();

        if (passengerName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter passenger name.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // Get train number
        String trainNumberText =
                cmbTrainNumber.getSelectedItem().toString();

        int trainNumber;

        try {

            trainNumber =
                    Integer.parseInt(trainNumberText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid train number.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // Get train name
        String trainName =
                cmbTrainName.getSelectedItem().toString();


        // Get class
        String classType =
                cmbClassType.getSelectedItem().toString();


        // Get journey date
        String journeyDate =
                txtJourneyDate.getText().trim();

        if (journeyDate.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter journey date.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // Validate date
        try {

            LocalDate.parse(
                    journeyDate,
                    DateTimeFormatter.ISO_LOCAL_DATE
            );

        } catch (DateTimeParseException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid date format.\n\n" +
                    "Use: yyyy-MM-dd\n" +
                    "Example: 2026-08-20",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // Source
        String source =
                txtSource.getText().trim();

        if (source.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter source station.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // Destination
        String destination =
                txtDestination.getText().trim();

        if (destination.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter destination station.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // --------------------------------
        // GENERATE PNR
        // --------------------------------

        String pnr =
                "PNR" + System.currentTimeMillis();


        // --------------------------------
        // CREATE RESERVATION OBJECT
        // --------------------------------

        Reservation reservation =
                new Reservation(
                        pnr,
                        passengerName,
                        trainNumber,
                        trainName,
                        classType,
                        journeyDate,
                        source,
                        destination
                );


        // --------------------------------
        // INSERT INTO DATABASE
        // --------------------------------

        boolean success =
                reservationDAO.insertReservation(
                        reservation
                );


        // --------------------------------
        // SHOW RESULT
        // --------------------------------

        if (success) {

            JOptionPane.showMessageDialog(
                    this,

                    "BOOKING SUCCESSFUL!\n\n" +

                    "PNR Number : " + pnr + "\n" +

                    "Passenger  : " + passengerName + "\n" +

                    "Train No   : " + trainNumber + "\n" +

                    "Train Name : " + trainName + "\n" +

                    "Class      : " + classType + "\n" +

                    "Date       : " + journeyDate + "\n" +

                    "From       : " + source + "\n" +

                    "To         : " + destination,

                    "Booking Confirmation",

                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Booking failed.\nPlease try again.",
                    "Booking Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ====================================
    // CLEAR FORM
    // ====================================

    private void clearForm() {

        txtPassengerName.setText("");

        txtJourneyDate.setText("");

        txtSource.setText("");

        txtDestination.setText("");

        cmbTrainNumber.setSelectedIndex(0);

        cmbTrainName.setSelectedIndex(0);

        cmbClassType.setSelectedIndex(0);
    }


    // ====================================
    // BUTTON ACTIONS
    // ====================================

    @Override
    public void actionPerformed(ActionEvent e) {

        // Book
        if (e.getSource() == btnBook) {

            bookTicket();
        }

        // Clear
        else if (e.getSource() == btnClear) {

            clearForm();
        }

        // Cancellation
        else if (e.getSource() == btnCancellation) {

            new CancellationFrame();
        }
    }


    // ====================================
    // MAIN METHOD
    // ====================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new ReservationFrame();
        });
    }
}