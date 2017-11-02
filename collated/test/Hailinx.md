# Hailinx
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addTodoItem(ReadOnlyPerson target, TodoItem todoItem)
                throws DuplicatePersonException, PersonNotFoundException, DuplicateTodoItemException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTodoItem(ReadOnlyPerson target, TodoItem todoItem)
                throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void resetTodoItem(ReadOnlyPerson target) throws DuplicatePersonException, PersonNotFoundException {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void updateTodoItemList() {
            fail("This method should not be called.");
        }
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void equals_indFuzzyFind() {
        FuzzySearchPredicate firstPredicate =
                new FuzzySearchPredicate("first");
        FuzzySearchPredicate secondPredicate =
                new FuzzySearchPredicate("second");

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void equals_findInDetails() {
        FindCommand.FindDetailDescriptor firstDescriptor =
                new FindDetailDescriptorBuilder().withName("first").build();
        FindCommand.FindDetailDescriptor secondDescriptor =
                new FindDetailDescriptorBuilder().withName("second").build();

        DetailsContainsPredicate firstPredicate =
                new DetailsContainsPredicate(firstDescriptor);
        DetailsContainsPredicate secondPredicate =
                new DetailsContainsPredicate(secondDescriptor);

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void execute_findModeFuzzy_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("-u 53");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(ALICE, CARL, DANIEL));
    }

    @Test
    public void execute_findInDetail_multiplePersonsFound() throws ParseException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FindCommand command = prepareCommand("-d a/street");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, DANIEL, GEORGE));
    }
```
###### \java\seedu\address\logic\commands\FindDetailDescriptorTest.java
``` java
package seedu.address.logic.commands;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.testutil.FindDetailDescriptorBuilder;

public class FindDetailDescriptorTest {

    @Test
    public void equals() {
        FindCommand.FindDetailDescriptor descriptor1 = new FindDetailDescriptorBuilder().withName("Bob").build();
        FindCommand.FindDetailDescriptor descriptor2 = new FindDetailDescriptorBuilder().withName("Bob").build();
        FindCommand.FindDetailDescriptor descriptor3 = new FindDetailDescriptorBuilder().withEmail("Bob").build();

        Assert.assertTrue(descriptor1.equals(descriptor2));

        Assert.assertTrue(descriptor1.equals(descriptor1));

        Assert.assertFalse(descriptor1.equals(descriptor3));
    }

    @Test
    public void test_toString() {
        FindCommand.FindDetailDescriptor descriptor1 = new FindDetailDescriptorBuilder().withName("Bob").build();
        FindCommand.FindDetailDescriptor descriptor2 = new FindDetailDescriptorBuilder().withName("Bob").build();
        FindCommand.FindDetailDescriptor descriptor3 = new FindDetailDescriptorBuilder().withEmail("Bob").build();

        Assert.assertTrue(descriptor1.toString().equals(descriptor2.toString()));

        Assert.assertFalse(descriptor1.toString().equals(descriptor3.toString()));
    }
}
```
###### \java\seedu\address\logic\commands\LockCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.LockCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.security.SecurityStubUtil;

public class LockCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void test_execute_whenUnSecured() throws ParseException, CommandException {
        new SecurityStubUtil().initialUnSecuredSecurity();

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void test_execute_whenSecured() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecuredSecurity();

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_DUPLICATED_LOCK);
    }

    @Test
    public void test_execute_whenIoexception() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithIoexception(false);

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_ERROR_STORAGE_ERROR);
    }

    @Test
    public void test_execute_whenEncryptOrDecryptException() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithEncryptOrDecryptException(false);

        LockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, LockCommand.MESSAGE_ERROR_LOCK_PASSWORD);
    }

    /**
     * Parses {@code userInput} into a {@code LockCommand} in default mode.
     */
    private LockCommand prepareCommand(String userInput) throws ParseException {
        LockCommand command = new LockCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     */
    private void assertCommandSuccess(LockCommand command, String expectedMessage) throws CommandException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }
}
```
###### \java\seedu\address\logic\commands\UnlockCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.UnlockCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.security.SecurityStubUtil;

