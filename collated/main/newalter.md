# newalter
###### \java\seedu\address\commons\events\model\NewPersonInfoEvent.java
``` java
/**
 * An event indicating new information of a person to be learnt by SuggestionHeuristic
 */
public class NewPersonInfoEvent extends BaseEvent {
    private final ReadOnlyPerson person;

    public NewPersonInfoEvent(ReadOnlyPerson person) {
        this.person = person;
    }

    public ReadOnlyPerson getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return "New Person Info Available";
    }
}
```
###### \java\seedu\address\commons\events\ui\ResizeMainWindowEvent.java
``` java
/**
 * An event requesting to change the size of the main window.
 */
public class ResizeMainWindowEvent extends BaseEvent {

    private int width;
    private int height;

    public ResizeMainWindowEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return null;
    }
}
```
###### \java\seedu\address\logic\commands\CommandWordList.java
``` java
/**
 * This class stores a list of all command words.
 */
public class CommandWordList {

    public static final List<String> COMMAND_WORD_LIST = Arrays.asList(AddCommand.COMMAND_WORD,
            AddTagCommand.COMMAND_WORD, AddMeetingCommand.COMMAND_WORD, BackupCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD, DeleteCommand.COMMAND_WORD, DeleteTagCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD, FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD, HistoryCommand.COMMAND_WORD, ListCommand.COMMAND_WORD,
            LoginCommand.COMMAND_WORD, NoteCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD,
            ResizeCommand.COMMAND_WORD, RestoreBackupCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
            SortCommand.COMMAND_WORD, SyncCommand.COMMAND_WORD, UndoCommand.COMMAND_WORD,
            DeleteMeetingCommand.COMMAND_WORD, LogoutCommand.COMMAND_WORD);
}
```
###### \java\seedu\address\logic\commands\FindCommand.java
``` java
    public static final String COMMAND_WORD = "find";
    public static final String COMMAND_ALIAS = "f";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose specified fields "
            + "contain any of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "KEYWORD...] "
            + "[" + PREFIX_PHONE + "KEYWORD...] "
            + "[" + PREFIX_EMAIL + "KEYWORD...] "
            + "[" + PREFIX_ADDRESS + "KEYWORD...] "
            + "[" + PREFIX_TAG + "KEYWORD...]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "alice bob charlie "
            + PREFIX_PHONE + "98765432 93250124 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "Clementi Ave "
            + PREFIX_TAG + "friends owesMoney";

    public static final Predicate<ReadOnlyPerson> FALSE = (unused -> false);

    private final ArrayList<Predicate<ReadOnlyPerson>> predicates;

    public FindCommand(ArrayList<Predicate<ReadOnlyPerson>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public CommandResult execute() {
        Predicate<ReadOnlyPerson> predicate = combinePredicates();

        Predicate<? super ReadOnlyPerson>  currentPredicate = model.getPersonListPredicate();
        if (currentPredicate == null) {
            model.updateFilteredPersonList(predicate);
        } else {
            model.updateFilteredPersonList(predicate.and(currentPredicate));
        }
        model.updateFilteredMeetingList(new MeetingContainPersonPredicate(model.getFilteredPersonList()));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    /**
     * combines the list of predicates into a single predicate for execution
     * by taking OR operations
     */
    private Predicate<ReadOnlyPerson> combinePredicates() {
        Predicate<ReadOnlyPerson> combinedPredicate = FALSE;
        for (Predicate predicate : predicates) {
            combinedPredicate = combinedPredicate.or(predicate);
        }
        return combinedPredicate;
    }
```
###### \java\seedu\address\logic\commands\ResizeCommand.java
``` java
/**
 * Resize the main window.
 */
public class ResizeCommand extends Command {
    public static final Dimension DIMENSION = ScreenDimension.getDimension();
    public static final int MAX_WIDTH = DIMENSION.width;
    public static final int MAX_HEIGHT = DIMENSION.height;
    public static final String COMMAND_WORD = "resize";
    public static final String COMMAND_ALIAS = "rs";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resize the MainWindows to "
            + String.format("the specified (%d<=)WIDTH(<=%d) and (%d<=)HEIGHT(<=%d) \n",
            MainWindow.getMinWidth(), MAX_WIDTH, MainWindow.getMinHeight(), MAX_HEIGHT)
            + "Parameters: WIDTH HEIGHT\n"
            + String.format("Example: " + COMMAND_WORD + " %d %d", MAX_WIDTH, MAX_HEIGHT);
    public static final String MESSAGE_SUCCESS = "Resize successfully to %d*%d";


    private int width;
    private int height;

    public ResizeCommand(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (width > MAX_WIDTH || height > MAX_HEIGHT
            || width < MainWindow.getMinWidth() || height < MainWindow.getMinHeight()) {
            throw new CommandException(MESSAGE_INVALID_COMMAND_PARAMETERS);
        }
        EventsCenter.getInstance().post(new ResizeMainWindowEvent(width, height));
        return new CommandResult(String.format(MESSAGE_SUCCESS, width, height));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResizeCommand // instanceof handles nulls
                && this.width == ((ResizeCommand) other).width
                && this.height == ((ResizeCommand) other).height); // state check
    }
}
```
###### \java\seedu\address\logic\parser\ArgumentWildcardMatcher.java
``` java
/**
 * Convert a list of string of unprocessed keywords with wildcard symbol "*" and "?"
 * into a list of lowercase regular expression matching the keywords.
 */
public class ArgumentWildcardMatcher {

    /**
     * Convert a list of keywords with wildcard symbol "*" and "?"
     * into a list of lowercase regular expression matching the keywords.
     * @param keywords List of String containing unprocessed keywords
     * @return A lists of string regular expression in lowercase matching the keywords.
     */
    public static List<String> processKeywords(List<String> keywords) {
        requireNonNull(keywords);
        ArrayList<String> processedKeywords = new ArrayList<>();
        for (String keyword : keywords) {
            processedKeywords.add(keyword.toLowerCase().replace("*", "\\S*").replace("?", "\\S"));
        }
        return processedKeywords;
    }
}
```
###### \java\seedu\address\logic\parser\FindCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    private static final Prefix[] searchFields = {PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG};

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, searchFields);
        if (!isAnyPrefixPresent(argMultimap, searchFields)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArrayList<Predicate<ReadOnlyPerson>> predicates = new ArrayList<>();

        for (Prefix prefix : searchFields) {
            if (argMultimap.getValue(prefix).isPresent()) {
                List<String> keywords = extractKeywords(argMultimap, prefix);
                switch (prefix.getPrefix()) {
                case "n/":
                    predicates.add(new NameContainsKeywordsPredicate(keywords));
                    break;
                case "p/":
                    predicates.add(new PhoneContainsKeywordsPredicate(keywords));
                    break;
                case "e/":
                    predicates.add(new EmailContainsKeywordsPredicate(keywords));
                    break;
                case "a/":
                    predicates.add(new AddressContainsKeywordsPredicate(keywords));
                    break;
                case "t/":
                    predicates.add(new ContainsTagsPredicate(keywords));
                    break;
                default:
                    assert false : "There should not be other prefixes.";
                }
            } else {
                predicates.add(FALSE);
            }
        }

        return new FindCommand(predicates);
    }

    /**
     * Returns false if all of the prefixes contains empty values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isAnyPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * extracts keywords separated by space under the specified{@code prefix}
     * @param argumentMultimap the argumentMultimap that maps prefix to keywords
     * @param prefix the specified prefix
     * @return the list of keywords
     */
    private static List<String> extractKeywords(ArgumentMultimap argumentMultimap, Prefix prefix) {
        List<String> keywords = new ArrayList<>();
        for (String argument : argumentMultimap.getAllValues(prefix)) {
            keywords.addAll(Arrays.asList(argument.split("\\s+")));
        }
        return keywords;
    }
}
```
###### \java\seedu\address\logic\parser\ResizeCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ResizeCommand object
 */
public class ResizeCommandParser implements Parser<ResizeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ResizeCommand
     * and returns an ResizeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format and precondition
     */
    public ResizeCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.matches("\\d+\\s\\d+")) {
            throwParserException();
        }

        String[] sizeParameters = trimmedArgs.split("\\s+");
        int width = Integer.parseInt(sizeParameters[0]);
        int height = Integer.parseInt(sizeParameters[1]);
        if (width > ResizeCommand.MAX_WIDTH || height > ResizeCommand.MAX_HEIGHT
            || width < MainWindow.getMinWidth() || height < MainWindow.getMinHeight()) {
            throwParserException();
        }

        return new ResizeCommand(width, height);
    }

    private void throwParserException() throws ParseException {
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * raise a new NewPersonInfoEvent whenever a person is added or edited
     * @param person the person added or edited
     */
    private void indicateNewPersonInfoAvailable(ReadOnlyPerson person) {
        raise(new NewPersonInfoEvent(person));
    }
```
###### \java\seedu\address\model\person\Name.java
``` java
        if (name == null) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
```
###### \java\seedu\address\model\person\predicate\AddressContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Address} matches any of the keywords given.
 */
