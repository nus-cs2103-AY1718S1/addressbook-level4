package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class TagCard extends UiPart<Region> {

    private static final String FXML = "TagListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Tag tag;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;

    public TagCard(Tag tag, int displayedIndex) {
        super(FXML);
        this.tag = tag;
        id.setText(displayedIndex + ". ");
        bindListeners(tag);
    }
    
    /**
     * Binds the individual UI elements to observe their respective {@code Tag} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Tag tag) {
        name.textProperty().bind(new SimpleStringProperty(tag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagCard)) {
            return false;
        }

        // state check
        TagCard card = (TagCard) other;
        return id.getText().equals(card.id.getText())
                && tag.equals(card.tag);
    }
}
