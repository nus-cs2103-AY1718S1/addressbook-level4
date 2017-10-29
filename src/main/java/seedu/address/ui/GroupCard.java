package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.address.model.group.ReadOnlyGroup;

/**
 * An UI component that displays information of a {@code Group}.
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    public final ReadOnlyGroup group;

    @FXML
    private HBox cardPane;
    @FXML
    private Text groupName;
    @FXML
    private Text id;
    @FXML
    private FlowPane members;

    public GroupCard(ReadOnlyGroup group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        initMembers(group);
        bindListeners(group);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Group} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyGroup group) {
        groupName.textProperty().bind(Bindings.convert(group.nameProperty()));
        group.membersProperty().addListener(((observable, oldValue, newValue) -> {
            members.getChildren().clear();
            initMembers(group);
        }));
    }

    /**
     * Initialises all members of the group into labels in flow pane
     * @param group
     */
    private void initMembers(ReadOnlyGroup group) {
        group.getGroupMembers().forEach(person ->  {
            Label memberName = new Label(person.getName().fullName);
            memberName.setStyle("-fx-background-color: black");
            members.setHgap(4);
            members.setVgap(7);
            members.getChildren().add(memberName);
        });
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
        return id.getText().equals(card.id.getText())
                && group.equals(card.group);
    }
}
