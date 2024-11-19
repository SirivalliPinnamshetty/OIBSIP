import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OnlineExamInterface {

    static class OnlineExam {
        private final String username = "user";
        private final String password = "password";
        private final String[] questions = {
            "What is Java?", 
            "Which of the following statements about Java is true?", 
            "What does JVM stand for?"
        };
        private final String[][] options = {
            {"Programming Language", "OS", "Software", "None"},
            {"Java is a platform-dependent language.", "Java is a platform-independent language.",
             "Java can only be used for web applications.", "Java is not an object-oriented programming language."},
            {"Java Variable Memory", " Java Virtual Machine", "Java Verified Method", "Java Variable Method"}
        };
        private final int[] correctAnswers = {0, 1, 1}; 

        public boolean validateLogin(String user, String pass) {
            return username.equals(user) && password.equals(pass);
        }

        public String[] getQuestions() {
            return questions;
        }

        public String[][] getOptions() {
            return options;
        }

        public int[] getCorrectAnswers() {
            return correctAnswers;
        }
    }

    public static void main(String[] args) {
        OnlineExam exam = new OnlineExam();

        // Frame setup
        JFrame frame = new JFrame("Online Exam");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(150, 25)); 
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(150, 25)); 
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30)); 

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(usernameField, gbc);

        
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(passwordField, gbc);

        
        gbc.gridx = 1;
        gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);

        
        frame.getContentPane().add(loginPanel, BorderLayout.CENTER);

        
        JPanel examPanel = new JPanel();
        examPanel.setLayout(new BoxLayout(examPanel, BoxLayout.Y_AXIS));
        examPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel questionLabel = new JLabel("Question will appear here.");
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JRadioButton option1 = new JRadioButton();
        JRadioButton option2 = new JRadioButton();
        JRadioButton option3 = new JRadioButton();
        JRadioButton option4 = new JRadioButton();

        ButtonGroup optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);

        
        JButton nextButton = new JButton("Next");
        nextButton.setPreferredSize(new Dimension(100, 30));

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(100, 30)); 

        JLabel timerLabel = new JLabel("Time Left: 00:00", SwingConstants.CENTER);

        examPanel.add(Box.createVerticalStrut(20)); 
        examPanel.add(questionLabel);
        examPanel.add(Box.createVerticalStrut(10)); 
        examPanel.add(option1);
        examPanel.add(option2);
        examPanel.add(option3);
        examPanel.add(option4);
        examPanel.add(Box.createVerticalStrut(10)); 
        examPanel.add(timerLabel);
        examPanel.add(Box.createVerticalStrut(10)); 

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        examPanel.add(buttonPanel);

        cardPanel.add(loginPanel, "Login");
        cardPanel.add(examPanel, "Exam");
        frame.add(cardPanel);

        
        loginButton.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            if (exam.validateLogin(user, pass)) {
                cardLayout.show(cardPanel, "Exam");
                startExam(exam, questionLabel, option1, option2, option3, option4, optionsGroup, timerLabel, nextButton, submitButton);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!");
            }
        });

        frame.setVisible(true);
    }

    private static void startExam(OnlineExam exam, JLabel questionLabel, JRadioButton option1, JRadioButton option2, JRadioButton option3, JRadioButton option4, ButtonGroup optionsGroup, JLabel timerLabel, JButton nextButton, JButton submitButton) {
        String[] questions = exam.getQuestions();
        String[][] options = exam.getOptions();
        int[] correctAnswers = exam.getCorrectAnswers();

        
        int[] currentQuestionIndex = {0}; 
        int[] score = {0}; 
        final int timeLimit = 60; 
        int[] timeRemaining = {timeLimit};

        
        updateQuestion(currentQuestionIndex[0], questions, options, questionLabel, option1, option2, option3, option4);

        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining[0] > 0) {
                    timeRemaining[0]--;
                    int minutes = timeRemaining[0] / 60;
                    int seconds = timeRemaining[0] % 60;
                    timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
                } else {
                    ((Timer) e.getSource()).stop();
                    JOptionPane.showMessageDialog(null, "Time's up! Exam submitted.");
                    submitExam(score[0], questions.length);
                }
            }
        });
        timer.start();

    
        nextButton.addActionListener(e -> {
            if (optionsGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(null, "Please select an option before proceeding.");
                return;
            }

            if (option1.isSelected() && correctAnswers[currentQuestionIndex[0]] == 0 ||
                option2.isSelected() && correctAnswers[currentQuestionIndex[0]] == 1 ||
                option3.isSelected() && correctAnswers[currentQuestionIndex[0]] == 2 ||
                option4.isSelected() && correctAnswers[currentQuestionIndex[0]] == 3) {
                score[0]++;
            }

            currentQuestionIndex[0]++;
            if (currentQuestionIndex[0] < questions.length) {
                updateQuestion(currentQuestionIndex[0], questions, options, questionLabel, option1, option2, option3, option4);
                optionsGroup.clearSelection(); 
            } else {
                timer.stop();
                JOptionPane.showMessageDialog(null, "Exam completed!");
                submitExam(score[0], questions.length);
            }
        });

        
        submitButton.addActionListener(e -> {
            timer.stop();
            submitExam(score[0], questions.length);
        });
    }

    private static void updateQuestion(int index, String[] questions, String[][] options, JLabel questionLabel, JRadioButton option1, JRadioButton option2, JRadioButton option3, JRadioButton option4) {
        questionLabel.setText(questions[index]);
        option1.setText(options[index][0]);
        option2.setText(options[index][1]);
        option3.setText(options[index][2]);
        option4.setText(options[index][3]);
    }

    private static void submitExam(int score, int totalQuestions) {
        JOptionPane.showMessageDialog(null, "You scored: " + score + " out of " + totalQuestions);
    }
}
