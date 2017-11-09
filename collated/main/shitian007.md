# shitian007
###### /resources/view/PersonPanel.fxml
``` fxml
<HBox prefHeight="400" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <children>
      <VBox alignment="TOP_CENTER" prefHeight="400.0">
        <children>
          <ImageView fx:id="picture" fitHeight="1.0" fitWidth="1.0" />
          <Label fx:id="name" text="\$name" />
          <FlowPane fx:id="tags" alignment="CENTER">
               <padding>
                  <Insets bottom="5.0" top="5.0" />
               </padding></FlowPane>
          <HBox alignment="CENTER" prefHeight="10.0" prefWidth="200.0">
            <children>
              <Label text="Phone: " />
              <Label fx:id="phone" text="\$phone" />
            </children>
          </HBox>
          <HBox alignment="CENTER" prefHeight="10.0">
            <children>
              <Label text="Room Address: " />
              <Label fx:id="address" text="\$address" />
            </children>
          </HBox>
          <HBox alignment="TOP_CENTER" prefHeight="25.0">
            <children>
              <Label text="Email: " />
              <Label fx:id="email" text="\$email" />
            </children>
          </HBox>
            <HBox alignment="CENTER" prefHeight="50.0" prefWidth="200.0">
               <children>
                  <Button mnemonicParsing="false" onAction="#handleAddImage" text="+Image">
                     <HBox.margin>
                        <Insets right="80.0" />
                     </HBox.margin>
                  </Button>
                  <Button mnemonicParsing="false" onAction="#handleDeleteImage" text="-Image" />
               </children>
               <padding>
                  <Insets left="50.0" right="50.0" />
               </padding>
            </HBox>
        </children>
      </VBox>
    </children>
</HBox>
```
###### /java/seedu/room/ui/PersonPanel.java
``` java
/**
 * The person information panel of the app.
 */
public class PersonPanel extends UiPart<Region> {

    private static final String FXML = "PersonPanel.fxml";
    private static String[] colors = {"red", "yellow", "blue", "orange", "brown", "green", "pink", "black", "grey"};
    private static HashMap<String, String> tagColors = new HashMap<String, String>();
    private static Random random = new Random();

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private final Logic logic;

    private ReadOnlyPerson person;

    @FXML
    private ImageView picture;
    @FXML
    private VBox informationPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;

    public PersonPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        loadDefaultScreen();
        registerAsAnEventHandler(this);
    }

    private static String getColorForTag(String tagValue) {
        if (!tagColors.containsKey(tagValue)) {
            tagColors.put(tagValue, colors[random.nextInt(colors.length)]);
        }

        return tagColors.get(tagValue);
    }

    /**
     * Sets the default parameters when the app starts up and no one is selected
     */
    private void loadDefaultScreen() {
        name.textProperty().setValue("No Resident Selected");
        phone.textProperty().setValue("-");
        address.textProperty().setValue("-");
        email.textProperty().setValue("-");
    }

    /**
     * loads the selected person's information to be displayed.
     */
    private void loadPersonInformation(ReadOnlyPerson person) {
        this.person = updatePersonFromLogic(person);
        name.textProperty().setValue(person.getName().toString());
        phone.textProperty().setValue(person.getPhone().toString());
        address.textProperty().setValue(person.getRoom().toString());
        email.textProperty().setValue(person.getEmail().toString());
        initTags();
        initImage();
    }

    /**
     * @param person whose image is to be updated within the filtered persons list
     * @return the updated person
     */
    private ReadOnlyPerson updatePersonFromLogic(ReadOnlyPerson person) {
        List<ReadOnlyPerson> personList = logic.getFilteredPersonList();
        for (ReadOnlyPerson p : personList) {
            if (p.getName().toString().equals(person.getName().toString())
                    && p.getPhone().toString().equals(person.getPhone().toString())) {
                return p;
            }
        }
        return null;
    }

    /**
     * Sets a background color for each tag.
     * @param
     */
    private void initTags() {
        tags.getChildren().clear();
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-background-color: " + getColorForTag(tag.tagName));
            tags.getChildren().add(tagLabel);
        });
    }

    /**
     * Initializes image for every person in person card
     */
    private void initImage() {
        try {
            File picFile = new File(person.getPicture().getPictureUrl());
            if (picFile.exists()) {
                FileInputStream fileStream = new FileInputStream(picFile);
                Image personPicture = new Image(fileStream);
                picture.setImage(personPicture);
            } else {
                initJarImage();
            }
            picture.setFitHeight(person.getPicture().PIC_HEIGHT);
            picture.setFitWidth(person.getPicture().PIC_WIDTH);
            informationPane.getChildren().add(picture);
            picture.setOnMouseClicked((MouseEvent e) -> {
                handleAddImage();
            });
        } catch (Exception e) {
            System.out.println("Image not found");
        }
    }

    /**
     * Handle loading of image from both within and outside of jar file
     */
    public void initJarImage() throws FileNotFoundException {
        try {
            InputStream in = this.getClass().getResourceAsStream(person.getPicture().getJarPictureUrl());
            Image personPicture = new Image(in);
            picture.setImage(personPicture);
            person.getPicture().setJarResourcePath();
        } catch (Exception e) {
            File picFile = new File(person.getPicture().getJarPictureUrl());
            FileInputStream fileStream = new FileInputStream(picFile);
            Image personPicture = new Image(fileStream);
            picture.setImage(personPicture);
            person.getPicture().setJarResourcePath();
        }
    }

    /**
     * Handler for adding image to person
     */
    @FXML
    private void handleAddImage() {
        FileChooser picChooser = new FileChooser();
        File selectedPic = picChooser.showOpenDialog(null);
        if (selectedPic != null) {
            try {
                person.getPicture().setPictureUrl(person.getName().toString() + person.getPhone().toString() + ".jpg");
                logic.updatePersonListPicture((Person) person);
                if (person.getPicture().checkJarResourcePath()) {
                    ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getJarPictureUrl()));
                    FileInputStream fileStream = new FileInputStream(person.getPicture().getJarPictureUrl());
                    Image newPicture = new Image(fileStream);
                    picture.setImage(newPicture);
                } else {
                    ImageIO.write(ImageIO.read(selectedPic), "jpg", new File(person.getPicture().getPictureUrl()));
                    FileInputStream fileStream = new FileInputStream(person.getPicture().getPictureUrl());
                    Image newPicture = new Image(fileStream);
                    picture.setImage(newPicture);
                }
            } catch (Exception e) {
                System.out.println(e + "Cannot set Image of person");
            }
        } else {
            System.out.println("Please select an Image File");
        }
    }

    /**
     * Handler for deleting a person's image
     */
    @FXML
    private void handleDeleteImage() {
        try {
            person.getPicture().resetPictureUrl();
            if (person.getPicture().checkJarResourcePath()) {
                System.out.println(person.getPicture().getJarPictureUrl());
                InputStream in = this.getClass().getResourceAsStream(person.getPicture().getJarPictureUrl());
                person.getPicture().setJarResourcePath();
                Image personPicture = new Image(in);
                picture.setImage(personPicture);
            } else {
                person.getPicture().resetPictureUrl();
                File picFile = new File(person.getPicture().getPictureUrl());
                FileInputStream fileStream = new FileInputStream(picFile);
                Image personPicture = new Image(fileStream);
                picture.setImage(personPicture);
            }
        } catch (Exception e) {
            System.out.println("Placeholder Image not found");
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInformation(event.getNewSelection().person);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonPanel)) {
            return false;
        }

        return false;
    }
}
```
###### /java/seedu/room/ui/MainWindow.java
``` java
        PersonPanel personPanel = new PersonPanel(logic);
        personPanelPlaceholder.getChildren().add(personPanel.getRoot());
```
###### /java/seedu/room/logic/Logic.java
``` java
    /**
     * Updates Picture of person list within model
     */
    void updatePersonListPicture(Person p);

    /** Updates and gets list of Auto-complete Strings */
    void updateAutoCompleteList(String userInput);
    String[] getAutoCompleteList();
```
###### /java/seedu/room/logic/parser/ResidentBookParser.java
``` java
        case HighlightCommand.COMMAND_WORD:
        case HighlightCommand.COMMAND_ALIAS:
            return new HighlightCommandParser().parse(arguments);
```
###### /java/seedu/room/logic/parser/DeleteImageCommandParser.java
``` java
/**
 * Parses the given {@code String} of arguments in the context of the DeleteImageCommand
 * and returns an DeleteImageCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class DeleteImageCommandParser implements Parser<DeleteImageCommand> {
    @Override
    public DeleteImageCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteImageCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteImageCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/room/logic/parser/AddImageCommandParser.java
``` java
/**
 * Parses the given {@code String} of arguments in the context of the AddImageCommand
 * and returns an AddImageCommand object for execution.
 * @throws ParseException if the user input does not conform the expected format
 */
