# LimYangSheng
###### \java\seedu\address\commons\events\storage\BackupDataEvent.java
``` java
/**
 * Requests for a backup of the current address book.
 */
public class BackupDataEvent extends BaseEvent {
    private ReadOnlyAddressBook addressBookData;

    public BackupDataEvent(ReadOnlyAddressBook addressBookData) {
        this.addressBookData = addressBookData;
    }

    public ReadOnlyAddressBook getAddressBookData() {
        return addressBookData;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\storage\BackupFilePresentEvent.java
``` java
/**
 * Requests to check if there is a backup file in the default path.
 */
public class BackupFilePresentEvent extends BaseEvent {
    private boolean backupFileIsPresent;

    public BackupFilePresentEvent() {
        this.backupFileIsPresent = false;
    }

    public void updateBackupFilePresenceStatus(boolean status) {
        backupFileIsPresent = status;
    }

    public boolean getBackupFilePresenceStatus() {
        return backupFileIsPresent;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\storage\RestoreBackupDataEvent.java
``` java
/**
 * Requests to restore backup version of Address Book from the default file path
 */
public class RestoreBackupDataEvent extends BaseEvent {
    private ReadOnlyAddressBook backupAddressBookData;

    public void updateAddressBookData(ReadOnlyAddressBook backupAddressBookData) {
        this.backupAddressBookData = backupAddressBookData;
    }

    public ReadOnlyAddressBook getAddressBookData() {
        return backupAddressBookData;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\BackupCommand.java
``` java
/**
 * Creates a backup of the address book.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "b";
    public static final String MESSAGE_SUCCESS = "Data has been backed up";

    @Override
    public CommandResult execute() throws CommandException {
        ReadOnlyAddressBook backupAddressBookData = model.getAddressBook();
        EventsCenter.getInstance().post(new BackupDataEvent(backupAddressBookData));
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }
}
```
###### \java\seedu\address\logic\commands\RestoreBackupCommand.java
``` java
/**
 * Replace the current address book with data from backup address book.
 */
public class RestoreBackupCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "restore";
    public static final String COMMAND_ALIAS = "rb";
    public static final String MESSAGE_SUCCESS = "Data has been restored";
    public static final String MESSAGE_NO_BACKUP_FILE =
            "Unable to execute restore as there is no backup file available";

    @Override
    protected CommandResult executeUndoableCommand() {
        if (backupFilePresent()) {
            RestoreBackupDataEvent event = new RestoreBackupDataEvent();
            EventsCenter.getInstance().post(event);
            ReadOnlyAddressBook backupAddressBookData = event.getAddressBookData();
            model.resetData(backupAddressBookData);
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } else {
            return new CommandResult(String.format(MESSAGE_NO_BACKUP_FILE));
        }
    }

    /**
     * Checks if there is a backup file.
     */
    private boolean backupFilePresent() {
        BackupFilePresentEvent event = new BackupFilePresentEvent();
        EventsCenter.getInstance().post(event);
        return (event.getBackupFilePresenceStatus());
    }
}
```
###### \java\seedu\address\model\AddressBook.java
``` java
    /**
     * Updates {@code person} meetings to have reference to itself.
     */
    private void setMeetingWithPersonDetails(ReadOnlyPerson person) {
        for (Meeting meeting : person.getMeetings()) {
            meeting.setPerson(person);
        }
    }

