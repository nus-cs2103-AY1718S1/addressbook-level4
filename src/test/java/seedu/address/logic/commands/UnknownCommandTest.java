package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;

//@@author philemontan
/**
 * Unit testing for {@link seedu.address.logic.commands.UnknownCommand}
 * Integration tests with model not required, as interactions with model happen only on the actual matched Command
 * subclasses
 * Tests are written to check for positive matching by UnknownCommand. To reduce redundancy,
 * test cases use inputs that are at the set maximum acceptable levenshtein distance:
 * @see seedu.address.logic.commands.UnknownCommand#ACCEPTABLE_LEVENSHTEIN_DISTANCE
 */
public class UnknownCommandTest {
    /**
     * This test case tests parsing of all commands that require input parameters.
     * We can test 2 separate functionality simultaneously to maximize efficiency.
     * 1: Correct matching to existing command if Levenshtein distance < current acceptable maximum:
     * @see seedu.address.logic.commands.UnknownCommand#ACCEPTABLE_LEVENSHTEIN_DISTANCE
     * 2: Parse exception thrown on invalid parameter input to command parsers.
     * We do this by instantiating the UnknownCommand with commandWords that are close enough to match known
     * commandWords, but we enter invalid arguments.
     */
    @Test
    public void acceptableDistanceMistypedInputWithInvalidParameters() {
        UnknownCommand tempCommand;

        //AddCommand test
        tempCommand = new UnknownCommand("a", "");
        executeTest(tempCommand, AddCommand.MESSAGE_USAGE);

        //AddECommand test
        tempCommand = new UnknownCommand("addEve", "");
        executeTest(tempCommand, AddEventCommand.MESSAGE_USAGE);

        //DeleteCommand test
        tempCommand  = new UnknownCommand("dele", "");
        executeTest(tempCommand, DeleteCommand.MESSAGE_USAGE);

        //DeleteEventCommand test
        tempCommand = new UnknownCommand("leteE", "");
        executeTest(tempCommand, DeleteEventCommand.MESSAGE_USAGE);

        //DeleteProfilePicCommand test
        tempCommand  = new UnknownCommand("deleteProfileP", "");
        executeTest(tempCommand, DeleteProfilePicCommand.MESSAGE_USAGE);

        //EditCommand test
        tempCommand = new UnknownCommand("edittt", "");
        executeTest(tempCommand, EditCommand.MESSAGE_USAGE);

        //EditEventCommand test
        tempCommand = new UnknownCommand("itE", "");
        executeTest(tempCommand, EditEventCommand.MESSAGE_USAGE);

        //EmailCommand test
        tempCommand = new UnknownCommand("ema", "");
        executeTest(tempCommand, EmailCommand.MESSAGE_USAGE);

        //FindCommand test
        tempCommand = new UnknownCommand("fi", "");
        executeTest(tempCommand, FindCommand.MESSAGE_USAGE);

        //LocationCommand test
        tempCommand = new UnknownCommand("locati", "");
        executeTest(tempCommand, LocationCommand.MESSAGE_USAGE);

        //RemoveTagCommand test
        tempCommand = new UnknownCommand("removet", "");
        executeTest(tempCommand, RemoveTagCommand.MESSAGE_USAGE);

        //SelectCommand test
        tempCommand = new UnknownCommand("sele", "");
        executeTest(tempCommand, SelectCommand.MESSAGE_USAGE);

        //UpdateProfilePicCommand test
        tempCommand = new UnknownCommand("updateProfileP", "");
        executeTest(tempCommand, UpdateProfilePicCommand.MESSAGE_USAGE);
    }

    /**
     * Executes the actual testing for {@link #acceptableDistanceMistypedInputWithInvalidParameters()}
     * @param tempCommand
     * @param expectedCommandMessageUsage
     */
    public void executeTest(UnknownCommand tempCommand, String expectedCommandMessageUsage) {
        try {
            tempCommand.suggestionFound();
        } catch (ParseException E) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, expectedCommandMessageUsage), E.getMessage());
        }
    }

    /**
     * Similar to {@link #acceptableDistanceMistypedInputWithInvalidParameters}, this test case is written for
     * Commands that do not require parameters.
     * In this test case, we only test for positive matching of mistyped inputs.
     */
    @Test
    public void acceptableDistanceMistypedInputWithNoRequiredParameters() {
        UnknownCommand tempCommand = null;
        String commandBaseClassPath = "seedu.address.logic.commands.";

        //ClearCommand test
        tempCommand = new UnknownCommand("cle", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "ClearCommand");

        //ExitCommand test
        tempCommand = new UnknownCommand("ex", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "ExitCommand");

        //Export test
        tempCommand = new UnknownCommand("exporttt", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "ExportCommand");

        //Help test
        tempCommand = new UnknownCommand("he", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "HelpCommand");

        //History test
        tempCommand = new UnknownCommand("histo", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "HistoryCommand");

        //Import test
        tempCommand = new UnknownCommand("impo", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "ImportCommand");

        //List test
        tempCommand = new UnknownCommand("li", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "ListCommand");

        //Redo test
        tempCommand = new UnknownCommand("re", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "RedoCommand");

        //Sort test
        tempCommand = new UnknownCommand("so", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "SortCommand");

        //Undo test
        tempCommand = new UnknownCommand("un", "");
        executeTestCommandWithoutParameterRequirement(tempCommand, commandBaseClassPath + "UndoCommand");
    }

    /**
     * Executes the actual testing for {@link #acceptableDistanceMistypedInputWithNoRequiredParameters}
     * @param tempCommand
     * @param fullyQualifiedActualClassPath
     */
    public void executeTestCommandWithoutParameterRequirement(UnknownCommand tempCommand,
                                                              String fullyQualifiedActualClassPath) {
        Class actualClass = null;
        try {
            tempCommand.suggestionFound();
        } catch (ParseException E1) {
            assert false : "Unexpected behaviour: Clear should not throw a parse exception with empty parameters";
        }
        try {
            actualClass = Class.forName(fullyQualifiedActualClassPath);
        } catch (ClassNotFoundException E2) {
            assert false : "Unexpected behaviour: ClearCommand class cannot be found";
        }
        assertTrue(tempCommand.getSuggestedCommand().getClass().getSimpleName()
                .equals(actualClass.getSimpleName()));
    }
}
