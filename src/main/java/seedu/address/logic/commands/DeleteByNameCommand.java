package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using the person's name from the address book.
 */
public class DeleteByNameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deleteByName";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the person's name\n"
            + "Parameters: Full Name of a person (String)\n"
            + "Example: " + COMMAND_WORD + " John Doe";

    public static final String MESSAGE_DELETE_PERSON_BY_NAME_SUCCESS = "Deleted Person: %1$s";

    private final String targetName;

    public DeleteByNameCommand(String targetName) {
        this.targetName = targetName;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        ReadOnlyPerson personToDelete = null;

        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getName().toString().equals(targetName)) {
                personToDelete = lastShownList.get(i);
                break;
            }
        }

        if (personToDelete == null) {
            throw new CommandException(Messages.MESSAGE_UNFOUND_PERSON_NAME);
        }

        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_BY_NAME_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteByNameCommand // instanceof handles nulls
                && this.targetName.equals(((DeleteByNameCommand) other).targetName)); // state check
    }
}
