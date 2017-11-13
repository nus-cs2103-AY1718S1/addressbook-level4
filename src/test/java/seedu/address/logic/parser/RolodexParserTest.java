package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PROMPT_COMMAND;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.SORT_ARGUMENT_NAME_DESCENDING;
import static seedu.address.logic.parser.CommandParserTestUtil.POSSIBLE_COMMAND_ABBREVIATIONS;
import static seedu.address.logic.parser.CommandParserTestUtil.generateCommandAbbreviationPermutations;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.util.Pair;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.commands.OpenRolodexCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StarWarsCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.exceptions.SuggestibleParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDataContainsKeywordsPredicate;
import seedu.address.model.person.Remark;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class RolodexParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RolodexParser parser = new RolodexParser();

    @Test
    public void parseCommandAdd() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommandClear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
        for (String abbreviation : ClearCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof ClearCommand);
        }
    }

    @Test
    public void parseCommandDelete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandEdit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getPersonDetails(person));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommandEmail() throws Exception {
        String subject = "Hello world";
        String subjectParsed = "Hello%20world";
        EmailCommand command = (EmailCommand) parser.parseCommand(EmailCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_SUBJECT + subject);
        assertEquals(new EmailCommand(INDEX_FIRST_PERSON, subjectParsed), command);
    }

    @Test
    public void parseCommandRemark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }

    @Test
    public void parseCommandExit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
        for (String abbreviation : ExitCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof ExitCommand);
        }
    }

    @Test
    public void parseCommandFind() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonDataContainsKeywordsPredicate(keywords), new ArrayList<>()), command);
    }

    @Test
    public void parseCommandHelp() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
        for (String abbreviation : HelpCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof HelpCommand);
        }
    }

    @Test
    public void parseCommandHistory() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);
        for (String abbreviation : HistoryCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof HistoryCommand);
        }

        try {
            parser.parseCommand("histories");
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommandList() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(
                ListCommand.COMMAND_WORD + " " + SORT_ARGUMENT_NAME_DESCENDING) instanceof ListCommand);
        for (String abbreviation : ListCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof ListCommand);
        }
    }

    @Test
    public void parseCommandSelect() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommandOpen() throws Exception {
        String validRolodexFile = "valid/filePath/valid.rldx";
        OpenRolodexCommand command = (OpenRolodexCommand) parser.parseCommand(
                OpenRolodexCommand.COMMAND_WORD + " " + validRolodexFile);
        assertEquals(new OpenRolodexCommand(validRolodexFile), command);
    }

    @Test
    public void parseCommandNew() throws Exception {
        String validRolodexFile = "valid/filePath/valid.rldx";
        NewRolodexCommand command = (NewRolodexCommand) parser.parseCommand(
                NewRolodexCommand.COMMAND_WORD + " " + validRolodexFile);
        assertEquals(new NewRolodexCommand(validRolodexFile), command);
    }

    @Test
    public void parseCommandRedoCommandWordReturnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
        for (String abbreviation : RedoCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof RedoCommand);
        }
    }

    @Test
    public void parseCommandUndoCommandWordReturnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
        for (String abbreviation : UndoCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof UndoCommand);
        }
    }

    @Test
    public void parseCommandStarWarsCommandWordReturnsStarWarsCommand() throws Exception {
        assertTrue(parser.parseCommand(StarWarsCommand.COMMAND_WORD) instanceof StarWarsCommand);
        assertTrue(parser.parseCommand("starwars 4") instanceof StarWarsCommand);
        for (String abbreviation : StarWarsCommand.COMMAND_WORD_ABBREVIATIONS) {
            assertTrue(parser.parseCommand(abbreviation) instanceof StarWarsCommand);
        }
    }

    @Test
    public void parseCommandUnrecognisedInputThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommandUnknownCommandThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }

    @Test
    public void parseCommandListInvalidArgumentThrowsSuggestibleParseException() throws Exception {
        thrown.expect(SuggestibleParseException.class);
        thrown.expectMessage(String.format(MESSAGE_PROMPT_COMMAND, ListCommand.COMMAND_WORD));
        parser.parseCommand(ListCommand.COMMAND_WORD + " 3");
        parser.parseCommand(ListCommand.COMMAND_WORD + " Bazinga");
    }

    @Test
    public void parseAllCommandAbbreviationsAreDisjoint() {
        ArrayList<Pair<Set<String>, Set<String>>> commandAbbreviationPermutations =
                generateCommandAbbreviationPermutations(POSSIBLE_COMMAND_ABBREVIATIONS);
        for (Pair<Set<String>, Set<String>> commandAbbreviationPair : commandAbbreviationPermutations) {
            assertTrue(Collections.disjoint(commandAbbreviationPair.getKey(), commandAbbreviationPair.getValue()));
        }
    }
}
