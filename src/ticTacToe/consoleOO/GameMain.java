package ticTacToe.consoleOO;

import java.util.Scanner;
import static ticTacToe.consoleOO.State.*;

/**
 * The main class for the Tic-Tac-Toe (Console-OO, non-graphics version)
 * It acts as the overall controller of the game.
 */
public class GameMain {
    // Define properties
    /** The game board */
    private ticTacToe.consoleOO.Board board;
    /** The current state of the game (of enum State) */
    private ticTacToe.consoleOO.State currentState;
    /** The current player (of enum Seed) */
    private ticTacToe.consoleOO.Seed currentPlayer;
    private int crossScore;
    private int noughtScore;
    private int roundsPlayed;
    private int matchesPlayed;
    private final int MATCH_WIN_THRESHOLD = 2;
    private final int ROUNDS_PER_MATCH = 3;


    private static Scanner in = new Scanner(System.in);

    /** Constructor to setup the game */
    public GameMain() {
        initGame();
        do {
            matchesPlayed++;
            playMatch();
            displayMatchResult();
            if (!askForRematch()) {
                break;
            }
        } while (true);
        System.out.println("Bye!");
    }

    public void initGame() {
        board = new Board();
    }

    public void playMatch() {
        crossScore = 0;
        noughtScore = 0;
        roundsPlayed = 0;
        do {
            roundsPlayed++;
            newGame();
            playRound();
            displayRoundResult();
        } while (crossScore < MATCH_WIN_THRESHOLD && noughtScore < MATCH_WIN_THRESHOLD);
    }

    public void playRound() {
        do {
            stepGame();
            board.paint();
            if (currentState == CROSS_WON) {
                System.out.println("'X' won this round!");
                crossScore++;
            } else if (currentState == NOUGHT_WON) {
                System.out.println("'O' won this round!");
                noughtScore++;
            } else if (currentState == DRAW) {
                System.out.println("It's a Draw!");
            }
        } while (currentState == PLAYING && (crossScore < MATCH_WIN_THRESHOLD && noughtScore < MATCH_WIN_THRESHOLD));
    }

    public void displayRoundResult() {
        System.out.println("Round " + roundsPlayed + " Scores:");
        System.out.println("'X' Score: " + crossScore);
        System.out.println("'O' Score: " + noughtScore);
    }

    public void displayMatchResult() {
        System.out.println("Match " + matchesPlayed + " Result:");
        System.out.println("'X' Total Score: " + crossScore);
        System.out.println("'O' Total Score: " + noughtScore);
        if (crossScore > noughtScore) {
            System.out.println("'X' wins the match!");
        } else if (crossScore < noughtScore) {
            System.out.println("'O' wins the match!");
        } else {
            System.out.println("It's a draw in the match!");
        }
    }

    public boolean askForRematch() {
        System.out.print("Play another match (y/n)? ");
        char ans = in.next().charAt(0);
        return ans == 'y' || ans == 'Y';
    }

    public void newGame() {
        board.newGame();  // Reset the board
        currentPlayer = Seed.CROSS;
        currentState = State.PLAYING;
    }

    public void stepGame() {
        boolean validInput = false;
        do {
            String icon = currentPlayer.getIcon();
            System.out.print("Player '" + icon + "', enter your move (row[1-3] column[1-3]): ");
            int row = in.nextInt() - 1;
            int col = in.nextInt() - 1;
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                    && board.cells[row][col].content == Seed.NO_SEED) {
                currentState = board.stepGame(currentPlayer, row, col);
                validInput = true;
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);

        // Switch players after each move
        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    public static void main(String[] args) {
        new GameMain();
    }
}