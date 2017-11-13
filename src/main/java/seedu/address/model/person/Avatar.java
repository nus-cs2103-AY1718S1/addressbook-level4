//@@author yuheng222

package seedu.address.model.person;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's avatar in the address book.
 */

public class Avatar {
    public static final String MESSAGE_AVATAR_CONSTRAINTS =
            "The file path must be valid and the avatar should be of correct image file format";
    public static final String DEFAULT_PATH = "default.png";
    public final String value;
    private BufferedImage avatar;
    private File defaultImageRoot = null;

    /**
     * Sets avatar based on the given filepath.
     *
     * @throws IllegalValueException if the image filepath is invalid.
     */
    public Avatar(String path) throws IllegalValueException, IOException {
        if (path == null || (path.length()) == 0) {
            this.avatar = ImageIO.read(getClass().getClassLoader().getResource(DEFAULT_PATH));
            defaultImageRoot = new File(DEFAULT_PATH);
            ImageIO.write(avatar, "png", defaultImageRoot);
            this.value = DEFAULT_PATH;
        } else {
            try {
                File source = new File(path);
                this.avatar = ImageIO.read(source);
                this.value = path;
            } catch (IOException e) {
                throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
            }
        }
    }

    public BufferedImage getAvatar() {
        return avatar;
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
