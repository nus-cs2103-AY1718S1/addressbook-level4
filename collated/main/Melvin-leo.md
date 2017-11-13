# Melvin-leo
###### \java\seedu\address\commons\events\ui\JumpToMeetingListRequestEvent.java
``` java
/**
 * Indicates a request to jump to the list of meetings
 */
public class JumpToMeetingListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToMeetingListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\MeetingPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Meeting List Panel
 */
public class MeetingPanelSelectionChangedEvent extends BaseEvent {
    private final MeetingCard newSelection;

    public MeetingPanelSelectionChangedEvent(MeetingCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public MeetingCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\logic\commands\AddMeetingCommand.java
``` java
    /**
     * Creates an AddMeetingCommand to add the specified {@code ReadOnlyMeeting}
     */
    public AddMeetingCommand (NameMeeting name, DateTime date, Place location,
                              List<Index> indexes, MeetingTag meetTag) {
        this.indexes = indexes;
        this.name = name;
        this.date = date;
        this.location = location;
        this.meetTag = meetTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyPerson> personsMeet = new ArrayList<>();

        for (Index index: indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToAdd = lastShownList.get(index.getZeroBased());
            if (!personsMeet.contains(personToAdd)) {
                personsMeet.add(personToAdd);
            }
        }

        toAdd = new Meeting(name, date, location, personsMeet, meetTag);
        try {
            model.addMeeting(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateMeetingException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        } catch (MeetingBeforeCurrDateException mde) { //This exception throw handles auto deletion of Meeting cards
            throw new CommandException(MESSAGE_OVERDUE_MEETING);
        } catch (MeetingClashException mce) {
            throw new CommandException(MESSAGE_MEETING_CLASH);
        }
    }


```
###### \java\seedu\address\logic\commands\ListMeetingCommand.java
``` java
/**
 * Lists all meetings in the address book to the user.
 */

public class ListMeetingCommand extends Command {

    public static final String COMMAND_WORD = "listmeeting";
    public static final String COMMAND_ALIAS = "lm";

    public static final String MESSAGE_SUCCESS = "Listed all meetings";


    @Override
    public CommandResult execute() {
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Adds a meeting to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicateMeetingException if an equivalent meeting of the same date and time already exists.
     */
    public void addMeeting(ReadOnlyMeeting m) throws DuplicateMeetingException, MeetingBeforeCurrDateException,
            MeetingClashException {
        Meeting newMeeting = new Meeting(m);
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime currDate = LocalDateTime.now();
        LocalDateTime meetDate = LocalDateTime.parse(newMeeting.getDate().toString(), formatter);
        if (meetDate.isAfter((currDate))) {
            meetings.add(newMeeting);
        } else {
            throw new MeetingBeforeCurrDateException();
        }
    }
```
###### \java\seedu\address\model\meeting\DateTime.java
``` java
/**
 * consist the date and time of an existing meeting in the address book.
 */
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and time should only contain numeric characters, colon and spaces, and it should not be blank."
                    + " Date and time should be an actual date and time, with the format dd-MM-yyyy";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATETIME_VALIDATION_REGEX = "^(((0[1-9]|[12]\\d|3[01])[\\/\\.-](0[13578]|1[02])"
            + "[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]))|((0[1-9]|[12]\\d|30)"
            + "[\\/\\.-](0[13456789]|1[012])[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]))"
            + "|((0[1-9]|1\\d|2[0-8])[\\/\\.-](02)[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]))"
            + "|((29)[\\/\\.-](02)[\\/\\.-]((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|"
            + "((16|[2468][048]|[3579][26])00))\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])))$";

    public final String value;

    /**
     * Validates given date and time.
     *
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public DateTime(String date) throws IllegalValueException {
        requireNonNull(date);
        if (!isValidDateTime(date)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(test, formatter);
            return test.matches(DATETIME_VALIDATION_REGEX);
        } catch (DateTimeException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.meeting.DateTime // instanceof handles nulls
                && this.value.equals(((seedu.address.model.meeting.DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
```
###### \java\seedu\address\model\meeting\exceptions\DuplicateMeetingException.java
``` java
/**
 * Signals that the operation will result in duplicate Meeting objects.
 */
public class DuplicateMeetingException extends DuplicateDataException {

    public DuplicateMeetingException() {
            super("Operation would result in duplicate meetings");
    }
}
```
###### \java\seedu\address\model\meeting\exceptions\MeetingBeforeCurrDateException.java
``` java
/**
 * Signals that the operation is unable to add meeting due to Date and time before log in time.
 */
public class MeetingBeforeCurrDateException extends Exception {

    public MeetingBeforeCurrDateException() {
        super("Operation would result in invalid meetings");
    }
}
```
###### \java\seedu\address\model\meeting\Meeting.java
``` java
/**
 * Represents a Meeting in the address book.
 * Guarantees: details are present and not null, field values are validated.
 */
public class Meeting implements ReadOnlyMeeting {
    private ObjectProperty<NameMeeting> name;
    private ObjectProperty<DateTime> date;
    private ObjectProperty<Place> place;
    private ObjectProperty<List<ReadOnlyPerson>> personsMeet;
    private ObjectProperty<MeetingTag> tag;

    public Meeting (NameMeeting name, DateTime date, Place place, List<ReadOnlyPerson> listPerson, MeetingTag tag) {
        requireAllNonNull(name, date, place);
        this.name = new SimpleObjectProperty<>(name);
        this.date = new SimpleObjectProperty<>(date);
        this.place = new SimpleObjectProperty<>(place);
        this.personsMeet = new SimpleObjectProperty<>(listPerson);
        this.tag = new SimpleObjectProperty<>(tag);
    }

    /**
     * Creates a copy of the given ReadOnlyMeeting.
     */
    public Meeting(ReadOnlyMeeting source) {
        this(source.getName(), source.getDate(), source.getPlace(), source.getPersonsMeet(),
                source.getMeetTag());
    }

    public void setName(NameMeeting name) {
        this.name.set(requireNonNull(name));
    }

    @Override
    public ObjectProperty<NameMeeting> nameProperty() {
        return name;
    }

    @Override
    public NameMeeting getName() {
        return name.get();
    }

    public void setDateTime(DateTime date) {
        this.date.set(requireNonNull(date));
    }

    @Override
    public ObjectProperty<DateTime> dateProperty() {
        return date;
    }

    @Override
    public DateTime getDate() {
        return date.get();
    }

    public LocalDateTime getActualDate(String date) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return localDateTime;
    }

    public void setPlace(Place place) {
        this.place.set(requireNonNull(place));
    }

    public void setPersonsMeet(List<ReadOnlyPerson> personsMeet) {
        this.personsMeet.set(personsMeet); }

    @Override
    public ObjectProperty<Place> placeProperty() {
        return place;
    }

    @Override
    public Place getPlace() {
        return place.get();
    }

    @Override
    public ObjectProperty<List<ReadOnlyPerson>> personsMeetProperty() {
        return personsMeet;
    }

    @Override
    public List<ReadOnlyPerson> getPersonsMeet() {
        return personsMeet.get();
    }

    @Override
    public ObjectProperty<MeetingTag> meetTagProperty() {
        return tag;
    }

    @Override
    public MeetingTag getMeetTag() {
        return tag.get();
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyMeeting // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyMeeting) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, date, place);
    }

    @Override
    public String toString() {
        if (personsMeet.get().size() > 1) {
            return getGroupText();
        }
        return getAsText();
    }

}
```
###### \java\seedu\address\model\meeting\MeetingTag.java
``` java
/**
 * Contains the tag for the meeting to show significance of meeting
 */
public class MeetingTag {
    public static final String MESSAGE_MEETTAG_CONSTRAINTS = "Importance tag should be from 0 to 2";
    public static final String MEETTAG_VALIDATION_REGEX = "[0-2]";

    public final String tagName;

    /**
     * Validates given tag name.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public MeetingTag(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidTagName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_MEETTAG_CONSTRAINTS);
        }
        this.tagName = trimmedName;
    }
    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(MEETTAG_VALIDATION_REGEX);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    @Override
    public String toString() {
        return tagName;
    }
}
```
###### \java\seedu\address\model\meeting\NameMeeting.java
``` java
/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class NameMeeting {

    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Meeting type should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String NAME_VALIDATION_REGEX = "[^\\s].*";

    public final String fullName;

    /**
     * Validates given name.
     *
     * @throws IllegalValueException if given name string is invalid.
     */
    public NameMeeting(String name) throws IllegalValueException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!isValidName(trimmedName)) {
            throw new IllegalValueException(MESSAGE_NAME_CONSTRAINTS);
        }
        this.fullName = trimmedName;
    }

    /**
     * Returns true if a given string is a valid person name.
     */
    public static boolean isValidName(String test) {
        return test.matches(NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameMeeting // instanceof handles nulls
                && this.fullName.equals(((NameMeeting) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
```
###### \java\seedu\address\model\meeting\Place.java
``` java
/**
 * Represents a Meeting's place in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Place {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Places can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ADDRESS_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Place(String address) throws IllegalValueException {
        requireNonNull(address);
        if (!isValidAddress(address)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }
        this.value = address;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(ADDRESS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.meeting.Place // instanceof handles nulls
                && this.value.equals(((seedu.address.model.meeting.Place) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\meeting\ReadOnlyMeeting.java
``` java
/**
 * A read-only immutable interface for a Meeting in the addressbook.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyMeeting {
    ObjectProperty<NameMeeting> nameProperty();
    NameMeeting getName();
    ObjectProperty<DateTime> dateProperty();
    DateTime getDate();
    ObjectProperty<Place> placeProperty();
    Place getPlace();
    ObjectProperty<List<ReadOnlyPerson>> personsMeetProperty();
    List<ReadOnlyPerson> getPersonsMeet();
    ObjectProperty<MeetingTag> meetTagProperty();
    MeetingTag getMeetTag();

    /**
     * Returns true if both have the same state. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyMeeting other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getName().equals(this.getName()) // state checks here onwards
                && other.getDate().equals(this.getDate())
                && other.getPlace().equals(this.getPlace()));
    }

    /**
     * Formats the meeting as text, showing all meeting details.
     */
    default String getAsText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nMeeting with: ")
                .append(getPersonsMeet().get(0).getName())
                .append("\nContact Number: ")
                .append(getPersonsMeet().get(0).getPhone())
                .append("\nDate and Time: ")
                .append(getDate())
                .append("\nLocation: ")
                .append(getPlace());
        return builder.toString();
    }

    /**
     * Formats the meeting as text, showing all meeting details.
     */
    default String getGroupText() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("\nMeeting with: Group")
                .append("\nDate and Time: ")
                .append(getDate())
                .append("\nLocation: ")
                .append(getPlace());
        return builder.toString();
    }
}
```
###### \java\seedu\address\model\meeting\UniqueMeetingList.java
``` java
    /**
     * Adds a meeting to the list.
     *
     * @throws DuplicateMeetingException if the meeting to add is a duplicate of an existing meeting in the list.
     */
    public void add(ReadOnlyMeeting toAdd) throws DuplicateMeetingException, MeetingClashException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMeetingException();
        } else if (diffNameOfMeeting(toAdd)) {
            throw new MeetingClashException();
        } else if (diffLocationOfMeeting(toAdd)) {
            throw new MeetingClashException();
        }
        internalMeetingList.add(new Meeting(toAdd));
        sort(internalMeetingList);

    }

    /**
     *  Sorts the meeting list in chronological order
     * @param list
     * @return MeetingList
     */
    private ObservableList<Meeting> sort(ObservableList<Meeting> list) {
        list.sort((m1, m2) -> m1.getActualDate(m1.getDate().toString())
                .compareTo(m2.getActualDate(m2.getDate().toString())));

        return list;
    }
    /**
     * Replaces the meeting {@code target} in the list with {@code editedMeeting}.
     *
     * @throws DuplicateMeetingException if the replacement is equivalent to another existing meeting in the list.
     * @throws MeetingNotFoundException if {@code target} could not be found in the list.
     */
    public void setMeeting(ReadOnlyMeeting target, ReadOnlyMeeting editedMeeting)
            throws DuplicateMeetingException, MeetingNotFoundException, MeetingClashException {
        requireNonNull(editedMeeting);

        int index = internalMeetingList.indexOf(target);
        if (index == -1) {
            throw new MeetingNotFoundException();
        }
        if (!target.equals(editedMeeting) && internalMeetingList.contains(editedMeeting)) {
            throw new DuplicateMeetingException();
        } else if (diffNameOfMeeting(editedMeeting, target) || diffLocationOfMeeting(editedMeeting, target)) {
            throw new MeetingClashException();
        }

        internalMeetingList.set(index, new Meeting(editedMeeting));
        sort(internalMeetingList);
    }