public class AddImageCommandParser implements Parser<AddImageCommand> {
    @Override
    public AddImageCommand parse(String args) throws ParseException {
        requireNonNull(args);

        Index index;
        String url;
        try {
            String[] individualArgs = args.split(" ", 3);
            index = ParserUtil.parseIndex(individualArgs[1]);
            url = individualArgs[2];
        } catch (Exception e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddImageCommand.MESSAGE_USAGE));
        }

        return new AddImageCommand(index, url);
    }
}
```
###### /java/seedu/room/logic/parser/HighlightCommandParser.java
``` java
package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.room.logic.commands.HighlightCommand;
import seedu.room.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class HighlightCommandParser implements Parser<HighlightCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public HighlightCommand parse(String args) throws ParseException {
        String highlightTag = args.trim();
        if (validTag(highlightTag)) {
            return new HighlightCommand(highlightTag);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HighlightCommand.MESSAGE_USAGE));
        }
    }

    private boolean validTag(String highlightTag) {
        return !highlightTag.isEmpty();
    }
}

```
###### /java/seedu/room/logic/commands/HighlightCommand.java
``` java
package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.logic.commands.exceptions.TagNotFoundException;

/**
 * Adds a person to the address book.
 */
public class HighlightCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "highlight";
    public static final String COMMAND_ALIAS = "hl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Highlights names with the specified tag. "
            + "Parameters: " + "tag."
            + "Example: " + COMMAND_WORD + " "
            + "Unregistered";

    public static final String MESSAGE_SUCCESS = "Highlighted persons with tag: ";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag not found: ";

    private final String highlightTag;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public HighlightCommand(String highlightTag) {
        this.highlightTag = highlightTag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.updateHighlightStatus(highlightTag);
            return new CommandResult(MESSAGE_SUCCESS + highlightTag);
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND + highlightTag);
        }
    }
}
```
###### /java/seedu/room/logic/commands/AddImageCommand.java
``` java
/**
 * Allows the addition of an image to a resident currently in the resident book
 */
