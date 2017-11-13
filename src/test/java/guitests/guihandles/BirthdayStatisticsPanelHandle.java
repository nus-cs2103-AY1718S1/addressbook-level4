package guitests.guihandles;

import javafx.scene.Node;

// @@author Valerieyue
/**
 * A handler for the {@code BirthdayStatisticsPanel} of the UI
 */
public class BirthdayStatisticsPanelHandle extends NodeHandle<Node> {

    public static final String BIRTHDAY_STATISTICS_PANEL_ID = "#birthdayStatisticsPanel";

    public BirthdayStatisticsPanelHandle(Node birthdayStatisticsPanelNode) {
        super(birthdayStatisticsPanelNode);
    }

    /**
     * Returns the BirthdayStatisticsPanel node.
     */
    public Node getBirthdayStatisticsPanelNode() {
        return getRootNode();
    }
}
