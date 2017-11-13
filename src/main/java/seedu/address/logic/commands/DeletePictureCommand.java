package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.ProfilePictureNotFoundException;

//@@author jaivigneshvenugopal
/**
 * Removes the display picture of a person.
 */
public class DeletePictureCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delpic";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the profile picture of the currently selected person or the person"
            + "identified by the index number used in the last "
            + "person listing.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELETE_PICTURE_SUCCESS = "%1$s profile picture has been removed!";
    public static final String MESSAGE_DELETE_PICTURE_FAILURE = "%1$s does not have a profile picture!";

    private final ReadOnlyPerson personToUpdate;

    public DeletePictureCommand() throws CommandException {
        personToUpdate = selectPersonForCommand();
    }

    public DeletePictureCommand(Index targetIndex) throws CommandException {
        personToUpdate = selectPersonForCommand(targetIndex);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            model.removeProfilePicture(personToUpdate);
        } catch (ProfilePictureNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_DELETE_PICTURE_FAILURE, personToUpdate.getName()));
        }

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        reselectPerson(personToUpdate);

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_DELETE_PICTURE_SUCCESS, personToUpdate.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePictureCommand // instanceof handles nulls
                && this.personToUpdate.equals(((DeletePictureCommand) other).personToUpdate));
    }

}
