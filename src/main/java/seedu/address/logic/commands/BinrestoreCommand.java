package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;



/**
 * Restore the person in bin to address book;
 */
public class BinrestoreCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bin-restore";

    public static final String MESSAGE_SUCCESS = "Resotred";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Restore the person in bin to address book";
    private ArrayList<Index> targets;
    private boolean allvalid = true;
    private boolean exist  = false;

    public BinrestoreCommand(ArrayList<Index> targets) {
        this.targets = targets;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastshownlist = model.getRecycleBinPersonList();
        ArrayList<ReadOnlyPerson> personstodelete = new ArrayList<>();

        for (Index s: targets) {
            if (s.getZeroBased() >= lastshownlist.size()) {
                allvalid = false;
            } else {
                personstodelete.add(lastshownlist.get(s.getZeroBased()));
                exist = true;
            }
        }

        if (allvalid && exist) {
            try {
                model.restorePerson(personstodelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            } catch (DuplicatePersonException d) {
                assert false : "the duplicate person in bin should be handled";
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BinrestoreCommand
                && this.targets.equals(((BinrestoreCommand) other).targets)); // state check
    }

}
