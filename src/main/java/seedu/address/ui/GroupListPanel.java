//@@ author hxy0229
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
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.GroupPanelSelectionChangedEvent;
import seedu.address.model.group.Group;

/**
 * Panel containing the list of persons.
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupListPanel.class);

    @FXML
    private ListView<GroupCard> groupListView;

    public GroupListPanel(ObservableList<Group> groupList) {
        super(FXML);
        setConnections(groupList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Group> groupList) {
        ObservableList<GroupCard> mappedList = EasyBind.map(
                groupList, (group) -> new GroupCard(group, groupList.indexOf(group) + 1));
        groupListView.setItems(mappedList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        groupListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in event list panel changed to : '" + newValue + "'");
                        raise(new GroupPanelSelectionChangedEvent(newValue));
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

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code EventCard}.
     */
    class GroupListViewCell extends ListCell<GroupCard> {

        @Override
        protected void updateItem(GroupCard group, boolean empty) {
            super.updateItem(group, empty);

            if (empty || group == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(group.getRoot());
            }
        }
    }

}