package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

//@@author JunQuann
/**
 * Indicate that a new image is available to be copied to designated folder
 */
public class NewImageEvent extends BaseEvent {

    public final String imageName;
    public final String currentImagePath;

    private String imagePath;

    public NewImageEvent(String imageName, String currentImagePath) {
        this.imageName = imageName;
        this.currentImagePath = currentImagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Image has been copied to the designated folder.";
    }
}
