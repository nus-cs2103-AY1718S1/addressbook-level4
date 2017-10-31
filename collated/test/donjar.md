# donjar
###### /java/seedu/address/model/person/PhoneTest.java
``` java
    @Test
    public void extractPhone() throws IllegalValueException {
        assertEquals("93121534", Phone.extractPhone("9312 1534")); // spaces within digits
        assertEquals("93121534", Phone.extractPhone("9312-1534")); // dashes within digits
        assertEquals("93121534", Phone.extractPhone("(9312) 1534")); // parentheses within digits
        assertEquals("1234567890", Phone.extractPhone("(123) 456-7890")); // complex phone number
    }
```
###### /java/seedu/address/model/person/NameMatchesRegexTest.java
``` java

package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class NameMatchesRegexTest {

    @Test
    public void equals() {
        String firstRegex = "^asdf$";
        String secondRegex = "a+s";

        NameMatchesRegexPredicate firstPredicate = new NameMatchesRegexPredicate(firstRegex);
        NameMatchesRegexPredicate secondPredicate = new NameMatchesRegexPredicate(secondRegex);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameMatchesRegexPredicate firstPredicateCopy = new NameMatchesRegexPredicate(firstRegex);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords() {
        NameMatchesRegexPredicate predicate = new NameMatchesRegexPredicate("^Man[mn]a$");
        assertTrue(predicate.test(new PersonBuilder().withName("Manna").build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Manma").build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Manma Chi").build()));
    }
}
```
###### /java/seedu/address/model/person/NameTest.java
``` java
        assertTrue(Name.isValidName("p")); // one letter
```
###### /java/seedu/address/model/person/NameTest.java
``` java
        assertTrue(Name.isValidName("Nguyễn Nguyễn Nguyễn")); // Vietnamese name sample
        assertTrue(Name.isValidName("习近平")); // Chinese name sample
```
###### /java/seedu/address/logic/commands/UndoCommandTest.java
``` java
    @Test
    public void execute_undoManyTimes() {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand(2);
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        String successMessage = UndoCommand.getSuccessMessage(2);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, successMessage, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_undoTooManyTimes() {
        UndoRedoStack undoRedoStack = prepareStack(
                Arrays.asList(deleteCommandOne, deleteCommandTwo), Collections.emptyList());
        UndoCommand undoCommand = new UndoCommand(3);
        undoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);

        String successMessage = UndoCommand.getSuccessMessage(2);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(undoCommand, model, successMessage, expectedModel);

        // no command in undoStack
        assertCommandFailure(undoCommand, model, UndoCommand.MESSAGE_FAILURE);
    }
```
###### /java/seedu/address/logic/commands/RedoCommandTest.java
``` java
    @Test
    public void execute_redoManyTimes() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand(2);
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String successMessage = RedoCommand.getSuccessMessage(2);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, successMessage, expectedModel);

        // no command in undoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void execute_redoTooManyTimes() {
        UndoRedoStack undoRedoStack = prepareStack(
                Collections.emptyList(), Arrays.asList(deleteCommandTwo, deleteCommandOne));
        RedoCommand redoCommand = new RedoCommand(3);
        redoCommand.setData(model, EMPTY_COMMAND_HISTORY, undoRedoStack);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String successMessage = RedoCommand.getSuccessMessage(2);

        deleteFirstPerson(expectedModel);
        deleteFirstPerson(expectedModel);
        assertCommandSuccess(redoCommand, model, successMessage, expectedModel);

        // no command in undoStack
        assertCommandFailure(redoCommand, model, RedoCommand.MESSAGE_FAILURE);
    }
```
###### /java/seedu/address/logic/commands/SizeCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.FontSizeChangeRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SizeCommand}.
 */
public class SizeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_resetSize() {
        SizeCommand sizeCommand = prepareCommand();
        try {
            CommandResult commandResult = sizeCommand.execute();

            assertEquals(SizeCommand.MESSAGE_RESET_FONT_SUCCESS, commandResult.feedbackToUser);

            FontSizeChangeRequestEvent lastEvent =
                    (FontSizeChangeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(0, lastEvent.sizeChange);
        } catch (CommandException e) {
            fail("This exception should not be thrown.");
        }
    }

    @Test
    public void execute_increaseSize() {
        assertExecutionSuccess(3, String.format(SizeCommand.MESSAGE_CHANGE_FONT_SUCCESS, "increased", 3, 3));
    }

