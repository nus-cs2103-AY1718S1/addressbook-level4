package seedu.address.ui;

import static java.util.stream.Collectors.toCollection;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of nearby persons.
 */
public class NearbyPersonListPanel extends UiPart<Region> {
    private static final String FXML = "NearbyPersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NearbyPersonListPanel.class);

    private ReadOnlyPerson currentPerson;

    @FXML
    private ListView<PersonCard> nearbyPersonListView;

    public NearbyPersonListPanel(ObservableList<ReadOnlyPerson> personList, ReadOnlyPerson person) {
        super(FXML);
        currentPerson = person;
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<ReadOnlyPerson> nearbyList = personList.stream()
                .filter(readOnlyPerson -> readOnlyPerson.isSameCluster(currentPerson))
                .filter(readOnlyPerson -> !readOnlyPerson.isSameStateAs(currentPerson))
                .collect(toCollection(FXCollections::observableArrayList));
        ObservableList<PersonCard> mappedList = EasyBind.map(
                nearbyList, (person) -> new PersonCard(person, nearbyList.indexOf(person) + 1));
        nearbyPersonListView.setItems(mappedList);
        nearbyPersonListView.setCellFactory(listView -> new NearbyPersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        nearbyPersonListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new PersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            nearbyPersonListView.scrollTo(index);
            nearbyPersonListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class NearbyPersonListViewCell extends ListCell<PersonCard> {

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
