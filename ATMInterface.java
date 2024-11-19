import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class ATMInterface extends JFrame {
    private String currentUser;
    private JTextField amountField;

    private static HashMap<String, String> users = new HashMap<>();
    private static HashMap<String, Double> balances = new HashMap<>();
    private static HashMap<String, ArrayList<String>> transactionHistory = new HashMap<>();

    public ATMInterface() {
        users.put("user1", "password1");
        users.put("user2", "password2");
        users.put("user3", "password3");

        balances.put("user1", 1000.00);
        balances.put("user2", 500.00);
        balances.put("user3", 1200.00);

        for (String user : users.keySet()) {
            transactionHistory.put(user, new ArrayList<>());
        }

        setTitle("ATM Interface");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new CardLayout());

        JPanel loginPanel = createLoginPanel();
        add(loginPanel, "Login");

        JPanel mainMenuPanel = createMainMenuPanel();
        add(mainMenuPanel, "MainMenu");

        showLoginScreen();
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        userLabel.setBounds(140, 80, 80, 25);
        userField.setBounds(220, 80, 100, 25);
        passLabel.setBounds(140, 120, 80, 25);
        passField.setBounds(220, 120, 100, 25);
        loginButton.setBounds(170, 160, 100, 30);

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (authenticate(username, password)) {
                currentUser = username;
                showMainMenu();
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid username or password!");
            }
        });

        return panel;
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0); 
        gbc.fill = GridBagConstraints.NONE;

        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton transferButton = new JButton("Transfer");
        JButton showBalanceButton = new JButton("Show Balance");
        JButton exitButton = new JButton("Exit");

        
        Dimension buttonSize = new Dimension(140, 30);
        transactionHistoryButton.setPreferredSize(buttonSize);
        withdrawButton.setPreferredSize(buttonSize);
        depositButton.setPreferredSize(buttonSize);
        transferButton.setPreferredSize(buttonSize);
        showBalanceButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        
        panel.add(transactionHistoryButton, gbc);
        panel.add(withdrawButton, gbc);
        panel.add(depositButton, gbc);
        panel.add(transferButton, gbc);
        panel.add(showBalanceButton, gbc);
        panel.add(exitButton, gbc);

        transactionHistoryButton.addActionListener(e -> showTransactionHistory());
        withdrawButton.addActionListener(e -> showWithdrawScreen());
        depositButton.addActionListener(e -> showDepositScreen());
        transferButton.addActionListener(e -> showTransferScreen());
        showBalanceButton.addActionListener(e -> showBalance());
        exitButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(panel, "Thank you for using our ATM. Goodbye!");
            System.exit(0);
        });

        return panel;
    }

    private void showLoginScreen() {
        CardLayout cl = (CardLayout) (getContentPane().getLayout());
        cl.show(getContentPane(), "Login");
    }

    private void showMainMenu() {
        CardLayout cl = (CardLayout) (getContentPane().getLayout());
        cl.show(getContentPane(), "MainMenu");
    }

    private void showTransactionHistory() {
        ArrayList<String> history = transactionHistory.get(currentUser);
        StringBuilder historyText = new StringBuilder("Transaction History: \n");

        if (history.isEmpty()) {
            historyText.append("No transactions yet.\n");
        } else {
            for (String transaction : history) {
                historyText.append(transaction).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, historyText.toString());
    }

    private void showWithdrawScreen() {
        amountField = new JTextField();
        Object[] message = {"Enter amount to withdraw:", amountField};

        int option = JOptionPane.showConfirmDialog(this, message, "Withdraw", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            double amount = Double.parseDouble(amountField.getText());
            withdraw(amount);
        }
    }

    private void showDepositScreen() {
        amountField = new JTextField();
        Object[] message = {"Enter amount to deposit:", amountField};

        int option = JOptionPane.showConfirmDialog(this, message, "Deposit", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            double amount = Double.parseDouble(amountField.getText());
            deposit(amount);
        }
    }

    private void showTransferScreen() {
        amountField = new JTextField();
        Object[] message = {"Enter amount to transfer:", amountField};

        int option = JOptionPane.showConfirmDialog(this, message, "Transfer", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            double amount = Double.parseDouble(amountField.getText());
            transfer(amount);
        }
    }

    private void showBalance() {
        double balance = balances.get(currentUser);
        JOptionPane.showMessageDialog(this, "Current Balance: $" + balance);
    }

    private boolean authenticate(String username, String password) {
        return password.equals(users.get(username));
    }

    private void withdraw(double amount) {
        double userBalance = balances.get(currentUser);
        if (amount <= userBalance) {
            balances.put(currentUser, userBalance - amount);
            transactionHistory.get(currentUser).add("Withdrew: $" + amount);
            JOptionPane.showMessageDialog(this, "Successfully withdrawn: $" + amount);
        } else {
            JOptionPane.showMessageDialog(this, "Insufficient balance.");
        }
    }

    private void deposit(double amount) {
        double userBalance = balances.get(currentUser);
        balances.put(currentUser, userBalance + amount);
        transactionHistory.get(currentUser).add("Deposited: $" + amount);
        JOptionPane.showMessageDialog(this, "Successfully deposited: $" + amount);
    }

    private void transfer(double amount) {
        String recipient = JOptionPane.showInputDialog(this, "Enter recipient username:");

        if (users.containsKey(recipient)) {
            double senderBalance = balances.get(currentUser);
            if (amount <= senderBalance) {
                balances.put(currentUser, senderBalance - amount);
                double recipientBalance = balances.get(recipient);
                balances.put(recipient, recipientBalance + amount);
                transactionHistory.get(currentUser).add("Transferred: $" + amount + " to " + recipient);
                JOptionPane.showMessageDialog(this, "Successfully transferred: $" + amount + " to " + recipient);
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Recipient not found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ATMInterface atm = new ATMInterface();
            atm.setVisible(true);
        });
    }
}
