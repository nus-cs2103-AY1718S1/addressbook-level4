package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelFindRouteEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dalessr
/**
 * Shows the route from the entered location to the selected person's address on Google map.
 */
public class MapRouteCommand extends Command {

    public static final String COMMAND_WORD = "m-route";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the route from the location provided to the selected person's address on Google map.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_ADDRESS + "ADDRESS\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADDRESS + "Blk 30 Clementi Street 29";

    public static final String MESSAGE_FIND_ROUTE_SUCCESS = "Found Route to Person: %1$s\n"
            + "Please click on the browser tab below to view the map.";

    private final Index targetIndex;
    private final String startLocation;

    public MapRouteCommand(Index targetIndex, String startLocation) {
        this.targetIndex = targetIndex;
        this.startLocation = startLocation;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new BrowserPanelFindRouteEvent(
                model.getFilteredPersonList().get(targetIndex.getZeroBased()), startLocation));
        return new CommandResult(String.format(MESSAGE_FIND_ROUTE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapRouteCommand // instanceof handles nulls
                && this.targetIndex.equals(((MapRouteCommand) other).targetIndex)); // state check
    }
}
