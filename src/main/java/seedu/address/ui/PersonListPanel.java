package seedu.address.ui;

import java.util.Set;
import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DeselectEvent;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.PersonPanelDeselectionEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private static final int SCROLL_INCREMENT = 11;
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<PersonCard> personListView;

    @FXML
    private ScrollBar personListViewScrollBar;

    public PersonListPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);

        setPersonListViewScrollBar();
        setConnections(personList);
        registerAsAnEventHandler(this);
    }

    //@@author ritchielq
    /**
     * Scrolls one page down
     */
    public void scrollDown() {
        Platform.runLater(() -> {
            if (personListViewScrollBar == null) {
                setPersonListViewScrollBar();
            }

            /*
             * Changing unit increment with setUnitIncrement() does not effect amount scrolled with increment()
             * Using loop as a workaround.
             */
            for (int i = 0; i < SCROLL_INCREMENT; i++) {
                personListViewScrollBar.increment();
            }
        });
    }

    /**
     * Scrolls one page up
     */
    public void scrollUp() {
        Platform.runLater(() -> {
            if (personListViewScrollBar == null) {
                setPersonListViewScrollBar();
            }

            /*
             * Changing unit increment with setUnitIncrement() does not effect amount scrolled with increment()
             * Using loop as a workaround.
             */
            for (int i = 0; i < SCROLL_INCREMENT; i++) {
                personListViewScrollBar.decrement();
            }
        });
    }

    //@@author
    /**
     * Initializes personListViewScrollBar and assigns personListView's scrollbar to it
     */
    private void setPersonListViewScrollBar() {
        Set<Node> set = personListView.lookupAll(".scroll-bar");
        for (Node node: set) {
            ScrollBar bar = (ScrollBar) node;
            if (bar.getOrientation() == Orientation.VERTICAL) {
                personListViewScrollBar = bar;
            }
        }
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
                    } else {
                        raise(new PersonPanelDeselectionEvent());
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
        personListView.getSelectionModel().clearSelection();
        scrollTo(event.targetIndex);
    }

    //@@author ritchielq
    @Subscribe
    private void handleDeselectEvent(DeselectEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        personListView.getSelectionModel().clearSelection();
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
