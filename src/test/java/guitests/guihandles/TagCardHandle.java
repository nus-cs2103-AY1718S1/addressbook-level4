package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author pohjie
/**
 * Provides a handle to a tag card in the tag list panel
 */
public class TagCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TAGSTRING_FIELD_ID = "#tagString";

    private final Label idLabel;
    private final Label tagStringLabel;

    public TagCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.tagStringLabel = getChildNode(TAGSTRING_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTagString() {
        return tagStringLabel.getText();
    }
}
