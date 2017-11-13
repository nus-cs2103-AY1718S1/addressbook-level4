# huiyiiih
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_NAME_JOE = "Joe Smith";
    public static final String VALID_NAME_JANE = "Jane Smith";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String REL_DESC_SIBLINGS = " " + PREFIX_ADD_RELATIONSHIP + VALID_REL_SIBLINGS;
    public static final String REL_DESC_COLLEAGUE = " " + PREFIX_ADD_RELATIONSHIP + VALID_REL_COLLEAGUE;
    public static final String REL_DESC_JOE_SIBLINGS = " " + PREFIX_ADD_RELATIONSHIP + "Joe Smith" + " ["
        + VALID_REL_COLLEAGUE + "]";
    public static final String REL_DESC_JANE_SIBLINGS = " " + PREFIX_ADD_RELATIONSHIP + "Jane Smith" + " ["
        + VALID_REL_COLLEAGUE + "]";
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final SetRelCommand.EditPerson DESC_JOE;
    public static final SetRelCommand.EditPerson DESC_JANE;
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
        DESC_JOE = new EditPersonBuilder().withName(VALID_NAME_JOE)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withCompany(VALID_COMPANY_AMY).withPosition(VALID_POSITION_AMY)
            .withStatus(VALID_STATUS_AMY).withPriority(VALID_PRIORITY_AMY)
            .withNote(VALID_NOTE_AMY).withPhoto(VALID_PHOTO_AMY).withTags
            (VALID_TAG_FRIEND).withToAddRel(VALID_REL_SIBLINGS).build();
        DESC_JANE = new EditPersonBuilder().withName(VALID_NAME_JANE)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withCompany(VALID_COMPANY_AMY).withPosition(VALID_POSITION_AMY)
            .withStatus(VALID_STATUS_AMY).withPriority(VALID_PRIORITY_AMY)
            .withNote(VALID_NOTE_AMY).withPhoto(VALID_PHOTO_AMY).withTags
            (VALID_TAG_FRIEND).withToAddRel(VALID_REL_COLLEAGUE).build();
