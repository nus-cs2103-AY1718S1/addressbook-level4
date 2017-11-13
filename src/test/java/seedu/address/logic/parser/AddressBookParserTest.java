package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BODY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BODY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_SUBJECT_DESC;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonsWithTagsCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTagsCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.SendEmailCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonContainsTagsPredicate;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    private final ArrayList<String> listOfAllCommandWordsAndAliases =
            AllCommandWordsAndAliases.getListOfAllCommandWordsAndAliases();

    @BeforeClass
    public static void createListOfAllCommandWordsAndAliases() {
        AllCommandWordsAndAliases.initializeListOfAllCommandWordsAndAliases();
    }

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);

        listOfAllCommandWordsAndAliases.add(AddCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_add_alias() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);

        listOfAllCommandWordsAndAliases.add(AddCommand.COMMAND_ALIAS);
    }

    //@@author srishag
    @Test
    public void parseCommand_addTask() throws Exception {
        Task task = new TaskBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(TaskUtil.getAddTaskCommand(task));
        assertEquals(new AddTaskCommand(task), command);

        listOfAllCommandWordsAndAliases.add(AddTaskCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_addTask_alias() throws Exception {
        Task task = new TaskBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(TaskUtil.getAddTaskCommand(task));
        assertEquals(new AddTaskCommand(task), command);

        listOfAllCommandWordsAndAliases.add(AddTaskCommand.COMMAND_ALIAS);
    }
    //@@author

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);

        listOfAllCommandWordsAndAliases.add(ClearCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_clear_alias() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS + " 3") instanceof ClearCommand);

        listOfAllCommandWordsAndAliases.add(ClearCommand.COMMAND_ALIAS);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);

        listOfAllCommandWordsAndAliases.add(DeleteCommand.COMMAND_WORD);

    }

    @Test
    public void parseCommand_delete_alias() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);

        listOfAllCommandWordsAndAliases.add(DeleteCommand.COMMAND_ALIAS);
    }

    //@@author srishag
    @Test
    public void parseCommand_deleteTask() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_TASK), command);

        listOfAllCommandWordsAndAliases.add(DeleteTaskCommand.COMMAND_WORD);

    }

    @Test
    public void parseCommand_deleteTask_alias() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
                DeleteTaskCommand.COMMAND_ALIAS + " " + INDEX_FIRST_TASK.getOneBased());
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_TASK), command);

        listOfAllCommandWordsAndAliases.add(DeleteTaskCommand.COMMAND_ALIAS);
    }
    //@@author

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);

        listOfAllCommandWordsAndAliases.add(EditCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_edit_alias() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);

        listOfAllCommandWordsAndAliases.add(EditCommand.COMMAND_ALIAS);
    }

    //@@author srishag
    @Test
    public void parseCommand_editTask() throws Exception {
        Task task = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(task).build();
        EditTaskCommand command = (EditTaskCommand) parser.parseCommand(EditTaskCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getTaskDetails(task));
        assertEquals(new EditTaskCommand(INDEX_FIRST_TASK, descriptor), command);

        listOfAllCommandWordsAndAliases.add(EditTaskCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_editTask_alias() throws Exception {
        Task task = new TaskBuilder().build();
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(task).build();
        EditTaskCommand command = (EditTaskCommand) parser.parseCommand(EditTaskCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + TaskUtil.getTaskDetails(task));
        assertEquals(new EditTaskCommand(INDEX_FIRST_TASK, descriptor), command);

        listOfAllCommandWordsAndAliases.add(EditTaskCommand.COMMAND_ALIAS);
    }
    //@@author

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);

        listOfAllCommandWordsAndAliases.add(ExitCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_exit_alias() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_ALIAS + " 3") instanceof ExitCommand);

        listOfAllCommandWordsAndAliases.add(ExitCommand.COMMAND_ALIAS);
    }


    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);

        listOfAllCommandWordsAndAliases.add(FindCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_find_alias() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);

        listOfAllCommandWordsAndAliases.add(FindCommand.COMMAND_ALIAS);

    }
    //@@author PokkaKiyo
    @Test
    public void parseCommand_findPersonsWithTags() throws Exception {
        List<String> keywords = Arrays.asList("friend", "colleague");
        List<String> expectedPredicateKeywords = Arrays.asList("friend", "friends", "colleague", "colleagues");
        FindPersonsWithTagsCommand command = (FindPersonsWithTagsCommand) parser.parseCommand(
                FindPersonsWithTagsCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonsWithTagsCommand(
                new PersonContainsTagsPredicate(expectedPredicateKeywords)), command);

        listOfAllCommandWordsAndAliases.add(FindPersonsWithTagsCommand.COMMAND_WORD);
    }
    //@@author

    @Test
    public void parseCommand_findPersonsWithTags_alias1() throws Exception {
        List<String> keywords = Arrays.asList("friend", "colleague");
        List<String> expectedPredicateKeywords = Arrays.asList("friend", "friends", "colleague", "colleagues");
        FindPersonsWithTagsCommand command = (FindPersonsWithTagsCommand) parser.parseCommand(
                FindPersonsWithTagsCommand.COMMAND_ALIAS1 + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonsWithTagsCommand(
                new PersonContainsTagsPredicate(expectedPredicateKeywords)), command);

        listOfAllCommandWordsAndAliases.add(FindPersonsWithTagsCommand.COMMAND_ALIAS1);
    }

    @Test
    public void parseCommand_findPersonsWithTags_alias2() throws Exception {
        List<String> keywords = Arrays.asList("friend", "colleague");
        List<String> expectedPredicateKeywords = Arrays.asList("friend", "friends", "colleague", "colleagues");
        FindPersonsWithTagsCommand command = (FindPersonsWithTagsCommand) parser.parseCommand(
                FindPersonsWithTagsCommand.COMMAND_ALIAS2 + " "
                        + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindPersonsWithTagsCommand(
                new PersonContainsTagsPredicate(expectedPredicateKeywords)), command);

        listOfAllCommandWordsAndAliases.add(FindPersonsWithTagsCommand.COMMAND_ALIAS2);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);

        listOfAllCommandWordsAndAliases.add(HelpCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_help_alias() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_ALIAS + " 3") instanceof HelpCommand);

        listOfAllCommandWordsAndAliases.add(HelpCommand.COMMAND_ALIAS);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }

        listOfAllCommandWordsAndAliases.add(HistoryCommand.COMMAND_WORD);
    }

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

        listOfAllCommandWordsAndAliases.add(HistoryCommand.COMMAND_ALIAS);
    }


    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);

        listOfAllCommandWordsAndAliases.add(ListCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_list_alias() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS + " 3") instanceof ListCommand);

        listOfAllCommandWordsAndAliases.add(ListCommand.COMMAND_ALIAS);
    }

    //@@author PokkaKiyo
    @Test
    public void parseCommand_listTags() throws Exception {
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD_ALIAS1) instanceof ListTagsCommand);
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD_ALIAS1 + " 3") instanceof ListTagsCommand);
    }

    @Test
    public void parseCommand_listTags_alias1() throws Exception {
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD_ALIAS2) instanceof ListTagsCommand);
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD_ALIAS2 + " 3") instanceof ListTagsCommand);
    }

    @Test
    public void parseCommand_listTags_alias2() throws Exception {
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD) instanceof ListTagsCommand);
        assertTrue(parser.parseCommand(ListTagsCommand.COMMAND_WORD + " 3") instanceof ListTagsCommand);
    }
    //@@author

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);

        listOfAllCommandWordsAndAliases.add(SelectCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_select_alias() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);

        listOfAllCommandWordsAndAliases.add(SelectCommand.COMMAND_ALIAS);
    }

    //@@author srishag
    @Test
    public void parseCommand_sendEmail() throws Exception {
        SendEmailCommand command = (SendEmailCommand) parser.parseCommand(SendEmailCommand.COMMAND_WORD
                + " " + "1" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC);
        assertEquals(new SendEmailCommand(INDEX_FIRST_PERSON, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY), command);

        listOfAllCommandWordsAndAliases.add(SendEmailCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_sendEmail_alias() throws Exception {
        SendEmailCommand command = (SendEmailCommand) parser.parseCommand(SendEmailCommand.COMMAND_ALIAS
                + " " + "1" + " " + VALID_EMAIL_SUBJECT_DESC + " " + VALID_EMAIL_BODY_DESC);
        assertEquals(new SendEmailCommand(INDEX_FIRST_PERSON, VALID_EMAIL_SUBJECT, VALID_EMAIL_BODY), command);

        listOfAllCommandWordsAndAliases.add(SendEmailCommand.COMMAND_ALIAS);
    }
    //@@author

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);

        listOfAllCommandWordsAndAliases.add(RedoCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_redoCommandAlias_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_ALIAS) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);

        listOfAllCommandWordsAndAliases.add(RedoCommand.COMMAND_ALIAS);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);

        listOfAllCommandWordsAndAliases.add(UndoCommand.COMMAND_WORD);
    }

    @Test
    public void parseCommand_undoCommandAlias_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_ALIAS) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);

        listOfAllCommandWordsAndAliases.add(UndoCommand.COMMAND_ALIAS);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @AfterClass
    public static void checkForAnyDuplicateCommandWordOrAlias() {
        Set<String> set = new HashSet<String>(AllCommandWordsAndAliases.getListOfAllCommandWordsAndAliases());
        assertTrue(set.size() ==  AllCommandWordsAndAliases.getListOfAllCommandWordsAndAliases().size());
    }

}
