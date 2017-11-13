package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_INDEX_ALL;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the list of person identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX [INDEX]...(must be positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 4 2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person:\n";

    private ArrayList<Index> targetIdxs;

    public DeleteCommand(ArrayList<Index> targetIndex) {
        this.targetIdxs = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ArrayList<Integer> executableIdx = new ArrayList<>();
        ArrayList<Integer> unExeIdx = new ArrayList<>();
        Boolean hasExecutableIdx = false;
        for (Index idx : targetIdxs) {
            int intIdx = idx.getZeroBased();
            if (intIdx < lastShownList.size()) {
                executableIdx.add(intIdx);
                hasExecutableIdx = true;
            } else {
                unExeIdx.add(intIdx);
            }
        }
        if (!hasExecutableIdx) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_INVALID_PERSON_INDEX_ALL);
        }
        ArrayList<ReadOnlyPerson> toDeletePerson = new ArrayList<>();
        for (int idx : executableIdx) {
            toDeletePerson.add(lastShownList.get(idx));
        }
        for (int i = 0; i < executableIdx.size(); i++) {
            try {
                model.deletePerson(toDeletePerson.get(i));
                model.propagateToGroup(toDeletePerson.get(i), null, this.getClass());
            } catch (PersonNotFoundException e) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(getSb(toDeletePerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIdxs.equals(((DeleteCommand) other).targetIdxs)); // state check
    }

    /**
     * Return a String
     * @param persons to be deleted
     * @return a String with all details listed
     */
    public static String getSb(ArrayList<ReadOnlyPerson> persons) {
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_DELETE_PERSON_SUCCESS);

        appendPersonList(sb, persons);
        return sb.toString();
    }
}
