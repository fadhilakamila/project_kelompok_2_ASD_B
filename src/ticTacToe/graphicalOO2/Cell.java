package ticTacToe.graphicalOO2;

import java.awt.*;
/**
 * The Cell class models each individual cell of the game board.
 */
public class Cell {
    // Define named constants for drawing
    public static final int CELL_SIZE = 200; // cell width/height (square)
    public static final int CELL_PADDING = CELL_SIZE / 5;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 10;
    // Symbols (cross/nought) are displayed inside a cell, with padding from border
    // Define properties (package-visible)
    /** Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT) */
    Seed content;
    /** Row and column of this cell */
    int row, col;

    /** Constructor to initialize this cell with the specified row and col */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        content = Seed.NO_SEED;
    }

    /** Reset this cell's content to EMPTY, ready for new game */
    public void newGame() {
        content = Seed.NO_SEED;
    }

    /** Paint itself on the graphics canvas, given the Graphics context */
    public void paint(Graphics g) {
        // Use Graphics2D which allows us to set the pen's stroke
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        // Draw the Seed if it is not empty
        int x1 = col * CELL_SIZE + CELL_PADDING;
        int y1 = row * CELL_SIZE + CELL_PADDING;
        if (content == Seed.CROSS) {
            g2d.setColor(GameMain.COLOR_CROSS);  // draw a 2-line cross
            int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
            int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y1, x1, y2);
        } else if (content == Seed.NOUGHT) {  // draw a circle
            g2d.setColor(GameMain.COLOR_NOUGHT);
            g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
        }
    }
}