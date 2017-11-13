//@@author conantteo
package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * An UI component that display the name of a {@code Tag}.
 */
public class GroupLabel extends UiPart<Region> {

    private static final String FXML = "GroupLabel.fxml";
    private final Tag tag;

    @FXML
    private HBox cardPane;
    @FXML
    private Label groupName;

    public GroupLabel(Tag tag) {
        super(FXML);
        this.tag = tag;
        initTags(tag);
        registerAsAnEventHandler(this);
    }

    private void initTags(Tag tag) {
        groupName.setText(tag.tagName);
    }

    /** Returns the tag associated with this GroupLabel **/
    public Tag getTag() {
        return this.tag;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof GroupLabel)) {
            return false;
        }
        // state check
        return tag.equals(((GroupLabel) other).tag);
    }
}
