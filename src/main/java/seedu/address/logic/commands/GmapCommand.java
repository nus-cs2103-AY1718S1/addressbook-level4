package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayGmapEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Display google map of person identified using it's last displayed index from the address book.
 */
public class GmapCommand extends Command {

    public static final String COMMAND_WORD = "gmap";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display google map data of person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GMAP_PERSON_SUCCESS = "Displayed Gmap of Person: %1$s";

    private final Index targetIndex;

    public GmapCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new DisplayGmapEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_GMAP_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GmapCommand // instanceof handles nulls
                && this.targetIndex.equals(((GmapCommand) other).targetIndex)); // state check
    }
}
