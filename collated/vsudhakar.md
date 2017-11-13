# vsudhakar
###### \main\java\seedu\address\logic\parser\ParserUtil.java
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
###### \main\java\seedu\address\model\person\Avatar.java
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
###### \main\java\seedu\address\model\person\Person.java
``` java
    public Person(Name name, Phone phone, Email email, Address address, Comment comment, Appoint appoint, Avatar avatar,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = new SimpleObjectProperty<>(name);
        this.phone = new SimpleObjectProperty<>(phone);
        this.email = new SimpleObjectProperty<>(email);
        this.address = new SimpleObjectProperty<>(address);
        // Use default avatar image
        this.avatar = new SimpleObjectProperty<>(avatar);
        this.comment = new SimpleObjectProperty<>(comment);
        this.appoint = new SimpleObjectProperty<>(appoint);
        // protect internal tags from changes in the arg list
        this.tags = new SimpleObjectProperty<>(new UniqueTagList(tags));
    }
```
###### \main\java\seedu\address\model\person\Person.java
``` java
    @Override
    public Avatar getAvatar() {
        return avatar.get();
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.set(requireNonNull(avatar));
    }
```
###### \main\java\seedu\address\model\UserPrefs.java
``` java
    private String avatarFilePath = "avatars/";
    private String currentUserTheme = "DarkTheme";
```
###### \main\java\seedu\address\model\UserPrefs.java
``` java

    public String getCurrentUserTheme() {
        return currentUserTheme;
    }

    public void setCurrentUserTheme(String currentUserTheme) {
        this.currentUserTheme = currentUserTheme;
    }

    public String getAvatarFilePath() {
        return avatarFilePath;
    }

    public void setAvatarFilePath(String avatarFilePath) {
        this.avatarFilePath = avatarFilePath;
    }

```
###### \main\java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        avatar = source.getAvatar().getAvatarFilePath();
```
###### \main\java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        Avatar avatar = null;
        try {
            avatar = new Avatar(this.avatar);
        } catch (IllegalValueException e) {
            avatar = new Avatar();
        }
```
###### \main\java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Opens theme window.
     */
    @FXML
    public void handleTheme() {
        System.out.println("Entering themeing mode!");
        ThemeSelectionWindow themeSelectionWindow = new ThemeSelectionWindow(prefs, primaryStage);
        themeSelectionWindow.show();

    }
```
###### \main\java\seedu\address\ui\PersonCard.java
``` java
        avatar.imageProperty().bind(person.getAvatar().avatarImageProperty());
```
###### \main\java\seedu\address\ui\ThemeSelectionWindow.java
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
###### \main\resources\view\DarkTheme.css
``` css
.background {
    -fx-background-color: #353B47;
    background-color: #383838; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: #353B47;
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: #353B47;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: #353B47;
}

.list-cell {
    -fx-background-radius: 7px;
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-radius: 7px;
    -fx-background-color: #282E3C;
        -fx-border-radius: 7px;
    -fx-border-color: #353B47;
    -fx-border-width: 3;
}

.list-cell:filled:odd {
    -fx-background-radius: 7px;
    -fx-background-color: #282E3C;
        -fx-border-radius: 7px;
    -fx-border-color: #353B47;
    -fx-border-width: 3;
}

.list-cell:filled:selected {
    -fx-background-radius: 7px;
    -fx-background-color: #424d5f;
}

.list-cell:filled:selected #cardPane {
    -fx-border-radius: 7px;
    -fx-border-color: #75B0C0;
    -fx-border-width: 3;
}

.list-cell:empty {
    -fx-background-color: #353B47;
}

.list-cell .label {
    -fx-text-fill: white;
}

#personList {
    -fx-background-color: #353B47;
}

.root {
    -fx-background-color: #353B47;
}

.cell_big_label {
    -fx-font-family: "arial";
    -fx-font-size: 16px;
    -fx-font-weight: 900;
    -fx-text-fill: #010504;
    -fx-line-spacing: 2
}

.cell_small_label {
    -fx-font-family: "arial";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
    -fx-line-spacing: 1.5
}

.anchor-pane {
     -fx-background-color: transparent;
}

.pane-with-border {
     -fx-background-color: #353B47;
}

.status-bar {
    -fx-background-color: #353B47;
    -fx-text-fill: black;
}

