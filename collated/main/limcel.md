# limcel
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts all contacts in alphabetical order by their names from the address book.
 * Command is case-insensitive
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " OR "
            + COMMAND_ALIAS
            + ": Sort all contacts names in alphabetical order. ";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted alphabetically by name.";

    private ArrayList<ReadOnlyPerson> contactList;

    public SortCommand() {
        contactList = new ArrayList<>();
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortByPersonName();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the schedule list */
    ObservableList<Schedule> getScheduleList();
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<Schedule> getScheduleList() {
        return model.getScheduleList(); }
```
###### \java\seedu\address\logic\parser\ScheduleCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ScheduleCommand object
 */
public class ScheduleCommandParser implements Parser<ScheduleCommand> {

    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<DateGroup> scheduleToParse;

    /**t
     * Parses the given {@code String} of arguments in the context of the ScheduleCommand
     * and returns an ScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SCHEDULE);
        Index index;

        try {
            args = args.trim();
            String[] str = args.split(" ");
            // string length should be at least of length three comprising of command, index and scheduled date,time:
            // e.g. Schedule, 1, d/28October2019 3pm
            if (str.length == 0 && str.length < 3) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
            // it is a valid Command
            } else if (str.length >= 3 && isNumeric(str[0])) {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
                com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
                scheduleToParse = parser.parse(argMultimap.getValue(PREFIX_SCHEDULE).get());
                // Natty returns no date or returns with more than one date --> invalid
                if (scheduleToParse.isEmpty() || scheduleToParse.size() > 1) {
                    throw new ParseException("Please enter a more specific date for your student's consultation.");
                }
                Calendar date = Calendar.getInstance();
                date.setTime(scheduleToParse.get(0).getDates().get(0));
                return new ScheduleCommand(index, date);
            }
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        throw new ParseException("Please enter the correct prefix");
    }

    /**
     * Checks if the String contains numeric
     */
    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    //// schedule-level operations

    public void addSchedule(Schedule s) {
        schedules.add(s);
    }

    public void removeSchedule(Schedule s) throws ScheduleNotFoundException {
        schedules.remove(s);
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public ObservableList<Schedule> getScheduleList() {
        System.out.println(schedules.asObservableList());
        return schedules.asObservableList();
    }
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public ObservableList<ReadOnlyPerson> listOfPersonNameSorted() {
        return persons.asObservableListSortedByName();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Deletes the given {@code tag} associated with any person in the addressbook.
     *
     * @throws PersonNotFoundException if there are no persons with {@code tag} in the addressbook.
     * @throws DuplicatePersonException if there are multiple same {@code tag} on a person.
     */
    void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException, TagNotFoundException;
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Sorts the list in alphabetical order.
     * @throws NullPointerException if {@code contactList} is null.
     */
    ObservableList<ReadOnlyPerson> sortByPersonName();

    /** Adds the given schedule */
    void addSchedule(Schedule schedule) throws PersonNotFoundException;

    /** Adds the given schedule */
    void removeSchedule(Schedule schedule) throws ScheduleNotFoundException;

    /** Returns an unmodifiable view of the schedules list */
    ObservableList<Schedule> getScheduleList();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException, TagNotFoundException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);
            Person newPerson = new Person(oldPerson);
            ObjectProperty<UniqueTagList> oldTagList = oldPerson.tagProperty();
            Set<Tag> newTags = oldTagList.get().toSet();
            newTags.remove(tag);
            newPerson.setTags(newTags);
            addressBook.updatePerson(oldPerson, newPerson);
        }
        addressBook.removeTag(tag);
    }

    @Override
    public ObservableList<ReadOnlyPerson> sortByPersonName() throws NullPointerException {
        return addressBook.listOfPersonNameSorted();
    }

    /**
     * Adds a schedule for a student's consultation
     */
    @Override
    public void addSchedule(Schedule schedule) {
        addressBook.addSchedule(schedule);
        indicateAddressBookChanged();
    }

    @Override
    public void removeSchedule(Schedule schedule) throws ScheduleNotFoundException {
        addressBook.removeSchedule(schedule);
        indicateAddressBookChanged();
    }

    /**
     * @return a unmodifiable view of the schedule list
     */
    @Override
    public ObservableList<Schedule> getScheduleList() {
        ObservableList<Schedule> list = addressBook.getScheduleList();
        return FXCollections.unmodifiableObservableList(list);
    }
```
###### \java\seedu\address\model\person\PostalCode.java
``` java
/**
 * Represents a Person's postal code in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {
    public static final String MESSAGE_POSTALCODE_CONSTRAINTS =
            "Postal code can only be 6 digits";
    public static final String POSTAL_CODE_VALIDATION_REGEX = "(\\d{6})|(\\(Postal code not recorded\\))";

    public final String value;

    /**
     * Validates given postal code.
     * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTALCODE_CONSTRAINTS);
        }
        this.value = trimmedPostalCode;
    }

    /**
     * Returns true if a given string is a valid person postal code.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTAL_CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
     public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && this.value.equals(((PostalCode) other).value)); // state check
    }

    @Override
     public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the schedules list.
     * This list will not contain any duplicate schedules.
     */
    ObservableList<Schedule> getScheduleList();
```
###### \java\seedu\address\model\schedule\exceptions\ScheduleNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified schedule.
 */
public class ScheduleNotFoundException extends Exception {}
```
###### \java\seedu\address\model\schedule\Schedule.java
``` java
/**
 * Represents the user's schedule in the address book.
 */
public class Schedule {

    private String personName;
    private Date date;

    public Schedule(String name, Calendar calendar) {
        requireNonNull(name);
        requireNonNull(calendar);
        Date date = calendar.getTime();
        this.personName = name;
        this.date = date;
    }

    public Schedule(String name) {
        this.personName = name;
    }

    public String getPersonName() {
        return this.personName;
    }

    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        if (date == null) {
            return "Schedule not fixed with " + getPersonName();
        } else {
            return "Schedule is fixed with " + getPersonName() + " on " + date;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && this.personName.equals(((Schedule) other).personName)
                && this.date.equals(((Schedule) other).date));
    }

    @Override
    public int hashCode() {
        return Objects.hash(personName, date);
    }
}
```
###### \java\seedu\address\model\schedule\UniqueScheduleList.java
``` java
/**
 * A list of schedules that enforces no nulls between its elements.
 *
 * @see Schedule#equals(Object)
 */
