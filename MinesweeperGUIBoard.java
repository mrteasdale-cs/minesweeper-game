import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
/**
 * This is the class for the GUI game board (the grid itself). It contains a slot observer inner class that implements Observer for
 * action listeners to be used on the Slot class
 * @author Myran Teasdale
 * @version v1.3 Minesweeper GUI Edition
 */
public class MinesweeperGUIBoard extends MouseAdapter {

    // Instance variables/objects
    private JPanel gameGridPanel;
    private MinesweeperGUI mainGUI;
    private MinesweeperGUILabels guiLabels;
    private Assign assign;
    private Minesweeper mainGame;
    private JButton[][] buttons;

    /* MinesweeperGUIBoard constructor
    * This sets the mainGUI pane, the game grid, labels and the buttons that go into the game grid.
    */
    public MinesweeperGUIBoard(MinesweeperGUI mainGUI, Minesweeper mainGame, MinesweeperGUILabels guiLabels) {
        this.mainGUI = mainGUI;
        this.mainGame = mainGame;
        this.buttons = new JButton[mainGame.getGameSize()][mainGame.getGameSize()]; //create an array of buttons of size 'gamesize'
        this.guiLabels = guiLabels;
        this.createGamePanel();
    }

    /**
     * This method sets up the game buttons with "?" to simulate an unkown square like in the Text UI
     * it then sets each button's properties, adds in to a JButton[][] array and adds an o bserver to the
     * button. An action listner is also added to allow it to perform a piece of code when clicking the
     * button. In this case each button click calls the getMakeMove method which in turn calls the makeMove method
     * in the Minesweeper class
     */
    public void createGamePanel() {
        gameGridPanel = new JPanel(new GridLayout(mainGame.getGameSize(), mainGame.getGameSize()));

        // Sets the game board based on the moves from the main Minesweeper class
        for (int row = 0; row < mainGame.getGameSize() ; row++) {
            for (int col = 0; col < mainGame.getGameSize() ; col++) {
                // Create the buttons
                JButton cellButton = createCellButtons();
                // assign the JButton to its own cell in the gamepanel grid
                buttons[row][col] = cellButton;
                gameGridPanel.add(cellButton);
                // setup a new slot based on the current row and column move
                Slot slot = mainGame.getMoves()[row][col];
                // call the addObserver method with a new instance of the slotobserver class
                slot.addObserver(new SlotObserver(mainGame, this, guiLabels.getLivesLabel(), cellButton, slot));
                // call the setupListeners method below that add an action and mouse listener for game functionality
                setupListeners(row, col, cellButton);
            }
        }
        gameGridPanel.setPreferredSize(new Dimension(mainGUI.getWIDTH(), mainGUI.getHEIGHT() - 100));
    }

    public JButton getCellButton(int row, int col) {
        return buttons[row][col];
    }

    /**
     * Method to create a single JButton cell button object
     * @return JButton (an individual cell on the game grid)
     */
    private JButton createCellButtons() {
        Font gameFont = new Font("Consolas", Font.BOLD, 24);
        JButton cellButton = new JButton("?");
        // Set apperance for button
        cellButton.setFont(gameFont);
        cellButton.setFocusable(false);
        cellButton.setBackground(GameColours.GAME_BUTTON_COLOUR);
        cellButton.setBorder(BorderFactory.createLineBorder(GameColours.BUTTON_BORDER));
        cellButton.setForeground(GameColours.LIGHT_TEXT_COLOUR);
        return cellButton;
    } // end method

    /**
     * Method to setup a listener for the button so when it is clicked it will react according to the
     * types of move the player made
     * @return
     */
    private void setupListeners(int row, int col, JButton cellButton) {
        // add action listener to button that will call the handleButtonClick() method
        cellButton.addActionListener(e -> handleButtonClick(row, col));
        // mouse listener to allow for hover over changes from the MouseHandler class
        cellButton.addMouseListener(new MouseHandler(buttons[row][col], "gameButton"));
    } //end setupListeners method

    /**
     * Method that handles the clicking of a game button. It will return the current mode, M or G and use that to retrieve the
     * outcome of the click via the Minesweeper classes makeMove method. This is then used to update the game status label.
     * @param row
     * @param col
     */
    public void handleButtonClick(int row, int col) {
        String mode = guiLabels.getModeLabel().getText();
        String result = mainGame.makeMove(String.valueOf(row), String.valueOf(col), mode);
        updateGameStatus(result);
        //JOptionPane.showMessageDialog(gameGridPanel, result, "Outcome", JOptionPane.INFORMATION_MESSAGE);
        mainGUI.saveMoves(row, col);
    }

    /**
     * Method to set the game status label at the top of the game board based on each players move.
     * @param result Outcome of each move as a String
     */
    public void updateGameStatus(String result) {
        // Set game status label using the outcome of each move passes as the parameter
        guiLabels.getGameStatusLabel().setText(result);
        //logic set the game status according to the result - red if the player loses a life, else blue/default
        if (result.contains("You have lost one life.")) {
            guiLabels.getGameStatusLabel().setForeground(GameColours.ALERT_TEXT_COLOUR);
        } else {
            guiLabels.getGameStatusLabel().setForeground(GameColours.GAMESTATUS_TEXT_COLOUR);
        }
    }

    /**
     * This method gets the JPanel
     * @return Jpanel as gamePanel object
     */
    public JPanel getPanel() {
        return gameGridPanel;
    } // end method

    /**
     * This method returns the JButton at the current location row/col
     * @param row an Integer
     * @param col an Integer
     * @return JButton object to be manipulated in the MinesweeperGUI class
     */
    public JButton getButton(int row, int col){
        return buttons[row][col];
    }


    public void setButtonImage(JButton button, String image) {
        // set mine icon algorithm
        // calculate a relative size using frame width and game size (grid) then multipling by 0.4 (40%)
        double relativeIconSize = ((double) mainGUI.getWIDTH() / mainGame.getGameSize()) * 0.4; //40% size of cell
        int roundIconSize = (int) relativeIconSize; // cast double result to int
        // load the image as ImageIcon
        ImageIcon originalIcon = new ImageIcon(image);
        Image originalImage = originalIcon.getImage();
        // scale the image using the relative int size
        Image scaledIcon = originalImage.getScaledInstance(roundIconSize, roundIconSize, Image.SCALE_SMOOTH);
        // convert back to ImageIcon object
        ImageIcon icon = new ImageIcon(scaledIcon);
        button.setIcon(icon); // set the buttons icon
    }// end setButtonImage method

} // end MinesweeperGUIBoard class
