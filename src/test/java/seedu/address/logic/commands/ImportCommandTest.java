//@@author danielbrzn
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.testfx.api.FxToolkit;

import com.google.api.services.people.v1.model.Person;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.GooglePersonBuilder;
import seedu.address.testutil.PersonBuilder;

public class ImportCommandTest {

    private Model model = new ModelManager(new AddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_importContactsWithoutGoogleAuthorization_success() {
        ImportCommand importCommand = prepareCommand("Google");
        assertCommandSuccess(importCommand, model, ImportCommand.MESSAGE_IN_PROGRESS, expectedModel);
    }

    @Test
    public void execute_importContactsFromEmptyList_noPersonAdded() {
        ImportCommand importCommand = prepareCommand("Google");
        importCommand.importContacts(new ArrayList<Person>());
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_importContactsFromNonEmptyList_personsAdded()
            throws DuplicatePersonException, InterruptedException, TimeoutException {
        ImportCommand importCommand = prepareCommand("Google");
        ArrayList<Person> googlePersonList = new ArrayList<Person>();
        GooglePersonBuilder googlePerson = new GooglePersonBuilder();
        googlePersonList.add(googlePerson.build());
        // Initialise a primary stage and the FxToolkit for importContacts to run
        FxToolkit.registerPrimaryStage();
        importCommand.importContacts(googlePersonList);
        PersonBuilder person = new PersonBuilder();
        expectedModel.addPerson(person.withInstagram("").withTwitter("")
                .withTags(GooglePersonBuilder.DEFAULT_TAGS).build());
        assertEquals(expectedModel, model);
    }

    @Test
    public void equals() {
        ImportCommand importGoogleCommand = new ImportCommand("Google");
        ImportCommand importAppleCommand = new ImportCommand("Apple");

        // same object -> returns true
        assertTrue(importGoogleCommand.equals(importGoogleCommand));

        // same values -> returns true;
        ImportCommand importGoogleCommandCopy = new ImportCommand("Google");
        assertTrue(importGoogleCommand.equals(importGoogleCommandCopy));

        // same values but different case -> returns true;
        ImportCommand importGoogleCommandCase = new ImportCommand("GoOgLe");
        assertTrue(importGoogleCommand.equals(importGoogleCommandCase));

        // different types -> returns false
        assertFalse(importGoogleCommand.equals(1));

        // null -> returns false
        assertFalse(importGoogleCommand.equals(null));

        // different commands -> returns false
        assertFalse(importGoogleCommand.equals(importAppleCommand));
    }

    /**
     * Returns an {@code ImportCommand} with parameters {@code service}
     */
    public ImportCommand prepareCommand(String service) {
        ImportCommand importCommand = new ImportCommand(service);
        importCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return importCommand;
    }

}
