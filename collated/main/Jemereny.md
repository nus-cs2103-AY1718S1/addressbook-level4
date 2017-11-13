# Jemereny
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.ui.UiStyle;

/**
 * change the theme of the address book
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_SHORT = "t";
    public static final String LIGHT_THEME = "light";
    public static final String DARK_THEME = "dark";
    public static final String LIGHT_THEME2 = "holy";
    public static final String DARK_THEME2 = "evil";

    public static final String MESSAGE_SUCCESS = "Theme has been changed!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes to selected theme. "
            + "Parameters: light/dark";

    private String theme;

    public ThemeCommand(String theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute() {
        if (theme.equalsIgnoreCase(ThemeCommand.LIGHT_THEME) || theme.equalsIgnoreCase(ThemeCommand.LIGHT_THEME2)) {
            UiStyle.getInstance().setToLightTheme();
        } else {
            UiStyle.getInstance().setToDarkTheme();
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && theme.equals(((ThemeCommand) other).theme));
    }

    @Override
    public String toString() {
        return theme;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case ThemeCommand.COMMAND_WORD:
        case ThemeCommand.COMMAND_SHORT:
            return new ThemeCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_WEBSITE = new Prefix("w/");
    public static final Prefix PREFIX_PICTURE = new Prefix("pic/");
    //author
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of ThemeCommand
     * and returns a ThemeCommand object for execution
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ThemeCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()
                    || (!trimmedArgs.equalsIgnoreCase(ThemeCommand.LIGHT_THEME)
                    && !trimmedArgs.equalsIgnoreCase(ThemeCommand.LIGHT_THEME2)
                    && !trimmedArgs.equalsIgnoreCase(ThemeCommand.DARK_THEME)
                    && !trimmedArgs.equalsIgnoreCase(ThemeCommand.DARK_THEME2))) {
                throw new IllegalValueException("");
            } else {
                return new ThemeCommand(trimmedArgs);
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                    ThemeCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons.sorted());
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setWebsite(Website website) {
        this.website.set(requireNonNull(website));
    }

    @Override
    public ObjectProperty<Website> websiteProperty() {
        return website;
    }

    @Override
    public Website getWebsite() {
        return website.get();
    }

    public void setPicture(Picture picture) {
        this.picture.set(requireNonNull(picture));
    }

    @Override
    public ObjectProperty<Picture> pictureProperty() {
        return picture;
    }

    @Override
    public Picture getPicture() {
        return picture.get();
    }
```
###### \java\seedu\address\model\person\Picture.java
``` java
package seedu.address.model.person;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.UserPrefs;

/**
 * Represents a Person's picture's name
 * Guarantees: immutable; is always valid
 */
public class Picture {

    public static final String MESSAGE_PROFILEPICTURE_CONSTRAINTS =
            "There should be a valid location to the picture, the picture must be a .png ";
    public static final String MESSAGE_PROFILEPICTURE_ERROR =
            "Error copying file.";

    public static final String PREFIX_PICTURE = "file://";
    public static final String PICTURE_SAVE_LOCATION =
            UserPrefs.FOLDER_LOCATION; // Where images are stored when added
    public static final String DEFAULT_PICTURE_LOCATION =
            "/images/";
    public static final String DEFAULT_PICTURE =
            "default_profile.png";
    public static final String DEFAULT_ALEX =
            "default_alex.png";
    public static final String DEFAULT_BALAKRISHNAN =
            "default_balakrishnan.png";
    public static final String DEFAULT_BERNICE =
            "default_bernice.png";
    public static final String DEFAULT_CHARLOTTE =
            "default_charlotte.png";
    public static final String DEFAULT_DAVID =
            "default_david.png";
    public static final String DEFAULT_IRFAN =
            "default_irfan.png";

    private static final int RESIZE_IMAGE_WIDTH = 256;
    private static final int RESIZE_IMAGE_HEIGHT = 256;

    private static final String PICTURE_SUFFIX = ".png";
    private static final String PICTURE_DELIMITER_SLASH = "/";
    private static final String PICTURE_DELIMITER_BACKSLASH = "\\\\";
    private static final int PICTURE_MAX_SIZE = 512000; // 512 KB

    public final String value;

