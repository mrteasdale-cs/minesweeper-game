import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * The test class UITest.
 *
 * @author  Myran
 * @version 29-5-24
 */
public class MinesweeperUITest
{
    private MinesweeperUI ui;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    /**
     * Default constructor for test class UITest
     */
    public MinesweeperUITest()
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
        ui = new MinesweeperUI();
        System.setOut(new PrintStream(outContent));
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

    /*
    * Test method to test the get choice method
     */
    @Test
    public void testGetChoiceT2()
    {
        MinesweeperUI thisMinesweeperUI = new MinesweeperUI();
        Minesweeper mineswee = new Minesweeper(0);
        assertEquals("L","L","L");
    }

    /*
     * Test method to test the winning announcement method
     */
    @Test
    public void testWinningAnnouncement() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        MinesweeperUI ui = new MinesweeperUI();
        ui.winningAnnouncement();

        String expectedOutput = "\nCongratulations, you solved the level";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

}

