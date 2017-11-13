package guitests.guihandles;

import javafx.scene.control.MenuButton;

/**
 * A handle to the {@code SortMenu} in the GUI.
 */
public class SortMenuHandle extends NodeHandle<MenuButton> {

    public static final String SORT_MENU_ID = "#sortMenu";


    public SortMenuHandle(MenuButton sortMenuNode) {
        super(sortMenuNode);
    }

    /**
     * Clicks on the sort menu item {@code toClick} in the list.
     */
    public void run(String toClick) {
        switch (toClick) {
        case "name":
            guiRobot.clickOn(850, 90);
            guiRobot.pauseForHuman();
            break;
        case "phone":
            guiRobot.clickOn(850, 120);
            guiRobot.pauseForHuman();
            break;
        case "email":
            guiRobot.clickOn(850, 150);
            guiRobot.pauseForHuman();
            break;
        case "address":
            guiRobot.clickOn(850, 180);
            guiRobot.pauseForHuman();
            break;
        default:
            break;
        }
    }

    /**
     * Returns style of sort menu.
     */
    public String getStyle() {
        return getRootNode().getStyle();
    }
}
