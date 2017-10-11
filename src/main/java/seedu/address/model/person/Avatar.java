package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

import java.io.File;

public class Avatar {

    public final static String DEFAULT_AVATAR_IMAGE_PATH = "/images/generic_avatar.png";
    public final static String MESSAGE_AVATAR_CONSTRAINTS = "File path provided must point to a valid, readable image.";

    private String avatarFilePath;

    public Avatar() {
        // Default object -> 'generic' avatar
        this.avatarFilePath = DEFAULT_AVATAR_IMAGE_PATH;
    }

    public Avatar(String avatarFilePath) throws IllegalValueException {
        if(validFile(avatarFilePath)) {
            this.avatarFilePath = avatarFilePath;
        } else {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public boolean validFile(String avatarFilePath) {
        File f = new File(avatarFilePath);
        if (f.exists() && f.canRead()) {
            return true;
        }
        return false;
    }
}
