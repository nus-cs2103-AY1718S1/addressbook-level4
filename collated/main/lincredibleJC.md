# lincredibleJC
###### \java\seedu\address\commons\events\ui\FilteredListChangedEvent.java
``` java
/**
 * Represents a Change in the current Filtered List
 */
public class FilteredListChangedEvent extends BaseEvent {

    private final ObservableList<ReadOnlyPerson> currentFilteredList;

    public FilteredListChangedEvent(ObservableList<ReadOnlyPerson> currentFilteredList) {
        this.currentFilteredList = currentFilteredList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyPerson> getCurrentFilteredList() {
        return currentFilteredList;
    }
}
```
###### \java\seedu\address\logic\commands\FindTagsCommand.java
``` java
/**
 * Finds and lists all persons in address book whose tags contains any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagsCommand extends Command {

    public static final String COMMAND_WORD = "findtags";
    public static final String COMMAND_ALIAS = "ft";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends owesMoney";

    private final TagsContainsKeywordsPredicate predicate;

    public FindTagsCommand(TagsContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(predicate);
        EventsCenter.getInstance().post(new FilteredListChangedEvent(model.getFilteredPersonList()));
        return new CommandResult(getMessageForPersonListShownSummary(model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindTagsCommand // instanceof handles nulls
                && this.predicate.equals(((FindTagsCommand) other).predicate)); // state check
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD://Fallthrough
        case AddCommand.COMMAND_ALIAS:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD://Fallthrough
        case EditCommand.COMMAND_ALIAS:
            return new EditCommandParser().parse(arguments);

        case SelectCommand.COMMAND_WORD://Fallthrough
        case SelectCommand.COMMAND_ALIAS:
            return new SelectCommandParser().parse(arguments);

        case ScheduleCommand.COMMAND_WORD://Fallthrough
        case ScheduleCommand.COMMAND_ALIAS:
            return new ScheduleCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD://Fallthrough
        case DeleteCommand.COMMAND_ALIAS:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD://Fallthrough
        case ClearCommand.COMMAND_ALIAS:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD://Fallthrough
        case FindCommand.COMMAND_ALIAS:
            return new FindCommandParser().parse(arguments);

        case FindTagsCommand.COMMAND_WORD://Fallthrough
        case FindTagsCommand.COMMAND_ALIAS:
            return new FindTagsCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD://Fallthrough
        case ListCommand.COMMAND_ALIAS:
            return new ListCommand();

        case HistoryCommand.COMMAND_WORD://Fallthrough
        case HistoryCommand.COMMAND_ALIAS:
            return new HistoryCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case UndoCommand.COMMAND_WORD://Fallthrough
        case UndoCommand.COMMAND_ALIAS:
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD://Fallthrough
        case RedoCommand.COMMAND_ALIAS:
            return new RedoCommand();

        case SortCommand.COMMAND_WORD:
        case SortCommand.COMMAND_ALIAS:
            return new SortCommand();

        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return new RemarkCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
```
###### \java\seedu\address\logic\parser\FindTagsCommandParser.java
``` java
/**
 * Parses input arguments and creates a new FindTagsCommand object
 */
public class FindTagsCommandParser implements Parser<FindTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagsCommand
     * and returns an FindTagsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindTagsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagsCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new FindTagsCommand(new TagsContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> formClass} into an {@code Optional<FormClass>} if {@code formClass}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<FormClass> parseFormClass(Optional<String> formClass) throws IllegalValueException {
        requireNonNull(formClass);
        return formClass.isPresent() ? Optional.of(new FormClass(formClass.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> grades} into an {@code Optional<PostalCode>} if {@code grades}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Grades> parseGrades(Optional<String> grades) throws IllegalValueException {
        requireNonNull(grades);
        return grades.isPresent() ? Optional.of(new Grades(grades.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\statistics\Statistics.java
``` java
/**
 * Calculates statistics of the persons inside an ObservableList
 */
public class Statistics {

    static final String NO_PERSONS_MESSAGE = "There are no persons";
    static final String INSUFFICIENT_DATA_MESSAGE = "Insufficient Data";
    private final int numDecimalPlace = 2;

    private double[] scoreArray;
    private int size;

    public Statistics(ObservableList<ReadOnlyPerson> personList) {
        initScore(personList);
    }

    protected Statistics(double[] scoreArray) {
        initScore(scoreArray);
    }

    /**
     * Takes in a PersonList and initialises the appropriate values to the Statistics instance
     *
     * @param personList the list of persons being taken in
     */
    public void initScore(ObservableList<ReadOnlyPerson> personList) {
        int listSize = personList.size();
        double[] listArray = new double[listSize];
        for (int i = 0; i < listSize; i++) {
            Person person = (Person) personList.get(i);
            listArray[i] = Double.parseDouble((person.getGrades().value));
        }
        initScore(listArray);
    }

