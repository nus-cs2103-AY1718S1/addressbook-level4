package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import org.fxmisc.easybind.EasyBind;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.NewTagColourChangedEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.logging.Logger;

public class GroupListPanel extends UiPart<Region> {

    private static final String FXML = "GroupListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    @javafx.fxml.FXML
    private ListView<PersonCard> groupListView;

    public GroupListPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        setConnections(personList);
        registerAsAnEventHandler(this);
    }
    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        groupListView.setItems(mappedList);
        groupListView.setCellFactory(listView -> new GroupListPanel.PersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
       groupListView.getSelectionModel().selectedItemProperty()
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
            groupListView.scrollTo(index);
            groupListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleNewTagColourChangedEvent(NewTagColourChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setConnections(event.getPersonsList());
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
