# Eric
###### \java\seedu\address\commons\events\ui\CalendarViewEvent.java
``` java
/**
 * Indicates a request to change calendar view
 */
public class CalendarViewEvent extends BaseEvent {

    public final Character c;

    public CalendarViewEvent(Character c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\logic\commands\AddAppointmentCommand.java
``` java
/**
 * Command to add appointment to a person in addressBook
 */
public class AddAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "appointment";
    public static final String COMMAND_ALIAS = "appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to a person in address book. \n"
            + COMMAND_ALIAS + ": Shorthand equivalent for add. \n"
            + "Parameters: " + PREFIX_NAME + "PERSON "
            + PREFIX_DATE + "DESCRIPTION, TIME" + "\n"
            + "Example 1:" + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "Lunch, Next Monday 3pm";

    public static final String MESSAGE_SUCCESS = "New appointment added. ";
    public static final String INVALID_PERSON = "This person is not in your address book";
    public static final String INVALID_DATE = "Invalid Date. Please enter a valid date.";
    public static final String SORT_APPOINTMENT_FEEDBACK = "Rearranged contacts to show upcoming appointments.";


    private final Index index;
    private final Appointment appointment;

    public AddAppointmentCommand(Index index, Appointment appointment) {
        this.index = index;
        this.appointment = appointment;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        requireNonNull(index);
        requireNonNull(appointment);

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddAppointment = lastShownList.get(index.getZeroBased());

        if (appointment.getDate() != null && !isDateValid()) {
            return new CommandResult(INVALID_DATE);
        }

        try {
            model.addAppointment(personToAddAppointment, appointment);
        } catch (PersonNotFoundException e) {
            return new CommandResult(INVALID_PERSON);
        }

        return new CommandResult(MESSAGE_SUCCESS);

    }

    /**
     * Checks if appointment date set to after current time
     */
    private boolean isDateValid() {
        requireNonNull(appointment);
        Calendar calendar = Calendar.getInstance();
        return !appointment.getDate().before(calendar.getTime());
    }

    public Index getIndex() {
        return this.index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && (this.index.getZeroBased() == ((AddAppointmentCommand) other).index.getZeroBased())
                && (this.appointment.equals(((AddAppointmentCommand) other).appointment)));
    }

    /**
     * For testing purposes
     *
     */
    public void setData(Model model) {
        this.model = model;
    }

}
```
###### \java\seedu\address\logic\commands\CalendarViewCommand.java
``` java
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.CalendarViewEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Command to change calendar view
 */
public class CalendarViewCommand extends Command {

    public static final String COMMAND_WORD = "calendar";
    public static final String COMMAND_ALIAS = "cal";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes calendar view. \n"
            + COMMAND_ALIAS + ": Short hand equivalent for calendar. \n"
            + "Parameter: \n"
            + "Day view: d\n"
            + "Week view: w\n"
            + "Month view: m\n"
            + "Year view: y\n";

    public static final String MESSAGE_SUCCESS = "View changed.";

    private Character arg;

    public CalendarViewCommand(Character c) {
        this.arg = c;
    }
    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new CalendarViewEvent(arg));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\CancelAppointmentCommand.java
