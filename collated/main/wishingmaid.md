# wishingmaid
###### \java\seedu\address\commons\util\ExtensionCheckerUtil.java
``` java
package seedu.address.commons.util;

/**
 * Helper functions for extensions of files
 */
public class ExtensionCheckerUtil {
    /**
     * @param ext cannot be null
     * @param requiredExt cannot be null
     * @return true if the ext is matches with any of the extensions in the requiredExt String array.
     */
    public static boolean isOfType(String ext, String[] requiredExt) {
        for (String i : requiredExt) {
            if (ext.toLowerCase().equals(i.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static String getExtension(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return ext;
    }
}
```
###### \java\seedu\address\logic\commands\AddPhotoCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.PhotoStorage;

/**
 * Adds a photo to the specified contact.
 * Incorrect formats of AddPhotoCommand will throw different exceptions.
 * */
public class AddPhotoCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addphoto";
    public static final String COMMAND_ALIAS = "ap";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds a photo to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_FILEPATH + " (must be valid filepath)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_FILEPATH + " C:/users/Desktop/imageFolder/Books";

    public static final String MESSAGE_ADD_PHOTO_SUCCESS = "New photo added to: %1$s";
    public static final String MESSAGE_DELETE_PHOTO_SUCCESS = "Removed photo from Person: %1$s";
    public static final String MESSAGE_NO_PHOTO_TO_DELETE = "This person has no photo to delete.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in PEERSONAL.";
    private final Index index;
    private final Photo photo;
    public AddPhotoCommand(Index index, Photo photo) {
        requireNonNull(index);
        requireNonNull(photo);
        this.index = index;
        this.photo = photo;
    }
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        if (personToEdit.getPhoto().getFilePath().isEmpty() && photo.getFilePath().isEmpty()) {
            throw new CommandException(MESSAGE_NO_PHOTO_TO_DELETE);
        } else if (!photo.getFilePath().equals("")) {
            try {
                //produces a new filepath and rewrites the new filepath to the photo object held by the contact
                PhotoStorage rewrite = new PhotoStorage(photo.getFilePath());
                photo.resetFilePath(rewrite.setNewFilePath());
            } catch (IOException e) {
                throw new CommandException(e.getMessage());
            }
        }
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getAge(), photo,
                personToEdit.getTags());
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException fpe) {
            throw new AssertionError("The target person cannot be missing.");
        }
        model.getFilteredPersonList();
        return new CommandResult(generateSuccessMessage(editedPerson));
    }
    /**Generate success message*/
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!photo.getFilePath().isEmpty()) {
            return String.format(MESSAGE_ADD_PHOTO_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_PHOTO_SUCCESS, personToEdit);
        }
    }



}
```
###### \java\seedu\address\logic\parser\AddPhotoCommandParser.java
``` java
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Photo;

/**
 * Parses input arguments and creates a new AddPhotoCommand object
 */
public class AddPhotoCommandParser implements Parser<AddPhotoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddPhotoCommand
     * and returns an AddPhotoCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddPhotoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);
        Index index;
        String filepath;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
            filepath = argMultimap.getValue(PREFIX_FILEPATH).orElse("");
            Photo photo = new Photo(filepath);
            return new AddPhotoCommand(index, photo);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE));
        } catch (IllegalArgumentException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }
}
```
###### \java\seedu\address\MainApp.java
``` java
    protected InitImageFolder imageFolder;
```
###### \java\seedu\address\MainApp.java
``` java
        logger.info("=============================[ Initializing PEERSONAL ]===========================");
```
###### \java\seedu\address\MainApp.java
``` java
        imageFolder = new InitImageFolder(userPrefs.getDisplayPicturesPath());
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<Photo> photoProperty() {
        return photo;
    }
    public Photo getPhoto() {
        return photo.get();
    }
    public void setPhoto(Photo photo) {
        this.photo.set(requireNonNull(photo));
    }
```
###### \java\seedu\address\model\person\Person.java
``` java

