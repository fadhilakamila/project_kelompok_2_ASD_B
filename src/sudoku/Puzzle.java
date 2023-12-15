package sudoku;
import java.util.Collections;
import java.util.Random;
import java.util.Arrays;

public class Puzzle {
    int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
    private Random random = new Random();

    public Puzzle() {
        super();
    }

    public void newPuzzle(int cellsToGuess) {
        resetBoard();
        if (generateSudokuGrid(0, 0)) {
            setClues(cellsToGuess);
        } else {
            // Jika papan tidak bisa dihasilkan, berikan pesan error atau tindakan yang sesuai
            System.out.println("Error: Unable to generate a valid Sudoku puzzle.");
        }
    }

    private boolean generateSudokuGrid(int row, int col) {
        if (row == SudokuConstants.GRID_SIZE) {
            row = 0;
            if (++col == SudokuConstants.GRID_SIZE) {
                return true;
            }
        }

        Integer[] nums = new Integer[SudokuConstants.GRID_SIZE];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i + 1;
        }
        Collections.shuffle(Arrays.asList(nums), random);

        for (int num : nums) {
            if (isValidPlacement(row, col, num)) {
                numbers[row][col] = num;
                if (generateSudokuGrid(row + 1, col)) {
                    return true;
                }
            }
        }

        numbers[row][col] = 0; // reset on backtrack
        return false;
    }

    private boolean isValidPlacement(int row, int col, int num) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
            if (numbers[row][i] == num || numbers[i][col] == num ||
                    numbers[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    private void setClues(int cellsToGuess) {
        for (int i = 0; i < SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE - cellsToGuess; ) {
            int row = random.nextInt(SudokuConstants.GRID_SIZE);
            int col = random.nextInt(SudokuConstants.GRID_SIZE);

            if (!isGiven[row][col]) {
                isGiven[row][col] = true;
                i++;
            }
        }
    }

    private void resetBoard() {
        for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
            for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
                numbers[row][col] = 0; // Reset angka
                isGiven[row][col] = false; // Reset status petunjuk
            }
        }
    }
}