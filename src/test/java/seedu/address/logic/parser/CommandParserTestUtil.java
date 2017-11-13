package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.util.Pair;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.commands.OpenRolodexCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.StarWarsCommand;
import seedu.address.logic.commands.Suggestion;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.exceptions.ParseArgsException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    public static final Set<Set<String>> POSSIBLE_COMMAND_ABBREVIATIONS = new HashSet<>(Arrays.asList(
            AddCommand.COMMAND_WORD_ABBREVIATIONS,
            ClearCommand.COMMAND_WORD_ABBREVIATIONS,
            DeleteCommand.COMMAND_WORD_ABBREVIATIONS,
            EditCommand.COMMAND_WORD_ABBREVIATIONS,
            EmailCommand.COMMAND_WORD_ABBREVIATIONS,
            ExitCommand.COMMAND_WORD_ABBREVIATIONS,
            FindCommand.COMMAND_WORD_ABBREVIATIONS,
            HelpCommand.COMMAND_WORD_ABBREVIATIONS,
            HistoryCommand.COMMAND_WORD_ABBREVIATIONS,
            ListCommand.COMMAND_WORD_ABBREVIATIONS,
            NewRolodexCommand.COMMAND_WORD_ABBREVIATIONS,
            OpenRolodexCommand.COMMAND_WORD_ABBREVIATIONS,
            RedoCommand.COMMAND_WORD_ABBREVIATIONS,
            SelectCommand.COMMAND_WORD_ABBREVIATIONS,
            UndoCommand.COMMAND_WORD_ABBREVIATIONS,
            Suggestion.COMMAND_WORD_ABBREVIATIONS,
            StarWarsCommand.COMMAND_WORD_ABBREVIATIONS
    ));

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser parser, String userInput, Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput, not suggestible.", pe);
        } catch (ParseArgsException pae) {
            throw new IllegalArgumentException("Invalid userInput, suggestible.", pae);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser parser, String userInput, String expectedMessage) {
        try {
            parser.parse(userInput);
            fail("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        } catch (ParseArgsException pae) {
            assertEquals(expectedMessage, pae.getMessage());
        }
    }

    /**
     * Returns a list permutation of all pairs of Set of Strings in a Set of Set of Strings
     * @param set to be permuted
     */
    public static ArrayList<Pair<Set<String>, Set<String>>> generateCommandAbbreviationPermutations(Set<Set<String>>
                                                                                                            set) {
        ArrayList<Pair<Set<String>, Set<String>>> commandAbbreviationPairList = new ArrayList<>();
        ArrayList<Set<String>> stringList = new ArrayList<>(set);

        for (int i = 0; i < set.size(); i++) {
            for (int j = i + 1; j < set.size(); j++) {
                if (i != j) {
                    Pair<Set<String>, Set<String>> setPair = new Pair<>(stringList.get(i), stringList.get(j));
                    commandAbbreviationPairList.add(setPair);
                }
            }
        }

        return commandAbbreviationPairList;
    }
}