    @Test
    public void execute_decreaseSize() {
        assertExecutionSuccess(-3, String.format(SizeCommand.MESSAGE_CHANGE_FONT_SUCCESS, "decreased", 3, -3));
    }

    @Test
    public void execute_increaseSizeOutOfBounds() {
        assertExecutionFailure(6);
    }

    @Test
    public void execute_decreaseSizeOutOfBounds() {
        assertExecutionFailure(-6);
    }

    @Test
    public void equals() {
        SizeCommand sizeResetCommand = new SizeCommand();
        SizeCommand sizeIncrementCommand = new SizeCommand(1);
        SizeCommand sizeDecrementCommand = new SizeCommand(-1);

        // same object -> returns true
        assertTrue(sizeResetCommand.equals(sizeResetCommand));

        // same values -> returns true
        SizeCommand sizeResetCommandCopy = new SizeCommand();
        assertTrue(sizeResetCommand.equals(sizeResetCommandCopy));

        // different types -> returns false
        assertFalse(sizeResetCommand.equals(1));

        // null -> returns false
        assertFalse(sizeResetCommand.equals(null));

        // different type -> returns false
        assertFalse(sizeResetCommand.equals(sizeIncrementCommand));

        // different value -> returns false
        assertFalse(sizeDecrementCommand.equals(sizeIncrementCommand));
    }

    /**
     * Returns a {@code SizeCommand} with parameters given.
     */
    private SizeCommand prepareCommand() {
        SizeCommand sizeCommand = new SizeCommand();
        sizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sizeCommand;
    }

    private SizeCommand prepareCommand(int change) {
        SizeCommand sizeCommand = new SizeCommand(change);
        sizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sizeCommand;
    }

    /**
     * Executes a {@code SizeCommand} with the given {@code change}, and checks that {@code FontSizeChangeRequestEvent}
     * is raised with the correct size change.
     */
    private void assertExecutionSuccess(int change, String message) {
        SizeCommand sizeCommand = prepareCommand(change);
        try {
            CommandResult commandResult = sizeCommand.execute();

            assertEquals(message, commandResult.feedbackToUser);

            FontSizeChangeRequestEvent lastEvent =
                    (FontSizeChangeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(change, lastEvent.sizeChange);
        } catch (CommandException e) {
            fail("This exception should not be thrown.");
        }
    }

    /**
     * Executes a {@code SizeCommand} with the given {@code change}, and checks that a {@code CommandException}
     * is thrown.
     */
    private void assertExecutionFailure(int change) {
        SizeCommand sizeCommand = prepareCommand(change);
        try {
            sizeCommand.execute();
            fail("CommandException should be thrown.");
        } catch (CommandException e) {
            assertEquals(String.format(SizeCommand.MESSAGE_FAILURE, 0, change), e.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
```
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void resetFontSize() {
            fail("This method should not be called.");
        }

        @Override
        public int updateFontSize(int change) throws FontSizeOutOfBoundsException {
            fail("This method should not be called.");
            return 0;
        }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_findregex() throws Exception {
        FindRegexCommand command = (FindRegexCommand) parser.parseCommand(FindRegexCommand.COMMAND_WORD + " asdf");
        assertEquals(new FindRegexCommand(new NameMatchesRegexPredicate("asdf")), command);
    }

    @Test
    public void parseCommand_findregex_alias() throws Exception {
        FindRegexCommand command = (FindRegexCommand) parser.parseCommand(FindRegexCommand.COMMAND_ALIAS + " asdf");
        assertEquals(new FindRegexCommand(new NameMatchesRegexPredicate("asdf")), command);
    }
```
###### /java/seedu/address/logic/parser/AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sizeCommandWord_returnsSizeCommand() throws Exception {
        assertTrue(parser.parseCommand(SizeCommand.COMMAND_WORD + " 1") instanceof SizeCommand);
        assertTrue(parser.parseCommand(SizeCommand.COMMAND_WORD + " -1") instanceof SizeCommand);
        assertTrue(parser.parseCommand(SizeCommand.COMMAND_WORD) instanceof SizeCommand);
    }

    @Test
    public void parseCommand_sizeCommandAlias_returnsSizeCommand() throws Exception {
        assertTrue(parser.parseCommand(SizeCommand.COMMAND_ALIAS + " 1") instanceof SizeCommand);
        assertTrue(parser.parseCommand(SizeCommand.COMMAND_ALIAS + " -1") instanceof SizeCommand);
        assertTrue(parser.parseCommand(SizeCommand.COMMAND_ALIAS) instanceof SizeCommand);
    }
```
###### /java/seedu/address/logic/parser/UndoCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.UndoCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class UndoCommandParserTest {

    private UndoCommandParser parser = new UndoCommandParser();

    @Test
    public void parse_validArgs_returnsUndoCommand() {
        assertParseSuccess(parser, "12", new UndoCommand(12));
    }

    @Test
    public void parse_emptyArgs_returnsUndoCommand() {
        assertParseSuccess(parser, "", new UndoCommand(1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, UndoCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/FindRegexCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REGEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FindRegexCommand;
import seedu.address.model.person.NameMatchesRegexPredicate;

public class FindRegexCommandParserTest {

    private FindRegexCommandParser parser = new FindRegexCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "(", String.format(MESSAGE_INVALID_REGEX, FindRegexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindRegexCommand() {
        String[] regexesToTest = new String[] { "abcd", "a   b", "^ab$", "23(x)\\1" };
        for (String regex : regexesToTest) {
            FindRegexCommand expectedCommand = new FindRegexCommand(new NameMatchesRegexPredicate(regex));
            assertParseSuccess(parser, regex, expectedCommand);
        }
    }

}
```
###### /java/seedu/address/logic/parser/RedoCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class RedoCommandParserTest {

    private RedoCommandParser parser = new RedoCommandParser();

    @Test
    public void parse_validArgs_returnsRedoCommand() {
        assertParseSuccess(parser, "12", new RedoCommand(12));
    }

    @Test
    public void parse_emptyArgs_returnsRedoCommand() {
        assertParseSuccess(parser, "", new RedoCommand(1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RedoCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java
    @Test
    public void parsePositiveInteger_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(1, ParserUtil.parsePositiveInteger("1"));
        assertEquals(12, ParserUtil.parsePositiveInteger("12"));

        // Leading and trailing whitespaces
        assertEquals(1, ParserUtil.parsePositiveInteger(" 1"));
        assertEquals(1, ParserUtil.parsePositiveInteger("1 "));
        assertEquals(1, ParserUtil.parsePositiveInteger(" 1 "));
    }

    @Test
    public void parsePositiveInteger_zeroInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_NUMBER);

        ParserUtil.parsePositiveInteger("0");
    }

    @Test
    public void parsePositiveInteger_negativeInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_NUMBER);

        ParserUtil.parsePositiveInteger("-3");
    }

    @Test
    public void parsePositiveInteger_nonIntegerInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_NUMBER);

