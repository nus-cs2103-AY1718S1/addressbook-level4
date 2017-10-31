package guitests.guihandles;

import javafx.scene.control.TextField;
/**
 * Provides a handle to the Person Panel
 */

//@@author nicholaschuayunzhi
public class CommandBoxHintsHandle extends NodeHandle<TextField> {
    public static final String COMMAND_BOX_HINTS_ID = "#commandBoxHints";
    private TextField commandBoxHints;

    public CommandBoxHintsHandle(TextField commandBoxHintsNode) {
        super(commandBoxHintsNode);
        this.commandBoxHints = getChildNode(COMMAND_BOX_HINTS_ID);
    }

    public String getText() {
        return commandBoxHints.getText();
    }

    public TextField getTextField() {
        return commandBoxHints;
    }
}
