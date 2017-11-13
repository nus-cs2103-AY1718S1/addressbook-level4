package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

//@@author JunQuann
/**
 * Indicates that the file chooser needs to pop up
 */
public class FileChooserEvent extends BaseEvent {

    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    @Override
    public String toString() {
        return "Select your image from the file chooser";
    }

}
