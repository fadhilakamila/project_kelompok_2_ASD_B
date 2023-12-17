package ticTacToe.consoleNonOO;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TTTConsoleNonOO {
    // Define named constants for:
    //  1. Player: using CROSS and NOUGHT
    //  2. Cell contents: using CROSS, NOUGHT, and NO_SEED
    public static final int CROSS = 0; // Konstanta yang merepresentasikan pemain 'X' pada papan.
    public static final int NOUGHT = 1; // Konstanta yang merepresentasikan pemain 'O' pada papan.
    public static final int NO_SEED = 2; // Konstanta yang merepresentasikan sel kosong pada papan.

    // The game board
    public static final int ROWS = 3, COLS = 3; //Jumlah baris dan kolom pada papan permainan.
    public static int[][] board = new int[ROWS][COLS]; // Matriks yang merepresentasikan papan permainan.
    // The current player
    public static int currentPlayer; // Menyimpan pemain yang saat ini sedang melakukan giliran: CROSS atau NOUGHT
    // Konstanta yang merepresentasikan status permainan sedang berlangsung.
    public static final int PLAYING = 0;
    public static final int DRAW = 1;
    public static final int CROSS_WON = 2;
    public static final int NOUGHT_WON = 3;
    public static int currentState; // Menyimpan status permainan saat ini.

    public static LinkedList<Integer[]> moveHistory = new LinkedList<>();
    public static Queue<Integer> moveQueue = new LinkedList<>();
    public static Scanner in = new Scanner(System.in);

    /** The entry main method (the program starts here) */
    public static void main(String[] args) {
        System.out.print("Enter the name for Player 'X': ");
        String playerX = in.nextLine();

        System.out.print("Enter the name for Player 'O': ");
        String playerO = in.nextLine();

        // Initialize the board, currentState, and currentPlayer
        // Play the game once
        do {
            // Play the game once
            initGame();
            do {
                // currentPlayer makes a move
                // Update board[selectedRow][selectedCol] and currentState
                stepGame(playerX, playerO);
                // Refresh the display
                paintBoard();
                // Print message if game over
                if (currentState == CROSS_WON) {
                    System.out.println(playerX + " won!");
                } else if (currentState == NOUGHT_WON) {
                    System.out.println(playerO + " won!");
                } else if (currentState == DRAW) {
                    System.out.println("It's a Draw!");
                }
                // Switch currentPlayer
                currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
            } while (currentState == PLAYING); // repeat if not game over

            // Prompt the user whether to play again
            System.out.print("Play again (y/n)? ");
            char ans = in.next().charAt(0);
            if (ans != 'y' && ans != 'Y') {
                System.out.println("Bye!");
                System.exit(0);  // terminate the program
            }
        } while (true);  // repeat until the user does not answer yes

    }

    /** Initialize the board[][], currentState, and currentPlayer for a new game*/
    public static void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = NO_SEED; // Setiap sel diatur ke nilai NO_SEED (kosong)
            }
        }
        currentPlayer = CROSS; //pemain x main
        currentState = PLAYING;
    }

    /** The currentPlayer makes one move (one step). Update board[selectedRow][selectedCol] and currentState. */
    public static void stepGame(String playerX, String playerO) {
        boolean validInput = false;  // Untuk validate input

        do {
            String currentPlayerName = (currentPlayer == CROSS) ? playerX : playerO;
            System.out.print(currentPlayerName + ", enter your move (row[1-3] column[1-3]): ");

            int row = in.nextInt() - 1;
            int col = in.nextInt() - 1;

            if (row >= 0 && row < ROWS && col >= 0 && col < COLS && board[row][col] == NO_SEED) {
                Integer[] move = {row, col};
                moveHistory.add(move);
                moveQueue.offer(moveHistory.size());

                currentState = stepGameUpdate(currentPlayer, row, col);
                validInput = true;
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput); // repeat jika input invalid
    }

    /**
     * Helper function of stepGame().
     * The given player makes a move at (selectedRow, selectedCol).
     * Update board[selectedRow][selectedCol]. Compute and return the
     * new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     * @return new game state
     */
    public static int stepGameUpdate(int player, int selectedRow, int selectedCol) {
        // Update game board
        board[selectedRow][selectedCol] = player;

        // Compute and return the new game state
        if (board[selectedRow][0] == player && board[selectedRow][1] == player && board[selectedRow][2] == player // simbol sama di baris 1
                || board[0][selectedCol] == player && board[1][selectedCol] == player && board[2][selectedCol] == player // simbol sama di kolom 1
                || selectedRow == selectedCol && board[0][0] == player && board[1][1] == player && board[2][2] == player // simbol sama di diagonal +
                || selectedRow + selectedCol == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player) {  // simbol sama di diagonal -
            return (player == CROSS) ? CROSS_WON : NOUGHT_WON;
        } else {
            // Tidak ada yang menang. Periksa DRAW (seluruh sel terisi) atau masih PLAYING.
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (board[row][col] == NO_SEED) {
                        return PLAYING; //Masih ada sel kosong
                    }
                }
            }
            return DRAW;
        }
    }

    /** Print the game board */
    public static void paintBoard() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                paintCell(board[row][col]); // Mencetak setiap sel
                if (col != COLS - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (row != ROWS - 1) {
                System.out.println("-----------");
            }
        }
        System.out.println();
    }

    /** Print a cell having the given content */
    public static void paintCell(int content) {
        switch (content) {
            case CROSS:
                System.out.print(" X ");
                break;
            case NOUGHT:
                System.out.print(" O ");
                break;
            case NO_SEED:
                System.out.print("   ");
                break;
        }
    }
}