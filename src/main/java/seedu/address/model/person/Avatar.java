package seedu.address.model.person;

import java.util.UUID;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.storage.ImageStorage;


/**
 * Represents a file path to Person's saved Avatar
 */

//@@author nicholaschuayunzhi
public class Avatar {


    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar file path must be .jpg or .png and must exist";

    public final String value;
    private String originalFilePath;

    /**
     * Creates an avatar with string
     * @param fileName can be invalid. Displayed avatar will then be default image
     */
    public Avatar(String fileName) {
        this.value = fileName;
    }

    /**
     * Creates an avatar with
     * @param fileName name of image to be saved as avatar
     * @param originalFilePath file path of file to be saved as avatar
     */
    private Avatar(String fileName, String originalFilePath) {
        this.value = fileName;
        this.originalFilePath = originalFilePath;
    }

    /**
     * Creates an avatar with given file path
     * file path is check to see if it is valid and if is a jpg or png
     */
    public static Avatar readAndCreateAvatar(String filePath) throws IllegalValueException {

        if (filePath == null) {
            return new Avatar("", "");
        }

        if (!ImageStorage.isValidImagePath(filePath)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }

        if (!ImageStorage.isJpgOrPng(filePath)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }

        //use a unique id for each image to prevent overwriting old images.
        return new Avatar(UUID.randomUUID().toString() + "." + ImageStorage.getFormat(filePath), filePath);
    }

    /**
     *  Saves image at {@code originalFilePath} with uid value via ImageStorage
     *  This is required at the moment as current implementation of input validation before enter
     *  uses AddressBookParser#parse
     */

    public void saveAvatar() {

        if (originalFilePath == null || originalFilePath.isEmpty()) {
            return;
        }

        ImageStorage.saveAvatar(originalFilePath, value);
    }

    public String getOriginalFilePath() {
        return originalFilePath;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