.result-display {
    -fx-text-fill: white;
    -fx-font-size: 13pt;
    -fx-font-family: "arial";
    -fx-background-radius: 5px;
    -fx-background-color: #282E3C;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
}

.context-menu {
    -fx-background-color: #282E3C;
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: #282E3C;
}

.menu-bar .label {
    -fx-font-size: 9pt;
    -fx-font-family: "arial";
    -fx-text-fill: white;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: #353B47;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-radius: 5px;
    -fx-background-color: transparent;
    -fx-border-width: 0;
    -fx-padding: 10 0 10 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-font-family: "arial";
   	-fx-text-fill: white;
   	-fx-background-radius: 5px;
   	-fx-background-color: #282E3C;
   	-fx-border-color: black;
   	-fx-border-width: 1;
   	-fx-border-radius: 5px;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: #282E3C;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#nameField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: white;
    -fx-background-radius: 5px;
    -fx-background-color: #282E3C;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#phoneField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: white;
    -fx-background-radius: 5px;
    -fx-background-color: #282E3C;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#emailField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: white;
    -fx-background-radius: 5px;
    -fx-background-color: #282E3C;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#tagsField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: white;
    -fx-background-radius: 5px;
    -fx-background-color: #282E3C;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#addressField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: white;
    -fx-background-radius: 5px;
    -fx-background-color: #282E3C;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#searchBarLabel {
    -fx-text-alignment: CENTER;
	-fx-background-radius: 5px;
	-fx-background-color: #75B0C0;
	-fx-font-family: "arial";
	-fx-font-weight: 900;
	-fx-font-size: 13px;
	-fx-text-fill: white;
}

#loginButton, #sendButton {
    -fx-background-radius: 10px;
	-fx-background-color: transparent;
	-fx-border-radius: 10px;
	-fx-border-color: white;
	-fx-font-family: "arial";
	-fx-font-weight: 500;
	-fx-font-size: 16px;
	-fx-text-fill: white;
}

.textfield {
	-fx-font-family: "arial";
	-fx-text-fill: white;
	-fx-font-size: 9pt;
	-fx-background-radius: 5px;
	-fx-background-color: #282E3C;
	-fx-border-color: black;
	-fx-border-width: 1;
	-fx-border-radius: 5px;
}

.default {
    -fx-background-color: #353B47;
}
```
###### \main\resources\view\LightTheme.css
``` css
.background {
    -fx-background-color: #D8D8D8;
    background-color: #383838; /* Used in the default.html file */
}

.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #555555;
    -fx-opacity: 0.9;
}

.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: #D8D8D8;
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: #D8D8D8;
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: #D8D8D8;
}

.list-view .virtual-flow .corner {
    -fx-background-color: red;
}

.list-cell {
    -fx-background-radius: 7px;
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
     -fx-background-radius: 7px;
     -fx-background-color: #D8D8D8;
     -fx-border-radius: 7px;
     -fx-border-color: #D8D8D8;
}

