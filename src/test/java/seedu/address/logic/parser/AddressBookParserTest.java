package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONFIRM_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_FROM_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAVIGATE_TO_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddPersonCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.logic.commands.ChangePrivacyCommand;
import seedu.address.logic.commands.ChangePrivacyCommand.PersonPrivacySettings;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.DismissCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditPersonCommand;
import seedu.address.logic.commands.EditPersonCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FavouriteListCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.FindTaskCommand;
import seedu.address.logic.commands.FontSizeCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocateCommand;
import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.commands.NavigateCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.PrivacyLevelCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SaveAsCommand;
import seedu.address.logic.commands.SelectPersonCommand;
import seedu.address.logic.commands.SetCompleteCommand;
import seedu.address.logic.commands.SetIncompleteCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.TagListCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Location;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsTagsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskContainsKeywordPredicate;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonPrivacySettingsBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class AddressBookParserTest {

    private static final boolean DEFAULT_STATE_LOCK = false;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommandAdd() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddPersonCommand) parser
                .parseCommand(PersonUtil.getAddCommand(person), DEFAULT_STATE_LOCK);
        assertEquals(new AddPersonCommand(person), command);

        Task task = new TaskBuilder().build();
        command = (AddTaskCommand) parser.parseCommand(TaskUtil.getAddCommand(task), DEFAULT_STATE_LOCK);
        assertEquals(new AddTaskCommand(task), command);
    }

    //@@author
    @Test
    public void parseCommandAliasAdd() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddPersonCommand) parser.parseCommand(AddPersonCommand.COMMAND_ALIAS + " "
                + PersonUtil.getPersonDetails(person), DEFAULT_STATE_LOCK);
        assertEquals(new AddPersonCommand(person), command);

        Task task = new TaskBuilder().build();
        command = (AddTaskCommand) parser.parseCommand(AddTaskCommand.COMMAND_ALIAS + " "
                + PREFIX_TASK + " " + TaskUtil.getTaskDetails(task), DEFAULT_STATE_LOCK);
        assertEquals(new AddTaskCommand(task), command);
    }

    //@@author Esilocke
    @Test
    public void parseCommandAssign() throws Exception {
        ArrayList<Index> personIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        AssignCommand command = (AssignCommand) parser.parseCommand(AssignCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new AssignCommand(personIndexes, INDEX_FIRST_TASK), command);
    }

    @Test
    public void parseCommandAliasAssign() throws Exception {
        ArrayList<Index> personIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        AssignCommand command = (AssignCommand) parser.parseCommand(AssignCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new AssignCommand(personIndexes, INDEX_FIRST_TASK), command);
    }

    //@@author jeffreygohkw
    @Test
    public void parseCommandChangePrivacy() throws Exception {
        Person person = new PersonBuilder().build();
        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder(person).build();

        ChangePrivacyCommand command = (ChangePrivacyCommand) parser.parseCommand(
                ChangePrivacyCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_NAME + String.valueOf(person.getName().getIsPrivate())
                        + " " + PREFIX_PHONE + String.valueOf(person.getPhone().getIsPrivate())
                        + " " + PREFIX_EMAIL + String.valueOf(person.getEmail().getIsPrivate())
                        + " " + PREFIX_ADDRESS + String.valueOf(person.getAddress().getIsPrivate()),
                DEFAULT_STATE_LOCK);
        ChangePrivacyCommand actualCommand = new ChangePrivacyCommand(INDEX_FIRST_PERSON, pps);

        assertTrue(changePrivacyCommandsEqual(command, actualCommand));
    }

    @Test
    public void parseCommandAliasChangePrivacy() throws Exception {
        Person person = new PersonBuilder().build();
        PersonPrivacySettings pps = new PersonPrivacySettingsBuilder(person).build();

        ChangePrivacyCommand command = (ChangePrivacyCommand) parser.parseCommand(
                ChangePrivacyCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased()
                        + " " + PREFIX_NAME + String.valueOf(person.getName().getIsPrivate())
                        + " " + PREFIX_PHONE + String.valueOf(person.getPhone().getIsPrivate())
                        + " " + PREFIX_EMAIL + String.valueOf(person.getEmail().getIsPrivate())
                        + " " + PREFIX_ADDRESS + String.valueOf(person.getAddress().getIsPrivate()),
                DEFAULT_STATE_LOCK);
        ChangePrivacyCommand actualCommand = new ChangePrivacyCommand(INDEX_FIRST_PERSON, pps);

        assertTrue(changePrivacyCommandsEqual(command, actualCommand));
    }

    @Test
    public void parseCommandPrivacyLevel() throws Exception {
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_WORD + " 1", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_WORD + " 2", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_WORD + " 3", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
    }

    @Test
    public void parseCommandAliasPrivacyLevel() throws Exception {
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_ALIAS + " 1", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_ALIAS + " 2", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
        assertTrue(parser.parseCommand(PrivacyLevelCommand.COMMAND_ALIAS + " 3", DEFAULT_STATE_LOCK)
                instanceof PrivacyLevelCommand);
    }

    @Test
    public void parseCommandTheme() throws Exception {
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD + " dark", DEFAULT_STATE_LOCK)
                instanceof ThemeCommand);
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_WORD + " light", DEFAULT_STATE_LOCK)
                instanceof ThemeCommand);
    }

    @Test
    public void parseCommandAliasTheme() throws Exception {
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_ALIAS + " dark", DEFAULT_STATE_LOCK)
                instanceof ThemeCommand);
        assertTrue(parser.parseCommand(ThemeCommand.COMMAND_ALIAS + " light", DEFAULT_STATE_LOCK)
                instanceof ThemeCommand);
    }

    //@@author
    @Test
    public void parseCommandClear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof ClearCommand);
    }

    @Test
    public void parseCommandAliasClear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_ALIAS, DEFAULT_STATE_LOCK) instanceof ClearCommand);
    }

    @Test
    public void parseCommandDelete() throws Exception {
        DeleteCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);

        command = (DeleteTaskCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_WORD + " "
                        + PREFIX_TASK + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void parseCommandAliasDelete() throws Exception {
        DeleteCommand command = (DeletePersonCommand) parser.parseCommand(
                DeletePersonCommand.COMMAND_ALIAS + " "
                        + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new DeletePersonCommand(INDEX_FIRST_PERSON), command);

        command = (DeleteTaskCommand) parser.parseCommand(
                DeletePersonCommand
                        .COMMAND_ALIAS + " " + PREFIX_TASK + " "
                        + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new DeleteTaskCommand(INDEX_FIRST_TASK), command);
    }

    //@@author Esilocke
    @Test
    public void parseCommandDismiss() throws Exception {
        ArrayList<Index> personIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DismissCommand command = (DismissCommand) parser.parseCommand(DismissCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_FROM + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new DismissCommand(personIndexes, INDEX_FIRST_TASK), command);
    }

    @Test
    public void parseCommandAliasDismiss() throws Exception {
        ArrayList<Index> personIndexes = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DismissCommand command = (DismissCommand) parser.parseCommand(DismissCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_FROM + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new DismissCommand(personIndexes, INDEX_FIRST_TASK), command);
    }
    //@@author

    @Test
    public void parseCommandEdit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person), DEFAULT_STATE_LOCK);
        assertEquals(new EditPersonCommand(INDEX_FIRST_PERSON, descriptor), command);

        Task task = new TaskBuilder().build();
        EditTaskDescriptor taskDescriptor = new EditTaskDescriptorBuilder(task).build();
        command = (EditTaskCommand) parser.parseCommand(EditPersonCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + PREFIX_TASK + " "
                + TaskUtil.getTaskDetails(task), DEFAULT_STATE_LOCK);
        assertEquals(new EditTaskCommand(INDEX_FIRST_TASK, taskDescriptor), command);
    }

    @Test
    public void parseCommandAliasEdit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditPersonCommand) parser.parseCommand(EditPersonCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person), DEFAULT_STATE_LOCK);
        assertEquals(new EditPersonCommand(INDEX_FIRST_PERSON, descriptor), command);

        Task task = new TaskBuilder().build();
        EditTaskDescriptor taskDescriptor = new EditTaskDescriptorBuilder(task).build();
        command = (EditTaskCommand) parser.parseCommand(EditPersonCommand.COMMAND_ALIAS + " "
                + INDEX_FIRST_TASK.getOneBased() + " " + PREFIX_TASK + " "
                + TaskUtil.getTaskDetails(task), DEFAULT_STATE_LOCK);
        assertEquals(new EditTaskCommand(INDEX_FIRST_TASK, taskDescriptor), command);
    }

    //@@author Esilocke
    @Test
    public void parseCommandEditTag() throws Exception {
        EditTagCommand command = (EditTagCommand) parser.parseCommand(EditTagCommand.COMMAND_WORD
                + " " + PREFIX_TAG_FULL + " "
                + " friends enemies", DEFAULT_STATE_LOCK);
        Tag friends = new Tag("friends");
        Tag enemies = new Tag("enemies");
        assertEquals(new EditTagCommand(friends, enemies), command);
    }

    @Test
    public void parseCommandAliasEditTag() throws Exception {
        EditTagCommand command = (EditTagCommand) parser.parseCommand(EditTagCommand.COMMAND_ALIAS
                + " " + PREFIX_TAG_FULL + " "
                + " friends enemies", DEFAULT_STATE_LOCK);
        Tag friends = new Tag("friends");
        Tag enemies = new Tag("enemies");
        assertEquals(new EditTagCommand(friends, enemies), command);
    }

    //@@author jeffreygohkw
    @Test
    public void parseCommandOpen() throws Exception {
        assertTrue(parser.parseCommand(OpenCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof OpenCommand);
        assertTrue(parser
                .parseCommand(OpenCommand.COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof OpenCommand);
    }

    @Test
    public void parseCommandSaveAs() throws Exception {
        assertTrue(parser.parseCommand(SaveAsCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof SaveAsCommand);
        assertTrue(parser
                .parseCommand(SaveAsCommand.COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof SaveAsCommand);
    }
    //@@author

    @Test
    public void parseCommandExit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof ExitCommand);
        assertTrue(parser
                .parseCommand(ExitCommand.COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof ExitCommand);
    }

    @Test
    public void parseCommandFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")), DEFAULT_STATE_LOCK);
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(keywords)), command);

        command = (FindCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_WORD + " " + PREFIX_TASK +  " "
                        + keywords.stream().collect(Collectors.joining(" ")), DEFAULT_STATE_LOCK);
        assertEquals(new FindTaskCommand(new TaskContainsKeywordPredicate(keywords,
                DEFAULT_STATE_LOCK, DEFAULT_STATE_LOCK, false, 0)), command);
    }

    @Test
    public void parseCommandAliasFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindPersonCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_ALIAS + " " + keywords
                        .stream().collect(Collectors.joining(" ")), DEFAULT_STATE_LOCK);
        assertEquals(new FindPersonCommand(new NameContainsKeywordsPredicate(keywords)), command);

        command = (FindCommand) parser.parseCommand(
                FindPersonCommand.COMMAND_ALIAS + " " + PREFIX_TASK +  " "
                        + keywords.stream().collect(Collectors.joining(" ")), DEFAULT_STATE_LOCK);
        assertEquals(new FindTaskCommand(new TaskContainsKeywordPredicate(
                keywords, DEFAULT_STATE_LOCK, false, false, 0)), command);
    }

    //@@author wangyiming1019
    @Test
    public void parseCommandAddTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toAdd = new Tag(tagName);
        AddTagCommand command = (AddTagCommand) parser.parseCommand(AddTagCommand.COMMAND_WORD
                + " " + PREFIX_TAG_FULL
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName, DEFAULT_STATE_LOCK);
        assertEquals(new AddTagCommand(toAdd, indexes), command);
    }

    @Test
    public void parseCommandAliasAddTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toAdd = new Tag(tagName);
        AddTagCommand command = (AddTagCommand) parser.parseCommand(AddTagCommand.COMMAND_ALIAS
                + " " + PREFIX_TAG_FULL
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName, DEFAULT_STATE_LOCK);
        assertEquals(new AddTagCommand(toAdd, indexes), command);
    }

    @Test
    public void parseCommandDeleteTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toDelete = new Tag(tagName);
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(DeleteTagCommand.COMMAND_WORD
                + " " + PREFIX_TAG_FULL
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName, DEFAULT_STATE_LOCK);
        assertEquals(new DeleteTagCommand(toDelete, indexes), command);
    }

    @Test
    public void parseCommandAliasDeleteTag() throws Exception {
        ArrayList<Index> indexes = new ArrayList<Index>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);
        final String tagName = "friends";
        Tag toDelete = new Tag(tagName);
        DeleteTagCommand command = (DeleteTagCommand) parser.parseCommand(DeleteTagCommand.COMMAND_ALIAS
                + " " + PREFIX_TAG_FULL
                + INDEX_FIRST_PERSON.getOneBased() + " "
                + INDEX_SECOND_PERSON.getOneBased() + " " + PREFIX_TAG + tagName, DEFAULT_STATE_LOCK);
        assertEquals(new DeleteTagCommand(toDelete, indexes), command);
    }

    @Test
    public void parseCommandTagList() throws Exception {
        assertTrue(parser.parseCommand(TagListCommand.COMMAND_WORD,
                DEFAULT_STATE_LOCK) instanceof TagListCommand);
        assertTrue(parser.parseCommand(TagListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof TagListCommand);
    }

    @Test
    public void parseCommandAliasTagList() throws Exception {
        assertTrue(parser.parseCommand(TagListCommand.COMMAND_ALIAS,
                DEFAULT_STATE_LOCK) instanceof TagListCommand);
        assertTrue(parser.parseCommand(TagListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof TagListCommand);
    }

    @Test
    public void parseCommandFindTag() throws Exception {
        List<String> keywords = Arrays.asList("friend", "colleague");
        FindTagCommand command = (FindTagCommand) parser.parseCommand(
                FindTagCommand.COMMAND_WORD + " " + PREFIX_TAG_FULL + " "
                        + keywords.stream().collect(Collectors.joining(" ")), DEFAULT_STATE_LOCK);
        assertEquals(new FindTagCommand(
                new NameContainsTagsPredicate(keywords)), command);
    }

    @Test
    public void parseCommandAliasFindTag() throws Exception {
        List<String> keywords = Arrays.asList("friend", "colleague");
        FindTagCommand command = (FindTagCommand) parser.parseCommand(
                FindTagCommand.COMMAND_ALIAS + " " + PREFIX_TAG_FULL + " "
                        + keywords.stream().collect(Collectors.joining(" ")), DEFAULT_STATE_LOCK);
        assertEquals(new FindTagCommand(
                new NameContainsTagsPredicate(keywords)), command);
    }

    @Test
    public void parseCommandFavourite() throws Exception {
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(
                FavouriteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                DEFAULT_STATE_LOCK);
        assertEquals(new FavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAliasFavourite() throws Exception {
        FavouriteCommand command = (FavouriteCommand) parser.parseCommand(
                FavouriteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(),
                DEFAULT_STATE_LOCK);
        assertEquals(new FavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandUnfavourite() throws Exception {
        UnfavouriteCommand command = (UnfavouriteCommand) parser.parseCommand(
                UnfavouriteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(),
                DEFAULT_STATE_LOCK);
        assertEquals(new UnfavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAliasUnfavourite() throws Exception {
        UnfavouriteCommand command = (UnfavouriteCommand) parser.parseCommand(
                UnfavouriteCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(),
                DEFAULT_STATE_LOCK);
        assertEquals(new UnfavouriteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandFavouriteList() throws Exception {
        assertTrue(parser.parseCommand(FavouriteListCommand.COMMAND_WORD,
                DEFAULT_STATE_LOCK) instanceof FavouriteListCommand);
        assertTrue(parser.parseCommand(FavouriteListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof FavouriteListCommand);
    }

    @Test
    public void parseCommandAliasFavouriteList() throws Exception {
        assertTrue(parser.parseCommand(FavouriteListCommand.COMMAND_ALIAS,
                DEFAULT_STATE_LOCK) instanceof FavouriteListCommand);
        assertTrue(parser.parseCommand(FavouriteListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof FavouriteListCommand);
    }
    //@@author

    @Test
    public void parseCommandHelp() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof HelpCommand);
    }

    @Test
    public void parseCommandHistory() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", DEFAULT_STATE_LOCK);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommandAliasHistory() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_ALIAS, DEFAULT_STATE_LOCK) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand
                .COMMAND_ALIAS + " 3", DEFAULT_STATE_LOCK) instanceof HistoryCommand);

        try {
            parser.parseCommand("histories", DEFAULT_STATE_LOCK);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommandList() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand
                .COMMAND_WORD + " 3", DEFAULT_STATE_LOCK) instanceof ListCommand);
    }

    @Test
    public void parseCommandAliasList() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_ALIAS, DEFAULT_STATE_LOCK) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand
                .COMMAND_ALIAS + " 3", DEFAULT_STATE_LOCK) instanceof ListCommand);
    }

    @Test
    public void parseCommandSelect() throws Exception {
        SelectPersonCommand command = (SelectPersonCommand) parser.parseCommand(
                SelectPersonCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SelectPersonCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAliasSelect() throws Exception {
        SelectPersonCommand command = (SelectPersonCommand) parser.parseCommand(
                SelectPersonCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SelectPersonCommand(INDEX_FIRST_PERSON), command);
    }

    //@@author jeffreygohkw
    @Test
    public void parseCommandLocate() throws Exception {
        LocateCommand command = (LocateCommand) parser.parseCommand(
                LocateCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new LocateCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandAliasLocate() throws Exception {
        LocateCommand command = (LocateCommand) parser.parseCommand(
                LocateCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new LocateCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandNavigate() throws Exception {
        NavigateCommand command = (NavigateCommand) parser.parseCommand(
                NavigateCommand.COMMAND_WORD + " " + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                        + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", DEFAULT_STATE_LOCK);
        Location from = new Location("NUS");
        Location to = new Location("Sentosa");
        assertEquals(new NavigateCommand(from, to, null, null, false, false),
                command);
    }

    @Test
    public void parseCommandAliasNavigate() throws Exception {
        NavigateCommand command = (NavigateCommand) parser.parseCommand(
                NavigateCommand.COMMAND_ALIAS + " " + PREFIX_NAVIGATE_FROM_ADDRESS + "NUS"
                        + " " + PREFIX_NAVIGATE_TO_ADDRESS + "Sentosa", DEFAULT_STATE_LOCK);
        Location from = new Location("NUS");
        Location to = new Location("Sentosa");
        assertEquals(new NavigateCommand(from, to, null, null, false, false),
                command);
    }

    //@@author charlesgoh
    @Test
    public void parseSortCommandWord() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " "
                + SortCommand.ACCEPTED_LIST_PARAMETERS.get(0) + " " + SortCommand.ACCEPTED_FIELD_PARAMETERS.get(0)
                + " " + SortCommand.ACCEPTED_ORDER_PARAMETERS.get(0), DEFAULT_STATE_LOCK) instanceof SortCommand);
    }

    @Test
    public void parseSortCommandAlias() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_ALIAS + " "
                + SortCommand.ACCEPTED_LIST_PARAMETERS.get(0) + " " + SortCommand.ACCEPTED_FIELD_PARAMETERS.get(0)
                + " " + SortCommand.ACCEPTED_ORDER_PARAMETERS.get(0), DEFAULT_STATE_LOCK) instanceof SortCommand);
    }

    @Test
    public void parseCommandBackup() throws Exception {
        assertTrue(parser.parseCommand(BackupCommand
                .COMMAND_WORD + " testbackupfilename", DEFAULT_STATE_LOCK) instanceof BackupCommand);
        assertTrue(parser.parseCommand(BackupCommand
                .COMMAND_ALIAS + " testbackupfilename", DEFAULT_STATE_LOCK) instanceof BackupCommand);
    }

    @Test
    public void parseCommandFontSizeWord() throws Exception {
        for (String arg: FontSizeCommand.ACCEPTED_PARAMETERS) {
            assertTrue(parser
                    .parseCommand(FontSizeCommand
                            .COMMAND_WORD + " " + arg, DEFAULT_STATE_LOCK) instanceof FontSizeCommand);
        }
    }

    @Test
    public void parseCommandFontSizeAlias() throws Exception {
        for (String arg: FontSizeCommand.ACCEPTED_PARAMETERS) {
            assertTrue(parser
                    .parseCommand(FontSizeCommand
                            .COMMAND_ALIAS + " " + arg, DEFAULT_STATE_LOCK) instanceof FontSizeCommand);
        }
    }

    @Test
    public void parseLockCommandValid() throws Exception {
        // Pass parser without prefix
        assertTrue(parser.parseCommand(LockCommand
                .COMMAND_WORD + " randompassword", DEFAULT_STATE_LOCK) instanceof LockCommand);

        // Pass parser with prefix
        assertTrue(parser.parseCommand(LockCommand
                .COMMAND_WORD + " " + PREFIX_PASSWORD + " randompassword", DEFAULT_STATE_LOCK) instanceof LockCommand);

        // Do the same tests, this time with command alias
        assertTrue(parser.parseCommand(LockCommand
                .COMMAND_ALIAS + " randompassword", DEFAULT_STATE_LOCK) instanceof LockCommand);

        assertTrue(parser.parseCommand(LockCommand
                .COMMAND_ALIAS + " " + PREFIX_PASSWORD + " randompassword", DEFAULT_STATE_LOCK) instanceof LockCommand);
    }

    @Test
    public void parseLockCommandWordInvalid() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
        parser.parseCommand(LockCommand.COMMAND_WORD, DEFAULT_STATE_LOCK);
    }

    @Test
    public void parseLockCommandAliasInvalid() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LockCommand.MESSAGE_USAGE));
        parser.parseCommand(LockCommand.COMMAND_ALIAS, DEFAULT_STATE_LOCK);
    }

    @Test
    public void parseUnlockCommandValid() throws Exception {
        // Pass parser without prefix
        assertTrue(parser.parseCommand(UnlockCommand
                .COMMAND_WORD + " randompassword", DEFAULT_STATE_LOCK) instanceof UnlockCommand);

        // Pass parser with prefix
        assertTrue(parser.parseCommand(UnlockCommand
                .COMMAND_WORD + " "
                + PREFIX_PASSWORD + " randompassword", DEFAULT_STATE_LOCK) instanceof UnlockCommand);

        // Do the same tests, this time with command alias
        assertTrue(parser.parseCommand(UnlockCommand
                .COMMAND_ALIAS + " randompassword", DEFAULT_STATE_LOCK) instanceof UnlockCommand);

        assertTrue(parser.parseCommand(UnlockCommand
                .COMMAND_ALIAS + " " + PREFIX_PASSWORD
                + " randompassword", DEFAULT_STATE_LOCK) instanceof UnlockCommand);
    }

    @Test
    public void parseUnlockCommandWordInvalid() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
        parser.parseCommand(UnlockCommand.COMMAND_WORD, DEFAULT_STATE_LOCK);
    }

    @Test
    public void parseUnlockCommandAliasInvalid() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnlockCommand.MESSAGE_USAGE));
        parser.parseCommand(UnlockCommand.COMMAND_ALIAS, DEFAULT_STATE_LOCK);
    }

    @Test
    public void parseChangePasswordCommand() throws Exception {
        // Pass inputs that are correct and valid
        assertTrue(parser.parseCommand(ChangePasswordCommand
                .COMMAND_WORD + " " + PREFIX_PASSWORD + "password "
                + PREFIX_NEW_PASSWORD + "newpassword "
                + PREFIX_CONFIRM_PASSWORD + "newpassword ", DEFAULT_STATE_LOCK) instanceof ChangePasswordCommand);

        // Pass inputs that are wrong but valid
        assertTrue(parser.parseCommand(ChangePasswordCommand
                .COMMAND_WORD + " " + PREFIX_PASSWORD + "password "
                + PREFIX_NEW_PASSWORD + "newpassword "
                + PREFIX_CONFIRM_PASSWORD + "newpassword:P ", DEFAULT_STATE_LOCK) instanceof ChangePasswordCommand);

        // Pass inputs that have the wrong order (i.e. fields are in the wrong order)
        assertTrue(parser.parseCommand(ChangePasswordCommand
                .COMMAND_WORD + " " + PREFIX_NEW_PASSWORD + "newpassword "
                + PREFIX_PASSWORD + "password "
                + PREFIX_CONFIRM_PASSWORD + "newpassword:P ", DEFAULT_STATE_LOCK) instanceof ChangePasswordCommand);

        assertTrue(parser.parseCommand(ChangePasswordCommand
                .COMMAND_WORD + " " + PREFIX_CONFIRM_PASSWORD + "newpassword:P "
                + PREFIX_PASSWORD + "password "
                + PREFIX_NEW_PASSWORD + "newpassword ", DEFAULT_STATE_LOCK) instanceof ChangePasswordCommand);

        // Do the same thing for alias counterparts
        assertTrue(parser.parseCommand(ChangePasswordCommand
                .COMMAND_ALIAS + " " + PREFIX_PASSWORD + "password "
                + PREFIX_NEW_PASSWORD + "newpassword "
                + PREFIX_CONFIRM_PASSWORD + "newpassword ", DEFAULT_STATE_LOCK) instanceof ChangePasswordCommand);

        assertTrue(parser.parseCommand(ChangePasswordCommand
                .COMMAND_ALIAS + " " + PREFIX_PASSWORD + "password "
                + PREFIX_NEW_PASSWORD + "newpassword "
                + PREFIX_CONFIRM_PASSWORD + "newpassword:P ", DEFAULT_STATE_LOCK) instanceof ChangePasswordCommand);

        assertTrue(parser.parseCommand(ChangePasswordCommand
                .COMMAND_ALIAS + " " + PREFIX_NEW_PASSWORD + "newpassword "
                + PREFIX_PASSWORD + "password "
                + PREFIX_CONFIRM_PASSWORD + "newpassword:P ", DEFAULT_STATE_LOCK) instanceof ChangePasswordCommand);

        assertTrue(parser.parseCommand(ChangePasswordCommand
                .COMMAND_ALIAS + " " + PREFIX_CONFIRM_PASSWORD + "newpassword:P "
                + PREFIX_PASSWORD + "password "
                + PREFIX_NEW_PASSWORD + "newpassword ", DEFAULT_STATE_LOCK) instanceof ChangePasswordCommand);
    }
    //@@author
    @Test
    public void parseCommandRedoCommandWordReturnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1", DEFAULT_STATE_LOCK) instanceof RedoCommand);
    }

    //@@author Esilocke
    @Test
    public void  parseCommandSetComplete() throws Exception {
        SetCompleteCommand command = (SetCompleteCommand) parser.parseCommand(SetCompleteCommand.COMMAND_WORD
                + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SetCompleteCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void  parseCommandAliasSetComplete() throws Exception {
        SetCompleteCommand command = (SetCompleteCommand) parser.parseCommand(SetCompleteCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SetCompleteCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void  parseCommandSetIncomplete() throws Exception {
        SetIncompleteCommand command = (SetIncompleteCommand) parser.parseCommand(SetIncompleteCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SetIncompleteCommand(INDEX_FIRST_TASK), command);
    }

    @Test
    public void  parseCommandAliasSetIncomplete() throws Exception {
        SetIncompleteCommand command = (SetIncompleteCommand) parser.parseCommand(SetIncompleteCommand.COMMAND_ALIAS
                + " " + INDEX_FIRST_TASK.getOneBased(), DEFAULT_STATE_LOCK);
        assertEquals(new SetIncompleteCommand(INDEX_FIRST_TASK), command);
    }
    //@@author

    @Test
    public void parseCommandUndoCommandWordReturnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD, DEFAULT_STATE_LOCK) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3", DEFAULT_STATE_LOCK) instanceof UndoCommand);
    }

    @Test
    public void parseCommandUnrecognisedInputThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("", DEFAULT_STATE_LOCK);
    }

    @Test
    public void parseCommandUnknownCommandThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand", DEFAULT_STATE_LOCK);
    }

    //@@author jeffreygohkw
    /**
     * Checks if 2 ChangePrivacyCommands are equal
     * @param command the expected command
     * @param actualCommand the actual command
     * @return true if all the data are equal
     */
    private boolean changePrivacyCommandsEqual(ChangePrivacyCommand command, ChangePrivacyCommand actualCommand) {
        assertEquals(command.getIndex(), actualCommand.getIndex());
        assertEquals(command.getPps().getAddressIsPrivate(), actualCommand.getPps().getAddressIsPrivate());
        assertEquals(command.getPps().getNameIsPrivate(), actualCommand.getPps().getNameIsPrivate());
        assertEquals(command.getPps().getEmailIsPrivate(), actualCommand.getPps().getEmailIsPrivate());
        assertEquals(command.getPps().getPhoneIsPrivate(), actualCommand.getPps().getPhoneIsPrivate());
        return true;
    }
}
