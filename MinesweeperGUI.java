import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Stack;
/**
 * This is the main class for the GUI edition of the game. It implements the GameInterface
 * Class to override its methods that it has in common with the UI Text-Based Game.
 * @author Myran Teasdale
 * @version v1.3 Minesweeper Game GUI Edition latest update: 18/6/24
 */
public class MinesweeperGUI implements GameInterface {

    // instantiate class fields
    private JFrame frame;
    private JPanel statusPanel;
    private final int HEIGHT; // set a variable for the height of the game window
    private final int WIDTH; // set a variable for the width of the game window
    boolean gameRunning = true;
    // Game objects
    private MinesweeperGUIBoard guiGameBoard;
    private MinesweeperGUIControl guiControlPanel;
    private Minesweeper mainGame;
    private MinesweeperGUILabels guiLabels;

    private Stack<String[]> moveHistory; // stack to keep track of moves for undo functionality

    public MinesweeperGUI(int HEIGHT, int WIDTH) {
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        int difficulty = getDifficulty(); // used to choose easy or hard mode
        this.mainGame = new Minesweeper(difficulty); // Initialise the game instance
        this.guiLabels = new MinesweeperGUILabels();
        this.guiControlPanel = new MinesweeperGUIControl(this, guiLabels);
        this.moveHistory = new Stack<>();
        this.createMainFrame();
        gameLoop();
    }

    /**
     * This getter method returns the height of the panel
     * @return the height of the panel as integer
     */
    public int getHEIGHT() {
        return HEIGHT;
    }

    /**
     * This getter method returns the width of the panel
     * @return the width of the panel as integer
     */
    public int getWIDTH() {
        return WIDTH;
    }

