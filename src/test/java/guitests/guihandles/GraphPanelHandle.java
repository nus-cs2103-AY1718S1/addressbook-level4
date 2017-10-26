package guitests.guihandles;

import javafx.scene.control.TextArea;

/**
 * Provides a handle to the graph of a person in the person list panel.
 */
public class GraphPanelHandle extends NodeHandle<TextArea> {

    public static final String GRAPH_DISPLAY_ID = "#graphPanelPlaceholder";

    public GraphPanelHandle(TextArea graphPanelNode) {
        super(graphPanelNode);
    }
}
