# vsudhakar
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> avatarImagePath} into an {@code Optional<Avatar>} if {@code avatarImagePath}
     * is present.
     */
    public static Optional<Avatar> parseAvatar(
            Optional<String> avatarImagePath) throws IllegalValueException {
        // Return default Avatar image if empty
        return avatarImagePath.isPresent() ? Optional.of(
                new Avatar(Avatar.getDirectoryPath(
                        avatarImagePath.get()))) : Optional.of(new Avatar());
    }
```
###### \java\seedu\address\model\person\Avatar.java
``` java

package seedu.address.model.person;

import java.io.File;

import com.sun.media.jfxmedia.logging.Logger;

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
    public static final String DEFAULT_AVATAR_DIRECTORY = "avatars/";
    public static final String MESSAGE_AVATAR_CONSTRAINTS = "File path provided must point to a valid, readable image.";

    private boolean defaultAvatar;
    private ObjectProperty<Image> avatarImage;
    private String avatarFilePath;

    public Avatar() {
        // Default object -> 'generic' avatar
        /*this.avatarFilePath = MainApp.class.getResource(DEFAULT_AVATAR_IMAGE_PATH).toExternalForm();
        System.out.println("USING DEFAULT PATH: " + this.avatarFilePath);
        Logger.logMsg(Logger.DEBUG, "USING DEFAULT PATH: " + this.avatarFilePath);*/
        this.avatarFilePath = "";
        this.defaultAvatar = true;
    }

    public Avatar(String avatarFilePath) throws IllegalValueException {
        if (validFile(avatarFilePath)) {
            File imgFile = new File(avatarFilePath);
            this.avatarFilePath = avatarFilePath;
            this.defaultAvatar = false;
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
        String resourcePath;
        if (this.defaultAvatar) {
            resourcePath = MainApp.class.getResource(DEFAULT_AVATAR_IMAGE_PATH).toString();
        } else {
            resourcePath = new File(this.avatarFilePath).toURI().toString();
        }
        Image imgObj = new Image(resourcePath);
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
            Logger.logMsg(Logger.DEBUG, "File: " + avatarFilePath + " | Exists: " + Boolean.toString(f.exists())
                          + " | Can Read: " + Boolean.toString(f.canRead()));
            return f.exists() && f.canRead();
        } catch (NullPointerException e) {
            Logger.logMsg(Logger.ERROR, "Error reading file at: " + avatarFilePath);
            Logger.logMsg(Logger.ERROR, e.toString());
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
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public Avatar getAvatar() {
        return avatar.get();
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.set(requireNonNull(avatar));
    }
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        Avatar avatar = null;
        try {
            avatar = new Avatar(this.avatar);
        } catch (IllegalValueException e) {
            avatar = new Avatar();
        }
```
###### \java\seedu\address\ui\ThemeSelectionWindow.java
``` java

package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.model.UserPrefs;

/**
 * Theme selection window
 */
public class ThemeSelectionWindow extends UiPart<Region> {

    private static final String FXML = "ThemeSelectionWindow.fxml";
    private Stage primaryStage;
    private UserPrefs prefs;
    private Image theme1Thumbnail;
    private Image theme2Thumbnail;

    @FXML
    private ImageView thumbnail1;

    @FXML
    private ImageView thumbnail2;

    @FXML
    private Label currentThemeLabel;

    public ThemeSelectionWindow(UserPrefs prefs, Stage parentStage) {
        super(FXML);
        this.prefs = prefs;

        // Create and set stage
        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());
        this.primaryStage.setScene(scene);
        this.primaryStage.getIcons().add(new Image("/images/address_book_32.png"));
        this.primaryStage.setHeight(729);
        this.primaryStage.setWidth(1018);
        this.primaryStage.setResizable(false);
        this.primaryStage.initOwner(parentStage);
        this.primaryStage.initModality(Modality.WINDOW_MODAL);

        // Configure UI
        this.primaryStage.setTitle("Theme Selection");

        /**
         * TODO: Scalable implementation using additional JSON file
         */
        theme1Thumbnail = new Image(
                MainApp.class.getResource("/images/theme_thumbnails/DarkTheme.png").toExternalForm());
        theme2Thumbnail = new Image(
                MainApp.class.getResource("/images/theme_thumbnails/LightTheme.png").toExternalForm());

        thumbnail1.setImage(theme1Thumbnail);
        thumbnail2.setImage(theme2Thumbnail);

        currentThemeLabel.setText("Current Theme: " + prefs.getCurrentUserTheme());


    }

    public void show() {
        primaryStage.showAndWait();
    }

    /**
     * select theme 1
     */
    public void handleTheme1() {
        System.out.println("Theme 1 selected!");
        prefs.setCurrentUserTheme("DarkTheme");
        currentThemeLabel.setText("Current Theme: " + prefs.getCurrentUserTheme());
    }

    /**
     * select theme 2
     */
    public void handleTheme2() {
        System.out.println("Theme 2 selected!");
        prefs.setCurrentUserTheme("LightTheme");
        currentThemeLabel.setText("Current Theme: " + prefs.getCurrentUserTheme());
    }
}

```
###### \resources\view\ThemeSelectionWindow.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>

```