    public Picture(String fileLocation) throws IllegalValueException {
        String trimmedFileLocation = fileLocation == null ? null : fileLocation.trim();
        if (!isValidPicture(trimmedFileLocation)) {
            System.out.println(fileLocation);
            throw new IllegalValueException(MESSAGE_PROFILEPICTURE_CONSTRAINTS);
        }

        if (trimmedFileLocation != null) {
            String[] split = splitFileLocation(trimmedFileLocation);

            // length will give 1 when it is the file we saved
            // in that case just put PICTURE_IMAGE_LOCATION to find it
            if (split.length != 1) {
                // Rename and copied files to avoid clashing
                String newFileName = UUID.randomUUID().toString() + PICTURE_SUFFIX;

                File src = new File(trimmedFileLocation);
                File dest = new File(PICTURE_SAVE_LOCATION + newFileName);

                // If file is too big, resize it.
                if (src.length() > PICTURE_MAX_SIZE) {
                    resizeAndSaveImage(src, newFileName);
                } else {
                    copyImage(src, dest);
                }
                this.value = newFileName;
            } else {
                // Last value is file name
                this.value = split[split.length - 1];
            }
        } else {
            this.value = null;
        }
    }

    /**
     * Splits the file depending on the delimiter used '/' or '\'
     * @param trimmedFileLocation location of valid file
     * @return split fileLocation
     */
    public static String[] splitFileLocation(String trimmedFileLocation) {
        String [] split = trimmedFileLocation.split(PICTURE_DELIMITER_SLASH);

        // If the fileLocation has been split but has length of 1,
        // It is either using another delimiter or is the file itself.
        if (split.length < 2) {
            split = trimmedFileLocation.split(PICTURE_DELIMITER_BACKSLASH);
        }

        return split;
    }

    /**
     * Returns true if file location of picture is valid and the picture exist
     * @param fileLocation
     * @return
     */
    public static boolean isValidPicture(String fileLocation) {
        if (fileLocation == null) {
            return true;
        }

        if ("".equals(fileLocation)) {
            return false;
        }

        // For default people
        if (fileLocation.equals(Picture.DEFAULT_ALEX) || fileLocation.equals(Picture.DEFAULT_BALAKRISHNAN)
                || fileLocation.equals(Picture.DEFAULT_BERNICE) || fileLocation.equals(Picture.DEFAULT_CHARLOTTE)
                || fileLocation.equals(Picture.DEFAULT_DAVID) || fileLocation.equals(Picture.DEFAULT_IRFAN)) {
            return true;
        }

        File file = new File(fileLocation);
        if (file.exists() && (fileLocation.endsWith(PICTURE_SUFFIX))) {
            return true;
        } else {
            file = new File(PICTURE_SAVE_LOCATION + fileLocation);
            if (file.exists()) {
                return true;
            }

            return false;
        }
    }

