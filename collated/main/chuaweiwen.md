# chuaweiwen
###### \java\seedu\address\commons\events\ui\ChangeThemeRequestEvent.java
``` java
/**
 * Indicates a request to change the theme
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final Theme theme;

    public ChangeThemeRequestEvent(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
/**
 * Finds and lists all persons in address book whose name and/or tags contains all of the argument keywords.
 * Keyword matching is not case sensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters all persons whose names and tags contain "
            + "all of the specified keywords and displays them as a list with index numbers.\n"
            + "Parameters: [n/NAME] [t/TAG]...\n"
            + "Note: At least one of the parameters must be specified.\n"
            + "Example: " + COMMAND_WORD + " n/Alex t/friends";

    private final NameAndTagsContainsKeywordsPredicate predicate;

    public FilterCommand(NameAndTagsContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        model.showDefaultPanel();
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && this.predicate.equals(((FilterCommand) other).predicate)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\NicknameCommand.java
``` java
/**
 * Sets the nickname of an existing person in the address book.
 */
public class NicknameCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "nickname";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the nickname to the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[NICKNAME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "Adam";

    public static final String MESSAGE_SET_NICKNAME_SUCCESS = "Nickname successfully set to Person: %1$s";
    public static final String MESSAGE_REMOVE_NICKNAME_SUCCESS = "Nickname successfully removed from Person: %1$s";
    public static final String MESSAGE_UNCHANGED_NICKNAME = "Nickname remains unchanged for Person: %1$s";

    private final Index index;
    private final Nickname nickname;

    /**
     * @param index of the person in the filtered person list to edit
     * @param nickname details to edit the person with
     */
    public NicknameCommand(Index index, Nickname nickname) {
        requireNonNull(index);
        requireNonNull(nickname);

        this.index = index;
        this.nickname = nickname;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = (Person) lastShownList.get(index.getZeroBased());
        Nickname previousNickname;

        try {
            previousNickname = personToEdit.getNickname();
        } catch (NullPointerException npe) {
            throw new AssertionError("Nickname cannot be null");
        }

        personToEdit.setNickname(nickname);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        try {
            model.updatePerson(personToEdit, personToEdit);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("The target person cannot be duplicated");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        if (nickname.equals(previousNickname)) {
            return new CommandResult(String.format(MESSAGE_UNCHANGED_NICKNAME, personToEdit.getAsText()));
        } else if (nickname.value.equals("")) {
            return new CommandResult(String.format(MESSAGE_REMOVE_NICKNAME_SUCCESS, personToEdit.getAsText()));
        } else {
            return new CommandResult(String.format(MESSAGE_SET_NICKNAME_SUCCESS, personToEdit.getAsText()));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NicknameCommand)) {
            return false;
        }

        // state check
        NicknameCommand e = (NicknameCommand) other;
        return index.equals(e.index) && nickname.equals(e.nickname);
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
/**
 * Changes the theme of the address book.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the theme of the address book\n"
            + "Parameter: THEME\n"
            + "List of available themes: "
            + ThemeList.THEME_DAY + ", "
            + ThemeList.THEME_NIGHT + "\n"
            + "Example: " + COMMAND_WORD + " day";

    public static final String MESSAGE_SET_THEME_SUCCESS = "Successfully set theme: %1$s";

    private final Theme theme;

    public ThemeCommand(Theme theme) {
        this.theme = theme;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(theme));
        return new CommandResult(String.format(MESSAGE_SET_THEME_SUCCESS, theme.getTheme()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ThemeCommand)) {
            return false;
        }

        // state check
        ThemeCommand e = (ThemeCommand) other;
        return theme.equals(e.theme);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case NicknameCommand.COMMAND_WORD:
            return new NicknameCommandParser().parse(arguments);

        case ThemeCommand.COMMAND_WORD:
            return new ThemeCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case FilterCommand.COMMAND_WORD:
            return new FilterCommandParser().parse(arguments);
```
###### \java\seedu\address\logic\parser\FilterCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns an FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        List<String> nameKeywordsList = new ArrayList<>();
        List<String> tagsKeywordsList = new ArrayList<>();

        String regex = "\\s+";

        // Extracting name
        if (!argMultimap.getAllValues(PREFIX_NAME).isEmpty()) {
            List<String> unprocessedNames = argMultimap.getAllValues(PREFIX_NAME);
            nameKeywordsList = Arrays.asList(getKeywordsFromList(unprocessedNames, regex));
        }

        // Extracting tags
        if (!argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            List<String> unprocessedTags = argMultimap.getAllValues(PREFIX_TAG);
            tagsKeywordsList = Arrays.asList(getKeywordsFromList(unprocessedTags, regex));
        }

        return new FilterCommand(new NameAndTagsContainsKeywordsPredicate(nameKeywordsList, tagsKeywordsList));
    }

    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private String[] getKeywordsFromList(List<String> list, String regex) throws ParseException {
        String keywords = "";
        for (String string : list) {
            // string cannot be empty
            if (string.length() == 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            keywords = keywords + " " + string;
        }
        return keywords.trim().split(regex);
    }
}
```
###### \java\seedu\address\logic\parser\NicknameCommandParser.java
``` java
/**
 * Parses input arguments and creates a new NicknameCommand object
 */
public class NicknameCommandParser implements Parser<NicknameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NicknameCommand
     * and returns an NicknameCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NicknameCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String regex = "[\\s]+";
        String[] splitArgs = args.trim().split(regex, 2);

        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NicknameCommand.MESSAGE_USAGE));
        }

        String nickname;
        if (splitArgs.length > 1) {
            nickname = splitArgs[1];
        } else {
            nickname = "";
        }

        return new NicknameCommand(index, new Nickname(nickname));
    }
}
```
###### \java\seedu\address\logic\parser\Theme.java
``` java
/**
 * Represents the theme of the address book.
 */
