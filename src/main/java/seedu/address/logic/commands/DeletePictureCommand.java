package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

public class DeletePictureCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delpic";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the profile picture of the currently selected person or the person"
            + "identified by the index number used in the last "
            + "person listing.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_DELPIC_SUCCESS = "%1$s profile picture has been removed!";
    public static final String MESSAGE_DELPIC_FAILURE = "Unable to remove %1$s profile picture!";

    private final Index targetIndex;

    public DeletePictureCommand() {
        this.targetIndex = null;
    }

    public DeletePictureCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String messageToDisplay = MESSAGE_DELPIC_SUCCESS;

        ReadOnlyPerson personToUpdate = selectPerson(targetIndex);

        model.removeProfilePicture(personToUpdate);

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messageToDisplay, personToUpdate.getName()));
    }
}
