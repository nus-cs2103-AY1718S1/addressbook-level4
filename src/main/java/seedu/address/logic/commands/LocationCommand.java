package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Terminates the program.
 */
//@@author sidhmads
public class LocationCommand extends Command {

    public static final String COMMAND_WORD = "map";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": If only one index found,"
            + " it finds the location of selected person."
            + "If more than one indexes are present, a map with the route that joins all the locations will be shown."
            + "Parameters: INDEXES (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Example: " + COMMAND_WORD + "1 2 3 4";

    public static final String MESSAGE_FIND_LOCATION_SUCCESS = "Opened Map";

    private final List<String> targetIndex;

    public LocationCommand(List<String> targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personToFind = new ArrayList<>();

        for (String index: this.targetIndex) {
            try {
                Index person =  ParserUtil.parseIndex(index);
                personToFind.add(lastShownList.get(person.getZeroBased()));
            } catch (IllegalValueException ive) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

        }
        try {
            model.findLocation(personToFind);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException("The target person cannot be missing");
        }

        return new CommandResult(MESSAGE_FIND_LOCATION_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocationCommand) other).targetIndex)); // state check
    }

}
