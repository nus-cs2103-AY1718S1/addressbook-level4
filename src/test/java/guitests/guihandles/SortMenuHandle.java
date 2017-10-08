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
            guiRobot.clickOn(getRootNode().getItems().get(0).impl_styleableGetNode());
            guiRobot.pauseForHuman();
            break;
        case "phone":
            guiRobot.clickOn(getRootNode().getItems().get(1).impl_styleableGetNode());
            guiRobot.pauseForHuman();
            break;
        case "email":
            guiRobot.clickOn(getRootNode().getItems().get(2).impl_styleableGetNode());
            guiRobot.pauseForHuman();
            break;
        case "address":
            guiRobot.clickOn(getRootNode().getItems().get(3).impl_styleableGetNode());
            guiRobot.pauseForHuman();
            break;
        default:
            break;
        }
    }
}
