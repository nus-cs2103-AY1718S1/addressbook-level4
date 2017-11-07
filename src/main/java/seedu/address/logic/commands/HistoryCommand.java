package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.AutoCorrectCommand;
import seedu.address.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String COMMAND_WORD_ALIAS = "h";
    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "";
    public static final String MESSAGE_SUCCESS = "Entered commands (from most recent to earliest):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";

    private AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();

    @Override
    public CommandResult execute() {
        List<String> previousCommands = history.getHistory();

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        Collections.reverse(previousCommands);

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
        }
    }

    @Override
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        requireNonNull(history);
        this.history = history;
    }
}
