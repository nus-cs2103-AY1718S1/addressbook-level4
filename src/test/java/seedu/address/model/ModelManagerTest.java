package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COLOR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.property.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColorManager;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalPersons;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void hasTag_emptyModel_returnFalse() throws Exception {
        ModelManager modelManager = new ModelManager();
        assertFalse(modelManager.hasTag(new Tag(VALID_TAG_FRIEND)));
    }

    @Test
    public void setTagColor_validFields_success() throws Exception {
        ModelManager modelManager = new ModelManager();
        Tag myTag = new Tag(VALID_TAG_FRIEND);
        modelManager.setTagColor(myTag, VALID_TAG_COLOR);
        assertEquals(VALID_TAG_COLOR, TagColorManager.getColor(myTag));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEventList().remove(0);
    }

    @Test
    public void removeTag_successfullyRemoveTag() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<Tag> tags = modelManager.getAddressBook().getTagList();
        int originTagSize = tags.size();
        modelManager.removeTag(tags.get(0));
        int newTagSize = modelManager.getAddressBook().getTagList().size();
        assertEquals(1, originTagSize - newTagSize);
    }

    @Test
    public void addPerson_successfullyAddEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyPerson> persons = modelManager.getAddressBook().getPersonList();
        int originalPersonListSize = persons.size();
        modelManager.addPerson(TypicalPersons.A1234B);
        int newPersonListSize = modelManager.getAddressBook().getPersonList().size();
        assertEquals(1, newPersonListSize - originalPersonListSize);
    }
    @Test
    public void sortEventList_successfullySortEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManager1 = new ModelManager(addressBook, userPrefs);
        modelManager.addEvent(TypicalEvents.EVENT2);
        modelManager.addEvent(TypicalEvents.EVENT1);
        modelManager1.addEvent(TypicalEvents.EVENT1);
        modelManager1.addEvent(TypicalEvents.EVENT2);
        modelManager.sortEventList();
        System.out.println(modelManager);
        assertEquals(modelManager, modelManager1);
    }

    @Test
    public void addEvent_successfullyAddEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyEvent> events = modelManager.getAddressBook().getEventList();
        int originalEventListSize = events.size();
        modelManager.addEvent(TypicalEvents.EVENT1);
        int newEventListSize = modelManager.getAddressBook().getEventList().size();
        assertEquals(1, newEventListSize - originalEventListSize);
    }

    @Test
    public void removePerson_successfullyRemoveEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyPerson> persons = modelManager.getAddressBook().getPersonList();
        int originalPersonListSize = persons.size();
        modelManager.deletePerson(persons.get(1));
        int newPersonListSize = modelManager.getAddressBook().getPersonList().size();
        assertEquals(1, originalPersonListSize - newPersonListSize);
    }

    @Test
    public void removeEvent_successfullyRemoveEvent() throws Exception {
        AddressBook addressBook = getTypicalAddressBook();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ObservableList<ReadOnlyEvent> events = modelManager.getAddressBook().getEventList();
        int originalEventListSize = events.size();
        modelManager.addEvent(TypicalEvents.EVENT1);
        modelManager.addEvent(TypicalEvents.EVENT2);
        modelManager.deleteEvent(events.get(1));
        int newEventListSize = modelManager.getAddressBook().getEventList().size();
        assertEquals(1, newEventListSize - originalEventListSize);
    }

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
        String[] keywords = ALICE.getName().getValue().split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredEventsList(PREDICATE_SHOW_ALL_EVENTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
