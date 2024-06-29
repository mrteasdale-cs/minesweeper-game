import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * This interface implements all the methods used across both the Text UI and GUI game interface
 */
public interface GameInterface {

    // create methods to be implemented by the GUi and UI class
    void clearGame();
    void saveGame(String fileName) throws IOException;
    void loadGame(String fileName) throws FileNotFoundException; //used for Text based game
    int getDifficulty();
    String getChoice();
    void undoMove();
    void saveMoves(int row, int col);
    String getSaveGame();
    void winningAnnouncement();
    void livesAnnouncement();

} // end interface GameInterface
