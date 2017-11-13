package seedu.address.logic.commands;

import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author kaiyu92

/**
 * switch between the addressbook and eventbook tab
 */
public class SwitchCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switch between the addressbook and eventbook UI tab";

    public static final String MESSAGE_SUCCESS = "Switched to the other tab";

    private static final int ADDRESS_TAB = 0;
    private static final int EVENTS_TAB = 1;

    private final TabPane tabPane;

    public SwitchCommand(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        int selectedIndex = selectionModel.getSelectedIndex();
        if (selectedIndex == ADDRESS_TAB) {
            selectedIndex = EVENTS_TAB;
        } else {
            selectedIndex = ADDRESS_TAB;
        }
        selectionModel.select(selectedIndex);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