```
###### \java\seedu\address\model\meeting\UniqueMeetingList.java
``` java
    public void setMeetings(List<? extends ReadOnlyMeeting> meetings) throws DuplicateMeetingException,
                                MeetingClashException {
        final UniqueMeetingList replacement = new UniqueMeetingList();
        for (final ReadOnlyMeeting meeting : meetings) {
            if (dateIsAfter((meeting.getDate().toString()))) { //to delete meetings that have passed automatically
                replacement.add(new Meeting(meeting));
            }
        }
        setMeetings(replacement);
    }
    /**
     * To check if date is after log in (current) date and time
     * @return true if meet date is after current date and time
     */
    private boolean dateIsAfter (String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime currDate = LocalDateTime.now();
        LocalDateTime meetDate = LocalDateTime.parse(date, formatter);
        if (meetDate.isAfter((currDate))) {
            return true;
        }
        return false;
    }
```
###### \java\seedu\address\storage\XmlAdaptedMeeting.java
``` java
    /**
     * Converts a given Meeting into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMeeting
     */
    public XmlAdaptedMeeting(ReadOnlyMeeting source) {
        name = source.getName().fullName;
        place = source.getPlace().value;
        date = source.getDate().toString();
        persons = new ArrayList<>();
        for (ReadOnlyPerson person: source.getPersonsMeet()) {
            persons.add(new XmlAdaptedPerson(person));
        }
        meetTag = source.getMeetTag().toString();
    }

