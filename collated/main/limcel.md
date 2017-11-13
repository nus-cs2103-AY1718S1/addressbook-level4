# limcel
###### /java/seedu/address/commons/events/model/ScheduleListChangedEvent.java
``` java
/**
 * Represents a change in the current schedule list
 */
public class ScheduleListChangedEvent extends BaseEvent {

    private final ObservableList<Schedule> currentList;

    public ScheduleListChangedEvent(ObservableList<Schedule> currentList) {
        this.currentList = currentList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### /java/seedu/address/commons/events/ui/JumpToTabRequestEvent.java
``` java
public class JumpToTabRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToTabRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### /java/seedu/address/logic/commands/DeleteScheduleCommand.java
``` java
/**
 * Deletes a schedule identified from the address book's schedule list.
 */
public class DeleteScheduleCommand extends Command {

    public static final String COMMAND_WORD = "deleteschedule";
    public static final String COMMAND_ALIAS = "deletesch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the schedule identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SCHEDULE_SUCCESS = "Deleted Schedule: %1$s";

    private final Index targetIndex;

    public DeleteScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        Model modelManager = new ModelManager();
        ObservableList<Schedule> lastShownList = model.getScheduleList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SCHEDULE_DISPLAYED_INDEX);
        }

        Schedule scheduleToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.removeSchedule(scheduleToDelete);
        } catch (ScheduleNotFoundException e) {
            assert false : "The target schedule cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_SCHEDULE_SUCCESS, scheduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteScheduleCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteScheduleCommand) other).targetIndex)); // state check
    }

}
```
###### /java/seedu/address/logic/commands/ScheduleCommand.java
``` java
/**
 * Schedules a consultation timeslot with the person identified using it's last displayed index from the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "addschedule";
    public static final String COMMAND_ALIAS = "addsch";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Schedules the selected indexed person to a consultation timeslot.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_SCHEDULE + "tomorrow 6.30pm";

    public static final String MESSAGE_SCHEDULE_PERSON_SUCCESS = "Scheduled Person: %1$s";
    public static final String PERSON_NOT_FOUND = "This person cannot be found";

    private final Index targetIndex;
    private final Calendar date;

    public ScheduleCommand(Index targetIndex, Calendar date) {
        this.targetIndex = targetIndex;
        this.date = date;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(targetIndex);
        requireNonNull(date);

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (date != null && !isDateValid()) {
            return new CommandResult("You have entered an invalid date.");
        }

        ReadOnlyPerson personAddedToSchedule = lastShownList.get(targetIndex.getZeroBased());
        Schedule schedule = new Schedule(personAddedToSchedule.getName().toString(), date);
        try {
            model.addSchedule(schedule);
        } catch (PersonNotFoundException e) {
            return new CommandResult(PERSON_NOT_FOUND);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult("Added " + personAddedToSchedule.getName().toString()
                + " to consultations schedule " + "on " + schedule.getDate().toString() + ".\n"
                + "Use 'viewsch' or 'viewschedule' command to view all your schedules.");

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && this.date.getTimeInMillis() == ((ScheduleCommand) other).date.getTimeInMillis())
                && this.targetIndex.getZeroBased() == ((ScheduleCommand) other).targetIndex.getZeroBased();
    }

    /**
     * Returns true if appointment date set to after current time
     */
    private boolean isDateValid() {
        requireNonNull(date);
        Calendar calendar = Calendar.getInstance();
        return !date.getTime().before(calendar.getTime());
    }
}
```
###### /java/seedu/address/logic/commands/SortCommand.java
``` java
/**
 * Sorts all contacts in alphabetical order by their names from the address book.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " OR "
            + COMMAND_ALIAS
            + ": Sort all contacts names in alphabetical order. ";

    public static final String MESSAGE_SUCCESS = "All contacts are sorted alphabetically by name.";

    public SortCommand() {
    }

    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortByPersonName();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/commands/TabCommand.java
``` java
public class TabCommand extends Command {
    public static final String COMMAND_WORD = "tab";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " : Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_SELECT_TAB_SUCCESS = "Selected Tab: %1$s";
    public static final String MESSAGE_INVALID_TAB_INDEX = "Invalid Tab Value";

    // there are only 2 types of graphs available for display (Graph / Bar)
    public static final int TAB_NUMBER = 2;

    private final Index targetIndex;

    /**
     * Selects @param targetIndex of the TabPane in the graph panel for switching
     */
    public TabCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        if (targetIndex.getZeroBased() >= TAB_NUMBER) {
            throw new CommandException(MESSAGE_INVALID_TAB_INDEX);
        }
        EventsCenter.getInstance().post(new JumpToTabRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_TAB_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TabCommand // instanceof handles nulls
                && this.targetIndex.equals(((TabCommand) other).targetIndex)); // state check
    }
}
```
###### /java/seedu/address/logic/commands/ViewScheduleCommand.java
``` java
/**
 * Lists all persons in the address book to the user.
 */
