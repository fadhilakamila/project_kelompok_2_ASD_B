package sudoku;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GameBoardPanel extends JPanel {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for UI sizes
    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int BOARD_WIDTH = CELL_SIZE * SudokuConstants.GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /**
     * The game board composes of 9x9 Cells (customized JTextFields)
     */
    private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    /**
     * It also contains a Puzzle with array numbers and isGiven
     */
    private Puzzle puzzle = new Puzzle();

    private int levelOptions = 0;
    private Random random = new Random();
    /**
     * Constructor
     */
    public GameBoardPanel() {
        super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col] = new Cell(row, col);
                super.add(cells[row][col]);   // JPanel
            }
        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the Cells (JTextFields)
        CellInputListener listener = new CellInputListener();


        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);  // Add listener to all editable cells
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame() {
        // Generate a new puzzle
        puzzle.newPuzzle(levelOptions);

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }
    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    return false;
                }
            }
        }
        return true;
    }

    // [TODO 2] Define a Listener Inner Class for all the editable Cells
    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell) e.getSource();

            // Retrieve the int entered
            int numberIn = Integer.parseInt(sourceCell.getText());
            // For debugging
            System.out.println("You entered " + numberIn);

            /*
             * [TODO 5] (later - after TODO 3 and 4)
             * Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */
            if (numberIn == sourceCell.number) {
                sourceCell.status = CellStatus.CORRECT_GUESS;
            } else {
                sourceCell.status = CellStatus.WRONG_GUESS;
            }
            sourceCell.paint();   // re-paint this cell based on its status

            /*
             * [TODO 6] (later): Check if the puzzle is solved
             * Check if the player has solved the puzzle after this move,
             *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
             */
            if (isSolved()) {
                JOptionPane.showMessageDialog(GameBoardPanel.this, "Congratulations! You solved the puzzle!");
            }
        }
    }

    public void resetGame() {
        for(int i = 0; i < SudokuConstants.GRID_SIZE; i++){
            for(int j = 0; j < SudokuConstants.GRID_SIZE; j++){
                if(cells[i][j].status == CellStatus.CORRECT_GUESS || cells[i][j].status == CellStatus.WRONG_GUESS) {
                    cells[i][j].setText("");
                    cells[i][j].status = CellStatus.TO_GUESS;
                    cells[i][j].paint();
                }
            }
        }
    }

    public void setLevel(int levelOptions) {
        this.levelOptions = levelOptions;
    }

    public void solve() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    cells[row][col].setText(cells[row][col].number + "");
                    cells[row][col].status = CellStatus.CORRECT_GUESS;
                    cells[row][col].paint();
                    if (isSolved()) {
                        JOptionPane.showMessageDialog(GameBoardPanel.this, "The puzzle is solved");
                    }
                }
            }
        }
    }
    public void showHints() {
        boolean hintGiven = false;
        for (int i = 0; i < SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE - 1;) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            for (int j = 0; j < SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE - 1; ) {
                int col = random.nextInt(SudokuConstants.GRID_SIZE);
                if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
                    cells[row][col].status = CellStatus.CORRECT_GUESS;
                    cells[row][col].setText(cells[row][col].number + "");
                    cells[row][col].paint();
                    hintGiven = true;
                    break;
                }
                j++;
            }
            if (hintGiven) break;
            i++;
        }

//        int row = random.nextInt(SudokuConstants.GRID_SIZE-1);
//        int col = random.nextInt(SudokuConstants.GRID_SIZE-1);
//
//        if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
//            cells[row][col].status = CellStatus.CORRECT_GUESS;
//            cells[row][col].setText(cells[row][col].number + "");
//        } else {
//            showHints();
//        }
    }
}