```
###### \java\seedu\address\model\person\Photo.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.io.File;

/**
 * Represents a Photo in the address book.
 */

public class Photo {
    public static final String URL_VALIDATION = "The filepath URL does not exist.";
    private static final String DEFAULT_FILEPATH = "";
    private String filepath;
    public Photo(String filepath) throws IllegalArgumentException {
        //this is to setup the default photo for contacts after it is added.
        requireNonNull(filepath);
        if (filepath.equals(DEFAULT_FILEPATH)) {
            this.filepath = DEFAULT_FILEPATH;
        } else {
            File file = new File(filepath);
            if (isValidFilePath(file)) {
                this.filepath = filepath;
            } else {
                throw new IllegalArgumentException(URL_VALIDATION);
            }
        }
    }

    public boolean isValidFilePath(File file) {
        return file.exists();
    }
    //the filepath of the image
    public String getFilePath() {
        return filepath;
    }
    /** It is guaranteed that the new filepath exists inside the resources folder */
    public void resetFilePath(String filepath) {
        this.filepath = filepath;
    }
    @Override
    public String toString() {
        return filepath;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Photo // instanceof handles nulls
                && this.filepath.equals(((Photo) other).filepath)); // state check
    }
    @Override
    public int hashCode() {
        return filepath.hashCode();
    }
}
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    private String displayPicturesPath = "displaypictures";
```
###### \java\seedu\address\model\UserPrefs.java
``` java
                && Objects.equals(displayPicturesPath, o.displayPicturesPath);
```
###### \java\seedu\address\model\UserPrefs.java
``` java
        return Objects.hash(guiSettings, addressBookFilePath, addressBookName, displayPicturesPath);
```
###### \java\seedu\address\model\UserPrefs.java
``` java
        sb.append("\nAddressBook image storage filepath : " + displayPicturesPath);
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getDisplayPicturesPath() {
        return displayPicturesPath;
    }
}
```
###### \java\seedu\address\storage\InitImageFolder.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * This class creates a image storage folder in the same directory as the addressbook jar file upon running
 * the main app.
 */
public class InitImageFolder {

    private static final Logger logger = LogsCenter.getLogger(InitImageFolder.class);
    public InitImageFolder(String destinationPath) throws IOException {
        requireNonNull(destinationPath);
        File file = new File(destinationPath);
        if (!file.exists()) {
            logger.info("Created " + destinationPath + "folder");
            file.mkdir();
        }
    }
}
```
###### \java\seedu\address\storage\PhotoStorage.java
``` java
package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import seedu.address.commons.util.ExtensionCheckerUtil;

/**
 * Represents the conversion of a local filepath inside the user's hard drive
 * into a new filepath that is local to the jar file of PEERSONAL.
 * Guarantees : The newly written filepath must exist if ImageIO.write is successful
 */
public class PhotoStorage {
    public static final String WRITE_FAILURE_MESSAGE = "Unable to write to local resource folder: displaypictures.\n"
            + "Make sure that the image type is supported and the image file cannot be hidden.\n"
            + "Supported types: JPEG, JPG, PNG.";
    private String[] allowedExt = null;
    private File fileReader = null;
    private String filePath = "";
    private File fileWriter = null;
    private BufferedImage imageReader = null;
    private String uniqueFileName;
    public PhotoStorage(String filePath) {
        this.filePath = filePath;
        imageReader = new BufferedImage(300, 400, BufferedImage.TYPE_INT_ARGB);
        allowedExt =  new String[]{"jpg", "png", "JPEG"};
    }

    public String setNewFilePath() throws IOException {
        requireNonNull(allowedExt);
        String ext = ExtensionCheckerUtil.getExtension(filePath);
        if (!ExtensionCheckerUtil.isOfType(ext, allowedExt)) {
            throw new IOException(WRITE_FAILURE_MESSAGE);
        }
        uniqueFileName = generateUniqueFileName();
        requireNonNull(uniqueFileName);
        String newFilePath = "displaypictures/" + uniqueFileName + "." + ext;
        try {
            fileReader = new File(filePath);
            imageReader = ImageIO.read(fileReader);
            fileWriter = new File(newFilePath);
            ImageIO.write(imageReader, ext, fileWriter);
            return newFilePath;
        } catch (IOException e) {
            throw new IOException(WRITE_FAILURE_MESSAGE);
        }
    }

