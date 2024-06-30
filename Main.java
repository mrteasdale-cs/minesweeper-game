import javax.swing.*;
/**
 * Main
 * This class handles the creation of the initial game object. Provides basic functionality to choose
 * the Text-Based UI (TUI) or the Graphical UI (GUI) using JOPtionpane confirm dialogs
 * @author Myran Teasdale
 * @version v1.3 29-5-24 (latest GUI Edition update)
 */
public class Main {
    /**
     * The main method within the Minesweeper Java application.
     * It's the core method of the program and calls all others.
     */
    public static void main(String args[]) {
        //Testing
        //MinesweeperGUI newGUI = new MinesweeperGUI(530, 480);
        //MinesweeperUI newUI = new MinesweeperUI();
        // Ask the user which type of game they would prefer.
        String[] choices = {"GUI", "TUI"}; //setup button choices
        int choice = JOptionPane.showOptionDialog(null, "GUI or Text-Based (TUI) game?",
                "Minesweeper v1.3 Combo Edition", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
        if (choice == 0) {
            MinesweeperGUI newGUI = new MinesweeperGUI(600, 500);
        } else {
            MinesweeperUI newUI = new MinesweeperUI();
        }
    } // end main method
} // end main class
