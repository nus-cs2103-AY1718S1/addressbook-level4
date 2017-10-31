package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.IndexRange;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Total Persons Deleted: %1$s \n%2$s";

    private final HashSet<Integer> zeroBasedTargetIndexes;

    public DeleteCommand(HashSet<Integer> zeroBasedTargetIndexes) {
        this.zeroBasedTargetIndexes = zeroBasedTargetIndexes;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (!IndexRange.areIndicesWithinBounds(zeroBasedTargetIndexes, lastShownList)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        List<ReadOnlyPerson> personToDelete = IndexRange.subList(zeroBasedTargetIndexes, lastShownList);

        try {
            model.deletePersons(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.size(),
                StringUtil.toIndexedListString(zeroBasedTargetIndexes, personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand d = (DeleteCommand) other;
        return zeroBasedTargetIndexes.equals(d.zeroBasedTargetIndexes);
    }
}
