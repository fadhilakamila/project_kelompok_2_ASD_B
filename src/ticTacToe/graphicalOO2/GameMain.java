package ticTacToe.graphicalOO2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class GameMain extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serializable warning

    // Define named constants for the drawing graphics
    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG = Color.black;  // background
    public static final Color COLOR_BG_STATUS = new Color(255, 255, 255);
    public static final Color COLOR_GRID   = Color.LIGHT_GRAY;  // grid lines
    public static final Color COLOR_CROSS  = new Color(158, 184, 217);
    public static final Color COLOR_NOUGHT = new Color(162, 87, 114);
    public static final Color COLOR_STATUS = new Color(0xBF3131);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    // Define game objects
    public static Board board;         // the game board
    public static State currentState;  // the current state of the game
    public static Seed currentPlayer;  // the current player

    // UI Components
    public static GamePanel gamePanel; // Drawing canvas (JPanel) for the game board
    public static ScorePanel scorePanel;
    public static JLabel statusBar;    // for displaying status message

    /** The entry "main" method */
    public static void main(String[] args) {
        // Run GUI construction codes in Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameMain();
            }
        });
    }
    /** Constructor to setup the UI and game components */
    public GameMain() {
        initGame();

        // Set up content pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Set up GUI components
        gamePanel = new GamePanel();  // Construct a drawing canvas (a JPanel)
        gamePanel.setPreferredSize(new Dimension(Board.BOARD_WIDTH, Board.BOARD_HEIGHT));

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel("       ");
        statusBar.setFont(FONT_STATUS);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
        statusBar.setOpaque(true);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setPreferredSize(new Dimension(300, 30));

        // Initialize scorePanel before using it
        scorePanel = new ScorePanel();

        // Create a panel for the status bar and score panel
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        // Add statusBar to the bottom panel
        bottomPanel.add(statusBar);

        // Add scorePanel to the bottom panel
        bottomPanel.add(scorePanel);

        // Tambahkan gamePanel di tengah
        cp.add(gamePanel, BorderLayout.CENTER);

        // Add the bottom panel to the PAGE_END
        cp.add(bottomPanel, BorderLayout.PAGE_END);
        // The canvas (JPanel) fires a MouseEvent upon mouse-click

        // This JPanel fires MouseEvent
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                int row = mouseY / Cell.CELL_SIZE;
                int col = mouseX / Cell.CELL_SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        // Update cells[][] and return the new game state after the move
                        currentState = board.stepGame(currentPlayer, row, col);
                        // Switch player
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    }
                } else {        // game over
                    newGame();  // restart the game
                }
                // Refresh the drawing canvas
                repaint();  // Callback paintComponent().
            }
        });

        // Set the content-pane of the JFrame to an instance of main JPanel
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(TITLE);
        setVisible(true);

        initGame();
        newGame();
        scorePanel.resetScores();
    }

    /** Initialize the game (run once) */
    public void initGame() {
        board = new Board(scorePanel);  // allocate the game-board
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.CROSS;    // cross plays first
        currentState = State.PLAYING;  // ready to play
        resetWinningLine();
    }
    private void resetWinningLine() {
        Board.winningLineStart = null;
        Board.winningLineEnd = null;
    }
}