    /**
     * This method sets up the main JFrame and other settings for the back panel of the game
     * void
     */
    private void createMainFrame() {
        frame = new JFrame("Minesweeper v1.3 (Combo Edition)");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon("Images/icon-ms.png").getImage());
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        addComponentsToFrame(); // call the addComponentsToFrame method to add the various components to the main frame
        frame.setVisible(true);
    }

    /**
     * This method adds all components to the frame
     */
    private void addComponentsToFrame() {
        frame.setJMenuBar(guiControlPanel.getCreateMenu());
        frame.add(createTopPanel(), BorderLayout.NORTH);
        frame.add(createGameBoard(), BorderLayout.CENTER);
        frame.add(guiControlPanel.getControlPanel(), BorderLayout.SOUTH);
    }

    /**
     * This method returns the game board panel (minesweeper cells)
     * @return JPanel - the top panel which is the display (mode and lives) as well as the game status panels
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(guiControlPanel.getDisplayPanel(), BorderLayout.NORTH);
        topPanel.add(createGameStatusPanel(), BorderLayout.CENTER);
        return topPanel;
    }

    /**
     * This method returns the game status panel
     * @return JPanel
     */
    private JPanel createGameStatusPanel() {
        statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setBackground(GameColours.STATUS_PANEL_COLOUR);
        statusPanel.setPreferredSize(new Dimension(WIDTH, 25));
        statusPanel.add(guiLabels.getGameStatusTitleLabel());
        statusPanel.add(guiLabels.getGameStatusLabel());
        return statusPanel;
    }

    /**
     * This method returns the game board panel (minesweeper cells)
     * @return JPanel - the game grid panel
     */
    private JPanel createGameBoard() {
        guiGameBoard = new MinesweeperGUIBoard(this, mainGame, guiLabels);
        return guiGameBoard.getPanel();
    }
    /**
     * method to return the main frame
     * @return JFrame frame
     */
    public JFrame getMainFrame() { return frame;}

    /**
     * Method to set the button state back to defaults - used in the undo and clear game method
     * @param row integer of current row
     * @param col integer of current column
     */
    public void setGameButtonDefault(int row, int col) {
        // Set the buttons state back to normal using the getButtonState method from the GUIBoard class
        // then enabling it, setting text back to ? and recolouring the background to grey
        JButton lastButtonPressed = guiGameBoard.getButton(row, col);
        lastButtonPressed.setEnabled(true);
        lastButtonPressed.setText("?");
        lastButtonPressed.setBackground(GameColours.GAME_BUTTON_COLOUR);
        lastButtonPressed.setForeground(GameColours.LIGHT_TEXT_COLOUR);
        guiGameBoard.setButtonImage(lastButtonPressed, "");
    }

    /**
     * Method that shows all mines on game lose
     * BUG: sometimes does not show mines
     */
    public void showMines() {
        for (int row = 0; row < mainGame.getGameSize() ; row++) {
            for (int col = 0; col < mainGame.getGameSize() ; col++) {
                String currentState = mainGame.getIndividualMove(row, col);
                if (currentState.equalsIgnoreCase("M")) {
                    JButton gameCell = guiGameBoard.getCellButton(row, col);
                    guiGameBoard.setButtonImage(gameCell, "Images/mine.png");
                    // TESTING
                    // System.out.println("Mine: " + row + ", " + col);
                    gameCell.setText("");
                    gameCell.setEnabled(false);
                }
            }
        }
        frame.revalidate();
        frame.repaint();
    }

    private void threadDelay(){
        try {
            Thread.sleep(200); // add a small delay to prevent cpu overusage
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to start the game loop within the contructor. allows the restart game function to work
     * as intended byt restarting the while loop that checks for the winning/losing condition.
     */
    public void gameLoop() {
        gameRunning = true;
        while (gameRunning) {
            String gameStatus = mainGame.checkWin();
            //System.out.println(gameStatus);
            threadDelay();
            if (gameStatus.equals("won")) {
                winningAnnouncement();
                checkGameEnd();
            } else if (gameStatus.equals("lives")) {
                showMines();
                JLabel labelTitle = guiLabels.getLabelTitle();
                labelTitle.setIcon(new ImageIcon("Images/sweeper-sad.png"));
                livesAnnouncement();
                checkGameEnd();
            }
        }
    } // end gameLoop method

    /**
     * Method that handles a graceful game exit when winning or losing
     */
    public void checkGameEnd() {
        int playAgain = JOptionPane.showConfirmDialog(
                frame, "Play again?",
                "New Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (playAgain == JOptionPane.YES_OPTION) {
            clearGame();
        } else {
            gameRunning = false;
            System.exit(0);
        }
        gameLoop();

    }

    //Overidden methods from the game interface class
    /**
     * This method implements a JOPtionpane to allow the user to either start again after winning
     * or exit the game
     */

    @Override
    public void winningAnnouncement() {
        JOptionPane.showConfirmDialog(
                frame, "Congrats! You solved the level.",
                "WINNER", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    } // end method

    /**
     * This method implements a joptionpane to allow the user to either start again after loosing
     * or exit the game
     */
    @Override
    public void livesAnnouncement() {
        JOptionPane.showConfirmDialog(
                frame, "You lose, better luck next time!",
                "LOSER", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    } // end method

    /**
     * This method is implemented in the saveGame and LoadGame methods.
     * It will open a file choose box and return the name of the file that the user selects.
     * @return String of the fileName to be saved/loaded
     */
    public String fileChooser(String operation) {
        //set empty fileName
        String fileName = "";
        int returnVal;
        //Create a file chooser
        final JFileChooser fChooser = new JFileChooser();
        // set current directory, could be changed to default to current location
        fChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        //In response to a button click
        if (operation.equals("load")) {
            returnVal = fChooser.showOpenDialog(frame); //show an openfile dialog
        } else {
            returnVal = fChooser.showSaveDialog(frame); //show an savefile dialog
        }

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File file = fChooser.getSelectedFile(); //open new file browser
            //String fileName = mainGUI.getSaveGame(); // set the save game filename string to pass into the loadGame method
            Path path = Paths.get(file.toURI());
            fileName = path.toString(); //cast file path to string for method parameter
        }
        return fileName;
    }

    /**
     * undoMove. Same as UI, this method utilises a stack data structure to check the previous move of
     * the game and revert back by setting that particular cell as a ?
     * Additionally, this GUI interface method also must return the button state back to default and
     * update the observers so it can be clicked again.
     * void method, does not return anything
     */
    @Override
    public void undoMove() {
        if (moveHistory.size() > 1) { // Check the move history stack is larger than 1
            int choice = JOptionPane.showConfirmDialog(
                    frame, "Are you sure you want to undo that move?",
                    "Undo Move", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (choice == JOptionPane.YES_OPTION) {
                String[] previousState = moveHistory.peek(); // Get the previous state
                // Create a slot object and assign the move to the previous column and row
                int previousCellRow = Integer.parseInt(previousState[0]);
                int previousCellCol = Integer.parseInt(previousState[1]);
                Slot slot = new Slot(previousCellRow, previousCellCol, "?");
                // Revert cell (on game board) back to normal state
                slot.setState("?");
                // Call the setButtonDefaults Method
                setGameButtonDefault(previousCellRow, previousCellCol);

                // Set label and lives back to 3 as well as the colour to green in case of lives being at 1
                JLabel livesLabel = guiLabels.getLivesLabel();
                int lives = Integer.parseInt(livesLabel.getText());

                if (lives < 3) {
                    lives++;
                    String newLives = Integer.toString(lives);
                    livesLabel.setText(newLives);
                    livesLabel.setForeground(GameColours.LIVES_TEXT_COLOUR);
                    mainGame.setLives(lives);
                }
                moveHistory.pop(); // Remove the current state to continue being able to undo moves
            }
        } else { // only shows if stack contains 1 or 0 elements
            JOptionPane.showConfirmDialog(
                    frame, "Make a move first!",
                    "Undo Move", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
        }
    }
    /**
     * This is the clearGame method. It will display to the user a popup box and if they choose yes it will iterate the main game grid
     * and set all slots to ? as well as reset the button appearances to default. The method will also return lives back to 3 and reset
     * any colour changes that have been made during the game such as the lives number.
     */
    @Override
    public void clearGame() {
        String gameStatus = mainGame.checkWin();

            for (int row = 0; row < mainGame.getGameSize(); row++) {
                for (int col = 0; col < mainGame.getGameSize(); col++) {
                    Slot slot = new Slot(row, col, "?");
                    slot.setState("?");
                    // create new assign object to get the current move and ensure buttons are reset properly to default
                    Assign assign = new Assign(mainGame, row, col,"");
                    mainGame.setCellState(row, col, assign.getMove(row, col));
                    setGameButtonDefault(row, col); // Method to clear button back to default state
                }
            }
            // Set game status back to default
            JLabel gameStatusLabel = guiLabels.getGameStatusLabel();
            gameStatusLabel.setText("Make a move first.");
            gameStatusLabel.setForeground(GameColours.GAMESTATUS_TEXT_COLOUR);

            // Set mode back to G
            JLabel modeLabel = guiLabels.getModeLabel();
            modeLabel.setText("G");
            modeLabel.setForeground(GameColours.GAMESTATUS_TEXT_COLOUR);

            // Set lives back to 3
            JLabel livesLabel = guiLabels.getLivesLabel();
            livesLabel.setText("3");
            livesLabel.setForeground(GameColours.LIVES_TEXT_COLOUR);
            mainGame.setLives(3);

            //sets the icon back to happy face when restarting the game after losing
            if (!gameStatus.equalsIgnoreCase("continue")) {
                JLabel labelTitle = guiLabels.getLabelTitle();
                labelTitle.setIcon(new ImageIcon("Images/sweeper-happy.png"));
            }
    } // end clearGame method

    /**
     * saveGame
     * This method saves the current state of the game, along with the corresponding moves already made
     * @param fileName accepts a string for the name of the file to save as
     */
    @Override
    public void saveGame(String fileName) {
        if (!fileName.isEmpty()) { //check if filename has string and run method if so
            try {
                // Create new printstream object to write to files
                PrintStream print = new PrintStream(fileName);

                print.println(mainGame.getGameSize()); // add the gamesize to the savefile

                for (int row = 0; row < mainGame.getGameSize(); row++) {
                    for (int col = 0; col < mainGame.getGameSize(); col++) {
                        // Create the save game using the current state of cells - this will generate a string with
                        // row, col and the cell state and write to the file
                        print.println(row + " " + col + " " + mainGame.getCellState(row, col));
                    }
                }

                print.close(); // close the printstream
                JOptionPane.showConfirmDialog(frame, "Saved successfully\n" + fileName, "Saving", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);

            } catch (IOException e) {
                // catch any exceptions with saving to file - uses a friendly JOPtion Pane
                JOptionPane.showConfirmDialog(frame, "Error Saving File" + "\n\nMore Information:\n" + e, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * saveGame
     * This method gets an already saved game form file and inserts it into the game so the player can start where they previosuly left off
     * @param fileName accepts a string for the name of the file to load
     */
    @Override
    public void loadGame(String fileName) throws FileNotFoundException {
        if (!fileName.isEmpty()) { //check if filename has string and run method if so
            try {
                clearGame(); //clear the game first which sets everything back to default
                Scanner reader = new Scanner(new File(fileName));
                int gameSize = Integer.parseInt(reader.next());

                // a nested loop to iterate through the game grid and set each button to the state from the savefile.
                // if the state is '?' it will not do anything.
                for (int i = 0; i < gameSize; i++) {
                    for (int j = 0; j < gameSize; j++) {
                        int row = Integer.parseInt(reader.next());
                        int col = Integer.parseInt(reader.next());
                        String status = reader.next();
                        new Slot(row, col, status);
                        JButton currButton = guiGameBoard.getButton(row, col);
                        // Only set the buttons to the status and disabled if they are not ? meaning they
                        // were previously uncovered
                        if (!status.equals("?")) {
                            currButton.setText(status);
                            Color currColour = GameColours.getCellColour(status);
                            currButton.setBackground(currColour);
                            currButton.setEnabled(false);
                            //currButton.getBut
                        }
                    }
                }

                // confirm dialog to tell the use if the load was successful
                JOptionPane.showConfirmDialog(
                        frame, "Loaded successfully from " + fileName,
                        "Success",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);

                reader.close(); //close reader to conserve memory and io

            } catch (FileNotFoundException e) {
                JOptionPane.showConfirmDialog(frame,
                        "Error Loading File. Try again." + "\n\nMore Information:\n" + e, "Error",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
            }
        }
    } // end saveGame method

    /**
    * This method returns an integer value based on the difficult the players wants
    */
    @Override
    public int getDifficulty() {
        String[] choices = {"Easy, please", "Hard!"}; //setup button choices
        int choice = JOptionPane.showOptionDialog(
                frame, "What difficulty?",
                "New Game", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);

        // logic to return easy (0) or hard mode (1)
        if (choice == 0) {
            return 0;
        } else {return 1;}

    } // end getDifficulty method

    /**
    * This method is used to sdave the current location of the cell that the player pressed into a stack called moveHistory
    * @param row - accepts an integer for the row and column of the cell being acessed.
    * @param col - accepts an integer for the row and column of the cell being acessed.
    */
    @Override
    public void saveMoves(int row, int col) {
        String[] currMove = new String[2];
        currMove[0] = Integer.toString(row);
        currMove[1] = Integer.toString(col);
        moveHistory.push(currMove);
    }

    /**
    * This method return the save file location as a string
    * @return String save game level
    */
    public String getSaveGame() {
        return "Levels/GUI/SAVEGAME.txt";
    }

    /**
    * This method is not used in the GUI. returns and empty string.
    */
    @Override
    public String getChoice() {
        return "";
    } //UNUSED

} // end MinesweeperGUI class
