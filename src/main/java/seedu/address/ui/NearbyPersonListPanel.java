package seedu.address.ui;

import static java.util.stream.Collectors.toCollection;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NearbyPersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author khooroko
/**
 * Panel containing the list of nearby persons.
 */
public class NearbyPersonListPanel extends UiPart<Region> {
    private static final String FXML = "NearbyPersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NearbyPersonListPanel.class);

    private ReadOnlyPerson currentPerson;
    private ObservableList<ReadOnlyPerson> nearbyList;

    @FXML
    private ListView<PersonCard> nearbyPersonListView;

    public NearbyPersonListPanel(ObservableList<ReadOnlyPerson> personList, ReadOnlyPerson person) {
        super(FXML);
        currentPerson = person;
        nearbyList = personList.stream()
                .filter(readOnlyPerson -> readOnlyPerson.isSameCluster(currentPerson))
                .collect(toCollection(FXCollections::observableArrayList));
        setConnections(nearbyList);
        registerAsAnEventHandler(this);
        scrollTo(nearbyList.indexOf(currentPerson));
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        nearbyPersonListView.setItems(mappedList);
        nearbyPersonListView.setCellFactory(listView -> new NearbyPersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        nearbyPersonListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in nearby person list panel changed to : '" + newValue + "'");
                        raise(new NearbyPersonPanelSelectionChangedEvent(newValue));
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
