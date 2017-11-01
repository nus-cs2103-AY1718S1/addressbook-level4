# duyson98
###### \java\seedu\address\logic\commands\RetrieveCommandTest.java
``` java

package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.RetrieveCommand.MESSAGE_NOT_FOUND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code RetrieveCommand}.
 */
public class RetrieveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));
        RetrieveCommand command = new RetrieveCommand(predicate);

        // same value -> returns true
        assertTrue(command.equals(new RetrieveCommand(predicate)));

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        // different type -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // different tag name -> returns false
        assertFalse(command.equals(new RetrieveCommand(new TagContainsKeywordPredicate(new Tag("family")))));
    }

    @Test
    public void execute_noPersonFound() throws Exception {
        StringJoiner joiner = new StringJoiner(", ");
        for (Tag tag: model.getAddressBook().getTagList()) {
            joiner.add(tag.toString());
        }
        String expectedMessage = (MESSAGE_NOT_FOUND + "\n"
                + "You may want to refer to the following existing tags: "
                + joiner.toString());
        RetrieveCommand command = prepareCommand("thisTag");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multiplePersonsFound() throws Exception {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        RetrieveCommand command = prepareCommand("retrieveTester");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, BENSON));
    }

    /**
     * Parses {@code userInput} into a {@code RetrieveCommand}.
     */
    private RetrieveCommand prepareCommand(String userInput) throws Exception {
        if (userInput.isEmpty()) {
            RetrieveCommand command = new RetrieveCommand(new TagContainsKeywordPredicate(new Tag(userInput)));
        }
        RetrieveCommand command = new RetrieveCommand(new TagContainsKeywordPredicate(new Tag(userInput)));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    public void assertCommandSuccess(RetrieveCommand command, String expectedMessage,
                                     List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### \java\seedu\address\logic\commands\TagCommandTest.java
``` java

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstAndSecondPersonsOnly;
import static seedu.address.logic.commands.TagCommand.MESSAGE_INVALID_INDEXES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class TagCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() throws Exception {
        PersonBuilder firstPersonInList = new PersonBuilder(ALICE);
        Person firstTaggedPerson = firstPersonInList.withTags("friends", "retrieveTester", "tagTester").build();
        PersonBuilder secondPersonInList = new PersonBuilder(BENSON);
        Person secondTaggedPerson = secondPersonInList.withTags("owesMoney", "friends",
                "retrieveTester", "tagTester").build();
        Tag tag = new Tag("tagTester");
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, 2, tag.toString()) + " "
                + firstTaggedPerson.getName().toString() + ", "
                + secondTaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstTaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondTaggedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListContainsPersonsAlreadyTagged_success() throws Exception {
        PersonBuilder firstPersonInList = new PersonBuilder(ALICE);
        Person firstTaggedPerson = firstPersonInList.withTags("friends", "retrieveTester", "owesMoney").build();
        PersonBuilder secondPersonInList = new PersonBuilder(BENSON);
        Person secondTaggedPerson = secondPersonInList.withTags("owesMoney", "friends", "retrieveTester").build();
        Tag tag = new Tag("owesMoney");
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, 1, tag.toString()) + " "
                + firstTaggedPerson.getName().toString() + "\n"
                + String.format(TagCommand.MESSAGE_PERSONS_ALREADY_HAVE_TAG, 1) + " "
                + secondTaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstTaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondTaggedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstTaggedPerson = new PersonBuilder(firstPersonInList).withTags("friends",
                "retrieveTester", "tagTester").build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondTaggedPerson = new PersonBuilder(secondPersonInList).withTags("owesMoney", "friends",
                "retrieveTester", "tagTester").build();
        Tag tag = new Tag("tagTester");
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, 2, tag.toString()) + " "
                + firstTaggedPerson.getName().toString() + ", "
                + secondTaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstTaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondTaggedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListContainsPersonsAlreadyTagged_success() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        ReadOnlyPerson firstPersonInList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person firstTaggedPerson = new PersonBuilder(firstPersonInList).withTags("friends",
                "retrieveTester", "owesMoney").build();
        ReadOnlyPerson secondPersonInList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person secondTaggedPerson = new PersonBuilder(secondPersonInList).withTags("owesMoney", "friends",
                "retrieveTester", "friends").build();
        Tag tag = new Tag("owesMoney");
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), tag);

        String expectedMessage = String.format(TagCommand.MESSAGE_SUCCESS, 1, tag.toString()) + " "
                + firstTaggedPerson.getName().toString() + "\n"
                + String.format(TagCommand.MESSAGE_PERSONS_ALREADY_HAVE_TAG, 1) + " "
                + secondTaggedPerson.getName().toString();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), firstTaggedPerson);
        expectedModel.updatePerson(model.getFilteredPersonList().get(1), secondTaggedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexesUnfilteredList_failure() throws Exception {
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, outOfBound), new Tag("tagTester"));

        assertCommandFailure(command, model, MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void execute_invalidPersonIndexesFilteredList_failure() throws Exception {
        showFirstAndSecondPersonsOnly(model);

        Index outOfBoundIndex = INDEX_THIRD_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        TagCommand command = prepareCommand(Arrays.asList(INDEX_FIRST_PERSON, outOfBoundIndex), new Tag("tagTester"));

        assertCommandFailure(command, model, MESSAGE_INVALID_INDEXES);
    }

    @Test
    public void equals() throws Exception {
        final List<Index> indexList = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        final Tag tag = new Tag("dummyTag");
        final TagCommand command = new TagCommand(indexList, tag);

        // same values -> returns true
        final List<Index> indexListCopy = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        final Tag tagCopy = new Tag("dummyTag");
        assertTrue(command.equals(new TagCommand(indexListCopy, tagCopy)));

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        // different types -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // different index list -> returns false
        final List<Index> anotherIndexList = Arrays.asList(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        assertFalse(command.equals(new TagCommand(anotherIndexList, tag)));

        // different tag -> returns false
        final Tag anotherTag = new Tag("anotherTag");
        assertFalse(command.equals(new TagCommand(indexList, anotherTag)));
    }

    /**
     * Returns an {@code TagCommand}.
     */
    private TagCommand prepareCommand(List<Index> indexes, Tag tag) {
        TagCommand command = new TagCommand(indexes, tag);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_retrieve() throws Exception {
        RetrieveCommand command = (RetrieveCommand) parser.parseCommand(RetrieveCommand.COMMAND_WORD + " " + "friends");
        assertEquals(new RetrieveCommand(new TagContainsKeywordPredicate(new Tag("friends"))), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_tag() throws Exception {
        TagCommand command = (TagCommand) parser.parseCommand(TagCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + ","
                + INDEX_SECOND_PERSON.getOneBased() + ","
                + INDEX_THIRD_PERSON.getOneBased() + " " + "friends");
        assertEquals(new TagCommand(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                new Tag("friends")), command);
    }
```
###### \java\seedu\address\logic\parser\RetrieveCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import org.junit.Test;

import seedu.address.logic.commands.RetrieveCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagContainsKeywordPredicate;

public class RetrieveCommandParserTest {

    private RetrieveCommandParser parser = new RetrieveCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        final String expectedMessage = String.format(RetrieveCommand.MESSAGE_EMPTY_ARGS, RetrieveCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "*&%nonAlphanumericCharacters!!!%&*", MESSAGE_TAG_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsRetrieveCommand() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));

        // no leading and trailing whitespaces
        RetrieveCommand expectedCommand =
                new RetrieveCommand(predicate);
        assertParseSuccess(parser, "friends", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n friends \t \n", expectedCommand);
    }

}
```
###### \java\seedu\address\logic\parser\TagCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TagCommand.MESSAGE_EMPTY_INDEX_LIST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.TagCommand;
import seedu.address.model.tag.Tag;

public class TagCommandParserTest {

    private static final String VALID_TAG_NAME = "friends";

    private static final String VALID_INDEX_LIST = "1,2,3";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE);

    private static final String MESSAGE_NO_INDEXES =
            String.format(MESSAGE_EMPTY_INDEX_LIST, TagCommand.MESSAGE_USAGE);

    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no indexes specified
        assertParseFailure(parser, VALID_TAG_NAME, MESSAGE_INVALID_FORMAT);

        // no tag name specified
        assertParseFailure(parser, VALID_INDEX_LIST, MESSAGE_INVALID_FORMAT);

        // no indexes and no tag name specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no indexes
        assertParseFailure(parser, ",,,, " + VALID_TAG_NAME, MESSAGE_NO_INDEXES);

        // negative index
        assertParseFailure(parser, "-5 " + VALID_TAG_NAME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + VALID_TAG_NAME, MESSAGE_INVALID_FORMAT);

        // indexes are not all integers
        assertParseFailure(parser, "1,2,three " + VALID_TAG_NAME, MESSAGE_INVALID_FORMAT);

        // invalid tag name
        assertParseFailure(parser, VALID_INDEX_LIST + " !@#$", MESSAGE_TAG_CONSTRAINTS);

        // invalid arguments being parsed
        assertParseFailure(parser, "1,2,three dummy tag", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsTagCommand() throws Exception {
        // no leading and trailing whitespaces
        TagCommand expectedCommand = new TagCommand(Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON), new Tag(VALID_TAG_NAME));
        assertParseSuccess(parser, VALID_INDEX_LIST + " " + VALID_TAG_NAME, expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\t " + VALID_INDEX_LIST + " \n"
                + VALID_TAG_NAME + "\t \n", expectedCommand);

        // multiple duplicated indexes
        expectedCommand = new TagCommand(Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON), new Tag(VALID_TAG_NAME));
        assertParseSuccess(parser, "1,1,1,2,2,3" + " " + VALID_TAG_NAME, expectedCommand);
    }

}
```
###### \java\seedu\address\model\tag\TagContainsKeywordPredicateTest.java
``` java

package seedu.address.model.tag;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class TagContainsKeywordPredicateTest {

    @Test
    public void equals() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));

        // same values -> returns true
        assertTrue(predicate.equals(new TagContainsKeywordPredicate(new Tag("friends"))));

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different person -> returns false
        assertFalse(predicate.equals(new TagContainsKeywordPredicate(new Tag("family"))));
    }

    @Test
    public void test_tagFound_returnsTrue() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withTags("friends", "tester").build()));
    }

    @Test
    public void test_tagNotFound_returnsFalse() throws Exception {
        TagContainsKeywordPredicate predicate = new TagContainsKeywordPredicate(new Tag("friends"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withTags("family", "tester").build()));
    }

}
```
