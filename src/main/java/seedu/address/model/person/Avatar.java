//@@author vsudhakar

package seedu.address.model.person;

import java.io.File;
import java.net.MalformedURLException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Contact's avatar
 */
public class Avatar {
    public static final String DEFAULT_AVATAR_IMAGE_PATH = "/images/generic_avatar.png";
    public static final String DEFAULT_AVATAR_DIRECTORY = "avatars/";
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "File path provided must point to a valid, readable image.";

    private ObjectProperty<Image> avatarImage;
    private String avatarFilePath;

    public Avatar() {
        // Default object -> 'generic' avatar
        this.avatarFilePath = new File(DEFAULT_AVATAR_IMAGE_PATH).getPath();
    }

    public Avatar(String avatarFilePath) throws IllegalValueException {
        if (validFile(avatarFilePath)) {
            File imgFile = new File(avatarFilePath);
            try {
                this.avatarFilePath = imgFile.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
            }
        } else {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    public static String getDirectoryPath(String imageFile) {
        return DEFAULT_AVATAR_DIRECTORY + imageFile;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    /**
     * Creates image object and object property
     * for UI to bind to
     *
     */
    public void constructImageProperty() {
        Image imgObj = new Image(this.avatarFilePath);
        this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
    }

    /**
     *
     * @return Bindable object for UI
     */
    public ObjectProperty<Image> avatarImageProperty() {
        if (avatarImage == null) {
            constructImageProperty();
        }
        return avatarImage;
    }

    /**
     * validate the file path
     *
     * @param avatarFilePath file path
     * @return true or false
     */
    public static boolean validFile(String avatarFilePath) {
        try {
            File f = new File(avatarFilePath);
            return f.exists() && f.canRead();
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Avatar
                && this.avatarFilePath.equals(((Avatar) other).avatarFilePath));
    }
}
