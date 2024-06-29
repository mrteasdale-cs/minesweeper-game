

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotTest.
 *
 * @author  Myran Teasdale
 * @version V1.1 31/5/24
 */
public class SlotTest
{
    Minesweeper testGame;
    Assign testAssign;
    Slot testSlot;
    int row = 2;
    int col = 4;
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        testGame = new Minesweeper(0);
        Assign testAssign = new Assign(testGame, row, col, "G");
        testSlot = new Slot(row, col, "0");
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    /**
     * Metho to test the Slot constructor.
     * CHecks that a Slot object is created successfully.
     */
    @Test
    public void testSlotGetStateT1() {
        String result = testSlot.getState();
        assertEquals("0", result);
    }
    /**
     * Metho to test the Slot constructor.
     * Checks that a Slot object is created successfully.
     */
    @Test
    public void testsetSlotStateT2() {
        testSlot.setState("3");
        String result = testSlot.getState();
        assertEquals("3", result);
    }

    @Test
    public void testSlotConstructorT3() {
        assertNotNull(testSlot);
    }
    /**
     * Tests multiple state changes.
     * Confirms that the state of the slot can be changed multiple times and always reflects the most recent change.
     * Useful when dealing with the save, load and clear methods as state changes will occur offten
     */
    @Test
    public void testMultipleStateChanges() {
        testSlot.setState("1");
        assertEquals("1", testSlot.getState());
        testSlot.setState("M");
        assertEquals("M", testSlot.getState());
        testSlot.setState("?");
        assertEquals("?", testSlot.getState());
    }

    
    

}
