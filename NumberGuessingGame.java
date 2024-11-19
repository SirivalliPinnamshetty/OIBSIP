import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame {
    private int randomNumber;
    private int attempts;
    private int score;
    private boolean gameActive;

    public NumberGuessingGame() {
        generateRandomNumber();
        createGUI();
    }

    private void generateRandomNumber() {
        Random random = new Random();
        randomNumber = random.nextInt(100) + 1;
        attempts = 0;
        score = 150;
        gameActive = true;
    }

    private void createGUI() {
        JFrame frame = new JFrame("Number Guessing Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); 
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); 
        
        JLabel instructionLabel = new JLabel("Guess the number (1-100):", JLabel.CENTER);
        panel.add(instructionLabel, gbc);

        gbc.gridy = 1;
        JTextField inputField = new JTextField(10); 
        panel.add(inputField, gbc);

        gbc.gridy = 2;
        JButton submitButton = new JButton("Submit Guess");
        submitButton.setPreferredSize(new Dimension(150, 30));
        panel.add(submitButton, gbc);

        gbc.gridy = 3;
        JLabel resultLabel = new JLabel("", JLabel.CENTER);
        panel.add(resultLabel, gbc);

        gbc.gridy = 4;
        JLabel scoreLabel = new JLabel("Score: " + score, JLabel.CENTER);
        panel.add(scoreLabel, gbc);

        gbc.gridy = 5;
        JButton restartButton = new JButton("Restart Game");
        restartButton.setPreferredSize(new Dimension(150, 30)); 
        panel.add(restartButton, gbc);

        frame.add(panel);

        submitButton.addActionListener(e -> {
            if (gameActive) {
                if (attempts < 5) {
                    try {
                        int userGuess = Integer.parseInt(inputField.getText());
                        attempts++;

                        
                        score -= 20;

                        
                        if (userGuess < 1 || userGuess > 100) {
                            resultLabel.setText("Enter a number between 1 and 100.");
                        } else if (userGuess < randomNumber) {
                            resultLabel.setText("Too low! Try again.");
                        } else if (userGuess > randomNumber) {
                            resultLabel.setText("Too high! Try again.");
                        } else {
                            resultLabel.setText("Correct! You guessed it in " + attempts + " attempts.");
                            gameActive = false;
                            submitButton.setEnabled(false);
                        }

                        
                        scoreLabel.setText("Score: " + score);

                        
                        if (attempts == 5 || userGuess == randomNumber) {
                            if (userGuess != randomNumber) {
                                resultLabel.setText("Game Over! The number was " + randomNumber);
                                scoreLabel.setText("Final Score: " + score);
                            }
                        }

                    } catch (NumberFormatException ex) {
                        resultLabel.setText("Please enter a valid number.");
                    }
                }
            }
        });

        restartButton.addActionListener(e -> {
            generateRandomNumber();
            inputField.setText("");
            resultLabel.setText("Game restarted! Guess the number.");
            scoreLabel.setText("Score: " + score);
            submitButton.setEnabled(true); 
            gameActive = true;
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGame::new);
    }
}
