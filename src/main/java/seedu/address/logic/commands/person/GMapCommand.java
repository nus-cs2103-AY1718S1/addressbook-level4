package seedu.address.logic.commands.person;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author dennaloh
/**
 * Opens Google Maps in browser with address of person identified using it's last displayed index from the address book
 * as the destination.
 */
public class GMapCommand extends Command {

    public static final String COMMAND_WORD = "gmap";
    public static final String COMMAND_ALIAS = "gm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens Google Maps in default browser with the address "
            + "of the person identified by the index number used in the last person listing being the Destination.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SUCCESS = "Opened Google Maps to get to %1$s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the person in the filtered person list to get directions to
     */
    public GMapCommand (Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToGetDirectionsTo = lastShownList.get(targetIndex.getZeroBased());

        String gmapUrl = model.getGMapUrl(personToGetDirectionsTo);
        model.openUrl(gmapUrl);

        return new CommandResult(String.format(MESSAGE_SUCCESS, personToGetDirectionsTo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GMapCommand // instanceof handles nulls
                && this.targetIndex.equals(((GMapCommand) other).targetIndex)); // state check
    }
}
