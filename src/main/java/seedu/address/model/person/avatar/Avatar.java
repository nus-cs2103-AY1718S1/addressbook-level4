package seedu.address.model.person.avatar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import javax.imageio.ImageIO;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.AvatarUtil;

//@@author teclu
/**
 * Represents a Person's avatar in the address book.
 * Guarantees: immutable.
 */
public class Avatar {
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar should be a valid online URL or local path.";
    public static final String DEFAULT_VALUE = "No Avatar";

    public final String value;
    private URL url;
    private BufferedImage image;

    /**
     * Sets a default placeholder avatar for new contacts being added.
     */
    public Avatar() throws IllegalValueException {
        AvatarUtil placeholder = new AvatarUtil();
        image = placeholder.getPlaceholderAvatar();
        value = DEFAULT_VALUE;
    }

    /**
     * Validates given avatar URL string and saves it in the data directory.
     *
     * @throws IllegalValueException if given URL string is invalid.
     */
    public Avatar(String url) throws IllegalValueException {
        try {
            if (url.isEmpty() || DEFAULT_VALUE.equals(url)) {
                AvatarUtil placeholder = new AvatarUtil();
                image = placeholder.getPlaceholderAvatar();
                value = DEFAULT_VALUE;
            } else {
                File defaultAvatar = new File(url);

                if (isValidUrl(url)) {
                    this.url = new URL(url);
                } else {
                    this.url = defaultAvatar.toURI().toURL();
                }
                this.image = ImageIO.read(this.url);

                if (!isSavedInData(url)) {
                    String outputName = "/data/" + this.image.hashCode() + ".png";
                    File outputImage = new File(System.getProperty("user.dir") + outputName);

                    File parentDirectory = outputImage.getParentFile();
                    if (!parentDirectory.exists()) {
                        parentDirectory.mkdirs();
                    }

                    ImageIO.write(this.image, "png", outputImage);
                    this.url = outputImage.toURI().toURL();
                }
                this.value = this.url.toString();
            }
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid url.
     */
    public static boolean isValidUrl(String url) {
        return (url.contains("https://") || url.contains("http://") || url.contains("file:/"));
    }

    /**
     * Returns true if a given valid url is already found in data folder.
     */
    public static boolean isSavedInData(String url) {
        return (url.contains("file:/") && url.contains("data/"));
    }

    public BufferedImage getImage() {
        return image;
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
