# Sri-vatsa
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated linkedin page of a person
        postNow(selectionChangedEventStub);

        String [] name = ALICE.getName().fullName.split(" ");
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
```
###### /java/seedu/address/logic/parser/DeleteTagCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeleteTagCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteTagCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteTagCommandParserTest {

    private DeleteTagCommandParser parser = new DeleteTagCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        //single entry
        String [] arg = new String[]{"friends"};
        assertParseSuccess(parser, "friends", new DeleteTagCommand(arg));

        //multiple entries
        String [] args = new String[] {"friends", "colleagues"};
        assertParseSuccess(parser, "friends colleagues", new DeleteTagCommand(args));

        //entries with space
        String [] argsWithSpace = new String[] {"friends", "colleagues"};
        assertParseSuccess(parser, "\n friends \n \t colleagues  \t", new DeleteTagCommand(argsWithSpace));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "    ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteTagCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/AddMeetingCommandParserTest.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_1;
import static seedu.address.logic.commands.CommandTestUtil.NOTES_1;
import static seedu.address.logic.commands.CommandTestUtil.PERSON_1;
import static seedu.address.logic.commands.CommandTestUtil.TIME_1;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.logic.commands.AddMeetingCommand;

import seedu.address.model.Meeting;

import seedu.address.model.person.InternalId;

public class AddMeetingCommandParserTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();
    @Test
    public void parse_allFieldsPresent_success() throws IllegalValueException {
        ArrayList<InternalId> ids = new ArrayList<>();
        ids.add(new InternalId(2));
        LocalDateTime localDateTime = LocalDateTime.of(2020, 10, 31, 18, 00);
        Meeting expectedMeeting = new Meeting(localDateTime, "Computing", "Project meeting", ids);

        // Add meeting successfully
        assertParseSuccess(parser, AddMeetingCommand.COMMAND_WORD + DATE_1 + TIME_1 + LOCATION_1 + NOTES_1
                + PERSON_1, new AddMeetingCommand(expectedMeeting));
        /*
        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_AMY + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
        */
    }
    /*
    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().withSearchCount().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PHONE_BOB
                + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + VALID_ADDRESS_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_ADDRESS_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_PHONE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_EMAIL_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + INVALID_ADDRESS_DESC + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + INVALID_ADDRESS_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }*/
}
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_listByMostSearched() throws Exception {
        assertTrue(parser.parseCommand(ListByMostSearchedCommand.COMMAND_WORD) instanceof ListByMostSearchedCommand);
        assertTrue(parser.parseCommand(ListByMostSearchedCommand.COMMAND_ALIAS) instanceof ListByMostSearchedCommand);
        assertTrue(parser.parseCommand(ListByMostSearchedCommand.COMMAND_WORD + " 3")
                instanceof ListByMostSearchedCommand);
    }
```
###### /java/seedu/address/logic/commands/FindCommandTest.java
``` java
    /***
     * Ensures that with each successful find, the search count of the contact is updated by 1
     */
    @Test
    public  void execute_recordStorage() {

        int carlIndex = model.getFilteredPersonList().indexOf(CARL);

        int countBeforeFind = Integer.parseInt(
                model.getFilteredPersonList().get(carlIndex).getSearchData().getSearchCount());

        FindCommand findCommand = prepareCommand("Carl");

        try {
            findCommand.execute();
        } catch (CommandException e) {
            e.printStackTrace();
        }

        int countAfterFind = Integer.parseInt(model.getFilteredPersonList().get(0).getSearchData().getSearchCount());
        assertEquals(countBeforeFind + 1, countAfterFind);
    }
```
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTags.MULTIPLE_TAG_DELETION;
import static seedu.address.testutil.TypicalTags.SINGLE_TAG_DELETION;
import static seedu.address.testutil.TypicalTags.SINGLE_TAG_DELETION_ALT;
import static seedu.address.testutil.TypicalTags.TAG_DOES_NOT_EXIST;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UniqueMeetingList;
import seedu.address.model.UserPrefs;

/***
 * Focuses tests on model's deleteTag method, assumes DeleteTagCommandParser test handles tests for converting User
 * input into type suitable for deleteTag method (i.e. String Array)
 */

