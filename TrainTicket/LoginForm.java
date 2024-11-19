import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    
    private final String defaultUsername = "admin";
    private final String defaultPassword = "admin123";

    public LoginForm() {
        setTitle("Login Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        // UI components
        add(new JLabel("Username:"));
        usernameField = new JTextField(defaultUsername); 
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField(defaultPassword); 
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        JButton resetButton = new JButton("Reset");
        add(resetButton);

        // Button actions
        loginButton.addActionListener(e -> authenticateUser());
        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        setVisible(true); 
    }

    private void authenticateUser() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        
        if (username.equals(defaultUsername) && password.equals(defaultPassword)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            dispose(); 
            System.out.println("Opening Reservation System...");
            SwingUtilities.invokeLater(() -> new ReservationSystem()); 
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
}

