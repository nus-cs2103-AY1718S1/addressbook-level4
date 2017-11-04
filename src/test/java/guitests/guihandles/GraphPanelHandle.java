package guitests.guihandles;

import javafx.scene.Node;

//@@author nahtanojmil
/**
 * Provides a handle to the graph of a person in the person list panel.
 */
public class GraphPanelHandle extends NodeHandle<Node> {

    public static final String GRAPH_DISPLAY_ID = "#graphPanelPlaceholder";

    public GraphPanelHandle(Node graphPanelNode) {
        super(graphPanelNode);
    }

    /**
     * Returns the graph in the graph panel display.
     */
    public void getGraph() {
    }
}