    /**
     * Copies the image and puts into data folder
     * @param src Source of file to save
     * @param dest Destination of the file to save
     * @throws IllegalValueException when src or dest is an invalid file/location
     */
    public static void copyImage(File src, File dest) throws IllegalValueException {
        try {
            FileUtils.copyFile(src, dest);
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_PROFILEPICTURE_ERROR);
        }
    }

    /**
     * Resizes and saves image to data folder
     * @param file the image
     * @param newFileName file name to save as
     * @throws IllegalValueException if there is an error loading the file
     */
    public static void resizeAndSaveImage(File file, String newFileName) throws IllegalValueException {
        try {
            BufferedImage resizedImage = resizeImage(ImageIO.read(file));

            // Saving of image into data folder
            ImageIO.write(resizedImage, "png", new File(PICTURE_SAVE_LOCATION + newFileName));
        } catch (IOException e) {
            throw new IllegalValueException(MESSAGE_PROFILEPICTURE_ERROR);
        }
    }

    /**
     * Redraws the original image into a smaller canvas, resizing the image.
     * @param originalImage The original image to be resized
     * @return BufferedImage image that is redrawn and resized
     */
    public static BufferedImage resizeImage(BufferedImage originalImage) {
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

        BufferedImage resizedImage = new BufferedImage(RESIZE_IMAGE_WIDTH, RESIZE_IMAGE_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, RESIZE_IMAGE_WIDTH, RESIZE_IMAGE_HEIGHT, null);
        g.dispose();

        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    /**
     * Returns default picture location if there is no value
     */
    public String getPictureLocation() {
        if (value == null) {
            return DEFAULT_PICTURE_LOCATION + DEFAULT_PICTURE;
        } else if (value.equals(Picture.DEFAULT_ALEX) || value.equals(Picture.DEFAULT_BALAKRISHNAN)
                || value.equals(Picture.DEFAULT_BERNICE) || value.equals(Picture.DEFAULT_CHARLOTTE)
                || value.equals(Picture.DEFAULT_DAVID) || value.equals(Picture.DEFAULT_IRFAN)) {
            // Sample data
            return DEFAULT_PICTURE_LOCATION + value;
        } else {
            return PREFIX_PICTURE + Paths.get(PICTURE_SAVE_LOCATION + value)
                    .toAbsolutePath().toUri().getPath();
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        } else if (!(other instanceof Picture)) { // instanceof handle nulls
            return false;
        } else if (this.value == ((Picture) other).value) {
            return true;
        } else if (this.value != null && this.value.equals(((Picture) other).value)) { // state check
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                      && other.getWebsite().equals(this.getWebsite())
                      && other.getPicture().equals(this.getPicture())
```
###### \java\seedu\address\model\person\Website.java
``` java
package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's website in the address book.
 * Guarantees: immutable; is valid as declared
 */
public class Website {
    public static final String MESSAGE_WEBSITE_CONSTRAINTS =
            "Website should contain a prefix of http:// https://.";
    public static final String WEBSITE_VALIDATION_REGEX =
            "https?://(www\\.)?[-a-z0-9]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&//=]*)";
    public static final String WEBSITE_EXAMPLE = "https://www.website.com/";
    public static final String WEBSITE_NULL = null; // no website
    public final String value;

    /**
     *
     */
    public Website(String website)throws IllegalValueException {
        String trimmedWebsite = website == WEBSITE_NULL ? WEBSITE_NULL : website.trim();
        if (!isValidWebsite(trimmedWebsite)) {
            throw new IllegalValueException(MESSAGE_WEBSITE_CONSTRAINTS);
        }

        this.value = trimmedWebsite;
    }

    /**
     * Returns true if given string is valid person website
     */
    public static boolean isValidWebsite(String test) {
        return test == WEBSITE_NULL || test.matches(WEBSITE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public static final String FOLDER_LOCATION = "data/";
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Load website of person if he has a website
     * If not just google search his name
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        if (person.getWebsite().hasWebsite()) {
            loadPage(person.getWebsite().value);
        } else {
            loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getName().fullName.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX);
        }
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
        UiStyle.getInstance().setScene(scene);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label website;
    @FXML
    private ImageView picture;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        website.textProperty().bind(Bindings.convert(person.websiteProperty()));

        person.pictureProperty().addListener((observable, oldValue, newValue) -> {
            picture.setImage(new Image(person.getPicture().getPictureLocation()));
        });

        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Initialise tag colors for person
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle(UiStyle.getInstance().getBackgroundStyle(getColorForTag(tag.tagName)));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initialise pictures for person
     */
    private void initPicture(ReadOnlyPerson person) {
        picture.setImage(new Image(person.getPicture().getPictureLocation()));

        Circle circle = new Circle(32.0, 32.0, 30.0);
        picture.setClip(circle);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, UiStyle.getInstance().getRandomHexColor());
        }

        return tagColors.get(tagValue);
    }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
        if (event.isError) {
            setStyleToIndicateCommandFailture();
        } else {
            setStyleToDefault();
        }
```
###### \java\seedu\address\ui\ResultDisplay.java
``` java
    private void setStyleToDefault() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets resultDisplay style to indicate failed command.
     */
    private void setStyleToIndicateCommandFailture() {
        ObservableList<String> styleClass = resultDisplay.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
        this.totalPersons.setText(totalPersons + " person(s) total");
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
    private void setTotalPersons(int totalPersons) {
        this.totalPersons.setText(totalPersons + " person(s) total");
    }
```
###### \java\seedu\address\ui\StatusBarFooter.java
``` java
        setTotalPersons(abce.data.getPersonList().size());
```
###### \java\seedu\address\ui\UiStyle.java
``` java
package seedu.address.ui;

import java.util.Random;

import javafx.scene.Scene;

/**
 * Contains the Styles / Colors that can be used in UI
 */

public class UiStyle {

    private static UiStyle instance = null;

    private static final String LIGHT_THEME_STYLE = "view/LightTheme.css";
    private static final String DARK_THEME_STYLE = "view/DarkTheme.css";

    private static Scene scene = null;

    private static final String STYLE_BACKGROUND_COLOR = "-fx-background-color: ";
    private static final String HEX_COLOR = "#%1$s";
    // Max hex color "FFFFFF" in integer
    private static final int MAX_HEX_COLOR = 16777216;

    private static Random random = new Random();

    public static UiStyle getInstance() {
        if (instance == null) {
            instance = new UiStyle();
        }

        return instance;
    }

    public static void setScene(Scene s) {
        scene = s;
        setToDarkTheme();
    }

    public static String getRandomHexColor() {
        return String.format(HEX_COLOR, Integer.toHexString(random.nextInt(MAX_HEX_COLOR)));
    }

    public static String getSpecificHexColor(String hexString) {
        return String.format(HEX_COLOR, hexString);
    }

    /**
     * @param color in hexadecimals
     * @return String to indicate background color
     */
    public static String getBackgroundStyle(String color) {
        return UiStyle.STYLE_BACKGROUND_COLOR + color;
    }

    //---------------------------------------------------------
    public static void setToLightTheme() {
        scene.getStylesheets().remove(DARK_THEME_STYLE);
        scene.getStylesheets().add(LIGHT_THEME_STYLE);
    }

    public static void setToDarkTheme() {
        scene.getStylesheets().remove(LIGHT_THEME_STYLE);
        scene.getStylesheets().add(DARK_THEME_STYLE);
    }

}
```
###### \resources\view\LightTheme.css
``` css
.background {
    -fx-background-color: derive(#5fb3d8, 20%);
    background-color: #c2e2f1; /* Used in the default.html file */
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
    -fx-text-fill: black;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
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
    -fx-base: #5fb3d8;
    -fx-control-inner-background: #5fb3d8;
    -fx-background-color: #5fb3d8;
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
    -fx-text-fill: black;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#70e1f5, 20%);
    -fx-border-color: transparent transparent transparent #70e1f5;
}

.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#5fb3d8, 20%);
}

.list-view {
    -fx-background-color: derive(#5fb3d8, 20%);
    -fx-background-insets: 0;
    -fx-padding: 0;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
}

.list-cell:filled:even {
    -fx-background-color: #5fb3d8;
}

.list-cell:filled:odd {
    -fx-background-color: #51a2e0;
}

.list-cell:filled:selected {
    -fx-background-color: #00fbfe;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: black;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#c2e2f1, 20%);
}

.pane-with-border {
     -fx-background-color: derive(#5fb3d8, 20%);
     -fx-border-color: derive(#5fb3d8, 10%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#5fb3d8, 20%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
}

.status-bar-with-border {
    -fx-background-color: derive(#5fb3d8, 30%);
    -fx-border-color: derive(#5fb3d8, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: black;
}

.grid-pane {
    -fx-background-color: derive(#5fb3d8, 30%);
    -fx-border-color: derive(#5fb3d8, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#5fb3d8, 30%);
}

.context-menu {
    -fx-background-color: derive(#5fb3d8, 50%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: derive(#5fb3d8, 20%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: black;
    -fx-opacity: 0.9;
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
    -fx-background-color: #5fb3d8;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: black;
      -fx-text-fill: #5fb3d8;
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
    -fx-background-color: #5fb3d8;
    -fx-text-fill: black;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: black;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #5fb3d8;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #5fb3d8;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#5fb3d8, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#70e1f5, 20%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#b1d6e9, 50%);
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
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #c2e2f1 #c2e2f1 #ffffff #c2e2f1;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: black;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #c2e2f1, transparent, #b1d6e9;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: black;
    -fx-background-color: #3e7b91;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}

/*Classes to use*/

.error {
    -fx-text-fill: #d06651 !important; /* The error class should always override the default text-fill style */
}

.list-cell:empty {
    /* Empty cells will not have alternating colours */
    -fx-background: #c2e2f1;
}

.tag-selector {
    -fx-border-width: 1;
    -fx-border-color: white;
    -fx-border-radius: 3;
    -fx-background-radius: 3;
}

.tooltip-text {
    -fx-text-fill: white;
}
```
###### \resources\view\StatusBarFooter.fxml
``` fxml
  <StatusBar styleClass="anchor-pane" fx:id="totalPersons" GridPane.columnIndex="1" />
```
