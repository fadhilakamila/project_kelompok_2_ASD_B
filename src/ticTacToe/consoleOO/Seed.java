package ticTacToe.consoleOO;

/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, or NO_SEED.
 *
 * We also attach a display icon (text or image) for each of the item,
 *   and define the related variable/constructor/getter.
 *
 * Ideally, we should define two enums with inheritance, which is,
 *  however, not supported.
 */
public enum Seed {   // to save as "Seed.java"
    CROSS("X"), NOUGHT("O"), NO_SEED(" ");

//    private Seed currentPlayer;  // declare variable currentPlayer as an instance of enum Seed
//    currentPlayer = Seed.CROSS;  // assign a value (an enum item) to the variable currentPlayer
//
//    private Seed content;        // cell's content
//    content = Seed.NO_SEED;

    // Private variable
    private String icon;
    // Constructor (must be private)
    private Seed(String icon) {
        this.icon = icon;
    }
    // Public Getter
    public String getIcon() {
        return icon;
    }
}
