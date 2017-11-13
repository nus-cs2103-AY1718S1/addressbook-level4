package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.ClearBrowserPanelEvent;
import seedu.address.commons.events.ui.FaceBookEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.ui.testutil.EventsCollectorRule;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }
    //@@author RonakLakhotia
    @Test
    public void getFilteredReminderList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void facebook_eventRaised() throws IOException {
        ModelManager model = new ModelManager();
        try {
            model.faceBook(ALICE);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("Person not found");
        }
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof FaceBookEvent);
    }

    @Test
    public void clearBrowserPanel_eventRaised() throws IOException {
        ModelManager model = new ModelManager();
        model.clearBrowserPanel();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ClearBrowserPanelEvent);
    }
    @Test
    public void sendEmailToContacts() throws IOException, URISyntaxException, IllegalValueException {

        ModelManager model = new ModelManager();
        String expectedAppendedMail = "johnd@example.com+johnd@example.com+johnd@example.com+";
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String appendedMail = model.getAppendedEmailIdOfContacts("friends", lastShownList);
        assertFalse(appendedMail.equals(expectedAppendedMail));
    }
    //@@author
    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
