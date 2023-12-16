package sudoku;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning
    // private variables
    GameBoardPanel board = new GameBoardPanel();

    /** The entry main() entry method */
    public static void main(String[] args) {
        // [TODO 1] Check "Swing program template" on how to run
        //  the constructor of "SudokuMain"
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SudokuMain(); // Create an instance of SudokuMain
            }
        });
    }

    // Constructor
    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JButton btnNewGame = new JButton("New Game");
        JButton btnResetGame = new JButton("Reset Game");
        JPanel panel = new JPanel(new GridLayout());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu optionsMenu = new JMenu("Options");
        JMenu helpMenu = new JMenu("Help");

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame();  // Call newGame() on the board to restart the game
            }
        });
        btnResetGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.resetGame();
            }
        });
        JMenuItem newGameItem = new JMenuItem("New Game");
        newGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame();
            }
        });

        JMenuItem resetGameItem = new JMenuItem("Reset Game");
        resetGameItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.resetGame();
            }
        });

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Menambahkan item ke menu "File"
        fileMenu.add(newGameItem);
        fileMenu.add(resetGameItem);
        fileMenu.addSeparator(); // Penyekat
        fileMenu.add(exitItem);

        // Menambahkan item ke menu bar
        menuBar.add(fileMenu);
        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);

        // Menetapkan menu bar ke frame
        setJMenuBar(menuBar);

        panel.add(btnNewGame);
        panel.add(btnResetGame);
        cp.add(panel, BorderLayout.SOUTH);
        cp.add(board, BorderLayout.CENTER);

        // Initialize the game board to start the game
        board.newGame();

        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }

}