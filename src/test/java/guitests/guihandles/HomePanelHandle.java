package guitests.guihandles;

import javafx.scene.Node;

// @@author johnweikangong
/**
 * A handler for the {@code HomePanel} of the UI
 */
public class HomePanelHandle extends NodeHandle<Node> {

    public static final String HOME_PANEL_ID = "#homePanel";

    public HomePanelHandle(Node homePanelNode) {
        super(homePanelNode);
    }

    /**
     * Returns the HomePanel node.
     */
    public Node getHomePanelNode() {
        return getRootNode();
    }
}