    /** generates a unique file path that is to be saved into displaypictures */
    private String generateUniqueFileName() {
        UUID uuid = UUID.randomUUID();
        String uniqueFilePath = uuid.toString();
        return uniqueFilePath;
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
    @XmlElement(required = true)
    private String filepath;
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        filepath = source.getPhoto().getFilePath();
```
###### \java\seedu\address\storage\XmlAdaptedPerson.java
``` java
        final Photo photo = new Photo(this.filepath);
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /** randomises which motivational page is used from the resource images folder */
    private void setUpMotivationPage()  {
        Image image = new Image(getClass().getResource("/images/"
                + motivationPages[random.nextInt(motivationPages.length)]).toExternalForm());
        splashPage.setImage(image);
    }
```
###### \java\seedu\address\ui\ExtendedPersonDetails.java
``` java
    private static final String FXML = "ExtendDetailsPerson.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final String defaultPicture = "/images/PEERSONAL_icon.png";
    private final String phoneIcon = "/images/Mobile-Phone-icon.png";
    private final String addressIcon = "/images/address.png";
    private final String emailIcon = "/images/email-icon.png";
    private final String birthdayIcon = "/images/BirthdayIcon.png";
    private final String remarkIcon = "/images/RemarkIcon.png";
    private final String ageIcon = "/images/AgeIcon.png";
```
###### \java\seedu\address\ui\ExtendedPersonDetails.java
``` java
    /**
     * This class loads the persons details in the UI for the extended person's panel.
     * */
    public ExtendedPersonDetails() {
        super(FXML);
        registerAsAnEventHandler(this);
        setUpUi();
    }
```
###### \java\seedu\address\ui\ExtendedPersonDetails.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
    private void setUpUi() {
        Image image = new Image(getClass().getResource(defaultPicture).toExternalForm());
        setCircle(circle, image);
        Image imagePhone = new Image(getClass().getResource(phoneIcon).toExternalForm());
        setCircle(circlePhone, imagePhone);
        Image imageAddress = new Image(getClass().getResource(addressIcon).toExternalForm());
        setCircle(circleAddress, imageAddress);
        Image imageEmail = new Image(getClass().getResource(emailIcon).toExternalForm());
        setCircle(circleEmail, imageEmail);
        Image imageRemark = new Image(getClass().getResource(remarkIcon).toExternalForm());
        setCircle(circleRemark, imageRemark);
        Image imageBirthday = new Image(getClass().getResource(birthdayIcon).toExternalForm());
        setCircle(circleBirthday, imageBirthday);
        Image imageAge = new Image(getClass().getResource(ageIcon).toExternalForm());
        setCircle(circleAge, imageAge);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static final String FXML = "PersonListCard.fxml";
    private static String[] colors = { "red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private ImageView imageView;
    @FXML
    private Circle circle;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        setImage(person);
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /** Changes the tag colour*/
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /** Checks if the user has added any photo to the specific contact*/
    private void setImage(ReadOnlyPerson person) {
        String url = person.getPhoto().getFilePath(); //gets the filepath directly from the resources folder.
        if (url.equals("")) {
            Image image = new Image(getClass().getResource("/images/noPhoto.png").toExternalForm());
            setCircle(image);
        } else {
            Image image = new Image("file:" + person.getPhoto().getFilePath());
            setCircle(image);
        }
    }
    private void setCircle(Image image) {
        ImagePattern pattern = new ImagePattern(image);
        circle.setFill(pattern);
        circle.setEffect(new DropShadow(10, Color.STEELBLUE));
    }
```
###### \resources\view\ExtendDetailsPerson.fxml
``` fxml

<VBox maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" style="-fx-background-image: images/Night.jpg;" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <Circle fx:id="circle" fill="#bfcbd7" radius="46.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" translateX="60.0" translateY="20.0" />
      <Line endX="100.0" startX="-100.0" stroke="#176fb2" strokeWidth="2.0" translateY="32.0" />
      <Label fx:id="name" translateX="45.0" translateY="35.0" wrapText="true">
         <font>
            <Font size="25.0" />
         </font></Label>
      <Label fx:id="phone" translateX="20.0" translateY="40.0">
         <graphic>
            <Circle fx:id="circlePhone" fill="#ffffff40" radius="11.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" />
         </graphic></Label>
      <Label fx:id="address" prefWidth="240.0" translateX="20.0" translateY="40.0" wrapText="true">
         <graphic>
            <Circle fx:id="circleAddress" fill="#ffffff40" radius="11.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" />
         </graphic></Label>
      <Label fx:id="email" prefWidth="240.0" translateX="20.0" translateY="40.0" wrapText="true">
         <graphic>
            <Circle fx:id="circleEmail" fill="#212cff3f" radius="11.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" />
         </graphic></Label>
      <Label fx:id="remark" prefWidth="240.0" translateX="20.0" translateY="40.0" wrapText="true">
         <graphic>
            <Circle fx:id="circleRemark" fill="#212cff3f" radius="11.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" />
         </graphic></Label>
      <Label fx:id="birthday" prefWidth="240.0" translateX="20.0" translateY="40.0" wrapText="true">
         <graphic>
            <Circle fx:id="circleBirthday" fill="#212cff3f" radius="11.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" />
         </graphic></Label>
      <Label fx:id="age" prefWidth="240.0" translateX="20.0" translateY="43.0">
         <graphic>
            <Circle fx:id="circleAge" fill="#212cff40" radius="11.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" />
         </graphic></Label>
      <ImageView fx:id="background" fitHeight="408.0" fitWidth="231.0" opacity="0.16" pickOnBounds="true" preserveRatio="true" rotate="18.4" scaleX="3.0" scaleY="5.0" translateY="-200.0">
         <image>
            <Image url="@../images/night.jpg" />
         </image>
      </ImageView>
   </children>
</VBox>
```
