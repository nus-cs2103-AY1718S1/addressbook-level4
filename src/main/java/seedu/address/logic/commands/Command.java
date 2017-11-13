package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    public static final String MESSAGE_GET_MORE_HELP = "For more information regarding the command, "
            + "enter 'help' (without the '   ') in the command box.";

    protected Model model;
    protected UserPrefs prefs;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    /**
     * Constructs a feedback message to summarise an operation that displayed a listing of persons.
     *
     * @param displaySize used to generate summary
     * @return summary message for persons displayed
     */
    public static String getMessageForPersonListShownSummary(int displaySize) {
        return String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, UserPrefs prefs, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.prefs = prefs;
    }
}
