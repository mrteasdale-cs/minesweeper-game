import javax.swing.*;
import java.awt.*;
/**
 * This is the class for the GUI Label creation which can then be added to the main GUI control class
 * @author Myran Teasdale
 * @version v1.3 Minesweeper Game (GUI Edition)
 */
public class MinesweeperGUILabels {

    // instance variables set to private to prevent access from other classes
    private JLabel labelTitle;
    private JLabel livesTitleLabel;
    private JLabel livesLabel;
    private JLabel modeTitleLabel;
    private JLabel modeLabel;
    private JLabel gameStatusTitleLabel;
    private JLabel gameStatusLabel;


    /**
     * The labels constructor used to instantiate an instance of the createlabels method
     */
    public MinesweeperGUILabels() {
        this.createLabels();
    }

    /**
     * This method creates all the labels for the game
     */
    public void createLabels() {
        Font gameFont = new Font("Consolas", Font.BOLD, 16);
        Font gameOptions = new Font("Consolas", Font.BOLD, 24);
        labelTitle = new JLabel("Mr Tea's Minesweeper!");
        labelTitle.setIcon(new ImageIcon("Images/sweeper-happy.png"));
        labelTitle.setFont(gameFont);
        labelTitle.setPreferredSize(new Dimension(280, 50));

        // set lives title
        livesTitleLabel = new JLabel("Lives: ");
        livesTitleLabel.setFont(gameFont);

        // set lives to 3
        livesLabel = new JLabel("3");
        livesLabel.setFont(gameOptions);
        livesLabel.setForeground(GameColours.LIVES_TEXT_COLOUR);

        // set the mode title label
        modeTitleLabel = new JLabel("Mode: ");
        modeTitleLabel.setFont(gameFont);

        // set the mode label to G for guess as defauilt
        modeLabel = new JLabel("G");
        modeLabel.setFont(gameOptions);
        modeLabel.setForeground(GameColours.GAMESTATUS_TEXT_COLOUR);

        //set game status label
        gameStatusTitleLabel = new JLabel("Status: ");
        gameStatusTitleLabel.setFont(gameFont);
        gameStatusTitleLabel.setForeground(Color.BLACK);

        //set game status label
        gameStatusLabel = new JLabel("Make a move first");
        gameStatusLabel.setFont(gameFont);
        gameStatusLabel.setForeground(GameColours.GAMESTATUS_TEXT_COLOUR);

    }

    // accessors/getters

    /**
    * This method returns the label title text
    */
    public JLabel getLabelTitle() {
        return labelTitle;
    }
    /**
    * This method returns the lives label
    */
    public JLabel getLivesTitleLabel() {
        return livesTitleLabel;
    }
    /**
    * This method returns the actual number of lives
    */
    public JLabel getLivesLabel() {
        return livesLabel;
    }
    /**
    * This method returns the mode label text
    */
    public JLabel getModeTitleLabel() {
        return modeTitleLabel;
    }
    /**
    * This method returns the actual mode 'M' or 'G' - mine or guess
    */
    public JLabel getModeLabel() {
        return modeLabel;
    }

    public JLabel getGameStatusTitleLabel() { return gameStatusTitleLabel; }

    public JLabel getGameStatusLabel() { return gameStatusLabel; }

    // mutators/setters
    /**
    * This method sets the mode text colour on the GUI
    */
    public void setModeLabelColor(int red, int green, int blue) {
        modeLabel.setForeground(new Color(red, green, blue));
    }
    /**
    * This method sets the text of the mode label (M or G) - used in the main GUI Control class
    */
    public void setModeLabel(String newLabel) {
        modeLabel.setText(newLabel);
    }
    /**
    * This method sets the number of lives - used in the control class to decrement and reset the number of lives
    */
    public void setLivesLabel(String newLives) {
        livesLabel.setText(newLives);
    }
    /**
     * This method sets the game status - used to add game messages
     */
    public void setGameStatusLabel(String newStatus) {
        gameStatusLabel.setText(newStatus);
    }

}
