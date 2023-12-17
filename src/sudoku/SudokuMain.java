package sudoku;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import static java.awt.Color.*;
import static javax.swing.JOptionPane.*;


/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
    private static final long serialVersionUID = 1L;  // to prevent serial warning
    // private variables
    GameBoardPanel board = new GameBoardPanel();
    String[] levelOptions = {"Easy","Medium","Hard"};
    int hintCount = 0;

    /** The entry main() entry method */
    public static void main(String[] args) {
        // [TODO 1] Check "Swing program template" on how to run the constructor of "SudokuMain"
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomeScreen(); // Membuat dan menampilkan welcome screen
            }
        });
    }

    // Constructor
    public SudokuMain() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JButton btnNewGame = new JButton("New Game");
        JButton btnResetGame = new JButton("Reset Game");
        JButton btnHints = new JButton("Hints");
        btnHints.setBackground(yellow);
        JButton btnSolve = new JButton("Solve Itself");
        JPanel panel = new JPanel(new GridLayout());
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu optionsMenu = new JMenu("Options");
        JMenu helpMenu = new JMenu("Help");
        JComboBox cbLevelOptions = new JComboBox(levelOptions);
        SoundPlayer player = new SoundPlayer();
        player.playBackgroundMusic("src/sudoku/music.wav");

        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.newGame();  // Call newGame() on the board to restart the game
                hintCount = 0;
            }
        });
        btnResetGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.resetGame();
                hintCount = 0;
            }
        });
        btnSolve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Panggil metode untuk menampilkan seluruh sel
                board.solve();
            }
        });

        btnHints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Panggil metode untuk menampilkan hint 1 cell
                if (!board.isSolved() && hintCount<3) {
                    board.showHints();
                    hintCount++;
                } else if (hintCount>=3) {
                    JOptionPane.showMessageDialog(SudokuMain.this, "You reached the maximum hint");
                }
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

        cbLevelOptions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox cblevelOptions = (JComboBox) e.getSource();
                int LevelOptions = cblevelOptions.getSelectedIndex();
                board.setLevel(LevelOptions);
                board.newGame();
                System.out.println(LevelOptions);
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
        panel.add(btnHints);
        panel.add(btnSolve);
        panel.add(cbLevelOptions);
        cp.add(panel, BorderLayout.SOUTH);
        cp.add(board, BorderLayout.CENTER);

        // Initialize the game board to start the game
        board.newGame();

        pack();     // Pack the UI components, instead of using setSize()
        setLocationRelativeTo(null); // Menempatkan JFrame di tengah layar
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle("Sudoku");
        setVisible(true);
    }
}