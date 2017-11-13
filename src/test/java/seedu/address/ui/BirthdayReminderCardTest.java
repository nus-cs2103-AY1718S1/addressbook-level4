package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertBirthdayReminderCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.BirthdayReminderCardHandle;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class BirthdayReminderCardTest extends GuiUnitTest {

    //@@author justinpoh
    @Test
    public void display() {
        // no nickname
        Person personWithNoNickName = new PersonBuilder().withNickname("").build();
        BirthdayReminderCard birthdayCard = new BirthdayReminderCard(personWithNoNickName, 1);
        uiPartRule.setUiPart(birthdayCard);
        assertCardDisplay(birthdayCard, personWithNoNickName, 1);

        // with nickname
        Person personWithNickname = new PersonBuilder().withNickname("nickname").build();
        birthdayCard = new BirthdayReminderCard(personWithNickname, 2);
        uiPartRule.setUiPart(birthdayCard);
        assertCardDisplay(birthdayCard, personWithNickname, 2);

        //changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithNickname.setName(ALICE.getName());
            personWithNickname.setNickname(ALICE.getNickname());
        });
        assertCardDisplay(birthdayCard, personWithNickname, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        BirthdayReminderCard birthdayCard = new BirthdayReminderCard(person, 0);

        // same person, same index -> returns true
        BirthdayReminderCard copy = new BirthdayReminderCard(person, 0);
        assertTrue(birthdayCard.equals(copy));

        // same object -> returns true
        assertTrue(birthdayCard.equals(birthdayCard));

        // null -> returns false
        assertFalse(birthdayCard.equals(null));

        // different types -> returns false
        assertFalse(birthdayCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("different name").build();
        assertFalse(birthdayCard.equals(new BirthdayReminderCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(birthdayCard.equals(new BirthdayReminderCard(person, 1)));
    }
    //@@author

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(BirthdayReminderCard personCard, ReadOnlyPerson expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        BirthdayReminderCardHandle birthdayReminderCardHandle = new BirthdayReminderCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", birthdayReminderCardHandle.getId());

        // verify person details are displayed correctly
        assertBirthdayReminderCardDisplaysPerson(expectedPerson, birthdayReminderCardHandle);
    }

}
