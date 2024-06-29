import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
/**
 * This is the SlotObserver class to implement observer from the Slot class to change button state in MinesweeperGUIBoard class
 *  * @author Myran Teasdale
 *  * @version v1.3 Minesweeper GUI Edition
 */
public class SlotObserver implements Observer {
    // instance variables
    private JButton gameButton;
    private Slot slot;
    private JLabel livesLabel;
    private MinesweeperGUIBoard guiGameBoard;
    private Minesweeper mainGame;
    private MinesweeperGUI minesweeperGUI;
    private JLabel modeLabel;

    public SlotObserver(Minesweeper mainGame, MinesweeperGUIBoard guiGameBoard, JLabel livesLabel, JButton gameButton, Slot slot) {
        this.mainGame = mainGame;
        this.guiGameBoard = guiGameBoard;
        this.livesLabel = livesLabel;
        this.gameButton = gameButton;
        this.slot = slot;
    }
    /**
     * Method that updates the button state. Gets the current slot state and button then sets ? to M or 1,2,3 etc
     * @param observable     the observable object (a button)
     * @param obj   an argument passed to the {@code notifyObservers} can be found in Slot class
     *              method.
     */
    public void update(Observable observable, Object obj) {
        if(!(obj instanceof String)) {
            return;
        }

        String state = (String) obj;
        gameButton.setText(state);
        if (state.equals("M")) {
            gameButton.setBackground(GameColours.getCellColour(state)); // Set cell to colour depending on its value (M,0,1,2,3 etc)
            gameButton.setText("");
            guiGameBoard.setButtonImage(gameButton, "Images/mine.png");
            // Get the users new lives after losing one for updating of the lives label
            int lives = mainGame.getLives();
            livesLabel.setText(Integer.toString(lives));
            if (lives <= 1) {
                // Reduce lives on the gameboard (is only for show on the board, this does not affect the actual game lives)
                livesLabel.setForeground(Color.RED); // set lives text colour to red if at 1
            }
        } else {
            gameButton.setBackground(GameColours.getCellColour(state)); // Set cell to colour depending on its value (M,0,1,2,3 etc)
        }
        gameButton.setEnabled(false); // disable the cell so the player cannot click anymore

    } // end update method

} // end SlotObserver Class