``` java
/**
 * Command to cancel an existing appointment
 */
public class CancelAppointmentCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cancel";
    public static final String NO_SUCH_PERSON_FOUND = "No such person found";
    public static final String NO_SUCH_APPOINTMENT = "No such appointment found";
    public static final String MESSAGE_SUCCESS = "Appointment canceled.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels an appointment from a person. \n"
            + "Parameters: " + "DESCRIPTION with PERSON NAME \n"
            + "Example 1:" + COMMAND_WORD + " "
            + "Lunch with John Doe";
    public static final String REFER_PROMPT = "Please refer to the appointment description.";
    private String personString;
    private String appointmentString;

    public CancelAppointmentCommand(String person, String appointment) {
        this.personString = person;
        this.appointmentString = appointment;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        try {
            ReadOnlyPerson person = getPersonFromName(personString);
            Appointment appointment = getAppointmentFromPerson(person, appointmentString);
            model.removeAppointment(person, appointment);
        } catch (PersonNotFoundException e) {
            throw new CommandException(NO_SUCH_PERSON_FOUND + "\n" + REFER_PROMPT);
        } catch (AppointmentNotFoundException e) {
            throw new CommandException(NO_SUCH_APPOINTMENT + "\n" + REFER_PROMPT);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Util method to search for the correct appointment from a person using only the description.
     * May have multiple appointments if there is same description under one person, but the first one will be deleted
     * @throws AppointmentNotFoundException if appointment not found
     */
    private Appointment getAppointmentFromPerson(ReadOnlyPerson person, String description)
            throws AppointmentNotFoundException {

        for (Appointment appointment : person.getAppointments()) {
            if (appointment.getDescription().equalsIgnoreCase(description.trim())) {
                return appointment;
            }
        }
        //Show Daily page for calendar
        EventsCenter.getInstance().post(new CalendarViewEvent('d'));
        throw new AppointmentNotFoundException();
    }

    /**
     * Extract person from address book using name. If there are more than one contact with the same name,
     * the first one will be extracted
     * @throws PersonNotFoundException if no such person is in the address book
     */
    private ReadOnlyPerson getPersonFromName(String personName) throws PersonNotFoundException {

        for (ReadOnlyPerson person : model.getAddressBook().getPersonList()) {
            if (person.getName().toString().equalsIgnoreCase(personName.trim())) {
                return person;
            }
        }

        throw new PersonNotFoundException();
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelAppointmentCommand // instanceof handles nulls
                && (this.appointmentString.equals(((CancelAppointmentCommand) other).appointmentString))
                && (this.personString.equals(((CancelAppointmentCommand) other).personString)));
    }
}
```
###### \java\seedu\address\logic\commands\ToggleTagColorCommand.java
``` java
/**
 *  Changes tag color in address book
 */
public class ToggleTagColorCommand extends Command {

    public static final String COMMAND_WORD = "tagcolor";
    public static final String COMMAND_ALIAS = "tc";
    public static final String MESSAGE_SUCCESS = "tag color set to ";
    public static final String NO_SUCH_TAG_MESSAGE = "No such tag";

    private final Logger logger = LogsCenter.getLogger(ToggleTagColorCommand.class);

    private String tag;
    private String color;
    private String message;

    public ToggleTagColorCommand(String tag, String color) {
        this.tag = tag;
        this.color = color;
    }

    /**
     * Sets all tag color on/off, OR sets a specific tag string to a specific color
     * @throws CommandException if tagcolor command has the wrong number of parameters
     *
     */
    @Override
    public CommandResult execute() throws CommandException {

        model.setTagColor(tag, color);
        model.resetData(model.getAddressBook());

        if (color == null) {
            message = String.format("%s%s", MESSAGE_SUCCESS, "random".equals(tag) ? "random" : "off");
        } else if (!(containsTag(model.getAddressBook().getTagList(), tag))) {
            message = NO_SUCH_TAG_MESSAGE;
        } else {
            message = tag + " " + MESSAGE_SUCCESS + color;
        }
        return new CommandResult(message);
    }

    /**
     * Check if
     * (a) Object is the same object
     * (b) Object is an instance of the object and that toSet, tag and color are the same
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ToggleTagColorCommand // instanceof handles nulls
                && this.tag.equals(((ToggleTagColorCommand) other).tag)); // state check
    }

    /**
     * Helper method to check if the tagList contains such a tag name
     * @param tagList - ObservableList type with elements of Tag type
     * @param tagString - Tag to check if it is within the list of tags (tagList)
     */
    private boolean containsTag(ObservableList<Tag> tagList, String tagString) {

        for (Tag tag : tagList) {
            if (tag.tagName.equals(tagString)) {
                return true;
            }
        }
        return false;
    }
}
```
###### \java\seedu\address\logic\parser\AddAppointmentParser.java
``` java
/**
 * Parse input arguments and creates a new AddAppointmentCommand Object
 */
public class AddAppointmentParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddAppointmentCommand parse(String userInput) throws ParseException {

        String[] args = userInput.split(" ");

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DATE);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        try {
            Index index = Index.fromOneBased(Integer.parseInt(args[1]));
            Appointment appointment = getAppointmentFromString(argumentMultimap.getValue(PREFIX_DATE).get());
            return new AddAppointmentCommand(index, appointment);
        } catch (NumberFormatException e) {
            throw new ParseException("Please input an index for appointment.\n"
                    + String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Natty parser that takes in a string and returns an appointment
     */
    public static Appointment getAppointmentFromString(String str) throws ParseException {
        String[] args = str.split(",");

        if (args.length != 2) {
            throw new ParseException("Please follow format for adding appointment.\n"
                    + AddAppointmentCommand.MESSAGE_USAGE);
        }

        String description = args[0];

        com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
        List<DateGroup> groups = parser.parse(args[1]);
        Calendar calendar = Calendar.getInstance();
        if (groups.size() == 0) {
            throw new ParseException("Please be more specific with your appointment time");
        }

        //If there is a start and end time that is parsed
        if (groups.get(0).getDates().size() == 2) {
            calendar.setTime(groups.get(0).getDates().get(0));
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(groups.get(0).getDates().get(1));
            return new Appointment(description, calendar, calendarEnd);
        }

        //Only one date parsed
        calendar.setTime(groups.get(0).getDates().get(0));
        return new Appointment(description, calendar, null);
    }

}
```
###### \java\seedu\address\logic\parser\CalendarViewParser.java
``` java

/**
 * Parser for CalendarViewCommand
 */
public class CalendarViewParser implements Parser {


    @Override
    public Command parse(String userInput) throws ParseException {
        userInput = userInput.trim();
        if (userInput.length() != 1 || !isValid(userInput)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CalendarViewCommand.MESSAGE_USAGE));
        }
        return new CalendarViewCommand(userInput.charAt(0));
    }

    /**
     * Util method to check if the parameters is either w,d,y or m.
     */
    private boolean isValid(String str) {

        assert(str.length() == 1);
        switch (str.charAt(0)) {
        case('w'):
        case('d'):
        case('y'):
        case('m'):
            return true;
        default:
            return false;
        }
    }
}
```
###### \java\seedu\address\logic\parser\ToggleTagColorParser.java
``` java
/**
 * Parse input for tagcolor command
 */
public class ToggleTagColorParser implements Parser<ToggleTagColorCommand> {

    private static final String RANDOM_KEY_WORD = "random";
    private static final String OFF_KEY_WORD = "off";

    private static final String MESSAGE_INVALID_COMMAND = "Invalid tagcolor command."
            + "\n"
            + "tc: Shorthand equivalent for tagcolor."
            + "\n"
            + "tagcolor: TagColor sets color for AddressBook"
            + "\n"
            + "Parameters: tagcolor random/off || tagcolor TAGNAME COLOR";

    @Override
    public ToggleTagColorCommand parse(String userInput) throws ParseException {

        String cleanUserInput;
        cleanUserInput = userInput.trim();
        String[] args = cleanUserInput.split("\\s+");
        if (args.length == 1) {
            switch (args[0]) {
            case RANDOM_KEY_WORD:
                return new ToggleTagColorCommand(RANDOM_KEY_WORD, null);
            case OFF_KEY_WORD:
                return new ToggleTagColorCommand(OFF_KEY_WORD, null);
            default:
                throw new ParseException(MESSAGE_INVALID_COMMAND);
            }
        } else if (args.length == 2) {
            return new ToggleTagColorCommand(args[0], args[1]);
        }

        throw new ParseException(MESSAGE_INVALID_COMMAND);
    }

    /**
     * Returns the Random Key Word
     */
    public String getRandomKeyWord() {
        return RANDOM_KEY_WORD;
    }

    /**
     * Returns the Off Key Word
     */
    public String getOffKeyWord() {
        return OFF_KEY_WORD;
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void setTags(Set<Tag> tags, String tagString, String color) {
        this.tags.setTags(tags, tagString, color);
    }


    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }


    public void addAppointment(ReadOnlyPerson target, Appointment appointment) throws PersonNotFoundException {
        persons.addAppointment(target, appointment);
    }

    public void removeAppointment(ReadOnlyPerson target, Appointment appointment) throws PersonNotFoundException {
        persons.removeAppointment(target, appointment);
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Sets tag color for a particular tag
     * Updates UI by refreshing personListPanel
     */
    @Override
    public synchronized void setTagColor(String tagString, String color) {
        Set<Tag> tag = new HashSet<>(addressBook.getTagList());
        addressBook.setTags(tag, tagString, color);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Adds appointment for a contact in address book
     */
    @Override
    public void addAppointment(ReadOnlyPerson target, Appointment appointment) throws PersonNotFoundException {
        addressBook.addAppointment(target, appointment);
        indicateAddressBookChanged();
    }


    @Override
    public void removeAppointment(ReadOnlyPerson target, Appointment appointment) throws PersonNotFoundException {
        addressBook.removeAppointment(target, appointment);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\person\Appointment.java
``` java
/**
 *  Appointment class to hold all the start and end time of the appointment and the description
 *  */
