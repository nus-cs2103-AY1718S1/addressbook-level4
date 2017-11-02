package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ExtendedPersonCardHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author limcel
public class ExtendedPersonCardTest extends GuiUnitTest {
    private ExtendedPersonCard extendedPersonCard;
    private ExtendedPersonCardHandle extendedPersonCardHandle;

    @Before
    public void setUp() {
        try {
            guiRobot.interact(() -> extendedPersonCard = new ExtendedPersonCard());
            uiPartRule.setUiPart(extendedPersonCard);
            extendedPersonCardHandle = new ExtendedPersonCardHandle(extendedPersonCard.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }
    @Test
    public void display() throws Exception {
        // select ALICE
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPersonIsDisplayed(ALICE, extendedPersonCardHandle);
        // select BOB
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertPersonIsDisplayed(BOB, extendedPersonCardHandle);
    }
    //======================== Helper methods ===============================
    /**
     * Asserts that {@code extended person card} displays details of {@code expectedPerson} correctly
     */
    private void assertPersonIsDisplayed(ReadOnlyPerson expectedPerson, ExtendedPersonCardHandle
            extendedPersonCardHandle) {
        guiRobot.pauseForHuman();
        assertEquals(expectedPerson.getName().toString(), extendedPersonCardHandle.getName());
        assertEquals(expectedPerson.getPhone().toString(), extendedPersonCardHandle.getPhone());
        assertEquals(expectedPerson.getAddress().toString(), extendedPersonCardHandle.getAddress());
        assertEquals(expectedPerson.getFormClass().toString(), extendedPersonCardHandle.getFormclass());
        assertEquals(expectedPerson.getGrades().toString(), extendedPersonCardHandle.getGrades());
        assertEquals(expectedPerson.getPostalCode().toString(), extendedPersonCardHandle.getPostalCode());
        assertEquals(expectedPerson.getEmail().toString(), extendedPersonCardHandle.getEmail());
        assertEquals(expectedPerson.getRemark().toString(), extendedPersonCardHandle.getRemark());
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
     * Asserts that {@code ExtendedPersonCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ExtendedPersonCard extendedpersonCard, ReadOnlyPerson expectedPerson) {
        guiRobot.pauseForHuman();

        ExtendedPersonCardHandle extendedPersonCardHandle = new ExtendedPersonCardHandle(extendedPersonCard.getRoot());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, extendedPersonCardHandle);
    }
}