public class UnlockCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void test_execute_whenSecured() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecuredSecurity();

        UnlockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, UnlockCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void test_execute_whenUnSecured() throws ParseException, CommandException {
        new SecurityStubUtil().initialUnSecuredSecurity();

        UnlockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, UnlockCommand.MESSAGE_DUPLICATED_UNLOCK);
    }

    @Test
    public void test_execute_whenIoexception() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithIoexception(true);

        UnlockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, UnlockCommand.MESSAGE_ERROR_STORAGE_ERROR);
    }

    @Test
    public void test_execute_whenEncryptOrDecryptException() throws ParseException, CommandException {
        new SecurityStubUtil().initialSecurityWithEncryptOrDecryptException(true);

        UnlockCommand command = prepareCommand("1234");
        assertCommandSuccess(command, UnlockCommand.MESSAGE_ERROR_LOCK_PASSWORD);
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand} in default mode.
     */
    private UnlockCommand prepareCommand(String userInput) throws ParseException {
        UnlockCommand command = new UnlockCommandParser().parse(userInput);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     */
    private void assertCommandSuccess(UnlockCommand command, String expectedMessage) throws CommandException {
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
    }

}
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Before
    public void initialSecurityManager() {
        new SecurityStubUtil().initialUnSecuredSecurity();
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Before
    public void initialSecurityManager() {
        securityStubUtil.initialUnSecuredSecurity();
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_add_alias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddAliasCommand(person));
        assertEquals(new AddCommand(person), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_clear_alias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_delete_alias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_lock() throws Exception {
        String password = "typicalPassword";
        LockCommand command = (LockCommand) parser.parseCommand(
                LockCommand.COMMAND_WORD + " " + password);
        assertEquals(new LockCommand(password), command);
    }

    @Test
    public void parseCommand_unlock() throws Exception {
        String password = "typicalPassword";
        UnlockCommand command = (UnlockCommand) parser.parseCommand(
                UnlockCommand.COMMAND_WORD + " " + password);
        assertEquals(new UnlockCommand(password), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_edit_alias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_find_alias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_history_alias() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_list_alias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_select_alias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + SelectCommand.PREFIX_SELECT_SEARCH_ADDRESS
                        + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON, BrowserSearchMode.GOOGLE_SEARCH_ADDRESS), command);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void secured_noCommandReturn() {
        securityStubUtil.initialSecuredSecurity();

        try {
            parser.parseCommand(AddCommand.COMMAND_WORD + "");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_IS_ENCRYPTD);
        }

        try {
            parser.parseCommand(DeleteCommand.COMMAND_WORD + "");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_IS_ENCRYPTD);
        }

        try {
            parser.parseCommand(LockCommand.COMMAND_WORD + "");
        } catch (ParseException e) {
            assertEquals(e.getMessage(), MESSAGE_IS_ENCRYPTD);
        }
    }
