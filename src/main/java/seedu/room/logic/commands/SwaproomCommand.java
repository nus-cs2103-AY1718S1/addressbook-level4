package seedu.room.logic.commands;

import java.util.List;

import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.PersonNotFoundException;


/**
 * Swaps two residents identified using their last displayed indexes from the resident book.
 */
public class SwaproomCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "swaproom";
    public static final String COMMAND_ALIAS = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Swaps the rooms for the two residents identified by index numbers used in the last person listing.\n"
            + "Parameters: INDEX1 and INDEX2 (both must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_SWAP_PERSONS_SUCCESS = "Swapped Rooms : %1$s and %2$s";

    private final Index targetIndex1;
    private final Index targetIndex2;

    public SwaproomCommand(Index targetIndex1, Index targetIndex2) {
        this.targetIndex1 = targetIndex1;
        this.targetIndex2 = targetIndex2;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex1.getZeroBased() >= lastShownList.size()
                || targetIndex2.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person1 = lastShownList.get(targetIndex1.getZeroBased());
        ReadOnlyPerson person2 = lastShownList.get(targetIndex2.getZeroBased());

        try {
            model.swapRooms(person1, person2);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person(s) cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_SWAP_PERSONS_SUCCESS, person1.getName(), person2.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwaproomCommand // instanceof handles nulls
                && this.targetIndex1.equals(((SwaproomCommand) other).targetIndex1)
                && this.targetIndex2.equals(((SwaproomCommand) other).targetIndex2)) // state check
                || (other instanceof SwaproomCommand // instanceof handles nulls
                && this.targetIndex1.equals(((SwaproomCommand) other).targetIndex2)
                && this.targetIndex2.equals(((SwaproomCommand) other).targetIndex1)); // state check
    }
}