    /**
     * Finds the meetings in meeting list with {@code Person} that equals {@code target} and replaces it with
     * {@code editedReadOnlyPerson}
     */
    public void updateMeetings(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson) {
        requireNonNull(editedReadOnlyPerson);
        meetings.updateMeetings(target, editedReadOnlyPerson);
    }

```
###### \java\seedu\address\model\meeting\Meeting.java
``` java
    /**
     * Overloaded constructor for creating meeting objects with no proper reference to their person object
     */
    public Meeting(String meetingName, String time) throws IllegalValueException {
        this.meetingName = meetingName;
        this.displayMeetingName = new SimpleObjectProperty<>(meetingName);
        if (time == null) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime date = LocalDateTime.parse(trimmedTime, formatter);
            this.date = date;
            value = date.format(formatter);
            this.displayValue = new SimpleObjectProperty<>(value);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_TIME_CONSTRAINTS);
        }
    }

    /**
     * Overloaded constructor to create a new meeting object given a meeting for reference purposes.
     */
    public Meeting(Meeting meeting) {
        this.meetingName = meeting.meetingName;
        this.date = meeting.date;
        this.value = meeting.value;
        this.person = meeting.person;
        this.displayValue = meeting.displayValue;
        this.displayMeetingName = meeting.displayMeetingName;
        this.displayName = meeting.displayName;
    }

    /**
     * Set the person attributes of the meeting object.
     */
    public void setPerson(ReadOnlyPerson person) {
        this.person = person;
        this.displayName = new SimpleObjectProperty<>(person.getName());
    }

    /**
     * Returns ReadOnlyPerson of the meeting
     */
    public ReadOnlyPerson getPerson() {
        return person;
    }

    /**
     * Return name for use by UI
     */
    public ObjectProperty<Name> nameProperty() {
        return displayName;
    }

    /**
     * Return meeting name for use by UI
     */
    public ObjectProperty<String> meetingNameProperty() {
        return displayMeetingName;
    }

    /**
     * Return meeting time for use by UI
     */
    public ObjectProperty<String> meetingTimeProperty() {
        return displayValue;
    }

```
###### \java\seedu\address\model\meeting\MeetingContainPersonPredicate.java
``` java
/**
 * Tests {@code ReadOnlyPerson} in list matches that in meeting list.
 */
public class MeetingContainPersonPredicate implements Predicate<Meeting> {
    private final List<ReadOnlyPerson> personList;

    public MeetingContainPersonPredicate(List<ReadOnlyPerson> personList) {
        this.personList = personList;
    }

    @Override
    public boolean test(Meeting meeting) {
        for (ReadOnlyPerson person : personList) {
            if (meeting.getPerson().equals(person)) {
                return true;
            }
        }

        return false;
    }
}
```
###### \java\seedu\address\model\meeting\UniqueMeetingList.java
``` java
    /**
     * Sorts the meeting list by date.
     */
    public void sortMeeting() {
        Collections.sort(internalList, new Comparator<Meeting>() {
            public int compare(Meeting one, Meeting other) {
                for (int i = 0; i < one.value.length(); i++) {
                    if (one.value.charAt(i) != (other.value.charAt(i))) {
                        if (one.value.charAt(i) > other.value.charAt(i)) {
                            return 1;
                        } else {
                            return -1;
                        }
                    }
                }
                return 0;
            }
        });
    }

    /**
     * Finds the meetings in meeting list with {@code Person} that equals {@code target} and replaces it with
     * {@code editedReadOnlyPerson}
     */
    public void updateMeetings(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson) {
        for (int i = 0; i < internalList.size(); i++) {
            Meeting meeting = new Meeting(internalList.get(i));
            if (meeting.getPerson().equals(target)) {
                meeting.setPerson(editedReadOnlyPerson);
                internalList.set(i, meeting);
            }
        }
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        List<ReadOnlyPerson> personSelected = new ArrayList<>();
        personSelected.add(event.getNewSelection().person);
        updateFilteredMeetingList(new MeetingContainPersonPredicate(personSelected));
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) {
        String backupAddressBookFilePath = createBackupAddressBookFilePath(addressBookStorage.getAddressBookFilePath());
        logger.info("Attempting to backup data to data file: " + backupAddressBookFilePath);
        try {
            saveAddressBook(addressBook, backupAddressBookFilePath);
        } catch (IOException e) {
            raise (new DataSavingExceptionEvent(e));
        }
    }

    /**
     * Creates file path of the backup data file.
     */
    private String createBackupAddressBookFilePath(String addressBookFilePath) {
        String nameOfFile = addressBookFilePath.split("[.]")[0];
        String nameOfBackupFile = nameOfFile + "-backup.xml";
        return nameOfBackupFile;
    }

