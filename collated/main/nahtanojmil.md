# nahtanojmil
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from person: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " : edits the remarks of a person "
            + "by the index from the last shown list. Old remarks will be overwritten by new "
            + "ones entered.\n"
            + "parameters: INDEX (positive integer)" + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + "3 " + PREFIX_REMARK + "likes to eat!";

    private Index index;
    private Remark stringRemark;


    public RemarkCommand(Index index, Remark remark) {
        if (index != null && remark != null) {
            this.index = index;
            this.stringRemark = remark;
        }
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson toBeEdited = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(toBeEdited.getName(), toBeEdited.getPhone(),
                toBeEdited.getEmail(), toBeEdited.getAddress(), toBeEdited.getFormClass(),
                toBeEdited.getGrades(), toBeEdited.getPostalCode(), stringRemark, toBeEdited.getTags());

        try {
            model.updatePerson(toBeEdited, editedPerson);
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException f) {
            throw new AssertionError("Person must be from last filtered list");
        }
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * @param editedPerson takes in a edited person and
     * @return if the remark changed is successful
     */
    private String generateSuccessMessage(ReadOnlyPerson editedPerson) {
        if (!stringRemark.value.equals(REMARK_IF_EMPTY)) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, editedPerson);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit
            return true;
        }
        if (!(other instanceof RemarkCommand)) { // if other is not RemarkCommand type; return false
            return false;
        }

        //state check: checks if both are the same object in each class
        RemarkCommand e = (RemarkCommand) other;
        return stringRemark.equals(e.stringRemark) && index.equals(e.index);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> remark} into an {@code Optional<Remark>} if {@code remark}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Remark> parseRemark(Optional<String> remark) throws IllegalValueException {
        requireNonNull(remark);
        return remark.isPresent() ? Optional.of(new Remark(remark.get())) : Optional.empty();
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws IllegalValueException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code Optional<String> schedule} into an {@code Optional<Schedule>} if {@code schedule}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Schedule> parseSchedule(Optional<String> schedule) throws IllegalValueException {
        requireNonNull(schedule);
        return schedule.isPresent() ? Optional.of(new Schedule(schedule.get())) : Optional.empty();
    }

}
```
###### \java\seedu\address\logic\parser\RemarkCommandParser.java
``` java
/**
 * Remark command parser
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {
    /**
     *  Parses the given {@code String} of arguments in the context of the RemarkCommand and
     * @returns an RemarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public RemarkCommand parse(String userInput) throws ParseException {

        requireNonNull(userInput);
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(userInput, PREFIX_REMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultiMap.getPreamble());
        } catch (IllegalValueException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        String remark = argMultiMap.getValue(PREFIX_REMARK).orElse("");

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

    public static final String REMARK_IF_EMPTY = "(add a remark)";

    public final String value;

    /**
     * Validates given remark.
     *
     */
    public Remark(String remark) {
        requireNonNull(remark);
        if (remark.equals("")) {
            this.value = REMARK_IF_EMPTY;
        } else {
            this.value = remark;
        }
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
###### \java\seedu\address\ui\GraphPanel.java
``` java
/**
 * Displays the specified graphs that the user wants
 */
public class GraphPanel extends UiPart<Region> {


    private static final String FXML = "GraphPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<ReadOnlyPerson> people;
    private XYChart.Series<String, Double> series = new XYChart.Series<>();

    @FXML
    private CategoryAxis xNameAxis;
    @FXML
    private NumberAxis yGradeAxis;
    @FXML
    private LineChart<String, Double> lineChart;

    public GraphPanel(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);
        this.people = personList;
        registerAsAnEventHandler(this);
    }

    /**
     * Displays statistics of @param person according to class
     */
    private void displayGraphStats (ReadOnlyPerson person) {
        xNameAxis.setLabel("Student Names");
        yGradeAxis.setLabel("Grades");
        lineChart.setTitle(person.getFormClass().toString());
        lineChart.layout();
        lineChart.setAnimated(false);

        for (ReadOnlyPerson people : people) {
            if (people.getFormClass().equals(person.getFormClass())) {
                series.getData().add(new XYChart.Data<>(people.getName().toString(),
                        Double.parseDouble(people.getGrades().toString())));
            }
        }

        try {
            series.getData().sort(Comparator.comparingDouble(d -> d.getYValue()));
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
        lineChart.getData().add(series);
        lineChart.setLegendVisible(false);
    }


    private void resetGraphStats() {
        series.getData().clear();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetGraphStats();
        displayGraphStats(event.getNewSelection().person);
    }
}
```
###### \resources\view\GraphPanel.fxml
``` fxml

<?import javafx.scene.chart.CategoryAxis?>
<?import javafx.scene.chart.LineChart?>
<?import javafx.scene.chart.NumberAxis?>
<?import javafx.scene.layout.AnchorPane?>
<AnchorPane prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.111"
            xmlns:fx="http://javafx.com/fxml/1">

    <children>
        <LineChart fx:id="lineChart" layoutY="10.0" maxHeight="1.5" maxWidth="1.5" minHeight="300" minWidth="300"
                   prefHeight="600" prefWidth="800.0">
            <xAxis>
                <CategoryAxis minHeight="400.0" minWidth="400.0" side="BOTTOM" fx:id="xNameAxis"/>
            </xAxis>
            <yAxis>
                <NumberAxis fx:id="yGradeAxis" side="LEFT" tickLabelFill="#ffffff2e"/>
            </yAxis>
        </LineChart>
    </children>

</AnchorPane>
```
