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
    public static final String COMMAND_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the last person listing.\n"
            + "Alias: " + COMMAND_ALIAS + "\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";


    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person(s):\n%1$s";

    private ArrayList<Index> targetIndexArraylist = new ArrayList<Index>();

    public DeleteCommand(ArrayList<Index> targetIndexArraylist) {
        this.targetIndexArraylist = targetIndexArraylist;
    }

    public DeleteCommand(Index targetIndex) {
        this.targetIndexArraylist.add(targetIndex);
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String result = "";
        String people = "";
        ReadOnlyPerson personToDelete = null;
        for (Index i : this.targetIndexArraylist) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            personToDelete = lastShownList.get(i.getZeroBased());

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }

            people += personToDelete + "\n";
        }

        people = people.substring(0, people.length() - 1);
        result = String.format(MESSAGE_DELETE_PERSON_SUCCESS, people);
        return new CommandResult(result);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndexArraylist.equals(((DeleteCommand) other).targetIndexArraylist)); // state check
    }
}
