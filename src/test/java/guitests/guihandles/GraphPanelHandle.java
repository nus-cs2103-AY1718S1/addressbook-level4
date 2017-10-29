package guitests.guihandles;

import javafx.scene.Node;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Provides a handle to the graph of a person in the person list panel.
 */
public class GraphPanelHandle extends NodeHandle<Node> {

    public static final String GRAPH_DISPLAY_ID = "#lineChart";
    private ReadOnlyPerson person;

    public GraphPanelHandle(Node graphPanelNode) {
        super(graphPanelNode);
    }

    /**
     * Returns the graph in the graph panel display.
     */
    public void getGraph() {
    // TODO: get the graph of the person
    }
}
