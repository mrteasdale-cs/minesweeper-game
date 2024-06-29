import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/**
 * Class to implement mouselistener. used to be able to change the apperance of each cell on hover/exit. could be extended to add
 * additional functionality
 */
public class MouseHandler implements MouseListener {
    //instantiate buttons
    private final JButton cellButton;
    private final String buttonType;

    // Mouse handler constructor
    public MouseHandler(JButton cellButton, String buttonType) {
        this.cellButton = cellButton;
        this.buttonType = buttonType;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (cellButton.isEnabled()) {
            cellButton.setBackground(GameColours.CLICKED_BUTTON);
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Invoked when a mouse button has entered a component. 
     * This method will set the cell to a specific colour to highlight which button the user will press.
    */
    public void mouseEntered(MouseEvent e) {
        if (cellButton.isEnabled()) {
            if (buttonType.equals("gameButton")) {
                cellButton.setBackground(GameColours.HOVER_COLOUR);
                cellButton.setForeground(GameColours.DARK_TEXT_COLOUR);
            } else if (buttonType.equals("controlButton")) {
                cellButton.setBackground(GameColours.CLICKED_BUTTON);

            }
        }
    }

    /**
     * Invoked when the mouse exits a component. Reverts colours back to default.
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (cellButton.isEnabled()) {
            if (buttonType.equals("gameButton")) {
                cellButton.setBackground(GameColours.GAME_BUTTON_COLOUR);
                cellButton.setForeground(GameColours.LIGHT_TEXT_COLOUR);
            } else if (buttonType.equals("controlButton")) {
                cellButton.setBackground(GameColours.CONTROL_BUTTON_COLOUR);
            }
        }
    }
}