```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleBackupDataEvent(BackupDataEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        backupAddressBook(event.getAddressBookData());
    }

    @Override
    @Subscribe
    public void handleRestoreBackupDataEvent(RestoreBackupDataEvent event) throws DataConversionException, IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyAddressBook backupAddressBookData;
        String backupFilePath = createBackupAddressBookFilePath(addressBookStorage.getAddressBookFilePath());
        backupAddressBookData = readAddressBook(backupFilePath).get();
        event.updateAddressBookData(backupAddressBookData);
    }

    @Override
    @Subscribe
    public void handleBackupFilePresentEvent(BackupFilePresentEvent event) {
        String backupAddressBookFilePath = createBackupAddressBookFilePath(addressBookStorage.getAddressBookFilePath());
        File f = new File(backupAddressBookFilePath);
        if (f.exists()) {
            event.updateBackupFilePresenceStatus(true);
        } else {
            event.updateBackupFilePresenceStatus(false);
        }
    }
```
###### \java\seedu\address\ui\MeetingCard.java
``` java
/**
 * An UI component that displays meetings of a person.
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingListCard.fxml";

    public final Meeting meeting;

    @FXML
    private HBox meetingCardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label meetingName;
    @FXML
    private Label meetingTime;

    public MeetingCard(Meeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ". ");
        bindListeners(meeting);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Meeting meeting) {
        name.textProperty().bind(Bindings.convert(meeting.nameProperty()));
        meetingName.textProperty().bind(Bindings.convert(meeting.meetingNameProperty()));
        meetingTime.textProperty().bind(Bindings.convert(meeting.meetingTimeProperty()));
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

    public MeetingListPanel(ObservableList<Meeting> meetingList) {
        super(FXML);
        setConnections(meetingList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Meeting> meetingList) {
        ObservableList<MeetingCard> mappedList = EasyBind.map(
                meetingList, (meeting) -> new MeetingCard(meeting, meetingList.indexOf(meeting) + 1));
        meetingListView.setItems(mappedList);
        meetingListView.setCellFactory(listView -> new MeetingListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
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
###### \resources\view\MeetingListCard.fxml
``` fxml

<HBox id="meetingCardPane" fx:id="meetingCardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <GridPane HBox.hgrow="ALWAYS">
         <columnConstraints>
            <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
         </columnConstraints>
         <children>
            <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
               <padding>
                  <Insets bottom="5" left="15" right="5" top="5" />
               </padding>
               <children>
                  <HBox alignment="CENTER_LEFT" prefWidth="130.0" spacing="5">
                     <children>
                        <Label fx:id="id" styleClass="cell_big_label">
                           <minWidth>
                              <Region fx:constant="USE_PREF_SIZE" />
                           </minWidth>
                        </Label>
                        <Label fx:id="name" styleClass="cell_big_label" text="\$first" />
                     </children>
                  </HBox>
                  <Label fx:id="meetingName" text="\$second">
                     <VBox.margin>
                        <Insets />
                     </VBox.margin>
                  </Label>
                  <Label fx:id="meetingTime" text="\$third">
                     <VBox.margin>
                        <Insets />
                     </VBox.margin>
                  </Label>
               </children>
            </VBox>
         </children>
         <rowConstraints>
            <RowConstraints />
         </rowConstraints>
      </GridPane>
   </children>
</HBox>
```
###### \resources\view\MeetingListPanel.fxml
``` fxml
<VBox xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <ListView fx:id="meetingListView" VBox.vgrow="ALWAYS" />
</VBox>
```
