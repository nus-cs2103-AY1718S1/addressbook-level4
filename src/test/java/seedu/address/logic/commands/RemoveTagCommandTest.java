package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_REMOVED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.RemoveTagCommandParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.NoSuchTagException;
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
    public void execute_removeTag_success() throws IllegalValueException, NoSuchTagException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand("friends");
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeTag(new Tag("friends"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeTagInvalidIndex() throws IllegalValueException {
        //addressbook does not have specified Index.
        assertCommandFailure(prepareCommand(10, "prospective"), model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_removeNonExistantTag_failure() throws IllegalValueException {
        //addressbook does not contain enemy tag.
        assertCommandFailure(prepareCommand("enemy"), model, RemoveTagCommand.MESSAGE_TAG_NOT_FOUND);

        //Elle has family tag but no enemy tag
        assertCommandFailure(prepareCommand(4, "enemy"), model,
                String.format(RemoveTagCommand.MESSAGE_TAG_NOT_FOUND_IN, 5));

        //Carl has no tags
        assertCommandFailure(prepareCommand(2, "colleagues"), model,
                String.format(RemoveTagCommand.MESSAGE_TAG_NOT_FOUND_IN, 3));

        //Benson has two tags, owesMoney and friend but no family tag.
        assertCommandFailure(prepareCommand(1, "family"), model,
                String.format(RemoveTagCommand.MESSAGE_TAG_NOT_FOUND_IN, 2));

    }

    @Test
    public void execute_removeSingleTag_success() throws IllegalValueException, NoSuchTagException {
        String expectedMessage = MESSAGE_TAG_REMOVED;

        RemoveTagCommand command = prepareCommand(4, "family");
        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.removeTag(Index.fromZeroBased(4), new Tag("family"));
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
