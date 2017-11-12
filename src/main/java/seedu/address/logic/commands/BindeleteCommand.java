package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author Pengyuz
/**
 * Deletes a person identified using it's last displayed index or name from the recycle bin.
 */
public class BindeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bin-delete";

    public static final String MESSAGE_SUCCESS = "Forever deleted.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Delete the person in bin.";
    private ArrayList<Index> targets;
    private boolean isValid = true;
    private boolean isEmpty  = false;

    public BindeleteCommand(ArrayList<Index> targets) {
        this.targets = targets;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastshownlist = model.getRecycleBinPersonList();
        ArrayList<ReadOnlyPerson> personstodelete = new ArrayList<>();

        for (Index s: targets) {
            if (s.getZeroBased() >= lastshownlist.size()) {
                isValid = false;
            } else {
                personstodelete.add(lastshownlist.get(s.getZeroBased()));
                isEmpty = true;
            }
        }

        if (isValid && isEmpty) {
            try {
                model.deleteBinPerson(personstodelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BindeleteCommand
                && this.targets.equals(((BindeleteCommand) other).targets)); // state check
    }
}
