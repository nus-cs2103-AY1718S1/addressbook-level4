package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.TagPanelSelectionChangedEvent;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of persons.
 */
public class TagListPanel extends UiPart<Region> {
    private static final String FXML = "TagListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TagListPanel.class);

    @FXML
    private ListView<TagCard> tagListView;

    public TagListPanel(ObservableList<Tag> tagList) {
        super(FXML);
        setConnections(tagList);
        registerAsAnEventHandler(this);
    }

    public void setVisible(boolean isVisible) {
        tagListView.setVisible(isVisible);
    }

    private void setConnections(ObservableList<Tag> tagList) {
        ObservableList<TagCard> mappedList = EasyBind.map(
                tagList, (tag) -> new TagCard(tag, tagList.indexOf(tag) + 1));
        tagListView.setItems(mappedList);
        tagListView.setCellFactory(listView -> new TagListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        tagListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in person list panel changed to : '" + newValue + "'");
                        raise(new TagPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code TagCard}.
     */
    class TagListViewCell extends ListCell<TagCard> {

        @Override
        protected void updateItem(TagCard tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(tag.getRoot());
            }
        }
    }

}
