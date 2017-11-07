# nguyenvanhoang7398
###### /java/seedu/address/logic/commands/AddMultipleByTsvCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.StringJoiner;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Command to add multiple contacts at once
 */
public class AddMultipleByTsvCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addMulTsv";
    public static final String COMMAND_ALIAS = "addMT";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds multiple people to the address book "
            + "given a tsv (tab separated value) txt file containing their contact information. "
            + "Parameters: TSV_PATH\n"
            + "Example: " + COMMAND_WORD + " D:/Contacts.txt";

    public static final String MESSAGE_SUCCESS = "%d new person (people) added";
    public static final String MESSAGE_DUPLICATE_PERSON = "%d new person (people) duplicated";
    public static final String MESSAGE_NUMBER_OF_ENTRIES_FAILED = "%d entry (entries) failed: ";
    public static final String MESSAGE_FILE_NOT_FOUND = "The system cannot find the file specified";

    private final ArrayList<Person> toAdd;
    private final ArrayList<Integer> failedEntries;
    private final boolean isFileFound;

    public AddMultipleByTsvCommand(ArrayList<ReadOnlyPerson> toAddPeople, ArrayList<Integer> failedEntries,
                                   boolean isFileFound) {
        this.toAdd = new ArrayList<Person>();
        this.failedEntries = failedEntries;
        this.isFileFound = isFileFound;
        for (ReadOnlyPerson person: toAddPeople) {
            toAdd.add(new Person(person));
        }
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        if (!isFileFound) {
            return new CommandResult(MESSAGE_FILE_NOT_FOUND);
        }
        int numAdded = 0;
        int numDuplicated = 0;

        for (Person person: toAdd) {
            try {
                model.addPerson(person);
                numAdded++;
            } catch (DuplicatePersonException e) {
                numDuplicated++;
            }
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, numAdded) + ", "
                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated) + ", "
                + String.format(MESSAGE_NUMBER_OF_ENTRIES_FAILED, failedEntries.size())
                + joinFailedEntries(failedEntries));
    }

    /**
     * Join the list of integers of failed entries into string
     * @param failedEntries
     * @return
     */
    private static String joinFailedEntries(ArrayList<Integer> failedEntries) {
        StringJoiner joiner = new StringJoiner(", ");
        for (Integer entry: failedEntries) {
            joiner.add(entry.toString());
        }
        return joiner.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMultipleByTsvCommand // instanceof handles nulls
                && toAdd.equals(((AddMultipleByTsvCommand) other).toAdd));
    }

}
```
###### /java/seedu/address/logic/commands/FindTagCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose tag contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */

public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_ALIAS = "fitg";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified tags (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friends family\n";

    private final TagsContainKeywordsPredicate predicate;

    public FindTagCommand(TagsContainKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagCommand) other).predicate)); // state check
    }

}
```
###### /java/seedu/address/logic/ContactTsvReader.java
``` java
package seedu.address.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Occupation;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Website;
import seedu.address.model.tag.Tag;

/**
 * Reads tsv file
 */
public class ContactTsvReader {
    private String contactTsvFilePath;
    private ArrayList<ReadOnlyPerson> toAddPeople;
    private ArrayList<Integer> failedEntries;

    public ContactTsvReader(String contactTsvFilePath) {
        this.contactTsvFilePath = contactTsvFilePath;
    }

    public ArrayList<ReadOnlyPerson> getToAddPeople() {
        return toAddPeople;
    }

    public ArrayList<Integer> getFailedEntries() {
        return failedEntries;
    }

    /**
     * Read contacts from the given file path and update toAddPeople and failedEntries
     * @throws ParseException
     * @throws IOException
     */
    public void readContactFromFile() throws ParseException, IOException {

        BufferedReader bufferedReader;
        toAddPeople = new ArrayList<ReadOnlyPerson>();
        failedEntries = new ArrayList<>();

        String line;
        bufferedReader = new BufferedReader(new FileReader(contactTsvFilePath));
        int i = 0;

        // How to read file in java line by line?
        while ((line = bufferedReader.readLine()) != null) {
            if (i != 0) {
                try {
                    ArrayList<String> columns = tsvLinetoArrayList(line);
                    Name name = ParserUtil.parseName(checkEmptyAndReturn(retrieveColumnField(columns, 0)))
                            .get();
                    Occupation occupation = ParserUtil.parseOccupation(checkEmptyAndReturn(retrieveColumnField(columns,
                            1))).get();
                    Phone phone = ParserUtil.parsePhone(checkEmptyAndReturn(retrieveColumnField(columns, 2)))
                            .get();
                    Email email = ParserUtil.parseEmail(checkEmptyAndReturn(retrieveColumnField(columns, 3)))
                            .get();
                    Address address = ParserUtil.parseAddress(checkEmptyAndReturn(retrieveColumnField(columns, 4)))
                            .get();
                    Website website = ParserUtil.parseWebsite(checkEmptyAndReturn(retrieveColumnField(columns, 5)))
                            .get();
                    Set<Tag> tagList = ParserUtil.parseTags(new ArrayList<String>(
                            Arrays.asList(retrieveColumnField(columns, 6)
                                    .replaceAll("^[,\"\\s]+", "")
                                    .replace("\"", "")
                                    .split("[,\\s]+"))));
                    Remark remark = new Remark("");
                    ReadOnlyPerson toAddPerson = new Person(name, occupation, phone, email, address, remark, website,
                            tagList);
                    toAddPeople.add(toAddPerson);
                } catch (IllegalValueException ive) {
                    throw new ParseException(ive.getMessage(), ive);
                } catch (NoSuchElementException nsee) {
                    failedEntries.add(i);
                }
            }
            i++;
        }

        bufferedReader.close();
    }

    /**
     * Check if a given string is empty and return Optional object accordingly
     * @param valueStr
     * @return
     */
    private static Optional<String> checkEmptyAndReturn(String valueStr) {
        Optional<String> result = valueStr.length() < 1 ? Optional.empty() : Optional.of(valueStr);
        System.out.println(result);
        return result;
    }

    /**
     * Convert a line in tsv file to list of string values of fields
     * @param line
     * @return
     */
    private static ArrayList<String> tsvLinetoArrayList(String line) {
        final String emptyFieldValue = "";
        ArrayList<String> result = new ArrayList<String>();

        if (line != null) {
            String[] splitData = line.split("\t");
            for (int i = 0; i < splitData.length; i++) {
                if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
                    result.add(splitData[i].trim());
                } else {
                    result.add(emptyFieldValue);
                }
            }
        }

        return result;
    }

    /**
     * Retrieve string value of a field given the columns and its index
     * @param columns
     * @param index
     * @return
     */
    private static String retrieveColumnField(ArrayList<String> columns, int index) {
        final String outOfBoundValue = "";

        try {
            return columns.get(index).replace("\"", "");
        } catch (IndexOutOfBoundsException e) {
            return outOfBoundValue;
        }
    }
}
```
###### /java/seedu/address/logic/parser/AddMultipleByTsvCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.logic.ContactTsvReader;
import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Parser for AddMultipleByTsvCommand
 */
