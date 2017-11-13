# 17navasaw
###### \java\guitests\guihandles\AgendaPanelHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.ScheduleCard;

/**
 * Provides a handle for {@code AgendaPanel} containing the list of {@code ScheduleCard}.
 */
public class AgendaPanelHandle extends NodeHandle<ListView<ScheduleCard>> {
    public static final String SCHEDULE_LIST_VIEW_ID = "#scheduleCardListView";

    private Optional<ScheduleCard> lastRememberedSelectedScheduleCard;

    public AgendaPanelHandle(ListView<ScheduleCard> agendaPanelNode) {
        super(agendaPanelNode);
    }

    /**
     * Returns a handle to the selected {@code ScheduleCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public ScheduleCardHandle getHandleToSelectedCard() {
        List<ScheduleCard> scheduleList = getRootNode().getSelectionModel().getSelectedItems();

        if (scheduleList.size() != 1) {
            throw new AssertionError("Person list size expected 1.");
        }

        return new ScheduleCardHandle(scheduleList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<ScheduleCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the schedule.
     */
    public void navigateToCard(Schedule schedule) {
        List<ScheduleCard> cards = getRootNode().getItems();
        Optional<ScheduleCard> matchingCard = cards.stream().filter(card -> card.schedule.equals(schedule)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the schedule card handle of a schedule associated with the {@code index} in the list.
     */
    public ScheduleCardHandle getScheduleCardHandle(int index) {
        return getScheduleCardHandle(getRootNode().getItems().get(index).schedule);
    }

    /**
     * Returns the {@code ScheduleCardHandle} of the specified {@code schedule} in the list.
     */
    public ScheduleCardHandle getScheduleCardHandle(Schedule schedule) {
        Optional<ScheduleCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.schedule.equals(schedule))
                .map(card -> new ScheduleCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Schedule does not exist."));
    }

    /**
     * Selects the {@code ScheduleCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code ScheduleCard} in the list.
     */
    public void rememberSelectedScheduleCard() {
        List<ScheduleCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedScheduleCard = Optional.empty();
        } else {
            lastRememberedSelectedScheduleCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ScheduleCard} is different from the value remembered by the most recent
     * {@code rememberSelectedScheduleCard()} call.
     */
    public boolean isSelectedScheduleCardChanged() {
        List<ScheduleCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedScheduleCard.isPresent();
        } else {
            return !lastRememberedSelectedScheduleCard.isPresent()
                    || !lastRememberedSelectedScheduleCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\guitests\guihandles\ReminderWindowBottomHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.schedule.Schedule;
import seedu.address.ui.ScheduleCard;

/**
 * Provides a handle for {@code reminderWindowBottom} containing the list of {@code ScheduleCard}.
 */
public class ReminderWindowBottomHandle extends NodeHandle<ListView<ScheduleCard>> {
    public static final String SCHEDULE_LIST_VIEW_ID = "#reminderSchedulesListView";

    //private Optional<ScheduleCard> lastRememberedSelectedScheduleCard;

    public ReminderWindowBottomHandle(ListView<ScheduleCard> reminderWindowBottomNode) {
        super(reminderWindowBottomNode);
    }

    /**
     * Navigates the listview to display and select the schedule.
     */
    public void navigateToCard(Schedule schedule) {
        List<ScheduleCard> cards = getRootNode().getItems();
        Optional<ScheduleCard> matchingCard = cards.stream().filter(card -> card.schedule.equals(schedule)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the schedule card handle of a schedule associated with the {@code index} in the list.
     */
    public ScheduleCardHandle getScheduleCardHandle(int index) {
        return getScheduleCardHandle(getRootNode().getItems().get(index).schedule);
    }

    /**
     * Returns the {@code ScheduleCardHandle} of the specified {@code schedule} in the list.
     */
    public ScheduleCardHandle getScheduleCardHandle(Schedule schedule) {
        Optional<ScheduleCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.schedule.equals(schedule))
                .map(card -> new ScheduleCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Schedule does not exist."));
    }
}
```
###### \java\guitests\guihandles\ScheduleCardHandle.java
``` java
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

/**
 * Provides a handle to a schedule card in the schedule list panel.
 */
public class ScheduleCardHandle extends NodeHandle<Node> {
    private static final String NUMBER_FIELD_ID = "#number";
    private static final String ACTIVITY_FIELD_ID = "#activity";
    private static final String DATE_FIELD_ID = "#date";
    private static final String NAMES_FIELD_ID = "#personNames";

    private final Label numberLabel;
    private final Label activityLabel;
    private final Label dateLabel;
    private final List<Label> nameLabels;

    public ScheduleCardHandle(Node cardNode) {
        super(cardNode);

        this.numberLabel = getChildNode(NUMBER_FIELD_ID);
        this.activityLabel = getChildNode(ACTIVITY_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);

        Region namesContainer = getChildNode(NAMES_FIELD_ID);
        this.nameLabels = namesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getNumber() {
        return numberLabel.getText();
    }

    public List<String> getNames() {
        return nameLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    public String getActivity() {
        return activityLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }
}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String INVALID_ADDRESS_BLOCK_DESC = " "
            + PREFIX_ADDRESS + "$, Cray, 43"; // '$' not allowed in block
    public static final String INVALID_ADDRESS_STREET_DESC = " "
            + PREFIX_ADDRESS + "21, %Cray, 43"; // '%' not allowed in street
    public static final String INVALID_ADDRESS_UNIT_DESC = " "
            + PREFIX_ADDRESS + "2, Cray, #90-@, 90"; // '@' not allowed in unit
    public static final String INVALID_ADDRESS_POSTALCODE_DESC = " "
            + PREFIX_ADDRESS + "1, Cray, $"; // '$' not allowed in postal code

```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void equals() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        DeleteCommand deleteFirstCommand = new DeleteCommand(indices);
        ArrayList<Index> indices2 = new ArrayList<>();
        indices2.add(INDEX_SECOND_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(indices2);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(indices);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Returns a {@code DeleteCommand} with the parameter {@code index}.
     */
    private DeleteCommand prepareCommand(Index index) {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(index);

        UserPrefs prefs = new UserPrefs();
        DeleteCommand deleteCommand = new DeleteCommand(indices);
        deleteCommand.setData(model, prefs, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

```
###### \java\seedu\address\logic\commands\LocateCommandTest.java
``` java
    /**
     * Returns a {@code LocateCommand} with parameters {@code index}.
     */
    private LocateCommand prepareCommand(Index index) {
        UserPrefs prefs = new UserPrefs();
        LocateCommand locateCommand = new LocateCommand(index);
        locateCommand.setData(model, prefs, new CommandHistory(), new UndoRedoStack());
        return locateCommand;
    }
}
```
###### \java\seedu\address\logic\commands\ScheduleCommandTest.java
``` java
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullSchedule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);

        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);

        new ScheduleCommand(null, null, null);
        new ScheduleCommand(indices, null, null);
    }

    @Test
    public void execute_addValidScheduleSuccessful() throws Exception {
        Schedule validSchedule = new ScheduleBuilder().build();
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_PERSON);

        CommandResult commandResult = getScheduleCommandForPerson(indices, validSchedule, model).execute();

        assertEquals(String.format(ScheduleCommand.MESSAGE_SCHEDULE_SUCCESS, indices.size()),
                commandResult.feedbackToUser);
    }

    /**
     * Generates a new ScheduleCommand with the details of the given schedule.
     */
    private ScheduleCommand getScheduleCommandForPerson(Set<Index> indices, Schedule validSchedule, Model model) {
        ScheduleCommand command =
                new ScheduleCommand(indices, validSchedule.getScheduleDate(), validSchedule.getActivity());
        command.setData(model, new UserPrefs(), new CommandHistory(), new UndoRedoStack());
        return command;
    }

```
###### \java\seedu\address\logic\commands\UndoCommandTest.java
``` java
    private DeleteCommand deleteCommandOne;
    private DeleteCommand deleteCommandTwo;

    @Before
    public void setUp() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);

        deleteCommandOne = new DeleteCommand(indices);
        deleteCommandTwo = new DeleteCommand(indices);

        deleteCommandOne.setData(model, prefs, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
        deleteCommandTwo.setData(model, prefs, EMPTY_COMMAND_HISTORY, EMPTY_STACK);
    }

```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_invalidValue_failure() {
        // invalid name

        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address format
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid block in address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_BLOCK_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Block.MESSAGE_BLOCK_CONSTRAINTS);

        // invalid street in address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_STREET_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Street.MESSAGE_STREET_CONSTRAINTS);

        // invalid unit in address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_UNIT_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Unit.MESSAGE_UNIT_CONSTRAINTS);

        // invalid postal code in address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_POSTALCODE_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                PostalCode.MESSAGE_POSTALCODE_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        DeleteCommand commandUsingAlias = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);

        assertEquals(new DeleteCommand(indices), command);
        assertEquals(new DeleteCommand(indices), commandUsingAlias);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_locate() throws Exception {
        LocateCommand command = (LocateCommand) parser.parseCommand(
                LocateCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        LocateCommand commandUsingAlias = (LocateCommand) parser.parseCommand(
                LocateCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LocateCommand(INDEX_FIRST_PERSON), command);
        assertEquals(new LocateCommand(INDEX_FIRST_PERSON), commandUsingAlias);
    }

```
###### \java\seedu\address\logic\parser\DeleteCommandParserTest.java
``` java
    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        ArrayList<Index> indices = new ArrayList<>();
        indices.add(INDEX_FIRST_PERSON);
        indices.add(INDEX_SECOND_PERSON);

        assertParseSuccess(parser, "1 2", new DeleteCommand(indices));
    }

```
###### \java\seedu\address\logic\parser\LocateCommandParserTest.java
``` java
public class LocateCommandParserTest {

    private LocateCommandParser parser = new LocateCommandParser();

    @Test
    public void parse_validArgs_returnsLocateCommand() {
        assertParseSuccess(parser, "1", new LocateCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, LocateCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseEmail_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmails(null);
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmails(Arrays.asList(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseEmails(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseEmail_collectionWithValidEmails_returnsEmailSet() throws Exception {
        Set<Email> actualEmailSet = ParserUtil.parseEmails(Arrays.asList(VALID_EMAIL_1, VALID_EMAIL_2));
        Set<Email> expectedEmailSet = new HashSet<>(Arrays.asList(new Email(VALID_EMAIL_1), new Email(VALID_EMAIL_2)));

        assertEquals(expectedEmailSet, actualEmailSet);
    }

```
###### \java\seedu\address\model\person\address\AddressTest.java
``` java
    @Test
    public void hasValidAddressFormat() {
        // invalid address formats
        assertFalse(Address.hasValidAddressFormat("")); // empty string
        assertFalse(Address.hasValidAddressFormat(" ")); // no delimiter present
        assertFalse(Address.hasValidAddressFormat("22,fort road")); // less than 3 tokens
        assertFalse(Address.hasValidAddressFormat("22,fort road,,")); // 2 tokens with consecutive commas
        assertFalse(Address.hasValidAddressFormat(",,,")); // no characters between commas
        assertFalse(Address.hasValidAddressFormat("22,fort road,#08-01,Singapore,439099")); // more than 4 tokens

        // valid address formats
        assertTrue(Address.hasValidAddressFormat("Blk 456, Den Road, Singapore 409999")); // 3 tokens
        assertTrue(Address.hasValidAddressFormat("Blk 456, Den Road, #01-355, Singapore 409999")); // 4 tokens
        assertTrue(Address.hasValidAddressFormat(" , , , ")); // whitespaces between commas
        assertTrue(Address.hasValidAddressFormat("Leng Inc, 1234 Market St, San Francisco CA 2349879"));
    }
}
```
###### \java\seedu\address\model\person\address\BlockTest.java
``` java
package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BlockTest {

    @Test
    public void isValidBlock() {
        // invalid blocks
        assertFalse(Block.isValidBlock(""));        // empty string
        assertFalse(Block.isValidBlock("-23"));     // unaccepted symbol
        assertFalse(Block.isValidBlock(" "));       // whitespace

        // valid blocks
        assertTrue(Block.isValidBlock("23A"));      // numbers and alphabets with no whitespaces
        assertTrue(Block.isValidBlock("23"));       // numbers only
        assertTrue(Block.isValidBlock("A"));        // alphabets only
        assertTrue(Block.isValidBlock("23 Alpha")); // numbers and alphabets with whitespace
    }

}
```
###### \java\seedu\address\model\person\address\PostalCodeTest.java
``` java
package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PostalCodeTest {

    @Test
    public void isValidPostalCode() {
        // invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("^")); // only non-alphanumeric characters
        assertFalse(PostalCode.isValidPostalCode("St. Garcia")); // contains non-alphanumeric characters

        // valid postal codes
        assertTrue(PostalCode.isValidPostalCode("mexico city")); // alphabets only
        assertTrue(PostalCode.isValidPostalCode("12345")); // numbers only
        assertTrue(PostalCode.isValidPostalCode("Singapore 439099")); // alphanumeric characters
        assertTrue(PostalCode.isValidPostalCode("Singapore")); // with capital letters
        assertTrue(PostalCode.isValidPostalCode("St Vincent and the Grenadines")); // long names
    }

}
```
###### \java\seedu\address\model\person\address\StreetTest.java
``` java
package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StreetTest {

    @Test
    public void isValidStreet() {
        // invalid streets
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("^")); // only non-alphanumeric characters
        assertFalse(PostalCode.isValidPostalCode("St. Patrick's Road")); // contains non-alphanumeric characters

        // valid street
        assertTrue(PostalCode.isValidPostalCode("watermelon lane")); // alphabets only
        assertTrue(PostalCode.isValidPostalCode("12345")); // numbers only
        assertTrue(PostalCode.isValidPostalCode("23rd st")); // alphanumeric characters
        assertTrue(PostalCode.isValidPostalCode("Farquhar Lane")); // with capital letters
        assertTrue(PostalCode.isValidPostalCode("Riverside Village of Church Creek")); // long names
    }
}
```
###### \java\seedu\address\model\person\address\UnitTest.java
``` java
package seedu.address.model.person.address;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UnitTest {

    @Test
    public void isValidUnit() {
        // invalid units
        assertFalse(Unit.isValidUnit(""));
        assertFalse(Unit.isValidUnit("#-00"));
        assertFalse(Unit.isValidUnit("#0-"));
        assertFalse(Unit.isValidUnit("#A"));
        assertFalse(Unit.isValidUnit("#1-A"));
        assertFalse(Unit.isValidUnit("#1- 0"));

        // valid units
        assertTrue(Unit.isValidUnit("#03-01"));
        assertTrue(Unit.isValidUnit("#0-0"));
    }
}
```
###### \java\seedu\address\model\UniqueEmailListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.email.UniqueEmailList;


public class UniqueEmailListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEmailList uniqueEmailList = new UniqueEmailList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEmailList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\model\UniquePersonNameListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.person.UniquePersonNameList;

public class UniquePersonNameListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniquePersonNameList uniquePersonNameList = new UniquePersonNameList();
        thrown.expect(UnsupportedOperationException.class);
        uniquePersonNameList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Parses the {@code emails} into a {@code Set<Email>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withEmails(String... emails) {
        try {
            descriptor.setEmails(ParserUtil.parseEmails(Arrays.asList(emails)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("emails are expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Parses the {@code emails} into a {@code Set<Email>} and sets it to the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String... emails) {
        try {
            this.person.setEmails(SampleDataUtil.getEmailSet(emails));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("emails are expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\testutil\ScheduleBuilder.java
``` java
    /**
     * Sets the {@code ScheduleDate} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withScheduleDate(String scheduleDate) {
        try {
            schedule = new Schedule(new ScheduleDate(scheduleDate), schedule.getActivity(),
                    schedule.getPersonInvolvedNames());
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("schedule date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Activity} of the {@code Schedule} that we are building.
     */
    public ScheduleBuilder withActivity(String activity) {
        try {
            schedule = new Schedule(schedule.getScheduleDate(), new Activity(activity),
                    schedule.getPersonInvolvedNames());
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("activity is expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111, Singapore 409999").withEmail("alice@example.com")
            .withPhone("86355255")
            .withTags("teachers").build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25, Singapore 807777")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("15, wall street, sv 0000").build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@google.com").withAddress("7, Bishan road, 706875").build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("14, michegan ave, il 805").build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("A, little tokyo, tk 24").build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("16, 4th street, some country").build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("14, little india, singapore 706754").build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("15, chicago ave, il 507").build();

```
###### \java\seedu\address\ui\AgendaPanelTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.AgendaPanelHandle;
import guitests.guihandles.ScheduleCardHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;



public class AgendaPanelTest extends GuiUnitTest {
    private static final List<Schedule> scheduleList = Arrays.asList(new ScheduleBuilder().build(),
            new ScheduleBuilder().withPersonName("Prince").build());
    private static final ObservableList<Schedule> TEST_SCHEDULES =
            FXCollections.observableList(scheduleList);

    private AgendaPanelHandle agendaPanelHandle;

    @Before
    public void setUp() {
        AgendaPanel agendaPanel = new AgendaPanel(TEST_SCHEDULES);
        uiPartRule.setUiPart(agendaPanel);

        agendaPanelHandle = new AgendaPanelHandle(getChildNode(agendaPanel.getRoot(),
                AgendaPanelHandle.SCHEDULE_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TEST_SCHEDULES.size(); i++) {
            agendaPanelHandle.navigateToCard(TEST_SCHEDULES.get(i));
            Schedule expectedSchedule = TEST_SCHEDULES.get(i);
            ScheduleCardHandle actualCard = agendaPanelHandle.getScheduleCardHandle(i);

            assertCardDisplaysSchedule(expectedSchedule, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getNumber());
        }
    }
}
```
###### \java\seedu\address\ui\ReminderWindowTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.ReminderWindowBottomHandle;
import guitests.guihandles.ScheduleCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;


public class ReminderWindowTest extends GuiUnitTest {
    private static final List<Schedule> scheduleList = Arrays.asList(new ScheduleBuilder().build(),
            new ScheduleBuilder().withPersonName("Prince").build());
    private static final ObservableList<Schedule> TEST_SCHEDULES =
            FXCollections.observableList(scheduleList);

    private ReminderWindow reminderWindow;
    private ReminderWindowBottomHandle reminderWindowBottomHandle;

    @Before
    public void setUp() throws TimeoutException {
        guiRobot.interact(() -> reminderWindow = new ReminderWindow(TEST_SCHEDULES));
        FxToolkit.setupStage((stage) -> stage.setScene(reminderWindow.getRoot().getScene()));
        FxToolkit.showStage();

        reminderWindowBottomHandle = new ReminderWindowBottomHandle(getChildNode(reminderWindow.getRoot(),
                reminderWindowBottomHandle.SCHEDULE_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TEST_SCHEDULES.size(); i++) {
            reminderWindowBottomHandle.navigateToCard(TEST_SCHEDULES.get(i));
            Schedule expectedSchedule = TEST_SCHEDULES.get(i);
            ScheduleCardHandle actualCard = reminderWindowBottomHandle.getScheduleCardHandle(i);

            assertCardDisplaysSchedule(expectedSchedule, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getNumber());
        }
    }
}
```
###### \java\seedu\address\ui\ScheduleCardTest.java
``` java
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysSchedule;

import org.junit.Test;

import guitests.guihandles.ScheduleCardHandle;

import seedu.address.model.schedule.Schedule;
import seedu.address.testutil.ScheduleBuilder;



public class ScheduleCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Schedule testSchedule = new ScheduleBuilder().withPersonName("Bob").build();
        ScheduleCard scheduleCard = new ScheduleCard(testSchedule, 2);
        uiPartRule.setUiPart(scheduleCard);
        assertCardDisplay(scheduleCard, testSchedule, 2);

        Schedule testSchedule2 = new ScheduleBuilder().build();
        ScheduleCard scheduleCard2 = new ScheduleCard(testSchedule, 1);
        // changes made to Schedule reflects on card
        guiRobot.interact(() -> {
            testSchedule2.setPersonInvolvedNames(testSchedule2.getPersonInvolvedNames());
            testSchedule2.setScheduleDate(testSchedule2.getScheduleDate());
            testSchedule2.setActivity(testSchedule2.getActivity());
        });
        assertCardDisplay(scheduleCard2, testSchedule, 1);
    }

    @Test
    public void equals() {
        Schedule schedule = new ScheduleBuilder().build();
        ScheduleCard scheduleCard = new ScheduleCard(schedule, 0);

        // same schedule, same index -> returns true
        ScheduleCard copy = new ScheduleCard(schedule, 0);
        assertTrue(scheduleCard.equals(copy));

        // same object -> returns true
        assertTrue(scheduleCard.equals(scheduleCard));

        // null -> returns false
        assertFalse(scheduleCard.equals(null));

        // different types -> returns false
        assertFalse(scheduleCard.equals(0));

        // different schedule, same index -> returns false
        Schedule differentSchedule = new ScheduleBuilder().withPersonName("differentName").build();
        assertFalse(scheduleCard.equals(new ScheduleCard((differentSchedule), 0)));

        // same person, different index -> returns false
        assertFalse(scheduleCard.equals(new ScheduleCard(schedule, 1)));
    }

    /**
     * Asserts that {@code scheduleCard} displays the details of {@code expectedSchedule} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ScheduleCard scheduleCard, Schedule expectedSchedule, int expectedId) {
        guiRobot.pauseForHuman();

        ScheduleCardHandle scheduleCardHandle = new ScheduleCardHandle(scheduleCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", scheduleCardHandle.getNumber());

        // verify schedule details are displayed correctly
        assertCardDisplaysSchedule(expectedSchedule, scheduleCardHandle);
    }
}
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the browser's url is changed to display the details of the person in the person list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     *
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        String selectedCardAddress = getPersonListPanel().getHandleToSelectedCard().getAddress();
        URL expectedUrl;
        try {
            String encodedUrlAddress = URLEncoder.encode(selectedCardAddress, "UTF-8").replaceAll("%2C", ",");
            expectedUrl = new URL(GOOGLE_MAPS_URL_PREFIX + encodedUrlAddress + GOOGLE_MAPS_URL_SUFFIX);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new AssertionError("URL expected to be valid.");
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }

```