public class UniqueScheduleList implements Iterable<Schedule> {

    private final ObservableList<Schedule> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty ScheduleList.
     */
    public UniqueScheduleList() {}

    /**
     * Creates a ScheduleList using given selected persons.
     * Enforces no nulls.
     */
    public UniqueScheduleList(Set<Schedule> schedules) {
        requireAllNonNull(schedules);
        internalList.addAll(schedules);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all schedules in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Schedule> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the schedules in this list with those in the argument schedules list.
     */
    public void setSchedule(Set<Schedule> schedules) {
        requireAllNonNull(schedules);
        internalList.setAll(schedules);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Schedule as the given argument.
     */
    public boolean contains(Schedule toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a schedule to the list.
     */
    public void add(Schedule toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Removes a Schedule from the list.
     *
     * @throws ScheduleNotFoundException if the Schedule to remove cannot be found as an existing Schedule in the list.
     */
    public void remove(Schedule toRemove) throws ScheduleNotFoundException {
        requireNonNull(toRemove);
        if (!contains(toRemove)) {
            throw new ScheduleNotFoundException();
        }
        internalList.remove(toRemove);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    @Override
    public Iterator<Schedule> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Schedule> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueScheduleList // instanceof handles nulls
                && this.internalList.equals(((UniqueScheduleList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedSchedule.java
``` java
/**
 * JAXB-friendly adapted version of the Schedule.
 */
public class XmlAdaptedSchedule {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String dateString;

    /**
     * Constructs an XmlAdaptedSchedule.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedSchedule() {}


    /**
     * Converts a given Schedule into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedSchedule(Schedule source) {
        name = source.getPersonName().toString();
        dateString = DATE_FORMAT.format(source.getDate());
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Schedule toModelType() throws IllegalValueException {
        final String name = this.name;
        final Schedule schedule;
        Calendar calendar = null;
        calendar = Calendar.getInstance();
        if (calendar != null) {
            try {
                calendar.setTime(DATE_FORMAT.parse(dateString));
                return new Schedule(name.toString(), calendar);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        schedule = new Schedule(name.toString(), calendar);
        return new Schedule(name.toString(), calendar);
    }
}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Schedule> getScheduleList() {
        final ObservableList<Schedule> schedules = this.schedules.stream().map(s -> {
            try {
                return s.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(schedules);
    }
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java

}
```
###### \java\seedu\address\ui\ExtendedPersonCard.java
``` java
/**
 * Extended Person Card Panel that displays the details of a Person
 */
public class ExtendedPersonCard extends UiPart<Region> {

    private static final String FXML = "ExtendedPersonCard.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<ReadOnlyPerson> people;

    @FXML
    private HBox cardpane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label formClass;
    @FXML
    private Label grades;
    @FXML
    private Label postalCode;
    @FXML
    private Label email;
    @FXML
    private Label remark;

    public ExtendedPersonCard() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    /**
     * Updates person details as displayed on the extended person card panel
     */
    protected void loadPersonDetails(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        address.setText(person.getAddress().toString());
        formClass.setText(person.getFormClass().toString());
        grades.setText(person.getGrades().toString());
        postalCode.setText(person.getPostalCode().toString());
        email.setText(person.getEmail().toString());
        remark.setText(person.getRemark().toString());
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }

}
```
###### \resources\view\ExtendedPersonCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<HBox id="stackPane" fx:id="stackPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
        <VBox alignment="TOP_LEFT" minHeight="105" spacing="15.0" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="5" right="15" top="5" />
            </padding>
            <HBox alignment="TOP_LEFT" spacing="10">
            <ImageView>
               <image>
                  <Image url="@../images/student(32px).png" />
               </image>
            </ImageView>
                <Label fx:id="name" styleClass="cell_big_label" text="\$first">
               <font>
                  <Font name="System Bold" size="18.0" />
               </font></Label>
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
            </HBox>
            <Label fx:id="phone" styleClass="cell_small_label" text="\$phone">
            <graphic>
               <ImageView>
                  <image>
                     <Image url="@../images/phone.png" />
                  </image>
               </ImageView>
            </graphic>
            <VBox.margin>
               <Insets />
            </VBox.margin></Label>
            <Label fx:id="address" styleClass="cell_small_label" text="\$address">
            <graphic>
               <ImageView>
                  <image>
                     <Image url="@../images/address.png" />
                  </image>
               </ImageView>
            </graphic></Label>
            <Label fx:id="formClass" styleClass="cell_small_label" text="\$formClass">
            <graphic>
               <ImageView>
                  <image>
                     <Image url="@../images/class.png" />
                  </image>
               </ImageView>
            </graphic></Label>
            <Label fx:id="grades" styleClass="cell_small_label" text="\$grades">
            <graphic>
               <ImageView>
                  <image>
                     <Image url="@../images/grades.png" />
                  </image>
               </ImageView>
            </graphic></Label>
            <Label fx:id="postalCode" styleClass="cell_small_label" text="\$postalCode">
                <graphic>
                    <ImageView>
                        <image>
                            <Image url="@../images/postalCode.png" />
                        </image>
                    </ImageView>
                </graphic></Label>
            <Label fx:id="email" styleClass="cell_small_label" text="\$email">
            <graphic>
               <ImageView>
                  <image>
                     <Image url="@../images/email.png" />
                  </image>
               </ImageView>
            </graphic></Label>
            <Label fx:id="remark" styleClass="cell_small_label" text="\$remark">
            <graphic>
               <ImageView>
                  <image>
                     <Image url="@../images/remark.png" />
                  </image>
               </ImageView>
            </graphic></Label>
         <GridPane.margin>
            <Insets />
         </GridPane.margin>
        </VBox>
        <rowConstraints>
            <RowConstraints />
        </rowConstraints>
    </GridPane>
</HBox>
```
