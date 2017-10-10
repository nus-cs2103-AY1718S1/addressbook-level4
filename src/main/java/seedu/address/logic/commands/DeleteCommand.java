package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_2 = "remove";
    public static final String COMMAND_WORD_3 = "-";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final StringBuilder MESSAGE_DELETE_PERSON_FAIL= new StringBuilder(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted";
    private boolean allvalid=true;
    private int num_of_invalid=0;

    private final ArrayList<Index> targetIndex;

    public DeleteCommand(ArrayList<Index> targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ArrayList<ReadOnlyPerson> personstodelete= new ArrayList<ReadOnlyPerson>();
        ArrayList<Index> invalid= new ArrayList<>();
        for(Index s: targetIndex) {
            if (s.getZeroBased() >= lastShownList.size()) {
                allvalid=false;
                num_of_invalid++;
                invalid.add(s);
            }
            else {
                personstodelete.add(lastShownList.get(s.getZeroBased()));
            }
        }
        if(allvalid) {
            try {
                model.deletePerson(personstodelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            return new CommandResult(MESSAGE_DELETE_PERSON_SUCCESS);
        }
        else{
            MESSAGE_DELETE_PERSON_FAIL.append(num_of_invalid);
            for(Index s: invalid) {
              MESSAGE_DELETE_PERSON_FAIL.append(s.getOneBased());
            }
            throw new CommandException(MESSAGE_DELETE_PERSON_FAIL.toString());
        }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
