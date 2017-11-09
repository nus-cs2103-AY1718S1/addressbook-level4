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
        Avatar avatar;
        try {
            if (Avatar.validFile(this.avatar)) {
                avatar = new Avatar(this.avatar);
            } else {
                avatar = new Avatar();
            }
        } catch (Exception e) {
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

    public ThemeSelectionWindow(UserPrefs prefs) {
        super(FXML);
        this.prefs = prefs;

        // Create and set stage
        this.primaryStage = new Stage();
        Scene scene = new Scene(getRoot());
        this.primaryStage.setScene(scene);

        // Set theme
        scene.getStylesheets().add(
                MainApp.class.getResource("/view/" + prefs.getCurrentUserTheme() + ".css").toExternalForm());

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

<AnchorPane prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.121" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <SplitPane dividerPositions="0.29797979797979796" layoutX="200.0" layoutY="120.0" prefHeight="400.0" prefWidth="600.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
        <items>
          <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="160.0" prefWidth="178.0">
               <children>
                  <Label fx:id="currentThemeLabel" text="" />
               </children>
            </AnchorPane>
          <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="160.0" prefWidth="100.0">
               <children>
                  <GridPane prefHeight="398.0" prefWidth="174.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                    <columnConstraints>
                      <ColumnConstraints hgrow="SOMETIMES" minWidth="10.0" prefWidth="100.0" />
                    </columnConstraints>
                    <rowConstraints>
                      <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                        <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES" />
                    </rowConstraints>
                     <children>
                        <ImageView fx:id="thumbnail1" fitHeight="150.0" fitWidth="200.0" onMouseClicked="#handleTheme1" pickOnBounds="true" preserveRatio="true" />
                        <ImageView fx:id="thumbnail2" fitHeight="150.0" fitWidth="200.0" onMouseClicked="#handleTheme2" pickOnBounds="true" preserveRatio="true" GridPane.rowIndex="1" />
                     </children>
                  </GridPane>
               </children></AnchorPane>
        </items>
      </SplitPane>
   </children>
</AnchorPane>

```
