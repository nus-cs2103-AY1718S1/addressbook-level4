package seedu.address.logic.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PhotoChangeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author JasmineSee

/**
 * Deletes photo of specified person.
 */
public class DeletePhotoCommand extends Command {
    public static final String COMMAND_WORD = "dphoto";
    public static final String COMMAND_ALIAS = "dp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes image of the person identified by the index number used "
            + "in the last person listing or from all images.\n"
            + "Parameters: INDEX (must be a positive integer).\n"
            + "Example: " + COMMAND_WORD + " 1\n";

    public static final String MESSAGE_DELETE_IMAGE_SUCCESS = "Deleted photo of Person: %1$s";
    public static final String MESSAGE_DELETE_IMAGE_FAILURE = "Person does not have a photo to delete";
    private final Index targetIndex;

    public DeletePhotoCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToDeleteImage = lastShownList.get(targetIndex.getZeroBased());

        if (isPhotoExist(personToDeleteImage)) {
            deletePhoto(personToDeleteImage);
            EventsCenter.getInstance().post(new PhotoChangeEvent());
        } else {
            throw new CommandException(String.format(MESSAGE_DELETE_IMAGE_FAILURE));
        }

        LoggingCommand loggingCommand = new LoggingCommand();
        loggingCommand.keepLog("", "Deleted photo of " + targetIndex.getOneBased());

        return new CommandResult(String.format(MESSAGE_DELETE_IMAGE_SUCCESS, personToDeleteImage));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeletePhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeletePhotoCommand) other).targetIndex)); // state check
    }

    /**
     * Checks if person's photo exist.
     */
    public boolean isPhotoExist(ReadOnlyPerson target) {
        Path path = Paths.get("photos/" + target.getEmail().toString() + ".png");
        if (Files.exists(path)) {
            return true;
        }
        return false;
    }

    /**
     * Deletes photo of the person identified using it's last displayed index from the address book.
     */
    public void deletePhoto(ReadOnlyPerson target) {
        File photoPath = new File("photos/" + target.getEmail().toString() + ".png");
        photoPath.delete();
    }

}
