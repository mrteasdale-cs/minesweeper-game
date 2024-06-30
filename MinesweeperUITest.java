import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class MinesweeperUITest {

    private MinesweeperUI ui;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    public void setUp() {
        ui = new MinesweeperUI();
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        originalIn = System.in;
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testWinningAnnouncement() {
        ui.winningAnnouncement();
        assertEquals("\nCongratulations, you solved the level\n", outContent.toString());
    }

    @Test
    public void testLivesAnnouncement() {
        ui.livesAnnouncement();
        assertEquals("\nYou have ran out of lives, the game is over\n", outContent.toString());
    }

    @Test
    public void testGetDifficultyEasy() {
        String input = "E";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        assertEquals(0, ui.getDifficulty());
    }

    @Test
    public void testGetDifficultyHard() {
        String input = "H";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        assertEquals(1, ui.getDifficulty());
    }

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
