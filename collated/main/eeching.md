# eeching
###### \java\seedu\address\logic\commands\PhoneCommand.java
``` java
/**
 * Adds or updates a custom field of a person identified using it's last displayed index from the address book.
 */
public class PhoneCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "updatePhone";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates one person's additional phone identified by the index"
            + " number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "ACTION \n"
            + "VALUE"
            + "Example: " + COMMAND_WORD + " 1" + " add" + " 6583609887";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final Phone phone;

    private final String action;


    public PhoneCommand(Index targetIndex, String action,  Phone phone) {
        this.targetIndex = targetIndex;
        this.action = action;
        this.phone = phone;
    }

    /**
     * Adds or Updates a Person's phoneNumber list
     */
    private Person updatePersonPhoneList(ReadOnlyPerson personToUpdatePhoneList, String action, Phone phone) {
        Name name = personToUpdatePhoneList.getName();
        Phone primaryPhone = personToUpdatePhoneList.getPhone();
        UniquePhoneList uniquePhoneList = personToUpdatePhoneList.getPhoneList();
        Email email = personToUpdatePhoneList.getEmail();
        Address address = personToUpdatePhoneList.getAddress();
        Photo photo = personToUpdatePhoneList.getPhoto();
        Set<Tag> tags = personToUpdatePhoneList.getTags();
        UniqueCustomFieldList customFields = personToUpdatePhoneList.getCustomFieldList();

        if (action.equals("remove")) {
            try {
                uniquePhoneList.remove(phone);
            } catch (PhoneNotFoundException e) {
                throw new AssertionError("phone number cannot be found");
            }
        } else if (action.equals("add")) {
            try {
                uniquePhoneList.add(phone);
            } catch (DuplicatePhoneException e) {
                throw new AssertionError("number adding already in the list");
            }
        } else if (action.equals("showAllPhones")) {

        }

        Person personUpdated = new Person(name, primaryPhone, email, address,
                photo, uniquePhoneList, tags, customFields.toSet());

        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdatePhoneList = lastShownList.get(targetIndex.getZeroBased());
        Person personUpdated = updatePersonPhoneList(personToUpdatePhoneList, action, phone);
        UniquePhoneList uniquePhoneList = personUpdated.getPhoneList();
        Phone primaryPhone = personUpdated.getPhone();
        try {
            model.updatePerson(personToUpdatePhoneList, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (action.equals("showAllPhones")) {
            String str = "The primary number is " + primaryPhone + "\n";
            return new CommandResult(str + uniquePhoneList.getAllPhone());
        } else if (action.equals("add")) {
            String successMessage = "Phone number " + phone.number + " has been added, ";
            String info = "the updated phone list now has " + (uniquePhoneList.getSize() + 1)
                    + " phone numbers, and the primary phone number is " + primaryPhone;
            return new CommandResult(successMessage + info);
        } else {
            String successMessage = "Phone number " + phone.number + " has been removed, ";
            String info = "the updated phone list now has " + (uniquePhoneList.getSize() + 1)
                    + " phone numbers, and the primary phone number is " + primaryPhone;
            return new CommandResult(successMessage + info);
        }
    }
}
```
###### \java\seedu\address\logic\parser\PhoneCommandParser.java
``` java
/**
 * Parses input arguments and creates a new object
 */
public class PhoneCommandParser implements Parser<PhoneCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PhoneCommand
     * and returns a PhoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public PhoneCommand parse(String args) throws ParseException {
        try {
            StringTokenizer st = new StringTokenizer(args);
            Index index = ParserUtil.parseIndex(st.nextToken());
            String action = st.nextToken();
            String value = "00000";
            if (st.hasMoreTokens()) {
                value = st.nextToken();
            }

            Phone phone = new Phone(value);
            return new PhoneCommand(index, action, phone);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
        }
    }

}
```
###### \java\seedu\address\model\person\Birthday.java
``` java
/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable;
 */

public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person birthday should be in the format dd/mm/yyyy, default is 00/00/0000";


    public static final String BIRTHDAY_VALIDATION_REGEX =
            "^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$";

    public final String value;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        requireNonNull(birthday);
        if (!isValidBirthday(birthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = birthday;
    }

    /**
     * Returns true if a given string is a valid person email.
     */
    public static boolean isValidBirthday(String test) {
        return test.matches(BIRTHDAY_VALIDATION_REGEX);
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
###### \java\seedu\address\model\person\exceptions\DuplicatePhoneException.java
``` java
/**
 * Signals that the operation will result in duplicate Phone objects.
 */
