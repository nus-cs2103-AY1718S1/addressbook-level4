//@@author TravisPhey
package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */

public class DeleteMultipleCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteMul";
    public static final String COMMAND_ALIAS = "delM";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Can be used to delete multiple contacts at one go.\n"
            + ": Indexes that are to be deleted must be listed in ascending order.\n"
            + ": Deletes the contacts identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " 2" + " 3";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final ArrayList<Index> arrayOfIndex;

    public DeleteMultipleCommand(ArrayList<Index> arrayOfIndex) {
        this.arrayOfIndex = arrayOfIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String listOfDeletedContacts = "";

        for (int n = 0; n < arrayOfIndex.size(); n++) {

            Index targetIndex = arrayOfIndex.get(n);
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            /*if (targetIndex.getZeroBased() <= 0) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }*/

            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());
            if (n == 0) {
                listOfDeletedContacts = listOfDeletedContacts + personToDelete;
            } else {
                listOfDeletedContacts = listOfDeletedContacts + ", " + personToDelete;
            }

            try {
                model.deletePerson(personToDelete);
                //@@author vmlimshimin
                queue.offer(personToDelete);
                //@@author TravisPhey
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, listOfDeletedContacts));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMultipleCommand // instanceof handles nulls
                && this.arrayOfIndex.equals(((DeleteMultipleCommand) other).arrayOfIndex)); // state check
    }

    //@@author vmlimshimin
    @Override
    public void setData(Model model, CommandHistory commandHistory,
                        UndoRedoStack undoRedoStack, RecentlyDeletedQueue queue, String theme) {
        this.model = model;
        this.queue = queue;
    }
}
