import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Stack;
/**
 * This class provides a text-based user interface for the player to interact with the game
 * @author Myran Teasdale
 * @version v1.3 (GUI Edition) 29-5-24 latest update: 18/6/24
 */
public class MinesweeperUI implements GameInterface {

    // instance variables set to private to prevent access from other classes
    private Minesweeper thegame;//this is the game model
    private String menuChoice;//this is the users choice from the menu
    private Scanner reader;//this scanner is used to read the terminal
    private Stack<String[]> moveHistory; // stack to keep track of moves for undo functionality
    private String saveGame = "Levels/";
    /**
     * Constructor for the class UI
     */
    public MinesweeperUI() {
        reader = new Scanner(System.in);
        thegame = new Minesweeper(getDifficulty());
        menuChoice="";
        moveHistory = new Stack<>(); // create a new stack for move history

        while(!menuChoice.equalsIgnoreCase("Q")&&thegame.checkWin().equals("continue")) {
            displayGame();
            menu();
            menuChoice = getChoice();
        }
        if (thegame.checkWin().equals("won") ){
            winningAnnouncement(); //check won
        } else if (thegame.checkWin().equals("lives") ){
            livesAnnouncement(); //check lose
        }
    }

    /**
     * Method that outputs an announcement when the user has won the game
     */
    @Override
    public void winningAnnouncement() {
        System.out.println("\nCongratulations, you solved the level");
    }

    /**
     * Method that outputs an announcement when the user has lost due to lack of lives
     */
    @Override
    public void livesAnnouncement() {
        System.out.println("\nYou have ran out of lives, the game is over");
    }

    /**
     * Method that displays the game for the user to play
     */
    private void displayGame() {
        //boardmoves = thegame.getMoves();
        System.out.print("\n\nCol    ");
        for (int r = 0; r < thegame.getGameSize(); r++) {
            System.out.print(r + " ");
        }
        for (int i = 0; i < thegame.getGameSize(); i++) {
            System.out.print("\nRow  " + i);
            for (int c = 0; c < thegame.getGameSize() ; c++) {
                System.out.print(" "+thegame.getCellState(i,c));
            }
        }
        
    }

    // Getters
    /**
     * This method gets the level name
     * @return the level name as string
     */
    @Override
    public int getDifficulty() {
        System.out.println("Welcome to Minesweeper Text-Based Edition\n(E)ASY or (H)ARD Mode?");
        int difficulty = 0;
        String choice = reader.next();

        while (!choice.equals("E") && !choice.equals("H")) {
            System.out.println("Invalid choice. Please enter (E) or (H)");
            choice = reader.next();
        }

        if (choice.equals("E")) {
            difficulty = 0;
        } else {
            difficulty = 1;
        }
        return difficulty;
    }


    /**
     * Method that displays the menu to the user
     * no return - void type
     */
    public void menu() {
        System.out.println("\n\nPlease select an option: \n"
            + "[M] Flag a mine\n"
            + "[G] Guess a square\n"
            + "[S] Save game\n"
            + "[L] Load saved game\n"
            + "[U] Undo move\n"
            + "[C] Clear game\n"
            + "[Q] Quit game");
    }

