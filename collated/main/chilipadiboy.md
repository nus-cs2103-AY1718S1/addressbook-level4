# chilipadiboy
###### \java\seedu\address\commons\events\ui\ShowBirthdayAlarmRequestEvent.java
``` java
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the Birthday Alarm page
 */
public class ShowBirthdayAlarmRequestEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\logic\commands\BirthdayAlarmCommand.java
``` java
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ShowBirthdayAlarmRequestEvent;

/**
 * Opens up the Reminders Panel
 */
public class BirthdayAlarmCommand extends Command {

    public static final String COMMAND_WORD = "reminders";
    public static final String COMMAND_SHORT = "rem";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows the reminders window.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_REMINDERS_MESSAGE = "Opened reminders window.";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ShowBirthdayAlarmRequestEvent());
        return new CommandResult(SHOWING_REMINDERS_MESSAGE);
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommand.java
``` java
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_REMARK + "[REMARK]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REMARK + "Likes to swim.";

    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Remark remark;

    /**
     * @param index of the person in the filtered person list to edit the remark
     * @param remark of the person
     */
    public RemarkCommand(Index index, Remark remark) {
        requireNonNull(index);
        requireNonNull(remark);

        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getBirthday(), remark, personToEdit.getWebsite(),
                personToEdit.getPicture(), personToEdit.getTags());
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generate success message based on the person edited
     * @param personToEdit
     * @return message whether remark is added or deleted
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!remark.value.isEmpty()) {
            return String.format(MESSAGE_ADD_REMARK_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_REMARK_SUCCESS, personToEdit);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        // state check
        RemarkCommand e = (RemarkCommand) other;
        return index.equals(e.index)
                && remark.equals(e.remark);
    }
}
```
###### \java\seedu\address\logic\Logic.java
``` java
    /**
     *Returns an unmodifiable AddressBook
     */
    ReadOnlyAddressBook getReadOnlyAddressBook();
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    @Override
    public ReadOnlyAddressBook getReadOnlyAddressBook() {
        return model.getAddressBook(); }
}
```
###### \java\seedu\address\logic\parser\AddressBookParser.java
``` java
        case BirthdayAlarmCommand.COMMAND_WORD:
        case BirthdayAlarmCommand.COMMAND_SHORT:
            return new BirthdayAlarmCommand();
```
###### \java\seedu\address\logic\parser\CliSyntax.java
``` java
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_BIRTHDAY = new Prefix("b/");
```
###### \java\seedu\address\model\person\Birthday.java
``` java
package seedu.address.model.person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
 */
public class Birthday {
    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Person Birthday should be in the format of DD/MM/YYYY and also valid";

    public final String value;

    /**
     * Validates given birthday.
     *
     * @throws IllegalValueException if given address string is invalid.
     */
    public Birthday(String birthday) throws IllegalValueException {
        String trimmedBirthday = birthday == null ? null : birthday.trim();
        if (!isValidBirthday(trimmedBirthday)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        this.value = trimmedBirthday;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    static boolean isValidBirthday(String test) {
        if (test == null) {
            return true;
        }

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);
        try {
            Date strToDate = format.parse(test);      //tries to parse provided string in given format
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setBirthday(Birthday birthday) {
        this.birthday.set(requireNonNull(birthday));
    }

    @Override
    public ObjectProperty<Birthday> birthdayProperty() {
        return birthday;
    }

    @Override
    public Birthday getBirthday() {
        return birthday.get();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void setRemark(Remark remark) {
        this.remark.set(requireNonNull(remark));
    }

    @Override
    public ObjectProperty<Remark> remarkProperty() {
        return remark;
    }

    @Override
    public Remark getRemark() {
        return remark.get();
    }
```
###### \java\seedu\address\model\person\Person.java
``` java
    public void resetData(ReadOnlyPerson replacement) {
        requireNonNull(replacement);

        this.setName(replacement.getName());
        this.setPhone(replacement.getPhone());
        this.setEmail(replacement.getEmail());
        this.setAddress(replacement.getAddress());
        this.setRemark(replacement.getRemark());
        this.setWebsite(replacement.getWebsite());
        this.setPicture(replacement.getPicture());
        this.setTags(replacement.getTags());
    }
```
###### \java\seedu\address\model\person\Person.java
``` java

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReadOnlyPerson // instanceof handles nulls
            && this.isSameStateAs((ReadOnlyPerson) other));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, remark, website, picture, tags);
    }

    @Override
    public String toString() {
        return getAsText();
    }
}
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                      && other.getAddress().equals(this.getAddress())
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
                      && other.getRemark().equals(this.getRemark()));
    }

    /**
     * Formats the person as text, showing all contact details.
     */
    default String getAsText() {
```
###### \java\seedu\address\model\person\ReadOnlyPerson.java
``` java
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getPhone().toString() != null) {
            builder.append(" Phone: ")
                .append(getPhone());
        }

        if (getEmail().toString() != null) {
            builder.append(" Email: ")
                .append(getEmail());
        }
        if (getAddress().toString() != null) {
            builder.append(" Address: ")
                .append(getAddress());
        }
        if (getBirthday().toString() != null) {
            builder.append(" Birthday: ")
                .append(getBirthday());
        }
        builder.append(" Remarks: ")
            .append(getRemark());
        if (getWebsite().toString() != null) {
            builder.append(" Website: ")
                .append(getWebsite());
        }

        if (getPicture().toString() != null) {
            builder.append(" Picture: ")
                    .append(getPicture());
        }
```
###### \java\seedu\address\model\person\Remark.java
``` java
package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {

    public static final String MESSAGE_REMARK_CONSTRAINTS =
        "Person remarks can take any values, can even be blank";

    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
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
###### \java\seedu\address\ui\BirthdayAlarmWindow.java
``` java
package seedu.address.ui;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Controller for Birthday Alarm
 */