public class AddMultipleByTsvCommandParser implements Parser<AddMultipleByTsvCommand> {

    /**
     * Parse arguments given by AddressBookParser
     * @param args
     * @return
     * @throws ParseException
     */
    public AddMultipleByTsvCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMultipleByTsvCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String contactTsvFilePath = nameKeywords[0];
        ContactTsvReader contactTsvReader = new ContactTsvReader(contactTsvFilePath);
        boolean isFileFound;
        ArrayList<ReadOnlyPerson> toAddPeople = new ArrayList<ReadOnlyPerson>();
        ArrayList<Integer> failedEntries = contactTsvReader.getFailedEntries();

        try {
            contactTsvReader.readContactFromFile();
            toAddPeople = contactTsvReader.getToAddPeople();
            failedEntries = contactTsvReader.getFailedEntries();
            isFileFound = true;
        } catch (IOException e) {
            isFileFound = false;
        }

        return new AddMultipleByTsvCommand(toAddPeople, failedEntries, isFileFound);
    }
}
```
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
        case AddMultipleByTsvCommand.COMMAND_WORD:
        case AddMultipleByTsvCommand.COMMAND_ALIAS:
            return new AddMultipleByTsvCommandParser().parse(arguments);
```
###### /java/seedu/address/logic/parser/FindTagCommandParser.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.TagsContainKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindTagCommand object
 */

