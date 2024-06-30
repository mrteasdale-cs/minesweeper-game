import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class AssignTest.
 *
 * @author  Myran Teasdale
 * @version v1.1 29/5/24
 */
public class AssignTest
{
    Minesweeper testGame;
    Assign assign;
    int row;
    int col;
    String guess;
    /**
     * Default constructor for test class AssignTest
     */
    public AssignTest()
    {
    }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
       testGame = new Minesweeper(0);
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    /**
     * Test Method that tests the constructor of the Assign class.
     * Verifies that a new Assign object is created successfully.
     */
    @Test
    public void testCheckConstructorT1() {
        row = 0;
        col = 4;
        guess = "G";
        assign = new Assign(testGame, row, col, guess);
        assertNotNull(assign);
    }
    
    /**
     * Test Method that tests the assignMove method for assigning a mine.
     * Verifies that a mine is correctly assigned to the specified position.
     */
    @Test
    public void testAssignMoveT2() {
        Assign assign = new Assign(testGame, 0, 0, "M");
        String result = assign.getMove(0,0);
        assertEquals("M", result);
    }
    
    /**
     * Test Method that tests the assignMove method for assigning a number.
     * Verifies that a number is correctly assigned to the specified position.
     */
    @Test
    public void testAssignMoveT3() {
        Assign assign = new Assign(testGame, 3, 0, "1");
        String result = assign.getMove(3,0);
        assertEquals("1", result);
    }
    
    /**
     * Test Method that tests the assignMove method for an out-of-bounds move.
     * Verifies that an invalid move is correctly identified.
     */
    @Test
    public void testAssignMoveT4() {
        Assign assign = new Assign(testGame, 5, 0, "1");
        assign.assignMove("1");
        String result = assign.getMove(5,0);
        assertEquals("Invalid move.", result);
    }
    
    /**
     * Test Method that tests the getRow method.
     * Verifies that the correct row is returned.
     */
    @Test
    public void testgetRowT5() {
        Assign assign = new Assign(testGame, 3, 0, "1");
        int result = assign.getRow();
        assertEquals(3, result);
    }
    
    /**
     * Test Method that tests the getCol method.
     * Verifies that the correct column is returned.
     */
    @Test
    public void testgetColT6() {
        Assign assign = new Assign(testGame, 3, 1, "1");
        int result = assign.getCol();
        assertEquals(1, result);
    }
    
    /**
     * Test Method that tests the getMoves method.
     * Verifies that a non-null 2D array of Slots is returned.
     */
    @Test
    public void testGetMovesT7() {
        Minesweeper testGame = new Minesweeper(0);
        Assign assign = new Assign(testGame, 0, 0, "-");
        Slot[][] result = assign.getMoves();
        assertNotNull(result);
    }
    
    /**
     * Tests the assignMove method for an out-of-bounds move.
     * Verifies that an invalid move is correctly identified when assigning outside the game board.
     */
    @Test
    public void testAssignMoveOutOfBounds() {
        Assign assign = new Assign(testGame, 10, 10, "M");
        assign.assignMove("M");
        String result = assign.getMove(10, 10);
        assertEquals("Invalid move.", result);
    }
    
    /**
     * Tests the initialization of the moves array in the Assign class.
     * Verifies that all cells are initialized with "?" except for the assigned move.
     */
    @Test
    public void testGetMovesInitialisation() {
        Assign assign = new Assign(testGame, 0, 0, "M");
        Slot[][] moves = assign.getMoves();
        for (int i = 0; i < testGame.getGameSize(); i++) {
            for (int j = 0; j < testGame.getGameSize(); j++) {
                if (i == 0 && j == 0) {
                    assertEquals("M", moves[i][j].getState());
                } else {
                    assertEquals("?", moves[i][j].getState());
                }
            }
        }
    }
}

