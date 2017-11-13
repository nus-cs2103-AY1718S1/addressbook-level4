package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelShowLocationEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dalessr
/**
 * Shows the location of a person on Google map identified using it's last displayed index from the address book.
 */
public class MapShowCommand extends Command {

    public static final String COMMAND_WORD = "m-show";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the location of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Located Person: %1$s\n"
            + "Please click on the browser tab below to view the map.";

    private final Index targetIndex;

    public MapShowCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new BrowserPanelShowLocationEvent(
                model.getFilteredPersonList().get(targetIndex.getZeroBased())));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapShowCommand // instanceof handles nulls
                && this.targetIndex.equals(((MapShowCommand) other).targetIndex)); // state check
    }
}
