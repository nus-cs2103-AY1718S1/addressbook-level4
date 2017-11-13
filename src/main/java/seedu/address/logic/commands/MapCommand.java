package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author liliwei25
/**
 *  Shows a person's address on Google Maps in browser
 */
public class MapCommand extends Command {
    public static final String COMMAND_WORD = "map";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the address on Google Maps of the person "
            + "identified by the index number used in the last person listing. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_MAP_SHOWN_SUCCESS = "Map for Person: %1$s";

    public final Index index;

    public MapCommand (Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToShow = lastShownList.get(index.getZeroBased());

        mapPerson(personToShow);

        return new CommandResult(String.format(MESSAGE_MAP_SHOWN_SUCCESS, personToShow));
    }

    /**
     * Shows the address of the selected {@code Person} on GoogleMaps in a new browser window
     *
     * @param personToShow Selected {@code Person} to show
     */
    private void mapPerson(ReadOnlyPerson personToShow) {
        try {
            model.mapPerson(personToShow);
        } catch (PersonNotFoundException pnfe) {
            assert false : MESSAGE_MISSING_PERSON;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.index.equals(((MapCommand) other).index)); // state check
    }
}
