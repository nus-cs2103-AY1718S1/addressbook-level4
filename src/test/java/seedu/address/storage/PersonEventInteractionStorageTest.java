package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.joinEvents;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

// @@author Adoby7
/**
 * Test the XmlAdaptedEventNoParticipant and XmlAdaptedPersonNoParticipation
 * Only test the save and read behavior
 * Integrate with model
 */
public class PersonEventInteractionStorageTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
    private XmlAddressBookStorage xmlAddressBookStorage;
    private XmlEventStorage xmlEventListStorage;
    private String addressBookPath;
    private String eventListPath;

    @Before
    public void setUp() {
        joinEvents(model);
        addressBookPath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        eventListPath = testFolder.getRoot().getPath() + "TempEventList.xml";
        xmlAddressBookStorage = new XmlAddressBookStorage(addressBookPath);
        xmlEventListStorage = new XmlEventStorage(eventListPath);
    }

    @Test
    public void testSaveAndRead() throws Exception {
        saveAndRead();

        // Disjoin an event
        Person person = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Event event = new Event(model.getFilteredEventList().get(INDEX_SECOND_EVENT.getZeroBased()));
        model.quitEvent(person, event);
        saveAndRead();

        // Join an event
        person = new Person(model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased()));
        event = new Event(model.getFilteredEventList().get(INDEX_THIRD_EVENT.getZeroBased()));
        model.joinEvent(person, event);
        saveAndRead();
    }

    /**
     * Save and read back the model, and check whether they are the same
     */
    private void saveAndRead() throws Exception {
        xmlAddressBookStorage.saveAddressBook(model.getAddressBook(), addressBookPath);
        xmlEventListStorage.saveEventStorage(model.getEventList(), eventListPath);
        ReadOnlyAddressBook readBackPerson = xmlAddressBookStorage.readAddressBook(addressBookPath).get();
        ReadOnlyEventList readBackEvent = xmlEventListStorage.readEventStorage(eventListPath).get();
        assertEquals(model.getAddressBook(), new AddressBook(readBackPerson));
        assertEquals(model.getEventList(), new EventList(readBackEvent));
    }
}
