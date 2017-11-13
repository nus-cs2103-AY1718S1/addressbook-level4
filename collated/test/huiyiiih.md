# huiyiiih
###### /java/systemtests/SetRelCommandSystemTest.java
``` java
public class SetRelCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void set() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index indexOne = INDEX_FIRST_PERSON;
        Index indexTwo = INDEX_SECOND_PERSON;
        ReadOnlyPerson personInFilteredListOne = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person personOne = new PersonBuilder(personInFilteredListOne).withRelation(VALID_BENSON_REL).build();
        Person personTwo = new PersonBuilder(personInFilteredListTwo).withRelation(VALID_ALICE_REL).build();
        String command = " " + SetRelCommand.COMMAND_WORD + "  " + indexOne.getOneBased() + " " + indexTwo.getOneBased()
            + "  " + REL_DESC_SIBLINGS + "  ";
        assertCommandSuccess(command, indexOne, indexTwo, personOne, personTwo);

        /* Case: undo the adding of the relationship of the two persons in the list -> relationship not added */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding of the relationship of the two persons in the list -> relationship added edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updatePerson(
            getModel().getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personOne);
        model.updatePerson(
            getModel().getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()), personTwo);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: deleting the relationship between the two persons -> relationship deleted */
        command = SetRelCommand.COMMAND_WORD + " " + indexOne.getOneBased() + " " + indexTwo.getOneBased()
            + " " + PREFIX_DELETE_RELATIONSHIP + VALID_REL_SIBLINGS;
        personOne = new PersonBuilder(personInFilteredListOne).withRelation().build();
        personTwo = new PersonBuilder(personInFilteredListTwo).withRelation().build();
        assertCommandSuccess(command, indexOne, indexTwo, personOne, personTwo);

        /* Case: undo the deleting of the relationship of the two persons in the list -> relationship not deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: clearing the relationship between the two persons -> relationship cleared */
        command = SetRelCommand.COMMAND_WORD + " " + indexOne.getOneBased() + " " + indexTwo.getOneBased()
            + " " + PREFIX_CLEAR_RELATIONSHIP + VALID_REL_SIBLINGS;
        personOne = new PersonBuilder(personInFilteredListOne).withRelation().build();
        personTwo = new PersonBuilder(personInFilteredListTwo).withRelation().build();
        assertCommandSuccess(command, indexOne, indexTwo, personOne, personTwo);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index(0) and index(1) -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 0 1 " + REL_DESC_SIBLINGS,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) and index(2) -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " -1 2 " + REL_DESC_SIBLINGS,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));

        /* Case: same indexes (2) -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 2 2 " + REL_DESC_SIBLINGS,
            SetRelCommandParser.SAME_INDEX_ERROR);

        /* Case: missing index -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + REL_DESC_SIBLINGS,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE));

        /* Case: set multiple relationship for the same persons -> rejected */
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 1 2" + REL_DESC_SIBLINGS + REL_DESC_COLLEAGUE,
            String.format(SetRelCommandParser.ONE_RELATIONSHIP_ALLOWED));

        /* Case: setting another relationship to the same two persons -> rejected*/
        executeCommand(SetRelCommand.COMMAND_WORD + " 1 2" + REL_DESC_COLLEAGUE);
        assertCommandFailure(SetRelCommand.COMMAND_WORD + " 1 2 " + REL_DESC_COLLEAGUE,
            SetRelCommand.MESSAGE_NO_MULTIPLE_REL);

    }

    /**
     * Performs the same verification as
     * {@code assertCommandSuccess(String, Index, Index, ReadOnlyPerson, ReadOnlyPerson)}
     * except that the browser url and selected card remain unchanged.
     *
     * @param toEdit the index of the current model's filtered list
     * @param toEditOne the index of the current model's filtered list
     * @see SetRelCommandSystemTest#assertCommandSuccess(String, Index, Index, ReadOnlyPerson, ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, Index toEdit, Index toEditOne, ReadOnlyPerson editedPersonOne,
                                      ReadOnlyPerson editedPersonTwo) {
        assertCommandSuccess(command, toEdit, toEditOne, editedPersonOne, editedPersonTwo, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     *
     * @param toEdit the index of the current model's filtered list.
     * @see SetRelCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Index toEditOne, ReadOnlyPerson
        editedPersonOne, ReadOnlyPerson editedPersonTwo, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        ReadOnlyPerson personInFilteredListOne = expectedModel.getFilteredPersonList().get(toEdit.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = expectedModel.getFilteredPersonList().get(toEditOne.getZeroBased());
        try {
            expectedModel.updatePerson(personInFilteredListOne, editedPersonOne);
            expectedModel.updatePerson(personInFilteredListTwo, editedPersonTwo);
            expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } catch (DuplicatePersonException | PersonNotFoundException e) {
            throw new IllegalArgumentException(
            "editedPerson is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
            String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPersonOne, editedPersonTwo),
                expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     *
     * @see SetRelCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### /java/seedu/address/logic/parser/EditCommandParserTest.java
``` java
    @Test
    public void parse_addrelprefixpresent_failure() {
        // only relationship prefix present
        assertParseFailure(parser, "1" + REL_DESC_SIBLINGS, EditCommand.MESSAGE_NOT_EDITED);

        // valid inputs and relationship prefix present
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + REL_DESC_SIBLINGS,
            Relationship.MESSAGE_REL_PREFIX_NOT_ALLOWED);

        // invalid phone and relationship present
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + REL_DESC_SIBLINGS, Phone.MESSAGE_PHONE_CONSTRAINTS);
    }
