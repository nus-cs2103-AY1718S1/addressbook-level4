package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code GraphDisplay} of the UI.
 */
public class GraphDisplayHandle extends NodeHandle<Node> {

    public static final String DISPLAY_ID = "#graphDisplay";

    public GraphDisplayHandle(Node graphDisplayNode) {
        super(graphDisplayNode);
    }
}
