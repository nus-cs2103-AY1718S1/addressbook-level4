package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AutoCorrectCommand;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_ALIAS = "d";
    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number(s) used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) OR INDEX/INDEX/...\n"
            + "Example 1: " + COMMAND_WORD + " 1" + " deletes the first person in the list.\n"
            + "Example 2: " + COMMAND_WORD + " 1/2/3" + " deletes the first, second and third persons in the list\n";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final ArrayList<Index> targetIndex;

    private AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();

    public DeleteCommand(ArrayList<Index> targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        //@@author chairz
        for (Index i : targetIndex) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        String result = "";
        Collections.sort(targetIndex);
        for (Index i : targetIndex) {
            ReadOnlyPerson personToDelete = lastShownList.get(i.getZeroBased());

            try {
                model.deletePerson(personToDelete);
                if (targetIndex.size() == 1) {
                    result = result.concat(personToDelete.toString());
                } else {
                    result = result.concat("\n" + personToDelete.toString());
                }
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }

        }

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, result));
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + String.format(MESSAGE_DELETE_PERSON_SUCCESS, result));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
