package seedu.room.logic.commands;

import static seedu.room.model.person.Room.ROOM_NOT_SET_DEFAULT;

import java.util.List;

import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.PersonNotFoundException;

//@@author sushinoya
/**
 * Swaps two residents identified using indexes from the last displayed residents list.
 */
public class SwaproomCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "swaproom";
    public static final String COMMAND_ALIAS = "sr";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Swaps the rooms for the two residents identified by index numbers used in the last person listing.\n"
            + "Parameters: INDEX1 and INDEX2 (both must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2";

    public static final String MESSAGE_SWAP_PERSONS_SUCCESS = "Swapped Rooms : %1$s and %2$s.";
    public static final String ROOMS_NOT_SET_ERROR = "Both %1$s and %2$s have not been assigned any rooms yet.";

    private final Index targetIndex1;
    private final Index targetIndex2;

    public SwaproomCommand(Index targetIndex1, Index targetIndex2) {
        this.targetIndex1 = targetIndex1;
        this.targetIndex2 = targetIndex2;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        //The index is larger than the last shown list.
        if (targetIndex1.getZeroBased() >= lastShownList.size()
                || targetIndex2.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person1 = lastShownList.get(targetIndex1.getZeroBased());
        ReadOnlyPerson person2 = lastShownList.get(targetIndex2.getZeroBased());

        //If both of the residents have not been allocated rooms, disallow swap.
        if (person1.getRoom().toString().equals(ROOM_NOT_SET_DEFAULT)
                && person2.getRoom().toString().equals(ROOM_NOT_SET_DEFAULT)) {
            throw new CommandException(String.format(ROOMS_NOT_SET_ERROR, person1.getName(), person2.getName()));
        }

        try {
            model.swapRooms(person1, person2);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person(s) cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_SWAP_PERSONS_SUCCESS, person1.getName(), person2.getName()));
    }

    @Override
    public boolean equals(Object other) { // swaproom A B is equal to swaproom B A
        return other == this // short circuit if same object
                || (other instanceof SwaproomCommand // instanceof handles nulls
                && this.targetIndex1.equals(((SwaproomCommand) other).targetIndex1)
                && this.targetIndex2.equals(((SwaproomCommand) other).targetIndex2)) // state check
                || (other instanceof SwaproomCommand // instanceof handles nulls
                && this.targetIndex1.equals(((SwaproomCommand) other).targetIndex2)
                && this.targetIndex2.equals(((SwaproomCommand) other).targetIndex1)); // state check
    }
}
