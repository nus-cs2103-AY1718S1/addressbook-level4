package guitests.guihandles;

import javafx.scene.control.TextArea;

//@@author awarenessxz
/**
 * A handler for the {@code RecipientsDisplay} of EmailMessageDisplay of the UI
 */
public class EmailRecipientsDisplayHandle extends NodeHandle<TextArea> {
    public static final String RECIPIENTS_DISPLAY_ID = "#recipientsArea";

    public EmailRecipientsDisplayHandle(TextArea recipientsDisplayNode) {
        super(recipientsDisplayNode);
    }

    /**
     * Returns the text in the message display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