public class AddImageCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addImage";
    public static final String COMMAND_ALIAS = "ai";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an image to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing Image will be replaced by the new image.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[ Image Url ]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "/Downloads/placeholder_image";

    public static final String MESSAGE_ADD_IMAGE_SUCCESS = "Successfully changed image for Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the resident book.";

    private final Index index;
    private final String newImageUrl;

    /**
     *
     * @param index of the person in the list whose image is to be updated
     * @param newImageUrl url to the new replacing image
     */
    public AddImageCommand(Index index, String newImageUrl) {
        requireNonNull(index);

        this.index = index;
        this.newImageUrl = newImageUrl;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (!(new File(newImageUrl).exists())) {
            throw new CommandException(Messages.MESSAGE_INVALID_IMAGE_URL);
        }

        ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
        Person editedPerson = editPersonImage(person);
        createPersonImage(editedPerson);

        try {
            model.updatePerson(person, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonListPicture(PREDICATE_SHOW_ALL_PERSONS, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_IMAGE_SUCCESS, editedPerson.getName()));
    }

    /**
     * @param person Person to edit
     * @return Person with updated Picture url
     */
    public Person editPersonImage(ReadOnlyPerson person) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Room room = person.getRoom();
        Timestamp timestamp = person.getTimestamp();
        Set<Tag> tags = person.getTags();

        Person editedPerson =  new Person(name, phone, email, room, timestamp, tags);
        if (checkJarResourcePath(person)) {
            editedPerson.getPicture().setJarResourcePath();
        }

        editedPerson.getPicture().setPictureUrl(name.toString() + phone.toString() + ".jpg");
        return editedPerson;
    }

    /**
     * @param person whose image is to be checked
     * @return true if in production mode (jar file)
     */
    public boolean checkJarResourcePath(ReadOnlyPerson person) {
        File picture = new File(person.getPicture().getPictureUrl());
        return (picture.exists()) ? false : true;
    }

    /**
     * @param person whose attributes would be used to generate image file
     */
    public void createPersonImage(ReadOnlyPerson person) {
        File picFile = new File(newImageUrl);
        try {
            if (person.getPicture().checkJarResourcePath()) {
                ImageIO.write(ImageIO.read(picFile), "jpg", new File(person.getPicture().getJarPictureUrl()));
            } else {
                ImageIO.write(ImageIO.read(picFile), "jpg", new File(person.getPicture().getPictureUrl()));
            }
        } catch (Exception e) {
            System.out.println("Cannot create Person Image");
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            System.out.println("this");
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddImageCommand)) {
            System.out.println("that");
            return false;
        }

        // state check
        AddImageCommand ai = (AddImageCommand) other;
        return index.equals(ai.index) && newImageUrl.equals(ai.newImageUrl);
    }

}
```
###### /java/seedu/room/logic/commands/exceptions/TagNotFoundException.java
``` java
package seedu.room.logic.commands.exceptions;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException(String message) {
        super(message);
    }
}

