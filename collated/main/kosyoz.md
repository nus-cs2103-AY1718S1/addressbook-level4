# kosyoz
###### \java\seedu\address\commons\events\ui\ChangeThemeEvent.java
``` java
/**
 * An event requesting to change theme.
 */
public class ChangeThemeEvent extends BaseEvent {

    public final String theme;
    public ChangeThemeEvent(String theme)
    {
        this.theme = theme;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();

    }
    public String getTheme()
    {
        return this.theme;
    }

}
```
###### \java\seedu\address\logic\commands\ChangeThemeCommand.java
``` java
/**
 * Change the theme of address book.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the theme of the App. "
            + "Parameters: ";
    public static final String MESSAGE_SUCCESS = "Theme Changed to ";

    private final String theme;

    public ChangeThemeCommand( String theme)
    {
        this.theme = theme;
    }
    @Override
    public CommandResult execute()
    {
        EventsCenter.getInstance().post(new ChangeThemeEvent(theme));
        return new CommandResult(String.format(MESSAGE_SUCCESS + theme));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangeThemeCommand // instanceof handles nulls
                && this.theme.equals(((ChangeThemeCommand) other).theme));
    }

}
```
###### \java\seedu\address\logic\commands\FindTagCommand.java
``` java
/**
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified tags (not case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_TAGS]...\n"
            + "Example: " + COMMAND_WORD + " friends family";

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
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_USAGE = COMMAND_WORD + " <INDEX> r/<REMARK>";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param remark of the person
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }
    /**
     * @param personToEdit
     * @return
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }

    @Override
     public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
```
###### \java\seedu\address\logic\parser\ChangeThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ChangeThemeCommandParser implements Parser<ChangeThemeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ChangeThemeCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ChangeThemeCommand parse(String userInput) throws ParseException {
        String trimmedArgs = userInput.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeThemeCommand.MESSAGE_USAGE));
        }
        return new ChangeThemeCommand(trimmedArgs);
    }
}
```
###### \java\seedu\address\logic\parser\FindTagCommandParser.java
``` java
/**
 */
public class FindTagCommandParser implements Parser<FindTagCommand> {
    /**
     * @param args
     * @return
     * @throws ParseException
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
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_REMARK);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultimap.getValue(PREFIX_REMARK).orElse("");

        return new RemarkCommand(index, new Remark(remark));
    }
}
```
###### \java\seedu\address\model\person\Remark.java
``` java
/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
            "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    public boolean isEmpty() {
        return value.equals("");
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && this.value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Change the theme of teh application.
     */
    @Subscribe
    public void handleChangeThemeEvent(ChangeThemeEvent event) {
        if (event.getTheme().equals("DarkTheme"))
        {
            vbox.getStylesheets().remove("/view/RedTheme.css");
            vbox.getStylesheets().add("/view/" + event.getTheme() + ".css");
        }

        else
        {
            vbox.getStylesheets().remove("/view/DarkTheme.css");
            vbox.getStylesheets().add("/view/" + event.getTheme() + ".css");
        }
    }
}
```
###### \java\seedu\address\ui\MapPanel.java
``` java
/**
 * The Map Panel of the App.
 */
public class MapPanel extends UiPart<Region> {
    public static final String DEFAULT_PAGE = "default.html";
    public static final String GOOGLE_SEARCH_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";

    private static final String FXML = "MapPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;
    public MapPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_SEARCH_URL_PREFIX + person.getAddress().toString().replaceAll(" ", "+"));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        URL defaultPage = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        loadPage(defaultPage.toExternalForm());
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

}
```
