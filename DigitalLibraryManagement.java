import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class DigitalLibraryManagement {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private HashMap<String, Integer> books = new HashMap<>();

    public DigitalLibraryManagement() {
        initializeBooks(); 
        createGUI();
    }

    private void initializeBooks() {
        books.put("The Alchemist", 5);
        books.put("Rich Dad Poor Dad", 3);
        books.put("Clean Code", 4);
        books.put("Java Programming", 6);
    }

    private void createGUI() {
        frame = new JFrame("Digital Library Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        
        JPanel homePanel = new JPanel(new BorderLayout());
        JLabel homeLabel = new JLabel("Digital Library Management", JLabel.CENTER);
        homeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        homePanel.add(homeLabel, BorderLayout.NORTH);

        JButton adminButton = new JButton("Admin Login");
        JButton userButton = new JButton("User Login");

        adminButton.addActionListener(e -> cardLayout.show(mainPanel, "Admin"));
        userButton.addActionListener(e -> cardLayout.show(mainPanel, "User"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(adminButton);
        buttonPanel.add(userButton);

        homePanel.add(buttonPanel, BorderLayout.CENTER);

    
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new BorderLayout());
        JLabel adminLabel = new JLabel("Admin Panel", JLabel.CENTER);
        adminLabel.setFont(new Font("Arial", Font.BOLD, 16));
        adminPanel.add(adminLabel, BorderLayout.NORTH);

        JTextArea adminOutput = new JTextArea(10, 30);
        adminOutput.setEditable(false);

        JPanel adminButtons = new JPanel();
        JButton addBookButton = new JButton("Add Book");
        JButton deleteBookButton = new JButton("Delete Book");
        JButton viewBooksButton = new JButton("View Books");
        JButton backToHomeAdmin = new JButton("Back to Home");

        adminButtons.add(addBookButton);
        adminButtons.add(deleteBookButton);
        adminButtons.add(viewBooksButton);
        adminButtons.add(backToHomeAdmin);

        adminPanel.add(adminButtons, BorderLayout.CENTER);
        adminPanel.add(new JScrollPane(adminOutput), BorderLayout.SOUTH);

        backToHomeAdmin.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        addBookButton.addActionListener(e -> {
            String bookName = JOptionPane.showInputDialog("Enter Book Name:");
            if (bookName != null && !bookName.trim().isEmpty()) {
                books.put(bookName, books.getOrDefault(bookName, 0) + 1);
                adminOutput.setText("Book added successfully: " + bookName);
            }
        });

        deleteBookButton.addActionListener(e -> {
            String bookName = JOptionPane.showInputDialog("Enter Book Name to Delete:");
            if (bookName != null && books.containsKey(bookName)) {
                books.remove(bookName);
                adminOutput.setText("Book deleted successfully: " + bookName);
            } else {
                adminOutput.setText("Book not found!");
            }
        });

        viewBooksButton.addActionListener(e -> {
            StringBuilder bookList = new StringBuilder("Books in Library:\n");
            for (String book : books.keySet()) {
                bookList.append(book).append(" - Copies: ").append(books.get(book)).append("\n");
            }
            adminOutput.setText(bookList.toString());
        });

        
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        JLabel userLabel = new JLabel("User Panel", JLabel.CENTER);
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userPanel.add(userLabel, BorderLayout.NORTH);

        JTextArea userOutput = new JTextArea(10, 30);
        userOutput.setEditable(false);

        JPanel userButtons = new JPanel();
        JButton searchBookButton = new JButton("Search Book");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton backToHomeUser = new JButton("Back to Home");

        userButtons.add(searchBookButton);
        userButtons.add(borrowBookButton);
        userButtons.add(backToHomeUser);

        userPanel.add(userButtons, BorderLayout.CENTER);
        userPanel.add(new JScrollPane(userOutput), BorderLayout.SOUTH);

        backToHomeUser.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        searchBookButton.addActionListener(e -> {
            String bookName = JOptionPane.showInputDialog("Enter Book Name to Search:");
            if (bookName != null && books.containsKey(bookName)) {
                userOutput.setText("Book found: " + bookName + " - Copies: " + books.get(bookName));
            } else {
                userOutput.setText("Book not found!");
            }
        });

        borrowBookButton.addActionListener(e -> {
            String bookName = JOptionPane.showInputDialog("Enter Book Name to Borrow:");
            if (bookName != null && books.containsKey(bookName)) {
                if (books.get(bookName) > 0) {
                    books.put(bookName, books.get(bookName) - 1);
                    userOutput.setText("You borrowed: " + bookName);
                } else {
                    userOutput.setText("No copies left!");
                }
            } else {
                userOutput.setText("Book not found!");
            }
        });

        
        mainPanel.add(homePanel, "Home");
        mainPanel.add(adminPanel, "Admin");
        mainPanel.add(userPanel, "User");

        frame.add(mainPanel);
        cardLayout.show(mainPanel, "Home");

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DigitalLibraryManagement::new);
    }
}