```
###### \java\seedu\address\ui\MeetingAlert.java
``` java
/**
 * To have a pop up window to remind user about the meetings they have today
 */
public class MeetingAlert extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(MeetingAlert.class);
    private static final String ICON = "/images/alert_icon.png";
    private static final String FXML = "MeetingAlert.fxml";
    private static final String TITLE = "REMINDER!!";
    private static final String MESSAGE = "Your Next Meeting is : ";

    private final Stage dialogStage;

    @FXML
    private Label warningMessage;

    @FXML
    private Label firstMeeting;

    @FXML
    private Label nameMeeting;


    public MeetingAlert(ObservableList<ReadOnlyMeeting> list) {
        super(FXML);
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setMaxHeight(600);
        dialogStage.setMaxWidth(1000);
        dialogStage.setX(475);
        dialogStage.setY(300);
        FxViewUtil.setStageIcon(dialogStage, ICON);
        warningMessage.setText(MESSAGE);
        if (isGroupMeeting(list)) {
            int indexDate = list.get(0).getDate().toString().indexOf(' ');
            firstMeeting.setText("Group Meeting at "
                    + list.get(0).getDate().toString().substring(indexDate + 1) + " for");
            nameMeeting.setText(list.get(0).getName().toString());
        } else {
            int indexDate = list.get(0).getDate().toString().indexOf(' ');
            firstMeeting.setText("Meeting with " + list.get(0).getPersonsMeet().get(0).getName().toString()
                    + " at " + list.get(0).getDate().toString().substring(indexDate + 1) + " for");
            nameMeeting.setText(list.get(0).getName().toString());
        }
    }
    /**
     * Shows the Alert window.
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
        logger.fine("Showing alert page about the application.");
        dialogStage.showAndWait();
    }
    /**
    *Get the number of individual meetings to be shown to user
     */
    private boolean isGroupMeeting(ObservableList<ReadOnlyMeeting> list) {
        if (list.get(0).getPersonsMeet().size() > 1) {
            return true;
        }
        return false;
    }
    @FXML
    private void handleExit() {
        dialogStage.close();
    }
}
```
###### \java\seedu\address\ui\MeetingCard.java
``` java
/**
 * An UI component that displays information of a {@code Meeting}.
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingListCard.fxml";
    private static String[] colors = { "darkRed", "red", "orangeRed", "grey" };
    private static final String ICONIMPT = "/images/important.png";
    private static final String ICONHATE = "/images/dislike.png";

    public final ReadOnlyMeeting meeting;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label place;
    @FXML
    private ListView<Label> person;
    @FXML
    private ImageView icon;


    public MeetingCard(ReadOnlyMeeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ". ");
        bindListeners(meeting);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Meeting} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyMeeting meeting) {
        name.textProperty().bind(Bindings.convert(meeting.nameProperty()));
        date.textProperty().bind(Bindings.convert(meeting.dateProperty()));
        place.textProperty().bind(Bindings.convert(meeting.placeProperty()));
        List<Label> labels = new ArrayList<>();
        meeting.getPersonsMeet().forEach(person -> {
            Label newPersonLabel = new PersonLabel(person.getName().toString() + "\n"
                    + person.getPhone().toString());
            labels.add(newPersonLabel);
        });
        person.setItems(FXCollections.observableList(labels));
        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime meetingDate = LocalDateTime.parse(meeting.getDate().toString(), formatter);
        LocalDateTime currDate = LocalDateTime.now();
        long daysBet = ChronoUnit.DAYS.between(currDate, meetingDate);
        if (daysBet == 0) {
            initMeeting(meeting, colors[0]);
        } else if (daysBet == 1) {
            initMeeting(meeting, colors[1]);
        } else if (daysBet == 2) {
            initMeeting(meeting, colors[2]);
        }
        if (meeting.getMeetTag().toString().equals("2")) {
            icon.setImage(new Image(ICONIMPT));
        }
        if (meeting.getMeetTag().toString().equals("0")) {
            icon.setImage(new Image(ICONHATE));
        }

    }

    /**
     * set colours to Meeting
     * @param meeting
     */
    private void initMeeting(ReadOnlyMeeting meeting, String color) {
        cardPane.setStyle("-fx-background-color: " + color);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingCard)) {
            return false;
        }

        // state check
        MeetingCard card = (MeetingCard) other;
        return id.getText().equals(card.id.getText())
                && meeting.equals(card.meeting);
    }
}
```
###### \java\seedu\address\ui\MeetingListPanel.java
``` java
/**
 * Panel containing the list of meetings.
 */
