//@@author danielbrzn

package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowUrlEvent;
import seedu.address.commons.util.GoogleUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Accesses the person's location in Google Maps on the browser.
 */

public class LocationCommand extends Command {

    public static final String COMMAND_WORD = "location";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens up the location of the person at the selected"
            + " index in Google Maps. "
            + "Parameters: "
            + "INDEX "
            + "Example: " + COMMAND_WORD + " "
            + "1 ";

    public static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps/place/";
    public static final String MESSAGE_SUCCESS = "Loaded location of %1$s";
    public static final String MESSAGE_NO_ADDRESS = "%1$s does not have an address.";
    public static final String MESSAGE_FAILURE = "Failed to load Google Maps. Check your internet connection.";

    private final Index index;

    public LocationCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        // Check if index is valid
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + index.getOneBased());
        }

        // Check if Google is reachable
        if (!GoogleUtil.isReachable()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        ReadOnlyPerson current = lastShownList.get(index.getZeroBased());

        // Check if Person has an address
        if (current.getAddress().toString().length() == 0) {
            throw new CommandException(String.format(MESSAGE_NO_ADDRESS, current.getName().toString()));
        }

        String finalUrl = GOOGLE_MAPS_URL_PREFIX + parseAddressForUrl(current.getAddress());
        EventsCenter.getInstance().post(new ShowUrlEvent(finalUrl));
        return new CommandResult(String.format(MESSAGE_SUCCESS, current.getName().toString()));
    }

    /**
     * Parses address into a URL-appendable string
     */
    public String parseAddressForUrl(Address address) {
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        String[] addressArray = address.toString().split(" ");

        for (String part : addressArray) {
            sb.append(prefix);
            prefix = "+";
            sb.append(part);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.index.equals(((LocationCommand) other).index)); // state check
    }

}

