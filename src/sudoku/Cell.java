package sudoku;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The Cell class model the cells of the Sudoku puzzle, by customizing (subclass)
 * the javax.swing.JTextField to include row/column, puzzle number and status.
 */
public class Cell extends JTextField {
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    // Define named constants for JTextField's colors and fonts
    // to be chosen based on CellStatus
    public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_NOT_GIVEN = Color.GRAY;
    public static final Color BG_TO_GUESS = new Color(255, 250, 205);
    public static final Color BG_CORRECT_GUESS = new Color(44, 238, 144);
    public static final Color BG_WRONG_GUESS = new Color(255, 102, 102);
    public static final Font FONT_NUMBERS = new Font("Helvetica Neue Thin", Font.PLAIN, 18);

    // Border untuk setiap cell
    private static final Border CELL_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1);

    // Border khusus untuk grid 3x3
    private static final Border BLOCK_BORDER = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK);
    public static final Color COLOR_OFF_WHITE = new Color(247, 242, 237); // Off-White
    public static final Color COLOR_WHITE = Color.white; // White


    // Define properties (package-visible)
    int row, col;  // The row and column number [0-8] of this cell
    int number;    // The puzzle number [1-9] for this cell
    CellStatus status;  // The status of this cell defined in enum CellStatus

    /** Constructor */
    public Cell(int row, int col) {
        super();   // JTextField
        this.row = row;
        this.col = col;

        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
        super.addKeyListener(new CellKeyListener());

        // Atur border cell
        setCellBorder(row, col);

    }

    private void setCellBorder(int row, int col) {
        int top = (row % 3 == 0) ? 2 : 1;
        int left = (col % 3 == 0) ? 2 : 1;
        int bottom = (row % 3 == 2) ? 2 : 1;
        int right = (col % 3 == 2) ? 2 : 1;

        setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
    }

    /** Reset this cell for a new game, given the puzzle number and isGiven */
    public void newGame(int number, boolean isGiven) {
        this.number = number;
        status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;

        // Jika cell adalah 'given', tampilkan angka dan jadikan tidak dapat diedit.
        // Jika tidak, kosongkan teks cell dan buat bisa diedit.
        if (isGiven) {
            super.setText(String.valueOf(number));
            super.setEditable(false);
        } else {
            super.setText(""); // Kosongkan teks untuk cell 'to guess'
            super.setEditable(true);
        }

        paint(); // Perbarui tampilan cell
    }

    /** This Cell (JTextField) paints itself based on its status */
    public void paint() {
        super.setEditable(status != CellStatus.GIVEN);

        if (status == CellStatus.GIVEN) {
            super.setText(String.valueOf(number));
        }

        // Mengatur warna latar belakang berdasarkan posisi cell dalam grid 3x3
        if ((row / 3 + col / 3) % 2 == 0) {
            super.setBackground(COLOR_WHITE);
        } else {
            super.setBackground(COLOR_OFF_WHITE);
        }

        // Mengatur warna teks berdasarkan status
        switch (status) {
            case TO_GUESS:
                super.setEditable(true);
                super.setBackground(BG_TO_GUESS);
                break;
            case CORRECT_GUESS:
                super.setEditable(true);
                super.setBackground(BG_CORRECT_GUESS); // Atau warna lain yang cocok
                break;
            case WRONG_GUESS:
                super.setEditable(true);
                super.setBackground(BG_WRONG_GUESS); // Atau warna lain yang menandakan kesalahan
                break;
        }

        super.repaint(); // Memastikan update visual
    }
    private class CellKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // Mendapatkan karakter yang diketik
            char inputChar = e.getKeyChar();

            // Memastikan bahwa karakter adalah digit antara 1 dan 9
            if (Character.isDigit(inputChar) && inputChar >= '1' && inputChar <= '9') {
                int numberIn = Character.getNumericValue(inputChar);
                processInput(numberIn);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Kosongkan implementasi jika tidak diperlukan
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Kosongkan implementasi jika tidak diperlukan
        }
    }
    /** Mengolah input angka yang dimasukkan oleh pengguna */
    private void processInput(int numberIn) {
        if (status == CellStatus.GIVEN) {
            // Jika sel adalah petunjuk, abaikan input pengguna
            return;
        }

        // Memperbarui nilai sel dan status berdasarkan input pengguna
        if (numberIn == number) {
            status = CellStatus.CORRECT_GUESS;
        } else {
            status = CellStatus.WRONG_GUESS;
        }
        paint(); // Memperbarui tampilan sel
    }
}