```
###### /java/seedu/address/logic/parser/SortCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsSortCommand() {
        //SortCommand exp
        assertParseSuccess(parser, "name", new SortCommand("name"));
        assertParseSuccess(parser, "tag", new SortCommand("tag"));
        assertParseSuccess(parser, "position", new SortCommand("position"));
        assertParseSuccess(parser, "company", new SortCommand("company"));
        assertParseSuccess(parser, "priority", new SortCommand("priority"));
    }
}
```
###### /java/seedu/address/logic/parser/relationship/SetRelCommandParserTest.java
``` java
package seedu.address.logic.parser.relationship;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REL_DESC_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.REL_DESC_JANE_SIBLINGS;
import static seedu.address.logic.commands.CommandTestUtil.REL_DESC_SIBLINGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_SIBLINGS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.relationship.SetRelCommand;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.model.relationship.Relationship;
import seedu.address.testutil.EditPersonBuilder;

public class SetRelCommandParserTest {

    private static final String REL_ADD_EMPTY = " " + PREFIX_ADD_RELATIONSHIP;
    private static final String REL_DELETE_EMPTY = " " + PREFIX_DELETE_RELATIONSHIP;
    private static final String WRONG_REL_PREFIX = " ar.";
    private static final boolean addPrefixPresent = false;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE);

    private SetRelCommandParser parser = new SetRelCommandParser();

    @Test
    public void parse_missingInput_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 7" + REL_DESC_SIBLINGS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 1" + REL_DESC_SIBLINGS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndexOne = INDEX_FIRST_PERSON;
        Index targetIndexTwo = INDEX_SECOND_PERSON;
        String userInputOne = targetIndexOne.getOneBased() + " " + targetIndexTwo.getOneBased()
            + INVALID_REL_DESC;
        assertParseFailure(parser, userInputOne, Relationship.MESSAGE_REL_CONSTRAINTS);

        // other valid values specified
        userInputOne = targetIndexOne.getOneBased() + " " + targetIndexTwo.getOneBased() + REL_DESC_SIBLINGS;
        EditPerson descriptor = new EditPersonBuilder().withToAddRel(VALID_REL_SIBLINGS).build();
        SetRelCommand expectedCommand = new SetRelCommand(targetIndexOne, targetIndexTwo, descriptor, addPrefixPresent);
        assertParseSuccess(parser, userInputOne, expectedCommand);
        //assertParseSuccess(parser, userInputTwo, expectedCommand);
    }
    @Test
    public void parse_multiplePrefix_failure() {
        assertParseFailure(parser, "1 2" + REL_DESC_COLLEAGUE + REL_DESC_SIBLINGS,
            SetRelCommandParser.ONE_RELATIONSHIP_ALLOWED);
        assertParseFailure(parser, "1 2" + REL_DELETE_EMPTY + VALID_REL_SIBLINGS + REL_DELETE_EMPTY
            + VALID_REL_COLLEAGUE, SetRelCommandParser.ONE_RELATIONSHIP_ALLOWED);
    }
    @Test
    public void parse_invalidInput_failure() {
        assertParseFailure(parser, "1 2" +  INVALID_REL_DESC, Relationship.MESSAGE_REL_CONSTRAINTS);
        assertParseFailure(parser, "1 2" + REL_ADD_EMPTY, SetRelCommandParser.NULL_RELATION_INPUT);
        assertParseFailure(parser, "1 2" + REL_DELETE_EMPTY, SetRelCommandParser.NULL_RELATION_INPUT);
    }
    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, "1 2" + WRONG_REL_PREFIX + VALID_REL_SIBLINGS,
            MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_validInput_success() {
        Index targetIndexOne = INDEX_FIRST_PERSON;
        Index targetIndexTwo = INDEX_SECOND_PERSON;
        String userInput = targetIndexOne.getOneBased() + " " + targetIndexTwo.getOneBased() + REL_DESC_SIBLINGS;

        EditPerson descriptor = new EditPersonBuilder().withToAddRel(VALID_REL_SIBLINGS).build();
        SetRelCommand expectedCommand = new SetRelCommand(targetIndexOne, targetIndexTwo, descriptor, addPrefixPresent);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "a b" + REL_DESC_JANE_SIBLINGS, MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_sameIndexes_failure() {
        assertParseFailure(parser, "1 1" + REL_DESC_JANE_SIBLINGS, SetRelCommandParser.SAME_INDEX_ERROR);
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parseRels_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRels(null);
    }
    @Test
    public void parseRels_collectionWithInvalidRels_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseRels(Arrays.asList(INVALID_REL));
    }
    @Test
    public void parseRels_collectionWithValidRels_returnsRelSet() throws Exception {
        Set<Relationship> actualRelSet = ParserUtil.parseRels(Arrays.asList(VALID_REL));
        Set<Relationship> expectedRelSet = new HashSet<>(Arrays.asList(new Relationship(VALID_REL)));

        assertEquals(expectedRelSet, actualRelSet);
    }
```
###### /java/seedu/address/logic/parser/CheckCommandsParserTest.java
``` java
package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CheckCommandsParserTest {

    private final CheckCommandsParser parser = new CheckCommandsParser();

    @Test
    public void checkCommand_add() {
        // Check if the synonyms  is equals to add
        assertEquals(parser.matchCommand("input"), "add");
        assertEquals(parser.matchCommand("a"), "add");
        assertNotEquals(parser.matchCommand("plus"), "add");
    }

    @Test
    public void checkCommand_clear() {
        // Check if the synonyms  is equals to clear
        assertEquals(parser.matchCommand("c"), "clear");
        assertEquals(parser.matchCommand("empty"), "clear");
        assertNotEquals(parser.matchCommand("cl"), "clear");
    }

    @Test
    public void checkCommand_edit() {
        // Check if the synonyms  is equals to edit
        assertEquals(parser.matchCommand("e"), "edit");
        assertEquals(parser.matchCommand("modify"), "edit");
        assertNotEquals(parser.matchCommand("ed"), "edit");
    }

    @Test
    public void checkCommand_delete() {
        // Check if the synonyms  is equals to delete
        assertEquals(parser.matchCommand("d"), "delete");
        assertEquals(parser.matchCommand("remove"), "delete");
        assertNotEquals(parser.matchCommand("away"), "delete");
    }

    @Test
    public void checkCommand_exit() {
        // Check if the synonyms  is equals to exit
        assertEquals(parser.matchCommand("quit"), "exit");
        assertEquals(parser.matchCommand("exit"), "exit");
        assertNotEquals(parser.matchCommand("out"), "exit");
    }

    @Test
    public void checkCommand_find() {
        // Check if the synonyms  is equals to find
        assertEquals(parser.matchCommand("search"), "find");
        assertEquals(parser.matchCommand("f"), "find");
        assertNotEquals(parser.matchCommand("looked"), "find");
    }

    @Test
    public void checkCommand_help() {
        // Check if the synonyms  is equals to help
        assertEquals(parser.matchCommand("info"), "help");
        assertEquals(parser.matchCommand("help"), "help");
        assertNotEquals(parser.matchCommand("helps"), "help");
    }

    @Test
    public void checkCommand_history() throws Exception {
        // Check if the synonyms  is equals to history
        assertEquals(parser.matchCommand("past"), "history");
        assertEquals(parser.matchCommand("h"), "history");
        assertNotEquals(parser.matchCommand("his"), "history");
    }

    @Test
    public void checkCommand_list() {
        // Check if the synonyms  is equals to list
        assertEquals(parser.matchCommand("display"), "list");
        assertEquals(parser.matchCommand("l"), "list");
        assertNotEquals(parser.matchCommand("showme"), "list");
    }

    @Test
    public void checkCommand_redo() {
        // Check if the synonyms  is equals to redo
        assertEquals(parser.matchCommand("r"), "redo");
        assertEquals(parser.matchCommand("redo"), "redo");
        assertNotEquals(parser.matchCommand("again"), "redo");
    }

    @Test
    public void checkCommand_select() {
        // Check if the synonyms  is equals to select
        assertEquals(parser.matchCommand("s"), "select");
        assertEquals(parser.matchCommand("pick"), "select");
        assertNotEquals(parser.matchCommand("pickthis"), "select");
    }

    @Test
    public void checkCommand_undo() {
        // Check if the synonyms  is equals to redo
        assertEquals(parser.matchCommand("u"), "undo");
        assertEquals(parser.matchCommand("undo"), "undo");
        assertNotEquals(parser.matchCommand("repeat"), "undo");
    }
    @Test
    public void checkCommand_checkschedule() {
        // Check if the synonyms  is equals to check schedule
        assertEquals(parser.matchCommand("checkschedule"), "thisweek");
        assertEquals(parser.matchCommand("cs"), "thisweek");
        assertNotEquals(parser.matchCommand("s"), "thisweek");
    }
    @Test
    public void checkCommand_addevents() {
        // Check if the synonyms  is equals to add events
        assertEquals(parser.matchCommand("addevent"), "eventadd");
        assertEquals(parser.matchCommand("ea"), "eventadd");
        assertNotEquals(parser.matchCommand("adde"), "eventadd");
    }
    @Test
    public void checkCommand_deleteevents() {
        // Check if the synonyms  is equals to find delete events
        assertEquals(parser.matchCommand("delevent"), "eventdel");
        assertEquals(parser.matchCommand("de"), "eventdel");
        assertNotEquals(parser.matchCommand("deletee"), "eventdel");
    }
    @Test
    public void checkCommand_editevents() {
        // Check if the synonyms  is equals to edit events
        assertEquals(parser.matchCommand("editevent"), "eventedit");
        assertEquals(parser.matchCommand("ee"), "eventedit");
        assertNotEquals(parser.matchCommand("edite"), "eventedit");
    }
    @Test
    public void checkCommand_findevents() {
        // Check if the synonyms  is equals to find events
        assertEquals(parser.matchCommand("findevent"), "eventfind");
        assertEquals(parser.matchCommand("fe"), "eventfind");
        assertNotEquals(parser.matchCommand("finde"), "eventfind");
    }
    @Test
    public void checkCommand_setrelationships() {
        // Check if the synonyms  is equals to set relationship
        assertEquals(parser.matchCommand("rel"), "set");
        assertEquals(parser.matchCommand("setrel"), "set");
        assertNotEquals(parser.matchCommand("s"), "set");
    }
    @Test
    public void checkCommand_timetable() {
        assertEquals(parser.matchCommand("tt"), "timetable");
        assertNotEquals(parser.matchCommand("time"), "timetable");
    }
    @Test
    public void checkCommand_updatephoto() {
        assertEquals(parser.matchCommand("up"), "updatephoto");
        assertNotEquals(parser.matchCommand("update"), "updatephoto");
    }
}
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
    @Test
    public void parse_addrelprefixpresent_failure() {
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + REL_DESC_SIBLINGS, Relationship.MESSAGE_REL_PREFIX_NOT_ALLOWED);

        // invalid phone, add relationship prefix present but invalid phone message will be shown
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + INVALID_PHONE_DESC
            + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + REL_DESC_SIBLINGS, Phone.MESSAGE_PHONE_CONSTRAINTS);
    }
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_NAME_JOE = "Joe Smith";
    public static final String VALID_NAME_JANE = "Jane Smith";
    public static final String VALID_PRIORITY = "L";
    public static final String VALID_POSITION = "NIL";
    public static final String VALID_REL_SIBLINGS = "siblings";
    public static final String VALID_REL_COLLEAGUE = "colleague";
    public static final String VALID_ALICE_REL = "Alice Pauline [siblings]";
    public static final String VALID_BENSON_REL = "Benson Meier [siblings]";
    public static final String VALID_CARL_REL = "Carl Kurz [siblings]";
    public static final String VALID_DANIEL_REL = "Daniel Meier [siblings]";
    public static final String VALID_JANE_REL = "Joe Smith [siblings]";
    public static final String VALID_JOE_REL = "Jane Smith [siblings]";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String REL_DESC_SIBLINGS = " " + PREFIX_ADD_RELATIONSHIP + VALID_REL_SIBLINGS;
    public static final String REL_DESC_COLLEAGUE = " " + PREFIX_ADD_RELATIONSHIP + VALID_REL_COLLEAGUE;
    public static final String REL_DESC_JANE_SIBLINGS = " " + PREFIX_ADD_RELATIONSHIP + "Jane Smith" + " ["
        + VALID_REL_COLLEAGUE + "]";
    public static final String POSITION_DESC = " " + PREFIX_POSITION + VALID_POSITION;
    public static final String PRIORITY_DESC = " " + PREFIX_PRIORITY + VALID_PRIORITY;
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final SetRelCommand.EditPerson DESC_JOE;
    public static final SetRelCommand.EditPerson DESC_JANE;
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
        DESC_JOE = new EditPersonBuilder().withName(VALID_NAME_JOE)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withCompany(VALID_COMPANY_AMY).withPosition(VALID_POSITION_AMY)
            .withStatus(VALID_STATUS_AMY).withPriority(VALID_PRIORITY_AMY)
            .withNote(VALID_NOTE_AMY).withPhoto(VALID_PHOTO_AMY).withTags
            (VALID_TAG_FRIEND).withToAddRel(VALID_JOE_REL).build();
        DESC_JANE = new EditPersonBuilder().withName(VALID_NAME_JANE)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withCompany(VALID_COMPANY_AMY).withPosition(VALID_POSITION_AMY)
            .withStatus(VALID_STATUS_AMY).withPriority(VALID_PRIORITY_AMY)
            .withNote(VALID_NOTE_AMY).withPhoto(VALID_PHOTO_AMY).withTags
            (VALID_TAG_FRIEND).withToAddRel(VALID_JANE_REL).build();
```
###### /java/seedu/address/logic/commands/relationship/SetRelCommandTest.java
``` java
package seedu.address.logic.commands.relationship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JANE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JOE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALICE_REL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BENSON_REL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_SIBLINGS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.EditPersonBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SetRelCommand.
 */
public class SetRelCommandTest {
    private boolean addPrefixPresent = false;
    private boolean shouldClear = true;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, new SetRelCommand
            .EditPerson(), addPrefixPresent);
        ReadOnlyPerson editedPersonOne = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson editedPersonTwo = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPersonOne,
            editedPersonTwo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPerson editPerson = new EditPersonBuilder().withToAddRel(VALID_REL_SIBLINGS).build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, outOfBoundIndex, editPerson, addPrefixPresent);

        assertCommandFailure(setRelCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, outOfBoundIndex,
            new EditPersonBuilder().withName(VALID_NAME_JOE).build(), addPrefixPresent);

        assertCommandFailure(setRelCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void execute_addMultipleRelToPersons_failure() {
        addPrefixPresent = true;
        EditPerson editPerson = new EditPersonBuilder().withToAddRel(VALID_REL_COLLEAGUE).build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, editPerson,
            addPrefixPresent);
        assertCommandFailure(setRelCommand, model, SetRelCommand.MESSAGE_NO_MULTIPLE_REL);
    }
    @Test
    public void execute_addRelToPerson_success() throws Exception {
        addPrefixPresent = true;
        ReadOnlyPerson personInFilteredListOne = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person personOne = new PersonBuilder(personInFilteredListOne).withRelation(VALID_BENSON_REL).build();
        Person personTwo = new PersonBuilder(personInFilteredListTwo).withRelation(VALID_ALICE_REL).build();
        EditPerson editPerson = new EditPersonBuilder().withToAddRel(VALID_REL_SIBLINGS).build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, editPerson,
            addPrefixPresent);
        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            personOne, personTwo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personInFilteredListOne, personOne);
        expectedModel.updatePerson(personInFilteredListTwo, personTwo);
        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);

    }
    @Test
    public void execute_deleteRelFromPersons_success() throws Exception {
        ReadOnlyPerson personInFilteredListOne = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        Person personOne = new PersonBuilder(personInFilteredListOne).withRelation().build();
        Person personTwo = new PersonBuilder(personInFilteredListTwo).withRelation().build();
        EditPerson editPerson = new EditPersonBuilder().withToDeleteRel(VALID_REL_SIBLINGS).build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, editPerson,
            addPrefixPresent);
        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            personOne, personTwo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personInFilteredListOne, personOne);
        expectedModel.updatePerson(personInFilteredListTwo, personTwo);
        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearAllRelFromPersons_success() throws Exception {
        EditPerson editPerson = new EditPersonBuilder().withToClearRels(shouldClear).build();
        ReadOnlyPerson personInFilteredListOne = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        Person personOne = new PersonBuilder(personInFilteredListOne).withRelation().build();
        Person personTwo = new PersonBuilder(personInFilteredListTwo).withRelation().build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, editPerson,
            addPrefixPresent);
        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            personOne, personTwo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personInFilteredListOne, personOne);
        expectedModel.updatePerson(personInFilteredListTwo, personTwo);
        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final SetRelCommand standardCommand = new SetRelCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, DESC_JANE,
            addPrefixPresent);

        // same values -> returns true
        EditPerson editPerson = new EditPerson(DESC_JANE);
        SetRelCommand commandWithSameValues = new SetRelCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
            editPerson, addPrefixPresent);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // same index -> returns false
        assertFalse(standardCommand.equals(new SetRelCommand(INDEX_SECOND_PERSON, INDEX_SECOND_PERSON, DESC_JANE,
            addPrefixPresent)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new SetRelCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, DESC_JOE,
            addPrefixPresent)));
    }

    /**
     * Returns an {@code SetRelCommand} with parameters {@code index} and {@code descriptor}
     */
    private SetRelCommand prepareCommand(Index indexOne, Index indexTwo, EditPerson descriptor,
                                            boolean addPrefixPresent) {
        SetRelCommand setRelCommand = new SetRelCommand(indexOne, indexTwo, descriptor, addPrefixPresent);
        setRelCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setRelCommand;
    }
}
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
    @Test
    public void getRelList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getRelList().remove(0);
    }
```
###### /java/seedu/address/model/UniqueRelListTest.java
``` java
package seedu.address.model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.relationship.UniqueRelList;

public class UniqueRelListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueRelList uniqueRelList = new UniqueRelList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueRelList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/model/relationship/RelationshipTest.java
``` java
package seedu.address.model.relationship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RelationshipTest {

    @Test
    public void isValidPriority() {
        // invalid relationship
        assertFalse(Relationship.isValidRelType("")); // empty string
        // input includes symbols other than square brackets and hyphens
        assertFalse(Relationship.isValidRelType("siblings!"));
    }
    @Test
    public void validPriority() {
        // valid relationship. Only alphabets, square brackets and hyphens
        assertTrue(Relationship.isValidRelType("siblings"));
        assertTrue(Relationship.isValidRelType("ex-colleague"));
    }
}
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Parses the {@code relation} into a {@code Set<Relationship>} and set it to the {@code Person} that we are
     * building.
     */
    public PersonBuilder withRelation(String... relation) {
        try {
            this.person.setRel(SampleDataUtil.getRelSet(relation));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("relationships are expected to be unique." + ive.getMessage());
        }
        return this;
    }
```
