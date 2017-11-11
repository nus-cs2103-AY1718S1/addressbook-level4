# nicholaschuayunzhi
###### \java\seedu\address\commons\events\ui\CommandInputChangedEvent.java
``` java
public class CommandInputChangedEvent extends BaseEvent {

    public final String currentInput;

    public CommandInputChangedEvent(String newCurrentInput) {
        currentInput = newCurrentInput;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\AddCommand.java
``` java
            toAdd.saveAvatar();
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose details contain ALL of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: PREFIX/KEYWORD [MORE_PREFIX/KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/alice a/Blk 100 Street t/friend";

    private final Predicate predicate;

    public FindCommand(Predicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && this.predicate.equals(((FindCommand) other).predicate)); // state check
    }

}
```
###### \java\seedu\address\logic\parser\AddCommandParser.java
``` java
            Avatar avatar = ParserUtil.parseAvatar(argMultimap.getValue(PREFIX_AVATAR)).get();
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java

public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        //replace new line character
        String trimmedArgs = args.trim().replace("\n", "").replace("\r", "");
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        return new FindCommand(inputIntoPredicate(trimmedArgs));
    }

    /**
     * Parses {@code userInput}
     * returns {@code PersonContainsFieldsPredicate} that matches the requirements of the input
     */
    private PersonContainsFieldsPredicate inputIntoPredicate(String userInput) {
        String trimmedArgs = userInput.trim();
        //we input white space infront so that ArgumentMultimap will be able to identify
        //first argument without prefix as a name
        //ie find alex
        String formattedText = " " + trimmedArgs;
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(formattedText,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_REMARK);

        List<Predicate> predicateList = new ArrayList<>();

        for (Prefix prefix : LIST_OF_PREFIXES) {
            for (String value : argumentMultimap.getAllValues(prefix)) {
                if (!value.equals("")) {
                    predicateList.add(valueAndPrefixIntoPredicate(value.trim(), prefix));

                }
            }
        }
        return new PersonContainsFieldsPredicate(predicateList);
    }

    /**
     * Takes in {@code value} and {@code prefix}
     * returns {@code Predicate} that checks for the value in person's field based on prefix
     */
    private Predicate valueAndPrefixIntoPredicate(String value, Prefix prefix) {
        switch (prefix.toString()) {
        case CliSyntax.PREFIX_NAME_STRING:
            return new NameContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_PHONE_STRING:
            return new PhoneContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_ADDRESS_STRING:
            return new AddressContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_EMAIL_STRING:
            return new EmailContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_TAG_STRING:
            return new TagsContainKeywordPredicate(value);
        case CliSyntax.PREFIX_EMPTY_STRING:
            return new NameContainsKeywordPredicate(value);
        case CliSyntax.PREFIX_REMARK_STRING:
            return new RemarkContainsKeywordPredicate(value);
        default:
            return new NameContainsKeywordPredicate(value);
        }
    }
}

