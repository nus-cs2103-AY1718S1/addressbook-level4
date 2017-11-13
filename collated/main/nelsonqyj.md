# nelsonqyj
###### \java\seedu\address\logic\commands\AddMeetingCommand.java
``` java
/**
 * Adds a meeting to the address book.
 */
public class AddMeetingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addmeeting";
    public static final String COMMAND_ALIAS = "am";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the address book. \n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME_OF_MEETING "
            + PREFIX_DATE + "DATE_TIME "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_TAG + "IMPORTANCE \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Project Meeting "
            + PREFIX_DATE + "31-10-2017 21:30 "
            + PREFIX_LOCATION + "School of Computing, NUS "
            + PREFIX_TAG + "1";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book";
    public static final String MESSAGE_OVERDUE_MEETING = "Meeting's date and time is before log in date and time";
    public static final String MESSAGE_MEETING_CLASH = "Meeting Clashes! Please choose another date and time.";

    private List<Index> indexes;
    private Meeting toAdd;
    private final NameMeeting name;
    private final DateTime date;
    private final Place location;
    private final MeetingTag meetTag;

```
###### \java\seedu\address\logic\commands\AddMeetingCommand.java
``` java
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddMeetingCommand // instanceof handles nulls
                && toAdd.equals(((AddMeetingCommand) other).toAdd));
    }

}
```
###### \java\seedu\address\logic\commands\Command.java
``` java
    public static String getMessageForMeetingListShownSummary (int displaySize) {
        return String.format(Messages.MESSAGE_MEETING_LISTED_OVERVIEW, displaySize);
    }

    /**
     * Executes the command and returns the result message.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute() throws CommandException;

    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack) {
        this.model = model;
    }
}
```
###### \java\seedu\address\logic\commands\SelectMeetingCommand.java
``` java
/**
 * Selects a meeting identified using it's last displayed index from the address book.
 */
public class SelectMeetingCommand extends Command {

    public static final String COMMAND_WORD = "selectmeeting";
    public static final String COMMAND_ALIAS = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the meeting identified by the index number used in the last meeting listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_MEETING_SUCCESS = "Selected Meeting: %1$s";

    private final Index targetMeetingIndex;

    public SelectMeetingCommand(Index targetIndex) {
        this.targetMeetingIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyMeeting> lastShownMeetingList = model.getFilteredMeetingList();

        if (targetMeetingIndex.getZeroBased() >= lastShownMeetingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToMeetingListRequestEvent(targetMeetingIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_MEETING_SUCCESS, targetMeetingIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectMeetingCommand // instanceof handles nulls
                && this.targetMeetingIndex.equals(((SelectMeetingCommand) other).targetMeetingIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\UndoableCommand.java
``` java
    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list and meeting to
     * show all persons and meetings.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);
        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /** Returns an unmodifiable view of the filtered list of meetings */
    ObservableList<ReadOnlyMeeting> getFilteredMeetingList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ObservableList<ReadOnlyMeeting> getFilteredMeetingList() {
        return model.getFilteredMeetingList();
    }

    @Override
    public ListElementPointer getHistorySnapshot() {
        return new ListElementPointer(history.getHistory());
    }
}
```
###### \java\seedu\address\logic\parser\AddMeetingCommandParser.java
``` java
/**
 * Parses input arguments and creates a new AddMeetingCommand object
 */
