package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    //@@author taojiashu
    public static final String MESSAGE_CONFIRMATION = "Type 'exit' again to confirm to exit";
    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Address Book as requested ...";

    //@@author taojiashu
    @Override
    public CommandResult execute() {
        List<String> previousCommands = history.getHistory();

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_CONFIRMATION);
        }

        Collections.reverse(previousCommands);
        if (previousCommands.get(0).equals("exit")) {
            EventsCenter.getInstance().post(new ExitAppRequestEvent());
            return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        } else {
            return new CommandResult(MESSAGE_CONFIRMATION);
        }
    }

    //@@author taojiashu
    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        requireNonNull(history);
        this.history = history;
    }
    //@@author taojiashu
}
