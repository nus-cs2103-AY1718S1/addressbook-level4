# lincredibleJC
###### /java/seedu/address/commons/events/model/FilteredPersonListChangedEvent.java
``` java
/** Represents the FilteredPersonList in the model has changed */
public class FilteredPersonListChangedEvent extends BaseEvent {

    private final ObservableList<ReadOnlyPerson> currentFilteredList;

    public FilteredPersonListChangedEvent(ObservableList<ReadOnlyPerson> currentFilteredList) {
        this.currentFilteredList = currentFilteredList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public ObservableList<ReadOnlyPerson> getCurrentFilteredPersonList() {
        return currentFilteredList;
    }
}
```
###### /java/seedu/address/logic/commands/FindTagsCommand.java
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
###### /java/seedu/address/logic/parser/AddressBookParser.java
``` java
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

        case ViewScheduleCommand.COMMAND_WORD:
        case ViewScheduleCommand.COMMAND_ALIAS:
            return new ViewScheduleCommand();

        case DeleteScheduleCommand.COMMAND_WORD:
        case DeleteScheduleCommand.COMMAND_ALIAS:
            return new DeleteScheduleCommandParser().parse(arguments);

        case RemarkCommand.COMMAND_WORD:
        case RemarkCommand.COMMAND_ALIAS:
            return new RemarkCommandParser().parse(arguments);

        case TabCommand.COMMAND_WORD:
            return new TabCommandParser().parse(arguments);

        case HomeCommand.COMMAND_WORD:
            return new HomeCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
```
###### /java/seedu/address/logic/parser/FindTagsCommandParser.java
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
###### /java/seedu/address/logic/parser/ParserUtil.java
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
###### /java/seedu/address/logic/statistics/Statistics.java
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
     * Takes in a PersonList, extracts every person's grades, sorts it and stores it in the current Statistics instance
     *
     * @param personList the list of persons to extract the scores from
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
     * Takes in an array of scores, sorts it and stores it in the current Statistics instance
     *
     * @param scoreArray the array of doubles used for calculating statistics
     */
    public void initScore(double[] scoreArray) {
        Arrays.sort(scoreArray);
        this.scoreArray = scoreArray;
        this.size = scoreArray.length;
    }

    /**
     * @return the average value in scoreArray
     */
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

    /**
     * @return Returns the middle number in scoreArray. If scoreArray is odd, the middle number is returned.
     * If scoreArray is even, the average of the two numbers in the middle is returned
     */
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

    /**
     * Returns the middle number in the array starting at startIndex and ending at endIndex in scoreArray
     * If scoreArray is odd, the middle number is returned
     * If scoreArray is even, the average of the two numbers in the middle is returned
     *
     * @param arr        the array of numbers
     * @param startIndex the starting index of the desired array in scoreArray
     * @param endIndex   the ending index of teh desired array in scoreArray
     * @return the middle value of the array starting at startIndex and ending at endIndex in scoreArray
     */
    private double getMedianWithIndexes(double[] arr, int startIndex, int endIndex) throws NegativeArraySizeException {
        int currSize = endIndex - startIndex + 1;
        if (currSize >= 0) {
            return (currSize % 2 == 0)
                    ? (arr[startIndex + currSize / 2 - 1] + arr[startIndex + currSize / 2]) / 2
                    : arr[startIndex + (currSize - 1) / 2];
        }
        throw new NegativeArraySizeException("endIndex must not be smaller than startIndex!");
    }

    /**
     * Returns the smaller number if there is a tie
     *
     * @return the double value that appears most frequently in scoreArray
     */
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

    /**
     * @return the 25th percontile score in the scoreArray
     */
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

    /**
     * @return the 75th percentile score in the scoreArray
     */
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

    /**
     * @return the difference between the 75th and 25th percentile scores in the scoreArray
     */
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

    /**
     * @return the variance of the values in the scoreArray
     */
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

    /**
     * @return the standard deviations of the values in the scoreArray
     */
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
     * Formats a double into a fixed number of decimal places and returns it as a string
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
###### /java/seedu/address/model/ModelManager.java
``` java
    /** Raises an event to update the StatisticsPanel */
    private void updateStatisticsPanel() {
        raise(new FilteredPersonListChangedEvent(filteredPersons));
    }
```
###### /java/seedu/address/model/ModelManager.java
``` java
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        updateStatisticsPanel();
    }
```
###### /java/seedu/address/model/person/FormClass.java
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
###### /java/seedu/address/model/person/Grades.java
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
###### /java/seedu/address/model/person/ReadOnlyPerson.java
``` java
    default String getTagsAsString() {
        StringBuilder sb = new StringBuilder();
        getTags().forEach(tag -> sb.append(tag.tagName + " "));
        return sb.toString();
    }
```
###### /java/seedu/address/model/person/TagsContainsKeywordsPredicate.java
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
###### /java/seedu/address/ui/StatisticsPanel.java
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
        loadListStatistics();
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
    private void handleFilteredPersonListChangedEvent(FilteredPersonListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        statistics.initScore(event.getCurrentFilteredPersonList()); // Updates the statistics instance values
        loadListStatistics();
    }

}
```
###### /resources/view/StatisticsPanel.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>
<VBox id="stackPane" prefHeight="315.0" prefWidth="335.0" xmlns="http://javafx.com/javafx/8.0.111"
      xmlns:fx="http://javafx.com/fxml/1">
    <padding>
        <Insets bottom="5" left="5" right="15" top="5"/>
    </padding>
    <children>
        <Label fx:id="groupStatistics" layoutX="10.0" layoutY="172.0" text="Statistics of the current list">
            <font>
                <Font size="28.0"/>
            </font>
            <graphic>
                <ImageView fitHeight="31.0" fitWidth="34.0" pickOnBounds="true" preserveRatio="true">
                    <image>
                        <Image url="@../images/statistics.png"/>
                    </image>
                </ImageView>
            </graphic>
        </Label>
        <GridPane>
            <columnConstraints>
                <ColumnConstraints hgrow="NEVER" maxWidth="130" minWidth="130.0" prefWidth="150.0"/>
                <ColumnConstraints hgrow="SOMETIMES" maxWidth="275" minWidth="10.0" prefWidth="275.0"/>
            </columnConstraints>
            <rowConstraints>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0" vgrow="SOMETIMES"/>
            </rowConstraints>
            <children>
                <Label text="Mean:"/>
                <Label id="mean" fx:id="mean" text="\$mean" GridPane.columnIndex="1"/>
                <Label text="Median:" GridPane.rowIndex="1"/>
                <Label text="Mode:" GridPane.rowIndex="2"/>
                <Label text="Variance:" GridPane.rowIndex="3"/>
                <Label text="StandardDeviation:" GridPane.rowIndex="4"/>
                <Label text="75th Percentile:" GridPane.rowIndex="5"/>
                <Label text="25th Percentile:" GridPane.rowIndex="6"/>
                <Label text="Interquartile Range:" GridPane.rowIndex="7"/>
                <Label id="median" fx:id="median" text="\$median" GridPane.columnIndex="1" GridPane.rowIndex="1"/>
                <Label id="mode" fx:id="mode" text="\$mode" GridPane.columnIndex="1" GridPane.rowIndex="2"/>
                <Label id="variance" fx:id="variance" text="\$variance" GridPane.columnIndex="1" GridPane.rowIndex="3"/>
                <Label id="standardDeviation" fx:id="standardDeviation" text="\$standardDeviation"
                       GridPane.columnIndex="1" GridPane.rowIndex="4"/>
                <Label id="quartile3" fx:id="quartile3" text="\$quartile3" GridPane.columnIndex="1"
                       GridPane.rowIndex="5"/>
                <Label id="quartile1" fx:id="quartile1" text="\$quartile1" GridPane.columnIndex="1"
                       GridPane.rowIndex="6"/>
                <Label id="interQuartileRange" fx:id="interquartileRange" text="\$interquartileRange"
                       GridPane.columnIndex="1" GridPane.rowIndex="7"/>
            </children>
        </GridPane>
    </children>
</VBox>
```
