//@@author conantteo
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.SearchTagEvent;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of unique tags
 */
public class GroupListPanel extends UiPart<Region> {
    private static final String FXML = "GroupList.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ListView<GroupLabel> groupListView;

    public GroupListPanel(ObservableList<Tag> allTagsList) {
        super(FXML);
        bindTags(allTagsList);
    }

    /**
     * Creating bindings for each tag to each {@code GroupListViewCell} in the ListView
     * @param allTagsList is a valid list of all unique tags to be displayed.
     */
    private void bindTags(ObservableList<Tag> allTagsList) {
        ObservableList<GroupLabel> mappedList = EasyBind.map(
                allTagsList, (tag) -> new GroupLabel(tag));
        groupListView.setItems(mappedList);
        groupListView.setCellFactory(listView -> new GroupListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        groupListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in group list panel changed to : '" + newValue + "'");
                        raise(new SearchTagEvent(newValue.getTag()));
                    }
                });
    }

    /**
     * Custom {@code GroupListViewCell} that displays the graphics of a {@code GroupLabel}.
     */
    class GroupListViewCell extends ListCell<GroupLabel> {

        @Override
        protected void updateItem(GroupLabel groupLabel, boolean empty) {
            super.updateItem(groupLabel, empty);

            Platform.runLater(() -> {
                if (empty || groupLabel == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(groupLabel.getRoot());
                }

            });

        }
    }
}