public class MeetingListPanel extends UiPart<Region> {
    private static final String FXML = "MeetingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetingListPanel.class);

    @FXML
    private ListView<MeetingCard> meetingListView;

    public MeetingListPanel(ObservableList<ReadOnlyMeeting> meetingList) {
        super(FXML);
        setConnections(meetingList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<ReadOnlyMeeting> meetingList) {
        ObservableList<MeetingCard> mappedMeetingList = EasyBind.map(
                meetingList, (meeting) -> new MeetingCard(meeting, meetingList.indexOf(meeting) + 1));
        meetingListView.setItems(mappedMeetingList);
        meetingListView.setCellFactory(listView -> new MeetingListPanel.MeetingListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        meetingListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in meeting list panel changed to : '" + newValue + "'");
                        raise(new MeetingPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code MeetingCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            meetingListView.scrollTo(index);
            meetingListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToMeetingListRequestEvent(JumpToMeetingListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code MeetingCard}.
     */
    class MeetingListViewCell extends ListCell<MeetingCard> {

        @Override
        protected void updateItem(MeetingCard meeting, boolean empty) {
            super.updateItem(meeting, empty);

            if (empty || meeting == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(meeting.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\PersonLabel.java
``` java
/**
 * A custom label for each person's name and phone in group meeting by using (@link Label) class
 */
public class PersonLabel extends Label {
    public PersonLabel(String text) {
        super(text);
    }
}
```
###### \java\seedu\address\ui\UiManager.java
``` java
    /**
     * To check if there is a meeting on the day of logging in, only shows reminder if there is a meeting
     * @param meetingList
     * @return
     */
    private boolean meetingToday (ObservableList<ReadOnlyMeeting> meetingList) {
        for (ReadOnlyMeeting meeting : meetingList) {
            DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime currDate = LocalDateTime.now();
            LocalDateTime meetDate = LocalDateTime.parse(meeting.getDate().toString(), formatter);
            long daysBet = ChronoUnit.DAYS.between(currDate, meetDate);
            if (daysBet == 0) {
                return true;
            }
        }
        return false;
    }
```
###### \resources\view\DarkTheme.css
``` css
.label-warning {
    -fx-font-size: 30pt;
    -fx-font-family: "Segoe UI Semibold";
    -fx-text-fill: white;
    -fx-opacity: 0.9;
}
```
###### \resources\view\MeetingAlert.fxml
``` fxml

<StackPane fx:id="helpWindowRoot" styleClass="background" stylesheets="@DarkTheme.css" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <StackPane prefHeight="600.0" prefWidth="1000.0" styleClass="background" stylesheets="@DarkTheme.css">
         <children>
            <Button layoutX="750.0" layoutY="500.0" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" mnemonicParsing="false" onKeyPressed="#handleExit" onMouseClicked="#handleExit" prefHeight="40.0" prefWidth="90.0" stylesheets="@DarkTheme.css" text="Got it!" StackPane.alignment="BOTTOM_CENTER">
               <StackPane.margin>
                  <Insets bottom="20.0" />
               </StackPane.margin>
            </Button>
            <Label fx:id="warningMessage" alignment="CENTER" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="100.0" prefWidth="700.0" styleClass="label-warning" stylesheets="@DarkTheme.css" text="\$Warning" StackPane.alignment="TOP_CENTER">
               <StackPane.margin>
                  <Insets top="60.0" />
               </StackPane.margin></Label>
            <Label fx:id="firstMeeting" alignment="CENTER" maxHeight="-Infinity" maxWidth="1.7976931348623157E308" minHeight="-Infinity" minWidth="-Infinity" prefHeight="100.0" prefWidth="800.0" styleClass="label-warning" stylesheets="@DarkTheme.css" text="\$PersonName" StackPane.alignment="CENTER">
               <StackPane.margin>
                  <Insets bottom="80.0" />
               </StackPane.margin>
            </Label>
            <Label fx:id="nameMeeting" alignment="CENTER" maxHeight="-Infinity" maxWidth="1.7976931348623157E308" minHeight="-Infinity" minWidth="-Infinity" prefHeight="70.0" prefWidth="700.0" styleClass="label-warning" stylesheets="@DarkTheme.css" text="\$Meeting" StackPane.alignment="CENTER">
               <StackPane.margin>
                  <Insets top="100.0" />
               </StackPane.margin>
            </Label>
         </children>
      </StackPane>
   </children>
</StackPane>
```
###### \resources\view\MeetingListCard.fxml
``` fxml

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
    <GridPane HBox.hgrow="ALWAYS">
        <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
        </columnConstraints>
      <ImageView fx:id="icon" fitHeight="25.0" fitWidth="25.0" pickOnBounds="true" preserveRatio="true">
         <GridPane.margin>
            <Insets left="180.0" top="65.0" />
         </GridPane.margin>
      </ImageView>
        <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
            <padding>
                <Insets bottom="5" left="15" right="5" top="5" />
            </padding>
            <HBox alignment="CENTER_LEFT" spacing="5">
                <Label fx:id="id" styleClass="cell_big_label">
                    <minWidth>
                        <!-- Ensures that the label text is never truncated -->
                        <Region fx:constant="USE_PREF_SIZE" />
                    </minWidth>
                </Label>
                <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
            </HBox>
            <FlowPane fx:id="tags" />
            <Label fx:id="date" text="\$date" />
         <ListView id="listViewCard" fx:id="person" prefHeight="42.0" prefWidth="200.0" />
            <Label fx:id="place" styleClass="cell_small_label" text="\$place" />
        </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
    </GridPane>
</HBox>
```
###### \resources\view\MeetingListPanel.fxml
``` fxml

<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <Label stylesheets="@DarkTheme.css" text="                   MEETINGS" />
    <ListView fx:id="meetingListView" VBox.vgrow="ALWAYS" />
</VBox>
```
