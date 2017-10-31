package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_REMOVED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.RemoveTagCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author freesoup
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemoveTagCommand.
 */
public class RemoveTagCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_emptyArg_throwsParseException() {
        RemoveTagCommandParser parser = new RemoveTagCommandParser();
        assertParseFailure(parser, "     ", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_removeTag_success() throws IllegalValueException, PersonNotFoundException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand("friends");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(new Tag("friends"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeSingleTag_success() throws IllegalValueException, PersonNotFoundException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand(5, "owesMoney");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(Index.fromOneBased(5), new Tag("owesMoney"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    /**
     * Generates a new {@code RemoveTagCommand} which upon execution, removes all tag
     * matching given Tag in {@code model}.
     */
    private RemoveTagCommand prepareCommand(String tag) throws IllegalValueException {
        RemoveTagCommand command = new RemoveTagCommand(new Tag(tag));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Generates a new {@code RemoveTagCommand} which upon execution, removes tag
     * corresponding to given Tag and Index in {@code model}.
     */
    private RemoveTagCommand prepareCommand(int index, String tag) throws IllegalValueException {
        RemoveTagCommand command = new RemoveTagCommand(Index.fromZeroBased(index), new Tag(tag));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