.list-cell:filled:even {
    -fx-background-radius: 10px;
    -fx-background-color: derive(#4051B5, 50%);
    -fx-border-radius: 7px;
    -fx-border-color: #D8D8D8;
    -fx-border-width: 3;
}

.list-cell:filled:odd {
    -fx-background-radius: 10px;
    -fx-background-color: derive(#4051B5, 50%);
    -fx-border-radius: 7px;
    -fx-border-color: #D8D8D8;
    -fx-border-width: 3;
}

.list-cell:filled:selected {
    -fx-background-radius: 10px;
    -fx-background-color: derive(#4051B5, 25%);
}

.list-cell:filled:selected #cardPane {
    -fx-border-radius: 5px;
    -fx-border-color: #4051B5;
    -fx-border-width: 3;
}

.list-cell:empty {
    -fx-background-color: #D8D8D8;
}

.list-cell .label {
    -fx-text-fill: white;
}

#personList {
    -fx-background-color: #D8D8D8;
}

.root {
    -fx-background-color: #D8D8D8;
}

.cell_big_label {
    -fx-font-family: "arial";
    -fx-font-size: 16px;
    -fx-font-weight: 900;
    -fx-text-fill: black !important;
    -fx-line-spacing: 2;
}

.cell_small_label {
    -fx-font-family: "arial";
    -fx-font-size: 13px;
    -fx-text-fill: black !important;
    -fx-line-spacing: 1.5;
}

.anchor-pane {
     -fx-background-color: transparent;
}

.pane-with-border {
     -fx-background-color: #D8D8D8;
}

.status-bar {
    -fx-background-color: #FFFFFF;
    -fx-text-fill: black;
}

.result-display {
    -fx-text-fill: black;
    -fx-font-family: "arial";
    -fx-font-size: 13pt;
    -fx-background-radius: 5px;
    -fx-background-color: #D8D8D8;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#1d1d1d, 30%);
}

.context-menu {
    -fx-background-color: #282E3C;
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: #4051B5;
}

.menu-bar .label {
    -fx-font-size: 9pt;
    -fx-font-family: "arial";
    -fx-text-fill: white;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #1d1d1d;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #1d1d1d;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#1d1d1d, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: white;
}

.scroll-bar .thumb {
    -fx-background-color: derive(#1d1d1d, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-radius: 5px;
    -fx-background-color: transparent;
    -fx-border-width: 1;
    -fx-border-color: #4051B5;
    -fx-border-radius: 5px;
    -fx-padding: 10 0 10 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-font-family: "arial";
   	-fx-text-fill: black;
   	-fx-background-radius: 5px;
   	-fx-background-color: #FFFFFF;
   	-fx-border-color: black;
   	-fx-border-width: 1;
   	-fx-border-radius: 5px;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: #FFFFFF;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: black;
    -fx-background-color: #74EDB4;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

#nameField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: black;
    -fx-background-radius: 5px;
    -fx-background-color: #FFFFFF;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#phoneField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: black;
    -fx-background-radius: 5px;
    -fx-background-color: #FFFFFF;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#emailField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: black;
    -fx-background-radius: 5px;
    -fx-background-color: #FFFFFF;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#tagsField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: black;
    -fx-background-radius: 5px;
    -fx-background-color: #FFFFFF;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#addressField {
    -fx-font-family: "arial";
    -fx-font-size: 9pt;
    -fx-text-fill: black;
    -fx-background-radius: 5px;
    -fx-background-color: #FFFFFF;
    -fx-border-color: black;
    -fx-border-width: 1;
    -fx-border-radius: 5px;
}

#searchBarLabel {
    -fx-text-alignment: CENTER;
	-fx-background-radius: 5px;
	-fx-background-color: #303E9F;
	-fx-font-family: "arial";
	-fx-font-weight: 900;
	-fx-font-size: 13px;
	-fx-text-fill: white;
}

#loginButton, #sendButton {
    -fx-background-radius: 10px;
	-fx-background-color: derive(#303E9F, 25%);
	-fx-border-radius: 10px;
	-fx-border-color: #303E9F;
	-fx-font-family: "arial";
	-fx-font-weight: 500;
	-fx-font-size: 16px;
	-fx-text-fill: white;
}

.textfield {
	-fx-font-family: "arial";
	-fx-text-fill: black;
	-fx-font-size: 9pt;
	-fx-background-radius: 5px;
	-fx-background-color: #FFFFFF;
	-fx-border-color: black;
	-fx-border-width: 1;
	-fx-border-radius: 5px;
}

.default {
    -fx-background-color: #D8D8D8;
}
```
###### \main\resources\view\ThemeSelectionWindow.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.SplitPane?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>

```
###### \test\java\seedu\address\model\person\AvatarTest.java
``` java
public class AvatarTest {

    @Test
    public void equals() {
        Avatar avatar = new Avatar();

        // same object -> true
        assertTrue(avatar.equals(avatar));

        // same values -> true
        Avatar avatarCopy = new Avatar();
        assertTrue(avatar.equals(avatarCopy));

        // different types -> false
        assertFalse(avatar.equals(1));

        // null -> false
        assertFalse(avatar.equals(null));

        // different paths -> false
        try {
            Avatar avatarDifferent = new Avatar("./src/test/avatars/generic_avatar.png");
            assertFalse(avatarCopy.equals(avatarDifferent));
        } catch (IllegalValueException e) {
            assert(false);
        }
    }

    @Test
    public void isInvalidImage() {
        String invalidFile = Avatar.getDirectoryPath("nonexistent.fake");
        assertFalse(Avatar.validFile(invalidFile));
    }

    @Test
    public void isValidImage() {
        String validFile = "./src/test/avatars/generic_avatar.png";
        assertTrue(Avatar.validFile(validFile));
    }
}
```
