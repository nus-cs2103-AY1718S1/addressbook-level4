package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handle for the {@code InformationPanel}.
 */
public class InformationPanelHandle extends NodeHandle<Node> {
    public static final String INFORMATION_PANEL_PLACEHOLDER = "#informationPanelPlaceholder";

    public InformationPanelHandle(Node informationPanelNode) {
        super(informationPanelNode);
    }

    public Node getInformationPanelNode() {
        return getRootNode();
    }
}
