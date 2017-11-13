package guitests.guihandles;

import javafx.scene.control.TextArea;

//@@author awarenessxz
/**
 * A handler for the {@code SubjectDisplay} of EmailMessageDisplay of the UI
 */
public class EmailSubjectDisplayHandle extends NodeHandle<TextArea> {
    public static final String SUBJECT_DISPLAY_ID = "#subjectArea";

    public EmailSubjectDisplayHandle(TextArea subjectDisplayNode) {
        super(subjectDisplayNode);
    }

    /**
     * Returns the text in the message display.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