public class FindTagCommandParser implements Parser<FindTagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagCommand
     * and returns an FindTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public FindTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindTagCommand(new TagsContainKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
```
###### /java/seedu/address/MainApp.java
``` java
    /**
     * Initialize empty storage folder to store profile picture
     */
    private void initEmptyStorage() {
        boolean success = (new File(StorageManager.EMPTY_STORAGE_DEFAULT_PATH)).mkdirs();
        if (!success) {
            logger.warning("Problem while initializing empty storage.");
        }
    }
```
###### /java/seedu/address/model/person/Person.java
``` java
    private static final String DEFAULT_NAME = "Full Name";
    private static final String DEFAULT_OCCUPATION = "Google, Software engineer";
    private static final String DEFAULT_PHONE = "123456";
    private static final String DEFAULT_EMAIL = "fullname@gmail.com";
    private static final String DEFAULT_ADDRESS = "Singapore";
    private static final String DEFAULT_REMARK = "funny";
    private static final String DEFAULT_WEBSITE = "https://www.google.com";
    private static final String DEFAULT_TAG = "me";
```
###### /java/seedu/address/model/person/Person.java
``` java
    public Person() {
        try {
            this.name = new SimpleObjectProperty<>(new Name(DEFAULT_NAME));
            this.occupation = new SimpleObjectProperty<>(new Occupation(DEFAULT_OCCUPATION));
            this.phone = new SimpleObjectProperty<>(new Phone(DEFAULT_PHONE));
            this.email = new SimpleObjectProperty<>(new Email(DEFAULT_EMAIL));
            this.address = new SimpleObjectProperty<>(new Address(DEFAULT_ADDRESS));
            this.remark = new SimpleObjectProperty<>(new Remark(DEFAULT_REMARK));
            this.website = new SimpleObjectProperty<>(new Website(DEFAULT_WEBSITE));
            List<Tag> tagList = new ArrayList<>();
            tagList.add(new Tag(DEFAULT_TAG));
            this.tags = new SimpleObjectProperty<>(new UniqueTagList(new HashSet<>(tagList)));
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
    }
```
###### /java/seedu/address/model/person/TagsContainKeywordsPredicate.java
``` java
package seedu.address.model.person;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */

public class TagsContainKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagsContainKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * @param setTags
     * @return
     */

    private String stringifySetTags(Set<Tag> setTags) {
        StringBuilder setTagsString = new StringBuilder();
        final String delimiter = " ";

        Iterator<Tag> tagIterator = setTags.iterator();
        while (tagIterator.hasNext()) {
            Tag checkingTag = tagIterator.next();
            setTagsString.append(checkingTag.tagName);
            setTagsString.append(delimiter);
        }

        return setTagsString.toString();
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(stringifySetTags(person.getTags()), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagsContainKeywordsPredicate) other).keywords)); // state check
    }
}
```
###### /java/seedu/address/ui/MainWindow.java
``` java
    @FXML
    private StackPane profilePlaceholder;
```
###### /java/seedu/address/ui/MainWindow.java
``` java
        profilePanel = new ProfilePanel(primaryStage);
        profilePlaceholder.getChildren().add(profilePanel.getRoot());
```
###### /java/seedu/address/ui/ProfilePanel.java
``` java
package seedu.address.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Profile Panel of the app
 */
public class ProfilePanel extends UiPart<Region> {

    private static final String FXML = "ProfilePanel.fxml";
    private static final String DEFAULT_IMAGE_STORAGE_PREFIX = "data/";
    private static final String DEFAULT_IMAGE_STORAGE_SUFFIX = ".png";
    private static final String DEFAULT_PROFILE_PICTURE_PATH = "/images/default_profile_picture.png";
    private static final String DEFAULT_PROFILE_BACKGROUND_PATH = "/images/profile_background.jpg";
    private static String[] colors = { "red", "yellow", "blue", "orange", "indigo", "green", "violet", "black" };
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final FileChooser fileChooser = new FileChooser();
    private ReadOnlyPerson person;
    private Stage primaryStage;

