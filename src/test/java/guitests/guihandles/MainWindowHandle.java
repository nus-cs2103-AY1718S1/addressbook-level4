package guitests.guihandles;

import guitests.guihandles.event.EventListPanelHandle;
import guitests.guihandles.person.PersonDetailsPanelHandle;
import guitests.guihandles.person.PersonListPanelHandle;
import javafx.stage.Stage;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.SwitchToContactsListEvent;
import seedu.address.commons.events.ui.SwitchToEventsListEvent;
import seedu.address.ui.MainWindow;

/**
 * Provides a handle for {@code MainWindow}.
 */
public class MainWindowHandle extends StageHandle {
    private final EventsCenter eventsCenter;

    private final MainMenuHandle mainMenu;
    private final CommandBoxHandle commandBox;
    private final ResultDisplayHandle resultDisplay;
    private final StatusBarFooterHandle statusBarFooter;

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
    }

    /**
     * Raises an {@link SwitchToEventsListEvent} event to switch the left-hand-side panel to the listing of events.
     *
     * TODO: This method is not ready for use.
     */
    public void activateEventListPanel(MainWindow mainWindow) {
        raise(new SwitchToEventsListEvent());
        eventListPanel = new EventListPanelHandle(getChildNode(EventListPanelHandle.EVENT_LIST_VIEW_ID));
        personListPanel = null;
    }

    /**
     * Raises an {@link SwitchToEventsListEvent} event to switch the left-hand-side panel to the listing of events.
     *
     * TODO: This method is not ready for use.
     */
    public void activatePersonListPanel() {
        raise(new SwitchToContactsListEvent());
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
