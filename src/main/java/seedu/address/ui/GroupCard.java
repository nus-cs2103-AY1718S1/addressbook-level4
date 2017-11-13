package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.group.ReadOnlyGroup;

//@@author Procrastinatus
/**
 * An UI component that displays information of a {@code Person}.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final ReadOnlyGroup group;

    @FXML
    private Label groupName;
    @FXML
    private Label groupId;

    public GroupCard(ReadOnlyGroup group, int displayedIndex) {
        super(FXML);
        this.group = group;
        groupId.setText(displayedIndex + ". ");
        bindListeners(group);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyGroup group) {
        groupName.textProperty().bind(Bindings.convert(group.nameProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GroupCard)) {
            return false;
        }

        // state check
        GroupCard card = (GroupCard) other;
        return groupId.getText().equals(card.groupId.getText())
                && group.equals(card.group);
    }
}
