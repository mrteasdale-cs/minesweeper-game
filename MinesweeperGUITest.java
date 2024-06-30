import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * The test class MinesweeperGUITest.
 *
 * @author  Myran Teasdale
 * @version 1.3 GUI Edition 24/6/24
 */
public class MinesweeperGUITest
{
    private MinesweeperGUI gui;
    /**
     * Default constructor for test class MinesweeperGUITest
     */
    public MinesweeperGUITest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        gui = new MinesweeperGUI(600, 500);
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

    @Test
    public void testCreateMainFrame() {
        // Test if the main frame is created with the correct properties
        JFrame frame = gui.getMainFrame();
        assertNotNull(frame);
        assertEquals("Minesweeper v1.3 (Combo Edition)", frame.getTitle());
        assertEquals(800, frame.getWidth());
        assertEquals(500, frame.getHeight());
        assertFalse(frame.isResizable());
        assertNotNull(frame.getIconImage());
    }

    @Test
    public void testCreateGameBoard() {
        JPanel gameBoard = gui.createGameBoard();
        assertNotNull(gameBoard);
        fail();
    }

    @Test
    public void testGetHeight() {
        // Test if the height is correctly returned
        assertEquals(600, gui.getHEIGHT());
    }

    @Test
    public void testGetWidth() {
        // Test if the width is correctly returned
        assertEquals(800, gui.getWIDTH());
    }

    @Test
    public void testMethods() {
        // Test if the overridden methods from the GameInterface are implemented
        gui.undoMove();
        gui.clearGame();
        gui.saveGame("test.txt");
        try {
            gui.loadGame("test.txt");
        } catch (FileNotFoundException e) {
            // Handle the exception if the file is not found
        }
        assertEquals(0, gui.getDifficulty());
        gui.saveMoves(0, 0);
        assertEquals("", gui.getSaveGame());
    }
}