    @FXML
    private HBox profilePane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label occupation;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private Label website;
    @FXML
    private FlowPane tags;
    @FXML
    private javafx.scene.image.ImageView profilePicture;
    @FXML
    private Button changePictureButton;

    public ProfilePanel(Stage primaryStage) {
        super(FXML);
        this.person = new Person();
        this.primaryStage = primaryStage;
        initChangePictureButton();
        initStyle();
        refreshState();
        registerAsAnEventHandler(this);
    }

    private void refreshState() {
        bindListeners();
        initPicture();
        initTags();
    }

    /**
     * Initialize change picture button
     */
    private void initChangePictureButton() {
        changePictureButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setExtFilters(fileChooser);
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            saveImageToStorage(file);
                            refreshState();
                        }
                    }
                }
        );
    }

    private void setExtFilters(FileChooser chooser) {
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    /**
     * Save a given image file to storage
     * @param file
     */
    private void saveImageToStorage(File file) {
        Image image = new Image(file.toURI().toString());
        String phoneNum = person.getPhone().value;

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png",
                    new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum + DEFAULT_IMAGE_STORAGE_SUFFIX));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Initialize profile picture
     */
    private void initPicture() {
        BufferedImage bufferedImage;
        String phoneNum = person.getPhone().value;

        try {
            bufferedImage = ImageIO.read(new File(DEFAULT_IMAGE_STORAGE_PREFIX + phoneNum
                    + DEFAULT_IMAGE_STORAGE_SUFFIX));
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            profilePicture.setImage(image);
        } catch (IOException ioe1) {
            Image image = new Image(MainApp.class.getResourceAsStream(DEFAULT_PROFILE_PICTURE_PATH));
            profilePicture.setImage(image);
        }
    }

    /**
     * Initialize panel's style such as color
     */
    private void initStyle() {
        profilePane.setStyle(String.format("-fx-background-image: url(%s); "
                        + "-fx-background-position: center center; -fx-background-size: cover;",
                DEFAULT_PROFILE_BACKGROUND_PATH));
    }

    /**
     * Change current displayed profile
     * @param person
     */
    public void changeProfile(ReadOnlyPerson person) {
        this.person = person;
        refreshState();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners() {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        occupation.textProperty().bind(Bindings.convert(person.occupationProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
        website.textProperty().bind(Bindings.convert(person.websiteProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            initTags();
        });
    }

    /**
     *javadoc comment
     */
    private void initTags() {
        //person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    //The method below retrieves the color for the specific tag
    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }
        return tagColors.get(tagValue);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeProfile(event.getNewSelection().person);
    }
}
```
###### /resources/view/MainWindow.fxml
``` fxml
    <StackPane fx:id="profilePlaceholder" minWidth="320" >
      <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
      </padding>
    </StackPane>

    <StackPane fx:id="browserPlaceholder" minWidth="750" prefWidth="750" >
      <padding>
        <Insets top="10" right="10" bottom="10" left="10" />
      </padding>
    </StackPane>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### /resources/view/ProfilePanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Button?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<HBox id="profilePane" fx:id="profilePane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="CENTER" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
         <ImageView fx:id="profilePicture" fitHeight="200.0" fitWidth="150.0" pickOnBounds="true" preserveRatio="true">
            <VBox.margin>
               <Insets bottom="15" top="15" />
            </VBox.margin>
         </ImageView>
            <HBox alignment="CENTER" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
            </HBox>
            <FlowPane fx:id="tags" alignment="CENTER" />
            <Label fx:id="occupation" styleClass="cell_small_label" text="\$occupation" />
            <Label fx:id="phone" styleClass="cell_small_label" text="\$phone" />
            <Label fx:id="address" styleClass="cell_small_label" text="\$address" />
            <Label fx:id="email" styleClass="cell_small_label" text="\$email" />
            <Label fx:id="website" styleClass="cell_small_label" text="\$website" />
            <Label fx:id="remark" styleClass="cell_small_label" text="\$remark" />
         <Button fx:id="changePictureButton" mnemonicParsing="false" prefWidth="200.0" text="Change photo">
            <VBox.margin>
               <Insets />
            </VBox.margin>
            <font>
               <Font size="24.0" />
            </font></Button>
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
