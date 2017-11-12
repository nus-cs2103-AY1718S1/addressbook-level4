package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InsurancePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.AppUtil.PanelChoice;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> personListView;

    public PersonListPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
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
        if (event.panelChoice == PanelChoice.PERSON) {
            logger.info(LogsCenter.getEventHandlingLogMessage(event));
            scrollTo(event.targetIndex);
        }
    }

    //@@author Juxarius
    @Subscribe
    private void handleInsurancePanelSelectionChangedEvent(InsurancePanelSelectionChangedEvent event) {
        personListView.getSelectionModel().clearSelection();
    }

    @Subscribe
    private void handlePersonNameClickedEvent(PersonNameClickedEvent event) {
        FilteredList<PersonCard> filtered = personListView.getItems().filtered(p ->
                p.person.getName().toString().equals(event.getName())
        );
        if (filtered.size() < 1) {
            return;
        } else {
            personListView.scrollTo(filtered.get(0));
            personListView.getSelectionModel().select(filtered.get(0));
        }
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
