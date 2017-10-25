package seedu.address.model.person;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import seedu.address.MainApp;
import seedu.address.commons.exceptions.IllegalValueException;

import java.io.File;

public class Avatar {

    public final static String DEFAULT_AVATAR_IMAGE_PATH = "/images/generic_avatar.png";
    public final static String AVATARS_DIRECTORY = "/images/avatars/";
    public final static String MESSAGE_AVATAR_CONSTRAINTS = "File path provided must point to a valid, readable image.";

    private String avatarFilePath;

    public ObjectProperty<Image> avatarImage;

    public static String getDirectoryPath(String imageFile) {
        return AVATARS_DIRECTORY + imageFile;
    }

    public Avatar() {
        // Default object -> 'generic' avatar
        this.avatarFilePath = DEFAULT_AVATAR_IMAGE_PATH;
        Image imgObj = new Image(this.avatarFilePath);
        this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
    }

    public Avatar(String avatarFilePath) throws IllegalValueException {
        this.avatarFilePath = avatarFilePath;
        if(validFile(this.avatarFilePath)) {
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
        File f = new File(MainApp.class.getResource(avatarFilePath).getFile());
        return f.exists() && f.canRead();
    }
}
