package ticTacToe.graphicalOO;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic-Tac-Toe: Two-player Graphics version with Simple-OO in one class
 */


public class TTTGraphics extends JFrame {
    private static final long serialVersionUID = 1L; // to prevent serializable warning

    // Define named constants for the game board
    public static final int ROWS = 3;  // ROWS x COLS cells
    public static final int COLS = 3;

    // Define named constants for the drawing graphics
    public static final int CELL_SIZE = 200; // cell width/height (square)
    public static final int BOARD_WIDTH  = CELL_SIZE * COLS; // the drawing canvas
    public static final int BOARD_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 5; // Grid-line's width
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;
    // Symbols (cross/nought) are displayed inside a cell, with padding from border
    public static final int CELL_PADDING = CELL_SIZE / 5;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // width/height
    public static final int SYMBOL_STROKE_WIDTH = 10; // pen's stroke width
    public static final Color COLOR_BG = Color.black;  // background
    public static final Color COLOR_BG_STATUS = new Color(240, 240, 240);
    public static final Color COLOR_GRID   = Color.LIGHT_GRAY;  // grid lines
    public static final Color COLOR_CROSS  = new Color(158, 184, 217);  // Red #D32D41
    public static final Color COLOR_NOUGHT = new Color(162, 87, 114); // Blue #4CB5F5
    public static final Color COLOR_STATUS = new Color(0xBF3131);
    public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);

    // This enum (inner class) contains the various states of the game
    public enum State {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }
    private State currentState;  // the current game state

    // This enum (inner class) is used for:
    // 1. Player: CROSS, NOUGHT
    // 2. Cell's content: CROSS, NOUGHT and NO_SEED
    public enum Seed {
        CROSS, NOUGHT, NO_SEED
    }
    private Seed currentPlayer; // the current player
    private Seed[][] board;     // Game board of ROWS-by-COLS cells

    // UI Components
    private GamePanel gamePanel; // Drawing canvas (JPanel) for the game board
    private JLabel statusBar;  // Status Bar

    // Garis Pemenang
    private int[] winningLineStart;
    private int[] winningLineEnd;
    private ScorePanel scorePanel;

    /** Constructor to setup the game and the GUI components */
    public TTTGraphics() {
        // Initialize the game objects
        initGame();

        // Set up content pane
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        // Set up GUI components
        gamePanel = new GamePanel();  // Construct a drawing canvas (a JPanel)
        gamePanel.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

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
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                int row = mouseY / CELL_SIZE;
                int col = mouseX / CELL_SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < ROWS && col >= 0
                            && col < COLS && board[row][col] == Seed.NO_SEED) {
                        // Update board[][] and return the new game state after the move
                        currentState = stepGame(currentPlayer, row, col);
                        // Switch player
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                    }
                } else {       // game over
                    newGame(); // restart the game
                }
                // Refresh the drawing canvas
                repaint();  // Callback paintComponent().
            }
        });

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setVisible(true);

        newGame();
        scorePanel.resetScores();
    }

    class ScorePanel extends JPanel {
        private JLabel playerXLabel;
        private JLabel playerOLabel;

        private int playerXScore;
        private int playerOScore;

        private static final int WINNING_THRESHOLD = 3; // Jumlah kemenangan yang diperlukan

        public ScorePanel() {
            setLayout(new FlowLayout());
            playerXLabel = new JLabel("Player X: 0  | ");
            playerOLabel = new JLabel("Player O: 0");
            playerOLabel.setFont(FONT_STATUS);
            playerXLabel.setFont(FONT_STATUS);

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


    /** Initialize the Game (run once) */
    public void initGame() {
        board = new Seed[ROWS][COLS]; // allocate array
    }

    /** Reset the game-board contents and the status, ready for new game */
    public void newGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.CROSS;    // cross plays first
        currentState  = State.PLAYING; // ready to play
        resetWinningLine();
    }

    private void resetWinningLine() {
        winningLineStart = null;
        winningLineEnd = null;
    }

    /**
     *  The given player makes a move on (selectedRow, selectedCol).
     *  Update cells[selectedRow][selectedCol]. Compute and return the
     *  new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
     */
    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        // Update game board
        board[selectedRow][selectedCol] = player;

        // Compute and return the new game state
        if (board[selectedRow][0] == player
                && board[selectedRow][1] == player
                && board[selectedRow][2] == player) {
            // Menyimpan posisi pemenang untuk garis horizontal
            winningLineStart = new int[]{selectedRow, 0};
            winningLineEnd = new int[]{selectedRow, 2};
            // Tambahkan skor jika ada pemenang
            if (player == Seed.CROSS) {
                scorePanel.increasePlayerXScore();
            } else {
                scorePanel.increasePlayerOScore();
            }
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (board[0][selectedCol] == player
                && board[1][selectedCol] == player
                && board[2][selectedCol] == player) {
            // Menyimpan posisi pemenang untuk garis vertikal
            winningLineStart = new int[]{0, selectedCol};
            winningLineEnd = new int[]{2, selectedCol};
            // Tambahkan skor jika ada pemenang
            if (player == Seed.CROSS) {
                scorePanel.increasePlayerXScore();
            } else {
                scorePanel.increasePlayerOScore();
            }
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (selectedRow == selectedCol &&
                board[0][0] == player &&
                board[1][1] == player &&
                board[2][2] == player) {
            // Menyimpan posisi pemenang untuk garis diagonal utama
            winningLineStart = new int[]{0, 0};
            winningLineEnd = new int[]{2, 2};
            // Tambahkan skor jika ada pemenang
            if (player == Seed.CROSS) {
                scorePanel.increasePlayerXScore();
            } else {
                scorePanel.increasePlayerOScore();
            }
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (selectedRow + selectedCol == 2 &&
                board[0][2] == player &&
                board[1][1] == player &&
                board[2][0] == player) {
            // Menyimpan posisi pemenang untuk garis diagonal kedua
            winningLineStart = new int[]{0, 2};
            winningLineEnd = new int[]{2, 0};
            // Tambahkan skor jika ada pemenang
            if (player == Seed.CROSS) {
                scorePanel.increasePlayerXScore();
            } else {
                scorePanel.increasePlayerOScore();
            }
            return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else {
            // Nobody win. Check for DRAW (all cells occupied) or PLAYING.
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (board[row][col] == Seed.NO_SEED) {
                        return State.PLAYING; // still have empty cells
                    }
                }
            }
            return State.DRAW; // no empty cell, it's a draw
        }
    }

    /**
     *  Inner class DrawCanvas (extends JPanel) used for custom graphics drawing.
     */
    class GamePanel extends JPanel {
        private static final long serialVersionUID = 1L; // to prevent serializable warning

        @Override
        public void paintComponent(Graphics g) {  // Callback via repaint()
            super.paintComponent(g);
            setBackground(COLOR_BG);  // set its background color

            // Draw the grid lines
            g.setColor(COLOR_GRID);
            for (int row = 1; row < ROWS; ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDTH_HALF,
                        BOARD_WIDTH-1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDTH_HALF, 0,
                        GRID_WIDTH, BOARD_HEIGHT-1, GRID_WIDTH, GRID_WIDTH);
            }

            // Draw the Seeds of all the cells if they are not empty
            // Use Graphics2D which allows us to set the pen's stroke
            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;

                    // Warna berdasarkan jenis Seed (CROSS atau NOUGHT)
                    g2d.setColor((board[row][col] == Seed.CROSS) ? COLOR_CROSS : COLOR_NOUGHT);

                    if (board[row][col] == Seed.CROSS) {  // draw a 2-line cross
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (board[row][col] == Seed.NOUGHT) {  // draw a circle
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }

            // Draw the winning line if there is a winner
            if (winningLineStart != null && winningLineEnd != null) {
                g2d.setColor(Color.GREEN);
                int x1 = winningLineStart[1] * CELL_SIZE + CELL_SIZE / 2;
                int y1 = winningLineStart[0] * CELL_SIZE + CELL_SIZE / 2;
                int x2 = winningLineEnd[1] * CELL_SIZE + CELL_SIZE / 2;
                int y2 = winningLineEnd[0] * CELL_SIZE + CELL_SIZE / 2;
                g2d.drawLine(x1, y1, x2, y2);
            }

            // Print status message
            if (currentState == State.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                statusBar.setText((currentPlayer == Seed.CROSS) ? "X's Turn" : "O's Turn");
            } else if (currentState == State.DRAW) {
                statusBar.setForeground(COLOR_STATUS);
                statusBar.setText("It's a Draw! Click to play again");
            } else if (currentState == State.CROSS_WON) {
                statusBar.setForeground(COLOR_STATUS);
                statusBar.setText("'X' Won! Click to play again");
            } else if (currentState == State.NOUGHT_WON) {
                statusBar.setForeground(COLOR_STATUS);
                statusBar.setText("'O' Won! Click to play again");
            }
        }
    }

    /** The entry main() method */
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TTTGraphics(); // Let the constructor do the job
            }
        });
    }
}