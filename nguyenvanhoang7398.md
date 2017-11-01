# nguyenvanhoang7398
###### \src\main\java\seedu\address\logic\ContactTsvReader.java
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
###### \src\main\java\seedu\address\logic\parser\AddMultipleByTsvCommandParser.java
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
###### \src\main\java\seedu\address\logic\parser\FindTagCommandParser.java
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
###### \src\main\java\seedu\address\model\person\TagsContainKeywordsPredicate.java
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
###### \src\main\java\seedu\address\ui\ProfilePanel.java
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
        profilePane.setStyle("-fx-background-color: #FFFFFF;");
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
###### \src\main\resources\view\ProfilePanel.fxml
``` fxml
<?xml version="1.0" encoding="UTF-8"?>

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
         <Button fx:id="changePictureButton" mnemonicParsing="false" prefWidth="119.0" text="Change" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### \src\test\java\seedu\address\logic\commands\AddMultipleByTsvCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.TsvFileBuilder;
import seedu.address.testutil.TypicalTsvFiles;

public class AddMultipleByTsvCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullTsvFile_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_tsvFileAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        boolean isFileFound = validTsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = validTsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = validTsvFile.getFailedEntries();

        CommandResult commandResult = getAddMultipleByTsvCommandForPerson(toAddPeople, failedEntries,
                isFileFound, modelStub).execute();

        assertEquals(String.format(TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_SUCCESS), commandResult.feedbackToUser);
        assertEquals(toAddPeople, modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicatePersonException();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        boolean isFileFound = validTsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = validTsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = validTsvFile.getFailedEntries();

        CommandResult commandResult = getAddMultipleByTsvCommandForPerson(toAddPeople, failedEntries,
                isFileFound, modelStub).execute();

        assertEquals(String.format(TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_ALL_DUPLICATED),
                commandResult.feedbackToUser);
    }

    private AddMultipleByTsvCommand getAddMultipleByTsvCommandForPerson(ArrayList<ReadOnlyPerson> toAddPeople,
                                                                        ArrayList<Integer> failedEntries,
                                                                        boolean isFileFound, Model model) {
        AddMultipleByTsvCommand command = new AddMultipleByTsvCommand(toAddPeople, failedEntries, isFileFound);
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return command;
    }

    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicatePersonException extends ModelStub {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
```
###### \src\test\java\seedu\address\logic\commands\FindTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.RecentlyDeletedQueue;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TagsContainKeywordsPredicate;

