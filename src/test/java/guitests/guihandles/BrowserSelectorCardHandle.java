package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

//@@author fongwz
/**
 * Provides a handle to a person card in the person list panel.
 */
public class BrowserSelectorCardHandle extends NodeHandle<Node> {
    private static final String TEXT_FIELD_ID = "#browserCardText";

    private final Label textLabel;

    public BrowserSelectorCardHandle(Node cardNode) {
        super(cardNode);

        this.textLabel = getChildNode(TEXT_FIELD_ID);
    }

    public String getBrowserTypeName() {
        return textLabel.getText();
    }
}