```
###### \java\seedu\address\logic\parser\FindCommandParserTest.java
``` java
    private String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", expectedMessage);
    }

    @Test
    public void parse_invalidModePrefix_throwsParseException() {
        assertParseFailure(parser, "-a", expectedMessage);
        assertParseFailure(parser, "--u", expectedMessage);
        assertParseFailure(parser, "--d", expectedMessage);
    }

    @Test
    public void parse_emptyModeArgs_throwsParseException() {
        assertParseFailure(parser, "-u", expectedMessage);
        assertParseFailure(parser, "-d    ", expectedMessage);
    }

    @Test
    public void parse_validFindFuzzy_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new FuzzySearchPredicate("Ali"));
        assertParseSuccess(parser, "-u Ali", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " -u   Ali    ", expectedFindCommand);
    }

    @Test
    public void parse_validFindInDetails_returnsFindCommand() {
        FindCommand.FindDetailDescriptor expectedDescriptor =
                new FindDetailDescriptorBuilder().withName("ali").withPhone("999").build();
        FindCommand expectedFindCommand =
                new FindCommand(new DetailsContainsPredicate(expectedDescriptor));
        assertParseSuccess(parser, "-d n/ali p/999", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  -d     n/ ali   p/   999", expectedFindCommand);
    }
```
###### \java\seedu\address\logic\parser\LockCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LockCommandParserTest {

    @Test
    public void test_parse() throws ParseException {
        LockCommand command;

        command = new LockCommandParser().parse("1234");
        assertTrue(command.equals(new LockCommand("1234")));

        command = new LockCommandParser().parse("abcd  ddd");
        assertTrue(command.equals(new LockCommand("abcd  ddd")));
    }

    @Test
    public void test_parse_throwsParseException() {

        assertTrue(isThrowsParseException(null));

        assertTrue(isThrowsParseException(""));

        assertTrue(isThrowsParseException("  "));

        assertTrue(isThrowsParseException(" a  "));

        assertTrue(isThrowsParseException("123"));
    }

    /**
     * @return true if a {@code ParseException} is thrown
     */
    private boolean isThrowsParseException(String password) {
        try {
            new LockCommandParser().parse(password);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

}
```
###### \java\seedu\address\logic\parser\optionparser\CommandOptionUtilTest.java
``` java
package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Test;

public class CommandOptionUtilTest {

    @Test
    public void test_getOptionPrefix_returnNotDefault() {
        String optionPrefix = "-a";
        Assert.assertEquals(optionPrefix, CommandOptionUtil.getOptionPrefix(optionPrefix + " " + "ALice"));

        Assert.assertEquals(optionPrefix, CommandOptionUtil.getOptionPrefix(optionPrefix + "   "));

        Assert.assertEquals(optionPrefix, CommandOptionUtil.getOptionPrefix(optionPrefix));
    }

    @Test
    public void test_getOptionPrefix_returnDefault() {
        // Empty string
        Assert.assertEquals(CommandOptionUtil.DEFAULT_OPTION_PREFIX, CommandOptionUtil.getOptionPrefix(""));

        // No prefix
        Assert.assertEquals(CommandOptionUtil.DEFAULT_OPTION_PREFIX, CommandOptionUtil.getOptionPrefix("Alice Bob"));

        // Wrong prefix
        Assert.assertEquals(CommandOptionUtil.DEFAULT_OPTION_PREFIX, CommandOptionUtil.getOptionPrefix("--a"));

        // Wrong prefix position
        Assert.assertEquals(CommandOptionUtil.DEFAULT_OPTION_PREFIX, CommandOptionUtil.getOptionPrefix("Alic -a"));
    }

    @Test
    public void test_getOptionArgs() {
        String args;
        // Empty string
        args = "";
        Assert.assertEquals("", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Empty optionArgs
        args = "-a";
        Assert.assertEquals("", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Empty optionArgs with whitespaces
        args = "-a     ";
        Assert.assertEquals("", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Has optionArgs
        args = "-a Alice";
        Assert.assertEquals("Alice", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // OptionArgs with more whitespaces
        args = "-a   Alice   ";
        Assert.assertEquals("Alice", CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Incorrect option prefix
        args = "--a";
        Assert.assertEquals(args, CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));

        // Incorrect option prefix position
        args = "Alice -a";
        Assert.assertEquals(args, CommandOptionUtil.getOptionArgs(CommandOptionUtil.getOptionPrefix(args), args));
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\FindOptionByNameTest.java
``` java
package seedu.address.logic.parser.optionparser;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindOptionByNameTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindOptionByName("").parse();
    }

    @Test
    public void test_parseKeywords() throws Exception {
        assertParseSuccess(Arrays.asList("Bob"), new FindOptionByName("Bob").parse());
        assertParseSuccess(Arrays.asList("Bob", "Alice"), new FindOptionByName("Bob Alice").parse());
        assertParseSuccess(Arrays.asList("Bob", "Alice", "Coal"), new FindOptionByName("Bob  Alice   Coal").parse());

        assertParseFailure(Arrays.asList("Bob", "Alice"), new FindOptionByName("Alice Bob").parse());
        assertParseFailure(Arrays.asList("Bob", "Ali"), new FindOptionByName("Alice Bob").parse());
    }

    private static void assertParseSuccess(List<String> excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new NameContainsKeywordsPredicate(excepted))));
    }

    private static void assertParseFailure(List<String> excepted, FindCommand actual) {
        Assert.assertFalse(actual.equals(new FindCommand(new NameContainsKeywordsPredicate(excepted))));
    }

    @Test
    public void test_isValidOptionArgs() {
        Assert.assertTrue(new FindOptionByName("abc").isValidOptionArgs());
        Assert.assertTrue(new FindOptionByName("Alic bob").isValidOptionArgs());

        Assert.assertFalse(new FindOptionByName("-d").isValidOptionArgs());
        Assert.assertFalse(new FindOptionByName("--d").isValidOptionArgs());
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\FindOptionFuzzyTest.java
``` java
package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.FuzzySearchPredicate;

public class FindOptionFuzzyTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindOptionFuzzy("").parse();
    }

    @Test
    public void test_parseKeywords() throws Exception {
        assertParseSuccess("Ac", new FindOptionFuzzy("Ac").parse());

        assertParseFailure("Ac", new FindOptionFuzzy("A").parse());
    }

    private static void assertParseSuccess(String excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new FuzzySearchPredicate(excepted))));
    }

    private static void assertParseFailure(String excepted, FindCommand actual) {
        Assert.assertFalse(actual.equals(new FindCommand(new FuzzySearchPredicate(excepted))));
    }
}
```
###### \java\seedu\address\logic\parser\optionparser\FindOptionInDetailTest.java
``` java
package seedu.address.logic.parser.optionparser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DetailsContainsPredicate;
import seedu.address.testutil.FindDetailDescriptorBuilder;

public class FindOptionInDetailTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_parseEmptyArg_throwException() throws Exception {
        thrown.expect(ParseException.class);
        new FindOptionInDetail("").parse();
    }

    @Test
    public void test_parseOptionArgs_assertSuccess() throws Exception {
        String inputOptionArgs;
        FindDetailDescriptor expectedDescriptor;

        // test name
        inputOptionArgs = "n/Ali";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test phone
        inputOptionArgs = "p/999";
        expectedDescriptor = new FindDetailDescriptorBuilder().withPhone("999").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test email
        inputOptionArgs = "e/com";
        expectedDescriptor = new FindDetailDescriptorBuilder().withEmail("com").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test address
        inputOptionArgs = "a/pgp";
        expectedDescriptor = new FindDetailDescriptorBuilder().withAddress("pgp").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test one tag
        inputOptionArgs = "t/fr";
        expectedDescriptor = new FindDetailDescriptorBuilder().withTags("fr").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test tags
        inputOptionArgs = "t/fr t/family";
        expectedDescriptor = new FindDetailDescriptorBuilder().withTags("fr", "family").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test mix
        inputOptionArgs = "n/alic a/pgp";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("alic").withAddress("pgp").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test mix
        inputOptionArgs = "e/12 p/qq";
        expectedDescriptor = new FindDetailDescriptorBuilder().withEmail("12").withPhone("qq").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test mix
        inputOptionArgs = "n/alic p/999 e/u.nus.edu a/utown t/friend t/classmate";
        expectedDescriptor = new FindDetailDescriptorBuilder()
                .withName("alic").withPhone("999").withEmail("u.nus.edu")
                .withAddress("utown").withTags("friend", "classmate").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test add whitespace
        inputOptionArgs = "n/ Ali";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());

        // test add whitespace
        inputOptionArgs = " n/ Ali ";
        expectedDescriptor = new FindDetailDescriptorBuilder().withName("Ali").build();
        assertParseSuccess(expectedDescriptor, new FindOptionInDetail(inputOptionArgs).parse());
    }

    @Test
    public void test_inValidDescriptor_wrongPrefix() throws Exception {
        thrown.expect(ParseException.class);
        String inputOptionArgs = "pp/999";
        new FindOptionInDetail(inputOptionArgs).parse();
    }

    @Test
    public void test_inValidDescriptor_noPrefix() throws Exception {
        thrown.expect(ParseException.class);
        String inputOptionArgs = "pgp";
        new FindOptionInDetail(inputOptionArgs).parse();
    }

    private static void assertParseSuccess(FindDetailDescriptor excepted, FindCommand actual) {
        Assert.assertTrue(actual.equals(new FindCommand(new DetailsContainsPredicate(excepted))));
    }

}
```
###### \java\seedu\address\logic\parser\SelectCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.SelectCommand;
import seedu.address.ui.BrowserSearchMode;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectCommandParserTest {

    private SelectCommandParser parser = new SelectCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "-n 1",
                new SelectCommand(INDEX_FIRST_PERSON, BrowserSearchMode.GOOGLE_SEARCH_NAME));

        assertParseSuccess(parser, "-p 2",
                new SelectCommand(INDEX_SECOND_PERSON, BrowserSearchMode.GOOGLE_SEARCH_PHONE));

        assertParseSuccess(parser, "-e 2",
                new SelectCommand(INDEX_SECOND_PERSON, BrowserSearchMode.GOOGLE_SEARCH_EMAIL));

        assertParseSuccess(parser, "-a 3",
                new SelectCommand(INDEX_THIRD_PERSON, BrowserSearchMode.GOOGLE_SEARCH_ADDRESS));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "5", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "--", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "--a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-n5", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "-f", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, SelectCommand.PREFIX_SELECT_SEARCH_EMAIL + " 0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        assertParseFailure(parser, SelectCommand.PREFIX_SELECT_SEARCH_ADDRESS + " -1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\UnlockCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnlockCommandParserTest {

    @Test
    public void test_parse() throws ParseException {
        UnlockCommand command;

        command = new UnlockCommandParser().parse("1234");
        assertTrue(command.equals(new UnlockCommand("1234")));

        command = new UnlockCommandParser().parse("abcd  ddd");
        assertTrue(command.equals(new UnlockCommand("abcd  ddd")));
    }

    @Test
    public void test_parse_throwsParseException() {

        assertTrue(isThrowsParseException(null));

        assertTrue(isThrowsParseException(""));

        assertTrue(isThrowsParseException("   "));

        assertTrue(isThrowsParseException("  a  "));

        assertTrue(isThrowsParseException("abc"));
    }

    /**
     * @return true if a {@code ParseException} is thrown
     */
    private boolean isThrowsParseException(String password) {
        try {
            new UnlockCommandParser().parse(password);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
```
###### \java\seedu\address\model\person\DetailsContainsPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.testutil.FindDetailDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

public class DetailsContainsPredicateTest {

    @Test
    public void equals() {
        FindDetailDescriptor firstPredicateDescriptor = new FindDetailDescriptorBuilder().withName("Alice").build();
        FindDetailDescriptor secondPredicateDescriptor = new FindDetailDescriptorBuilder().withPhone("123456").build();

        DetailsContainsPredicate firstPredicate = new DetailsContainsPredicate(firstPredicateDescriptor);
        DetailsContainsPredicate secondPredicate = new DetailsContainsPredicate(secondPredicateDescriptor);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DetailsContainsPredicate firstPredicateCopy = new DetailsContainsPredicate(firstPredicateDescriptor);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchAllAttributes_returnsTrue() {
        ReadOnlyPerson person = new PersonBuilder().build();

        // same name -> returns true
        FindDetailDescriptor descriptor =
                new FindDetailDescriptorBuilder().withName(PersonBuilder.DEFAULT_NAME).build();
        DetailsContainsPredicate predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));

        // name is the substring in person name -> returns true
        descriptor = new FindDetailDescriptorBuilder().withName("alic").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));

        // phone and email are substring in person -> returns true
        descriptor = new FindDetailDescriptorBuilder().withPhone("8535").withEmail("ali").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));

        // address is substring in person address -> returns true
        descriptor = new FindDetailDescriptorBuilder().withAddress("jurong WEST").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));

        // tag is substring in person tag -> returns true
        descriptor = new FindDetailDescriptorBuilder().withTags("frie").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_notMatch_returnsFalse() {
        ReadOnlyPerson person = new PersonBuilder().build();

        // different name -> returns false
        FindDetailDescriptor descriptor = new FindDetailDescriptorBuilder().withName("aliv").build();
        DetailsContainsPredicate predicate = new DetailsContainsPredicate(descriptor);
        assertFalse(predicate.test(person));

        // email is matched but phone is different -> returns false
        descriptor = new FindDetailDescriptorBuilder().withPhone("0000").withEmail("ali").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertFalse(predicate.test(person));

        // different address -> returns false
        descriptor = new FindDetailDescriptorBuilder().withAddress("pgp").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertFalse(predicate.test(person));

        // different tag -> returns false
        descriptor = new FindDetailDescriptorBuilder().withTags("family").build();
        predicate = new DetailsContainsPredicate(descriptor);
        assertFalse(predicate.test(person));
    }
}
```
###### \java\seedu\address\model\person\FuzzySearchPredicateTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class FuzzySearchPredicateTest {

    @Test
    public void equals() {
        FuzzySearchPredicate firstPredicate = new FuzzySearchPredicate("first keyword");
        FuzzySearchPredicate secondPredicate = new FuzzySearchPredicate("second keyword");

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FuzzySearchPredicate firstPredicateCopy = new FuzzySearchPredicate("first keyword");
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_containedInPerson_returnsTrue() {
        FuzzySearchPredicate predicate;
        ReadOnlyPerson person = new PersonBuilder().build();

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_NAME);
        assertTrue(predicate.test(person));

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_PHONE.substring(2, 4));
        assertTrue(predicate.test(person));

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_EMAIL.substring(2, 4));
        assertTrue(predicate.test(person));

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_ADDRESS.substring(2, 4));
        assertTrue(predicate.test(person));

        predicate = new FuzzySearchPredicate(PersonBuilder.DEFAULT_TAGS.substring(1));
        assertTrue(predicate.test(person));
    }

    @Test
    public void test_containedInPerson_returnsFalse() {
        FuzzySearchPredicate predicate;
        ReadOnlyPerson person = new PersonBuilder().build();

        predicate = new FuzzySearchPredicate("Bob");
        assertFalse(predicate.test(person));

        predicate = new FuzzySearchPredicate("family");
        assertFalse(predicate.test(person));

        predicate = new FuzzySearchPredicate("999");
        assertFalse(predicate.test(person));

        predicate = new FuzzySearchPredicate("@u.nus.edu");
        assertFalse(predicate.test(person));

        predicate = new FuzzySearchPredicate("Utown");
        assertFalse(predicate.test(person));
    }
}
```
###### \java\seedu\address\security\SecurityManagerTest.java
``` java
package seedu.address.security;