public class ViewScheduleCommand extends Command {

    public static final String COMMAND_WORD = "viewschedules";
    public static final String COMMAND_ALIAS = "viewsch";

    public static final String MESSAGE_SUCCESS = "Listed your schedule. \n";


    @Override
    public CommandResult execute() {
        model.getScheduleList();

        return new CommandResult(MESSAGE_SUCCESS
                + changeToAppropriateUiFormat((model.getAddressBook().getScheduleList()).toString()));
    }

    //====================================== HELPER METHODS =====================================
    /**
     * Converts the schedule list output by replacing all occurrence of "," with ": " for better UI visualisation.
     */
    public static String changeToAppropriateUiFormat(String value) {
        value = value.replace(",", "\n");
        return value;
    }
}
```
###### /java/seedu/address/logic/Logic.java
``` java
    /** Returns an unmodifiable view of the schedule list */
    ObservableList<Schedule> getScheduleList();
```
###### /java/seedu/address/logic/LogicManager.java
``` java
    @Override
    public ObservableList<Schedule> getScheduleList() {
        return model.getScheduleList(); }
```
###### /java/seedu/address/logic/parser/ScheduleCommandParser.java
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
            if (str.length == 0 || str.length < 3) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
            // it is a valid Command
            } else if (str.length >= 3 && isNumeric(str[0])) {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
                ScheduleCommand scheduleCommand = onlyReturnsSchedulesWithSpecificDate(index, argMultimap);
                return scheduleCommand;
            }
        } catch (IllegalValueException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ScheduleCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if the string contains numbers, false otherwise
     */
    private static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * Checks if date given by user is specific
     * @return ScheduleCommand if date given is specific
     * @throws ParseException if date given is not specific enough
     */
    private ScheduleCommand onlyReturnsSchedulesWithSpecificDate(Index index, ArgumentMultimap argMultimap)
            throws ParseException {
        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        scheduleToParse = parser.parse(argMultimap.getValue(PREFIX_SCHEDULE).get());
        // Natty returns no date or with more than one date (date entered not specific enough) --> throw exception
        if (scheduleToParse.isEmpty() || scheduleToParse.size() > 1) {
            throw new ParseException("Please enter a more specific date for your student's consultation.");
        }
        Calendar date = Calendar.getInstance();
        date.setTime(scheduleToParse.get(0).getDates().get(0));
        return new ScheduleCommand(index, date);
    }
}
```
###### /java/seedu/address/logic/parser/TabCommandParser.java
``` java
public class TabCommandParser implements Parser<TabCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TabCommand
     * and returns an TabCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TabCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new TabCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));
        }
    }
}
```
###### /java/seedu/address/model/AddressBook.java
``` java
    //// schedule-level operations

    public void addSchedule(Schedule s) {
        schedules.add(s);
    }

    public void removeSchedule(Schedule s) throws ScheduleNotFoundException {
        schedules.remove(s);
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public ObservableList<Schedule> getScheduleList() {
        return schedules.asObservableList();
    }
```
###### /java/seedu/address/model/AddressBook.java
``` java
    public ObservableList<ReadOnlyPerson> listOfPersonNameSorted() {
        return persons.asObservableListSortedByName();
    }

    public ObservableList<Schedule> sortSchedules() {
        return schedules.asObservableListSortedChronologically();
    }
```
###### /java/seedu/address/model/Model.java
``` java
    /**
     * Deletes the given {@code tag} associated with any person in the addressbook.
     *
     * @throws PersonNotFoundException if there are no persons with {@code tag} in the addressbook.
     * @throws DuplicatePersonException if there are multiple same {@code tag} on a person.
     */
    void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException, TagNotFoundException;
```
###### /java/seedu/address/model/Model.java
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
###### /java/seedu/address/model/ModelManager.java
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
        addressBook.sortSchedules();
        indicateAddressBookChanged();
        indicateScheduleListChanged();
    }

    @Override
    public void removeSchedule(Schedule schedule) throws ScheduleNotFoundException {
        addressBook.removeSchedule(schedule);
        indicateAddressBookChanged();
        indicateScheduleListChanged();
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
###### /java/seedu/address/model/person/PostalCode.java
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
###### /java/seedu/address/model/person/UniquePersonList.java
``` java
    /**
     * Returns an observable list as as an unmodifiable {@code ObservableList}
     */
    public ObservableList<ReadOnlyPerson> asObservableListSortedByName() {
        internalList.sort((o1, o2) ->((o1.getName().toString().toLowerCase())
                .compareTo(o2.getName().toString().toLowerCase())));
        return FXCollections.unmodifiableObservableList(mappedList);
    }
```
###### /java/seedu/address/model/ReadOnlyAddressBook.java
``` java
    /**
     * Returns an unmodifiable view of the schedules list.
     * This list will not contain any duplicate schedules.
     */
    ObservableList<Schedule> getScheduleList();
```
###### /java/seedu/address/model/schedule/exceptions/ScheduleNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified schedule.
 */
public class ScheduleNotFoundException extends Exception {}
```
###### /java/seedu/address/model/schedule/Schedule.java
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
###### /java/seedu/address/model/schedule/UniqueScheduleList.java
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
    /**
     * Returns an observable list as as an unmodifiable {@code ObservableList}
     */
    public ObservableList<Schedule> asObservableListSortedChronologically() {
        internalList.sort((date1, date2) -> (date1.getDate().compareTo(date2.getDate())));
        return FXCollections.unmodifiableObservableList(internalList);
    }
```
###### /java/seedu/address/storage/XmlAdaptedSchedule.java
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
     * @param source future changes to this will not affect the created XmlAdaptedSchedule
     */
    public XmlAdaptedSchedule(Schedule source) {
        name = source.getPersonName();
        dateString = DATE_FORMAT.format(source.getDate());
    }

    /**
     * Converts this jaxb-friendly adapted schedule object into the model's Schedule object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted schedule
     */
    public Schedule toModelType() throws IllegalValueException {
        final String name = this.name;
        final Schedule schedule;
        Calendar calendar;
        calendar = Calendar.getInstance();
        if (calendar != null) {
            try {
                calendar.setTime(DATE_FORMAT.parse(dateString));
                return new Schedule(name, calendar);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }
        schedule = new Schedule(name, calendar);
        return new Schedule(name, calendar);
    }
}
```
###### /java/seedu/address/storage/XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<Schedule> getScheduleList() {
        final ObservableList<Schedule> schedules = this.schedules.stream().map(s -> {
            try {
                return s.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(schedules);
    }
```
###### /java/seedu/address/ui/ExtendedPersonCard.java
``` java
/**
 * An UI component that displays all information of a {@code Person}.
 */
public class ExtendedPersonCard extends UiPart<Region> {

    private static final String FXML = "ExtendedPersonCard.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private ObservableList<ReadOnlyPerson> people;

    @FXML
    private GridPane gridPane;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label parentPhone;
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
        loadIconDescriptionOnStartUp();
    }

    /**
     * Updates person details as displayed on the extended person card panel
     */
    protected void loadPersonDetails(ReadOnlyPerson person) {
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().toString());
        parentPhone.setText(person.getParentPhone().toString());
        address.setText(person.getAddress().toString());
        formClass.setText(person.getFormClass().toString());
        grades.setText(person.getGrades().toString());
        postalCode.setText(person.getPostalCode().toString());
        email.setText(person.getEmail().toString());
        remark.setText(person.getRemark().toString());
    }

```
###### /java/seedu/address/ui/ExtendedPersonCard.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonDetails(event.getNewSelection().person);
    }

}
```
###### /resources/view/ExtendedPersonCard.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.image.Image?>
<?import javafx.scene.image.ImageView?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>
<VBox prefHeight="315.0" prefWidth="335.0" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <children>
        <HBox prefHeight="57.0" prefWidth="335.0">
            <children>
                <ImageView>
                    <image>
                        <Image url="@../images/student.png"/>
                    </image>
                </ImageView>
                <Label fx:id="name" lineSpacing="10" styleClass="cell_big_label" text="\$name" underline="true">
                    <font>
                        <Font name="System Bold" size="18.0"/>
                    </font>
                    <padding>
                        <Insets left="5.0"/>
                    </padding>
                    <HBox.margin>
                        <Insets/>
                    </HBox.margin>
                </Label>
            </children>
        </HBox>
        <GridPane fx:id="stackPane">
            <children>
                <Label fx:id="phone" lineSpacing="10.0" styleClass="cell_small_label" text="\$phone"
                       GridPane.columnIndex="2" GridPane.rowIndex="2">
                    <padding>
                        <Insets bottom="5.0" top="7.0"/>
                    </padding>
                    <font>
                        <Font size="14.0"/>
                    </font>
                </Label>
                <Label fx:id="parentPhone" lineSpacing="10.0" styleClass="cell_small_label" text="\$parentPhone"
                       GridPane.columnIndex="2" GridPane.rowIndex="1">
                    <padding>
                        <Insets bottom="5.0"/>
                    </padding>
                    <font>
                        <Font size="14.0"/>
                    </font>
                    <GridPane.margin>
                        <Insets top="7.0"/>
                    </GridPane.margin>
                </Label>
                <Label fx:id="address" lineSpacing="10.0" styleClass="cell_small_label" text="\$address"
                       GridPane.columnIndex="2" GridPane.rowIndex="3">
                    <padding>
                        <Insets bottom="5.0" top="7.0"/>
                    </padding>
                    <font>
                        <Font size="14.0"/>
                    </font>
                </Label>
                <Label fx:id="formClass" lineSpacing="10.0" styleClass="cell_small_label" text="\$formClass"
                       GridPane.columnIndex="2" GridPane.rowIndex="4">
                    <padding>
                        <Insets bottom="5.0" top="7.0"/>
                    </padding>
                    <font>
                        <Font size="14.0"/>
                    </font>
                </Label>
                <Label fx:id="grades" lineSpacing="10.0" styleClass="cell_small_label" text="\$grades"
                       GridPane.columnIndex="2" GridPane.rowIndex="5">
                    <padding>
                        <Insets bottom="5.0" top="5.0"/>
                    </padding>
                    <font>
                        <Font size="14.0"/>
                    </font>
                </Label>
                <Label fx:id="postalCode" lineSpacing="10.0" styleClass="cell_small_label" text="\$postalCode"
                       GridPane.columnIndex="2" GridPane.rowIndex="6">
                    <padding>
                        <Insets bottom="5.0" top="5.0"/>
                    </padding>
                    <font>
                        <Font size="14.0"/>
                    </font>
                </Label>
                <Label fx:id="email" lineSpacing="10.0" styleClass="cell_small_label" text="\$email"
                       GridPane.columnIndex="2" GridPane.rowIndex="7">
                    <padding>
                        <Insets bottom="5.0" top="3.0"/>
                    </padding>
                    <font>
                        <Font size="14.0"/>
                    </font>
                </Label>
                <Label fx:id="remark" lineSpacing="10.0" styleClass="cell_small_label" text="\$remark"
                       GridPane.columnIndex="2" GridPane.rowIndex="8">
                    <font>
                        <Font size="14.0"/>
                    </font>
                </Label>
                <Label text="Parent:" GridPane.columnIndex="1" GridPane.rowIndex="1"/>
                <Label text="Student:" GridPane.columnIndex="1" GridPane.rowIndex="2"/>
                <Label text="Address:" GridPane.columnIndex="1" GridPane.rowIndex="3"/>
                <ImageView GridPane.rowIndex="3">
                    <image>
                        <Image url="@../images/address.png"/>
                    </image>
                </ImageView>
                <ImageView GridPane.rowIndex="2">
                    <image>
                        <Image url="@../images/studentPhone.png"/>
                    </image>
                </ImageView>
                <ImageView GridPane.rowIndex="1">
                    <image>
                        <Image url="@../images/parentPhone.png"/>
                    </image>
                </ImageView>
                <ImageView GridPane.rowIndex="4">
                    <image>
                        <Image url="@../images/formClass.png"/>
                    </image>
                </ImageView>
                <ImageView GridPane.rowIndex="5">
                    <image>
                        <Image url="@../images/grades.png"/>
                    </image>
                </ImageView>
                <ImageView GridPane.rowIndex="6">
                    <image>
                        <Image url="@../images/postalCode.png"/>
                    </image>
                </ImageView>
                <ImageView GridPane.rowIndex="7">
                    <image>
                        <Image url="@../images/email.png"/>
                    </image>
                </ImageView>
                <ImageView GridPane.rowIndex="8">
                    <image>
                        <Image url="@../images/remark.png"/>
                    </image>
                </ImageView>
                <Label text="FormClass:" GridPane.columnIndex="1" GridPane.rowIndex="4"/>
                <Label text="Grades:" GridPane.columnIndex="1" GridPane.rowIndex="5"/>
                <Label text="PostalCode:" GridPane.columnIndex="1" GridPane.rowIndex="6"/>
                <Label text="Email:" GridPane.columnIndex="1" GridPane.rowIndex="7"/>
                <Label text="Remarks:" GridPane.columnIndex="1" GridPane.rowIndex="8"/>
            </children>
            <columnConstraints>
                <ColumnConstraints maxWidth="25.0" minWidth="25.0" prefWidth="25.0"/>
                <ColumnConstraints maxWidth="100.0" minWidth="100.0" prefWidth="95.0"/>
                <ColumnConstraints maxWidth="295.3333435058594" minWidth="164.0" prefWidth="216.0"/>
            </columnConstraints>
            <rowConstraints>
                <RowConstraints/>
                <RowConstraints minHeight="10.0" prefHeight="30.0"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0"/>
                <RowConstraints minHeight="10.0" prefHeight="30.0"/>
            </rowConstraints>
        </GridPane>
    </children>
</VBox>
```
