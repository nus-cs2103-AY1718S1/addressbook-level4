package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no mods
        Person personWithNoMods = new PersonBuilder().withModules(new String[0]).build();
        PersonCard personCard = new PersonCard(personWithNoMods, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoMods, 1);

        // with mods
        Person personWithMods = new PersonBuilder().build();
        personCard = new PersonCard(personWithMods, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithMods, 2);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithMods.setName(ALICE.getName());
            personWithMods.setAddress(ALICE.getAddress());
            personWithMods.setEmails(ALICE.getEmails());
            personWithMods.setPhones(ALICE.getPhones());
            personWithMods.setModules(ALICE.getModules());
        });
        assertCardDisplay(personCard, personWithMods, 2);
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
}