import java.io.IOException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.EncryptOrDecryptException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;

public class SecurityManagerTest {

    private Storage storage = new StorageStub();

    @Test
    public void test_equalInstance() {
        Security instance1 = SecurityManager.getInstance(storage);
        Security instance2 = SecurityManager.getInstance();
        Security instance3 = SecurityManager.getInstance(instance1);
        Security instance4 = SecurityManager.getInstance(instance2);

        Assert.assertTrue(instance1 == instance2);
        Assert.assertTrue(instance2 == instance3);
        Assert.assertTrue(instance3 == instance4);
    }

    @Test
    public void test_commandBlocking() {
        Security security = SecurityManager.getInstance(storage);
        security.configSecurity("add", "find");

        Assert.assertTrue(security.isPermittedCommand("add"));
        Assert.assertTrue(security.isPermittedCommand("find"));

        Assert.assertFalse(security.isPermittedCommand(""));
        Assert.assertFalse(security.isPermittedCommand("list"));
    }

    @Test
    public void test_raise() {
        Security security = SecurityManager.getInstance(storage);
        security.raise(new NewResultAvailableEvent("new result"));
    }

    @Test
    public void test_isSecured() {
        Security security = SecurityManager.getInstance(storage);
        Assert.assertFalse(security.isSecured());
    }

