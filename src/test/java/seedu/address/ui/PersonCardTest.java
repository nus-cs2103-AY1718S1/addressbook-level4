package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Rule;
import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowPersonAddressEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.MapCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.testutil.EventsCollectorRule;


public class PersonCardTest extends GuiUnitTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void display() {
        // no tags
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(personWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoTags, 1);

        // with tags
        Person personWithTags = new PersonBuilder().build();
        personCard = new PersonCard(personWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTags, 2);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setAddress(ALICE.getAddress());
            personWithTags.setEmail(ALICE.getEmail());
            personWithTags.setPhone(ALICE.getPhone());
            personWithTags.setTags(ALICE.getTags());
            personWithTags.setRemark(ALICE.getRemark());
            personWithTags.setImage(ALICE.getImage());
            personWithTags.setUsername(ALICE.getUsername());

        });
        assertCardDisplay(personCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }
    @Test
    public void displayImage() {
        Person personWithDisplayPicture = new PersonBuilder().withImage("src/main/resources/images/clock.png")
                .build();
        PersonCard personCard = new PersonCard(personWithDisplayPicture, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithDisplayPicture, 1);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithDisplayPicture.setName(ALICE.getName());
            personWithDisplayPicture.setAddress(ALICE.getAddress());
            personWithDisplayPicture.setEmail(ALICE.getEmail());
            personWithDisplayPicture.setPhone(ALICE.getPhone());
            personWithDisplayPicture.setRemark(ALICE.getRemark());
            personWithDisplayPicture.setImage(ALICE.getImage());
            personWithDisplayPicture.setTags(ALICE.getTags());
        });
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
    @Test
    public void checkIfEventCollected() throws CommandException {
        MapCommand mapCommand = prepareCommand(INDEX_FIRST_PERSON);
        mapCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowPersonAddressEvent);

    }
    /**
     * Returns an {@code LocationCommand} with parameters {@code index}}
     */
    private MapCommand prepareCommand(Index index) {
        MapCommand mapCommand = new MapCommand(index);
        mapCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapCommand;
    }
}
