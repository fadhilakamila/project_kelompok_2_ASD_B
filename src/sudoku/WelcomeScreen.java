package sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeScreen extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomeScreen(); // Create and display the welcome screen
            }
        });
    }

    public WelcomeScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(522, 235);
        setLocationRelativeTo(null); // Center the JFrame on screen
        setTitle("Sudoku");
        setLayout(new BorderLayout());

        // Create a panel for content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(0xFAF8F5)); // A soft off-white background
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Sudoku!");
        welcomeLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        welcomeLabel.setForeground(new Color(0x654321)); // A deep brown color
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // Bottom padding

        // Start button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setBackground(new Color(0xFFFFFF)); // White background
        startButton.setForeground(new Color(0x2E2E2E)); // Dark grey text
        startButton.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(0x654321), 2, true), // Brown border
        BorderFactory.createEmptyBorder(5, 15, 5, 15))); // Padding

        // Add action listener to start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Hide the welcome screen when "Start Game" is pressed
                showGameBoard(); // Show the game board
            }
        });

        // Add components to the content panel
        contentPanel.add(welcomeLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between components
        contentPanel.add(startButton);

        // Add the content panel to the frame
        add(contentPanel, BorderLayout.CENTER);

        // Make the window's size fit the preferred size and layouts of its subcomponents
        pack();
        setLocationRelativeTo(null); // Center the JFrame on screen after packing
        setVisible(true);
    }

    private void showGameBoard() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuMain(); // Create and display the game board
            }
        });
    }
}