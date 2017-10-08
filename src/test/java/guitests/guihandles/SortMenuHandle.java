package guitests.guihandles;

import javafx.scene.control.MenuButton;

/**
 * A handle to the {@code SearchField} in the GUI.
 */
public class SortMenuHandle extends NodeHandle<MenuButton> {

    public static final String SORT_MENU_ID = "#sortMenu";


    public SortMenuHandle(MenuButton sortMenuNode) {
        super(sortMenuNode);
    }

    /**
     * Enters the given Person name in the search field.
     */
    public void run(String toClick) {
        switch (toClick) {
        case "name":
            guiRobot.clickOn(50, 235);
            guiRobot.pauseForHuman();
            break;
        case "phone":
            guiRobot.clickOn(50, 250);
            guiRobot.pauseForHuman();
            break;
        case "email":
            guiRobot.clickOn(50, 280);
            guiRobot.pauseForHuman();
            break;
        case "address":
            guiRobot.clickOn(50, 310);
            guiRobot.pauseForHuman();
            break;
        default:
            break;
        }
    }
}
