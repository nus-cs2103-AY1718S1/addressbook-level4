package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.LocateMrtCommandEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Locate a person's address on Google Map using it's last displayed index from the address book.
 */
public class LocateMrtCommand extends Command {

    public static final String COMMAND_WORD = "locateMrt";
    public static final String COMMAND_ALIAS = "lMrt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Locate the person's nearest MRT specified on google map.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Showing the nearest MRT of person ";

    private final Index targetIndex;

    public LocateMrtCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToSearchAddress = lastShownList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new LocateMrtCommandEvent(personToSearchAddress));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, personToSearchAddress));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocateMrtCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocateMrtCommand) other).targetIndex)); // state check
    }
}
