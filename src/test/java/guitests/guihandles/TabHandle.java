package guitests.guihandles;

import javafx.scene.Node;

//@@author kennard123661
/**
 * Provides a handle to a parcel card in the parcel list panel.
 */
public class TabHandle extends NodeHandle<Node> {
    public static final String UNDELIVERED_PARCEL_TAB_ID = "#undeliveredParcelsTab";
    public static final String DELIVERED_PARCEL_TAB_ID = "#deliveredParcelsTab";

    public TabHandle(Node cardNode) {
        super(cardNode);
    }

    public Node getTabNode() {
        return this.getRootNode();
    }
}
