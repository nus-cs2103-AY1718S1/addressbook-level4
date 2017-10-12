package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.testutil.PersonBuilder;

public class ParcelCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Parcel parcelWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        ParcelCard parcelCard = new ParcelCard(parcelWithNoTags, 1);
        uiPartRule.setUiPart(parcelCard);
        assertCardDisplay(parcelCard, parcelWithNoTags, 1);

        // with tags
        Parcel parcelWithTags = new PersonBuilder().build();
        parcelCard = new ParcelCard(parcelWithTags, 2);
        uiPartRule.setUiPart(parcelCard);
        assertCardDisplay(parcelCard, parcelWithTags, 2);

        // changes made to Parcel reflects on card
        guiRobot.interact(() -> {
            parcelWithTags.setName(ALICE.getName());
            parcelWithTags.setAddress(ALICE.getAddress());
            parcelWithTags.setEmail(ALICE.getEmail());
            parcelWithTags.setPhone(ALICE.getPhone());
            parcelWithTags.setTags(ALICE.getTags());
        });
        assertCardDisplay(parcelCard, parcelWithTags, 2);
    }

    @Test
    public void equals() {
        Parcel parcel = new PersonBuilder().build();
        ParcelCard parcelCard = new ParcelCard(parcel, 0);

        // same parcel, same index -> returns true
        ParcelCard copy = new ParcelCard(parcel, 0);
        assertTrue(parcelCard.equals(copy));

        // same object -> returns true
        assertTrue(parcelCard.equals(parcelCard));

        // null -> returns false
        assertFalse(parcelCard.equals(null));

        // different types -> returns false
        assertFalse(parcelCard.equals(0));

        // different parcel, same index -> returns false
        Parcel differentParcel = new PersonBuilder().withName("differentName").build();
        assertFalse(parcelCard.equals(new ParcelCard(differentParcel, 0)));

        // same parcel, different index -> returns false
        assertFalse(parcelCard.equals(new ParcelCard(parcel, 1)));
    }

    /**
     * Asserts that {@code parcelCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(ParcelCard parcelCard, ReadOnlyParcel expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(parcelCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify parcel details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}
