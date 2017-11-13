# frozventus
###### \java\seedu\address\commons\events\commands\DisplayBinEvent.java
``` java
/** Indicates the Recycle Bin is now displayed*/
public class DisplayBinEvent extends BaseEvent {

    public DisplayBinEvent () {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\commands\DisplayListFilteredEvent.java
``` java
/** Indicates the the displayed list is now filtered*/
public class DisplayListFilteredEvent extends BaseEvent {

    public DisplayListFilteredEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\commands\DisplayListResetEvent.java
``` java
/** Indicates the main contact list is now displayed*/
public class DisplayListResetEvent extends BaseEvent {

    public DisplayListResetEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\commands\PersonDeletedEvent.java
``` java
/** Indicates the AddressBook in the display list is now filtered*/
public class PersonDeletedEvent extends BaseEvent {

    public PersonDeletedEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

```
###### \java\seedu\address\logic\commands\BinClearCommand.java
``` java
/**
 * Clears the recycle bin.
 */
public class BinClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "binclear";
    public static final String MESSAGE_SUCCESS = "Recycle Bin has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new AddressBookData(new AddressBook(model.getAddressBook()), new RecycleBin()));
        EventsCenter.getInstance().post(new PersonDeletedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\BinListCommand.java
``` java
    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new DisplayBinEvent());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
```
###### \java\seedu\address\logic\commands\Command.java
``` java
    /**
     * Provides any needed dependencies to the command.
     * Commands making use of any of these should override this method to gain
     * access to the dependencies.
     */
    public void setData(Model model, CommandHistory history, UndoRedoStack undoRedoStack, boolean binMode) {
        this.model = model;
        this.binMode = binMode;
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public void setListDisplay() {
        this.model.setListDisplay();
    }

    @Override
    public void setBinDisplay() {
        this.model.setBinDisplay();
    }

```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Subscribe
    public void handleDisplayListResetEvent(DisplayListResetEvent event) {
        binMode = false;
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    public void handleDisplayBinEvent(DisplayBinEvent event) {
        binMode = true;
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }
}
```
###### \java\seedu\address\model\Model.java
``` java
    /** Restores the given person from recycle bin */
    void restorePerson(ReadOnlyPerson target) throws PersonNotFoundException, DuplicatePersonException;

    /** Deletes the given person from bin */
    void deleteFromBin(ReadOnlyPerson target) throws PersonNotFoundException;

```
###### \java\seedu\address\model\Model.java
``` java
    /** Toggle display to show list */
    void setListDisplay();

    /** Toggle display to show bin */
    void setBinDisplay();

    /** Returns an unmodifiable view of the address book person list */
    ObservableList<ReadOnlyPerson> getAddressBookList();

    /** Returns an unmodifiable view of the recycle bin list */
    ObservableList<ReadOnlyPerson> getRecycleBinList();

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void resetData(AddressBookData newData) {
        addressBook.resetData(newData.getAddressBook());
        recycleBin.resetData(newData.getRecycleBin());
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(new AddressBookData(addressBook, recycleBin)));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException,
                                                                        DuplicatePersonException {
        addressBook.removePerson(target);
        recycleBin.addPerson(target);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void restorePerson(ReadOnlyPerson target) throws PersonNotFoundException,
                                                                         DuplicatePersonException {
        addressBook.addPerson(target);
        recycleBin.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteFromBin(ReadOnlyPerson target) throws PersonNotFoundException {
        recycleBin.removePerson(target);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void setListDisplay() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        this.filteredPersons = filteredAddresses;
    }

    @Override
    public void setBinDisplay() {
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        this.filteredPersons = filteredBin;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getAddressBookList() {
        return filteredAddresses;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getRecycleBinList() {
        return filteredBin;
    }

```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's birthday in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Birthday has to be in the format dd-mm-yyyy";
    public static final String BIRTHDAY_VALIDATION_REGEX = "(0[1-9]|[12]\\d|3[01])[-](0[1-9]|1[0-2])[-]\\d{4}";
    public static final String BIRTHDAY_NOT_ASSIGNED = "~";
    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given birthday is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {

        if (birthday == null) {
            this.value = BIRTHDAY_NOT_ASSIGNED;
        } else {
            String trimmedBirthday = birthday.trim();

            if (!trimmedBirthday.equals(BIRTHDAY_NOT_ASSIGNED)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setLenient(false);

                try {
                    dateFormat.parse(trimmedBirthday);
                } catch (ParseException e) {
                    throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
                }
            }

            if (!isValidBirthday(trimmedBirthday)) {
                throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
            }
            this.value = trimmedBirthday;
        }
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX) || test.matches(BIRTHDAY_NOT_ASSIGNED);
    }

    public String getMonth() {
        String[] args = value.split("-");

        return args[1];
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Birthday // instanceof handles nulls
                && this.value.equals(((Birthday) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\RecycleBin.java
``` java
/**
 * An extension of AddressBook to hold deleted contacts
 * Duplicates are not allowed (by .equals comparison)
 */
public class RecycleBin extends AddressBook {

    public RecycleBin() {}

    /**
     * Creates a RecycleBin using the Persons and Tags in the {@code toBeCopied}
     */
    public RecycleBin(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    @Override
    /**
     * Resets the existing data of this {@code RecycleBin} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }
}
```
###### \java\seedu\address\storage\AddressBookData.java
``` java
/**
 * A storage data holder to hold both addressBook and recycleBin data
 */
public class AddressBookData {

    private ReadOnlyAddressBook addressBook;
    private ReadOnlyAddressBook recycleBin;

    public AddressBookData() {
        addressBook = new AddressBook();
        recycleBin = new RecycleBin();
    }

    public AddressBookData(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin) {
        this();
        this.addressBook = addressBook;
        this.recycleBin = recycleBin;
    }

    public void setAddressBook (ReadOnlyAddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public void setRecycleBin (ReadOnlyAddressBook recycleBin) {
        this.recycleBin = recycleBin;
    }

    public ReadOnlyAddressBook getAddressBook () {
        return addressBook;
    }

    public ReadOnlyAddressBook getRecycleBin () {
        return recycleBin;
    }
}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    /**
     * Similar to {@link #saveAddressBook(AddressBookData)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(AddressBookData addressBook, String filePath)
            throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        File file = new File(filePath);
        FileUtil.createIfMissing(file);
        XmlFileStorage.saveDataToFile(file, new XmlSerializableAddressBookCombined(addressBook.getAddressBook(),
                                                                                   addressBook.getRecycleBin()));
    }

}
```
###### \java\seedu\address\storage\XmlFileStorage.java
``` java
    /**
     * Returns address book in the file or an empty address book
     */
    public static AddressBookData loadDataFromSaveFile(File file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            XmlSerializableAddressBookCombined data =
                    XmlUtil.getDataFromFile(file, XmlSerializableAddressBookCombined.class);
            return new AddressBookData(data.getAddressBook(), data.getRecycleBin());
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
```
###### \java\seedu\address\storage\XmlSerializableAddressBookCombined.java
``` java
/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBookCombined {

    @XmlElement
    private XmlSerializableAddressBook addressBook;
    @XmlElement
    private XmlSerializableAddressBook recycleBin;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBookCombined() {
        addressBook = new XmlSerializableAddressBook();
        recycleBin = new XmlSerializableAddressBook();
    }

    /**
     * Conversion
     */

    public XmlSerializableAddressBookCombined(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin) {
        this();
        this.addressBook = new XmlSerializableAddressBook(addressBook);
        this.recycleBin = new XmlSerializableAddressBook(recycleBin);
    }

    public XmlSerializableAddressBook getAddressBook () {
        return addressBook;
    }

    public XmlSerializableAddressBook getRecycleBin () {
        return recycleBin;
    }
}
```
###### \java\seedu\address\ui\AddressPanel.java
``` java
    /**
     * Resets details shown
     */
    private void resetPersonDetails() {
        loadDefaultPage();
        addressLarge.textProperty().unbind();
        addressLarge.textProperty().setValue("");
    }

```
###### \java\seedu\address\ui\AddressPanel.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        addressLarge.textProperty().bind(Bindings.convert(person.addressProperty()));
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

```
###### \java\seedu\address\ui\AddressPanel.java
``` java
    @Subscribe
    public void handlePersonDeletedEvent(PersonDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetPersonDetails();
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        addressPanel = new AddressPanel(prefs);
        addressPlaceholder.getChildren().add(addressPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        ResultDisplay resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(prefs.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        personDisplayCard = new PersonDisplayCard();
        detailsPlaceholder.getChildren().add(personDisplayCard.getRoot());
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    private void setListDisplay() {
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    private void switchListDisplay() {
        logic.setListDisplay();
        listName.textProperty().setValue("List");
    }

    private void switchBinDisplay() {
        logic.setBinDisplay();
        listName.textProperty().setValue("Bin");
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleDisplayListFilteredEvent(DisplayListFilteredEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (listName.textProperty().get().equals("List")) {
            listName.textProperty().setValue("Filtered List");
        } else if (listName.textProperty().get().equals("Bin")) {
            listName.textProperty().setValue("Filtered Bin");
        }
    }

    @Subscribe
    private void handleDisplayListResetEvent(DisplayListResetEvent event) {
        if (listName.textProperty().get().equals("Bin") || listName.textProperty().get().equals("Filtered Bin")) {
            switchListDisplay();
            setListDisplay();
        }
        if (listName.textProperty().get().equals("Filtered List")) {
            listName.textProperty().setValue("List");
        }
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

    @Subscribe
    private void handleDisplayBinEvent(DisplayBinEvent event) {
        if (listName.textProperty().get().equals("Filtered List") || listName.textProperty().get().equals("List")) {
            switchBinDisplay();
            setListDisplay();
        }
        if (listName.textProperty().get().equals("Filtered Bin")) {
            listName.textProperty().setValue("Bin");
        }
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
    }

```
###### \java\seedu\address\ui\PersonCard.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        phone.textProperty().bind(Bindings.convert(person.phoneProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        });
    }

    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
```
###### \java\seedu\address\ui\PersonDisplayCard.java
``` java
    /**
     * Resets details shown
     */
    private void resetPersonDetails() {
        nameLarge.textProperty().unbind();
        nameLarge.textProperty().setValue("");
        phoneLarge.textProperty().unbind();
        phoneLarge.textProperty().setValue("");
        birthdayLarge.textProperty().unbind();
        birthdayLarge.textProperty().setValue("");
        emailLarge.textProperty().unbind();
        emailLarge.textProperty().setValue("");
        organisationLarge.textProperty().unbind();
        organisationLarge.textProperty().setValue("");
        remarkLarge.textProperty().unbind();
        remarkLarge.textProperty().setValue("");
        tagsLarge.getChildren().clear();
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        nameLarge.textProperty().bind(Bindings.convert(person.nameProperty()));
        phoneLarge.textProperty().bind(Bindings.convert(person.phoneProperty()));
        birthdayLarge.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        emailLarge.textProperty().bind(Bindings.convert(person.emailProperty()));
        organisationLarge.textProperty().bind(Bindings.convert(person.organisationProperty()));
        remarkLarge.textProperty().bind(Bindings.convert(person.remarkProperty()));
        person.tagProperty().addListener((observable, oldValue, newValue) -> {
            tagsLarge.getChildren().clear();
            person.getTags().forEach(tag -> tagsLarge.getChildren().add(new Label(tag.tagName)));
        });
    }

```
###### \java\seedu\address\ui\PersonDisplayCard.java
``` java
    @Subscribe
    private void handlePersonDeletedEvent(PersonDeletedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        resetPersonDetails();
    }
}
```
###### \resources\view\AddressPanel.fxml
``` fxml
<StackPane xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <VBox fx:id="addressPane" prefHeight="200.0" prefWidth="100.0">
      <children>
         <SplitPane dividerPositions="0.5" maxHeight="100.0" minHeight="100.0" prefHeight="100.0" prefWidth="200.0">
            <items>
               <Label fx:id="addressHeader" maxWidth="110.0" minWidth="110.0" prefWidth="110.0" text="Address :">
                  <padding>
                     <Insets left="5.0" />
                  </padding></Label>
               <Label fx:id="addressLarge">
                  <padding>
                     <Insets left="5.0" />
                  </padding></Label>
            </items>
         </SplitPane>
        <WebView fx:id="browser" minHeight="-Infinity" minWidth="-Infinity" VBox.vgrow="ALWAYS" />
      </children>
   </VBox>
</StackPane>
```
###### \resources\view\MainWindow.fxml
``` fxml
            <SplitPane dividerPositions="0.2" prefWidth="200.0" VBox.vgrow="ALWAYS">
              <items>
                  <StackPane fx:id="detailsPlaceholder" prefHeight="150.0" prefWidth="200.0">
                     <padding>
                        <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                     </padding></StackPane>

                      <StackPane fx:id="addressPlaceholder" prefWidth="400.0">
                  <padding>
                    <Insets bottom="5.0" left="5.0" right="5.0" top="5.0" />
                  </padding>
                </StackPane>
              </items>
            </SplitPane>
         </children>
      </VBox>
  </SplitPane>

  <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\PersonDetailsCard.fxml
``` fxml
<StackPane alignment="TOP_LEFT" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <children>
      <VBox fx:id="detailsPane" prefWidth="160.0">
         <children>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="nameHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Name :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="nameLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="phoneHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Phone :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="phoneLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="birthdayHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Birthday :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="birthdayLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="emailHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Email :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="emailLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
               <items>
                  <Label fx:id="organisationHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Organisation :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="organisationLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
               </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="tagsHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Tags :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <FlowPane fx:id="tagsLarge" alignment="CENTER_LEFT">
                     <padding>
                        <Insets left="5.0" />
                     </padding></FlowPane>
              </items>
            </SplitPane>
            <SplitPane dividerPositions="0.5" prefHeight="160.0" prefWidth="200.0">
              <items>
                  <Label fx:id="remarkHeader" maxWidth="150.0" minWidth="150.0" prefWidth="150.0" text="Remarks :">
                     <padding>
                        <Insets left="5.0" />
                     </padding>
                  </Label>
                  <Label fx:id="remarkLarge">
                     <padding>
                        <Insets left="5.0" />
                     </padding></Label>
              </items>
            </SplitPane>
         </children>
      </VBox>
   </children>
</StackPane>
```
