import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
/**
 * This is the class for the GUI controls (buttons and adding labels from MinesweeperGUILabels Class)
 * @author Myran Teasdale
 * @version v1.3 Minesweeper GUI Edition
 */
public class MinesweeperGUIControl implements ActionListener {
    // instance variables/objects
    private JPanel controlPanel;
    private JPanel displayPanel;
    private MinesweeperGUI mainGUI;
    private MinesweeperGUILabels guiLabels;

    /**
     * This contructor takes in several paramters includuing an instance of the mainGUi as well as the labels that will
     * show on the control panel
     * @param mainGUI the mainGUI runner class 'MinesweeperGUI'
     * @param guiLabels the MinesweeperGUILabels class
     */
    public MinesweeperGUIControl(MinesweeperGUI mainGUI, MinesweeperGUILabels guiLabels) {
        this.mainGUI = mainGUI;
        this.guiLabels = guiLabels;
        this.createDisplayPanel();
        this.createControlPanel();
    }

    /**
     * Method to create a menu (work in progress) 26-6-24
     * @return JMenuBar item with 3 menu items
     */
    public JMenuBar getCreateMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenuItem menuItem;

        JMenu menu = new JMenu("File");
        menu.getAccessibleContext().setAccessibleDescription("File Menu");
        menuBar.add(menu);
        // add menu
        menuItem = new JMenuItem("Light Mode", KeyEvent.VK_D);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Help", KeyEvent.VK_H);
        menuItem.addActionListener(this);
        menu.add(menuItem);
        menuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //add the menu items to the menu bar
        menuBar.add(menu);
        return menuBar;
    } //end method

    /**
     * Method to create the main display panel at the top of the game window with all labels
     */
    public void createDisplayPanel() {
        displayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        displayPanel.setBackground(GameColours.DISPLAY_PANEL); // constant from GameColours class
        displayPanel.setPreferredSize(new Dimension(mainGUI.getWIDTH(), 60));
        // add the labels to the control panel
        //guiLabels = new MinesweeperGUILabels();
        displayPanel.add(guiLabels.getLabelTitle());
       // displayPanel.add(guiLabels.getSpacerLabel()); // Spacer label
        displayPanel.add(guiLabels.getLivesTitleLabel());
        displayPanel.add(guiLabels.getLivesLabel());
        displayPanel.add(guiLabels.getModeTitleLabel());
        displayPanel.add(guiLabels.getModeLabel());
        guiLabels.setModeLabel("G"); // Set default mode to Guess (G)
    } // end createDisplayPanel method

    /**
     * This method sets up the control panel at the top of the screen
     */
    public void createControlPanel() {
        controlPanel = new JPanel(new GridLayout(1, 6));
        controlPanel.setBackground(GameColours.CONTROL_BUTTON_COLOUR);
        controlPanel.setPreferredSize(new Dimension(mainGUI.getWIDTH(), 43));

        // set a new controlButtons array with names of the buttons
        String[] controlButtons = {
                "Toggle", "Save", "Undo", "Clear", "Load", "Quit"
        }; // add the buttons to an array for easy scalability of the program

        //  add each button for gameplay, uses action listners to check for clicks and acts accordingly
        for (String buttonText : controlButtons) {
            JButton button = new JButton(buttonText);
            button.setFont(new Font("Consolas", Font.BOLD, 12));
            button.setFocusable(false);
            button.setPreferredSize(new Dimension(70, 28));
            button.addActionListener(this);
            button.addMouseListener(new MouseHandler(button, "controlButton"));

            // Set button apperance
            //button.setBorderPainted(false);
            button.setBackground(GameColours.CONTROL_BUTTON_COLOUR);

            controlPanel.add(button);
        }
    } // end createControlPanel method

    // Getter method for display panel
    public JPanel getDisplayPanel() {
        return displayPanel;
    } // end method

    // Getter method for control panel
    public JPanel getControlPanel() {
        return controlPanel;
    } // end method

    /* 
    * This method is used to perform the game controls by calling each method based on which button is clicked
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        // this logic will grab each menu item and perform the appropriate actions
        if (e.getSource() instanceof JMenuItem) {
            JMenuItem item = (JMenuItem) e.getSource();
            if (item.getText().equals("Light Mode")) {
                //WORK IN PROGRESS - NOT FUNCTIONING AS EXPECTED
                controlPanel.setForeground(GameColours.DARK_TEXT_COLOUR);
                controlPanel.repaint(); // this is a work in progress.have not solved the repainting issue.
            } else if (item.getText().equals("Help")) {
                JOptionPane.showMessageDialog(null, "Click the squares with ? if you hit a mine you lose a life. If you uncover a number this will tell you how many mines are nearby.\n\n" +
                        "Controls\nClear Game: clears the game and starts again\nUndo Move: Undoes a move!\nToggle: switches between flagging for mines and guessing square" +
                        "\nLoad Game: Loads a save game file from browser\nSave Game: Saves you current game" +
                        "\n\nThat's all. Have fun!\nContact: Mr Tea (teach@mrteasdale.com)");
            } else if (item.getText().equals("Quit")) {
                System.exit(0);
            }
        }

        // this logic will grab the control panel buttons (at the bottom of the screen) and assign actions to them
        if (e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource();
            String buttonText = clickedButton.getText();
            int choice;
            // switch statement to check which button was pressed and perform necessary action
            switch (buttonText) {
                case "Toggle":
                    String mode = guiLabels.getModeLabel().getText();
                    //System.out.println("Before Toggle: " + mode);
                    if (mode.equalsIgnoreCase("G")) {
                        JOptionPane.showConfirmDialog( //show confirmation box to player  mainGUI.getMainFrame() centers the dialog box in the main gui frame
                                mainGUI.getMainFrame(), "Careful, you are now flagging a mine...",
                                "Flag a Mine", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                        guiLabels.setModeLabel("M");
                        guiLabels.setModeLabelColor(255,0,0);
                    } else {
                        guiLabels.setModeLabel("G");
                        guiLabels.setModeLabelColor(0, 135, 214);
                    }
                    break;
                case "Save":
                    // create mew filechooser object of type save
                    String saveGameName = mainGUI.fileChooser("save");
                    mainGUI.saveGame(saveGameName);
                    break;
                case "Undo":
                    mainGUI.undoMove();
                    break;
                case "Clear":
                    choice = JOptionPane.showConfirmDialog(
                            null, "Play again?",
                            "New Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (choice == JOptionPane.YES_OPTION) {
                        mainGUI.clearGame();
                    } else {
                        mainGUI.gameRunning = false;
                        System.exit(0);
                    }
                    mainGUI.clearGame();
                    break;
                case "Load":
                    // create mew filechooser object of type load
                    String fileName = mainGUI.fileChooser("load");
                        try {
                            mainGUI.loadGame(fileName); // load the game - uses try catch block to catch any filenotfound errors
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    break;
                case "Quit":
                    choice = JOptionPane.showConfirmDialog(
                            mainGUI.getMainFrame(), "Are you sure you want to quit?",
                            "Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (choice == 0) {System.exit(0);}
            } //end switch statement
        }
    } // end action performed method
} // end class
