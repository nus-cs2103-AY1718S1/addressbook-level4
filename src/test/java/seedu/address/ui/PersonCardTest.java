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
        // No tags.
        Person personWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(personWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithNoTags, 1);

        // with tags.
        Person personWithTags = new PersonBuilder().build();
        personCard = new PersonCard(personWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, personWithTags, 2);

        // Changes made to Person reflects on card.
        guiRobot.interact(() -> {
            personWithTags.setName(ALICE.getName());
            personWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(personCard, personWithTags, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // Same person, same index -> returns true.
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // Same object -> returns true.
        assertTrue(personCard.equals(personCard));

        // Null -> returns false.
        assertFalse(personCard.equals(null));

        // Different types -> returns false.
        assertFalse(personCard.equals(0));

        // Different person, same index -> returns false.
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // Same person, different index -> returns false.
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // Verify id is displayed correctly.
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // Verify person details are displayed correctly.
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
