//@@author vsudhakar

package seedu.address.model.person;

import java.io.File;

import com.sun.javafx.robot.FXRobot;
import com.sun.javafx.robot.FXRobotImage;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
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

    private FXRobot robot = new FXRobot() {
        @Override
        public void waitForIdle() {

        }

        @Override
        public void keyPress(KeyCode code) {

        }

        @Override
        public void keyRelease(KeyCode code) {

        }

        @Override
        public void keyType(KeyCode code, String keyChar) {

        }

        @Override
        public void mouseMove(int x, int y) {

        }

        @Override
        public void mousePress(MouseButton button, int clickCount) {

        }

        @Override
        public void mouseRelease(MouseButton button, int clickCount) {

        }

        @Override
        public void mouseClick(MouseButton button, int clickCount) {

        }

        @Override
        public void mouseDrag(MouseButton button) {

        }

        @Override
        public void mouseWheel(int wheelAmt) {

        }

        @Override
        public int getPixelColor(int x, int y) {
            return 0;
        }

        @Override
        public FXRobotImage getSceneCapture(int x, int y, int width, int height) {
            return null;
        }
    };

    public Avatar() {
        // Default object -> 'generic' avatar
        this.avatarFilePath = DEFAULT_AVATAR_IMAGE_PATH;
        Image imgObj = new Image(this.avatarFilePath);
        this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
    }

    public Avatar(String avatarFilePath) throws IllegalValueException {
        this.avatarFilePath = avatarFilePath;
        try {
            Image imgObj = new Image(this.avatarFilePath);
            this.avatarImage = new SimpleObjectProperty<Image>(imgObj);
        } catch (NullPointerException e) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
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
     *
     * @param avatarFilePath file path
     * @return true or false
     */
    public static boolean validFile(String avatarFilePath) {
        try {
            File f = new File(MainApp.class.getResource(avatarFilePath).getFile());
            return f.exists() && f.canRead();
        } catch (NullPointerException e) {
            //throw(e);
            return false;
        }
    }
}

//@@author