public class DuplicatePhoneException extends DuplicateDataException {
    public DuplicatePhoneException() {
        super("Operation would result in duplicate phones");
    }
}
```
###### \java\seedu\address\model\person\exceptions\NoLocalNumberException.java
``` java
/**
 * Signals that the operation will result in duplicate Phone objects.
 */

public class NoLocalNumberException extends Exception {
    public NoLocalNumberException() {
        super("no local number added");
    }
}
```
###### \java\seedu\address\model\person\exceptions\PhoneNotFoundException.java
``` java
/**
 * Signals that the operation is unable to find the specified phone.
 */
public class PhoneNotFoundException extends Exception {}
```
###### \java\seedu\address\model\person\phone\UniquePhoneList.java
``` java
/**
 * A list of phones that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Phone#equals(Object)
 */
public class UniquePhoneList implements Iterable<Phone> {

    private final ObservableList<Phone> internalList = FXCollections.observableArrayList();

    /**
     * Constructs phoneList with a number.
     */

    public UniquePhoneList() {}

    public UniquePhoneList(Phone phone) {

        requireNonNull(phone);
        internalList.add(phone);
    }

```
###### \java\seedu\address\model\person\phone\UniquePhoneList.java
``` java
    /**
     * Returns true if the list contains an equivalent phone as the given argument.
     */
    public boolean contains(Phone toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a person to the list.
     *
     * @throws DuplicatePhoneException if the person to add is a duplicate of an existing person in the list.
     */
    public void add(Phone toAdd) throws DuplicatePhoneException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePhoneException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent person from the list.
     *
     * @throws PhoneNotFoundException if no such person could be found in the list.
     */
    public boolean remove(Phone toRemove) throws PhoneNotFoundException {
        requireNonNull(toRemove);
        final boolean phoneFoundAndDeleted = internalList.remove(toRemove);
        if (!phoneFoundAndDeleted) {
            throw new PhoneNotFoundException();
        }
        return phoneFoundAndDeleted;
    }


    public int getSize() {
        return internalList.size();
    }


    public String getAllPhone() {

        if (internalList.size() > 1) {
            String rest = "The additional phone number(s) are/is \n";
            int index = 1;
            for (Phone phone: internalList) {
                rest = rest + index + "/ " + phone.number + "\n";
                index++;
            }
            return rest;
        } else {
            return "";
        }
    }


    @Override
    public Iterator<Phone> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Phone> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.person.phone.UniquePhoneList // instanceof handles nulls
                && this.internalList.equals(((seedu.address.model.person.phone.UniquePhoneList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(seedu.address.model.person.phone.UniquePhoneList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}
```
###### \resources\view\LightTheme.css
``` css
/**
 * Shared
 */
.root * {
    midLight: derive(white, -70%);
    -fx-font-family: "Helvetica";
    -fx-background-color: transparent;
    -fx-text-fill: black;
}

.scroll-bar .thumb {
    -fx-background-color: derive(grey, -20%);
}


/**
 * Background
 */
.background {
    -fx-background-color: derive(grey, -20%);
    background-color: #f2f2f2; /* Used in the default.html file */
    background-image: url("../images/bg_01.jpg");
    background-size: 550px;
    background-position: center 10px;
    background-repeat: no-repeat;
}


/**
 * Menu Bar
 */
.context-menu {
    -fx-background-color: derive(grey, 90%);
}

.context-menu .label {
    -fx-text-fill: black;
}

.menu-bar {
    -fx-background-color: #fafafa;
}

.menu-bar .label {
    -fx-font-size: 14pt;
    -fx-text-fill: black;
    -fx-opacity: 0.9;
}


/**
 * Command Box
 */
#commandTextField {
    -fx-font-size: 20pt;
    -fx-font-weight: bold;
    -fx-border-width: 1;
}


/**
 * Result Display
 */
#resultDisplay .content {
    -fx-background-color: #fafafa;
    -fx-background-radius: 5;
    /*
    -fx-border-color: transparent transparent black transparent;
    */
}


/**
 * Person List
 */
#personListVBox #name, #personListVBox #id{
    -fx-font-size: 15pt;
}

#personListVBox .scroll-bar:horizontal .thumb {
   /*
    -fx-background-color: transparent;
    */
    -fx-background-color: blue;
}

/**
 * List Cell
 */
.list-cell:filled:odd {
    -fx-background-color: #fafafa;
}

.list-cell:filled:even {
    -fx-background-color: #f2f2f2;
}

.list-cell:filled:hover {
    -fx-background-color: rgba(192, 192, 192, 5);
}

.list-cell:filled:selected {
    -fx-background-color: rgba(192, 192, 192, 10);
}


/**
 * Cell
 */
.cell_big_label {
    -fx-font-size: 20px;
    -fx-text-fill: black;
    -fx-font-weight: normal;
}

.cell_small_label {
    -fx-font-size: 13px;
    -fx-text-fill: #424242;
    -fx-font-weight: lighter;
}


/**
 * Status Bar
 */
.status-bar {
    -fx-background-color: #fafafa;
    -fx-text-fill: black;
    -fx-font-weight: bold;
}


/**
 * Tags
 */
#tags {
    -fx-hgap: 7;
    -fx-vgap: 3;
 }