public class BirthdayAlarmWindow extends UiPart<Region> implements Initializable {
    private static final String FXML = "BirthdayAlarmWindow.fxml";
    private static final String TITLE = "Birthday Alarm";
    private final Logger logger = LogsCenter.getLogger(BirthdayAlarmWindow.class);


    @FXML
    private TableView<ReadOnlyPerson> BirthdayTable;
    @FXML
    private TableColumn<ReadOnlyPerson , String> NameColumn;
    @FXML
    private TableColumn<ReadOnlyPerson, String> BirthdayColumn;

    private final Stage dialogStage;

    public BirthdayAlarmWindow(ReadOnlyAddressBook ab) throws ParseException {
        super(FXML);
        ObservableList<ReadOnlyPerson> pl = FXCollections.observableArrayList();
        Scene scene = new Scene(getRoot());
        //Null passed as the parent stage to make it non-modal.
        dialogStage = createDialogStage(TITLE, null, scene);
        dialogStage.setResizable(true);
        pl.addAll(ab.getPersonList());
        for (int i = pl.size() - 1; i >= 0; i--) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate storedDate = LocalDate.parse(pl.get(i).getBirthday().value, dtf);
            LocalDate currentDate = LocalDate.now();
            int bdayMonth = storedDate.getMonthValue();
            int currentMonth = currentDate.getMonthValue();
            if (bdayMonth < currentMonth) {
                pl.remove(i); //removes entry before current month
            }

        }

        FilteredList<ReadOnlyPerson> fd = new FilteredList(pl);
        SortedList<ReadOnlyPerson> sl = new SortedList<>(fd);
        BirthdayTable.setItems(sl);
        sl.comparatorProperty().bind(BirthdayTable.comparatorProperty());
    }

    /**
     * Shows the reminders window.
     *
     * @throws IllegalStateException <ul>
     *                               <li>
     *                               if this method is called on a thread other than the JavaFX Application Thread.
     *                               </li>
     *                               <li>
     *                               if this method is called during animation or layout processing.
     *                               </li>
     *                               <li>
     *                               if this method is called on the primary stage.
     *                               </li>
     *                               <li>
     *                               if {@code dialogStage} is already showing.
     *                               </li>
     *                               </ul>
     */
    public void show() {
        logger.fine("Showing Birthday Alarm Page");
        dialogStage.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set up columns
        NameColumn.setCellValueFactory(new PropertyValueFactory<ReadOnlyPerson, String>("Name"));
        BirthdayColumn.setCellValueFactory(new PropertyValueFactory<ReadOnlyPerson, String>("Birthday"));
    }
}

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @FXML
    private void handlebirthdayalarms() throws ParseException {
        BirthdayAlarmWindow birthdayAlarmWindow = new BirthdayAlarmWindow(logic.getReadOnlyAddressBook());
        birthdayAlarmWindow.show();
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    @Subscribe
    private void handleShowBirthdayAlarmEvent (ShowBirthdayAlarmRequestEvent event) throws ParseException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handlebirthdayalarms();
    }
}
```
###### \java\seedu\address\ui\PersonCard.java
``` java
    @FXML
    private Label birthday;
    @FXML
    private Label remark;
```
###### \java\seedu\address\ui\PersonCard.java
``` java
        birthday.textProperty().bind(Bindings.convert(person.birthdayProperty()));
        remark.textProperty().bind(Bindings.convert(person.remarkProperty()));
```
###### \resources\view\BirthdayAlarmWindow.fxml
``` fxml

<?import javafx.scene.control.TableColumn?>
<?import javafx.scene.control.TableView?>
<?import javafx.scene.control.TitledPane?>
<TitledPane fx:id="BirthdayTableWindowroot" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="400.0" prefWidth="600.0" text="Birthdays" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <content>
      <TableView fx:id="BirthdayTable" prefHeight="200.0" prefWidth="200.0">
        <columns>
          <TableColumn fx:id="NameColumn" editable="false" sortable="true" prefWidth="300.0" text="Name" />
          <TableColumn fx:id="BirthdayColumn" editable="false" sortable="true" sortType="DESCENDING" prefWidth="300.0" text="Birthday" />
        </columns>
      </TableView>
   </content>
</TitledPane>
```
