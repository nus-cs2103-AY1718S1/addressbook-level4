package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_WORD_ALIAS = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the currently selected person or the person identified by the index number used in the last "
            + "person listing.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "%1$s has been deleted from the address book!";

    private final ReadOnlyPerson personToDelete;

    public DeleteCommand() throws CommandException {
        personToDelete = selectPersonForCommand();
    }

    public DeleteCommand(Index targetIndex) throws CommandException {
        personToDelete = selectPersonForCommand(targetIndex);
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        boolean requireJump = personToDelete.equals(model.getSelectedPerson());
        Index index = ListObserver.getIndexOfPersonInCurrentList(personToDelete);
        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        if (requireJump) {
            EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.personToDelete.equals(((DeleteCommand) other).personToDelete)); // state check
    }
}
