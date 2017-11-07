package seedu.address.commons.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Class for handling save/delete image in the storage
 */
public class ImageStorageHandler {
    private static final String DEFAULT_IMAGE_STORAGE_PREFIX = "data/";
    private static final String DEFAULT_IMAGE_STORAGE_SUFFIX = ".png";
    private static final String PROFILE_PICTURE_PATH_FORMAT = DEFAULT_IMAGE_STORAGE_PREFIX
            + "%s" + DEFAULT_IMAGE_STORAGE_SUFFIX;

    private static String getProfilePicturePath(ReadOnlyPerson person) {
        return String.format(PROFILE_PICTURE_PATH_FORMAT, person.getPhone().value);
    }

    /**
     * Save a given image file to storage
     * @param file
     */
    public static void saveImageToStorage(File file, ReadOnlyPerson person) throws CommandException {

        try {
            BufferedImage image = ImageIO.read(file);
            ImageIO.write(image, "png", new File(getProfilePicturePath(person)));
        } catch (IOException | IllegalArgumentException e) {
            throw new CommandException(Messages.MESSAGE_FILE_NOT_FOUND);
        }
    }

    /**
     * Delete a person's profile picture in storage
     * @param personToDelete
     */
    public static void deleteProfilePicture(ReadOnlyPerson personToDelete) {
        String profilePictureToDeletePath = getProfilePicturePath(personToDelete);
        File profilePictureToDelete = new File(profilePictureToDeletePath);
        Boolean isSuccessfullyDeleted = profilePictureToDelete.delete();
    }
}