```
###### \java\seedu\address\logic\parser\HintParser.java
``` java
    /**
     * Parses {@code String input} and returns an appropriate hint
     */
    public static String generateHint(String input) {
        //the ordering matters as prefix hints are generated inorder
        assert LIST_OF_PREFIXES.equals(Arrays.asList(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_REMARK, PREFIX_AVATAR, PREFIX_EMPTY));

        String[] command;

        try {
            command = ParserUtil.parseCommandAndArguments(input);
        } catch (ParseException e) {
            return " type help for guide";
        }

        userInput = input;
        commandWord = command[0];
        arguments = command[1];
        String hintContent = generateHintContent();

        return hintContent;
    }

    /**
     * returns an appropriate hint based on commandWord and arguments
     * userInput and arguments are referenced to decide whether whitespace should be added to
     * the front of the hint
     */
    private static String generateHintContent() {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return generateAddHint();
        case AliasCommand.COMMAND_WORD:
            return " creates an alias";
        case EditCommand.COMMAND_WORD:
            return generateEditHint();
        case FindCommand.COMMAND_WORD:
            return generateFindHint();
        case SelectCommand.COMMAND_WORD:
        case DeleteCommand.COMMAND_WORD:
            return generateDeleteAndSelectHint();
        case ClearCommand.COMMAND_WORD:
            return " clears address book";
        case ListCommand.COMMAND_WORD:
            return " lists all people";
        case HistoryCommand.COMMAND_WORD:
            return " show command history";
        case ExitCommand.COMMAND_WORD:
            return " exits the app";
        case HelpCommand.COMMAND_WORD:
            return " shows user guide";
        case UndoCommand.COMMAND_WORD:
            return " undo command";
        case RedoCommand.COMMAND_WORD:
            return " redo command";
        default:
            return " type help for guide";
        }
    }

    /**
     * returns a parameter based on {@code prefix}
     */
    private static String prefixIntoParameter(Prefix prefix) {
        switch (prefix.toString()) {
        case PREFIX_NAME_STRING:
            return "NAME";
        case PREFIX_PHONE_STRING:
            return "PHONE";
        case PREFIX_ADDRESS_STRING:
            return "ADDRESS";
        case PREFIX_EMAIL_STRING:
            return "EMAIL";
        case PREFIX_TAG_STRING:
            return "TAG";
        case PREFIX_REMARK_STRING:
            return "REMARK";
        case PREFIX_EMPTY_STRING:
            return "KEYWORD";
        default:
            return "KEYWORD";
        }
    }

    /**
     * returns a hint specific to the add command
     */
    private static String generateAddHint() {
        AddCommandHint addCommandHint = new AddCommandHint(userInput, arguments);
        addCommandHint.parse();
        return addCommandHint.getArgumentHint() + addCommandHint.getDescription();

    }

    /**
     * returns a hint specific to the edit command
     */
    private static String generateEditHint() {
        EditCommandHint editCommandHint = new EditCommandHint(userInput, arguments);
        editCommandHint.parse();
        return editCommandHint.getArgumentHint() + editCommandHint.getDescription();
    }

    /**
     * returns a hint specific to the find command
     */
    private static String generateFindHint() {
        FindCommandHint findCommandHint = new FindCommandHint(userInput, arguments);
        findCommandHint.parse();
        return findCommandHint.getArgumentHint() + findCommandHint.getDescription();
    }

    /**
     * returns a hint specific to the select and delete command
     */
    private static String generateDeleteAndSelectHint() {

        DeleteCommandHint deleteCommandHint = new DeleteCommandHint(userInput, arguments);
        deleteCommandHint.parse();
        return deleteCommandHint.getArgumentHint() + deleteCommandHint.getDescription();

    }

}
```
###### \java\seedu\address\model\person\AddressContainsKeywordPredicate.java
``` java
public class AddressContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public AddressContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getAddress().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((AddressContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Avatar.java
``` java
public class Avatar {


    public static final String MESSAGE_AVATAR_CONSTRAINTS = "Avatar file path must be .jpg or .png and must exist";

    public final String value;
    private String originalFilePath;

    /**
     * Creates an avatar with string
     * @param fileName can be invalid. Displayed avatar will then be default image
     */
    public Avatar(String fileName) {
        this.value = fileName;
    }

    /**
     * Creates an avatar with
     * @param fileName name of image to be saved as avatar
     * @param originalFilePath file path of file to be saved as avatar
     */
    private Avatar(String fileName, String originalFilePath) {
        this.value = fileName;
        this.originalFilePath = originalFilePath;
    }

    /**
     * Creates an avatar with given file path
     * file path is check to see if it is valid and if is a jpg or png
     */
    public static Avatar readAndCreateAvatar(String filePath) throws IllegalValueException {

        if (filePath == null) {
            return new Avatar("", "");
        }

        if (!ImageStorage.isValidImagePath(filePath)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }

        if (!ImageStorage.isJpgOrPng(filePath)) {
            throw new IllegalValueException(MESSAGE_AVATAR_CONSTRAINTS);
        }

        //use a unique id for each image to prevent overwriting old images.
        return new Avatar(UUID.randomUUID().toString() + "." + ImageStorage.getFormat(filePath), filePath);
    }

    /**
     *  Saves image at {@code originalFilePath} with uid value via ImageStorage
     *  This is required at the moment as current implementation of input validation before enter
     *  uses AddressBookParser#parse
     */

    public void saveAvatar() {

        if (originalFilePath == null || originalFilePath.isEmpty()) {
            return;
        }

        ImageStorage.saveAvatar(originalFilePath, value);
    }

    public String getOriginalFilePath() {
        return originalFilePath;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Avatar // instanceof handles nulls
                && this.value.equals(((Avatar) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

```
###### \java\seedu\address\model\person\EmailContainsKeywordPredicate.java
``` java
public class EmailContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public EmailContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getEmail().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((EmailContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
```
###### \java\seedu\address\model\person\NameContainsKeywordPredicate.java
``` java
public class NameContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public NameContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getName().fullName.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((NameContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
```
###### \java\seedu\address\model\person\PersonContainsFieldsPredicate.java
``` java
public class PersonContainsFieldsPredicate implements Predicate<ReadOnlyPerson> {
    private final Set<Predicate> predicates;

    public PersonContainsFieldsPredicate(List<Predicate> predicates) {
        this.predicates = predicates.stream().collect(Collectors.toSet());
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (Predicate searchQuery : predicates) {
            if (!searchQuery.test(person)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsFieldsPredicate // instanceof handles nulls
                && checkEquality((PersonContainsFieldsPredicate) other)); // state check
    }

    /**
     * returns true if other predicate is functionally the same as this predicate
     * and have the same number of predicates
     */
    public boolean checkEquality(PersonContainsFieldsPredicate other) {
        return predicates.equals(other.predicates);
    }
}
```
###### \java\seedu\address\model\person\PhoneContainsKeywordPredicate.java
``` java
public class PhoneContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public PhoneContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return person.getPhone().value.toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((PhoneContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }

}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    ObjectProperty<Avatar> avatarProperty();
    Avatar getAvatar();
    /**
     * This is required as explained in Avatar#saveAvatar
     * Must be changed in the future as this breaks the read-only interface of ReadOnlyPerson
     */
    void saveAvatar();
```
###### \java\seedu\address\model\person\RemarkContainsKeywordPredicate.java
``` java
public class RemarkContainsKeywordPredicate implements Predicate<ReadOnlyPerson> {

    private final String keyword;
    public RemarkContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return containsWordIgnoreCase(person.getRemark().value, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemarkContainsKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((RemarkContainsKeywordPredicate) other).keyword)); // state check
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
```
###### \java\seedu\address\model\person\TagsContainKeywordPredicate.java
``` java
public class TagsContainKeywordPredicate implements Predicate<ReadOnlyPerson> {
    private final String keyword;

    public TagsContainKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return personTagsContainKeyword(person, keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainKeywordPredicate // instanceof handles nulls
                && this.keyword.equals(((TagsContainKeywordPredicate) other).keyword)); // state check
    }

    /**
     * Returns true if person's tags list contains keyword (case insensitive)
     */
    private boolean personTagsContainKeyword(ReadOnlyPerson person, String keyword) {
        return person.getTags().stream().anyMatch(tag -> tag.tagName.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public int hashCode() {
        return keyword.hashCode();
    }
}
```
###### \java\seedu\address\storage\ImageStorage.java
``` java
public class ImageStorage {

    public static final String PNG = "png";
    public static final String JPG = "jpg";
    public static final String AVATAR_STORAGE_PATH = "data/images/avatars/";
    public static final String DEFAULT_RESOURCE_PATH = "/images/avatars/default.png";

    /**
     * Looks for image in {@code AVATAR_STORAGE_PATH} based on imageName
     * @param imageName
     * @return image if it exists or default image if image does not exist
     */
    public static Image getAvatar(String imageName) {
        Image image;

        try {

            String fullPath = AVATAR_STORAGE_PATH + imageName;
            image = new Image(new FileInputStream(new File(fullPath)));

        } catch (FileNotFoundException e) {
            image = AppUtil.getImage(DEFAULT_RESOURCE_PATH);
        }

        return image;
    }

    /**
     * Saves image to {@code AVATAR_STORAGE_PATH}
     * @param imageFilePath file path of image to be saved
     * @param name name of image to be saved as
     * @return true if avatar is successfully saved and false if avatar is not saved
     */
    public static boolean saveAvatar(String imageFilePath, String name) {

        String format = getFormat(imageFilePath);

        try {
            File imageFile = new File(imageFilePath);
            File imageFileToWrite = new File(AVATAR_STORAGE_PATH + name);
            FileUtil.createIfMissing(imageFileToWrite);
            ImageIO.write(ImageIO.read(imageFile), format, imageFileToWrite);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * parses file path and returns format
     * @return image format of {@param imageFilePath}
     * assumes filePath either ends with .png or .jpg (see #isJpgOrPng)
     */
    public static String getFormat(String imageFilePath) {
        String format = PNG;

        if (isJpg(imageFilePath)) {
            format = JPG;
        }
        return format;
    }

    /**
     * @return true if filePath is a path to a .jpg or .png
     */
    public static boolean isJpgOrPng(String filePath) {
        return isJpg(filePath) || isPng(filePath);
    }

    /**
     * parses {@code filePath} and checks if its a path to .jpg or .png
     * and checks if the file exists and is not a directory
     * @return true if above criteria is met
     */
    public static boolean isValidImagePath(String filePath) {

        if (!isJpgOrPng(filePath)) {
            return false;
        }

        File f = new File(filePath);
        if (!f.exists() || f.isDirectory()) {
            return false;
        }

        return true;
    }

    private static boolean isJpg(String filePath) {
        return filePath.toLowerCase().endsWith(".jpg");
    }

    private static boolean isPng(String filePath) {
        return filePath.toLowerCase().endsWith(".png");
    }

}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        commandTextField.textProperty().addListener((ob, o, n) -> {
            // expand the textfield
            double width = TextUtil.computeTextWidth(commandTextField.getFont(),
                    commandTextField.getText(), 0.0D) + 2;
            double halfWindowWidth = (MainWindow.getInstance() == null)
                    ? 250 : MainWindow.getInstance().getRoot().getWidth() / 2;
            width = (width < 1) ? 1 : width;
            width = (width > halfWindowWidth) ? halfWindowWidth : width;
            commandTextField.setPrefWidth(width);
            commandTextField.setAlignment(Pos.CENTER_RIGHT);
        });

        commandBoxItems.setOnMouseClicked((event) -> {
            commandTextField.requestFocus();
            commandTextField.positionCaret(commandTextField.getText().length());
        });

        CommandBoxHints commandBoxHints = new CommandBoxHints(commandTextField);
        commandBoxItems.getChildren().add(commandBoxHints.getRoot());
        commandTextField.prefColumnCountProperty().bind(commandTextField.textProperty().length());

        // changes command box style to match validity of the input whenever there is a change
        // to the text of the command box.
        commandTextField.textProperty().addListener((observable, oldInput, newInput) ->
                setStyleByValidityOfInput(newInput));

        // posts a CommandInputChangedEvent whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((observable, oldInput, newInput) ->
                EventsCenter.getInstance().post(new CommandInputChangedEvent(newInput)));
        historySnapshot = logic.getHistorySnapshot();

```
###### \java\seedu\address\ui\CommandBoxHints.java
``` java
public class CommandBoxHints extends UiPart<TextField> {

    private static final String FXML = "CommandBoxHints.fxml";

    @FXML
    private TextField commandBoxHints;

    private TextField commandTextField;

    public CommandBoxHints(TextField commandTextField) {
        super(FXML);
        registerAsAnEventHandler(this);
        this.commandTextField = commandTextField;

        commandBoxHints.textProperty().addListener((ob, o, n) -> {
            // expand the textfield
            double width = TextUtil.computeTextWidth(commandBoxHints.getFont(),
                    commandBoxHints.getText(), 0.0D) + 1;
            width = (width < 1) ? 1 : width;
            commandBoxHints.setPrefWidth(width);
        });
    }


    @Subscribe
    private void handleCommandInputChangedEvent(CommandInputChangedEvent event) {
        String userInput = event.currentInput;
        if (userInput.isEmpty()) {
            commandBoxHints.setText("Enter Command Here");
            return;
        }
        String hint = generateHint(userInput);
        commandBoxHints.setText(hint);

    }

    @FXML
    private void handleOnClick() {
        commandTextField.requestFocus();
        commandTextField.positionCaret(commandTextField.getText().length());
    }


}
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
public class PersonPanel extends UiPart<Region> {

    private static final String FXML = "PersonPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private VBox panel;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label remark;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView avatar;

    public PersonPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Updates person details showcased on the panel
     */
    private void loadPersonDetails(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        email.setText(person.getEmail().toString());
        tags.getChildren().clear();
        person.getTags().forEach(tag -> tags.getChildren().add(new Tag(tag.tagName).getRoot()));
```
###### \java\seedu\address\ui\PersonPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {

        Image image = ImageStorage.getAvatar(event.getNewSelection().person.getAvatar().value);
        avatar.setImage(image);

        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }
}
```
###### \java\seedu\address\ui\Tag.java
``` java
    public Tag(String tagValue) {
        super(FXML);
        tag.setText(tagValue);
        value = tagValue;
        if (MainWindow.getInstance() != null) {
            logic = MainWindow.getInstance().getLogic();
        }
    }

    /**
     * onMouseClicked, a search for tag will be executed
     */
    @FXML
    private void handleClick(MouseEvent event) {
        try {
            CommandResult commandResult =
                    logic.execute(FindCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_TAG_STRING + value);
            // process result of the command
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            // handle command failure
            logger.info("Invalid tag find: " + value);
            raise(new NewResultAvailableEvent(e.getMessage()));
        } catch (NullPointerException ne) {
            logger.info("Address Book Logic not initialised");
            assert false;
        }
    }

    public String getText() {
        return value;
    }

    public Label getLabel() {
        return tag;
    }

}
```
###### \resources\view\CommandBoxHints.fxml
``` fxml
<?import javafx.scene.Cursor?>
<?import javafx.scene.control.TextField?>