```
###### \java\seedu\address\logic\commands\relationship\SetRelCommandTest.java
``` java
package seedu.address.logic.commands.relationship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JANE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JOE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_SIBLINGS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
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
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.EditPersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class SetRelCommandTest {
    private static final boolean addPrefixPresent = false;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    //private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

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
    /*@Test
    Todo: To be completed
    public void execute_addRelToPersons_success() throws Exception {
        EditPerson editPerson = new EditPersonBuilder().withToAddRel("siblings").build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, editPerson,
            addPrefixPresent);
        Person personOne = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
            .withRelation("siblings").build();
        Person personTwo = new PersonBuilder(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()))
            .withRelation("siblings").build();
        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            personOne, personTwo);
        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);
    }*/

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
        // relationship already set for two persons
        assertFalse(standardCommand.equals(new SetRelCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, DESC_JOE,
            true)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private SetRelCommand prepareCommand(Index indexOne, Index indexTwo, EditPerson descriptor,
                                            boolean addPrefixPresent) {
        SetRelCommand setRelCommand = new SetRelCommand(indexOne, indexTwo, descriptor, addPrefixPresent);
        setRelCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setRelCommand;
    }
}
```
###### \java\seedu\address\logic\parser\CheckCommandsParserTest.java
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
        assertEquals(parser.matchCommand("insert"), "add");
        assertEquals(parser.matchCommand("a"), "add");
        assertEquals(parser.matchCommand("create"), "add");
        assertEquals(parser.matchCommand("add"), "add");
        assertNotEquals(parser.matchCommand("plus"), "add");
    }

    @Test
    public void checkCommand_clear() {
        // Check if the synonyms  is equals to clear
        assertEquals(parser.matchCommand("c"), "clear");
        assertEquals(parser.matchCommand("empty"), "clear");
        assertEquals(parser.matchCommand("clean"), "clear");
        assertEquals(parser.matchCommand("clear"), "clear");
        assertNotEquals(parser.matchCommand("cl"), "clear");
    }

    @Test
    public void checkCommand_edit() {
        // Check if the synonyms  is equals to edit
        assertEquals(parser.matchCommand("e"), "edit");
        assertEquals(parser.matchCommand("modify"), "edit");
        assertEquals(parser.matchCommand("change"), "edit");
        assertEquals(parser.matchCommand("revise"), "edit");
        assertEquals(parser.matchCommand("edit"), "edit");
        assertNotEquals(parser.matchCommand("ed"), "edit");
    }

    @Test
    public void checkCommand_delete() {
        // Check if the synonyms  is equals to delete
        assertEquals(parser.matchCommand("d"), "delete");
        assertEquals(parser.matchCommand("remove"), "delete");
        assertEquals(parser.matchCommand("throw"), "delete");
        assertEquals(parser.matchCommand("erase"), "delete");
        assertEquals(parser.matchCommand("delete"), "delete");
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
        assertEquals(parser.matchCommand("look"), "find");
        assertEquals(parser.matchCommand("check"), "find");
        assertEquals(parser.matchCommand("f"), "find");
        assertEquals(parser.matchCommand("find"), "find");
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
        assertEquals(parser.matchCommand("history"), "history");
        assertEquals(parser.matchCommand("h"), "history");
        assertNotEquals(parser.matchCommand("his"), "history");
    }

    @Test
    public void checkCommand_list() {
        // Check if the synonyms  is equals to list
        assertEquals(parser.matchCommand("display"), "list");
        assertEquals(parser.matchCommand("l"), "list");
        assertEquals(parser.matchCommand("show"), "list");
        assertEquals(parser.matchCommand("list"), "list");
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
        assertEquals(parser.matchCommand("choose"), "select");
        assertEquals(parser.matchCommand("select"), "select");
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
        assertEquals(parser.matchCommand("thisweek"), "thisweek");
        assertEquals(parser.matchCommand("checkschedule"), "thisweek");
        assertEquals(parser.matchCommand("schedule"), "thisweek");
        assertEquals(parser.matchCommand("tw"), "thisweek");
        assertEquals(parser.matchCommand("cs"), "thisweek");
        assertNotEquals(parser.matchCommand("s"), "thisweek");
    }
    @Test
    public void checkCommand_addevents() {
        // Check if the synonyms  is equals to add events
        assertEquals(parser.matchCommand("eventadd"), "eventadd");
        assertEquals(parser.matchCommand("addevent"), "eventadd");
        assertEquals(parser.matchCommand("ae"), "eventadd");
        assertEquals(parser.matchCommand("ea"), "eventadd");
        assertNotEquals(parser.matchCommand("adde"), "eventadd");
    }
    @Test
    public void checkCommand_deleteevents() {
        // Check if the synonyms  is equals to find delete events
        assertEquals(parser.matchCommand("eventdel"), "eventdel");
        assertEquals(parser.matchCommand("delevent"), "eventdel");
        assertEquals(parser.matchCommand("deleteevent"), "eventdel");
        assertEquals(parser.matchCommand("eventdelete"), "eventdel");
        assertEquals(parser.matchCommand("de"), "eventdel");
        assertEquals(parser.matchCommand("ed"), "eventdel");
        assertNotEquals(parser.matchCommand("deletee"), "eventdel");
    }
    @Test
    public void checkCommand_editevents() {
        // Check if the synonyms  is equals to edit events
        assertEquals(parser.matchCommand("eventedit"), "eventedit");
        assertEquals(parser.matchCommand("editevent"), "eventedit");
        assertEquals(parser.matchCommand("ee"), "eventedit");
        assertNotEquals(parser.matchCommand("edite"), "eventedit");
    }
    @Test
    public void checkCommand_findevents() {
        // Check if the synonyms  is equals to find events
        assertEquals(parser.matchCommand("eventfind"), "eventfind");
        assertEquals(parser.matchCommand("findevent"), "eventfind");
        assertEquals(parser.matchCommand("fe"), "eventfind");
        assertEquals(parser.matchCommand("ef"), "eventfind");
        assertNotEquals(parser.matchCommand("finde"), "eventfind");
    }
    @Test
    public void checkCommand_setrelationships() {
        // Check if the synonyms  is equals to set relationship
        assertEquals(parser.matchCommand("set"), "set");
        assertEquals(parser.matchCommand("rel"), "set");
        assertEquals(parser.matchCommand("setrel"), "set");
        assertNotEquals(parser.matchCommand("s"), "set");
    }
}
```
###### \java\seedu\address\logic\parser\relationship\SetRelCommandParserTest.java
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
###### \java\seedu\address\logic\parser\SortCommandParserTest.java
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
    }
}
```
###### \java\seedu\address\model\UniqueRelListTest.java
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
###### \java\seedu\address\testutil\EditPersonBuilder.java
``` java
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building EditPerson objects.
 */
public class EditPersonBuilder {

    private EditPerson editPerson;

    public EditPersonBuilder() {
        editPerson = new EditPerson();
    }

    public EditPersonBuilder(EditPerson editPerson) {
        this.editPerson = new EditPerson(editPerson);
    }

    /**
     * Returns an {@code EditPerson} with fields containing {@code person}'s details
     */
    public EditPersonBuilder(ReadOnlyPerson person) {
        editPerson = new EditPerson();
        editPerson.setName(person.getName());
        editPerson.setPhone(person.getPhone());
        editPerson.setEmail(person.getEmail());
        editPerson.setAddress(person.getAddress());
        editPerson.setCompany(person.getCompany());
        editPerson.setPosition(person.getPosition());
        editPerson.setStatus(person.getStatus());
        editPerson.setPriority(person.getPriority());
        editPerson.setNote(person.getNote());
        editPerson.setPhoto(person.getPhoto());
        editPerson.setTags(person.getTags());
        editPerson.setToAdd(person.getRelation());
    }

    /**
     * Sets the {@code Name} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withName(String name) {
        try {
            ParserUtil.parseName(Optional.of(name)).ifPresent(editPerson::setName);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withPhone(String phone) {
        try {
            ParserUtil.parsePhone(Optional.of(phone)).ifPresent(editPerson::setPhone);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("phone is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withEmail(String email) {
        try {
            ParserUtil.parseEmail(Optional.of(email)).ifPresent(editPerson::setEmail);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("email is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPerson} that we are building.
     */
    public EditPersonBuilder withAddress(String address) {
        try {
            ParserUtil.parseAddress(Optional.of(address)).ifPresent(editPerson::setAddress);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\testutil\PersonBuilder.java
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
