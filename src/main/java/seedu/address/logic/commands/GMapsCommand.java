package seedu.address.logic.commands;

import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_EXAMPLE;
import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_GMAPS;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ReadOnlyPerson;

//@@author nbriannl
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class GMapsCommand extends Command {

    public static final String COMMAND_WORD = "gmaps";
    public static final String COMMAND_ALIAS = "g";
    public static final String COMMAND_SECONDARY_ONE = "map";
    public static final String COMMAND_SECONDARY_TWO = "maps";

    public static final String MESSAGE_USAGE = "| " + COMMAND_WORD + " |"
            + ": Opens a Google Maps view of a person’s address.\n"
            + FORMAT_ALIGNMENT_TO_GMAPS
            + " If an address is specified, shows the directions from the address to that person's address.\n"
            + "Format: " + COMMAND_WORD + " INDEX [a/ADDRESS]\n"
            + "Example: " + COMMAND_WORD + " 1 \n"
            + FORMAT_ALIGNMENT_TO_EXAMPLE + COMMAND_WORD + " 1 a/Blk 123, Yishun 75";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Showing Map View of %1$s's address";
    public static final String MESSAGE_DIRECTIONS_TO_PERSON_SUCCESS = "Showing directions to %1$s";
    public static final String MESSAGE_PERSON_HAS_NO_ADDRESS = "%1$s has no address!";

    private final Index targetIndex;
    private final Address targetAddress;

    public GMapsCommand (Index targetIndex, Address targetAddress) {
        this.targetIndex = targetIndex;
        this.targetAddress = targetAddress;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToShowMap = lastShownList.get(targetIndex.getZeroBased());
        if (personToShowMap.getAddress().toString().equals("")) {
            throw new CommandException(String.format(MESSAGE_PERSON_HAS_NO_ADDRESS, personToShowMap.getName()));
        }

        if (targetAddress != null) {
            model.showDirectionsTo(personToShowMap, targetAddress, targetIndex);
            return new CommandResult(String.format(MESSAGE_DIRECTIONS_TO_PERSON_SUCCESS, personToShowMap.getName()));
        } else {
            model.showMapOf(personToShowMap, targetIndex);
            return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, personToShowMap.getName()));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GMapsCommand)) {
            return false;
        }

        // state check
        GMapsCommand e = (GMapsCommand) other;

        return Objects.equals(this.targetIndex, e.targetIndex)
                && Objects.equals(this.targetAddress, e.targetAddress);

    }
}
