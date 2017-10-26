package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {
    protected Model model;
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
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }

    /**
     * Retrieves a person based on input for the command to be applied on.
     * @param index can be a positive integer or a null object.
     * @return currently selected person in the model if no index is provided, or the person in the specified index in
     * the last shown list
     */
    public ReadOnlyPerson selectPerson(Index index) throws CommandException {
        ReadOnlyPerson selectedPerson;
        if (index == null) {
            if (model.getSelectedPerson() == null) {
                throw new CommandException(Messages.MESSAGE_NO_PERSON_SELECTED);
            } else {
                selectedPerson = model.getSelectedPerson();
            }
        } else {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            selectedPerson = lastShownList.get(index.getZeroBased());
        }
        return selectedPerson;
    }
}
