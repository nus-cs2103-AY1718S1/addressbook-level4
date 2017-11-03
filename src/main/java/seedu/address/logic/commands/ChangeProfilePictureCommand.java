package seedu.address.logic.commands;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Change profile picture of a contact
 */
public class ChangeProfilePictureCommand extends Command {

    public static final String COMMAND_WORD = "changePicture";
    public static final String COMMAND_ALIAS = "chgPic";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change profile picture of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) PICTURE_PATH (must be in PNG or JPG format)\n"
            + "Example: " + COMMAND_WORD + " 1 D:/JamesProfilePicture.png";

    public static final String MESSAGE_CHANGE_PROFILE_PICTURE_SUCCESS = "Successfully change profile picture "
            + "of person: %1$s";
    private static final String DEFAULT_IMAGE_STORAGE_PREFIX = "data/";
    private static final String DEFAULT_IMAGE_STORAGE_SUFFIX = ".png";

    private final Index targetIndex;
    private final String picturePath;

    public ChangeProfilePictureCommand(Index targetIndex, String picturePath) {
        this.targetIndex = targetIndex;
        this.picturePath = picturePath;
    }

    /**
     * Save a given image file to storage
     * @param file
     */
    private void saveImageToStorage(File file, ReadOnlyPerson person) throws CommandException {
        Image image = new Image(file.toURI().toString());
        String phoneNum = person.getPhone().value;

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",
                    new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum + DEFAULT_IMAGE_STORAGE_SUFFIX));
        } catch (IOException | IllegalArgumentException e) {
            throw new CommandException(Messages.MESSAGE_FILE_NOT_FOUND);
        }
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToChangeProfilePicture = lastShownList.get(targetIndex.getZeroBased());
        saveImageToStorage(new File(picturePath), personToChangeProfilePicture);

        return new CommandResult(String.format(MESSAGE_CHANGE_PROFILE_PICTURE_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeProfilePictureCommand // instanceof handles nulls
                && this.targetIndex.equals(((ChangeProfilePictureCommand) other).targetIndex)); // state check
    }
}
