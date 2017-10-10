package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.SortListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;

/***
 * Sort list
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort list by given single attribute "
            + "the specified attribute (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: ATTRIBUTE\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "List sorted successfully";

    private static ListingUnit currentListingUnit = ListingUnit.getCurrentListingUnit();

    @Override
    public CommandResult execute() throws CommandException {

        EventsCenter.getInstance().post(new SortListRequestEvent(currentListingUnit));
        return new CommandResult(MESSAGE_SELECT_PERSON_SUCCESS);

    }

}