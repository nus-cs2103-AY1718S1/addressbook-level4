package seedu.address.logic.commands.person;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final List<Index> targetIndices = new ArrayList<>();

    public DeleteCommand(Index targetIndex) {
        this.targetIndices.add(targetIndex);
    }

    public DeleteCommand(List<Index> indices) {
        targetIndices.addAll(indices);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> personsToDelete = new ArrayList<>();
        int counter = 0;
        for (Index checkException : targetIndices) {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
            if (checkException.getZeroBased() - counter >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
        for (Index targetIndex : targetIndices) {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased() - counter);
            personsToDelete.add(personToDelete);

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            counter++;
        }
        StringBuilder builder = new StringBuilder();
        for (ReadOnlyPerson toAppend: personsToDelete) {
            builder.append("\n" + toAppend.toString());
        }
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, builder));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndices.equals(((DeleteCommand) other).targetIndices)); // state check
    }
}
