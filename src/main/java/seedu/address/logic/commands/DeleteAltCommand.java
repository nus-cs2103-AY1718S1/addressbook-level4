package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.logic.ContactAltDeletionEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class DeleteAltCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the name.\n"
            + "Parameters: NAME (must be alphabetical letters)\n"
            + "Example: " + COMMAND_WORD + " john";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    private final String targetName;

    public DeleteAltCommand(String targetName) { this.targetName = targetName; }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        EventsCenter.getInstance().post(new ContactAltDeletionEvent(targetName));

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        int index = 0;

        for (ReadOnlyPerson p : lastShownList) {
            if (p.getName().toString().contains(targetName)) {
                index = lastShownList.indexOf(p);
                break;
            } else {
                index = -1;
            }
        }

        if (index >= lastShownList.size() || index == -1) {
            throw new CommandException(Messages.MESSAGE_PERSON_NAME_ABSENT);
        }

        ReadOnlyPerson personToDelete = lastShownList.get(index);

        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteAltCommand // instanceof handles nulls
                && this.targetName.equals(((DeleteAltCommand) other).targetName)); // state check
    }
}