```
###### /java/seedu/room/logic/commands/DeleteImageCommand.java
``` java
/**
 * Allows deletion of an image for a specified person
 */
public class DeleteImageCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deleteImage";
    public static final String COMMAND_ALIAS = "di";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an image to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing Image will be reset to placeholder image.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 3 ";

    public static final String MESSAGE_RESET_IMAGE_SUCCESS = "Successfully reset image for Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the resident book.";

    private final Index index;

    /**
     * @param index of the person in the list whose image is to be deleted
     */
    public DeleteImageCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
        Person editedPerson = resetPersonImage(person);

        try {
            model.updatePerson(person, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonListPicture(PREDICATE_SHOW_ALL_PERSONS, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RESET_IMAGE_SUCCESS, editedPerson.getName()));
    }

    /**
     * @param person whose image is to be reset
     * @return Person object with picture url reset
     */
    public Person resetPersonImage(ReadOnlyPerson person) {
        Name name = person.getName();
        Phone phone = person.getPhone();
        Email email = person.getEmail();
        Room room = person.getRoom();
        Timestamp timestamp = person.getTimestamp();
        Set<Tag> tags = person.getTags();

        Person editedPerson =  new Person(name, phone, email, room, timestamp, tags);
        return editedPerson;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            System.out.println("this");
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteImageCommand)) {
            System.out.println("that");
            return false;
        }

        // state check
        DeleteImageCommand ai = (DeleteImageCommand) other;
        return index.equals(ai.index);
    }
}
```
###### /java/seedu/room/logic/AutoComplete.java
``` java
package seedu.room.logic;

import java.util.ArrayList;

import seedu.room.model.Model;
import seedu.room.model.person.ReadOnlyPerson;


/**
 * AutoComplete class integrated into LogicManager to keep track of current set
 * of autocomplete suggestions
 */
public class AutoComplete {

