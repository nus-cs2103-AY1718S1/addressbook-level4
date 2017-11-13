package guitests.guihandles.person;

import guitests.guihandles.NodeHandle;
import javafx.scene.Node;

/**
 * Provides a handle to the {@code PersonDetailsPanel} on the right side of GUI.
 */
public class PersonDetailsPanelHandle extends NodeHandle<Node> {

    protected PersonDetailsPanelHandle(Node rootNode) {
        super(rootNode);
    }
}
