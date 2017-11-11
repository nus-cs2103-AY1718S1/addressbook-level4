package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;


//@@author Pengyuz
/**
 * Restore a person identified using it's last displayed index or name from the recycle bin.
 */
public class BinrestoreCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bin-restore";
    public static final String MESSAGE_SUCCESS = "Resotred";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Restore the person in bin to address book";
    private ArrayList<Index> targets;
    private boolean isVaild = true;
    private boolean isEmpty = false;

    public BinrestoreCommand(ArrayList<Index> targets) {
        this.targets = targets;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastshownlist = model.getRecycleBinPersonList();
        ArrayList<ReadOnlyPerson> personstodelete = new ArrayList<>();

        for (Index s: targets) {
            if (s.getZeroBased() >= lastshownlist.size()) {
                isVaild = false;
            } else {
                personstodelete.add(lastshownlist.get(s.getZeroBased()));
                isEmpty = true;
            }
        }

        if (isVaild && isEmpty) {
            try {
                model.restorePerson(personstodelete);
                model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
                Index lastIndex = new Index(model.getFilteredPersonList().size() - 1);
                EventsCenter.getInstance().post(new JumpToListRequestEvent(lastIndex));
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
