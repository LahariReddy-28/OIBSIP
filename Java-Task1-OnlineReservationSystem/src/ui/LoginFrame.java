package ui;

import dao.LoginDAO;
import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {

    JLabel lblTitle;
    JLabel lblUsername;
    JLabel lblPassword;

    JTextField txtUsername;
    JPasswordField txtPassword;

    JButton btnLogin;
    JButton btnClear;

    public LoginFrame() {

        setTitle("Online Reservation System");
        setSize(450, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title
        lblTitle = new JLabel("ONLINE RESERVATION SYSTEM");

        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        lblTitle.setBounds(60, 20, 330, 30);
        add(lblTitle);

        // Username
        lblUsername = new JLabel("Username");
        lblUsername.setBounds(50, 80, 100, 25);
        add(lblUsername);

        txtUsername = new JTextField();
        txtUsername.setBounds(160, 80, 180, 25);
        add(txtUsername);

        // Password
        lblPassword = new JLabel("Password");
        lblPassword.setBounds(50, 120, 100, 25);
        add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(160, 120, 180, 25);
        add(txtPassword);

        // Login button
        btnLogin = new JButton("Login");
        btnLogin.setBounds(90, 190, 100, 35);
        add(btnLogin);

        // Clear button
        btnClear = new JButton("Clear");
        btnClear.setBounds(230, 190, 100, 35);
        add(btnClear);

        // Login action
        btnLogin.addActionListener(e -> login());

        // Clear action
        btnClear.addActionListener(e -> {
            txtUsername.setText("");
            txtPassword.setText("");
        });

        setVisible(true);
    }

    private void login() {

        String username =
                txtUsername.getText().trim();

        String password =
                new String(txtPassword.getPassword());

        // Empty field validation
        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        LoginDAO dao = new LoginDAO();

        boolean valid =
                dao.validateLogin(username, password);

        if (valid) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login Successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new ReservationFrame();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid Username or Password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}