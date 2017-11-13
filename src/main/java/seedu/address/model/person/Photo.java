package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

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

    private String defaultPhoto = "template.png";

    /**
     * Validates given photo. If the parameter {@code photoDir} is Empty, uses the default photo.
     *
     * @throws IllegalValueException if given photo address string is invalid.
     */
    public Photo(Optional<String> photoDir) throws IllegalValueException {
        requireNonNull(photoDir);
        if (photoDir.isPresent()) {
            String trimmedPhoto = photoDir.get().trim();
            if (!isValidPhoto(trimmedPhoto)) {
                throw new IllegalValueException(MESSAGE_PHOTO_CONSTRAINTS);
            }
            this.photoDir = trimmedPhoto;
        } else {
            this.photoDir = defaultPhoto;
        }
    }

    public String getPhotoDir() {
        return photoDir;
    }

    public String getFullPhotoDir() {
        return BASE_DIR + photoDir;
    }

    public String getTemplatePhotoDir() {
        return BASE_DIR + defaultPhoto;
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
