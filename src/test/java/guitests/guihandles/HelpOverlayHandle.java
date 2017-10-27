package guitests.guihandles;

import javafx.scene.layout.GridPane;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class HelpOverlayHandle extends NodeHandle<GridPane> {

    public static final String HELP_OVERLAY_ID = "#helpOverlay";

    public HelpOverlayHandle(GridPane helpOverlayNode) {
        super(helpOverlayNode);
    }

    public boolean isOverlayPresent() {
        return getRootNode().isVisible();
    }
}

