package guitests.guihandles;

import javafx.scene.control.TextArea;

/**
 * A handler for the {@code ResultDisplay} of the UI
 */
public class ResultDisplayHandle extends NodeHandle<TextArea> {

    public static final String RESULT_DISPLAY_ID = "#resultDisplay";

    public ResultDisplayHandle(TextArea resultDisplayNode) {
        super(resultDisplayNode);
    }

    /**
     * Returns the text in the result display.
     */
    public String getText() {
        return getRootNode().getText();
    }

    public String getPromptText() {
        return getRootNode().getPromptText();
    }

    /**
     * Returns true if the text area is focused.
     */
    public boolean isFocused() {
        return getRootNode().isFocused();
    }
}