    @Test
    public void test_isEncrypted() throws IOException {
        Security security = SecurityManager.getInstance(storage);
        Assert.assertFalse(security.isEncrypted());
    }

    @Test
    public void test_encryptAddressBook() throws IOException, EncryptOrDecryptException {
        Security security = SecurityManager.getInstance(storage);
        security.encryptAddressBook("");
    }

    @Test
    public void test_decryptAddressBook() throws IOException, EncryptOrDecryptException {
        Security security = SecurityManager.getInstance(storage);
        security.decryptAddressBook("");
    }

    private class StorageStub implements Storage {

        @Override
        public String getUserPrefsFilePath() {
            return null;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
            return null;
        }

        @Override
        public void saveUserPrefs(UserPrefs userPrefs) throws IOException {

        }

        @Override
        public String getAddressBookFilePath() {
            return null;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws
                DataConversionException, IOException {
            return null;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(String filePath)
                throws DataConversionException, IOException {
            return null;
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {

        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {

        }

        @Override
        public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {

        }

        @Override
        public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {

        }

        @Override
        public boolean isEncrypted() throws IOException {
            return false;
        }

        @Override
        public void encryptAddressBook(String password)
                throws IOException, EncryptOrDecryptException {

        }

        @Override
        public void decryptAddressBook(String password)
                throws IOException, EncryptOrDecryptException {

        }
    }
}
```
###### \java\seedu\address\security\SecurityStubUtil.java
``` java
package seedu.address.security;

import java.io.IOException;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;

/**
 * Provides Security Stubs for testing.
 */
public class SecurityStubUtil {

    /**
     * Represents a basic SecurityManager stub.
     */
    private class BaseSecurityStub implements Security {

        private boolean isSecured;

        public BaseSecurityStub(boolean isSecured) {
            super();
            this.isSecured = isSecured;
        }

        @Override
        public void configSecurity(String... permittedCommands) {
        }

        @Override
        public boolean isSecured() {
            return isSecured;
        }

        @Override
        public void raise(BaseEvent event) {
        }

        @Override
        public boolean isPermittedCommand(String commandWord) {
            return false;
        }

        @Override
        public boolean isEncrypted() throws IOException {
            return isSecured;
        }

        @Override
        public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        }

        @Override
        public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        }
    }

    /**
     * Represents a SecurityManager which indicates that the address book is secured.
     */
    private class SecurityStubIoexception extends BaseSecurityStub {


        public SecurityStubIoexception(boolean isSecured) {
            super(isSecured);
        }

        @Override
        public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            throw new IOException();
        }

        @Override
        public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            throw new IOException();
        }
    }

