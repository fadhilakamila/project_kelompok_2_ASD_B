package ticTacToe.graphicalOO2;

import javax.swing.*;
import java.awt.*;

class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L; // to prevent serializable warning

    @Override
    public void paintComponent(Graphics g) {  // Callback via repaint()
        super.paintComponent(g);
        setBackground(GameMain.COLOR_BG);  // set its background color

        // Draw the grid lines
        g.setColor(GameMain.COLOR_GRID);
        for (int row = 1; row < Board.ROWS; ++row) {
            g.fillRoundRect(0, Cell.CELL_SIZE * row - Board.GRID_WIDHT_HALF,
                    Board.BOARD_WIDTH-1, Board.GRID_WIDTH, Board.GRID_WIDTH, Board.GRID_WIDTH);
        }
        for (int col = 1; col < Board.COLS; ++col) {
            g.fillRoundRect(Cell.CELL_SIZE * col - Board.GRID_WIDHT_HALF, 0,
                    Board.GRID_WIDTH, Board.BOARD_HEIGHT-1, Board.GRID_WIDTH, Board.GRID_WIDTH);
        }

        // Draw the Seeds of all the cells if they are not empty
        // Use Graphics2D which allows us to set the pen's stroke
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(Cell.SYMBOL_STROKE_WIDTH,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                int x1 = col * Cell.CELL_SIZE + Cell.CELL_PADDING;
                int y1 = row * Cell.CELL_SIZE + Cell.CELL_PADDING;

                // Warna berdasarkan jenis Seed (CROSS atau NOUGHT)
                g2d.setColor((GameMain.board.cells[row][col].content == Seed.CROSS) ? GameMain.COLOR_CROSS : GameMain.COLOR_NOUGHT);

                if (GameMain.board.cells[row][col].content == Seed.CROSS) {  // draw a 2-line cross
                    int x2 = (col + 1) * Cell.CELL_SIZE - Cell.CELL_PADDING;
                    int y2 = (row + 1) * Cell.CELL_SIZE - Cell.CELL_PADDING;
                    g2d.drawLine(x1, y1, x2, y2);
                    g2d.drawLine(x2, y1, x1, y2);
                } else if (GameMain.board.cells[row][col].content == Seed.NOUGHT) {  // draw a circle
                    g2d.drawOval(x1, y1, Cell.SYMBOL_SIZE, Cell.SYMBOL_SIZE);
                }
            }
        }

        // Draw the winning line if there is a winner
        if (Board.winningLineStart != null && Board.winningLineEnd != null) {
            g2d.setColor(Color.GREEN);
            int x1 = Board.winningLineStart[1] * Cell.CELL_SIZE + Cell.CELL_SIZE / 2;
            int y1 = Board.winningLineStart[0] * Cell.CELL_SIZE + Cell.CELL_SIZE / 2;
            int x2 = Board.winningLineEnd[1] * Cell.CELL_SIZE + Cell.CELL_SIZE / 2;
            int y2 = Board.winningLineEnd[0] * Cell.CELL_SIZE + Cell.CELL_SIZE / 2;
            g2d.drawLine(x1, y1, x2, y2);
        }

        // Print status message
        if (GameMain.currentState == State.PLAYING) {
            GameMain.statusBar.setForeground(Color.BLACK);
            GameMain.statusBar.setText((GameMain.currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
        } else if (GameMain.currentState == State.DRAW) {
            GameMain.statusBar.setForeground(GameMain.COLOR_STATUS);
            GameMain.statusBar.setText("It's a Draw! Click to play again");
        } else if (GameMain.currentState == State.CROSS_WON) {
            GameMain.statusBar.setForeground(GameMain.COLOR_STATUS);
            GameMain.statusBar.setText("'X' Won! Click to play again");
        } else if (GameMain.currentState == State.NOUGHT_WON) {
            GameMain.statusBar.setForeground(GameMain.COLOR_STATUS);
            GameMain.statusBar.setText("'O' Won! Click to play again");
        }
    }
}