package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.ProfilePictureNotFoundException;

//@@author jaivigneshvenugopal
/**
 * Adds a display picture to a person.
 */
public class AddPictureCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addpic";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the profile picture of the currently selected person or the person"
            + "identified by the index number used in the last "
            + "person listing.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_ADDPIC_SUCCESS = "%1$s profile picture has been updated!";
    public static final String MESSAGE_ADDPIC_FAILURE = "Unable to update %1$s profile picture!";

    private final ReadOnlyPerson personToUpdate;

    public AddPictureCommand() throws CommandException {
        personToUpdate = selectPersonForCommand();
    }

    public AddPictureCommand(Index targetIndex) throws CommandException {
        personToUpdate = selectPersonForCommand(targetIndex);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String messageToDisplay = MESSAGE_ADDPIC_SUCCESS;

        try {
            model.addProfilePicture(personToUpdate);
        } catch (ProfilePictureNotFoundException ppnfe) {
            throw new CommandException(String.format(MESSAGE_ADDPIC_FAILURE, personToUpdate.getName()));
        }

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        reselectPerson(personToUpdate);

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messageToDisplay, personToUpdate.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddPictureCommand // instanceof handles nulls
                && this.personToUpdate.equals(((AddPictureCommand) other).personToUpdate)); // state check
    }
}
