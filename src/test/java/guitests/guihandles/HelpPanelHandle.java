package guitests.guihandles;

import javafx.scene.Node;

// @@author johnweikangong
/**
 * A handler for the {@code HelpPanel} of the UI
 */
public class HelpPanelHandle extends NodeHandle<Node> {

    public static final String HELP_PANEL_ID = "#helpPanel";

    public HelpPanelHandle(Node helpPanelNode) {
        super(helpPanelNode);
    }

    /**
     * Returns the HelpPanel node.
     */
    public Node getHelpPanelNode() {
        return getRootNode();
    }
}
