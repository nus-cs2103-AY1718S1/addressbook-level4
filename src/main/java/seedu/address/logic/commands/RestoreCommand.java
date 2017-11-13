package seedu.address.logic.commands;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.commands.PersonDeletedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author frozventus-reused
/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class RestoreCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "restore";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Restores the persons identified by the index number used in the last bin listing.\n"
            + "Parameters: INDEX (must be positive integers in ascending order, separated by a comma)\n"
            + "Example: " + COMMAND_WORD + " 1, 3";

    public static final String MESSAGE_RESTORE_PERSON_SUCCESS = "Restored Person: ";

    private final Index[] targetIndex;

    public RestoreCommand (Index... targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getRecycleBinList();
        for (Index targetIndex : targetIndex) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        if (targetIndex.length > 1) {
            for (int i = 1; i < targetIndex.length; i++) {
                if (targetIndex[i].getZeroBased() < targetIndex[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_ORDER_PERSONS_INDEX);
                } else if (targetIndex[i].getZeroBased() == targetIndex[i - 1].getZeroBased()) {
                    throw new CommandException(Messages.MESSAGE_REPEATED_INDEXES);
                }
            }
        }

        ReadOnlyPerson personToRestore;
        String[] personRestoredMessage = new String[targetIndex.length];
        StringBuilder restoreMessage = new StringBuilder();

        for (int i = (targetIndex.length - 1); i >= 0; i--) {
            int target = targetIndex[i].getZeroBased();
            personToRestore = lastShownList.get(target);
            try {
                model.restorePerson(personToRestore);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
            }
            personRestoredMessage[i] = MESSAGE_RESTORE_PERSON_SUCCESS + personToRestore;
        }

        for (String message : personRestoredMessage) {
            restoreMessage.append(message);
            restoreMessage.append("\n");
        }
        EventsCenter.getInstance().post(new PersonDeletedEvent());
        return new CommandResult(restoreMessage.toString().trim());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestoreCommand // instanceof handles nulls
                && Arrays.equals(this.targetIndex, ((RestoreCommand) other).targetIndex)); // state check
    }
}
