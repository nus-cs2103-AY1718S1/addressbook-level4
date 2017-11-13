package guitests.guihandles;

import javafx.scene.control.TextArea;

//@@author awarenessxz
/**
 * A handler for the {@code MessageDisplay} of EmailMessageDisplay of the UI
 */
public class EmailMessageDisplayHandle extends NodeHandle<TextArea> {
    public static final String MESSAGE_DISPLAY_ID = "#messageArea";

    public EmailMessageDisplayHandle(TextArea messageDisplayNode) {
        super(messageDisplayNode);
    }

    /**
     * Returns the text in the message display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
