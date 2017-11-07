package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.events.ui.ShowLocationRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Display the location of the person with the given index in the contact list in Google Maps
 */
public class LocateCommand extends Command {

    public static final String COMMAND_WORDVAR = "locate";

    public static final String MESSAGE_USAGE = COMMAND_WORDVAR
            + ": Displays the location of the person identified by the index number in the latest person listing."
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORDVAR + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Address of the person is displayed";

    private final Index targetIndex;

    public LocateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new ShowLocationRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocateCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocateCommand) other).targetIndex)); // state check
    }
}