public class FindTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagsContainKeywordsPredicate firstPredicate =
                new TagsContainKeywordsPredicate(Collections.singletonList("first"));
        TagsContainKeywordsPredicate secondPredicate =
                new TagsContainKeywordsPredicate(Collections.singletonList("second"));

        FindTagCommand findFirstCommand = new FindTagCommand(firstPredicate);
        FindTagCommand findSecondCommand = new FindTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagCommand findFirstCommandCopy = new FindTagCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FindTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 5);
        FindTagCommand command = prepareCommand("friends owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON, CARL, FIONA, GEORGE));
    }

    /**
     *
     * @param userInput
     * @return
     */
    private FindTagCommand prepareCommand(String userInput) {
        FindTagCommand command =
                new FindTagCommand(new TagsContainKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack(), new RecentlyDeletedQueue());
        return command;
    }

    /**
     *
     * @param command
     * @param expectMessage
     * @param expectedList
     */
    private void assertCommandSuccess(FindTagCommand command, String expectMessage, List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \src\test\java\systemtests\AddMultipleByTsvCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_NUMBER_OF_ENTRIES_FAILED;
import static seedu.address.logic.commands.AddMultipleByTsvCommand.MESSAGE_SUCCESS;

import java.util.ArrayList;
import java.util.StringJoiner;

import org.junit.Test;

import seedu.address.logic.commands.AddMultipleByTsvCommand;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.TsvFileBuilder;
import seedu.address.testutil.TypicalTsvFiles;


public class AddMultipleByTsvCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void addMultipleByTsv() {
        Model model = getModel();
        TsvFileBuilder validTsvFile = new TsvFileBuilder();
        String command = AddMultipleByTsvCommand.COMMAND_WORD + " " + TypicalTsvFiles.PERFECT_TSV_FILE_PATH;
        String expectedResultMessage = TypicalTsvFiles.PERFECT_TSV_FILE_MESSAGE_SUCCESS;
        assertCommandSuccess(command, validTsvFile);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, TsvFileBuilder)}. Executes {@code command}
     * instead.
     * @see AddMultipleByTsvCommandSystemTest#assertCommandSuccess(String, TsvFileBuilder)
     */
    private void assertCommandSuccess(String command, TsvFileBuilder tsvFile) {
        Model expectedModel = getModel();
        boolean isFileFound = tsvFile.getIsFileFound();
        ArrayList<ReadOnlyPerson> toAddPeople = tsvFile.getToAddPeople();
        ArrayList<Integer> failedEntries = tsvFile.getFailedEntries();

        if (!isFileFound) {
            throw new IllegalArgumentException(AddMultipleByTsvCommand.MESSAGE_FILE_NOT_FOUND);
        }
        int numAdded = 0;
        int numDuplicated = 0;
        ArrayList<Person> toAdd = new ArrayList<Person>();

        for (ReadOnlyPerson person: toAddPeople) {
            toAdd.add(new Person(person));
        }
        for (Person person: toAdd) {
            try {
                expectedModel.addPerson(person);
                numAdded++;
            } catch (DuplicatePersonException e) {
                numDuplicated++;
            }
        }
        String expectedResultMessage = String.format(MESSAGE_SUCCESS, numAdded) + ", "
                + String.format(MESSAGE_DUPLICATE_PERSON, numDuplicated) + ", "
                + String.format(MESSAGE_NUMBER_OF_ENTRIES_FAILED, failedEntries.size())
                + joinFailedEntries(failedEntries);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddMultipleByTsvCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Join list of failed entries as integers to a single string
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
}
```
###### \src\test\java\systemtests\FindTagCommandSystemTest.java
``` java
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_COLLEAGUES;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_FAMILY;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_FRIENDS;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_OWESMONEY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class FindTagCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void findtag() {

        /* Case: find multiple persons in address book with a single tag
         * -> 1 persons found
         */
        String command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_OWESMONEY;
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, BENSON);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book with single tag, command with leading spaces and trailing spaces
         * -> 5 persons found
         */
        command = "   " + FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS + "   ";
        ModelHelper.setFilteredList(expectedModel, ALICE, BENSON, CARL, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_OWESMONEY + " " + KEYWORD_MATCHING_FAMILY;
        ModelHelper.setFilteredList(expectedModel, BENSON, ELLE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords, in reverse order -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 keywords with 1 repeat -> 2 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY
                + " " + KEYWORD_MATCHING_FAMILY;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple persons in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 persons found
         */
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FAMILY + " " + KEYWORD_MATCHING_OWESMONEY
                + " " + "NoneMatchingKeyword";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: undo previous find tag command -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo previous find tag command -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: find same persons in address book after deleting 1 of them -> 4 person found */
        executeCommand(DeleteCommand.COMMAND_WORD + " 1");
        assert !getModel().getAddressBook().getPersonList().contains(BENSON);
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ALICE, CARL, FIONA, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is same as name but of different case -> 4 person found */
        command = FindTagCommand.COMMAND_WORD + " FriEnds";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, keyword is substring of name -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " frie";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person in address book, name is substring of keyword -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " friendss";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find person not in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " siblings";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find occupation of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getOccupation().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find phone number of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find website of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getWebsite().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find name of person in address book -> 0 persons found */
        command = FindTagCommand.COMMAND_WORD + " " + DANIEL.getName().fullName;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find while a person is selected -> selected card deselected */
        showAllPersons();
        selectPerson(Index.fromOneBased(1));
        assert !getPersonListPanel().getHandleToSelectedCard().getName().equals(DANIEL.getName().fullName);
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_COLLEAGUES;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardDeselected();

        /* Case: find person in empty address book -> 0 persons found */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        command = FindTagCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_FRIENDS;
        expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "FiNdtAg" + " " + KEYWORD_MATCHING_FRIENDS;
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_PERSONS_LISTED_OVERVIEW, expectedModel.getFilteredPersonList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