    private final String[] baseCommands = { "add", "edit", "select", "delete", "clear",
        "backup", "find", "list", "history", "exit", "help", "undo", "redo"
    };
    private ArrayList<String> personsStringArray;
    private String[] autoCompleteList;
    private Model model;

    public AutoComplete(Model model) {
        this.model = model;
        autoCompleteList = baseCommands;
        personsStringArray = new ArrayList<String>();
        this.updatePersonsArray();
    }

    /**
     * Updates AutoComplete suggestions according to user typed input
     * @param userInput
     */
    public void updateAutoCompleteList(String userInput) {
        switch (userInput) {
        case "":
            this.resetAutocompleteList();
            break;
        case "find":
            this.autoCompleteList = getConcatPersonsArray("find");
            break;
        case "edit":
            this.autoCompleteList = getConcatPersonsArray("edit");
            break;
        case "delete":
            this.autoCompleteList = getConcatPersonsArray("delete");
            break;
        default:
            return;
        }
    }

    // Concatenate Persons to suggestions when command typed
    private String[] getConcatPersonsArray(String command) {
        String[] newAutoCompleteList = new String[personsStringArray.size()];
        for (int i = 0; i < personsStringArray.size(); i++) {
            if (command.equals("find")) {
                newAutoCompleteList[i] = command + " " + personsStringArray.get(i);
            } else {
                newAutoCompleteList[i] = command + " " + (i + 1);
            }
        }
        return newAutoCompleteList;
    }

    /**
     * Reset autocomplete suggestions to base commands
     */
    public void resetAutocompleteList() {
        this.autoCompleteList = baseCommands;
    }

    /**
     * Update array of persons suggestions when list modified
     */
    public void updatePersonsArray() {
        personsStringArray.clear();
        for (ReadOnlyPerson p: model.getFilteredPersonList()) {
            personsStringArray.add(p.getName().toString());
        }
    }

    /**
     * Getter for auto-complete list suggestions
     */
    // Update array of persons suggestions when list modified
    public String[] getAutoCompleteList() {
        return autoCompleteList;
    }
}
```
###### /java/seedu/room/logic/LogicManager.java
``` java
    @Override
    public void updateAutoCompleteList(String userInput) {
        autoCompleteList.updateAutoCompleteList(userInput);
    }

    @Override
    public String[] getAutoCompleteList() {
        return autoCompleteList.getAutoCompleteList();
    }

    @Override
    public void updatePersonListPicture(Person person) {
        model.updateFilteredPersonListPicture(PREDICATE_SHOW_ALL_PERSONS, person);
    }
```
###### /java/seedu/room/model/person/Picture.java
``` java
/**
 * Represents the url of the picture of the person in the resident book.
 */
public class Picture {

    public static final int PIC_WIDTH = 100;
    public static final int PIC_HEIGHT = 100;

    public static final String IMAGE_URL_VALIDATION_REGEX = "[^ ]+";

    public static final String BASE_URL = System.getProperty("user.dir")
            + "/data/contact_images/";

    public static final String PLACEHOLDER_IMAGE = System.getProperty("user.dir")
            + "/src/main/resources/images/placeholder_person.png";

    public static final String BASE_JAR_URL = System.getProperty("user.dir") + "/";

    public static final String PLACEHOLDER_JAR_URL = "/images/placeholder_person.png";

    private String pictureUrl;
    private String jarPictureUrl;
    private boolean jarResourcePath;

    public Picture() {
        this.pictureUrl = PLACEHOLDER_IMAGE;
        this.jarPictureUrl = PLACEHOLDER_JAR_URL;
        this.jarResourcePath = false;
    }

    /**
     * @return pictureUrl
     */
    public String getPictureUrl() {
        return pictureUrl;
    }

    /**
     * Handles resource path for jar
     */
    public String getJarPictureUrl() {
        return jarPictureUrl;
    }

    /**
     * Sets jar resource path to true
     */
    public void setJarResourcePath() {
        this.jarResourcePath = true;
    }

