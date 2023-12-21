package ticTacToe.graphicalOO2;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel playerXLabel;
    private JLabel playerOLabel;

    private int playerXScore;
    private int playerOScore;

    private static final int WINNING_THRESHOLD = 3; // Jumlah kemenangan yang diperlukan

    public ScorePanel() {
        setLayout(new FlowLayout());
        playerXLabel = new JLabel("Player X: 0  | ");
        playerOLabel = new JLabel("Player O: 0");
        playerOLabel.setFont(GameMain.FONT_STATUS);
        playerXLabel.setFont(GameMain.FONT_STATUS);

        add(playerXLabel);
        add(playerOLabel);

        resetScores();
    }

    public void increasePlayerXScore() {
        playerXScore++;
        updateScoreLabels();
        checkForWinner();
    }

    public void increasePlayerOScore() {
        playerOScore++;
        updateScoreLabels();
        checkForWinner();
    }

    private void updateScoreLabels() {
        playerXLabel.setText("Player X: " + playerXScore + "  | ");
        playerOLabel.setText("Player O: " + playerOScore);
    }

    private void checkForWinner() {
        if (playerXScore >= WINNING_THRESHOLD) {
            JOptionPane.showMessageDialog(null, "Player X is the overall winner!");
            resetScores();
        } else if (playerOScore >= WINNING_THRESHOLD) {
            JOptionPane.showMessageDialog(null, "Player O is the overall winner!");
            resetScores();
        }
    }

    public void resetScores() {
        playerXScore = 0;
        playerOScore = 0;
        updateScoreLabels();
    }
}