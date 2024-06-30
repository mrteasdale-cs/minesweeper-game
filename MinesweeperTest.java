import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MinesweeperTest.
 * This class contains unit tests for the Minesweeper class.
 * It verifies the functionality of the Minesweeper class methods.
 * 
 * @version v1.3 Minesweeper Game (GUI Edition)
 */
public class MinesweeperTest {
    private Minesweeper mineswee1;

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp() {
        mineswee1 = new Minesweeper(0); // Initialize with easy difficulty
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Verifies that a Minesweeper object is created successfully.
     */
    @Test
    public void testDefaultMinesweeperConstructorT1() {
        assertNotNull(mineswee1);
    }

    /**
     * Tests the makeMove method for a valid mine move.
     */
    @Test
    public void checkMakeMoveValidT2() {
        assertEquals("You have successfully found a mine", mineswee1.makeMove("0", "0", "M"));
    }

    /**
     * Tests the makeMove method for an unsuccessful/invalid mine move.
     */
    @Test
    public void checkMakeMoveValidT3() {
        assertEquals("Unsuccessful - this was not a mine", mineswee1.makeMove("4", "4", "M"));
    }

    /**
     * Tests the makeMove method for a valid guess move that results in losing a life.
     * Verifies that the life count is correctly updated.
     */
    @Test
    public void checkMakeMoveValidT4() {
        assertEquals("You have lost one life. \nNew life total: 2", mineswee1.makeMove("1", "4", "G"));
    }

    /**
     * Tests the makeMove method for a valid guess move that does not result in losing a life.
     * Verifies that the move is correctly identified as safe.
     */
    @Test
    public void checkMakeMoveValidT5() {
        assertEquals("You have survived this time", mineswee1.makeMove("2", "2", "G"));
    }

    /**
     * Tests the readLevelFile method.
     * Verifies that the game board is correctly read from the level file.
     */
    @Test
    public void testReadLevelFileT6() {
        String[][] gameBoard = mineswee1.readLevelFile();
        assertNotNull(gameBoard);
    } // end method

    /**
     * Tests the getCellState method for an initial cell state.
     * Verifies that the initial state of a cell is correctly returned.
     */
    @Test
    public void testGetInitialCellStateT7() {
        int row = 2;
        int col = 3;
        String[][] gameBoard = mineswee1.readLevelFile();
        String result = mineswee1.getCellState(row, col);
        assertEquals("?", result);
    } // end method

    /**
     * Checks the getCellState method and that the state of a cell is correctly updated after a move.
     */
    @Test
    public void testGetCellStateAfterMoveT8() {
        int row = 4;
        int col = 0;
        String[][] gameBoard = mineswee1.readLevelFile();
        mineswee1.makeMove(Integer.toString(row), Integer.toString(col), "G");
        String result = mineswee1.getCellState(row, col);
        assertEquals("M", result); // check for a mine
    } // end method

    /**
     * Method to test the setLives method.
     * Verifies that the lives count is correctly updated.
     */
    @Test
    public void testSetLives() {
        mineswee1.setLives(2);
        assertEquals(2, mineswee1.getLives());

        mineswee1.setLives(0);
        assertEquals(0, mineswee1.getLives());
    } // end method

    /**
     * Method to test the getIndividualMove method. Checks the state of an individual cell is correctly returned.
     */
    @Test
    public void testGetIndividualMove() {
        int row = 1;
        int col = 1;
        mineswee1.setCellState(row, col, "G");
        String result = mineswee1.getIndividualMove(row, col);
        assertEquals("G", result);
    } // end method

    /**
     * Tests the checkWin method for a winning condition. Checks game correctly identifies a win.
     */
    @Test
    public void testCheckWin() {
        int gameSize = mineswee1.getGameSize();
        String[][] gameBoard = mineswee1.readLevelFile();

        // Simulate a winning condition by revealing all non-mine cells
        for (int i = 0; i < gameSize; i++) {
            for (int j = 0; j < gameSize; j++) {
                if (!gameBoard[i][j].equals("M")) {
                    // Reveal non-mine cells
                    mineswee1.makeMove(String.valueOf(i), String.valueOf(j), "G");
                } else {
                    // Flag mine cells
                    mineswee1.makeMove(String.valueOf(i), String.valueOf(j), "M");
                }
            }
        }
        // Check if the game is won
        String result = mineswee1.checkWin();
        assertEquals("won", result, "The game should be won when all non-mine cells are revealed and all mines are flagged");
    } // end method

    /**
     * Tests the checkWin method for a losing condition. CLoops through and delibrately selects Mines to lose the game.
     */
    @Test
    public void testCheckLose() {
        int gameSize = mineswee1.getGameSize();
        String[][] gameBoard = mineswee1.readLevelFile();
        String result = mineswee1.checkWin();
        // Simulate a winning condition by revealing all non-mine cells
        for (int row = 0; row < gameSize; row++) {
            for (int col = 0; col < gameSize; col++) {
                if (gameBoard[row][col].equals("M") && result.equals("continue")) {
                    // Make incorrect guesses to reduce lives to 0
                    mineswee1.makeMove(String.valueOf(row), String.valueOf(col), "G");
                    // Check if the game is won
                    result = mineswee1.checkWin();
                }
            }
        }
        assertEquals("lives", result, "Game lost when lives gets to 0");
    } // end method

    /**
     * Method to test the getGameSize method. Confirms that the game size is correctly returned.
     */
    @Test
    public void testGetGameSize() {
        int gameSize = mineswee1.getGameSize();
        assertEquals(5, gameSize); // Assuming the game size is 5 for this test
    }

} //end class
