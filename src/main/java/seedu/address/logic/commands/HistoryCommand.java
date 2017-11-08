package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String COMMAND_ALIAS = "his";
    public static final String MESSAGE_SUCCESS = "Entered commands (from most recent to earliest):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    @Override
    public CommandResult execute() {
        List<String> previousCommands = history.getHistory();

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        Collections.reverse(previousCommands);
        return new CommandResult(String.format(MESSAGE_SUCCESS, HistoryCommand.getHistoryText(previousCommands)));
    }

    // @@author donjar

    /**
     * Returns the text displayed when executing the `history` command.
     * @param previousCommands a list containing the previous commands
     * @return the text displayed when executing the `history` command
     */
    public static String getHistoryText(List<String> previousCommands) {
        StringBuilder historyText = new StringBuilder();

        int idx = 1;
        for (String command : previousCommands) {
            historyText.append(String.format("(%1$s) %2$s", idx, command));
            if (idx != previousCommands.size()) {
                historyText.append("\n");
            }
            idx++;
        }

        return historyText.toString();
    }
    // @@author

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        requireNonNull(history);
        this.history = history;
    }
}
