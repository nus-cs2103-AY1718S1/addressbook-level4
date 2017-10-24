package guitests.guihandles;

import guitests.guihandles.event.EventListPanelHandle;
import guitests.guihandles.person.PersonDetailsPanelHandle;
import guitests.guihandles.person.PersonListPanelHandle;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.MainWindow;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {
    private static final String SWITCH_TO_CONTACTS_BUTTON = "#switchToContactsButton";
    private static final String SWITCH_TO_EVENTS_BUTTON = "#switchToEventsButton";

    private final EventsCenter eventsCenter;

    private final MainMenuHandle mainMenu;
    private final CommandBoxHandle commandBox;
    private final ResultDisplayHandle resultDisplay;
    private final StatusBarFooterHandle statusBarFooter;

    private final Node switchToContactsButton;
    private final Node switchToEventsButton;

    /**
     * The GUI elements below are not initialized in the constructor and thus not declared as {@code final}.
     * This is because they are not loaded by default at the initial stage or during a certain stage.
     */
    private PersonListPanelHandle personListPanel = null;
    private EventListPanelHandle eventListPanel = null;
    private PersonDetailsPanelHandle personDetailsPanel = null;

    public MainWindowHandle(Stage stage) {
        super(stage);
        eventsCenter = EventsCenter.getInstance();
        eventsCenter.registerHandler(this);

        mainMenu = new MainMenuHandle(getChildNode(MainMenuHandle.MENU_BAR_ID));
        resultDisplay = new ResultDisplayHandle(getChildNode(ResultDisplayHandle.RESULT_DISPLAY_ID));
        commandBox = new CommandBoxHandle(getChildNode(CommandBoxHandle.COMMAND_INPUT_FIELD_ID));
        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        statusBarFooter = new StatusBarFooterHandle(getChildNode(StatusBarFooterHandle.STATUS_BAR_PLACEHOLDER));

        switchToContactsButton = getChildNode(SWITCH_TO_CONTACTS_BUTTON);
        switchToEventsButton = getChildNode(SWITCH_TO_EVENTS_BUTTON);
    }

    /**
     * Clicks the button to switch the left-hand-side panel to the listing of events.
     *
     * TODO: This method is not ready for use.
     */
    public void activateEventListPanel(MainWindow mainWindow) {
        guiRobot.clickOn(switchToEventsButton, MouseButton.PRIMARY);
        eventListPanel = new EventListPanelHandle(getChildNode(EventListPanelHandle.EVENT_LIST_VIEW_ID));
        personListPanel = null;
    }

    /**
     * Clicks the button to switch the left-hand-side panel to the listing of events.
     *
     * TODO: This method is not ready for use.
     */
    public void activatePersonListPanel(MainWindow mainWindow) {
        guiRobot.clickOn(switchToContactsButton);
        eventListPanel = null;
        personListPanel = new PersonListPanelHandle(getChildNode(PersonListPanelHandle.PERSON_LIST_VIEW_ID));
    }

    public EventListPanelHandle getEventListPanel() {
        return eventListPanel;
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

    private void raise(BaseEvent event) {
        eventsCenter.post(event);
    }
}
