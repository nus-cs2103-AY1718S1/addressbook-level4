package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.TabPane;

//@@author nahtanojmil
/**
 * Provides a handle to the graph of a person in the person list panel.
 */
public class GraphPanelHandle extends NodeHandle<Node> {

    public static final String GRAPH_DISPLAY_ID = "#graphPanelPlaceholder";
    private static final String TAB_PANEL_ID = "#tabPaneGraphs";

    private final TabPane tabPane;

    public GraphPanelHandle(Node graphPanelNode) {
        super(graphPanelNode);
        this.tabPane = getChildNode(TAB_PANEL_ID);
    }

    public TabPane getTabPanel() {
        return tabPane;
    }



}
