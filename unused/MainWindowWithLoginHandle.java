package guitests.guihandles;

import guitests.guihandles.exceptions.NodeNotFoundException;
import javafx.stage.Stage;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.LoginRequestEvent;

//@@author ReneeSeet

/**
 * When testing, this will handle the Login Panel before handling the main window.
 * Reason why this was unused: This is not a good practice as this causes increase in coupling
 * and cause the testing of other features to be dependent on the Login feature.
 */
/**
 * Provides a handle to the main menu of the app.
 */

public class MainWindowWithLoginHandle extends StageHandle {

    private PersonListPanelHandle personListPanel;
    private ResultDisplayHandle resultDisplay;
    private CommandBoxHandle commandBox;
    private StatusBarFooterHandle statusBarFooter;
    private MainMenuHandle mainMenu;
    private BrowserPanelHandle browserPanel;
    private final LoginHandle loginPanel;

    public MainWindowWithLoginHandle(Stage stage) {
        super(stage);
        loginPanel = new LoginHandle(getChildNode(LoginHandle.LOGIN_DISPLAY_ID));
        EventsCenter.getInstance().post(new LoginRequestEvent()); //unlock for system test
        try {
            personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
            resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
            commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
            statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
            mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
            browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
        } catch (NodeNotFoundException ne) {
            EventsCenter.getInstance().post(new LoginRequestEvent()); //unlock for system test
            personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
            resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
            commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
            statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));
            mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
            browserPanel = new BrowserPanelHandle(getChildNode(BrowserPanelHandle.BROWSER_ID));
        }
    }

    public PersonListPanelHandle getPersonListPanel() {
        return personListPanel;
    }

    public ResultDisplayHandle getResultDisplay() {
        return resultDisplay;
    }

    public CommandBoxHandle getCommandBox() {
        return commandBox;
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return statusBarFooter;
    }

    public MainMenuHandle getMainMenu() {
        return mainMenu;
    }

    public BrowserPanelHandle getBrowserPanel() {
        return browserPanel;
    }
}