    /**
     * Takes in an array and assigns the appropriate values to the Statistics instance
     *
     * @param scoreArray the array of doubles used fo calculating statistics
     */
    public void initScore(double[] scoreArray) {
        Arrays.sort(scoreArray);
        this.scoreArray = scoreArray;
        this.size = scoreArray.length;
    }

    private double getMean() {
        return DoubleStream.of(scoreArray).sum() / size;
    }

    public String getMeanString() {
        if (size > 0) {
            return getRoundedStringFromDouble(getMean(), numDecimalPlace);
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    private double getMedian() {
        return (size % 2 == 1)
                ? scoreArray[(size - 1) / 2]
                : (scoreArray[(size - 1) / 2] + scoreArray[((size - 1) / 2) + 1]) / 2;
    }

    public String getMedianString() {
        if (size > 0) {
            return getRoundedStringFromDouble(getMedian(), numDecimalPlace);
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    private double getMedianWithIndexes(double[] arr, int startIndex, int endIndex) {
        int currSize = endIndex - startIndex + 1;
        return (currSize % 2 == 0)
                ? (arr[startIndex + currSize / 2 - 1] + arr[startIndex + currSize / 2]) / 2
                : arr[startIndex + (currSize - 1) / 2];
    }

    private double getMode() {
        double mode = 0;
        double currPersonScore;
        int maxCount = 0;
        for (int i = 0; i < size; i++) {
            currPersonScore = scoreArray[i];
            int currCount = 0;
            for (int j = 0; j < size; j++) {
                if (currPersonScore == scoreArray[j]) {
                    currCount++;
                }
                if (currCount > maxCount) {
                    maxCount = currCount;
                    mode = currPersonScore;
                }
            }
        }
        return mode;
    }

    public String getModeString() {
        if (size > 0) {
            return getRoundedStringFromDouble(getMode(), numDecimalPlace);
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    private double getQuartile1() {
        return getMedianWithIndexes(scoreArray, 0, size / 2 - 1);
    }

    public String getQuartile1String() {
        if (size > 1) {
            return getRoundedStringFromDouble(getQuartile1(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    private double getQuartile2() {
        return getMedianWithIndexes(scoreArray, 0, size - 1);
    }

    public String getQuartile2String() {
        if (size > 1) {
            return getRoundedStringFromDouble(getQuartile2(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    private double getQuartile3() {
        return (size % 2 == 0)
                ? getMedianWithIndexes(scoreArray, size / 2, size - 1)
                : getMedianWithIndexes(scoreArray, size / 2 + 1, size - 1);
    }

    public String getQuartile3String() {
        if (size > 1) {
            return getRoundedStringFromDouble(getQuartile3(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    private double getInterQuartileRange() {
        return getQuartile3() - getQuartile1();
    }

    public String getInterquartileRangeString() {
        if (size > 1) {
            return getRoundedStringFromDouble(getInterQuartileRange(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    private double getVariance() {
        double temp = 0;
        double mean = getMean();
        for (double score : scoreArray) {
            temp += (score - mean) * (score - mean);
        }
        return temp / (size - 1);
    }

    public String getVarianceString() {
        if (size > 1) {
            return getRoundedStringFromDouble(getVariance(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    private double getStdDev() {
        return Math.sqrt(getVariance());
    }

    public String getStdDevString() {
        if (size > 1) {
            return getRoundedStringFromDouble(getStdDev(), numDecimalPlace);
        } else if (size > 0) {
            return INSUFFICIENT_DATA_MESSAGE;
        } else {
            return NO_PERSONS_MESSAGE;
        }
    }

    /**
     * Formats and returns a double into a fixed number of decimal places and returns it as a string
     *
     * @param value  the double to be formatted
     * @param places number of decimal places of the output string
     * @return value formatted to places decimal places in a String
     */
    private static String getRoundedStringFromDouble(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return Double.toString(bd.doubleValue());
    }

}
```
###### \java\seedu\address\model\person\FormClass.java
``` java
/**
 * Represents a Person's FormClass name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidFormClass(String)}
 */
public class FormClass {

    public static final String MESSAGE_FORMCLASS_CONSTRAINTS =
            "FormClass names should be alphanumeric and can contain '.' and '-' ";

    public static final String FORMCLASS_VALIDATION_REGEX = "^[a-zA-Z0-9\\.\\-\\/]+$";

    public final String value;

    /**
     * Validates given FormClass.
     *
     * @throws IllegalValueException if given FormClass string is invalid.
     */
    public FormClass(String formClass) throws IllegalValueException {
        requireNonNull(formClass);
        String trimmedFormClass = formClass.trim();
        if (!isValidFormClass(trimmedFormClass)) {
            throw new IllegalValueException(MESSAGE_FORMCLASS_CONSTRAINTS);
        }
        this.value = trimmedFormClass.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid formClass name.
     */
    public static boolean isValidFormClass(String test) {
        return test.matches(FORMCLASS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FormClass // instanceof handles nulls
                && this.value.equals(((FormClass) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
```
###### \java\seedu\address\model\person\Grades.java
``` java
/**
 * Represents a Person's Grades in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrades(String)}
 */
public class Grades {

    public static final String MESSAGE_GRADES_CONSTRAINTS =
            "Grades can only contain positive numbers up to 2 decimal points";

    public static final String GRADES_VALIDATION_REGEX = "^((\\d|[1-9]\\d+)(\\.\\d{1,2})?|\\.\\d{1,2})$";

    public final String value;

    /**
     * Validates given Grades.
     *
     * @throws IllegalValueException if given Grades string is invalid.
     */
    public Grades(String grades) throws IllegalValueException {
        requireNonNull(grades);
        String trimmedGrades = grades.trim();
        if (!isValidGrades(trimmedGrades)) {
            throw new IllegalValueException(MESSAGE_GRADES_CONSTRAINTS);
        }
        this.value = trimmedGrades;
    }

    /**
     * Returns true if a given string is a valid Grades.
     */
    public static boolean isValidGrades(String test) {
        return test.matches(GRADES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grades // instanceof handles nulls
                && this.value.equals(((Grades) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }



}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
    default String getTagsAsString() {
        StringBuilder sb = new StringBuilder();
        getTags().forEach(tag -> sb.append(tag.tagName + " "));
        return sb.toString();
    }
```
###### \java\seedu\address\model\person\TagsContainsKeywordsPredicate.java
``` java
/**
 * Tests that a {@code ReadOnlyPerson}'s {@code Tag} matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<ReadOnlyPerson> {
    private final List<String> keywords;

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(ReadOnlyPerson person) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getTagsAsString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
                && this.keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }

}

```
###### \java\seedu\address\ui\StatisticsPanel.java
``` java
/**
 * Statistics Panel that displays the statistics of a filteredList
 */
public class StatisticsPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    private ObservableList<ReadOnlyPerson> currentList;
    private Statistics statistics;

    @FXML
    private VBox cardpane;
    @FXML
    private Label mean;
    @FXML
    private Label median;
    @FXML
    private Label mode;
    @FXML
    private Label variance;
    @FXML
    private Label standardDeviation;
    @FXML
    private Label quartile1;
    @FXML
    private Label quartile3;
    @FXML
    private Label interquartileRange;

    public StatisticsPanel(ObservableList<ReadOnlyPerson> currentList) {
        super(FXML);
        registerAsAnEventHandler(this);
        statistics = new Statistics(currentList);

    }

    /**
     * Updates list Statistics in the Statistics panel
     */
    protected void loadListStatistics() {
        mean.setText(statistics.getMeanString());
        median.setText(statistics.getMedianString());
        mode.setText(statistics.getModeString());
        variance.setText(statistics.getVarianceString());
        standardDeviation.setText(statistics.getStdDevString());
        quartile1.setText(statistics.getQuartile1String());
        quartile3.setText(statistics.getQuartile3String());
        interquartileRange.setText(statistics.getInterquartileRangeString());
    }

    @FXML
    @Subscribe
    private void handleFilteredListChangedEvent(FilteredListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        statistics.initScore(event.getCurrentFilteredList()); // Update currentList data
        loadListStatistics();
    }

}
```
###### \resources\view\StatisticsPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox id="stackPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <padding>
        <Insets bottom="5" left="5" right="15" top="5" />
    </padding>
    <children>
        <Label layoutX="10.0" layoutY="172.0" text="Group Statistics">
            <font>
                <Font size="28.0" />
            </font>
        </Label>
        <Label id="mean" fx:id="mean" text="\$mean">
            <graphic>
                <Label text="Mean:" />
            </graphic>
        </Label>
        <Label id="median" fx:id="median" text="\$median">
            <graphic>
                <Label text="Median" />
            </graphic>
        </Label>
        <Label id="mode" fx:id="mode" layoutX="10.0" layoutY="37.0" text="\$mode">
            <graphic>
                <Label text="Mode:" />
            </graphic>
        </Label>
        <Label id="variance" fx:id="variance" layoutX="10.0" layoutY="10.0" text="\$variance">
            <graphic>
                <Label text="Variance:" />
            </graphic>
        </Label>
        <Label id="standardDeviation" fx:id="standardDeviation" layoutX="10.0" layoutY="10.0" text="\$standardDeviation">
            <graphic>
                <Label text="StandardDeviation" />
            </graphic>
        </Label>
        <Label id="quartile3" fx:id="quartile3" text="\$quartile3">
            <graphic>
                <Label text="75th Percentile:" />
            </graphic>
        </Label>
        <Label id="quartile1" fx:id="quartile1" layoutX="10.0" layoutY="37.0" text="\$quartile1">
            <graphic>
                <Label text="25th Percentile:" />
            </graphic>
        </Label>
        <Label id="interQuartileRange" fx:id="interquartileRange" layoutX="10.0" layoutY="91.0" text="\$interquartileRange">
            <graphic>
                <Label text="Interquartile Range:" />
            </graphic>
        </Label>
    </children>
</VBox>
```
