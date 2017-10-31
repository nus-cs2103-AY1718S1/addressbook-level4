package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code HomePanel} of the UI
 */
public class HelpPanelHandle extends NodeHandle<Node> {

    public static final String HELP_PANEL_ID = "#helpPanel";

    public HelpPanelHandle(Node helpPanelNode) {
        super(helpPanelNode);
    }

    /**
     * Returns the HomePanel node.
     */
    public Node getHelpPanelNode() {
        return getRootNode();
    }
}
