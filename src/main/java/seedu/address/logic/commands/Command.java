package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
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

    //@@author khooroko
    /**
     * Retrieves a person based on input for the command to be applied on.
     * @param index must be a positive integer.
     * @return the person in the specified index in the last shown list.
     */
    public ReadOnlyPerson selectPersonForCommand(Index index) throws CommandException {
        List<ReadOnlyPerson> lastShownList = ListObserver.getCurrentFilteredList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Retrieves the current person for the command to be applied on.
     */
    public ReadOnlyPerson selectPersonForCommand() throws CommandException {
        if (ListObserver.getSelectedPerson() == null) {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_SELECTED);
        }
        return ListObserver.getSelectedPerson();
    }

    /**
     * Selects the specified person if he/she exists in the list. If he/she does not, a deselection is done.
     * @param person the person to be selected.
     */
    public void reselectPerson(ReadOnlyPerson person) {
        if (ListObserver.getCurrentFilteredList().contains(person)) {
            EventsCenter.getInstance().post(new
                    JumpToListRequestEvent(ListObserver.getIndexOfPersonInCurrentList(person)));
        } else {
            model.deselectPerson();
        }
    }
}
