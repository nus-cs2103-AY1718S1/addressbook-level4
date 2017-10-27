package guitests.guihandles;

import javafx.scene.control.ScrollPane;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class HelpOverlayHandle extends NodeHandle<ScrollPane> {

    public static final String HELP_OVERLAY_ID = "#helpOverlay";

    public HelpOverlayHandle(ScrollPane helpOverlayNode) {
        super(helpOverlayNode);
    }

    public boolean isOverlayPresent() {
        return getRootNode().isVisible();
    }
}

