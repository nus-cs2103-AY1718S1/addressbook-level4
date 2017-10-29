package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.AccessLocationRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Accesses a person's location in the address book.
 */
public class LocationCommand extends Command {
    public static final String COMMAND_WORD = "locate";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Accesses the location of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Accessed location of Person: %2$s at index %1$s";

    private final Index targetIndex;

    public LocationCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(targetIndex.getZeroBased());
        String name = person.getName().toString();

        EventsCenter.getInstance().post(new AccessLocationRequestEvent(person.getAddress().toString()));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, targetIndex.getOneBased(), name));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocationCommand) other).targetIndex)); // state check
    }
}
