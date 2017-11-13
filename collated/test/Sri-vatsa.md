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
        //assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
```
###### /java/seedu/address/logic/parser/DeleteTagCommandParserTest.java
``` java
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
public class AddMeetingCommandParserTest {
    private AddMeetingCommandParser parser = new AddMeetingCommandParser();
    @Test
    public void parse_allFieldsPresent_success() throws IllegalValueException {
        ArrayList<InternalId> ids = new ArrayList<>();
        ids.add(new InternalId(2));
        LocalDateTime localDateTime = LocalDateTime.of(2020, 10, 31, 18, 00);
        Meeting expectedMeeting = new Meeting(localDateTime, "Computing", "Project meeting", ids);

        // Add meeting successfully
        assertParseSuccess(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + TIME_VALID + LOCATION_1
                + NOTES_1 + PERSON_1, new AddMeetingCommand(expectedMeeting));
    }

    //meeting date is in the past
    @Test
    public void parse_dateFromPast_failure() throws IllegalValueException {

        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_PAST + TIME_VALID + LOCATION_1
                + NOTES_1 + PERSON_1, "Please enter a date & time that is in the future.");
    }

    //missing fields
    @Test
    public void parse_missingFields_failure() {

        //missing id
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + TIME_VALID + LOCATION_1
                + NOTES_1, ADD_MEETING_INVALID_FORMAT);

        //missing date
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + TIME_VALID + LOCATION_1
                + NOTES_1 + PERSON_1, ADD_MEETING_INVALID_FORMAT);

        //missing time
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + LOCATION_1
                + NOTES_1 + PERSON_1, ADD_MEETING_INVALID_FORMAT);

        //missing location
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + TIME_VALID
                + NOTES_1 + PERSON_1, ADD_MEETING_INVALID_FORMAT);

        //missing notes
        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_VALID + TIME_VALID + LOCATION_1
                 + PERSON_1, ADD_MEETING_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidDate_failure() {

        assertParseFailure(parser, AddMeetingCommand.COMMAND_WORD + DATE_INVALID + TIME_VALID + LOCATION_1
                + NOTES_1, ADD_MEETING_INVALID_FORMAT);

    }

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

    @Test
    public void parseCommand_addMeeting() throws Exception {

        //Create new Id arrayList
        ArrayList<InternalId> ids = new ArrayList<>();
        ids.add(new InternalId(1));

        //create a new localDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");
        String dateTime = "27/10/2020 1800";
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        //Create an expected new meeting
        Meeting newMeeting = new Meeting(localDateTime, "Computing", "Coding Project", ids);

        //create new user-input based add meeting command
        AddMeetingCommand command = (AddMeetingCommand)
                parser.parseCommand(AddMeetingCommand.COMMAND_WORD + " "
                        + PREFIX_DATE + " 27/10/2020 "
                        + PREFIX_TIME + " 1800 "
                        + PREFIX_LOCATION + " Computing "
                        + PREFIX_NOTES + " Coding Project "
                        + PREFIX_PERSON + " 1");

        assertEquals(new AddMeetingCommand(newMeeting), command);
    }

    @Test
    public void parseCommand_deleteTag_one() throws Exception {
        String [] tags = {"friends"};
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(
                DeleteTagCommand.COMMAND_WORD + " " + "friends");
        assertEquals(new DeleteTagCommand(tags), command);
    }

    @Test
    public void parseCommand_deleteTag_multiple() throws Exception {
        String [] tags = {"friends", "colleagues"};
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(
                DeleteTagCommand.COMMAND_WORD + " " + "friends" + " " + "colleagues");
        assertEquals(new DeleteTagCommand(tags), command);
    }

    @Test
    public void parseCommand_setUniqueKey() throws Exception {
        String accessCode = "0/b62305d262c673af5c042bfad54ef832";
        SetUniqueKeyCommand command = (SetUniqueKeyCommand) parser.parseCommand(
                SetUniqueKeyCommand.COMMAND_WORD + " " + accessCode);
        assertEquals(new SetUniqueKeyCommand(accessCode), command);
    }

    @Test
    public void parseCommand_setupAsana() throws Exception {
        assertTrue(parser.parseCommand(SetupAsanaCommand.COMMAND_WORD) instanceof SetupAsanaCommand);
        assertTrue(parser.parseCommand(SetupAsanaCommand.COMMAND_ALIAS) instanceof SetupAsanaCommand);
        assertTrue(parser.parseCommand(SetupAsanaCommand.COMMAND_WORD + " 2") instanceof SetupAsanaCommand);
    }

```
###### /java/seedu/address/logic/parser/SetUniqueKeyCommandParserTest.java
``` java
public class SetUniqueKeyCommandParserTest {
    private SetUniqueKeyCommandParser parser = new SetUniqueKeyCommandParser();

