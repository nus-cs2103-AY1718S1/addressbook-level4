package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class GroupCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#groupId";
    private static final String NAME_FIELD_ID = "#groupName";

    private final Label idLabel;
    private final Label nameLabel;

    public GroupCardHandle(Node cardNode) {
        super(cardNode);
        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

}
