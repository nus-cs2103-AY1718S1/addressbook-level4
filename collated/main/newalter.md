# newalter
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
###### \java\seedu\address\logic\commands\FilterCommand.java
``` java
/**
 * Filters the current list with persons who are tagged with any of the specified tags.
 * Tag matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";
    public static final String COMMAND_ALIAS = "ft";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the current list with persons who are tagged"
            + " with any of the specified tags (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: TAG [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " family friends colleagues";

    private final ContainsTagsPredicate predicate;

    public FilterCommand(ContainsTagsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        Predicate<? super ReadOnlyPerson>  currentPredicate = model.getPersonListPredicate();
        if (currentPredicate == null) {
            model.updateFilteredPersonList(predicate);
        } else {
            model.updateFilteredPersonList(predicate.and(currentPredicate));
        }
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
            + String.format("the specified WIDTH(<=%d) and HEIGHT(<=%d) \n", MAX_WIDTH, MAX_HEIGHT)
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
        if (width > MAX_WIDTH || height > MAX_HEIGHT) {
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
     * Convert arguments string of keywords with wildcard symbol "*" and "?"
     * into a list of lowercase regular expression matching the keywords.
     * @param keywords List of String containing unprocessed keywords
     * @return A lists of string regular expression in lowercase matching the keywords.
     */
    public static List<String> processKeywords(List<String> keywords) {
        ArrayList<String> processedKeywords = new ArrayList<>();
        for (String keyword : keywords) {
            processedKeywords.add(keyword.toLowerCase().replace("*", "\\S*").replace("?", "\\S"));
        }
        return processedKeywords;
    }
}
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
     * @throws ParseException if the user input does not conform the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        String[] tags = trimmedArgs.split("\\s+");

        return new FilterCommand(new ContainsTagsPredicate(Arrays.asList(tags)));
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
        if (width > ResizeCommand.MAX_WIDTH || height > ResizeCommand.MAX_HEIGHT) {
            throwParserException();
        }

        return new ResizeCommand(width, height);
    }

    private void throwParserException() throws ParseException {
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResizeCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Returns the predicate of the current filtered person list */
    Predicate<? super ReadOnlyPerson> getPersonListPredicate();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public Predicate<? super ReadOnlyPerson> getPersonListPredicate() {
        return filteredPersons.getPredicate();
    }
```
###### \java\seedu\address\model\person\ContainsTagsPredicate.java
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
###### \java\seedu\address\model\person\NameContainsKeywordsPredicate.java
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
###### \java\seedu\address\ui\CommandBox.java
``` java
        commandTextField.generateOptions(logic.getFilteredPersonList());
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        case TAB:
            // Auto-complete using the first entry of the drop down menu
            keyEvent.consume();;
            commandTextField.completeFirst();
            break;
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        commandTextField.replaceText(historySnapshot.previous());
```
###### \java\seedu\address\ui\CommandBox.java
``` java
        commandTextField.replaceText(historySnapshot.next());
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
            dimension = new Dimension();
        }
        return dimension;
    }
}
```
###### \java\seedu\address\ui\TabCompleteTextField.java
``` java
/**
 * This class enables auto-completion feature as a drop down menu from the command box.
 */
public class TabCompleteTextField extends TextField {

    private static final int MAX_ENTRIES = 5;

    private final SortedSet<String> options = new TreeSet<>();
    private final ContextMenu dropDownMenu = new ContextMenu();

    private String prefixWords;
    private String lastWord;


    public TabCompleteTextField() {
        super();
        // calls generateSuggestions() whenever there is a change to the text of the command box.
        textProperty().addListener((unused1, unused2, unused3) -> generateSuggestions());
        // hides the drop down menu whenever the focus in not on the command box
        focusedProperty().addListener((unused1, unused2, unused3) -> dropDownMenu.hide());
    }

    /**
     * Generates a list of suggestions according to the prefix of the lastWord.
     * Shows the drop down menu if the menu is not empty.
     * Hides the menu otherwise.
     */
    private void generateSuggestions() {
        splitCommandWords();
        if (lastWord.length() == 0 || prefixWords.equals("")) {
            dropDownMenu.hide();
        } else {
            LinkedList<String> matchedWords = new LinkedList<>();
            matchedWords.addAll(options.subSet(lastWord + Character.MIN_VALUE, lastWord + Character.MAX_VALUE));
            if (matchedWords.size() > 0) {
                fillDropDown(matchedWords);
                if (!dropDownMenu.isShowing()) {
                    dropDownMenu.show(TabCompleteTextField.this, Side.BOTTOM, 0, 0);
                }
            } else {
                dropDownMenu.hide();
            }
        }
    }

    /**
     * Generate options for Auto-Completion from
     * the names and tags of persons from a list.
     * @param persons a list of person
     */
    public void generateOptions(List<ReadOnlyPerson> persons) {
        for (ReadOnlyPerson person : persons) {
            options.addAll(Arrays.asList(person.getName().fullName.split("\\s+")));
            person.getTags().stream().map(tag -> tag.tagName).forEachOrdered(options::add);
        }
    }

    /**
     * Splits the command in the command box into
     * two parts by the last occurrence of space.
     * Store them into prefixWords and lastWord respectively.
     */
    private void splitCommandWords() {
        String text = getText();
        int lastSpace = text.lastIndexOf(" ");
        prefixWords = text.substring(0, lastSpace + 1);
        lastWord = text.substring(lastSpace + 1);
    }

    /**
     * Fill the dropDownMenu with the matched words up to MAX_ENTRIES.
     * @param matchedWords The list of matched words.
     */
    private void fillDropDown(List<String> matchedWords) {
        List<MenuItem> menuItems = dropDownMenu.getItems();
        menuItems.clear();

        int numEntries = Math.min(matchedWords.size(), MAX_ENTRIES);
        for (int i = 0; i < numEntries; i++) {
            final String suggestion = prefixWords + matchedWords.get(i);
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

    public ContextMenu getDropDownMenu() {
        return dropDownMenu;
    }
}
```