public class Theme {
    private final String theme;
    private final String filePath;

    public Theme(String theme, String filePath) {
        this.theme = theme;
        this.filePath = filePath;
    }

    public String getTheme() {
        return theme;
    }

    public String getFilePath() {
        return filePath;
    }

    public String toString() {
        return getTheme();
    }

    @Override
    public int hashCode() {
        return theme == null ? 0 : theme.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Theme)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Theme otherTheme = (Theme) obj;
        return otherTheme.getTheme().equals(getTheme()) && otherTheme.getFilePath().equals(getFilePath());
    }
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ThemeCommand object
 */
public class ThemeCommandParser implements Parser<ThemeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ThemeCommand
     * and returns an ThemeCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ThemeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
        }

        switch(trimmedArgs) {
        case ThemeList.THEME_DAY:
            return new ThemeCommand(new Theme(trimmedArgs, ThemeList.THEME_DAY_PATH));

        case ThemeList.THEME_NIGHT:
            return new ThemeCommand(new Theme(trimmedArgs, ThemeList.THEME_NIGHT_PATH));

        default:
            throw new ParseException(
                String.format(MESSAGE_UNKNOWN_THEME, ThemeCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ThemeList.java
``` java
/**
 * Contains a list of names and file paths of available themes
 */
public class ThemeList {
    public static final String DEFAULT_PATH = "view/";

    public static final String THEME_DAY = "day";
    public static final String THEME_NIGHT = "night";

    public static final String THEME_DAY_PATH = DEFAULT_PATH + "DayTheme.css";
    public static final String THEME_NIGHT_PATH = DEFAULT_PATH + "NightTheme.css";
}
```
###### \java\seedu\address\MainApp.java
``` java
    @Subscribe
    public void handleChangeThemeRequestEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        userPrefs.setThemeFilePath(event.theme.getFilePath());
    }
```
###### \java\seedu\address\model\person\NameAndTagsContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Name, @code Tag} matches all of the keywords given.
 */
public class NameAndTagsContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> nameKeywords;
    private final List<String> tagKeywords;

    public NameAndTagsContainsKeywordsPredicate(List<String> nameKeywords, List<String> tagKeywords) {
        this.nameKeywords = nameKeywords;
        this.tagKeywords = tagKeywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        boolean hasTag = false;

        int numTagKeywords = tagKeywords.size();
        int tagsMatchedCount = 0;
        if (!tagKeywords.isEmpty()) {
            tagsMatchedCount = countTagMatches(person);
        }

        if (tagsMatchedCount == numTagKeywords) {
            hasTag = true;
        }

        boolean hasName = false;
        if (!nameKeywords.isEmpty()) {
            hasName = nameKeywords.stream().allMatch(nameKeywords -> StringUtil
                    .containsWordIgnoreCase(person.getName().fullName, nameKeywords));
        }

        if (nameKeywords.isEmpty() && tagKeywords.isEmpty()) {
            throw new AssertionError("Either name or tag must be non-empty");
        } else if (nameKeywords.isEmpty()) {
            return hasTag;
        } else if (tagKeywords.isEmpty()) {
            return hasName;
        }

        return hasName && hasTag;
    }

    /**
     * Counts the number of matching tags of a person and returns the count
     */
    public int countTagMatches(ReadOnlyPerson person) {
        int tagsMatchedCount = 0;

        for (String keywords : tagKeywords) {
            if (containsTag(keywords, person)) {
                tagsMatchedCount++;
            }
        }
        return tagsMatchedCount;
    }

    /**
     * Returns true if the person's tag can be found in the keywords. Otherwise returns false.
     */
    public boolean containsTag(String keywords, ReadOnlyPerson person) {
        Set<Tag> tagsOfPerson = person.getTags();
        for (Tag tag : tagsOfPerson) {
            if (tag.tagName.equalsIgnoreCase(keywords)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameAndTagsContainsKeywordsPredicate // instanceof handles nulls
                && this.nameKeywords.equals(((NameAndTagsContainsKeywordsPredicate) other)
                .nameKeywords)
                && this.tagKeywords.equals(((NameAndTagsContainsKeywordsPredicate) other)
                .tagKeywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\Nickname.java
``` java
/**
 * Represents a Person's nickname in the address book.
 * Guarantees: immutable; is valid
 */
public class Nickname {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Person's nickname can take any values.";

    public final String value;

    public Nickname(String nickname) {
        requireNonNull(nickname);
        this.value = nickname;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nickname // instanceof handles nulls
                && this.value.equals(((Nickname) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\UserPrefs.java
``` java
    public String getThemeFilePath() {
        return themeFilePath;
    }

    public void setThemeFilePath(String themeFilePath) {
        this.themeFilePath = themeFilePath;
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleChangeThemeEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeTheme(event.theme);
    }

    /**
     * Changes the theme
     */
    private void changeTheme(Theme theme) {
        Scene scene = primaryStage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(theme.getFilePath());
        scene.getStylesheets().add(STYLE);
    }
```