    /**
     * Represents a SecurityManager which indicates that the address book is secured.
     */
    private class SecurityStubEncryptOrDecryptException extends BaseSecurityStub {


        public SecurityStubEncryptOrDecryptException(boolean isSecured) {
            super(isSecured);
        }

        @Override
        public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            throw new EncryptOrDecryptException();
        }

        @Override
        public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            throw new EncryptOrDecryptException();
        }
    }

    public void initialUnSecuredSecurity() {
        SecurityManager.getInstance(new BaseSecurityStub(false));
    }

    public void initialSecuredSecurity() {
        SecurityManager.getInstance(new BaseSecurityStub(true));
    }

    public void initialSecurityWithIoexception(boolean isSecured) {
        SecurityManager.getInstance(new SecurityStubIoexception(isSecured));
    }

    public void initialSecurityWithEncryptOrDecryptException(boolean isSecured) {
        SecurityManager.getInstance(new SecurityStubEncryptOrDecryptException(isSecured));
    }
}
```
###### \java\seedu\address\security\SecurityUtilTest.java
``` java
package seedu.address.security;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.EncryptOrDecryptException;

public class SecurityUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private File file;

    @Before
    public void init() throws IOException {
        file = new File(testFolder.getRoot().getPath() + "TempAddressBook.xml");
    }

    @Test
    public void test_isPasswordValid() {
        Assert.assertTrue(SecurityUtil.isValidPassword("1234"));

        Assert.assertFalse(SecurityUtil.isValidPassword(null));
        Assert.assertFalse(SecurityUtil.isValidPassword(""));
        Assert.assertFalse(SecurityUtil.isValidPassword("12"));
        Assert.assertFalse(SecurityUtil.isValidPassword("dd"));
    }

    @Test
    public void test_encrypt_noException() throws Exception {
        String password = "tempPassword";

        // encrypt normal file
        write_tempXmlFile("a normal file");
        SecurityUtil.encrypt(file, password);

        // encrypt a xml file
        write_tempXmlFile(SecurityUtil.XML_STARTER + "<addressbook></addressbook>");
        SecurityUtil.encrypt(file, password);

        // encrypt file without content
        write_tempXmlFile("");
        SecurityUtil.encrypt(file, password);
    }

    @Test
    public void test_encrypt_throwIoexception() throws Exception {
        thrown.expect(IOException.class);

        String password = "tempPassword";

        SecurityUtil.encrypt(new File(""), password);

        SecurityUtil.encrypt(new File("abcdefg"), password);
    }

    @Test
    public void test_decrypt_noException() throws Exception {
        String password = "tempPassword";

        // encrypt a normal xml file and then decrypt it
        String fileStr = SecurityUtil.XML_STARTER + "a normal file";
        write_tempXmlFile(fileStr);
        SecurityUtil.encrypt(file, password);
        SecurityUtil.decrypt(file, password);
        Assert.assertEquals(fileStr, read_tempXmlFile());

        // encrypt a empty xml file and then decrypt it
        fileStr = SecurityUtil.XML_STARTER + "";
        write_tempXmlFile(fileStr);
        SecurityUtil.encrypt(file, password);
        SecurityUtil.decrypt(file, password);
        Assert.assertEquals(fileStr, read_tempXmlFile());
    }

    @Test
    public void test_decryptWrongPassword_throwsEncryptOrDecryptException() throws Exception {
        thrown.expect(EncryptOrDecryptException.class);

        // encrypt a normal xml file and then decrypt it
        String fileStr = SecurityUtil.XML_STARTER + "a normal file";
        write_tempXmlFile(fileStr);
        SecurityUtil.encrypt(file, "the first");
        SecurityUtil.decrypt(file, "the second");
    }

    @Test
    public void test_decryptNotEncryptedFile_throwsEncryptOrDecryptException() throws Exception {
        thrown.expect(EncryptOrDecryptException.class);

        // encrypt a normal xml file and then decrypt it
        String fileStr = "a normal file";
        write_tempXmlFile(fileStr);
        SecurityUtil.decrypt(file, "the second");
    }

    /**
     * Writes {@code inputString} into the file.
     */
    public void write_tempXmlFile(String inputString) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(inputString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Write to temp file failed.");
        }
    }

    /**
     * Reads from the file.
     */
    public String read_tempXmlFile() {
        try {
            Reader reader = new FileReader(file);
            char[] buffer = new char[(int) file.length()];
            reader.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Read temp file failed.");
        }
        return null;
    }

}
```
###### \java\seedu\address\storage\XmlAddressBookStorageTest.java
``` java
    @Test
    public void test_isEncrypted() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file
        AddressBook original = getTypicalAddressBook();
        xmlAddressBookStorage.saveAddressBook(original, filePath);

        assertFalse(xmlAddressBookStorage.isEncrypted());

        xmlAddressBookStorage.encryptAddressBook("tempPassword");

        assertTrue(xmlAddressBookStorage.isEncrypted());

        xmlAddressBookStorage.decryptAddressBook("tempPassword");

        assertFalse(xmlAddressBookStorage.isEncrypted());
    }

    @Test
    public void decrypt_notEncrypt_throwsException() throws Exception {
        thrown.expect(EncryptOrDecryptException.class);

        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file
        AddressBook original = getTypicalAddressBook();
        xmlAddressBookStorage.saveAddressBook(original, filePath);

        xmlAddressBookStorage.decryptAddressBook("tempPassword");
    }

    @Test
    public void decrypt_wrongPassword_throwsException() throws Exception {
        thrown.expect(EncryptOrDecryptException.class);

        String filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);

        //Save in new file
        AddressBook original = getTypicalAddressBook();
        xmlAddressBookStorage.saveAddressBook(original, filePath);

        xmlAddressBookStorage.encryptAddressBook("password");
        xmlAddressBookStorage.decryptAddressBook("wrong password");
    }
