package guitests.guihandles;

import javafx.scene.control.TextArea;

/**
 * A handler for the {@code ResultDisplay} of the UI, in particular, the information display segment.
 */
public class InfoDisplayHandle extends NodeHandle<TextArea> {

    public static final String INFO_DISPLAY_ID = "#infoDisplay";

    public InfoDisplayHandle(TextArea infoDisplayNode) {
        super(infoDisplayNode);
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
