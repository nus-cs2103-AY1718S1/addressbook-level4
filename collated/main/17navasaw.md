# 17navasaw
###### \java\seedu\address\commons\core\index\Index.java
``` java
    @Override
    public int compareTo(Object o) {
        Index compareIndex = (Index) o;

        return (compareIndex).getZeroBased() - this.zeroBasedIndex;
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    private final ArrayList<Index> targetIndices;

    public DeleteCommand(ArrayList<Index> targetIndices) {
        Collections.sort(targetIndices);
        this.targetIndices = targetIndices;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (int i = 0; i < targetIndices.size(); i++) {
            Index targetIndex = targetIndices.get(i);

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());

            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(MESSAGE_DELETE_PERSON_SUCCESS);
    }

```
###### \java\seedu\address\logic\commands\EditCommand.java
``` java
        public void setEmails(Set<Email> emails) {
            this.emails = emails;
        }

        public Optional<Set<Email>> getEmail() {
            return Optional.ofNullable(emails);
        }

```
###### \java\seedu\address\logic\commands\LocateCommand.java
``` java
/**
 * Locates a person's address by showing its location on Google Maps.
 */
public class LocateCommand extends Command {

    public static final String COMMAND_WORD = "locate";
    public static final String COMMAND_ALIAS = "lc";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ": Locates the address of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Located the Address of Person %1$s";

    private final Index targetIndex;

```
###### \java\seedu\address\logic\parser\DeleteCommandParser.java
``` java
    public DeleteCommand parse(String args) throws ParseException {
        String invalidCommandString = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);
        try {
            String trimmedArgs = args.trim();
            String[] indicesInString = trimmedArgs.split("\\s+");

            ArrayList<Index> indices = new ArrayList<>();
            for (int i = 0; i < indicesInString.length; i++) {
                Index index = ParserUtil.parseIndex(indicesInString[i]);

                // Check if there are repeated indices
                if (i >= 1) {
                    for (Index indexInList: indices) {
                        if (indexInList.equals(index)) {
                            throw new ParseException(invalidCommandString);
                        }
                    }
                }
                indices.add(index);
            }

            return new DeleteCommand(indices);
        } catch (IllegalValueException ive) {
            throw new ParseException(invalidCommandString);
        }
    }

}
```
###### \java\seedu\address\logic\parser\EditCommandParser.java
``` java
    /**
     * Parses {@code Collection<String> emails} into a {@code Set<Email>} if {@code emails} is non-empty.
     * If {@code emails} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Email>} containing zero emails.
     */
    private Optional<Set<Email>> parseEmailsForEdit(Collection<String> emails) throws IllegalValueException {
        assert emails != null;

        if (emails.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> emailSet = emails.size() == 1 && emails.contains("") ? Collections.emptySet() : emails;
        return Optional.of(ParserUtil.parseEmails(emailSet));
    }

```
###### \java\seedu\address\logic\parser\LocateCommandParser.java
``` java
/**
 * Parses input arguments and creates a new LocateCommand object
 */
public class LocateCommandParser implements Parser<LocateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LocateCommand
     * and returns an LocateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * * Parses a {@code Optional<String> email} into an {@code Optional<Email>} if {@code email} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Set<Email> parseEmails(Collection<String> emails) throws IllegalValueException {
        requireNonNull(emails);
        final Set<Email> emailSet = new HashSet<>();
        for (String emailName : emails) {
            emailSet.add(new Email(emailName));
        }
        return emailSet;
    }

```
###### \java\seedu\address\model\person\address\Block.java
``` java
package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the block in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidBlock(String)}
 */
public class Block {

    public static final String MESSAGE_BLOCK_CONSTRAINTS =
            "Block can only contain numbers or alphabets, and should be at least 1 character long";
    private static final String BLOCK_VALIDATION_REGEX = "\\w{1,}";
    public final String value;

    public Block(String block) throws IllegalValueException {

        requireNonNull(block);
        String trimmedBlock = block.trim();

        if (!isValidBlock(trimmedBlock)) {
            throw new IllegalValueException(MESSAGE_BLOCK_CONSTRAINTS);
        }
        this.value = trimmedBlock;
    }

    /**
     * Returns true if a given string is a valid block in an address.
     */
    public static boolean isValidBlock(String test) {
        return test.matches(BLOCK_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\address\PostalCode.java
``` java
package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the postal code in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {

    public static final String MESSAGE_POSTALCODE_CONSTRAINTS =
            "Postal Code can contain alphanumeric characters and spaces, and it should not be blank";
    private static final String POSTALCODE_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();

        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTALCODE_CONSTRAINTS);
        }
        this.value = trimmedPostalCode;
    }

    /**
     * Returns true if a given string is a valid postal code in an address.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTALCODE_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\address\Street.java
``` java
package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the street in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidStreet(String)}
 */
