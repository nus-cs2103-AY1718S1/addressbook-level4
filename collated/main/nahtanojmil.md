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
        Person editedPerson = new Person(toBeEdited.getName(), toBeEdited.getPhone(), toBeEdited.getParentPhone(),
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
    private XYChart.Series<String, Double> lineSeries = new XYChart.Series<>();
    private XYChart.Series<String, Double> barSeries = new XYChart.Series<>();

    private Logic logic;

    @FXML
    private TabPane tabPaneGraphs;
    @FXML
    private CategoryAxis xLineNameAxis;
    @FXML
    private NumberAxis yLineGradeAxis;
    @FXML
    private CategoryAxis xBarNameAxis;
    @FXML
    private NumberAxis yBarGradeAxis;
    @FXML
    private LineChart<String, Double> lineChart;
    @FXML
    private BarChart<String, Double> barChart;

    public GraphPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        people = logic.getFilteredPersonList();
        registerAsAnEventHandler(this);
    }

    /**
     * Displays line statistics of @param person according to class/tags
     */
    private void displayLineGraphStats(ReadOnlyPerson person) {

        lineChart.setAnimated(false);
        lineChart.layout();
        yLineGradeAxis.setLabel("Grades");
        xLineNameAxis.setLabel("Student Names");

        lineChart.setTitle(person.getFormClass().toString());

        for (ReadOnlyPerson people : people) {
            if (people.getFormClass().equals(person.getFormClass())) {
                lineSeries.getData().add(new XYChart.Data<>(people.getName().toString(),
                        Double.parseDouble(people.getGrades().toString())));
            }
        }

        lineSeries.getData().sort(Comparator.comparingDouble(d -> d.getYValue()));
        lineChart.getData().add(lineSeries);
        lineChart.setLegendVisible(false);

    }

    /**
     * Displays bar statistics of @param person according to class/tags
     */
    private void displayBarGraphStats(ReadOnlyPerson person) {

        xBarNameAxis.setLabel("Student Names");
        yBarGradeAxis.setLabel("Grades");
        barChart.setAnimated(false);
        barChart.layout();

        barChart.setTitle(person.getFormClass().toString());

        for (ReadOnlyPerson people : people) {
            if (people.getFormClass().equals(person.getFormClass())) {
                barSeries.getData().add(new XYChart.Data<>(people.getName().toString(),
                        Double.parseDouble(people.getGrades().toString())));
            }
        }

        barSeries.getData().sort(Comparator.comparingDouble(d -> d.getYValue()));
        barChart.getData().add(barSeries);
        barChart.setLegendVisible(false);
    }

    private void selectTab(int index) {
        tabPaneGraphs.getSelectionModel().select(index);
    }

    /**
     * Resets both graphs and series when a new person is selected.
     */
    private void resetGraphStats() {
        lineChart.getData().clear();
        lineSeries.getData().clear();
        barChart.getData().clear();
        barSeries.getData().clear();
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetGraphStats();
        displayLineGraphStats(event.getNewSelection().person);
        displayBarGraphStats(event.getNewSelection().person);
    }

    @Subscribe
    private void handleTabPanelSelectionChangedEvent (JumpToTabRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        selectTab(event.targetIndex);
    }
}
```
###### \resources\view\GraphPanel.fxml
``` fxml
<?import javafx.scene.chart.BarChart?>
<?import javafx.scene.chart.CategoryAxis?>
<?import javafx.scene.chart.LineChart?>
<?import javafx.scene.chart.NumberAxis?>
<?import javafx.scene.control.Tab?>
<?import javafx.scene.control.TabPane?>
<?import javafx.scene.layout.AnchorPane?>

<AnchorPane minHeight="400.0" minWidth="600.0" prefHeight="400.0" prefWidth="600.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <TabPane fx:id="tabPaneGraphs" layoutX="10.0" layoutY="10.0" prefHeight="400.0" prefWidth="600.0" tabClosingPolicy="UNAVAILABLE" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
         <tabs>
            <Tab closable="false" text="Line Chart">
               <content>
                  <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="180.0" prefWidth="200.0">
                     <children>
                        <LineChart fx:id="lineChart" maxHeight="1.5" maxWidth="600.0" minHeight="0.0" minWidth="600" prefHeight="361.0" prefWidth="600.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                           <xAxis>
                              <CategoryAxis minHeight="400.0" minWidth="400.0" side="BOTTOM" fx:id="xLineNameAxis" />
                           </xAxis>
                           <yAxis>
                              <NumberAxis fx:id="yLineGradeAxis" side="LEFT" tickLabelFill="#ffffff2e" />
                           </yAxis>
                        </LineChart>
                     </children>
                  </AnchorPane>
               </content>
            </Tab>
            <Tab closable="false" text="Bar Chart">
               <content>
                  <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="180.0" prefWidth="200.0">
                     <children>
                        <BarChart fx:id="barChart" prefHeight="361.0" prefWidth="600.0" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                           <xAxis>
                              <CategoryAxis side="BOTTOM" fx:id="xBarNameAxis" />
                           </xAxis>
                           <yAxis>
                              <NumberAxis fx:id="yBarGradeAxis" side="LEFT" />
                           </yAxis>
                        </BarChart>
                     </children>
                  </AnchorPane>
               </content>
            </Tab>
         </tabs>
      </TabPane>
   </children>
</AnchorPane>
```
