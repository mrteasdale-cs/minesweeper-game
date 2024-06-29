/**
 * Assign
 * This class handles the creation of all moves in the game
 * @author Myran Teasdale
 * @version v1.3 29-5-24 (latest GUI Edition update)
 */
public class Assign {
    private int col, row;//The row and column being assigned
    private Minesweeper game;//The game 
    Slot[][] moves;//2D Array to store the game's moves
    /**
     * Constructor for Assign class.
     * This gets the total number of moves and calls methods to determine the row that will be filled, and to set the state of the slot being assigned.
     * @param game - the game
     * @param col - the column the user has selected
     * @param guess - a String value that allows the guess to be placed on the board
     */
    public Assign(Minesweeper game, int row,int col, String guess){
        this.game = game;
        this.col = col;
        this.row = row;
        this.moves = new Slot[game.getGameSize()][game.getGameSize()]; // Initialize array
        // Initialize each element of the moves array with a new Slot instance
        for (int i = 0; i < game.getGameSize(); i++) {
            for (int j = 0; j < game.getGameSize(); j++) {
                moves[i][j] = new Slot(i, j, "?"); // Initialize with a default state of "?"
            }
        }
        assignMove(guess);
    }

    /**
     * assignMove
     * This method assigns the move to the game
     * @param guess to set the item onto the board
     */
    public void assignMove(String guess) {
        if (!(row < 0 || col < 0 || row >= game.getGameSize() || col >= game.getGameSize())) {
            moves[row][col].setState(guess);
            game.setCellState(row, col, guess); // set current game cell state to update main board
        }
    }

    /**
     * getRow
     * This method returns the current row value for this move. It allows another element of the program to access this move's current row.
     * @return the row value as an integer
     */
    public int getRow() {
        return row;
    }
    /**
     * getCol
     * This method returns the current col value for this move. It allows another element of the program to access this move's current row.
     * @return the column value as an integer
     */
    public int getCol() {
        return col;
    }

    /**
     * getMove
     * @return Returns the current state of a cell as a String
     */
    public String getMove(int row, int col) {
        if (row < 0 || row >= game.getGameSize() || col < 0 || col >= game.getGameSize()) {
            return "Invalid move.";
        }
        else {
            return game.getCellState(row, col);
        }
    }

    /**
     * This method gets the moves from the board asa 2d array
     * @return a 2d array of Slot type
     */
    public Slot[][] getMoves() {
        return moves;
    }
}//End of class Assign
