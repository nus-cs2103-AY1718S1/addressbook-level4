//@@author conantteo
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a {@code groupLabel} in the GroupListPanel.
 */
public class GroupLabelHandle extends NodeHandle<Node> {
    private static final String GROUP_FIELD_ID = "#groupName";

    private final Label groupLabel;

    private boolean isGroupLabelClicked = false;

    public GroupLabelHandle(Node labelNode) {
        super(labelNode);
        this.isGroupLabelClicked = false;
        this.groupLabel = getChildNode(GROUP_FIELD_ID);
    }

    // isGroupLabelClicked returns true after guiRobot has click the rootNode
    @Override
    public void click() {
        guiRobot.clickOn(getRootNode());
        this.isGroupLabelClicked = true;
    }

    public boolean isClicked() {
        return isGroupLabelClicked;
    }

    public String getGroupName() {
        return groupLabel.getText();
    }
}