        ParserUtil.parsePositiveInteger("0.3");
    }

    @Test
    public void parsePositiveInteger_nonNumberInput_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(MESSAGE_INVALID_NUMBER);

        ParserUtil.parsePositiveInteger("afg");
    }

```
###### /java/seedu/address/logic/parser/ParserUtilTest.java
``` java

    @Test
    public void parseName_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseName(null);
    }

    @Test
    public void parseName_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseName(Optional.of(INVALID_NAME));
    }

    @Test
    public void parseName_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseName(Optional.empty()).isPresent());
    }

    @Test
    public void parseName_validValue_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Optional<Name> actualName = ParserUtil.parseName(Optional.of(VALID_NAME));

        assertEquals(expectedName, actualName.get());
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePhone(null);
    }

    @Test
    public void parsePhone_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhone(Optional.of(INVALID_PHONE));
    }

    @Test
    public void parsePhone_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhone(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhone_validValue_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Optional<Phone> actualPhone = ParserUtil.parsePhone(Optional.of(VALID_PHONE));

        assertEquals(expectedPhone, actualPhone.get());
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseAddress(null);
    }

    @Test
    public void parseAddress_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseAddress(Optional.of(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseAddress(Optional.empty()).isPresent());
    }

    @Test
    public void parseAddress_validValue_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        Optional<Address> actualAddress = ParserUtil.parseAddress(Optional.of(VALID_ADDRESS));

        assertEquals(expectedAddress, actualAddress.get());
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEmail(null);
    }

    @Test
    public void parseEmail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseEmail(Optional.of(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseEmail(Optional.empty()).isPresent());
    }

    @Test
    public void parseEmail_validValue_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Optional<Email> actualEmail = ParserUtil.parseEmail(Optional.of(VALID_EMAIL));

        assertEquals(expectedEmail, actualEmail.get());
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
```
