package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import seedu.address.ui.CommandBox;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class CommandBoxHandle extends NodeHandle<TextField> {

    public static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(TextField commandBoxNode) {
        super(commandBoxNode);
    }

    /**
     * Returns the text in the command box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    //@@author joanneong
    /**
     * Enters the given input in the {@code CommandBox} without executing the input and
     * without using autocompletion.
     */
    public void enterInputWithoutAutocompletion(String input) {
        click();
        guiRobot.interact(() -> getRootNode().setText(input));
    }

    /**
     * Enters the given input in the {@code CommandBox} without executing the input and
     * chooses the first option in the auto-complete suggestions.
     *
     * Note that the input is not executed.
     */
    public void enterInput(String input) {
        enterInputWithoutAutocompletion(input);

        guiRobot.type(KeyCode.TAB);
    }

    //@@author
    /**
     * Enters the given command in the Command Box and presses enter.
     * @return true if the command succeeded, false otherwise.
     */
    public boolean run(String command) {
        click();
        guiRobot.interact(() -> getRootNode().setText(command));
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);

        return !getStyleClass().contains(CommandBox.ERROR_STYLE_CLASS);
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