    /**
     * Checks if image is to be retrieved in jar
     */
    public boolean checkJarResourcePath() {
        return this.jarResourcePath;
    }

    /**
     * Sets name of image which will be appended to contact_images directory
     */
    public void setPictureUrl(String pictureUrl) {
        if (pictureUrl.contains("/")) {
            String[] splitStrings = pictureUrl.split("/");
            String pictureName = splitStrings[splitStrings.length - 1];
            this.pictureUrl = BASE_URL + pictureName;
            this.jarPictureUrl = BASE_JAR_URL + pictureName;
        } else {
            this.pictureUrl = BASE_URL + pictureUrl;
            this.jarPictureUrl = BASE_JAR_URL + pictureUrl;
        }
    }

    /**
     * Resets picture of person to original placeholder
     */
    public void resetPictureUrl() {
        this.pictureUrl = PLACEHOLDER_IMAGE;
        this.jarPictureUrl = PLACEHOLDER_JAR_URL;
    }

    /**
     * Checks validity of picture url
     */
    public static boolean isValidImageUrl(String imageUrl) {
        return imageUrl.matches(IMAGE_URL_VALIDATION_REGEX) && !imageUrl.contains("//");
    }

}
```
###### /java/seedu/room/model/person/ReadOnlyPerson.java
``` java
    ObjectProperty<Picture> pictureProperty();
    Picture getPicture();
```
###### /java/seedu/room/model/person/UniquePersonList.java
``` java
    /**
     * Updates the highlight status of the persons with the specified tag
     */
    public void updateHighlight(String highlightTag) throws TagNotFoundException {
        for (Person p : internalList) {
            p.setHighlightStatus(false);
            Set<Tag> personTags = p.getTags();
            for (Tag t : personTags) {
                if (t.toString().equals("[" + highlightTag + "]")) {
                    p.setHighlightStatus(true);
                }
            }
        }
    }
```
###### /java/seedu/room/model/person/Person.java
``` java
    @Override
    public ObjectProperty<Picture> pictureProperty() {
        return picture;
    }
```
###### /java/seedu/room/model/person/Person.java
``` java
    /**
     * Replaces this person's tags with the tags in the argument tag set.
     */
    public void setTags(Set<Tag> replacement) {
        tags.set(new UniqueTagList(replacement));
    }

    public void setHighlightStatus(boolean val) {
        this.highlight = val;
    }

    public boolean getHighlightStatus() {
        return this.highlight;
    }
```
###### /java/seedu/room/model/ResidentBook.java
``` java
    /** Updates highlight status of person with specified tag
     */
    public void updateHighlight(String highlightTag) {
        try {
            persons.updateHighlight(highlightTag);
            if (!this.tags.contains(new Tag(highlightTag))) {
                throw new TagNotFoundException("Tag not found");
            }
        } catch (IllegalValueException e) {
            throw new TagNotFoundException("Tag not found");
        }
    }
```
###### /java/seedu/room/model/ModelManager.java
``` java
    @Override
    public void updateFilteredPersonListPicture(Predicate<ReadOnlyPerson> predicate, Person person) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        for (ReadOnlyPerson p : filteredPersons) {
            if (p.getName().toString().equals(person.getName().toString())
                    && p.getPhone().toString().equals(person.getPhone().toString())) {
                p.getPicture().setPictureUrl(person.getPicture().getPictureUrl());
            }
        }
        indicateResidentBookChanged();
    }
```
###### /java/seedu/room/model/ModelManager.java
``` java
    /**
     * Updates the highlight status of a person if tag matches input tag
     */
    public void updateHighlightStatus(String highlightTag) throws TagNotFoundException {
        residentBook.updateHighlight(highlightTag);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }
```
###### /java/seedu/room/model/Model.java
``` java
    /**
     * Updates the highlight status of persons with the specified tag
     *
     * @throws TagNotFoundException if no specified tag exists
     */
    void updateHighlightStatus(String highlightTag) throws TagNotFoundException;
```
