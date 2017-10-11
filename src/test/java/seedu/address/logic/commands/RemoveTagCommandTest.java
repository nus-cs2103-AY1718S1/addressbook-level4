package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_REMOVED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.parser.RemoveTagParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

public class RemoveTagCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_emptyArg_throwsParseException() {
        RemoveTagParser parser = new RemoveTagParser();
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

    /**
     * Generates a new {@code RemoveTagCommand} which upon execution, removes tag in {@code model}.
     */
    private RemoveTagCommand prepareCommand(String tag) {
        RemoveTagCommand command = new RemoveTagCommand(tag);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
