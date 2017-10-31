package seedu.address.logic.commands;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import com.google.api.services.people.v1.PeopleService;

import seedu.address.commons.core.Messages;
import seedu.address.google.OAuth;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected static CompletableFuture<PeopleService> clientFuture;

    protected Model model;
    protected CommandHistory history;
    protected UndoRedoStack undoRedoStack;

    protected OAuth oauth;
    protected ExecutorService executor;

    //@@author
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

    //@@author derrickchua
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }

    public void setOAuth (OAuth oauth) {
        this.oauth = oauth;
    }

    public void setExecutor (ExecutorService executor) {
        this.executor = executor;
    }

}
