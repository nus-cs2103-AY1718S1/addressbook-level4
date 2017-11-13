//@@author hthjthtrh
package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a group card in the group list panel.
 */
public class GroupCardHandle extends  NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String GROUPNAME_FIELD_ID = "#grpName";
    private static final String FIRSTP_FIELD_ID = "#firstGroup";
    private static final String SECONDP_FIELD_ID = "#secondGroup";
    private static final String THIRDP_FIELD_ID = "#thirdGroup";
    private static final String ELLIPSIS_FIELD_ID = "#ellipsis";

    private final Label idLabel;
    private final Label grpNameLabel;
    private final Label firstPLabel;
    private final Label secondPLabel;
    private final Label thirdPLabel;
    private final Label ellipsisLabel;

    public GroupCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.grpNameLabel = getChildNode(GROUPNAME_FIELD_ID);
        this.firstPLabel = getChildNode(FIRSTP_FIELD_ID);
        this.secondPLabel = getChildNode(SECONDP_FIELD_ID);
        this.thirdPLabel = getChildNode(THIRDP_FIELD_ID);
        this.ellipsisLabel = getChildNode(ELLIPSIS_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return grpNameLabel.getText();
    }

    public String getFirstPerson() {
        return firstPLabel.getText();
    }

    public String getSecondPerson() {
        return secondPLabel.getText();
    }

    public String getThirdPerson() {
        return thirdPLabel.getText();
    }

    public String getEllipsis() {
        return ellipsisLabel.getText();
    }

}
//@@author
