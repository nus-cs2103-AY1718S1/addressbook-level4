package seedu.address.ui;

import static seedu.address.logic.commands.FontSizeCommand.MAXIMUM_FONT_SIZE_MULTIPLIER;
import static seedu.address.logic.commands.FontSizeCommand.MINIMUM_FONT_SIZE_MULTIPLIER;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> personListView;

    private int fontSizeMultiplier;
    private ObservableList<ReadOnlyPerson> personList;

    public PersonListPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        this.personList = personList;
        fontSizeMultiplier = MINIMUM_FONT_SIZE_MULTIPLIER;
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(personList, (person) ->
                new PersonCard(person, personList.indexOf(person) + 1, fontSizeMultiplier));
        personListView.setItems(mappedList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    //@@author charlesgoh
    /**
     * Increases all person cards' font sizes in person list
     */
    public void increaseFontSize() {
        logger.info("PersonListPanel: Increasing font sizes");
        setFontSizeMultiplier(this.fontSizeMultiplier + 1);
        setConnections(personList);
    }

    /**
     * Decreases all person cards' font sizes in person list
     */
    public void decreaseFontSize() {
        logger.info("PersonListPanel: Decreasing font sizes");
        setFontSizeMultiplier(this.fontSizeMultiplier - 1);
        setConnections(personList);
    }

    /**
     * Resets all person cards' font sizes in person list
     */
    public void resetFontSize() {
        logger.info("PersonListPanel: Resetting font sizes");
        fontSizeMultiplier = MINIMUM_FONT_SIZE_MULTIPLIER;
        setConnections(personList);
    }

    /**
     * Gets integer value of font size multiplier
     */
    public int getFontSizeMultiplier() {
        return fontSizeMultiplier;
    }

    /**
     * Set integer value of font size multiplier
     */
    public void setFontSizeMultiplier(int fontSizeMultiplier) {
        // Set new font size
        this.fontSizeMultiplier = fontSizeMultiplier;

        // Restrict from minimum
        this.fontSizeMultiplier = Math.max(MINIMUM_FONT_SIZE_MULTIPLIER, this.fontSizeMultiplier);

        // Restrict from maximum
        this.fontSizeMultiplier = Math.min(MAXIMUM_FONT_SIZE_MULTIPLIER, this.fontSizeMultiplier);

        logger.info("New person font size multiplier: " + Integer.toString(this.fontSizeMultiplier));
    }

    /**
     * Handles command induced change font size event for person cards
     * @param event
     */
    @Subscribe
    private void handlePersonCardChangeFontSizeEvent (ChangeFontSizeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        switch (event.getTriggerOption()) {
        case 0:
            logger.info("Attempting to increase font size");
            increaseFontSize();
            break;
        case 1:
            decreaseFontSize();
            logger.info("Attempting to decrease font size");
            break;
        case 2:
            resetFontSize();
            logger.info("Attempting to reset font size");
            break;
        default:
            logger.info("Unable to handle change font size event. Stopping execution now");
        }
    }
    //@@author

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            personListView.scrollTo(index);
            personListView.getSelectionModel().clearAndSelect(index);
        });
    }

    /**
     * Listens for change of font size events
     * @param event
     */
    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        logger.info("Attempting to clear selection in person list view");
        Platform.runLater(personListView.getSelectionModel()::clearSelection);
    }

    @Subscribe
    private void handleBrowserPanelLocateEvent(BrowserPanelLocateEvent event) {
        logger.info("Attempting to clear selection in person list view");
        Platform.runLater(personListView.getSelectionModel()::clearSelection);
    }

    @Subscribe
    private void handleBrowserPanelNavigateEvent(BrowserPanelNavigateEvent event) {
        logger.info("Attempting to clear selection in person list view");
        Platform.runLater(personListView.getSelectionModel()::clearSelection);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