<TextField fx:id="commandBoxHints" editable="false" onMouseClicked="#handleOnClick" pickOnBounds="false" text="Enter Command Here" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <cursor>
      <Cursor fx:constant="TEXT" />
   </cursor>
</TextField>
```
###### \resources\view\main.css
``` css

@font-face {
    src: url("./ProximaNovaAltRegular-webfont.ttf");
}

.root * {
    base: black;
    accent: #141E30;
    light: white;
    -fx-font-family: "Proxima Nova Alt Rg", "Helvetica";
    -fx-background-color: transparent;
    -fx-text-fill: light;
}

#id, #tags .label {
    -fx-text-fill: derive(light, -40%);
}

#tag:hover {
    -fx-underline: true;
    -fx-text-fill: light !important;
}

.scroll-bar .thumb {
    -fx-background-color: derive(base, -5%);
    -fx-fill-width: 10;
}

/**
 * Menu Bar
 */

#menuBar * {
    -fx-background-color: light;
    -fx-text-fill: base;
}

/**
 * Command Box
 */

#commandBoxWrapper {
    -fx-padding: 20;
}

#commandBoxItems {
    -fx-border-color: transparent transparent light transparent;
}

#commandTextField, #commandBoxHints{
    -fx-font-size: 25pt;
    -fx-font-weight: bold;
    -fx-effect: dropshadow(gaussian, base, 5, 0, 0, 0);
    -fx-padding: 0 0 0 0;
}