```
###### \java\seedu\address\testutil\FindDetailDescriptorBuilder.java
``` java
package seedu.address.testutil;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FindCommand.FindDetailDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * A utility class to help with building FindDetailDescriptorBuilder objects.
 */
public class FindDetailDescriptorBuilder {

    private FindDetailDescriptor descriptor;

    public FindDetailDescriptorBuilder() {
        descriptor = new FindDetailDescriptor();
    }

    /**
     * Returns an {@code FindDetailDescriptorBuilder} with fields containing {@code person}'s details
     * in string and tag set.
     */
    public FindDetailDescriptorBuilder(ReadOnlyPerson person) {
        descriptor = new FindDetailDescriptor();
        descriptor.setName(person.getName().fullName);
        descriptor.setPhone(person.getPhone().value);
        descriptor.setEmail(person.getEmail().value);
        descriptor.setAddress(person.getAddress().value);
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code name} of the {@code FindDetailDescriptor} that we are building.
     */
    public FindDetailDescriptorBuilder withName(String name) {
        Optional.of(name).ifPresent(descriptor::setName);
        return this;
    }

    /**
     * Sets the {@code phone} of the {@code FindDetailDescriptor} that we are building.
     */
    public FindDetailDescriptorBuilder withPhone(String phone) {
        Optional.of(phone).ifPresent(descriptor::setPhone);
        return this;
    }

