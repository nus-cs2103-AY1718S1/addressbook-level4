package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the directory to a person's photo in the address book.
 */
public class Photo {

    public static final String MESSAGE_PHOTO_CONSTRAINTS = "Person's photo should be in format: nameOfFile.png";

    public static final String PHOTO_VALIDATION_REGEX = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)";

    public static final int HEIGHT = 100;
    public static final int WIDTH = 75;

    public static final String BASE_DIR = System.getProperty("user.dir") + "/src/main/resources/person_photos/";

    private String photoDir;

    public String getPhotoDir() {
        return photoDir;
    }

    public String getFullPhotoDir() { return BASE_DIR + photoDir; }

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday address string is invalid.
     */
    public Photo(String photoDir) throws IllegalValueException {
        requireNonNull(photoDir);
        String trimmedPhoto = photoDir.trim();
        if (!isValidPhoto(trimmedPhoto)) {
            throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
        }
        this.photoDir = trimmedPhoto;
    }

    /**
     * Returns if a given string is a valid person photo.
     */
    public static boolean isValidPhoto(String test) {
        return test.matches(PHOTO_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return photoDir;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.photoDir.equals(((Photo) other).photoDir)); // state check
    }

    @Override
    public int hashCode() {
        return photoDir.hashCode();
    }

}
