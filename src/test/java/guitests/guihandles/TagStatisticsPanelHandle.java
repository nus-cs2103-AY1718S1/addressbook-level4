package guitests.guihandles;

import javafx.scene.Node;

// @@author Valerieyue
/**
 * A handler for the {@code TagStatisticsPanel} of the UI
 */
public class TagStatisticsPanelHandle extends NodeHandle<Node> {

    public static final String TAG_STATISTICS_PANEL_ID = "#tagStatisticsPanel";

    public TagStatisticsPanelHandle(Node tagStatisticsPanelNode) {
        super(tagStatisticsPanelNode);
    }

    /**
     * Returns the TagStatisticsPanel node.
     */
    public Node getTagStatisticsPanelNode() {
        return getRootNode();
    }
}
