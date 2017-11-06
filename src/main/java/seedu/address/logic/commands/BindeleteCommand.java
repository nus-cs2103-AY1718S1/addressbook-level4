package seedu.address.logic.commands;

import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;



/**
 * Delete the person in bin forever
 */
public class BindeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "bin-delete";

    public static final String MESSAGE_SUCCESS = "Forever deleted.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "Delete the person in bin forever.";
    private Index persontodelete;

    public BindeleteCommand(Index in) {
        persontodelete = in;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastshownlist = model.getRecycleBinPersonList();
        if (persontodelete.getZeroBased() >= lastshownlist.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson target = lastshownlist.get(persontodelete.getZeroBased());
        try {
            model.deleteBinPerson(target);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, target));
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BindeleteCommand
                && this.persontodelete.equals(((BindeleteCommand) other).persontodelete)); // state check
    }
}
