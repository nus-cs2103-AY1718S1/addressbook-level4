package seedu.address.ui;

import javafx.scene.layout.Region;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seedu.address.model.person.Group;

public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    public final Group group;

    @FXML
    private Label name;

    public GroupCard (Group group){
        super(FXML);
        this.group = group;
        name.setText(group.getGroupName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        GroupCard card = (GroupCard) other;
        return name.getText().equals(card.name.getText())
                && group.equals(card.group);
    }
}
