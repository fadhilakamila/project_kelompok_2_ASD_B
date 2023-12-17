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
                new WelcomeScreen(); // Membuat dan menampilkan layar sambutan
            }
        });
    }
    public WelcomeScreen() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // Menempatkan JFrame di tengah layar
        setTitle("Welcome to Sudoku");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel label = new JLabel("Welcome to Sudoku!");
        label.setHorizontalAlignment(JLabel.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Sembunyikan layar sambutan saat tombol "Start Game" ditekan
                showGameBoard(); // Tampilkan game board
            }
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(startButton, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void showGameBoard() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuMain(); // Membuat dan menampilkan game board
            }
        });
    }
}