package seedu.address.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.ui.testutil.GuiTestAssert.assertExtendedCardDisplaysPerson;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.ExtendedPersonDisplayHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author jacoblipech
public class ExtendedPersonDisplayTest extends GuiUnitTest {

    private ExtendedPersonDisplay extendedPersonDisplay;
    private ExtendedPersonDisplayHandle extendedPersonDisplayHandle;

    @Before
    public void setUp() {
        try {
            guiRobot.interact(() -> extendedPersonDisplay = new ExtendedPersonDisplay());
            uiPartRule.setUiPart(extendedPersonDisplay);
            extendedPersonDisplayHandle = new ExtendedPersonDisplayHandle(extendedPersonDisplay.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Test
    public void display() {
        //ReadOnlyPerson personForTesting = ALICE;
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertExtendedPersonCardDisplay(ALICE, extendedPersonDisplayHandle);
        //personForTesting = BOB;
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(DANIEL, 3)));
        assertExtendedPersonCardDisplay(DANIEL, extendedPersonDisplayHandle);
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
     * Asserts that {@code extendedPersonDisplay} shows the details of {@code expectedPerson} correctly.
     */
    private void assertExtendedPersonCardDisplay(ReadOnlyPerson expectedPerson, ExtendedPersonDisplayHandle
            extendedPersonDisplayHandle) {
        guiRobot.pauseForHuman();

        // verify person details are displayed correctly
        assertExtendedCardDisplaysPerson(expectedPerson, extendedPersonDisplayHandle);
    }
}