public class Street {
    public static final String MESSAGE_STREET_CONSTRAINTS =
            "Street can contain alphanumeric characters and spaces, and it should not be blank";
    private static final String STREET_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    public Street(String street) throws IllegalValueException {
        requireNonNull(street);
        String trimmedStreet = street.trim();

        if (!isValidStreet(trimmedStreet)) {
            throw new IllegalValueException(MESSAGE_STREET_CONSTRAINTS);
        }
        this.value = trimmedStreet;
    }

    /**
     * Returns true if a given string is a valid street in an address.
     */
    public static boolean isValidStreet (String test) {
        return test.matches(STREET_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\address\Unit.java
``` java
package seedu.address.model.person.address;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the unit in Person's address.
 * Guarantees: immutable; is valid as declared in {@link #isValidUnit(String)}
 */
public class Unit {
    public static final String MESSAGE_UNIT_CONSTRAINTS =
            "Unit must begin with '#' and be followed by 2 numeric strings separated by '-'";
    private static final String UNIT_VALIDATION_REGEX = "#[\\d\\.]+-[\\d\\.]+";

    public final String value;

    public Unit(String unit) throws IllegalValueException {
        requireNonNull(unit);
        String trimmedUnit = unit.trim();

        if (!isValidUnit(trimmedUnit)) {
            throw new IllegalValueException(MESSAGE_UNIT_CONSTRAINTS);
        }
        this.value = trimmedUnit;
    }

    /**
     * Returns true if a given string is a valid unit in an address.
     */
    public static boolean isValidUnit(String test) {
        return test.matches(UNIT_VALIDATION_REGEX);
    }
}
```
###### \java\seedu\address\model\person\Address.java
``` java
    private Block block;
    private Street street;
    private Unit unit;
    private PostalCode postalCode;

    /**
     * Validates given address.
     *
     * @throws IllegalValueException if given address string follows invalid format.
     */
    public Address(String address) throws IllegalValueException {
        requireNonNull(address);
        String trimmedAddress = address.trim();

        if (!hasValidAddressFormat(trimmedAddress)) {
            throw new IllegalValueException(MESSAGE_ADDRESS_CONSTRAINTS);
        }

        splitAddressString(trimmedAddress);

        this.value = trimmedAddress;
    }

    /**
     * Splits Address into Block, Street, Unit[optional parameter] and PostalCode.
     */
    private void splitAddressString(String trimmedAddress) throws IllegalValueException {
        String[] tokens = trimmedAddress.split(",");
        int numTokens = tokens.length;

        assert ((numTokens == 3) || (numTokens == 4)) : "The address should be split into 3 or 4 tokens.";

        block = new Block(tokens[0]);
        street = new Street(tokens[1]);

        if (numTokens == 3) {

            postalCode = new PostalCode(tokens[2]);

        } else if (numTokens == 4) {

            unit = new Unit (tokens[2]);
            postalCode = new PostalCode(tokens[3]);
        }
    }

    /**
     * Returns true if a given string follows the valid format for address.
     */
    public static boolean hasValidAddressFormat(String test) {
        StringTokenizer tokenizer = new StringTokenizer(test, ADDRESS_FORMAT_DELIMITER);

        return ((tokenizer.countTokens() == 3) || (tokenizer.countTokens() == 4));
    }

```
###### \java\seedu\address\model\person\email\UniqueEmailList.java
``` java
package seedu.address.model.person.email;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.CollectionUtil;

/**
 * A list of emails that enforces no nulls and uniqueness between its elements.
 *
 * Supports minimal set of list operations for the app's features.
 *
 * @see Email#equals(Object)
 */
public class UniqueEmailList {

    private final ObservableList<Email> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty EmailList.
     */
    public UniqueEmailList() {}

    /**
     * Creates a UniqueEmailList using given tags.
     * Enforces no nulls.
     */
    public UniqueEmailList(Set<Email> emails) {
        requireAllNonNull(emails);
        internalList.addAll(emails);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all Emails in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Email> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Email> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueEmailList // instanceof handles nulls
                && this.internalList.equals(((UniqueEmailList) other).internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }

}

```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
    * Replaces this person's emails with the emails in the argument tag set.
    */
    public void setEmails(Set<Email> replacement) {
        emails.set(new UniqueEmailList(replacement));
    }

    @Override
    public ObjectProperty<UniqueEmailList> emailProperty() {
        return emails;
    }

    /**
     * Returns an immutable email set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    @Override
    public Set<Email> getEmails() {
        return Collections.unmodifiableSet(emails.get().toSet());
    }

```
###### \java\seedu\address\model\schedule\Schedule.java
``` java
    public void setActivity(Activity activity) {
        this.activity.set(activity);
    }

    public Name getPersonInvolvedName() {
        return personInvolvedName.get();
    }

    public ObjectProperty<Name> getPersonInvolvedNameProperty() {
        return personInvolvedName;
    }

    public void setPersonInvolvedName(Name personInvolvedName) {
        this.personInvolvedName.set(personInvolvedName);
    }

```
###### \java\seedu\address\model\schedule\UniqueScheduleList.java
``` java
    /**
     * Ensures every schedule in the argument list exists in this object.
     */
    public void mergeFrom(UniqueScheduleList from) {
        final Set<Schedule> alreadyInside = this.toSet();

        for (Schedule scheduleFrom : from.internalList) {
            int flag = 1;
            for (Schedule scheduleInside : alreadyInside) {
                if (scheduleFrom.equals(scheduleInside)) {
                    flag = 0;
                    break;
                }
            }
            if (flag == 1) {
                this.internalList.add(scheduleFrom);
            }
        }
        assert CollectionUtil.elementsAreUnique(internalList);
    }

```
###### \java\seedu\address\model\schedule\UniqueScheduleList.java
``` java
    /**
     * Sorts the list from earliest to latest schedule.
     */
    public void sort() {
        FXCollections.sort(internalList, (schedule1, schedule2) -> {
            String schedule1DateInString = schedule1.getScheduleDate().value;
            String schedule2DateInString = schedule2.getScheduleDate().value;

            int schedule1Year = DateUtil.getYear(schedule1DateInString);
            int schedule2Year = DateUtil.getYear(schedule2DateInString);
            if (schedule1Year != schedule2Year) {
                return schedule1Year - schedule2Year;
            }

            int schedule1Month = DateUtil.getMonth(schedule1DateInString);
            int schedule2Month = DateUtil.getMonth(schedule2DateInString);
            if (schedule1Month != schedule2Month) {
                return schedule1Month - schedule2Month;
            }

            int schedule1Day = DateUtil.getDay(schedule1DateInString);
            int schedule2Day = DateUtil.getDay(schedule2DateInString);
            return schedule1Day - schedule2Day;
        });
    }

```
###### \java\seedu\address\model\util\SampleDataUtil.java
``` java
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Email> getEmailSet(String... strings) throws IllegalValueException {
        HashSet<Email> emails = new HashSet<>();
        for (String s : strings) {
            emails.add(new Email(s));
        }

        return emails;
    }

```
###### \java\seedu\address\storage\XmlAdaptedEmail.java
``` java
package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.email.Email;

/**
 * JAXB-friendly adapted version of the Email.
 */
public class XmlAdaptedEmail {
    @XmlValue
    private String emailName;

    /**
     * Constructs an XmlAdaptedEmail.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEmail() {}

    /**
     * Converts a given Email into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedEmail(Email source) {
        emailName = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Email object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Email toModelType() throws IllegalValueException {
        return new Email(emailName);
    }
}
```
###### \java\seedu\address\storage\XmlAdaptedSchedule.java
``` java
    public XmlAdaptedSchedule(Schedule source) {
        ScheduleDate scheduleDate = source.getScheduleDate();
        Activity activity = source.getActivity();
        Name personInvolvedName = source.getPersonInvolvedName();

        schedule = "Date: " + scheduleDate.toString() + " Activity: " + activity.toString()
                + " Person: " + personInvolvedName.toString();
    }

```
###### \java\seedu\address\ui\AgendaPanel.java
``` java
package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.schedule.Schedule;

/**
 * Panel containing the list of schedules.
 */
public class AgendaPanel extends UiPart<Region> {
    private static final String FXML = "AgendaPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AgendaPanel.class);

    @FXML
    private ListView<ScheduleCard> scheduleCardListView;

    public AgendaPanel(ObservableList<Schedule> scheduleList) {
        super(FXML);
        setConnections(scheduleList);
        registerAsAnEventHandler(this);
    }

    /**
     * Creates a list of {@code ScheduleCard} from {@code scheduleList}, sets them to the {@code scheduleCardListView}
     * and adds listener to {@code scheduleCardListView} for selection change.
     */
    private void setConnections(ObservableList<Schedule> scheduleList) {
        for (Schedule i : scheduleList) {
            logger.info(i.toString() + " Index: " + scheduleList.indexOf(i));
        }
        ObservableList<ScheduleCard> mappedList = EasyBind.map(
                scheduleList, (schedule) -> new ScheduleCard(schedule, scheduleList.indexOf(schedule) + 1));
        scheduleCardListView.setItems(mappedList);
        scheduleCardListView.setCellFactory(listView -> new ScheduleCardListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ScheduleCard}.
     */
    class ScheduleCardListViewCell extends ListCell<ScheduleCard> {

        @Override
        protected void updateItem(ScheduleCard schedule, boolean empty) {
            super.updateItem(schedule, empty);

            if (empty || schedule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(schedule.getRoot());
            }
        }
    }
}
```
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    /**
     * Loads google maps web page locating person's address.
     */
    private void loadPersonPage(ReadOnlyPerson person) {
        Address personAddress = person.getAddress();

        String urlEncodedAddressIntermediate = personAddress.toString().replaceAll("#", "%23");
        String urlEncodedAddressFinal = urlEncodedAddressIntermediate.replaceAll(" ", "+");

        loadPage(GOOGLE_MAPS_URL_PREFIX
                + urlEncodedAddressFinal
                + GOOGLE_MAPS_URL_SUFFIX);
    }

```
###### \java\seedu\address\ui\ScheduleCard.java
``` java
package seedu.address.ui;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.scene.layout.Region;
import seedu.address.model.schedule.Schedule;

/**
 * An UI component that displays information of a {@code Schedule}.
 */
public class ScheduleCard extends UiPart<Region> {
    private static final String FXML = "ScheduleCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Schedule schedule;

    @FXML
    private HBox cardPane;
    @FXML
    private Label activity;
    @FXML
    private Label number;
    @FXML
    private Label date;
    @FXML
    private Label personName;

    public ScheduleCard(Schedule schedule, int displayedIndex) {
        super(FXML);
        this.schedule = schedule;
        number.setText(displayedIndex + ". ");
        bindListeners(schedule);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code Schedule} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(Schedule schedule) {
        activity.textProperty().bind(Bindings.convert(schedule.getActivityProperty()));
        date.textProperty().bind(Bindings.convert(schedule.getScheduleDateProperty()));
        personName.textProperty().bind(Bindings.convert(schedule.getPersonInvolvedNameProperty()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleCard)) {
            return false;
        }

        // state check
        ScheduleCard card = (ScheduleCard) other;
        return number.getText().equals(card.number.getText())
                && schedule.equals(card.schedule);
    }
}
```
###### \resources\view\AgendaPanel.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<VBox stylesheets="@AgendaTheme.css" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
   <Label alignment="CENTER" contentDisplay="TOP" text="My Agenda" textFill="#16afcc">
      <font>
         <Font name="Calibri Bold Italic" size="25.0" />
      </font>
      <VBox.margin>
         <Insets left="60.0" />
      </VBox.margin>
   </Label>
  <ListView fx:id="scheduleCardListView" VBox.vgrow="ALWAYS" />
</VBox>
```
###### \resources\view\AgendaTheme.css
``` css
.background {
    -fx-background-color: derive(#40593c, 20%);
    background-color: #40593c; /* Used in the default.html file */
}

.label {
    -fx-font-size: 14pt;
    -fx-font-family: "Comic Sans MS";
    -fx-text-fill: #00c1a7;
    -fx-opacity: 0.9;
}
```
###### \resources\view\MainWindow.fxml
``` fxml
      <VBox alignment="TOP_RIGHT" minWidth="175.0" prefHeight="20.0" prefWidth="225.0">
         <children>
            <StackPane fx:id="agendaPanelPlaceholder" />
         </children>
      </VBox>
    </SplitPane>

    <StackPane fx:id="statusbarPlaceholder" VBox.vgrow="NEVER" />
</VBox>
```
###### \resources\view\ScheduleCard.fxml
``` fxml
<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.layout.ColumnConstraints?>
<?import javafx.scene.layout.GridPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.Region?>
<?import javafx.scene.layout.RowConstraints?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.text.Font?>

<HBox id="cardPane" fx:id="cardPane" xmlns="http://javafx.com/javafx/8.0.111" xmlns:fx="http://javafx.com/fxml/1">
  <GridPane HBox.hgrow="ALWAYS">
    <columnConstraints>
      <ColumnConstraints hgrow="SOMETIMES" minWidth="10" prefWidth="150" />
    </columnConstraints>
    <VBox alignment="CENTER_LEFT" minHeight="105" GridPane.columnIndex="0">
      <padding>
        <Insets bottom="5" left="15" right="5" top="5" />
      </padding>
      <HBox alignment="CENTER_LEFT" spacing="5">
        <Label fx:id="number" styleClass="cell_big_label">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
        </Label>
        <Label fx:id="activity" styleClass="cell_big_label" text="\\$Activity" />
      </HBox>
      <Label styleClass="cell_small_label" text="Date:">
            <font>
               <Font name="System Bold" size="13.0" />
            </font></Label>
      <Label fx:id="date" styleClass="cell_small_label" text="\\$date" />
         <Label text="Person(s) Involved:">
            <font>
               <Font name="System Bold" size="13.0" />
            </font></Label>
         <Label fx:id="personName" text="\\$person" />
    </VBox>
      <rowConstraints>
         <RowConstraints />
      </rowConstraints>
  </GridPane>
</HBox>
```
