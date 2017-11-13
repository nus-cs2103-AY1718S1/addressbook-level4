package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import seedu.address.email.EmailManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.ImageInit;

public class UndoableCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
    private final DummyCommand dummyCommand = new DummyCommand(model);

    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());

    @BeforeClass
    public static void setup() throws Exception {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @AfterClass
    public static void recovery() {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void executeUndo() throws Exception {
        dummyCommand.execute();
        ImageInit.initAlice();
        deleteFirstPerson(expectedModel);
        assertFalse(ImageInit.checkAlicePhoto()); //Check if Alice's photo is deleted.
        assertEquals(expectedModel, model);

        showFirstPersonOnly(model);

        // undo() should cause the model's filtered list to show all persons
        dummyCommand.undo();
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new EmailManager());
        assertEquals(expectedModel, model);
        assertTrue(ImageInit.checkAlicePhoto()); //Check if alice's photo is back.
    }

    @Test
    public void redo() throws Exception {
        showFirstPersonOnly(model);
        // redo() should cause the model's filtered list to show all persons
        dummyCommand.redo();
        ImageInit.initAlice();
        deleteFirstPerson(expectedModel);
        assertEquals(expectedModel, model);
        assertFalse(ImageInit.checkAlicePhoto()); //Check if alice's photo is deleted
    }

    /**
     * Deletes the first person in the model's filtered list.
     */
    class DummyCommand extends UndoableCommand {
        DummyCommand(Model model) {
            this.model = model;
        }

        @Override
        public CommandResult executeUndoableCommand() throws CommandException {
            ReadOnlyPerson personToDelete = model.getFilteredPersonList().get(0);
            try {
                model.deletePerson(personToDelete);
            } catch (PersonNotFoundException pnfe) {
                fail("Impossible: personToDelete was retrieved from model.");
            } catch (IOException ioe) {
                fail("Impossible: Image must exist");
            }
            return new CommandResult("");
        }
    }
}
