# Eric
###### \java\seedu\address\logic\commands\AddAppointmentCommand.java
``` java
/**
 * Command to add appointment to a person in addressBook
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "appointment";
    public static final String COMMAND_ALIAS = "apt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appoint to a person in address book. \n"
            + COMMAND_ALIAS + ": Shorthand equivalent for add. \n"
            + "Parameters: " + PREFIX_NAME + "PERSON "
            + PREFIX_DATE + "TIME" + "\n"
            + "Example 1:" + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "Next Monday 3pm";

    public static final String MESSAGE_SUCCESS = "New appointment added. ";
    public static final String INVALID_PERSON = "This person is not in your address book";
    public static final String INVALID_DATE = "Invalid Date. Please enter a valid date.";


    private final Index index;
    private final Calendar date;


    public AddAppointmentCommand() {
        date = null;
        index = null;
    }

    public AddAppointmentCommand(Index index) {
        this.index = index;
        this.date = null;
    }

    public AddAppointmentCommand(Index index, Calendar date) {
        this.index = index;
        this.date = date;
    }

    @Override
    public CommandResult execute() throws CommandException {


        if (date == null && index == null) {
            model.listAppointment();
            return new CommandResult("Rearranged contacts to show upcoming appointments.");
        }

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();


        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddAppointment = lastShownList.get(index.getZeroBased());

        Appointment appointment;
        requireNonNull(index);
        if (date == null) {
            appointment = new Appointment(personToAddAppointment.getName().toString());
        } else {
            appointment = new Appointment(personToAddAppointment.getName().toString(), date);
        }

        if (date != null && !isDateValid()) {
            return new CommandResult(INVALID_DATE);
        }

        try {
            model.addAppointment(appointment);
        } catch (PersonNotFoundException e) {
            return new CommandResult(INVALID_PERSON);
        }
        if (date == null) {
            return new CommandResult("Appointment with " + personToAddAppointment.getName().toString()
                    + " set to off.");
        }
        return new CommandResult(MESSAGE_SUCCESS + "Meet " +  appointment.getPersonName().toString()
                + " on "
                +  appointment.getDate().toString());
    }

    /**
     * @return is appointment date set to after current time
     */
    private boolean isDateValid() {
        requireNonNull(date);
        Calendar calendar = Calendar.getInstance();
        return !date.getTime().before(calendar.getTime());
    }

    public Index getIndex() {
        return this.index;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddAppointmentCommand // instanceof handles nulls
                && this.index.getZeroBased() ==  ((AddAppointmentCommand) other).index.getZeroBased())
                && this.date.getTimeInMillis() == ((AddAppointmentCommand) other).date.getTimeInMillis();
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

        if (userInput.split(" ").length == 1) {
            return new AddAppointmentCommand();
        }

        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DATE);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        String[] args = userInput.split(" ");
        try {
            Index index = Index.fromOneBased(Integer.parseInt(args[1]));
            if ("d/off".equals(args[2])) {
                return new AddAppointmentCommand(index);
            }
            com.joestelmach.natty.Parser parser = new com.joestelmach.natty.Parser();
            List<DateGroup> groups = parser.parse(argumentMultimap.getValue(PREFIX_DATE).get());
            Calendar calendar = Calendar.getInstance();
            if (groups.size() == 0) {
                throw new ParseException("Please be more specific with your appointment time");
            }
            calendar.setTime(groups.get(0).getDates().get(0));
            return new AddAppointmentCommand(index, calendar);
        } catch (NumberFormatException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
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

```
###### \java\seedu\address\model\AddressBook.java
``` java
    public void addAppointment(Appointment appointment) throws PersonNotFoundException {
        persons.addAppointment(appointment);
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
    public void addAppointment(Appointment appointment) throws PersonNotFoundException {
        addressBook.addAppointment(appointment);
        indicateAddressBookChanged();
    }

    /**
     * @return an unmodifiable view of the list of ReadOnlyPerson that has nonNull appointment date,
     * in chronological order
     */
    @Override
    public ObservableList<ReadOnlyPerson> listAppointment() {

        ObservableList<ReadOnlyPerson> list = addressBook.getPersonListSortByAppointment();


        return FXCollections.unmodifiableObservableList(list);
    }

```
###### \java\seedu\address\model\person\Appointment.java
``` java
/**
 *  Appointment class to hold all the appointment information of an appointment
 *  */
public class Appointment {

    public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm";
    public static final DateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    private String personString;
    private Date date;


    public Appointment(String person) {
        this.personString = person;
    }
    public Appointment(String person, Calendar calendar) {
        requireNonNull(calendar);
        Date date = calendar.getTime();
        this.personString = person;
        this.date = date;
    }

    public String getPersonName() {
        return this.personString;
    }

    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        if (date != null) {
            return "Appointment on " + DATE_FORMATTER.format(date);
        } else {
            return "No appointment set with " + personString;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Appointment // instanceof handles nulls
                && this.personString.equals(((Appointment) other).personString)
                && this.getDate().toInstant().equals(((Appointment) other).getDate().toInstant()));
    }
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
    private Calendar calendar;
    public CalendarWindow(ObservableList<ReadOnlyPerson> personList) {
        super(FXML);

        this.personList = personList;
        calendarView = new CalendarView();

        CalendarSource myCalendarSource = new CalendarSource("My Calendars");

        calendarView.getCalendarSources().addAll(myCalendarSource);

        calendarView.setRequestedTime(LocalTime.now());

        calendar = new Calendar("Appointment");

        CalendarSource calendarSource = new CalendarSource("Appointments");

        calendarSource.getCalendars().add(calendar);
        calendarView.getCalendarSources().add(calendarSource);
        //Disabling views to make the calendar more simplistic
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowPageToolBarControls(false);
        calendarView.setShowSearchField(false);
        calendarView.setShowSearchResultsTray(false);
        calendarView.setShowPageSwitcher(false);
        calendarView.setShowPrintButton(false);
        calendarView.setShowToolBar(false);
        calendarView.showWeekPage();
        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                        setAppointments();
                    });

                    try {
                        // update every 10 seconds
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }

    public CalendarView getRoot() {
        return this.calendarView;
    }

    public void setAppointments() {
        for (ReadOnlyPerson person : personList) {
            if (person.getAppointment().getDate() == null) {
                continue;
            }
            LocalDateTime ldt = LocalDateTime.ofInstant(person.getAppointment().getDate().toInstant(),
                    ZoneId.systemDefault());
            Entry entry = new Entry(person.getName().toString());
            entry.setInterval(new Interval(ldt, ldt.plusHours(1)));
            List<Entry<?>> result = calendar.findEntries(person.getName().toString());
            calendar.removeEntries(result);
            calendar.addEntry(entry);
        }
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
