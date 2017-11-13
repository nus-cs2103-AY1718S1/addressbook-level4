//@@author tshradheya
package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPopularPerson;

import org.junit.Test;

import guitests.guihandles.PopularContactCardHandle;
import seedu.address.model.person.DisplayPicture;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class PopularContactCardTest extends GuiUnitTest {

    @Test
    public void displayImage() {
        Person personWithDisplayPicture = new PersonBuilder().withDisplayPicture("1137944384").build();
        PopularContactCard popularContactCard = new PopularContactCard(personWithDisplayPicture, 1);
        uiPartRule.setUiPart(popularContactCard);
        assertCardDisplay(popularContactCard, personWithDisplayPicture, 1);

        // changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithDisplayPicture.setName(ALICE.getName());
            personWithDisplayPicture.setAddress(ALICE.getAddress());
            personWithDisplayPicture.setEmail(ALICE.getEmail());
            personWithDisplayPicture.setPhone(ALICE.getPhone());
            personWithDisplayPicture.setNickname(ALICE.getNickname());
            personWithDisplayPicture.setDisplayPicture(new DisplayPicture("1137944384"));
            personWithDisplayPicture.setTags(ALICE.getTags());
        });
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PopularContactCard popularContactCard = new PopularContactCard(person, 0);

        // same object -> returns true
        assertTrue(popularContactCard.equals(popularContactCard));

        // null -> returns false
        assertFalse(popularContactCard.equals(null));

        // different types -> returns false
        assertFalse(popularContactCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(popularContactCard.equals(new PopularContactCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(popularContactCard.equals(new PopularContactCard(person, 1)));
    }

    /**
     * Asserts that {@code popularContactCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedRank}.
     */
    private void assertCardDisplay(PopularContactCard popularContactCard, ReadOnlyPerson expectedPerson,
                                   int expectedRank) {
        guiRobot.pauseForHuman();

        PopularContactCardHandle popularContactCardHandle = new PopularContactCardHandle(popularContactCard.getRoot());

        // verify id is displayed correctly
        assertEquals("#" + Integer.toString(expectedRank) + " ", popularContactCardHandle.getRank());

        // verify person details are displayed correctly
        assertCardDisplaysPopularPerson(expectedPerson, popularContactCardHandle);
    }
}