    /**
     * Sets the {@code email} of the {@code FindDetailDescriptor} that we are building.
     */
    public FindDetailDescriptorBuilder withEmail(String email) {
        Optional.of(email).ifPresent(descriptor::setEmail);
        return this;
    }

    /**
     * Sets the {@code address} of the {@code FindDetailDescriptor} that we are building.
     */
    public FindDetailDescriptorBuilder withAddress(String address) {
        Optional.of(address).ifPresent(descriptor::setAddress);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code FindDetailDescriptorBuilder}
     * that we are building.
     */
    public FindDetailDescriptorBuilder withTags(String... tags) {
        try {
            descriptor.setTags(ParserUtil.parseTags(Arrays.asList(tags)));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("tags are expected to be unique.");
        }
        return this;
    }

    public FindDetailDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
    @Test
    public void test_getUrl() {
        ReadOnlyPerson person = ALICE;

        postNow(new ChangeSearchEvent(GOOGLE_SEARCH_NAME));
        String searchNameUrl = GOOGLE_SEARCH_URL_PREFIX + "Alice+Pauline" + GOOGLE_SEARCH_URL_SUFFIX;
        assertEquals(browserPanel.getUrl(person), searchNameUrl);

        postNow(new ChangeSearchEvent(GOOGLE_SEARCH_PHONE));
        String searchPhoneUrl = GOOGLE_SEARCH_URL_PREFIX + "85355255" + GOOGLE_SEARCH_URL_SUFFIX;
        assertEquals(browserPanel.getUrl(person), searchPhoneUrl);

        postNow(new ChangeSearchEvent(GOOGLE_SEARCH_EMAIL));
        String searchEmailUrl = GOOGLE_SEARCH_URL_PREFIX + "alice@example.com" + GOOGLE_SEARCH_URL_SUFFIX;
        assertEquals(browserPanel.getUrl(person), searchEmailUrl);

        postNow(new ChangeSearchEvent(GOOGLE_SEARCH_ADDRESS));
        String searchAddressUrl = GOOGLE_MAP_URL_PREFIX + "123,+Jurong+West+Ave+6,+#08-111";
        assertEquals(browserPanel.getUrl(person), searchAddressUrl);
    }
```
