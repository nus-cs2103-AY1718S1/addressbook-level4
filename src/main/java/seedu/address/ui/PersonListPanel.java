package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import org.fxmisc.easybind.EasyBind;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ChangeListingUnitEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.SortListRequestEvent;
import seedu.address.model.ListingUnit;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.Comparator;
import java.util.logging.Logger;

/**
 * Panel containing the list of info.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private ObservableList<ReadOnlyPerson> personList;

    @FXML
    private ListView<PersonCard> personListView;


    public PersonListPanel(ObservableList<ReadOnlyPerson> infoList) {
        super(FXML);
        this.personList = infoList;
        setConnections(infoList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> infoList) {

        ObservableList<PersonCard> mappedList = EasyBind.map(
                infoList, (person) -> new PersonCard(person, infoList.indexOf(person) + 1));

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

    @Subscribe
    private void handleChangeListingUnitEvent(ChangeListingUnitEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(personList);
    }

    @Subscribe
    private void handleSortListRequestEvent(SortListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        SortedList<ReadOnlyPerson> sortedList;

        switch (ListingUnit.getCurrentListingUnit()) {
        case ADDRESS:
            sortedList = new SortedList<ReadOnlyPerson>(personList, new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
                    return firstPerson.getAddress().value.compareTo(secondPerson.getAddress().value);
                }
            });
            break;

        case PHONE:
            sortedList = new SortedList<ReadOnlyPerson>(personList, new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
                    return firstPerson.getPhone().value.compareTo(secondPerson.getPhone().value);
                }
            });
            break;

        case EMAIL:
            sortedList = new SortedList<ReadOnlyPerson>(personList, new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
                    return firstPerson.getEmail().value.compareTo(secondPerson.getEmail().value);
                }
            });
            break;

        default:
            sortedList = new SortedList<ReadOnlyPerson>(personList, new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson firstPerson, ReadOnlyPerson secondPerson) {
                    return firstPerson.getName().fullName.compareTo(secondPerson.getName().fullName);
                }
            });
            break;
        }
        setConnections(sortedList);
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
