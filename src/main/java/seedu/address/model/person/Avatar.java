package seedu.address.model.person;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import seedu.address.commons.exceptions.IllegalValueException;

import java.io.File;
import java.io.IOException;

public class Avatar {

    public final static String DEFAULT_AVATAR_IMAGE_PATH = "/images/generic_avatar.png";
    public final static String MESSAGE_AVATAR_CONSTRAINTS = "File path provided must point to a valid, readable image.";

    private String avatarFilePath;

    public ObjectProperty<Image> avatarImage;

    public Avatar() {
        // Default object -> 'generic' avatar
        this.avatarFilePath = DEFAULT_AVATAR_IMAGE_PATH;
        Image imgObj = new Image(this.avatarFilePath);
        this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
    }

    public Avatar(String avatarFilePath) throws IllegalValueException {
        if(validFile(avatarFilePath)) {
            this.avatarFilePath = avatarFilePath;
            Image imgObj = new Image(this.avatarFilePath);
            this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
        } else {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public ObjectProperty<Image> avatarImageProperty() {
         return avatarImage;
    }

    public boolean validFile(String avatarFilePath) {
        File f = new File(avatarFilePath);
        if (f.exists() && f.canRead()) {
            return true;
        }
        return false;
    }
}
