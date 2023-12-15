package ticTacToe.consoleOO;

/**
 * The Board class models the TTT game-board of 3x3 cells.
 */
public class Board {  // save as "Board.java"
    // Define named constants for the grid
    public static final int ROWS = 3;
    public static final int COLS = 3;

    // Define properties (package-visible)
    /** A board composes of [ROWS]x[COLS] Cell instances */
    Cell[][] cells;

    /** Constructor to initialize the game board */
    public Board() {
        initGame();
    }

    /** Initialize the board (run once) */
    public void initGame() {
        cells = new Cell[ROWS][COLS];  // allocate the array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // Allocate element of the array
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    /** Reset the contents of the game board, ready for new game. */
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                cells[row][col].newGame();  // The cells init itself
            }
        }
    }

    /**
     *  The given player makes a move on (selectedRow, selectedCol).
     *  Update cells[selectedRow][selectedCol]. Compute and return the
     *  new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update game board
        cells[selectedRow][selectedCol].content = player;

        // Compute and return the new game state
        if (cells[selectedRow][0].content == player  // 3-in-the-row
                && cells[selectedRow][1].content == player
                && cells[selectedRow][2].content == player
                || cells[0][selectedCol].content == player // 3-in-the-column
                && cells[1][selectedCol].content == player
                && cells[2][selectedCol].content == player
                || selectedRow == selectedCol         // 3-in-the-diagonal
                && cells[0][0].content == player
                && cells[1][1].content == player
                && cells[2][2].content == player
                || selectedRow + selectedCol == 2     // 3-in-the-opposite-diagonal
                && cells[0][2].content == player
                && cells[1][1].content == player
                && cells[2][0].content == player) {
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else {
            // Nobody win. Check for DRAW (all cells occupied) or PLAYING.
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (cells[row][col].content == Seed.NO_SEED) {
                        return State.PLAYING; // still have empty cells
                    }
                }
            }
            return State.DRAW; // no empty cell, it's a draw
        }
    }

    /** The board paints itself */
    public void paint() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                System.out.print(" ");
                cells[row][col].paint();   // each cell paints itself
                System.out.print(" ");
                if (col < COLS - 1) System.out.print("|");  // column separator
            }
            System.out.println();
            if (row < ROWS - 1) {
                System.out.println("-----------");  // row separator
            }
        }
        System.out.println();
    }

    // Inside the Board class
    public State getCurrentState() {
        if (checkForWin(Seed.CROSS)) {
            return State.CROSS_WON;
        } else if (checkForWin(Seed.NOUGHT)) {
            return State.NOUGHT_WON;
        } else if (isBoardFull()) {
            return State.DRAW;
        } else {
            return State.PLAYING;
        }
    }

    private boolean checkForWin(Seed player) {
        // Check for a win for the specified player
        for (int row = 0; row < ROWS; row++) {
            if (cells[row][0].content == player
                    && cells[row][1].content == player
                    && cells[row][2].content == player) {
                return true;  // 3-in-the-row
            }
        }

        for (int col = 0; col < COLS; col++) {
            if (cells[0][col].content == player
                    && cells[1][col].content == player
                    && cells[2][col].content == player) {
                return true;  // 3-in-the-column
            }
        }

        if (cells[0][0].content == player
                && cells[1][1].content == player
                && cells[2][2].content == player) {
            return true;  // 3-in-the-diagonal
        }

        if (cells[0][2].content == player
                && cells[1][1].content == player
                && cells[2][0].content == player) {
            return true;  // 3-in-the-opposite-diagonal
        }

        return false;  // No win
    }

    private boolean isBoardFull() {
        // Check if the board is full
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (cells[row][col].content == Seed.NO_SEED) {
                    return false;  // Board is not full
                }
            }
        }
        return true;  // Board is full
    }

}