public class Appointment {

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private String description;
    private Date date;
    private Date endDate;

    public Appointment(String description, Calendar calendar, Calendar calendarEnd) {
        requireNonNull(calendar);
        requireNonNull(description);
        this.description = description;
        this.date = calendar.getTime();
        if (calendarEnd != null) {
            this.endDate = calendarEnd.getTime();
        } else {
            calendar.add(Calendar.HOUR, 1);
            this.endDate = calendar.getTime();
        }
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDate() {
        return this.date;
    }

    public Date getEndDate() {
        return endDate;
    }
    @Override
    public String toString() {
        if (date != null) {
            return "Appointment on " + DATE_FORMATTER.format(date);
        } else {
            return "No appointment";
        }
    }

    /**
     * @return starting appointment time in the format yyyy/MM/dd HH:mm
     */
    public String getDateInStringFormat() {
        return DATE_FORMATTER.format(date);
    }

    /**
     * @return ending appointment time in the format yyyy/MM/dd HH:mm
     */
    public String getDateEndInStringFormat() {
        return DATE_FORMATTER.format(endDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.getDateInStringFormat().equals(((Appointment) other).getDateInStringFormat()))
                && this.getDateEndInStringFormat().equals(((Appointment) other).getDateEndInStringFormat());
    }
}
```
###### \java\seedu\address\model\person\AppointmentList.java
``` java
/**
 * A list of appointments of a person
 */
public class AppointmentList {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty appointment list
     */
    public AppointmentList() {

    }

