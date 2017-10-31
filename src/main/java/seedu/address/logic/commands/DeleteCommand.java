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
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Multiple parameters must be separated by a space\n"
            + "Example: " + COMMAND_WORD + " 1 "
            //@@author Estois
            + "or " + COMMAND_WORD + " 1 3 4";
            //@@author

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted: %1$s";

    //@@author Estois
    private ArrayList <Index> targetIndex = new ArrayList <>();

    public DeleteCommand(ArrayList <Index> targetIndex) {
        this.targetIndex = targetIndex;
    }
    //@@author


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        //@@author Estois
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        for (int x = 0; x < targetIndex.size(); x++) {
            if (targetIndex.get(x).getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        ArrayList<ReadOnlyPerson> personToDelete = new ArrayList<>();
        for (int x = 0; x < targetIndex.size(); x++) {
            personToDelete.add(lastShownList.get(targetIndex.get(x).getZeroBased()));
        }
        try {
            for (int x = 0; x < personToDelete.size(); x++) {
                model.deletePerson(personToDelete.get(x));
            }
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        String outputResult = MESSAGE_DELETE_PERSON_SUCCESS;
        outputResult = String.format(outputResult, personToDelete.get(0));
        for (int x = 1; x < personToDelete.size(); x++) {
            outputResult = outputResult + "\n";
            String temp = personToDelete.get(x).toString();
            outputResult += temp;
        }
        //@@author
        return new CommandResult(outputResult);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