public class AddMeetingCommandParser implements Parser<AddMeetingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddMeetingCommand
     * and returns an AddMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddMeetingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_NAME, PREFIX_DATE, PREFIX_LOCATION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_NAME, PREFIX_DATE, PREFIX_LOCATION, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMeetingCommand.MESSAGE_USAGE));
        }

        try {
            List<Index> indexList = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_INDEX).get());
            NameMeeting name = ParserUtil.parseNameMeeting(argMultimap.getValue(PREFIX_NAME)).get();
            DateTime date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE)).get();
            Place place = ParserUtil.parsePlace(argMultimap.getValue(PREFIX_LOCATION)).get();
            MeetingTag meetingTag = ParserUtil.parseMeetTag(argMultimap.getValue(PREFIX_TAG)).get();

            return new AddMeetingCommand(name, date, place, indexList, meetingTag);
        } catch (IllegalValueException ive) {
            throw new ParseException(ive.getMessage(), ive);
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
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> name} into an {@code Optional<NameMeeting>} if {@code name} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<NameMeeting> parseNameMeeting(Optional<String> name) throws IllegalValueException {
        requireNonNull(name);
        return name.isPresent() ? Optional.of(new NameMeeting(name.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> date} into an {@code Optional<Date>} if {@code date} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<DateTime> parseDate(Optional<String> date) throws IllegalValueException {
        requireNonNull(date);
        return date.isPresent() ? Optional.of(new DateTime(date.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> Place} into an {@code Optional<Place>} if {@code place} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Place> parsePlace(Optional<String> place) throws IllegalValueException {
        requireNonNull(place);
        return place.isPresent() ? Optional.of(new Place(place.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> phone} into an {@code Optional<Phone>} if {@code phone} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Phone> parsePhone(Optional<String> phone) throws IllegalValueException {
        requireNonNull(phone);
        return phone.isPresent() ? Optional.of(new Phone(phone.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> address} into an {@code Optional<Address>} if {@code address} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Address> parseAddress(Optional<String> address) throws IllegalValueException {
        requireNonNull(address);
        return address.isPresent() ? Optional.of(new Address(address.get())) : Optional.empty();
    }

    /**
     * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<Email> parseEmail(Optional<String> email) throws IllegalValueException {
        requireNonNull(email);
        return email.isPresent() ? Optional.of(new Email(email.get())) : Optional.empty();
    }
    /**
     * Parses a {@code Optional<String> tagname} into an {@code Optional<MeetingTag>} if {@code ntagame} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<MeetingTag> parseMeetTag(Optional<String> meetingTag) throws IllegalValueException {
        requireNonNull(meetingTag);
        return meetingTag.isPresent() ? Optional.of(new MeetingTag(meetingTag.get())) : Optional.empty();
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
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * @throws IllegalValueException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static ArrayList<Index> parseIndexes(String indexes) throws IllegalValueException {
        ArrayList<Index> listOfIndex = new ArrayList<>();
        String trimmedIndex = indexes.trim();
        String[] indexs = trimmedIndex.split("\\s+");
        for (String index : indexs) {
            if (!StringUtil.isNonZeroUnsignedInteger(index)) {
                throw new IllegalValueException(MESSAGE_INVALID_INDEX);
            }
            listOfIndex.add(Index.fromOneBased(Integer.parseInt(index)));
        }
        return listOfIndex;
    }

}
```
###### \java\seedu\address\logic\parser\SelectMeetingCommandParser.java
``` java
/**
 * Parses input arguments and creates a new SelectMeetingCommand object
 */
public class SelectMeetingCommandParser implements Parser<SelectMeetingCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectMeetingCommand
     * and returns an SelectMeetingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectMeetingCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectMeetingCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectMeetingCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\meeting\exceptions\MeetingClashException.java
``` java
/**
 * Signals that the operation will result in duplicate Meeting objects.
 */
public class MeetingClashException extends IllegalValueException {

    public MeetingClashException() {
            super("Operation would result in clashing meetings");
    }
}

```
###### \java\seedu\address\model\meeting\UniqueMeetingList.java
``` java
/**
 * A list of meetings that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 *
 * @see Meeting#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalMeetingList = FXCollections.observableArrayList();
    // used by asObservableList()
    private final ObservableList<ReadOnlyMeeting> mappedMeetingList =
            EasyBind.map(internalMeetingList, (meeting) -> meeting);


    /**
     * Returns true if the list contains an equivalent meeting as the given argument.
     */
    public boolean contains(ReadOnlyMeeting toCheck) {
        requireNonNull(toCheck);
        return internalMeetingList.contains(toCheck);
    }

    /**
     * Returns true if the list contains a clashing meeting that has a different name of meeting as the given argument.
     */
    public boolean diffNameOfMeeting(ReadOnlyMeeting toCheck) {
        for (Meeting meeting : internalMeetingList) {
            if (toCheck.getDate().equals(meeting.getDate())) {
                if (!toCheck.getName().equals(meeting.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Returns true if the list contains a clashing meeting that has a different name of meeting as the given argument.
     */
    public boolean diffNameOfMeeting(ReadOnlyMeeting toCheck, ReadOnlyMeeting target) {
        for (Meeting meeting : internalMeetingList) {
            if (meeting != target) {
                if (toCheck.getDate().equals(meeting.getDate())) {
                    if (!toCheck.getName().equals(meeting.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the list contains a clashing meeting that has a different meeting location as the given argument.
     */
    public boolean diffLocationOfMeeting(ReadOnlyMeeting toCheck) {
        for (Meeting meeting : internalMeetingList) {
            if (toCheck.getDate().equals(meeting.getDate())) {
                if (!toCheck.getPlace().equals(meeting.getPlace())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Returns true if the list contains a clashing meeting that has a different meeting location as the given argument.
     */
    public boolean diffLocationOfMeeting(ReadOnlyMeeting toCheck, ReadOnlyMeeting target) {
        for (Meeting meeting : internalMeetingList) {
            if (target != meeting) {
                if (toCheck.getDate().equals(meeting.getDate())) {
                    if (!toCheck.getPlace().equals(meeting.getPlace())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

```
###### \java\seedu\address\model\meeting\UniqueMeetingList.java
``` java
    /**
     * Removes the equivalent meeting from the list.
     *
     * @throws MeetingNotFoundException if no such meeting could be found in the list.
     */
    public boolean remove(ReadOnlyMeeting toRemove) throws MeetingNotFoundException {
        requireNonNull(toRemove);
        final boolean meetingFoundAndDeleted = internalMeetingList.remove(toRemove);
        if (!meetingFoundAndDeleted) {
            throw new MeetingNotFoundException();
        }
        return meetingFoundAndDeleted;
    }

    public void setMeetings(UniqueMeetingList replacement) {
        this.internalMeetingList.setAll(replacement.internalMeetingList);
    }

```
###### \java\seedu\address\model\meeting\UniqueMeetingList.java
``` java
    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<ReadOnlyMeeting> asObservableList() {
        return FXCollections.unmodifiableObservableList(mappedMeetingList);
    }

    @Override
    public Iterator<Meeting> iterator() {
        return internalMeetingList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                && this.internalMeetingList.equals(((UniqueMeetingList) other).internalMeetingList));
    }

    @Override
    public int hashCode() {
        return internalMeetingList.hashCode();
    }

}
```
###### \java\seedu\address\storage\XmlAdaptedMeeting.java
``` java
/**
 * JAXB-friendly version of the Meeting.
 */
public class XmlAdaptedMeeting {

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String place;
    @XmlElement(required = true)
    private String date;
    @XmlElement
    private List<XmlAdaptedPerson> persons = new ArrayList<>();
    @XmlElement(required = true)
    private String meetTag;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedMeeting.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}


```
###### \java\seedu\address\storage\XmlAdaptedMeeting.java
``` java
    /**
     * Converts this jaxb-friendly adapted meeting object into the model's Meeting object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting
     */
    public Meeting toModelType() throws IllegalValueException {

        final NameMeeting name = new NameMeeting(this.name);
        final DateTime dateTime = new DateTime(this.date);
        final Place place = new Place(this.place);
        final List<ReadOnlyPerson> personsMeet = new ArrayList<>();
        for (XmlAdaptedPerson person: this.persons) {
            personsMeet.add(person.toModelType());
        }
        final MeetingTag meetTag = new MeetingTag(this.meetTag);

        return new Meeting(name, dateTime, place, personsMeet, meetTag);
    }

}
```
###### \java\seedu\address\storage\XmlSerializableAddressBook.java
``` java
    @Override
    public ObservableList<ReadOnlyMeeting> getMeetingList() {
        final ObservableList<ReadOnlyMeeting> meetings = this.meetings.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return FXCollections.unmodifiableObservableList(meetings);
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    private void loadMeetingPage(ReadOnlyMeeting meeting) {
        loadPage(GOOGLE_MAP_SEARCH_URL_PREFIX + meeting.getPlace().value.replaceAll(" ", "+")
                + GOOGLE_MAP_SEARCH_URL_SUFFIX);
    }
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    @Subscribe
    private void handleMeetingPanelSelectionChangedEvent(MeetingPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadMeetingPage(event.getNewSelection().meeting);
    }
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    public int getZeroBasedIndex() {
        return index - 1; //to ensure the index is zero-based
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * Opens the person window.
     */
    @FXML
    public void handlePersonWindow(PersonCard personCard) {
        PersonWindow personWindow = new PersonWindow(personList, personCard);
        personWindow.show();
    }
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handlePersonWindow(event.getNewSelection());
    }
```
###### \java\seedu\address\ui\PersonWindow.java
``` java
/**
 * Pop-up to show a contact's detail
 */
public class PersonWindow extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(PersonWindow.class);
    private static final String ICON = "/images/contacts_icon.png";
    private static final String FXML = "PersonWindow.fxml";
    private static final String TITLE = "Contact Details";

    private final Stage dialogStage;

    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;



    public PersonWindow(ObservableList<ReadOnlyPerson> list, PersonCard personCard) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaxHeight(600);
        dialogStage.setMaxWidth(1000);
        dialogStage.setX(475);
        dialogStage.setY(300);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        name.setText("Name: " + list.get(personCard.getZeroBasedIndex()).getName().toString());
        phone.setText("Phone Number: " + list.get(personCard.getZeroBasedIndex()).getPhone().toString());
        address.setText("Address: " + list.get(personCard.getZeroBasedIndex()).getAddress().toString());
        email.setText("Email: " + list.get(personCard.getZeroBasedIndex()).getEmail().toString());
    }

    /**
     * Shows the Person window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing person page about the application.");
        dialogStage.showAndWait();
    }

    public void close() {
        dialogStage.close();
    }

    @FXML
    private void handleExit () {
        dialogStage.close();
    }

}
```
###### \resources\view\DarkTheme.css
``` css
.label {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: #ffffff;
    -fx-opacity: 0.9;
}
```
###### \resources\view\DarkTheme.css
``` css
.label-bright {
    -fx-font-size: 11pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.label-header {
    -fx-font-size: 32pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 1;
}

.text-field {
    -fx-font-size: 12pt;
    -fx-font-family: "Segoe UI Semibold";
}

.tab-pane {
    -fx-padding: 0 0 0 1;
}

.tab-pane .tab-header-area {
    -fx-padding: 0 0 0 0;
    -fx-min-height: 0;
    -fx-max-height: 0;
}

.table-view {
    -fx-base: #1d1d1d;
    -fx-control-inner-background: #1d1d1d;
    -fx-background-color: #1d1d1d;
    -fx-table-cell-border-color: transparent;
    -fx-table-header-border-color: transparent;
    -fx-padding: 5;
}

.table-view .column-header-background {
    -fx-background-color: transparent;
}

.table-view .column-header, .table-view .filler {
    -fx-size: 35;
    -fx-border-width: 0 0 1 0;
    -fx-background-color: transparent;
    -fx-border-color:
        transparent
        transparent
        derive(-fx-base, 80%)
        transparent;
    -fx-border-insets: 0 10 1 0;
}

.table-view .column-header .label {
    -fx-font-size: 20pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-alignment: center-left;
    -fx-opacity: 1;
}

.table-view:focused .table-row-cell:filled:focused:selected {
    -fx-background-color: -fx-focus-color;
}

.split-pane:horizontal .split-pane-divider {
    -fx-background-color: derive(#3d4691, 0%);
    -fx-border-color: transparent transparent transparent #4d4d4d;
}

/*contacts*/
.split-pane {
    -fx-border-radius: 1;
    -fx-border-width: 1;
    -fx-background-color: derive(#3d4691, 0%);
}

.list-view {
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: derive(#3d4691, 20%);
}
#listViewCard, #listViewCard .list-cell{
    -fx-background-insets: 0;
    -fx-padding: 0;
    -fx-background-color: transparent;
}

.list-cell {
    -fx-label-padding: 0 0 0 0;
    -fx-graphic-text-gap : 0;
    -fx-padding: 0 0 0 0;
    -fx-background-color: derive(#3d4691, 20%);
}

.list-cell:filled:even {
    /*-fx-background-color: #f18d00; orange*/
    -fx-background-color: derive(#3d4691, 50%);
}

.list-cell:filled:odd {
    -fx-background-color: #4a80f5;
}

.list-cell:filled:selected {
    -fx-background-color: #424d5f;
}

.list-cell:filled:selected #cardPane {
    -fx-border-color: #3e7b91;
    -fx-border-width: 1;
}

.list-cell .label {
    -fx-text-fill: white;
}

.cell_big_label {
    -fx-font-family: "Segoe UI Semibold";
    -fx-font-size: 16px;
    -fx-text-fill: #010504;
}

.cell_small_label {
    -fx-font-family: "Segoe UI";
    -fx-font-size: 13px;
    -fx-text-fill: #010504;
}

.anchor-pane {
     -fx-background-color: derive(#1d1d1d, 20%);
}

/*2nd portion*/
.pane-with-border {
     -fx-background-color: derive(#3d4691, 0%);
     -fx-border-color: derive(#000000, 0%);
     -fx-border-top-width: 1px;
}

.status-bar {
    -fx-background-color: derive(#000080, 0%);
    -fx-text-fill: black;
}

.result-display {
    -fx-background-color: transparent;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

.result-display .label {
    -fx-text-fill: black !important;
}

.status-bar .label {
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
}

.status-bar-with-border {
    -fx-background-color: derive(#000080, 0%);
    -fx-border-color: derive(#1d1d1d, 25%);
    -fx-border-width: 1px;
}

.status-bar-with-border .label {
    -fx-text-fill: white;
}

.grid-pane {
    -fx-background-color: derive(#000080, 30%);
    -fx-border-color: derive(#1d1d1d, 30%);
    -fx-border-width: 1px;
}

.grid-pane .anchor-pane {
    -fx-background-color: derive(#000080, 30%);
}

.context-menu {
    -fx-background-color: derive(#000080, 50%);
}

.context-menu .label {
    -fx-text-fill: white;
}

.menu-bar {
    -fx-background-color: derive(#000080, 0%);
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-font-family: "Segoe UI Light";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}

.menu .left-container {
    -fx-background-color: black;
}

/*
 * Metro style Push Button
 * Author: Pedro Duque Vieira
 * http://pixelduke.wordpress.com/2012/10/23/jmetro-windows-8-controls-on-java/
 */
.button {
    -fx-padding: 5 22 5 22;
    -fx-border-color: #e2e2e2;
    -fx-border-width: 2;
    -fx-background-radius: 0;
    -fx-background-color: #3d4699;
    -fx-font-family: "Segoe UI", Helvetica, Arial, sans-serif;
    -fx-font-size: 11pt;
    -fx-text-fill: #d8d8d8;
    -fx-background-insets: 0 0 0 0, 0, 1, 2;
}

.button:hover {
    -fx-background-color: #3a3a3a;
}

.button:pressed, .button:default:hover:pressed {
  -fx-background-color: white;
  -fx-text-fill: #1d1d1d;
}

.button:focused {
    -fx-border-color: white, white;
    -fx-border-width: 1, 1;
    -fx-border-style: solid, segments(1, 1);
    -fx-border-radius: 0, 0;
    -fx-border-insets: 1 1 1 1, 0;
}

.button:disabled, .button:default:disabled {
    -fx-opacity: 0.4;
    -fx-background-color: #1d1d1d;
    -fx-text-fill: white;
}

.button:default {
    -fx-background-color: -fx-focus-color;
    -fx-text-fill: #ffffff;
}

.button:default:hover {
    -fx-background-color: derive(-fx-focus-color, 30%);
}

.dialog-pane {
    -fx-background-color: #000080;
}

.dialog-pane > *.button-bar > *.container {
    -fx-background-color: #000080;
}

.dialog-pane > *.label.content {
    -fx-font-size: 14px;
    -fx-font-weight: bold;
    -fx-text-fill: white;
}

.dialog-pane:header *.header-panel {
    -fx-background-color: derive(#000080, 25%);
}

.dialog-pane:header *.header-panel *.label {
    -fx-font-size: 18px;
    -fx-font-style: italic;
    -fx-fill: white;
    -fx-text-fill: white;
}

.scroll-bar {
    -fx-background-color: derive(#808080, 0%);
}

.scroll-bar .thumb {
    -fx-background-color: derive(#808080, 50%);
    -fx-background-insets: 3;
}

.scroll-bar .increment-button, .scroll-bar .decrement-button {
    -fx-background-color: transparent;
    -fx-padding: 0 0 0 0;
}

.scroll-bar .increment-arrow, .scroll-bar .decrement-arrow {
    -fx-shape: " ";
}

.scroll-bar:vertical .increment-arrow, .scroll-bar:vertical .decrement-arrow {
    -fx-padding: 1 8 1 8;
}

.scroll-bar:horizontal .increment-arrow, .scroll-bar:horizontal .decrement-arrow {
    -fx-padding: 8 1 8 1;
}

#cardPane {
    -fx-background-color: transparent;
    -fx-border-width: 0;
}

#commandTypeLabel {
    -fx-font-size: 11px;
    -fx-text-fill: #F70D1A;
}

#commandTextField {
    -fx-background-color: transparent #383838 transparent #383838;
    -fx-background-insets: 0;
    -fx-border-color: #ffffff #ffffff #ffffff #ffffff;
    -fx-border-insets: 0;
    -fx-border-width: 1;
    -fx-font-family: "Segoe UI Light";
    -fx-font-size: 13pt;
    -fx-text-fill: white;
}

#filterField, #personListPanel, #personWebpage {
    -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);
}

#resultDisplay .content {
    -fx-background-color: transparent, #383838, transparent, #383838;
    -fx-background-radius: 0;
}

#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
}

#tags .label {
    -fx-text-fill: white;
    -fx-background-color: #34af23;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
}
```
###### \resources\view\PersonWindow.fxml
``` fxml

<TitledPane animated="false" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="335.0" prefWidth="440.0" text="Contact Details" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <content>
    <AnchorPane minHeight="0.0" minWidth="0.0" prefHeight="190.0" prefWidth="398.0" style="-fx-background-color: paleturquoise;">
         <children>
            <Label fx:id="email" layoutX="7.0" layoutY="236.0" text="\$email">
               <font>
                  <Font name="Noteworthy Bold" size="17.0" />
               </font></Label>
            <Label fx:id="address" layoutX="7.0" layoutY="206.0" text="\$address">
               <font>
                  <Font name="Noteworthy Bold" size="17.0" />
               </font></Label>
            <Label fx:id="name" layoutX="7.0" layoutY="146.0" text="\$name">
               <font>
                  <Font name="Noteworthy Bold" size="17.0" />
               </font></Label>
            <ImageView fitHeight="150.0" fitWidth="200.0" layoutX="3.0" pickOnBounds="true" preserveRatio="true">
               <image>
                  <Image url="@../images/red_contacts_icon.png" />
               </image>
            </ImageView>
            <Button layoutX="383.0" layoutY="263.0" mnemonicParsing="false" onKeyPressed="#handleExit" onMouseClicked="#handleExit" style="-fx-background-color: palevioletred;" text="Done">
               <font>
                  <Font name="Noteworthy Bold" size="13.0" />
               </font></Button>
            <Label fx:id="phone" layoutX="7.0" layoutY="176.0" text="\$number">
               <font>
                  <Font name="Noteworthy Bold" size="17.0" />
               </font></Label>
         </children></AnchorPane>
  </content>
   <font>
      <Font name="Noteworthy Bold" size="13.0" />
   </font>
</TitledPane>
```