    /**
     * Contructs an appointment list with new appointments
     */
    public AppointmentList(List<Appointment> appointments) {
        requireAllNonNull(appointments);
        sortAppointmentsInChronologicalOrder(appointments);
        internalList.addAll(appointments);

    }

    /**
     * @param appointments list must be sorted
     */
    private void requireAllSorted(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            assert !appointments.get(i + 1).getDate().before(appointments.get(i).getDate());
        }
    }

    /**
     * Sorts all the appointments in the list before adding it to the internal list
     */
    private void sortAppointmentsInChronologicalOrder(List<Appointment> appointment) {
        requireNonNull(appointment);

        appointment.sort((o1, o2) -> {
            if (o1.getDate().toInstant().isBefore(o2.getDate().toInstant())) {
                return -1;
            } else {
                return 1;
            }
        });

        requireAllSorted(appointment);
    }

    /**
     * Returns true if list contains and equivalent appointment
     */
    public boolean contains (Appointment appointment) {
        return internalList.contains(appointment);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentList // instanceof handles nulls
                && this.internalList.equals(((AppointmentList) other).internalList));
    }

    /**
     * Returns all appointments in this list as a list.
     * This List is mutable and change-insulated against the internal list.
     */
    public List<Appointment> toList() {
        return new ArrayList<>(internalList);
    }

    @Override
    public String toString() {
        if (internalList.isEmpty()) {
            return "No appointment set";
        } else {
            return internalList.size() == 1 ? "" + internalList.size() + " appointment set"
                    : "" + internalList.size() + " appointments set";
        }
    }

}
```
###### \java\seedu\address\model\person\exceptions\AppointmentNotFoundException.java
``` java
/**
 * Signals that the appointment cannot be found
 */
