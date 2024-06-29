import java.util.Observable;

/**
 * Slot
 * This class handles the state of each slot in the game. Extending observable in preparation for the addition of a GUI.
 * @author Myran Teasdale
 * @version v1.1 1/6/24
 */
public class Slot extends Observable{

    private String state;//The current state of the slot
    private int row, col;//The row and column number of the slot 

    /**
     * Constructor of the class slot when importing the level file
     * This creates the slot and denotes where it is placed in the game board.
     * 
     * @param col - the slot's column number
     * @param row - the slot's row number
     * @param status - the number that is in that cell
     */
    public Slot (int col, int row, String status) {
        this.row = row;
        this.col = col;
        this.state = status;
    }

    /**
     * getState
     * This provides the current state of the slot
     * @return the current state of the slot
     */
    public String getState(){
        return state;  
    }
    
    /**
     * setState
     * This method sets the state of the slot. addition of setChanged and notifyObservers to implement the GUI
     * @param state - the new state of the slot
     */
    public void setState(String state) {
        this.state = state;
        setChanged();
        notifyObservers(state);
    }

}//End of class Slot

