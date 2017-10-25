package seedu.address.model.person;

import java.io.File;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Contact's avatar
 */
public class Avatar {
    public static final String DEFAULT_AVATAR_IMAGE_PATH = "/images/generic_avatar.png";
    public static final String AVATARS_DIRECTORY = "/images/avatars/";
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "File path provided must point to a valid, readable image.";

    private ObjectProperty<Image> avatarImage;
    private String avatarFilePath;

    public Avatar() {
        // Default object -> 'generic' avatar
        this.avatarFilePath = DEFAULT_AVATAR_IMAGE_PATH;
        Image imgObj = new Image(this.avatarFilePath);
        this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
    }

    public Avatar(String avatarFilePath) throws IllegalValueException {
        this.avatarFilePath = avatarFilePath;
        if (validFile(this.avatarFilePath)) {
            Image imgObj = new Image(this.avatarFilePath);
            this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
        } else {
            this.avatarImage = new SimpleObjectProperty<Image>(new Image("/images/generic_avatar.png"));
        }
    }

    public static String getDirectoryPath(String imageFile) {
        return AVATARS_DIRECTORY + imageFile;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public ObjectProperty<Image> avatarImageProperty() {
        return avatarImage;
    }

    /**
     * validate the file path
     * @param avatarFilePath file path
     * @return true or false
     */
    public boolean validFile(String avatarFilePath) {
        try {
            File f = new File(MainApp.class.getResource(avatarFilePath).getFile());
            return f.exists() && f.canRead();
        } catch (NullPointerException e) {
            return false;
        }
    }
}
