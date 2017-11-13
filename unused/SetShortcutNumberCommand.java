//@@author yanji1221
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Sets shortcut number of person identified using it's last displayed index from the address book.
 */
public class SetShortcutNumberCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the shortcut number of person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)NUMBER (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1" + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Person's shorcut number sets: %1$s";


    private final Index targetIndex;
    private final int shortcutNumber;

    public SetShortcutNumberCommand(Index targetIndex, int shortcutNumber) {
        this.targetIndex = targetIndex;
        this.shortcutNumber = shortcutNumber;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSet = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.setPerson(personToSet);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToSet));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetShortcutNumberCommand // instanceof handles nulls
                && this.targetIndex.equals(((SetShortcutNumberCommand) other).targetIndex)); // state check
    }

}