#commandBoxHints {
    -fx-opacity: 0.4;
}


#commandBoxIconPlaceholder {
    -fx-padding: 0 0 0 10;
}

#commandBoxIconPlaceholder .ikonli-font-icon {
    -fx-icon-color: light;
}

/**
 * Result Display
 */

#resultDisplay {
    -fx-font-size: 13pt;
    -fx-text-fill: derive(light, -40%);
}

#resultDisplayPlaceholder {
    -fx-padding: 0 10 0 10;
}

/**
 * Person List
 */

#personListVBox {
    -fx-padding: 0 0 0 20;
}

#personListVBox #name, #personListVBox #id{
    -fx-font-size: 15pt;
}

#personListVBox .scroll-bar:horizontal .thumb {
    -fx-background-color: transparent;
}

.list-cell:filled:hover {
    -fx-background-color: rgba(0, 0, 0, 0.5);
}

.list-cell:filled:selected {
    -fx-background-color: rgba(0, 0, 0, 0.7);
}

/**
 * Person Panel
 */
#personPanel {
    -fx-font-size: 15pt;
}

#personPanel #name {
    -fx-font-size: 25pt;
    -fx-effect: dropshadow(gaussian, base, 10, 0, 0, 2);
}

#details {
    lighterBase: derive(base, 10%);
    -fx-background-color: linear-gradient(from 50% 0% to 50% 100%, accent 0%, lighterBase 100%);
}
```
###### \resources\view\PersonPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.FlowPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.VBox?>

<VBox fx:id="personPanel" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <HBox>
         <children>
            <ImageView fx:id="avatar" fitHeight="150.0" fitWidth="150.0" pickOnBounds="true" preserveRatio="true">
               <HBox.margin>
                  <Insets bottom="10.0" left="10.0" right="10.0" top="10.0" />
               </HBox.margin></ImageView>
            <VBox alignment="CENTER_LEFT">
               <children>
                  <Label fx:id="name" textAlignment="LEFT" wrapText="true" />
                  <FlowPane fx:id="tags" alignment="TOP_LEFT" hgap="8.0" rowValignment="TOP">
                     <padding>
                        <Insets bottom="5.0" right="5.0" />
                     </padding>
                  </FlowPane>
                  <Label fx:id="phone" alignment="TOP_LEFT" contentDisplay="LEFT" textAlignment="LEFT" wrapText="true" />
                  <Label fx:id="address" alignment="TOP_LEFT" contentDisplay="LEFT" textAlignment="LEFT" wrapText="true" />
                  <Label fx:id="email" alignment="TOP_LEFT" contentDisplay="LEFT" textAlignment="LEFT" wrapText="true" />
               </children>
            </VBox>
         </children>
      </HBox>
      <Label fx:id="remark" alignment="TOP_LEFT" contentDisplay="LEFT" textAlignment="LEFT" wrapText="true" />
   </children>
</VBox>
```
###### \resources\view\Tag.fxml
``` fxml
<?import javafx.scene.control.Label?>


<Label fx:id="tag" onMouseClicked="#handleClick" text="Label" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1" />
```
