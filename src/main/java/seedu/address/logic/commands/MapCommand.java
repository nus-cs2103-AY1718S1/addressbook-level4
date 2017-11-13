//@@author ChenXiaoman
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowPersonAddressEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Shows a person's address identified using person's last displayed index from the list.
 */
public class MapCommand extends Command {
    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the address of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Showing the address of Person: %1$s";

    private final Index targetIndex;

    public MapCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        // The index is invalid because it is larger than the size of entire person list.
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Get the peron with the index.
        ReadOnlyPerson person = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        // Post an event to show the address.
        EventsCenter.getInstance().post(new ShowPersonAddressEvent(person.getAddress().value));

        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, person.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.targetIndex.equals(((MapCommand) other).targetIndex)); // state check
    }
}
