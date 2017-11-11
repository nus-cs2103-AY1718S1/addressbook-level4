package guitests.guihandles;

import javafx.scene.Node;

//@@author nahtanojmil
/**
 * Provides a handle to the homePanel.
 */
public class HomePanelHandle extends NodeHandle<Node> {

    public static final String HOMEPANEL_DISPLAY_ID = "#homePanelPlaceholder";

    public HomePanelHandle(Node homePanelNode) {
        super(homePanelNode);
    }

}