public class AppointmentNotFoundException extends Exception {
}
```
###### \java\seedu\address\model\person\Person.java
``` java
    @Override
    public ObjectProperty<AppointmentList> appointmentProperty() {
        return appointments;
    }

    @Override
    public List<Appointment> getAppointments() {
        return appointments.get().toList();
    }

    public void setAppointment(List<Appointment> appointments) {
        this.appointments.set(new AppointmentList(appointments));
    }
```
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Adds appointment to a person in the internal list.
     *
     * @throws PersonNotFoundException if no such person exist in the internal list
     */
    public void addAppointment(ReadOnlyPerson target, Appointment appointment) throws PersonNotFoundException {
        requireNonNull(target);
        requireNonNull(appointment);
        Person person = getPerson(target);
        List<Appointment> list = target.getAppointments();
        list.add(appointment);
        person.setAppointment(list);
    }
    /**
     * Removes an appointment from a person in the internal list
     *
     * @throws PersonNotFoundException if no such person exist in the internal list
     */
    public void removeAppointment(ReadOnlyPerson target, Appointment appointment)
            throws PersonNotFoundException {
        requireNonNull(target);
        requireNonNull(appointment);

        Person person = getPerson(target);
        List<Appointment> newApptList = person.getAppointments();
        newApptList.remove(appointment);
        person.setAppointment(newApptList);

    }

    /**
     * Util method to extract person out from a list
     */
    private Person getPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        requireNonNull(target);
        for (Person person : internalList) {
            if (person.equals(target)) {
                return person;
            }
        }
        throw new PersonNotFoundException();
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public Tag(String name, String tagColor) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
        }
        this.tagName = trimmedName;
        this.tagColor = tagColor;
    }

    public String getTagColor() {
        return this.tagColor;
    }


    public void setColor(String color) {
        tagColor = color;
    }


    public void setRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);
        String colorInHexString = convertColorToHexadecimal(randomColor);
        tagColor = colorInHexString;
    }


    public void setOffColor() {
        tagColor = "grey";
    }
```
###### \java\seedu\address\model\tag\Tag.java
``` java
    /**
     * Converts a color to hexadecimal string
     *
     */
    private static String convertColorToHexadecimal(Color color) {
        return String.format("#%06x", color.getRGB() & 0x00FFFFFF);
    }
}
```
###### \java\seedu\address\model\tag\UniqueTagList.java
``` java
    /**
     * Replaces the Tags in this list with those in the argument tag list.
     */
    public void setTags(Set<Tag> tags, String tagString, String color) {

        requireAllNonNull(tags);

        // If color is null, it means either tag color random or tag color off
        // Else it means color is specified, and addressBook should assign a color to a tag

        if (color == null) {
            if ("random".equals(tagString)) {
                setRandomColor(tags);
            } else if ("off".equals(tagString)) {
                setOffColor(tags);
            }
        } else {
            setColor(tags, tagString, color);
        }

        assert CollectionUtil.elementsAreUnique(internalList);
        internalList.setAll(tags);
    }
```
###### \java\seedu\address\model\tag\UniqueTagList.java
``` java
    private void setOffColor(Set<Tag> tags) {
        for (Tag tag : tags) {
            tag.setOffColor();
        }
    }


    private void setColor(Set<Tag> tags, String tagString, String color) {
        for (Tag tag : tags) {
            if (tag.tagName.equals(tagString)) {
                tag.setColor(color);
            }
        }
    }

    private void setRandomColor(Set<Tag> tags) {
        for (Tag tag : tags) {
            tag.setRandomColor();
        }
    }
```
###### \java\seedu\address\storage\XmlAdaptedAppointment.java
``` java
/**
 * AXB-friendly version of the appointment list of a person
 */
public class XmlAdaptedAppointment {

    @XmlElement(required = true)
    private String description;

    @XmlElement(required = true)
    private String appointmentStart;

    @XmlElement(required = true)
    private String appointmentEnd;

    /**
     * Constructs an XmlAdaptedAppointment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {}

    /**
     * Converts a given appointment into this class for JAXB use
     */
    public XmlAdaptedAppointment(Appointment source) {
        description = source.getDescription();
        appointmentStart = source.getDateInStringFormat();
        appointmentEnd = source.getDateEndInStringFormat();
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's appointment object.
     *
     * @throws ParseException if there were any data constraints violated in the adapted person
     */
    public Appointment toModelType() throws ParseException {
        String description = this.description;

        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(Appointment.DATE_FORMATTER.parse(appointmentStart));

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(Appointment.DATE_FORMATTER.parse(appointmentEnd));

        return new Appointment(description, calendarStart, calendarEnd);
    }

}
```
###### \java\seedu\address\ui\CalendarWindow.java
``` java
/**
 * The Browser Panel of the App.
 */
public class CalendarWindow extends UiPart<Region> {

    private static final String FXML = "CalendarPanel.fxml";

    @FXML
    private CalendarView calendarView;

    private ObservableList<ReadOnlyPerson> personList;

    public CalendarWindow(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);

        this.personList = personList;

        calendarView = new CalendarView();
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        updateCalendar();
        disableViews();
        registerAsAnEventHandler(this);
    }

    /**
     * Remove clutter from interface
     */
    private void disableViews() {
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPrintButton(false);
        calendarView.showDayPage();
    }

    /**
     * Changes calendar view accordingly
     */
    private void showPage(Character c) {
        switch(c) {
        case ('d'):
            calendarView.showDayPage();
            return;
        case ('w'):
            calendarView.showWeekPage();
            return;
        case ('m'):
            calendarView.showMonthPage();
            return;
        case ('y'):
            calendarView.showYearPage();
            return;
        default:
            //should not reach here
            assert (false);
        }
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }

    @Subscribe
    private void handleNewAppointmentEvent(AddressBookChangedEvent event) {
        personList = event.data.getPersonList();
        Platform.runLater(
                this::updateCalendar
        );

    }

    @Subscribe
    private void handleCalendarViewEvent(CalendarViewEvent event) {
        Character c = event.c;
        Platform.runLater(() -> showPage(c));
    }

    /**
     * Creates a new a calendar with the update information
     */
    private void updateCalendar() {
        setTime();
        CalendarSource calendarSource = new CalendarSource("Appointments");
        int styleNum = 0;
        for (ReadOnlyPerson person : personList) {
            Calendar calendar = getCalendar(styleNum, person);
            calendarSource.getCalendars().add(calendar);
            ArrayList<Entry> entries = getEntries(person);
            styleNum++;
            styleNum = styleNum % 5;
            for (Entry entry : entries) {
                calendar.addEntry(entry);
            }
        }
        calendarView.getCalendarSources().add(calendarSource);
    }

    private Calendar getCalendar(int styleNum, ReadOnlyPerson person) {
        Calendar calendar = new Calendar(person.getName().toString());
        calendar.setStyle(Calendar.Style.getStyle(styleNum));
        calendar.setLookAheadDuration(Duration.ofDays(365));
        return calendar;
    }

    private void setTime() {
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.getCalendarSources().clear();
    }

    private ArrayList<Entry> getEntries(ReadOnlyPerson person) {
        ArrayList<Entry> entries = new ArrayList<>();
        for (Appointment appointment : person.getAppointments()) {
            LocalDateTime ldtstart = LocalDateTime.ofInstant(appointment.getDate().toInstant(),
                    ZoneId.systemDefault());
            LocalDateTime ldtend = LocalDateTime.ofInstant(appointment.getEndDate().toInstant(),
                    ZoneId.systemDefault());

            entries.add(new Entry(appointment.getDescription() + " with " + person.getName(),
                    new Interval(ldtstart, ldtend)));
        }
        return entries;
    }

}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    private void setStyle(int displayedIndex) {
        appointment.setStyle("-fx-font-weight: bold");
        color = displayedIndex % 2 == 0 ? PANE_COLOR_EVEN : PANE_COLOR_ODD;
        cardPane.setStyle("-fx-background-color: " + color);
    }
```
