package ui;

import dao.ReservationDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.Reservation;

public class CancellationFrame extends JFrame implements ActionListener {
        JLabel lblTitle;
        JLabel lblPNR;
        JLabel lblPassenger;
        JLabel lblTrain;
        JLabel lblTrainName;
        JLabel lblClass;
        JLabel lblDate;
        JLabel lblSource;
        JLabel lblDestination;
        JTextField txtPNR;
        JTextField txtPassenger;
        JTextField txtTrain;
        JTextField txtTrainName;
        JTextField txtClass;
        JTextField txtDate;
        JTextField txtSource;
        JTextField txtDestination;
        JButton btnFetch;
        JButton btnCancel;
        ReservationDAO reservationDAO =new ReservationDAO();
        private Reservation currentReservation;
        public CancellationFrame() {

        setTitle("Cancel Reservation");
        setSize(550, 550);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblTitle =
                new JLabel("CANCEL TICKET");

        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        lblTitle.setBounds(190, 20, 200, 30);
        add(lblTitle);

        // PNR
        lblPNR = new JLabel("PNR Number");
        lblPNR.setBounds(50, 75, 120, 25);
        add(lblPNR);

        txtPNR = new JTextField();
        txtPNR.setBounds(180, 75, 200, 25);
        add(txtPNR);

        btnFetch = new JButton("Fetch");
        btnFetch.setBounds(390, 75, 90, 25);
        btnFetch.addActionListener(this);
        add(btnFetch);

        // Passenger
        lblPassenger = new JLabel("Passenger");
        lblPassenger.setBounds(50, 125, 120, 25);
        add(lblPassenger);

        txtPassenger = createReadOnlyField(180, 125);
        add(txtPassenger);

        // Train
        lblTrain = new JLabel("Train Number");
        lblTrain.setBounds(50, 165, 120, 25);
        add(lblTrain);

        txtTrain = createReadOnlyField(180, 165);
        add(txtTrain);

        // Train Name
        lblTrainName = new JLabel("Train Name");
        lblTrainName.setBounds(50, 205, 120, 25);
        add(lblTrainName);

        txtTrainName = createReadOnlyField(180, 205);
        add(txtTrainName);

        // Class
        lblClass = new JLabel("Class");
        lblClass.setBounds(50, 245, 120, 25);
        add(lblClass);

        txtClass = createReadOnlyField(180, 245);
        add(txtClass);

        // Date
        lblDate = new JLabel("Journey Date");
        lblDate.setBounds(50, 285, 120, 25);
        add(lblDate);

        txtDate = createReadOnlyField(180, 285);
        add(txtDate);

        // Source
        lblSource = new JLabel("Source");
        lblSource.setBounds(50, 325, 120, 25);
        add(lblSource);

        txtSource = createReadOnlyField(180, 325);
        add(txtSource);

        // Destination
        lblDestination = new JLabel("Destination");
        lblDestination.setBounds(50, 365, 120, 25);
        add(lblDestination);

        txtDestination = createReadOnlyField(180, 365);
        add(txtDestination);

        // Cancel
        btnCancel =
                new JButton("Confirm Cancellation");

        btnCancel.setBounds(170, 420, 210, 40);
        btnCancel.addActionListener(this);
        btnCancel.setEnabled(false);

        add(btnCancel);

        setVisible(true);
    }

    private JTextField createReadOnlyField(
            int x,
            int y) {

        JTextField field = new JTextField();

        field.setBounds(x, y, 250, 25);
        field.setEditable(false);

        return field;
    }

    private void fetchReservation() {

        String pnr =
                txtPNR.getText().trim();

        if (pnr.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter PNR number."
            );

            return;
        }

        currentReservation =
                reservationDAO.getReservation(pnr);

        if (currentReservation == null) {

            clearDetails();

            btnCancel.setEnabled(false);

            JOptionPane.showMessageDialog(
                    this,
                    "Reservation not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        txtPassenger.setText(
                currentReservation.getPassengerName()
        );

        txtTrain.setText(
                String.valueOf(
                        currentReservation.getTrainNumber()
                )
        );

        txtTrainName.setText(
                currentReservation.getTrainName()
        );

        txtClass.setText(
                currentReservation.getClassType()
        );

        txtDate.setText(
                currentReservation.getJourneyDate()
        );

        txtSource.setText(
                currentReservation.getSource()
        );

        txtDestination.setText(
                currentReservation.getDestination()
        );

        btnCancel.setEnabled(true);
    }

    private void cancelReservation() {

        if (currentReservation == null) {
            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to cancel\n" +
                        "this reservation?",
                        "Confirm Cancellation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

        if (choice == JOptionPane.YES_OPTION) {

            boolean success =
                    reservationDAO.cancelReservation(
                            currentReservation.getPnr()
                    );

            if (success) {

                JOptionPane.showMessageDialog(
                        this,
                        "Reservation cancelled successfully."
                );

                clearDetails();

                txtPNR.setText("");

                currentReservation = null;

                btnCancel.setEnabled(false);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Cancellation failed.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void clearDetails() {

        txtPassenger.setText("");
        txtTrain.setText("");
        txtTrainName.setText("");
        txtClass.setText("");
        txtDate.setText("");
        txtSource.setText("");
        txtDestination.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnFetch) {

            fetchReservation();
        }

        else if (e.getSource() == btnCancel) {

            cancelReservation();
        }
    }
}