public class DeleteTagCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UniqueMeetingList(), new UserPrefs());

    @Test
    public void constructor_nullArgument_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteTagCommand(null);
    }

    @Test
    public void execute_deleteSingleTagSuccessful() throws Exception {

        CommandResult commandResult = getDeleteTagCommand(SINGLE_TAG_DELETION, model).executeUndoableCommand();

        assertEquals(String.format(DeleteTagCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }

    @Test
    public void execute_deleteMultipleTagSuccessful() throws Exception {

        CommandResult commandResult = getDeleteTagCommand(MULTIPLE_TAG_DELETION, model).executeUndoableCommand();

        assertEquals(String.format(DeleteTagCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }

    @Test
    public void execute_deleteSingleTag_tagDoesNotExist() throws Exception {

        CommandResult commandResult = getDeleteTagCommand(TAG_DOES_NOT_EXIST, model).executeUndoableCommand();

        assertEquals(String.format(DeleteTagCommand.MESSAGE_NO_TAGS_DELETED), commandResult.feedbackToUser);
    }

    /**
     * Generates a new DeleteTagCommand for test
     */
    private DeleteTagCommand getDeleteTagCommand(String [] arg, Model model) {
        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(arg);
        deleteTagCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteTagCommand;
    }


    @Test
    public void equals() {
        DeleteTagCommand deleteTagCommandOne = new DeleteTagCommand(SINGLE_TAG_DELETION);
        DeleteTagCommand deleteTagCommandTwo = new DeleteTagCommand(SINGLE_TAG_DELETION_ALT);

        // same object -> returns true
        assertTrue(deleteTagCommandOne.equals(deleteTagCommandOne));

        // same values -> returns true
        DeleteTagCommand deleteTagCommandOneCopy = new DeleteTagCommand(SINGLE_TAG_DELETION);
        assertTrue(deleteTagCommandOne.equals(deleteTagCommandOneCopy));

        // different types -> returns false
        assertFalse(deleteTagCommandOne.equals(1));

        // null -> returns false
        assertFalse(deleteTagCommandOne.equals(null));

        // different person -> returns false
        assertFalse(deleteTagCommandOne.equals(deleteTagCommandTwo));
    }

}
```
###### /java/seedu/address/logic/commands/ListByMostSearchedCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UniqueMeetingList;
import seedu.address.model.UserPrefs;

/***
 * Class of tests for ListByMostSearchedCommandTest
 */
public class ListByMostSearchedCommandTest {
    private Model model;
    private ListByMostSearchedCommand lmsCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UniqueMeetingList(), new UserPrefs());

        lmsCommand = new ListByMostSearchedCommand();
        lmsCommand.setData(model, new CommandHistory(), new UndoRedoStack());

    }

    @Test
    public void execute_listInDescendingSearchCount_verification() {
        lmsCommand.executeUndoableCommand();

        //In a list sorted in descending order of search count, SearchCountA refers to the search count A of the
        //person higher up on the list with a supposed search Count greater or equals to the search count of person
        //B who is lower in the list, with a lower search count
        int searchCountA;
        int searchCountB;

        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            for (int j = i + 1; j < model.getFilteredPersonList().size(); j++) {
                searchCountA = Integer.parseInt(model.getFilteredPersonList().get(j).getSearchData().getSearchCount());
                searchCountB = Integer.parseInt(model.getFilteredPersonList().get(i).getSearchData().getSearchCount());
                assertTrue(searchCountA <= searchCountB);
            }
        }

    }
}
```
###### /java/seedu/address/logic/commands/AddMeetingCommandTest.java
``` java

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AddMeetingCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullMeeting_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMeetingCommand(null);
    }
    /*
    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        AddCommandTest.ModelStubAcceptingPersonAdded modelStub = new AddCommandTest.ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = getAddCommandForPerson(validPerson, modelStub).execute();

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        AddCommandTest.ModelStub modelStub = new AddCommandTest.ModelStubThrowingDuplicatePersonException();
        Person validPerson = new PersonBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);

        getAddCommandForPerson(validPerson, modelStub).execute();
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new AddCommand with the details of the given person.
     *
    private AddCommand getAddCommandForPerson(Person person, Model model) {
        AddCommand command = new AddCommand(person);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     *
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyMeetingList getMeetingList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public boolean deleteTag(Tag[] tags) throws PersonNotFoundException, DuplicatePersonException {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList() {
            updateFilteredPersonList();
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public UserPrefs getUserPrefs() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void recordSearchHistory() throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListBySearchCount() {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListLexicographically() {
            fail("This method should not be called.");
        }

        @Override
        public void mapPerson(ReadOnlyPerson target) throws PersonNotFoundException {

        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     *
    private class ModelStubThrowingDuplicatePersonException extends AddCommandTest.ModelStub {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            throw new DuplicatePersonException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     *
    private class ModelStubAcceptingPersonAdded extends AddCommandTest.ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();

        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            personsAdded.add(new Person(person));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
    */
}
```
###### /java/seedu/address/testutil/TypicalPersons.java
``` java
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final ReadOnlyPerson ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("85355255")
            .withTags("friends").withSearchCount().build();
    public static final ReadOnlyPerson BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").withSearchCount().build();
    public static final ReadOnlyPerson CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withSearchCount().build();
    public static final ReadOnlyPerson DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withSearchCount().build();
    public static final ReadOnlyPerson ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withSearchCount().build();
    public static final ReadOnlyPerson FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withSearchCount().build();
    public static final ReadOnlyPerson GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withSearchCount().build();

    // Manually added
    public static final ReadOnlyPerson HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withSearchCount().build();
    public static final ReadOnlyPerson IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withSearchCount().build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withSearchCount().build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withSearchCount().build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getTypicalPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyPerson> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withSearchCount() {
        try {
            this.person.getSearchData().setSearchCount("0");
        } catch (IllegalAccessError ive) {
            throw new IllegalAccessError("Search count cannot be updated");
        }
        return this;
    }
```
###### /java/systemtests/AddressBookSystemTest.java
``` java
    /**
     * Asserts that the browser's url is changed to display the details of the person in the person list panel at
     * {@code expectedSelectedCardIndex}, and only the card at {@code expectedSelectedCardIndex} is selected.
     * @see BrowserPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardChanged(Index expectedSelectedCardIndex) {
        String selectedCardName = getPersonListPanel().getHandleToSelectedCard().getName();
        URL expectedUrl;
        try {
            expectedUrl = new URL(GOOGLE_SEARCH_URL_PREFIX + selectedCardName.replaceAll(" ", "+")
                    + GOOGLE_SEARCH_URL_SUFFIX);
        } catch (MalformedURLException mue) {
            throw new AssertionError("URL expected to be valid.");
        }
        assertEquals(expectedUrl, getBrowserPanel().getLoadedUrl());

        assertEquals(expectedSelectedCardIndex.getZeroBased(), getPersonListPanel().getSelectedCardIndex());
    }
```
