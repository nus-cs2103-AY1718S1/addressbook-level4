package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookAccessChangedEvent;
import seedu.address.commons.events.ui.AccessCountDisplayToggleEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ToggleBrowserPanelEvent;
import seedu.address.logic.Logic;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> personListView;

    private ObservableList<PersonCard> mappedListWithAccessCount;
    private ObservableList<PersonCard> mappedListWithoutAccessCount;

    public PersonListPanel(Logic logic) {
        super(FXML);
        setConnections(logic.getFilteredPersonList(), logic);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList, Logic logic) {
        mappedListWithAccessCount = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1,
                         true, logic));
        mappedListWithoutAccessCount = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1,
                         false, logic));
        personListView.setItems(mappedListWithAccessCount);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        personListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        EventsCenter.getInstance().post(new ToggleBrowserPanelEvent());
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                        updateAccessCount(oldValue, newValue);
                    }
                });
    }

    //@@author Zzmobie
    /**
     * This function updates the access counts only when necessary. The access count should only be incremented
     * when the card selected is a new card, and when the old card selected was not null. The need for the latter
     * condition is a result of the way edit commands affect the selected person panel.
     * @param oldValue Previous value for the PersonCard object
     * @param newValue New value for the PersonCard object that has changed.
     */
    private void updateAccessCount(PersonCard oldValue, PersonCard newValue) {
        if (oldValue == null || oldValue.person.getName() != newValue.person.getName()) {
            raise(new AddressBookAccessChangedEvent(newValue.person));
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

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    //@@author Zzmobie
    @Subscribe
    private void handleAccessCountDisplayToggleEvent(AccessCountDisplayToggleEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event) + event.isDisplayed());
        if (event.isDisplayed()) {
            personListView.setItems(mappedListWithAccessCount);
        } else {
            personListView.setItems(mappedListWithoutAccessCount);
        }
        personListView.refresh();
    }

    //@@author
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