#tags .label {
    -fx-text-fill: black;
    -fx-padding: 1 3 1 3;
    -fx-border-radius: 2;
    -fx-background-radius: 2;
    -fx-font-size: 11;
    -fx-font-weight: bold;
 }
```
###### \resources\view\MainWindow.fxml
``` fxml

<VBox xmlns="http://javafx.com/javafx/9.0.1" xmlns:fx="http://javafx.com/fxml/1">
  <stylesheets>
    <URL value="@DarkTheme.css" />
    <URL value="@Extensions.css" />
  </stylesheets>
    <SplitPane dividerPositions="0.4" VBox.vgrow="ALWAYS">

<AnchorPane fx:id="topBar" styleClass="menu-bar-container">
    <MenuBar fx:id="menuBar" styleClass="menu-bar" VBox.vgrow="NEVER">
        <Menu mnemonicParsing="false" text="File">
            <MenuItem mnemonicParsing="false" onAction="#handleExit" styleClass="menu-bar-item" text="Exit" />
            <MenuItem fx:id="weatherForecast" mnemonicParsing="false" onAction="#handleWeather" text="Weather Forecast" />
        </Menu>
        <Menu mnemonicParsing="false" text="Help">
           <MenuItem fx:id="helpMenuItem" mnemonicParsing="false" onAction="#handleHelp" styleClass="menu-bar-item" text="Help" />
        </Menu>
    </MenuBar>
</AnchorPane>
        <AnchorPane styleClass="searchBoxContainer">
            <StackPane fx:id="searchBoxPlaceholder" styleClass="pane-with-border" AnchorPane.bottomAnchor="0.0" AnchorPane.leftAnchor="0.0" AnchorPane.rightAnchor="0.0" AnchorPane.topAnchor="0.0">
                <padding>
                    <Insets bottom="5.0" left="10.0" right="10.0" top="5.0" />
                </padding>
            </StackPane>
        </AnchorPane>
    </SplitPane>


   <SplitPane VBox.vgrow="NEVER">
        <StackPane fx:id="commandBoxPlaceholder" styleClass="pane-with-border">
          <padding>
            <Insets bottom="5" left="10" right="10" top="5" />
          </padding>
        </StackPane>
   </SplitPane>

  <StackPane fx:id="resultDisplayPlaceholder" maxHeight="100" minHeight="100" prefHeight="100" styleClass="pane-with-border" VBox.vgrow="NEVER">
  </StackPane>

  <SplitPane id="splitPane" fx:id="splitPane" dividerPositions="0.5" VBox.vgrow="ALWAYS">
    <VBox fx:id="personList" minWidth="340" prefWidth="340" SplitPane.resizableWithParent="false">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
      <StackPane fx:id="personListPanelPlaceholder" VBox.vgrow="ALWAYS" />
    </VBox>
      <GridPane minWidth="1000" prefHeight="650">
      <StackPane fx:id="personInformationPanelPlaceholder" GridPane.columnIndex="1" GridPane.rowIndex="0" />
         <columnConstraints>
            <ColumnConstraints />
            <ColumnConstraints />
         </columnConstraints>
         <rowConstraints>
            <RowConstraints />
         </rowConstraints>

      </GridPane>
<!-- Commented out to remove browser panel
    <StackPane fx:id="browserPlaceholder" prefWidth="340">
      <padding>
        <Insets bottom="10" left="10" right="10" top="10" />
      </padding>
    </StackPane>-->
  </SplitPane>

    <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />

   <stylesheets>
      <URL value="@DarkTheme.css" />
      <URL value="@Extensions.css" />
   </stylesheets>
</VBox>
```
