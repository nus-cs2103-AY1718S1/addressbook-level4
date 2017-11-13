package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.index.Selection;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author martyn-wong
/**
 *  Returns selected person's address in google map search in browser panel
 */
public class MapCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "map";
    public static final String COMMAND_ALIAS = "m";

    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + " INDEX";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the address of person on Google Maps "
            + "identified by the index number used in the last person listing. "
            + "Parameters: INDEX "
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String MESSAGE_MAP_SHOWN_SUCCESS = "Map Display Successful! Address of: %1$s";

    public final Index index;

    public MapCommand (Index index) {
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (Selection.getSelectionStatus() == false && index.getOneBased() <= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_PERSON_NOT_SELECTED);
        }

        if (Selection.getMeetingStatus() == true) {
            throw new CommandException(Messages.MESSAGE_WRONG_DISPLAY_MODE);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToShow = lastShownList.get(index.getZeroBased());

        try {
            model.mapPerson(personToShow);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_MAP_SHOWN_SUCCESS, personToShow));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapCommand // instanceof handles nulls
                && this.index.equals(((MapCommand) other).index)); // state check
    }
}