    /**
     * Method that gets the user's choice from the menu and conducts the activities
     * accordingly
     * @return the choice the user has selected
     * 
     */
    public String getChoice() {
        String choice = reader.next().toUpperCase();

        while (!choice.matches("[MGSULCQmgsulcq]")) { // regex to validate input choice
            System.out.println("Invalid choice. Try again.");
            choice = reader.next();
        }

        if (choice.equalsIgnoreCase("M") || choice.equalsIgnoreCase("G")) {
            int row;
            int col;

            while (true) {
                System.out.print("Which row is the cell you wish to flag?  ");
                String rowInput = reader.next();
                try {
                    row = Integer.parseInt(rowInput);
                    if (row < 0 || row >= thegame.getGameSize()){
                        System.out.println("Invalid row");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Must be a valid integer.\n");
                }
            }

            while (true) {
                System.out.print("Which column is the cell you wish to flag?  ");
                String colInput = reader.next();
                try {
                    col = Integer.parseInt(colInput);
                    if (col < 0 || col >= thegame.getGameSize()){
                        System.out.println("Invalid column");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Must be a valid integer.\n");
                }
            }

            System.out.print(thegame.makeMove(Integer.toString(row), Integer.toString(col), choice));
            saveMoves(row, col);

        } else if (choice.equalsIgnoreCase("S")) {
            String saveName = getFolderLocation();
            System.out.print("Enter filename (without extension/.txt): ");
            String fileName = reader.next();
            try { saveGame(fileName); }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else if (choice.equalsIgnoreCase("U")) {
            undoMove();
        } else if (choice.equalsIgnoreCase("L")) {
            //String fileName = "SAVEGAME.txt";
            System.out.print("Enter filename (test with SAVEGAME1.txt): ");
            String fileName = reader.next();
            String fileType = fileName.substring(fileName.lastIndexOf(".")); //get filetype using substring method
            // check if filetype is txt, then try to load file using try catch block
            if (fileType.equalsIgnoreCase(".txt")) {
                try {
                    loadGame(fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace(); //error handling
                }
            } else {
                System.out.println("Invalid filetype. Must be .txt"); //error handling for incorrect filetype
            }
        } else if (choice.equalsIgnoreCase("C")) {
            System.out.println("Are you sure? Press Y to clear.");
            choice = reader.next();
            if (choice.equalsIgnoreCase("Y")){
                clearGame();
            }
        } else if (choice.equalsIgnoreCase("Q")) {
            System.exit(0);
        }
        return choice;
    } //end getChoice method

    // Main Game Methods (Overriden) - implemented from the GameInterface interface
    /**
     * saveGame 
     * This method saves the current state of the game, along with the corresponding moves already made
     * @param fileName accepts a string for the name of the file to save as
     */
    @Override // Overidden method to allow for UI and GUI implementation via an interface
    public void saveGame(String fileName) throws IOException {
        try {
            String fullPath = getFolderLocation() + fileName + ".txt";
            System.out.println("Saving to..." + fullPath);
            // Create new printstream object to write to files
            PrintStream print = new PrintStream(new File(fullPath));
            print.println(Integer.toString(thegame.getGameSize())); // add the gamesize to the savefile
            print.println(Integer.toString(thegame.getLives())); // add the lives to the savefile

            for (int row = 0; row < thegame.getGameSize(); row++) {
                for (int col = 0; col < thegame.getGameSize(); col++) {
                    // Create the save game using the current state of cells - this will generate a string with
                    // row, col and the cell state and write to the file
                    print.println(Integer.toString(row) + " " + Integer.toString(col) + " " + thegame.getCellState(row,col));
                }
            }
            print.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving game.");
        }
    } //end saveGame method

    /**
     * undoMove 
     * This method utilises a stack data structure to check the previous move of the game and revert back by setting that
     * particular cell as a ?
     * void method, does not return anything
     */
    @Override
    public void undoMove() {
        if (moveHistory.size() >= 1) {
            String[] previousState = moveHistory.peek(); // Get the previous state
            // Create an Assign object and assign the move
            Assign assign
                    = new Assign(thegame, Integer.parseInt(previousState[0]), Integer.parseInt(previousState[1]), "?");
            // Assign a ? to cover the cell
            assign.assignMove("?");
            System.out.println("Move undone.");
            moveHistory.pop(); // Remove the current state to continue being able to undo moves
        } else {
            System.out.println("Make a move first!");
        }
    } //end undoMove method

    /**
     * A method to save the last move to allow the undoMove() method to function
     * @return String[] of lastMove containing 3 items, row, col, and cell state
     */
    @Override
    public void saveMoves(int row, int col) {
        String[] currMove = new String[2];
        currMove[0] = Integer.toString(row);
        currMove[1] = Integer.toString(col);
        moveHistory.push(currMove);
    } //end saveMoves method

    /**
     * loadGame
     * This method loads a previous saved game by using the scanner class to read from a file and using the readLevelFile
     * method. Improvements for file handling (list filenames on console so user knows the names)
     * @param fileName a string that is used to locate the level file
     */
    @Override
    public void loadGame(String fileName) throws FileNotFoundException {
        try {
            String fullPath = getFolderLocation() + fileName; // create a string for the full path
            System.out.println("Loading: " + fullPath); // friendly user message
            Scanner reader = new Scanner(new File(fullPath));
            int gameSize = Integer.parseInt(reader.next()); // get the game size from file
            int lives = Integer.parseInt(reader.next()); // load the lives in case player had less than 3
            // nested for loop will iterate through each cell in the 2d array
            for (int i = 0; i < gameSize; i++) {
                for (int j = 0; j < gameSize; j++) {
                    int row = Integer.parseInt(reader.next());
                    int col = Integer.parseInt(reader.next());
                    String guess = reader.next();
                    // create a new Assign object and pass in the row/col as well as the guess that has been read from the file
                    new Assign(thegame, row, col, guess);
                }
            }
            thegame.setLives(lives); // set players lives per the savefile
            System.out.println("Loaded Successfully.");
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error, file not found: " + e.getMessage());
        }
    } //end loadGame method

    /**
     * clearGame
     * This method clears the game board and any record of moves by setting the cell state back to ?
     * and clearing the moveHistory stack
     * void method does not return anything
     */
    @Override
    public void clearGame() {
        // We could simply call a new UI (UI thisUI = new UI();)
        // Lets iterate through the game size and set cell states to "?"
        for (int row = 0; row < thegame.getGameSize(); row++){
            for (int col = 0; col < thegame.getGameSize(); col++){
                Assign assign = new Assign(thegame, row, col, "?");
                // Assign a ? to cover the cell
                assign.assignMove("?");
            }
        }
        // clear the move stack
        moveHistory.clear();
        System.out.println("Game cleared.");
    } //end clearGame method

    /**
     * getSaveGame
     * Used to return the appropriate save game name. Will be expanded to provide dynamic savenames
     * @return String save game text file name
     */
    public String getFolderLocation() {
        //return "Levels/SAVE-" + thegame.getLevel().replace("Levels/", "");
        return "Levels\\SAVEGAMES\\";
    } //end method
}//end of class MinewsweeperUI