package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author mavistoh
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be positive integers in ascending order, separated by a comma)\n"
            + "Example: " + COMMAND_WORD + " 1, 4";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: ";

    private final Index[] targetIndexes;

    public DeleteCommand(Index... targetIndex) {
        this.targetIndexes = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (Index targetIndex : targetIndexes) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        if (targetIndexes.length > 1) {
            for (int i = 1; i < targetIndexes.length; i++) {
                if (targetIndexes[i].getZeroBased() < targetIndexes[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_ORDER_PERSONS_INDEX);
                } else if (targetIndexes[i].getZeroBased() == targetIndexes[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_REPEATED_INDEXES);
                }
            }
        }

        ReadOnlyPerson[] personsToDelete = new ReadOnlyPerson[targetIndexes.length];
        ReadOnlyPerson personToDelete;
        String[] personDeleteMessage = new String[targetIndexes.length];
        StringBuilder deleteMessage = new StringBuilder();

        for (int i = (targetIndexes.length - 1); i >= 0; i--) {
            int target = targetIndexes[i].getZeroBased();
            personToDelete = lastShownList.get(target);
            personsToDelete[i] = personToDelete;
            personDeleteMessage[i] = MESSAGE_DELETE_PERSON_SUCCESS + personToDelete;
        }

         try {
            model.deletePerson(personsToDelete);
         } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
         }

        for (String message : personDeleteMessage) {
            deleteMessage.append(message);
            deleteMessage.append("\n");
        }

        return new CommandResult(deleteMessage.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && Arrays.equals(this.targetIndexes, ((DeleteCommand) other).targetIndexes)); // state check
    }
}
