package seedu.address.logic.commands;

import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.TypicalPersons;



public class BatchCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void execute() throws IllegalValueException, CommandException {

        Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(), new UserPrefs());

        Set<Tag> tagsToDelete = new HashSet<>();
        BatchCommand command = new BatchCommand(tagsToDelete);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        //Should not throw any error
        try {
            command.execute();
        } catch (CommandException e) {
            fail();
        }

        tagsToDelete.add(new Tag("nosuczhtag", "red"));

        command = new BatchCommand(tagsToDelete);
        command.setData(model, new CommandHistory(), new UndoRedoStack());

        thrown.expect(CommandException.class);
        command.execute();

    }
}

