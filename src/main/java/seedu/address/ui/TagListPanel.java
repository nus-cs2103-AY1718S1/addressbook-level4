package seedu.address.ui;

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.MasterTagListHasAnUnusedTagEvent;
import seedu.address.model.tag.Tag;

//author@@ nbriannl
/**
 * An UI component that displays all the {@code Tags} in the {@code AddressBook} .
 */
public class TagListPanel extends UiPart<Region> {

    private static final String FXML = "TagListPanel.fxml";
    public final ObservableList<Tag> tagList;
    private final Logger logger = LogsCenter.getLogger(TagListPanel.class);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */


    @FXML
    private FlowPane tags;

    public TagListPanel (ObservableList<Tag> tagList) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.tagList = tagList;
        initTags(tagList);
        //bindListeners(person);
    }

    private void updateTagList (ObservableList<Tag> newtagList) {
        tags.getChildren().clear();
        initTags(newtagList);
    }

    /**
     * Initializes the tags for tag list
     */
    private void initTags (ObservableList<Tag> tagList) {
        Set<Tag> tagSet = tagList.stream().collect(Collectors.toSet());
        tagSet.forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + TagColorMap.getInstance().getTagColor(tag.tagName));
            tags.getChildren().add(tagLabel);
        }
        );
    }

    /**
     *  Update the Tag List panel which has unused tags
     */
    private void updateTagListWithUnusedTag (ObservableList<Tag> tagList, Set<Tag> outdatedTags) {
        tags.getChildren().clear();
        Set<Tag> tagSet = tagList.stream().collect(Collectors.toSet());

        initTagsWithUnusedTags(outdatedTags, tagSet);
    }

    /**
     * Initializes the tags for a tag list which contains unused tags
     * and gives a different color to distinguish unused tags
     */
    private void initTagsWithUnusedTags (Set<Tag> outdatedTags, Set<Tag> tagSet) {
        tagSet.forEach(tag -> {
            if (!outdatedTags.contains(tag)) {
                Label tagLabel = new Label(tag.tagName);
                tagLabel.setStyle("-fx-background-color: " + TagColorMap.getInstance().getTagColor(tag.tagName));
                tags.getChildren().add(tagLabel);
            }
        }
        );
        outdatedTags.forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: Gray  ");
            tags.getChildren().add(tagLabel);
        }
        );

    }

    @Subscribe
    public void handleAddressBookChangedEvent (AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updateTagList(event.data.getTagList());
    }

    @Subscribe
    public void handleMasterTagListHasUnusedTagEvent (MasterTagListHasAnUnusedTagEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updateTagListWithUnusedTag(tagList, event.outdatedTags);
    }
}
