import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *  * This utility class handles all the colours in the game to allow easy changing of game cells, display panels
 *  * and borders. Could be extended to add other game colours such as adding themes etc.
 * @author M Teasdale
 * @version 25/6/24
 */
public class GameColours {
    public static final Color BUTTON_BORDER = new Color(212, 212, 212);
    public static final Color CONTROL_BUTTON_COLOUR = new Color(241, 241, 241);
    public static final Color GAME_BUTTON_COLOUR = new Color(75, 75, 75);
    public static final Color HOVER_COLOUR = new Color(152, 195, 255);
    public static final Color DISPLAY_PANEL = new Color(224, 236, 255);
    public static final Color STATUS_PANEL_COLOUR = new Color(228, 228, 228);
    public static final Color CLICKED_BUTTON = new Color(255, 102, 102);
    public static final Color LIGHT_TEXT_COLOUR = new Color(242, 242, 242);
    public static final Color DARK_TEXT_COLOUR = new Color(52, 52, 52);
    public static final Color GAMESTATUS_TEXT_COLOUR = new Color(0, 112, 176);
    public static final Color LIVES_TEXT_COLOUR = new Color(2, 162, 72);
    public static final Color ALERT_TEXT_COLOUR = new Color(255, 30, 30);

    // Game board colours
    public static final Color MINE_CELL_COLOUR_1 = new Color(255, 46, 46);
    public static final Color MINE_CELL_COLOUR_2 = new Color(250, 250, 250);
    public static final Color MINE_CELL_COLOUR_3 = new Color(210, 255, 200);
    public static final Color MINE_CELL_COLOUR_4 = new Color(233, 255, 190);
    public static final Color MINE_CELL_COLOUR_5 = new Color(255, 245, 189);
    public static final Color MINE_CELL_COLOUR_6 = new Color(255, 216, 161);
    public static final Color MINE_CELL_COLOUR_7 = new Color(253, 139, 139);
    public static final Color MINE_CELL_COLOUR_8 = new Color(251, 107, 107);

    /**
     * This method allows the game to grab the colour of a button based on the underlying state of the
     * slot/cell. Allows easier maintenance of the colours as this method is used in the loadGame and main function
     * of the buttons
     * @param state is the underlying state of a cell i.e. M, -, 0, 1 etc
     * @return new Color object
     */
    public static Color getCellColour(String state) {
        // uses hashmaps
        Map<String, Color> buttonColourMap = new HashMap<>();
        Color result = new Color(255,255,255);
        // Add the colours to the buttonColourMap HashMap strcture
        // Could add these colours to the game colours class for better maintainability
        buttonColourMap.put("M", GameColours.MINE_CELL_COLOUR_1);
        buttonColourMap.put("-", GameColours.MINE_CELL_COLOUR_2);
        buttonColourMap.put("0", GameColours.MINE_CELL_COLOUR_3);
        buttonColourMap.put("1", GameColours.MINE_CELL_COLOUR_4);
        buttonColourMap.put("2", GameColours.MINE_CELL_COLOUR_5);
        buttonColourMap.put("3", GameColours.MINE_CELL_COLOUR_6);
        buttonColourMap.put("4", GameColours.MINE_CELL_COLOUR_7);
        buttonColourMap.put("5", GameColours.MINE_CELL_COLOUR_8);
        // iterate through the hashmap of colours and return the value based on the state parameter
        for (Map.Entry<String, Color> entry : buttonColourMap.entrySet() )
        {
            if (state.equals(entry.getKey())) {
                result = entry.getValue();
            }
        }
        return result;
    } // end method

}
