package guitests.guihandles;

import javafx.scene.Node;

/**
 * A handler for the {@code ProfilePanel} of the UI.
 */
public class ProfilePanelHandle extends NodeHandle<Node> {

    public static final String PROFILE_ID = "#profile";


    public ProfilePanelHandle(Node profilePanelNode) {
        super(profilePanelNode);

    }
}