public class AddressContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;


    public AddressContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = ArgumentWildcardMatcher.processKeywords(keywords);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (String address : person.getAddress().value.split("\\s+")) {
            for (String keyword : keywords) {
                if (address.toLowerCase().matches(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((AddressContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\predicate\ContainsTagsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tags} matches any of the tags given.
 */
public class ContainsTagsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public ContainsTagsPredicate(List<String> keywords) {
        this.keywords = ArgumentWildcardMatcher.processKeywords(keywords);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (Tag tag : person.getTags()) {
            for (String keyword : keywords) {
                if (tag.tagName.toLowerCase().matches(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsTagsPredicate // instanceof handles nulls
                && this.keywords.equals(((ContainsTagsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\predicate\EmailContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;


    public EmailContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = ArgumentWildcardMatcher.processKeywords(keywords);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String email = person.getEmail().value;
        return keywords.stream().anyMatch(email.toLowerCase()::matches);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((EmailContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\model\person\predicate\NameContainsKeywordsPredicate.java
``` java
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = ArgumentWildcardMatcher.processKeywords(keywords);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        for (String name : person.getName().fullName.split("\\s+")) {
            for (String keyword : keywords) {
                if (name.toLowerCase().matches(keyword)) {
                    return true;
                }
            }
        }
        return false;
    }
```
###### \java\seedu\address\model\person\predicate\PhoneContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;


    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = ArgumentWildcardMatcher.processKeywords(keywords);
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        String phone = person.getPhone().value;
        return keywords.stream().anyMatch(phone::matches);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedClass.java
``` java
/**
 * Represents all XmlAdapted Classes e.g. XmlAdaptedTag
 * @param <E> the corresponding Class of the XmlAdaptedClass e.g. Tag
 */
public interface XmlAdaptedClass<E> {

    /**
     * Converts the jaxb-friendly adapted object into the corresponding object in model.
     * @throws IllegalValueException if there were any data constraints violated
     */
    public E toModelType() throws IllegalValueException;

}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return convertToModelType(this.persons);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return convertToModelType(this.tags);
    }

    @Override
    public ObservableList<Meeting> getMeetingList() {
        return convertToModelType(this.meetings);
    }

    /**
     * Converts a list of XmlAdaptedType Objects into an ObservableList of ModelType Objects
     * @param xmlAdaptedObjectList the list of the XmlAdaptedType Objects e.g. this.tags
     * @param <ModelT> the Model Type e.g. Tag
     * @param <XmlAdaptedT> the XmlAdaptedType that implements XmlAdaptedClass e.g. XmlAdaptedTag
     * @return an ObservableList of ModelType Objects
     */
    private <ModelT, XmlAdaptedT extends XmlAdaptedClass<ModelT>> ObservableList<ModelT> convertToModelType(
            List<XmlAdaptedT> xmlAdaptedObjectList) {
        final List<ModelT> modelTypeList = new ArrayList<>();
        for (XmlAdaptedT element : xmlAdaptedObjectList) {
            try {
                modelTypeList.add((element.toModelType()));
            } catch (IllegalValueException e) {
                logger.warning("Illegal data found in storage.");
            }
        }
        return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(modelTypeList));
    }
```
###### \java\seedu\address\ui\AutoCompleteTextField.java
``` java
/**
 * This class enables auto-completion feature as a drop down menu from the command box.
 */
public class AutoCompleteTextField extends TextField {

    private static final int MAX_ENTRIES = 5;

    private final ContextMenu dropDownMenu = new ContextMenu();
    private final SuggestionHeuristic heuristic = new SuggestionHeuristic();
    private String prefixWords;
    private String lastWord;

    public AutoCompleteTextField() {
        super();
        // calls generateSuggestions() whenever there is a change to the text of the command box.
        textProperty().addListener((unused1, unused2, unused3) -> generateSuggestions());
        // hides the drop down menu when the focus changes
        focusedProperty().addListener((unused1, unused2, unused3) -> dropDownMenu.hide());
    }

    /**
     * Updates the drop down menu with the new set of matchedWords
     * Shows the menu if the set is non empty.
     * Hides the menu otherwise.
     */
    private void generateSuggestions() {
        splitWords();
        SortedSet<String> matchedWords = heuristic.getSuggestions(prefixWords, lastWord);
        if (matchedWords.size() <= 0) {
            dropDownMenu.hide();
            return;
        }

        fillDropDown(matchedWords);
        if (!dropDownMenu.isShowing()) {
            dropDownMenu.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
        }
    }

    /**
     * Initialises suggestion heuristic for Auto-Completion from
     * the different fields of persons from a list.
     * @param persons a list of person
     */
    public void initialiseHeuristic(List<ReadOnlyPerson> persons) {
        heuristic.initialise(persons);
    }

    /**
     * Fill the dropDownMenu with the matched words up to MAX_ENTRIES.
     * @param matchedWords The list of matched words.
     */
    private void fillDropDown(SortedSet<String> matchedWords) {
        List<MenuItem> menuItems = dropDownMenu.getItems();
        menuItems.clear();

        Iterator<String> iterator = matchedWords.iterator();
        int numEntries = Math.min(matchedWords.size(), MAX_ENTRIES);
        for (int i = 0; i < numEntries; i++) {
            final String suggestion = prefixWords + iterator.next();
            MenuItem item = new CustomMenuItem(new Label(suggestion), true);
            // Complete the word with the chosen suggestion when Enter is pressed.
            item.setOnAction((unused) -> complete(item));
            menuItems.add(item);
        }
    }

    /**
     * Sets the text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    public void replaceText(String text) {
        setText(text);
        positionCaret(getText().length());
    }

    /**
     * Auto-Complete with the first item in the dropDownMenu
     */
    public void completeFirst() {
        if (dropDownMenu.isShowing()) {
            MenuItem item = dropDownMenu.getItems().get(0);
            complete(item);
        }
    }

    /**
     * Auto-Complete with the given MenuItem
     * @param item the MenuItem used for Auto-Complete
     */
    private void complete(MenuItem item) {
        String suggestion = ((Label) ((CustomMenuItem) item).getContent()).getText();
        replaceText(suggestion);
    }

    /**
     * Splits the command in the command box into
     * two parts by the last occurrence of space or slash.
     * Store them into prefixWords and lastWord respectively.
     */
    private void splitWords() {
        String text = getText();
        int lastSpace = text.lastIndexOf(" ");
        int lastSlash = text.lastIndexOf("/");
        int splitingPosition = Integer.max(lastSlash, lastSpace);
        prefixWords = text.substring(0, splitingPosition + 1);
        lastWord = text.substring(splitingPosition + 1).toLowerCase();
    }

    public ContextMenu getDropDownMenu() {
        return dropDownMenu;
    }
}
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case TAB:
            // Auto-complete using the first entry of the drop down menu
            keyEvent.consume();;
            commandTextField.completeFirst();
            break;
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleResizeMainWindowEvent(ResizeMainWindowEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        primaryStage.setWidth(event.getWidth());
        primaryStage.setHeight(event.getHeight());
    }
```
###### \java\seedu\address\ui\ScreenDimension.java
``` java
/**
 * Provides the dimension of the current screen
 */
public class ScreenDimension {

    private static Dimension dimension;

    public static Dimension getDimension() {
        if (dimension != null) {
            return dimension;
        }
        // Handle exceptions in Travis-CI environment
        try {
            dimension = Toolkit.getDefaultToolkit().getScreenSize();
        } catch (Exception e) {
            dimension = new Dimension(1366, 768);
        }
        return dimension;
    }
}
```
###### \java\seedu\address\ui\SuggestionHeuristic.java
``` java
/**
 * This class provides the relevant suggestions for Auto-Completion in TabCompleTextField
 */
public class SuggestionHeuristic {

    private final String[] sortFieldsList = {"name", "phone", "email", "address", "tag", "meeting"};

    private SortedSet<String> empty = new TreeSet<>();
    private SortedSet<String> commands = new TreeSet<>();
    private SortedSet<String> sortFields = new TreeSet<>();
    private SortedSet<String> tags = new TreeSet<>();
    private SortedSet<String> names = new TreeSet<>();
    private SortedSet<String> phones = new TreeSet<>();
    private SortedSet<String> emails = new TreeSet<>();
    private SortedSet<String> addresses = new TreeSet<>();
    private SortedSet<String> windowSizes = new TreeSet<>();

    public SuggestionHeuristic() {
        EventsCenter.getInstance().registerHandler(this);
        commands.addAll(CommandWordList.COMMAND_WORD_LIST);
        sortFields.addAll(Arrays.asList(sortFieldsList));
    }

    /**
     * Generates heuristics using information of a list of person
     * @param persons the list of person to extract information from
     */
    public void initialise(List<ReadOnlyPerson> persons) {
        persons.forEach(this::extractInfoFromPerson);
    }

    /**
     * Extracts the relevant information from a person
     * and put them into respective heuristics
     * @param person
     */
    private void extractInfoFromPerson(ReadOnlyPerson person) {
        names.addAll(Arrays.asList(person.getName().fullName.toLowerCase().split("\\s+")));
        phones.add(person.getPhone().value);
        emails.add(person.getEmail().value.toLowerCase());
        person.getTags().stream().map(tag -> tag.tagName.toLowerCase()).forEachOrdered(tags::add);
        addresses.addAll(Arrays.asList(person.getAddress().value.toLowerCase().split("\\s+")));
    }

    /**
     * Generates a SortedSet containing suggestions from the inputted text
     * @param prefixWords the prefix words in the inputted text
     * @param lastWord the last (partial) word the the inputted text
     * @return a SortedSet that contains suggestions for Auto-Completion
     */
    public SortedSet<String> getSuggestions(String prefixWords, String lastWord) {
        if (lastWord.equals("")) {
            return empty;
        }
        SortedSet<String> suggestionSet = getSuggestionSet(prefixWords);
        return suggestionSet.subSet(lastWord + Character.MIN_VALUE, lastWord + Character.MAX_VALUE);
    }

    /**
     * Returns the relevant SortedSet to generate suggestions from
     * according to the context in the prefixWords
     * @param prefixWords words that have been keyed in before the last word
     * @return a SortedSet for generating suggestions
     */
    private SortedSet<String> getSuggestionSet(String prefixWords) {
        String commandWord = prefixWords.trim().split("\\s+")[0];

        switch (commandWord) {

        // commands that uses prefixes for arguments
        case AddCommand.COMMAND_WORD: case AddCommand.COMMAND_ALIAS:
        case EditCommand.COMMAND_WORD: case EditCommand.COMMAND_ALIAS:
        case FindCommand.COMMAND_WORD: case FindCommand.COMMAND_ALIAS:
            int lastSlash = prefixWords.lastIndexOf("/");
            if (lastSlash - 1 <= 0) {
                return empty;
            }
            switch (prefixWords.substring(lastSlash - 1, lastSlash)) {
            case "n":
                return names;
            case "p":
                return phones;
            case "e":
                return emails;
            case "a":
                return addresses;
            case "t":
                return tags;
            default:
                return empty;
            }

        // commands specifying meeting
        case AddMeetingCommand.COMMAND_WORD: case AddMeetingCommand.COMMAND_ALIAS:
        case DeleteMeetingCommand.COMMAND_WORD: case DeleteMeetingCommand.COMMAND_ALIAS:
            //TODO: BETTER SUGGESTIONS FOR MEETINGS
            return empty;

        // commands specifying tag in argument
        case AddTagCommand.COMMAND_WORD: case AddTagCommand.COMMAND_ALIAS:
        case DeleteTagCommand.COMMAND_WORD: case DeleteTagCommand.COMMAND_ALIAS:
            return tags;

        // commands with no argument or single number argument
        case BackupCommand.COMMAND_WORD: case BackupCommand.COMMAND_ALIAS:
        case ClearCommand.COMMAND_WORD: case ClearCommand.COMMAND_ALIAS:
        case DeleteCommand.COMMAND_WORD: case DeleteCommand.COMMAND_ALIAS:
        case ExitCommand.COMMAND_WORD: case ExitCommand.COMMAND_ALIAS:
        case HelpCommand.COMMAND_WORD: case HelpCommand.COMMAND_ALIAS:
        case HistoryCommand.COMMAND_WORD: case HistoryCommand.COMMAND_ALIAS:
        case LoginCommand.COMMAND_WORD: case LoginCommand.COMMAND_ALIAS:
        case LogoutCommand.COMMAND_WORD: case LogoutCommand.COMMAND_ALIAS:
        case ListCommand.COMMAND_WORD: case ListCommand.COMMAND_ALIAS:
        case SyncCommand.COMMAND_WORD: case SyncCommand.COMMAND_ALIAS:
        case UndoCommand.COMMAND_WORD: case UndoCommand.COMMAND_ALIAS:
        case RedoCommand.COMMAND_WORD: case RedoCommand.COMMAND_ALIAS:
        case RestoreBackupCommand.COMMAND_WORD: case RestoreBackupCommand.COMMAND_ALIAS:
        case SelectCommand.COMMAND_WORD: case SelectCommand.COMMAND_ALIAS:
            return empty;

        // stand alone special commands
        case NoteCommand.COMMAND_WORD: case NoteCommand.COMMAND_ALIAS:
            //TODO: BETTER SUGGESTIONS FOR NOTES
            return empty;

        case ResizeCommand.COMMAND_WORD: case ResizeCommand.COMMAND_ALIAS:
            return windowSizes;

        case SortCommand.COMMAND_WORD: case SortCommand.COMMAND_ALIAS:
            return sortFields;

        // incomplete or wrong command word
        default:
            return commands;
        }
    }


    /**
     * Updates heuristic for resize command from ResizeMainWindowEvent
     */
    @Subscribe
    private void handleResizeMainWindowEvent(ResizeMainWindowEvent event) {
        windowSizes.add(Integer.toString(event.getHeight()));
        windowSizes.add(Integer.toString(event.getWidth()));
    }

    /**
     * Updates heuristic using information from a person
     */
    @Subscribe
    private void handleNewPersonInfoEvent(NewPersonInfoEvent event) {
        extractInfoFromPerson(event.getPerson());
    }
}
```
