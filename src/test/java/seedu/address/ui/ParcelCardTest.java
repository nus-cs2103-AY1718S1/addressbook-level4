package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.person.Parcel;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

public class ParcelCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Parcel parcelWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(parcelWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, parcelWithNoTags, 1);

        // with tags
        Parcel parcelWithTags = new PersonBuilder().build();
        personCard = new PersonCard(parcelWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, parcelWithTags, 2);

        // changes made to Parcel reflects on card
        guiRobot.interact(() -> {
            parcelWithTags.setName(ALICE.getName());
            parcelWithTags.setAddress(ALICE.getAddress());
            parcelWithTags.setEmail(ALICE.getEmail());
            parcelWithTags.setPhone(ALICE.getPhone());
            parcelWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(personCard, parcelWithTags, 2);
    }

    @Test
    public void equals() {
        Parcel parcel = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(parcel, 0);

        // same parcel, same index -> returns true
        PersonCard copy = new PersonCard(parcel, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different parcel, same index -> returns false
        Parcel differentParcel = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentParcel, 0)));

        // same parcel, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(parcel, 1)));
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
