import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Test class for MinesweeperUI.
 * This class contains unit tests for various methods in the MinesweeperUI class.
 */
public class MinesweeperUITest {

    private MinesweeperUI ui;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private InputStream originalIn;

    /**
     * Set up the test environment before each test.
     * Initializes MinesweeperUI, redirects System.out, and saves original System.in.
     */
    @BeforeEach
    public void setUp() {
        ui = new MinesweeperUI();
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        originalIn = System.in;
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Test the winningAnnouncement method.
     * Verifies that the correct winning message is printed.
     */
    @Test
    public void testWinningAnnouncement() {
        ui.winningAnnouncement();
        assertEquals("\nCongratulations, you solved the level\n", outContent.toString());
    }

    /**
     * Test the livesAnnouncement method.
     * Verifies that the correct game over message is printed.
     */
    @Test
    public void testLivesAnnouncement() {
        ui.livesAnnouncement();
        assertEquals("\nYou have ran out of lives, the game is over\n", outContent.toString());
    }

    /**
     * Test the getDifficulty method for Easy difficulty.
     * Simulates user input 'E' and checks if the method returns 0 (Easy).
     */
    @Test
    public void testGetDifficultyEasy() {
        String input = "E";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        assertEquals(0, ui.getDifficulty());
    }

    /**
     * Test the getDifficulty method for Hard difficulty.
     * Simulates user input 'H' and checks if the method returns 1 (Hard).
     */
    @Test
    public void testGetDifficultyHard() {
        String input = "H";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        assertEquals(1, ui.getDifficulty());
    }

    /**
     * Test the menu method.
     * Verifies that the correct menu options are printed.
     */
    @Test
    public void testMenu() {
        ui.menu();
        String expectedMenu = "\n\nPlease select an option: \n"
                + "[M] Flag a mine\n"
                + "[G] Guess a square\n"
                + "[S] Save game\n"
                + "[L] Load saved game\n"
                + "[U] Undo move\n"
                + "[C] Clear game\n"
                + "[Q] Quit game\n";
        assertEquals(expectedMenu, outContent.toString());
    }
}