    @Test
    public void parse_accessCode_success() {
        String accessCode = "0/1e2345h78hy70";

        assertParseSuccess(parser, accessCode,
                new SetUniqueKeyCommand(accessCode));

    }

    @Test
    public void parse_accessCode_failure() {
        String accessCode = "gibberish";

        assertParseFailure(parser, accessCode, "Please make sure the access code you have copied "
                + "follows the format:\nDIGIT/ALPHANUMERICS");
    }

}
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    private static final String INVALID_DATE = "22/12/198";
    private static final String INVALID_TIME = "+5";
    private static final String INVALID_LOCATION = " ";
    private static final String INVALID_NOTES = " ";
    private static final String INVALID_PERSON = "#1";
    private static final String INVALID_FORMAT_ACCESSCODE = "Gibberish";

    private static final String VALID_DATE = "27/11/2020";
    private static final String VALID_TIME = "1800";
    private static final String VALID_LOCATION = "UTown";
    private static final String VALID_NOTES = "Meeting";
    private static final String VALID_PERSON = "1";
    private static final String VALID_FORMAT_ACCESSCODE = "0/1g23j765kl985";

```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseLocation_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseLocation(null);
    }

    @Test
    public void parseLocation_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseLocation(Optional.of(INVALID_LOCATION));
    }

    @Test
    public void parseLocation_validValue_returnsLocation() throws Exception {

        Optional<String> actualLocation = ParserUtil.parseLocation(Optional.of(VALID_LOCATION));

        assertEquals(VALID_LOCATION, actualLocation.get());
    }

    @Test
    public void parseNotes_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseNotes(null);
    }

    @Test
    public void parseNotes_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseNotes(Optional.of(INVALID_NOTES));
    }

    @Test
    public void parseNotes_validValue_returnsEmail() throws Exception {

        Optional<String> actualNotes = ParserUtil.parseLocation(Optional.of(VALID_NOTES));

        assertEquals(VALID_NOTES, actualNotes.get());
    }

    @Test
    public void parseDate_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDate(null);
    }

    @Test
    public void parseDate_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDate(Optional.empty()).isPresent());
    }

    @Test
    public void parseDate_validValue_returnsDate() throws Exception {

        Optional<String> actualDate = ParserUtil.parseDate(Optional.of(VALID_DATE));

        assertEquals(VALID_DATE, actualDate.get());
    }
    @Test
    public void parseTime_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTime(null);
    }

    @Test
    public void parseTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseTime_validValue_returnsTime() throws Exception {

        Optional<String> actualTime = ParserUtil.parseTime(Optional.of(VALID_TIME));

        assertEquals(VALID_TIME, actualTime.get());
    }

    @Test
    public void parseDateTime_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDateTime(null, null);
    }

    @Test
    public void parseDateTime_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDateTime(INVALID_DATE, INVALID_TIME);
    }

    @Test
    public void parseDateTime_validValue_returnsDateTime() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu HHmm");
        String dateTime = VALID_DATE + " " + VALID_TIME;
        LocalDateTime localDateTimeExpected = LocalDateTime.parse(dateTime, formatter);

        LocalDateTime localDateTimeActual = ParserUtil.parseDateTime(VALID_DATE, VALID_TIME);

        assertEquals(localDateTimeExpected, localDateTimeActual);
    }

    @Test
    public void parseIds_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseIds(null);
    }

    @Test
    public void parseIds_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ArrayList<String> ids = new ArrayList<>();
        ids.add(INVALID_PERSON);
        ParserUtil.parseIds(ids);
    }

    @Test
    public void parseIds_validValue_returnsIds() throws Exception {
        //expected data
        ArrayList<InternalId> idsExpected = new ArrayList<>();
        idsExpected.add(new InternalId(Integer.parseInt(VALID_PERSON)));

        //actual data
        ArrayList<String> idsActual = new ArrayList<>();
        idsActual.add(VALID_PERSON);

        ArrayList<InternalId> actualIds = ParserUtil.parseIds(idsActual);

        assertEquals(idsExpected, actualIds);
    }

    @Test
    public void parseAccessCode_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAccessCode(null);
    }

    @Test
    public void parseAccessCode_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAccessCode(INVALID_FORMAT_ACCESSCODE);
    }

    @Test
    public void parseAccessCode_validValue_returnsAccessCode() throws Exception {

        String actualAccessCode = ParserUtil.parseAccessCode(VALID_FORMAT_ACCESSCODE);

        assertEquals(VALID_FORMAT_ACCESSCODE, actualAccessCode);
    }

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

```
###### /java/seedu/address/logic/commands/DeleteTagCommandTest.java
``` java
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
###### /java/seedu/address/logic/commands/ListCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UniqueMeetingList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UniqueMeetingList(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_list_successfully() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listCheckAlphabeticalSequence() {
        listCommand.executeUndoableCommand();

        String personAFullName;
        String personBFullName;

        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            for (int j = i + 1; j < model.getFilteredPersonList().size(); j++) {
                personAFullName = model.getFilteredPersonList().get(i).getName().fullName;
                personBFullName = model.getFilteredPersonList().get(j).getName().fullName;
                assertTrue(personAFullName.compareTo(personBFullName) <=  0);
            }
        }

    }
}
```
###### /java/seedu/address/logic/commands/AddMeetingCommandTest.java
``` java
public class AddMeetingCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullMeeting_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddMeetingCommand(null);
    }

    //successful meeting added without Asana configuration
    @Test
    public void execute_addMeeting_success() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ModelStubAcceptingMeetingAdded();

        ArrayList<InternalId> ids = new ArrayList<>();
        ids.add(new InternalId(2));
        LocalDateTime localDateTime = LocalDateTime.of(2020, 10, 31, 18, 00);
        Meeting expectedMeeting = new Meeting(localDateTime, "Computing", "Project meeting", ids);

        CommandResult commandResult = getAddMeetingCommand(expectedMeeting, modelStub).execute();
        assertEquals(AddMeetingCommand.MESSAGE_SUCCESS_ASANA_NO_CONFIG, commandResult.feedbackToUser);
    }

    //Duplicate meeting
    @Test
    public void execute_duplicateMeeting_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateMeetingException();

        ArrayList<InternalId> ids = new ArrayList<>();
        ids.add(new InternalId(2));
        LocalDateTime localDateTime = LocalDateTime.of(2020, 10, 31, 18, 00);
        Meeting expectedMeeting = new Meeting(localDateTime, "Computing", "Project meeting", ids);


        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMeetingCommand.MESSAGE_DUPLICATE_MEETING);

        getAddMeetingCommand(expectedMeeting, modelStub).execute();
    }

    /**
     * Generates a new AddMeetingCommand with the details of the given person.
     */
    private AddMeetingCommand getAddMeetingCommand (Meeting meeting, Model model) {
        AddMeetingCommand command = new AddMeetingCommand(meeting);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }


    /**
     * A default model stub that have all of the methods failing.
     */
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
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException, IllegalIdException {
            fail("This method should not be called.");
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
     * A Model stub that always throw a DuplicateMeetingException when trying to add a meeting.
     */
    private class ModelStubThrowingDuplicateMeetingException extends ModelStub {
        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException {
            throw new DuplicateMeetingException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the meeting being added.
     */
    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        final ArrayList<ReadOnlyMeeting> meetingsAdded = new ArrayList<>();

        @Override
        public void addMeeting(ReadOnlyMeeting meeting) {
            meetingsAdded.add(new Meeting(meeting));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_DATE = "31/10/2020";
    public static final String VALID_TIME = "1800";
    public static final String VALID_LOCATION = "Computing";
    public static final String VALID_NOTES = "Project meeting";
    public static final String VALID_PERSON = "2";

    public static final String PAST_DATE = "31/10/1995";
    public static final String INVALID_DATE = "12/11/198";

```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String DATE_VALID = " " + PREFIX_DATE + VALID_DATE;
    public static final String TIME_VALID = " " + PREFIX_TIME + VALID_TIME;
    public static final String LOCATION_1 = " " + PREFIX_LOCATION + VALID_LOCATION;
    public static final String NOTES_1 = " " + PREFIX_NOTES + VALID_NOTES;
    public static final String PERSON_1 = " " + PREFIX_PERSON + VALID_PERSON;

    public static final String DATE_PAST = " " + PREFIX_DATE + PAST_DATE;
    public static final String DATE_INVALID = " " + PREFIX_DATE + INVALID_DATE;

    public static final String ADD_MEETING_INVALID_FORMAT = "Invalid command format! \n"
            + "addMeeting: Adds a meeting to the address book. Parameters: on DATE from TIME at LOCATION about NOTES "
            + "with PERSON 1 with PERSON 2 ...\n"
            + "Example: addMeeting on 20/11/2017 from 1800 at UTown Starbucks about Project Meeting with 1";


```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException, IllegalIdException {
            fail("This method should not be called.");
        }
        //@author
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
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sortPersonListBySearchCount() {
            fail("This method should not be called.");
        }

        @Override
        public void sortPersonListLexicographically() {
            fail("This method should not be called.");
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
###### /java/seedu/address/testutil/TypicalTags.java
``` java
/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalTags {
    public static final String [] SINGLE_TAG_DELETION = new String [] {"friends"};
    public static final String [] SINGLE_TAG_DELETION_ALT = new String [] {"colleagues"};
    public static final String [] MULTIPLE_TAG_DELETION = new String [] {"friends", "colleagues"};
    public static final String [] TAG_DOES_NOT_EXIST = new String[] {"blahblah"};
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
